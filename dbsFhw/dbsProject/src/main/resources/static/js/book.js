var vue = new Vue({
    el: '#app',
    data: {
        bBookname: "",
        bAuthor: "",
        bPublishTime: "",
        bPublisher: "",
        bRateLow: "",
        bRateHigh: "",
        books: [], //查询结果
        currentBook: {}, //当前编辑的事项
        dialogVisible: false, //对话框是否显示
        editMode: false,  //当前是否是编辑模式（还是添加模式）
        currentPage:1,
        totalCount:1,
        pageSizes:[10,20,30,40],
        PageSize:10,
        imgUrl: "https://img.alicdn.com/tfs/TB161Wer1uSBuNjy1XcXXcYjFXa-2528-1266.png",
        detailVisible: false,
        fileContent: "",
        csvVisible: false,
        csvTitle: ""
    },
    methods: {
        query: function (bBookname,bAuthor) {
            var path = '/books?pNum=' + this.currentPage + "&&pSize=" + this.PageSize
            if (this.bBookname && this.bBookname != "") path = path + "&&name=" + this.bBookname
            if(this.bAuthor && this.bAuthor != "") path = path + "&&author=" +this.bAuthor
            if(this.bPublishTime && this.bPublishTime != "") path = path + "&&startTime=" + this.bPublishTime[0] + "&&endTime=" + this.bPublishTime[1]
            if(this.bPublisher && this.bPublisher != "") path = path + "&&publisher=" +this.bPublisher
            if(this.bRateLow && this.bRateHigh && this.bRateLow != "" || this.bRateHigh != ""){
                if(!this.bRateLow) this.bRateLow = 0
                if(!this.bRateHigh) this.bRateHigh = 10
                path = path + "&&ratingLow=" + this.bRateLow + "&&ratingHigh=" + this.bRateHigh;
            }
            var self = this
            axios.get(path)
                .then(response =>{
                    self.books = response.data.content
                    this.totalCount =  response.data.totalElements
                })
                .catch(e => self.$message.error(e.response.data))
            this.currentPage = 1
        },
        deleteBook: function (book) {
            var self = this
            axios.delete('/books/' + book.id)
                .then(response => self.query())
                .catch(e => self.$message.error(e.response.data))
        },
        showEdit: function (book) {
            this.dialogVisible = true
            this.editMode = true;
            this.currentBook = Object.assign({}, book)
        },
        showAdd: function (book) {
            this.detailVisible=false
            this.dialogVisible = true
            this.editMode = false;
            this.currentBook = {complete: false}
        },
        saveBook: function () {
            var self = this
            if (self.editMode) {
                axios.put('/books/' + self.currentBook.id, self.currentBook)
                    .then(response => self.query())
                    .catch(e => self.$message.error(e.response.data))
            } else {
                axios.post('/books', self.currentBook)
                    .then(response => self.query())
                    .catch(e => self.$message.error(e.response.data))
            }
            this.dialogVisible = false
        },
        handleSizeChange(val) {
            // 改变每页显示的条数
            this.PageSize=val
            // 注意：在改变每页显示的条数时，要将页码显示到第一页
            this.currentPage=1
            this.query()
        },
        // 显示第几页
        handleCurrentChange(val) {
            // 改变默认的页数
            this.currentPage=val
            this.query()
        },
        showImg(row){
            this.currentBook=row
            this.imgUrl=this.currentBook.imgUrl
            if(this.imgUrl == null) this.imgUrl = "https://img.alicdn.com/tfs/TB161Wer1uSBuNjy1XcXXcYjFXa-2528-1266.png"
            if(!this.dialogVisible) this.detailVisible=true
        },
        clearSiev(){
            this.bRateHigh = ""
            this.bRateLow = ""
            this.bPublisher = ""
            this.bBookname = ""
            this.bPublishTime = ""
            this.bAuthor = ""
            this.query()
        },
        openCsvDialog() {
            this.file = {};
            this.csvVisible = true;
            this.csvTitle = '批量添加';
            this.$refs.upload.clearFiles();
        },
        importCsv() {
            var path = '/books/upload?csvContent=' + this.fileContent.toString()
            var self = this
            axios.get(path)
                .then(response =>{
                    const h = this.$createElement;
                    this.$notify({
                        title: '提示',
                        message: h('i', { style: 'color: teal'}, '上传成功！')
                    });
                })
                .catch(e => {
                    const h = this.$createElement;
                    this.$notify({
                        title: '提示',
                        message: h('i', { style: 'color: red'}, '上传失败！')
                    });
                })
        },
        readCsv(file, fileList) {
            let reader = new FileReader();
            reader.readAsText(file.raw, "UTF-8");
            var $this = this
            reader.onload = e => {
                $this.fileContent = e.target.result
            }
        }

    }
})
vue.query();
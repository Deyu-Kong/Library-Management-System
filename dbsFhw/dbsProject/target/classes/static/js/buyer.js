var vue = new Vue({
    el: '#app',
    data: {
        // keyword: "",
        bookid:null,
        userid:null,
        buyers: [], //查询结果
        currentBuyer: {book:{},user:{}}, //当前编辑的事项
        dialogVisible: false, //对话框是否显示
        editMode: false,  //当前是否是编辑模式（还是添加模式）
        currentPage:1,
        totalCount:1,
        pageSizes:[10,20,30,40],
        PageSize:10,
        // imgUrl: "https://img.alicdn.com/tfs/TB161Wer1uSBuNjy1XcXXcYjFXa-2528-1266.png",
        detailVisible: false
    },
    methods: {
        query: function (keyword) {
            var path = '/buyers';
            var flag = false;
            if (this.bookid&&this.bookid != null){
                path = path + "?bookID=" + this.bookid;
                if(!flag) flag = true;
            }
            if (this.userid&&this.userid != null){
                if(flag) path = path + "&&"
                else{
                    flag = true;
                    path = path + "?"
                }
                path = path + "userID=" +this.userid

            }
            var self = this
            axios.get(path)
                .then(response => {
                    self.buyers = response.data
                    this.totalCount =  this.buyers.length;
                })
                .catch(e => self.$message.error(e.response.data))
            this.currentPage = 1
        },
        deleteBuyer: function (buyer) {
            var self = this
            axios.delete('/buyers/' + buyer.buyerId)
                .then(response => self.query())
                .catch(e => self.$message.error(e.response.data))
        },
        showEdit: function (buyer) {
            this.dialogVisible = true
            this.editMode = true;
            this.currentBuyer= Object.assign({}, buyer)
        },
        showAdd: function (buyer) {
            this.dialogVisible = true
            this.editMode = false;
            // this.currentBuyer = {complete: false}
            this.currentBuyer = {book:{},user:{},complete: false}
        },
        saveBuyer: function () {
            var self = this
            if (self.editMode) {
                axios.put('/buyers/' + self.currentBuyer.buyerId, self.currentBuyer)
                    .then(response => self.query())
                    .catch(e => self.$message.error(e.response.data))
            } else {
                axios.post('/buyers', self.currentBuyer)
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
        },
        // 显示第几页
        handleCurrentChange(val) {
            // 改变默认的页数
            this.currentPage=val
        },
        clearSiev(){
            this.bookid = null
            this.userid = null

            this.query()
        }
    }
})
vue.query();
var vue = new Vue({
    el: '#app',
    data: {
        keyword: "",
        papers: [], //查询结果
        currentPaper: {uploader:{}}, //当前编辑的事项
        dialogVisible: false, //对话框是否显示
        editMode: false  //当前是否是编辑模式（还是添加模式）
    },
    methods: {
        query: function (keyword) {
            var path = '/papers';
            if (this.keyword !== "") path = path + "?paperTitle=" + this.keyword;
            var self = this
            axios.get(path)
                .then(
                    response => {
                        self.papers = response.data;
                        console.log(self.papers);
                    }
                )
                .catch(e => self.$message.error(e.response.data))
        },
        deletePaper: function (paper) {
            var self = this
            axios.delete('/papers/' + paper.paperId)
                .then(response => self.query())
                .catch(e => self.$message.error(e.response.data))
        },
        showEdit: function (paper) {
            this.dialogVisible = true
            this.editMode = true;
            this.currentPaper = Object.assign({}, paper)
            // console.log(this.currentPaper)
        },
        showAdd: function (paper) {
            this.dialogVisible = true
            this.editMode = false;
            // this.currentPaper = {complete: false}
            this.currentPaper = {uploader:{},complete: false}
        },
        savePaper: function () {
            var self = this
            if (self.editMode) {
                axios.put('/papers/' + self.currentPaper.paperId, self.currentPaper)
                    .then(response => self.query())
                    .catch(e => self.$message.error(e.response.data))
            } else {
                axios.post('/papers', self.currentPaper)
                    .then(response => self.query())
                    .catch(e => self.$message.error(e.response.data))
            }
            this.dialogVisible = false
        }
    }
})
vue.query();

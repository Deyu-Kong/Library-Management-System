var vue = new Vue({
    el: '#app',
    data: {
        keyword: "",
        buyers: [], //查询结果
        currentBuyer: {}, //当前编辑的事项
        dialogVisible: false, //对话框是否显示
        editMode: false  //当前是否是编辑模式（还是添加模式）
    },
    methods: {
        query: function (keyword) {
            var path = '/buyers';
            if (this.keyword != "") path = path + "?buyerId=" + this.keyword;
            var self = this
            axios.get(path)
                .then(response => self.buyers = response.data)
                .catch(e => self.$message.error(e.response.data))
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
            this.currentBuyer = {complete: false}
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
        }
    }
})
vue.query();
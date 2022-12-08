var vue = new Vue({
    el: '#app',
    data: {
        keyword: "",
        books: [], //查询结果
        currentBook: {}, //当前编辑的事项
        dialogVisible: false, //对话框是否显示
        editMode: false  //当前是否是编辑模式（还是添加模式）
    },
    methods: {
        query: function (keyword) {
            var path = '/books';
            if (this.keyword != "") path = path + "?bookName=" + this.keyword;
            var self = this
            axios.get(path)
                .then(response => self.books = response.data)
                .catch(e => self.$message.error(e.response.data))
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
        }
    }
})
vue.query();
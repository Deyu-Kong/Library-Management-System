var vue = new Vue({
    el: '#app',
    data: {
        keyword: "",
        users: [], //查询结果
        currentUser: {}, //当前编辑的事项
        dialogVisible: false, //对话框是否显示
        editMode: false  //当前是否是编辑模式（还是添加模式）
    },
    methods: {
        query: function (keyword) {
            var path = '/users';
            if (this.keyword != "") path = path + "?userName=" + this.keyword;
            var self = this
            axios.get(path)
                .then(response => self.users = response.data)
                .catch(e => self.$message.error(e.response.data))
        },
        deleteUser: function (user) {
            var self = this
            axios.delete('/users/' + user.userId)
                .then(response => self.query())
                .catch(e => self.$message.error(e.response.data))
        },
        showEdit: function (user) {
            this.dialogVisible = true
            this.editMode = true;
            this.currentUser = Object.assign({}, user)
        },
        showAdd: function (user) {
            this.dialogVisible = true
            this.editMode = false;
            this.currentUser = {complete: false}
        },
        saveUser: function () {
            var self = this
            if (self.editMode) {
                axios.put('/users/' + self.currentUser.userId, self.currentUser)
                    .then(response => self.query())
                    .catch(e => self.$message.error(e.response.data))
            } else {
                axios.post('/users', self.currentUser)
                    .then(response => self.query())
                    .catch(e => self.$message.error(e.response.data))
            }
            this.dialogVisible = false
        }
    }
})
vue.query();
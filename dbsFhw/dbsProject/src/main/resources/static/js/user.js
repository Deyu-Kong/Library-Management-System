var vue = new Vue({
    el: '#app',
    data: {
        // keyword: "",
        uName:"",
        uIdentity:"",
        users: [], //查询结果
        currentUser: {}, //当前编辑的事项
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
            var path = '/users';
            var flag = false;
            if (this.uName&&this.uName != "") {
                path = path + "?userName=" + this.uName;
                if(!flag) flag = true;
            }
            if(this.uIdentity && this.uIdentity != ""){
                if(flag) path = path + "&&"
                else{
                    flag = true;
                    path = path + "?"
                }
                path = path + "userIdentity=" +this.uIdentity
            }
            var self = this
            axios.get(path)
                .then(response => {
                    self.users = response.data;
                    this.totalCount =  this.users.length;
                })
                .catch(e => self.$message.error(e.response.data))
            this.currentPage = 1
        },
        deleteUser: function (user) {
            var self = this
            axios.delete('/users/' + user.userId)
                .then(response => self.query())
                .catch(e => self.$message.error("违反外键约束，请先删除改用户的买书记录与上传记录"))
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
            this.uIdentity = ""
            this.uName = ""

            this.query()
        }
    }
})
vue.query();
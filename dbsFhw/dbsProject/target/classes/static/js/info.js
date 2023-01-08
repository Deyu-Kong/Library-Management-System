var vue = new Vue({
    el: '#app',
    data: {
        // global
        bookCount: null,
        paperCount: null,
        userCount: null,
        message: "",
        // user
        userName: "",
        buyCount: null,
        uploadCount: null,
        showUserInfo: false,
    },
    methods: {
        handleClick(tab, event) {
            // console.log(tab, event);
            console.log("切换统计选项")
            this.showUserInfo = false;
            if (tab.name === "globalInfo") {
                console.log("统计全局信息")
                axios.get('/info/global')
                    .then(response => {
                        this.bookCount = response.data.bookCount;
                        this.paperCount = response.data.paperCount;
                        this.userCount = response.data.userCount;
                    })
                    .catch(e => self.$message.error(e.response.data))
            }
        },
        getUserInfo() {
            console.log("查询用户统计信息")
            axios.get('/info/user/' + this.userName)
                .then(response => {
                    this.buyCount = response.data.buyCount;
                    this.uploadCount = response.data.uploadCount;
                    this.showUserInfo = true;
                })
                .catch(e => self.$message.error(e.response.data))
        }
    }
})

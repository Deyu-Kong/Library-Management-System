var vue = new Vue({
    el: '#app',
    data: {
        bookCount: null,
        paperCount: null,
        userCount: null,
        message:"",
    },
    methods: {
        handleClick(tab, event) {
            // console.log(tab, event);
            console.log("切换统计选项")
            if(tab.name==="globalInfo"){
                console.log("统计全局信息")
                axios.get('/info/global')
                    .then(response => {
                        this.bookCount=response.data.bookCount;
                        this.paperCount=response.data.paperCount;
                        this.userCount=response.data.userCount;
                    })
                    .catch(e => self.$message.error(e.response.data))
            }
        }
    }
})

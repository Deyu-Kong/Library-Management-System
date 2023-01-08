var vue = new Vue({
    el: '#app',
    data() {
        return {
            // global
            bookCount: null,
            paperCount: null,
            userCount: null,
            message: "",

            // book
            option: {
                title: {
                    text: '评分分布情况'
                },
                tooltip: {},//提示框
                legend: {
                    data: ['图书数量']
                },
                xAxis: {
                    data: []
                },
                yAxis: {},
                series: [
                    {
                        name: '图书数量',
                        type: 'bar',
                        data: []
                    }
                ],
            },
            
            // user
            userName: "",
            buyCount: null,
            uploadCount: null,
            showUserInfo: false,
        }

    },
    methods: {
        handleClick(tab, event) {
            // console.log(tab, event);
            console.log("切换统计选项");
            this.showUserInfo = false;
            if (tab.name === "globalInfo") {
                console.log("统计全局信息");
                axios.get('/info/global')
                    .then(response => {
                        this.bookCount = response.data.bookCount;
                        this.paperCount = response.data.paperCount;
                        this.userCount = response.data.userCount;
                    })
                    .catch(e => self.$message.error(e.response.data))
            }
            else if (tab.name === "bookInfo") {
                console.log("统计图书信息");
                axios.get('info/book')
                    .then(response => {
                        console.log(response);
                        this.option.xAxis.data = response.data.xAxisData;
                        this.option.series[0].data = response.data.seriesData;
                        console.log(this.option)
                        this.chartChange();
                    })
                    // .catch(e => self.$message.error(e.response.data))
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
                .catch(e => self.$message.error(e.response.data));
        },
        chartChange() {
            // 基于准备好的dom，初始化echarts实例
            const myEcharts = echarts.init(document.getElementById('bar_rating'));
            // 使用刚指定的配置项和数据显示图表。
            myEcharts.setOption(this.option, true);
            console.log(myEcharts);
        }
    }
})



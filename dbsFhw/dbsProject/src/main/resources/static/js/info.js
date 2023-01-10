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
            rateBarOption: {
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
            // paper
            UploaderRankBarOption: {
                title: {
                    text: '论文贡献排行'
                },
                tooltip: {},//提示框
                legend: {
                    data: ['上传数']
                },
                xAxis: {
                    data: []
                },
                yAxis: {},
                series: [
                    {
                        name: '上传数',
                        type: 'bar',
                        data: []
                    }
                ],
            },

            // user
            userName: "",
            buyCount: null,
            uploadCount: null,
            selectSingleUser: false,

            mid: "",
            mname: "",
            mcnt: "",



            identityPieOption: {
                tooltip: {
                    trigger: 'item'
                },
                legend: {
                    top: '5%',
                    left: 'center'
                },
                series: [
                    {
                        name: '人数',
                        type: 'pie',
                        radius: ['40%', '70%'],
                        avoidLabelOverlap: false,
                        label: {
                            show: false,
                            position: 'center'
                        },
                        emphasis: {
                            label: {
                                show: true,
                                fontSize: 40,
                                fontWeight: 'bold'
                            }
                        },
                        labelLine: {
                            show: false
                        },
                        data: []
                    }
                ]
            },
        }

    },
    methods: {
        handleClick(tab, event) {
            // console.log(tab, event);
            console.log("切换统计选项");
            this.selectSingleUser = false;
            if (tab.name === "globalInfo") {
                console.log("统计全局信息");
                axios.get('/info/global')
                    .then(response => {
                        this.bookCount = response.data.bookCount;
                        this.paperCount = response.data.paperCount;
                        this.userCount = response.data.userCount;
                    })
                    .catch(e => self.$message.error(e.response.data))
            } else if (tab.name === "bookInfo") {
                console.log("统计图书信息");
                axios.get('info/book')
                    .then(response => {
                        console.log(response);
                        this.rateBarOption.xAxis.data = response.data.xAxisData;
                        this.rateBarOption.series[0].data = response.data.seriesData;
                        // console.log(this.rateBarOption)
                        this.rateBarChange();
                    });
                // .catch(e => self.$message.error(e.response.data))
            } else if (tab.name === "paperInfo") {
                console.log("统计论文信息");
                axios.get("info/paper")
                    .then(response => {
                        console.log(response);
                        this.UploaderRankBarOption.xAxis.data = response.data.xAxisData;
                        this.UploaderRankBarOption.series[0].data = response.data.seriesData;
                        this.UploaderRankBarChange();
                    });
            } else if (tab.name === "userInfo") {

                console.log("统计用户信息");
                axios.get('info/user')
                    .then(response => {
                        console.log(response.data.identityPie);
                        this.identityPieOption.series[0].data = response.data.identityPie;
                        this.identityPieChange();
                        this.mid = response.data.mid;
                        this.mname = response.data.mname;
                        this.mcnt = response.data.mcnt;
                    })
            }
        },
        getUserInfo() {
            console.log("查询用户统计信息")
            axios.get('/info/user/' + this.userName)
                .then(response => {
                    this.buyCount = response.data.buyCount;
                    this.uploadCount = response.data.uploadCount;
                    this.selectSingleUser = true;
                })
                .catch(e => self.$message.error(e.response.data));
        },
        rateBarChange() {
            // 基于准备好的dom，初始化echarts实例
            const rateBarEcharts = echarts.init(document.getElementById('bar_rating'));
            // 使用刚指定的配置项和数据显示图表。
            rateBarEcharts.setOption(this.rateBarOption, true);
            console.log(rateBarEcharts);
        },
        identityPieChange() {
            const identityPieEcharts = echarts.init(document.getElementById("identity_pie"))
            identityPieEcharts.setOption(this.identityPieOption, true);
        },
        UploaderRankBarChange() {
            const UploaderRankBarEcharts = echarts.init(document.getElementById("paper_uploader_rank_bar"))
            UploaderRankBarEcharts.setOption(this.UploaderRankBarOption, true);
        }


    }
})



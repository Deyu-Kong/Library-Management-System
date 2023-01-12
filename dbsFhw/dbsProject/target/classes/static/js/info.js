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
            publisherBarOption:{
                title: {
                    text: '出版社rank20'
                },
                tooltip: {},//提示框
                legend: {
                    data: ['出版图书数量']
                },
                xAxis: {
                    data: []
                },
                yAxis: {},
                series: [
                    {
                        name: '出版图书数量',
                        type: 'bar',
                        data: []
                    }
                ],
            },
            bookRankOption:{
                title: {
                    text: '图书rank20'
                },
                tooltip: {},//提示框
                legend: {
                    data: ['购买数量']
                },
                xAxis: {
                    data: []
                },
                yAxis: {},
                series: [
                    {
                        name: '购买数量',
                        type: 'bar',
                        data: []
                    }
                ],
            },
            publishYearOption : {
                xAxis: {
                    type: 'category',
                    data: []
                },
                yAxis: {
                    type: 'value'
                },
                series: [
                    {
                        data: [],
                        type: 'line'
                    }
                ]
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

            paperYearOption : {
                xAxis: {
                    type: 'category',
                    data: []
                },
                yAxis: {
                    type: 'value'
                },
                series: [
                    {
                        data: [],
                        type: 'line'
                    }
                ]
            },
            loaderPieOption: {
                tooltip: {
                    trigger: 'item'
                },
                legend: {
                    top: '5%',
                    left: 'center'
                },
                series: [
                    {
                        name: '篇数',
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
            // user
            userName: "",
            buyCount: null,
            uploadCount: null,
            selectSingleUser: false,

            mid: "",
            mname: "",
            mcnt: "",

            bookTitles:[],


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

            userBuyBarOption: {
                title: {
                    text: '购书前十名'
                },
                tooltip: {},//提示框
                legend: {
                    data: ['购买数']
                },
                xAxis: {
                    data: []
                },
                yAxis: {},
                series: [
                    {
                        name: '购买数',
                        type: 'bar',
                        data: []
                    }
                ],
            },
            buyerWordCloudOption:{
                series: [{
                    type: 'wordCloud',
                    sizeRange: [15, 80],
                    rotationRange: [0, 0],
                    rotationStep: 45,
                    gridSize: 8,
                    shape: 'pentagon',
                    width: '100%',
                    height: '100%',
                    // maskImage:"",
                    textStyle: {
                        normal: {
                            color: function () {
                                return 'rgb(' + [
                                    Math.round(Math.random() * 160),
                                    Math.round(Math.random() * 160),
                                    Math.round(Math.random() * 160)
                                ].join(',') + ')';
                            },
                            fontFamily: 'sans-serif',
                            fontWeight: 'normal'
                        },
                        emphasis: {
                            shadowBlur: 10,
                            shadowColor: '#333'
                        }
                    },
                    data:  []

                }]
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
                        this.rateBarOption.xAxis.data = response.data.rate_xAxisData;
                        this.rateBarOption.series[0].data = response.data.rate_seriesData;
                        // this.rateBarChange();
                        this.chartChange("bar_rating",this.rateBarOption)
                        this.publisherBarOption.xAxis.data = response.data.publisher_xAxisData;
                        this.publisherBarOption.series[0].data = response.data.publisher_seriesData;
                        this.chartChange("publisher_rank",this.publisherBarOption)
                        this.publishYearOption.xAxis.data = response.data.year_xAxisData;
                        this.publishYearOption.series[0].data = response.data.year_seriesData;
                        this.chartChange("publish_year",this.publishYearOption)
                        this.buyerWordCloudOption.series[0].data=response.data.bookTitles;
                        // this.buyerWordCloudOption.series[0].maskImage=new Image();
                        // this.buyerWordCloudOption.series[0].maskImage.src="data:image/png;base64,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"
                        this.chartChange3("buyerWordCloud",this.buyerWordCloudOption);
                        this.bookRankOption.xAxis.data=response.data.book_xAxisData;
                        this.bookRankOption.series[0].data=response.data.book_seriesData;
                        this.chartChange("book_rank",this.bookRankOption);

                    });
                // .catch(e => self.$message.error(e.response.data))
            } else if (tab.name === "paperInfo") {
                console.log("统计论文信息");
                axios.get("info/paper")
                    .then(response => {
                        // console.log(response);
                        this.UploaderRankBarOption.xAxis.data = response.data.xAxisData;
                        this.UploaderRankBarOption.series[0].data = response.data.seriesData;
                        this.chartChange("paper_uploader_rank_bar",this.UploaderRankBarOption)
                        this.paperYearOption.xAxis.data = response.data.paperyear_xAxisData;
                        this.paperYearOption.series[0].data = response.data.paperyear_seriesData;
                        this.chartChange("paper_year",this.paperYearOption)

                        this.loaderPieOption.series[0].data=response.data.loaderPie;
                        this.chartChange("loader_pie",this.loaderPieOption)
                    });
            } else if (tab.name === "userInfo") {

                console.log("统计用户信息");
                axios.get('info/user')
                    .then(response => {
                        // console.log(response.data.identityPie);
                        this.identityPieOption.series[0].data = response.data.identityPie;
                        this.chartChange("identity_pie",this.identityPieOption)
                        this.mid = response.data.mid;
                        this.mname = response.data.mname;
                        this.mcnt = response.data.mcnt;
                        this.userBuyBarOption.xAxis.data = response.data.xAxisData;
                        this.userBuyBarOption.series[0].data = response.data.seriesData;
                        // this.buyerWordCloudOption.data=response.data.bookTitles;
                        this.chartChange("userBuyBar",this.userBuyBarOption);

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
        chartChange(div_id,option){
            const myEcharts = echarts.init(document.getElementById(div_id));
            myEcharts.setOption(option, true);
        },
        chartChange3(div_id,option){
            const myEcharts = echarts3.init(document.getElementById(div_id));
            myEcharts.setOption(option, true);
            window.addEventListener("resize", function() {
                myEcharts.resize();
            })
        },
    }
})



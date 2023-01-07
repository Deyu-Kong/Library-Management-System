var vue = new Vue({
    el: '#app',
    data: {
        keyword: "",
        books: [], //查询结果
        currentBook: {}, //当前编辑的事项
        dialogVisible: false, //对话框是否显示
        editMode: false,  //当前是否是编辑模式（还是添加模式）
        currentPage: 1,
        totalCount: 1,
        pageSizes: [10, 20, 30, 40],
        PageSize: 10,
        imgUrl: "https://img.alicdn.com/tfs/TB161Wer1uSBuNjy1XcXXcYjFXa-2528-1266.png",
        detailVisible: false
    },
    methods: {}
})

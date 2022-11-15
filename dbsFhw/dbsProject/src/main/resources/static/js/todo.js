 var vue=new Vue({
        el: '#app',
        data:{
            keyword:"",
            todos: [], //查询结果
            currentTodo:{}, //当前编辑的事项
            dialogVisible: false, //对话框是否显示
            editMode:false  //当前是否是编辑模式（还是添加模式）
        },
        methods: {
            query: function (keyword) {
                var path='/todos';
                if(this.keyword!="") path=path+"?name="+this.keyword;
                var self = this
                axios.get(path)
                    .then(response=>self.todos = response.data)
                    .catch(e =>self.$message.error(e.response.data))
            },
            deleteTodo: function (todo) {
                var self = this
                axios.delete('/todos/'+todo.id)
                    .then(response=>self.query())
                    .catch(e =>self.$message.error(e.response.data))
            },
            showEdit:function(todo){
                this.dialogVisible = true
                this.editMode=true;
                this.currentTodo = Object.assign({},todo)
            },
            showAdd:function(todo){
                this.dialogVisible = true
                this.editMode=false;
                this.currentTodo = {complete:false}
            },
            saveTodo:function(){
                var self = this
                if(self.editMode){
                    axios.put('/todos/'+self.currentTodo.id,self.currentTodo)
                    .then(response=> self.query())
                    .catch(e =>self.$message.error(e.response.data))
                }else{
                    axios.post('/todos',self.currentTodo)
                    .then(response=> self.query())
                    .catch(e => self.$message.error(e.response.data))
                }
                this.dialogVisible = false
            }
        }
    })
    vue.query();
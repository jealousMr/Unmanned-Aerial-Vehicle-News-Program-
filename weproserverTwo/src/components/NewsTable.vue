<template>
  <div class="mains">
    <div class="content-panel">
      <h4>News Table</h4>
      <hr>
      <table class="table table-striped table-advance table-hover">
        <thead>
          <tr>
            <th>id</th>
            <th>标题</th>
            <th>副标题</th>
            <th>发布时间</th>
            <th>更多详情</th>
          </tr>
        </thead>
        <tbody>
           <tr v-for="(item,index) in newsList" :key="index">
             <td>
               <img :src="item.imgUrl" style="width: 60px;height:auto">
             </td>
             <td class="hidden-phone duo">{{item.title}}</td>
             <td class="duo">{{item.titleTwo}}</td>
             <td>{{item.sendTime}} </td>
             <td>
               <button class="btn btn-info btn-xs" @click="deletes(item.id,index)">
                 <img src="../assets/trash.png" style="width: 20px;height: 20px">
               </button>
               <button class="btn btn-success btn-xs" data-toggle="modal" data-target="#detail" @click="setCurr(item.id)">
                 <img src="../assets/mor.png" style="width: 20px;height: 20px">
               </button>
             </td>
           </tr>
        </tbody>
      </table>
      <div class="next">
        <button class="btn btn-info" @click="last">上一页</button>
        <button class="btn btn-success" @click="next">下一页</button>

      </div>
    </div>

<!--    模态框-->
    <div class="modal fade" id="detail" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
      <div class="modal-dialog">
        <div class="modal-content norep">
          <div class="modal-header">
            <div class="writter">
              <label>
                发布者ID：
              </label>
              <span>{{currentNews.userId}}</span>
            </div>
          </div>
          <div class="modal-body">
            <form>
              <div class="form-group">
                <label>
                  新闻主标题
                </label>
                <input type="text" class="form-control" v-model="title" :placeholder="currentNews.title" style="margin-bottom: 20px">
              </div>
              <div class="form-group">
                <label>
                  新闻副标题
                </label>
                <input v-model="titleTwo" type="text" class="form-control" :placeholder="currentNews.titleTwo" style="margin-bottom: 20px">
              </div>
              <div class="form-group content">
                <label>新闻内容</label>
                <textarea v-model="content" class="form-control" :placeholder="currentNews.content"></textarea>
              </div>
            </form>
          </div>
          <div class="modal-footer">
            <button type="button" class="btn btn-info" data-dismiss="modal">关闭</button>
            <button type="button" class="btn btn-primary" @click="up">更新</button>
          </div>
        </div>
      </div>
    </div>


  </div>
</template>

<script>
    export default {
        name: "NewsTable",
      data(){
          return{
            newsList:[],
            begin:1,
            end:10,
            currentNews:'',
            title:'',
            titleTwo:'',
            content:'',
          }
      },
      mounted(){
          this.end=10
          this.begin=0
        this.newsList.splice(0)
          var that=this
        this.$axios.get('/newsList',{
          params:{
            begin:that.begin,
            end:that.end
          }
        })
          .then((response)=>{
            if(response.data.state=='-1'){
              console.log("没有下一页了")
            }else{
              var jsons= JSON.parse(response.data.list)
             for(let i=0;i<jsons.length;i++)
                that.newsList.push(jsons[i])
            }
          })
          .catch((error)=>{
            console.log(error)
          })
      },
      methods:{
        next(){
            this.newsList.splice(0)
            this.begin=this.end+1
            this.end=this.end+10
            var that=this
            this.$axios.get('/newsList',{
              params:{
                begin:that.begin,
                end:that.end
              }
            })
              .then((response)=>{
                if(response.data.state=='-1'){
                  console.log("没有下一页了")
                  alert("没有下一页了")
                }else{
                  var jsons= JSON.parse(response.data.list)
                  for(let i=0;i<jsons.length;i++)
                    that.newsList.push(jsons[i])
                }
              })
              .catch((error)=>{
                console.log(error)
              })
          },
        last(){
          this.newsList.splice(0)
          this.end=this.begin-1
          this.begin=this.begin-10
          if(this.begin<1){
            alert("已经第一页")
          }else{
            var that=this
            this.$axios.get('/newsList',{
              params:{
                begin:that.begin,
                end:that.end
              }
            })
              .then((response)=>{
                var jsons= JSON.parse(response.data.list)
                for(let i=0;i<jsons.length;i++)
                  that.newsList.push(jsons[i])
              })
              .catch((error)=>{
                console.log(error)
              })
          }
        },
        setCurr(id){
          for(let i=0;i<this.newsList.length;i++)
            if(id==this.newsList[i].id){
              this.currentNews=this.newsList[i]
              break;
            }
        },
        up(){
          if(this.title.length>1||this.titleTwo.length>1||this.content.length>1)
          {
            let data = {"title":this.title,"titleTwo":this.titleTwo,"content":this.content,"articleId":this.currentNews.articleId}
            this.$axios.post('/updateNews',data)
              .then(res=>{
                if(res.data.state=='1')
                  alert("更新成功")
              })
          }
        },
        deletes(itemId,index){
          var mes=confirm("确认删除？")
          if(mes==true) {
            this.newsList.splice(index,1)
            this.$axios.get('/deleteNews',{
              params:{
                tableId:itemId
              }
            })
              .then((response)=>{
              })
              .catch((error)=>{
                console.log(error)
              })
            alert("删除成功")
          }
        }
      }
    }
</script>

<style scoped>
  .norep{
    opacity: 0.9;
    background-color: #d0d5da;
  }
  form{
    color: #2e2f2f;
  }
  .modal-footer button{
    opacity: 0.7;
  }
.content label{
  border-left:3px solid #a92c2b;
  padding: 3px;
}
.content textarea{
    width: 100%;
    min-height: 160px;
}
  form label{
    color: #4e4f4f;
    font-weight: 500;
  }
  form input {
    opacity: 0.5;
  }
  form textarea{
    opacity: 0.5;
  }
  @keyframes stream {
  0%  {
    background-position: 0 0;
  }
  100% {
    background-position: -100% 0;
  }
  }
  .writter{
    display: flex;
    padding: 2px;
    margin: 0;
    border-top-left-radius: 40px;
    overflow: hidden;
    font-size: 1.2rem;
    font-style: oblique;
    text-align: left;
    line-height: 3rem;
    background-image: -webkit-linear-gradient(left, #b99011, #cedaff, #6aff02, #ffb22d, #FFDCDB 65%,green 65%,
    #c7c8d4 70%, #de000c 60%, #00ceff 75%, #6e6f6f 85%,#AEBCFF 100%);
    -webkit-text-fill-color: transparent;
    -webkit-background-clip: text;
    animation: stream 15s infinite linear;
    background-size: 200% 100%;
    background-color: #e6e6e6;
  }
  .duo{
    white-space: nowrap;
    overflow: hidden;
    text-overflow: ellipsis;
    max-width: 120px;
  }
  .next{
    margin: 5px;
    padding-left: 36%;
  }
  .next button{
    margin-left: 10px;
  }
.mains{
    margin-top: 100px;
    padding: 0;
}
.content-panel {
  background: #ffffff;
  box-shadow: 0px 3px 2px #aab2bd;
  padding-top: 15px;
  padding-bottom: 5px;
}
.content-panel h4 {
  margin-left: 10px;
}
hr {
  margin-top: 20px;
  margin-bottom: 20px;
  border: 0;
  border-top: 1px solid #797979;
}
.table {
  width: 100%;
  max-width: 100%;
  margin-bottom: 20px;
}
table {
  background-color: transparent;
  border-spacing: 0;
  border-collapse: collapse;
}
thead {
  display: table-header-group;
  vertical-align: middle;
  border-color: inherit;
}
tr {
  display: table-row;
  vertical-align: inherit;
  border-color: inherit;
}
.table>thead>tr>th {
  vertical-align: bottom;
  border-bottom: 2px solid #ddd;
}
th {
  text-align: left;
}
tbody {
  display: table-row-group;
  vertical-align: middle;
  border-color: inherit;
}
.table-striped>tbody>tr:nth-of-type(odd) {
  background-color: #f9f9f9;
}
tr {
  display: table-row;
  vertical-align: inherit;
  border-color: inherit;
}
.table>tbody>tr>td{
  padding: 8px;
  line-height: 1.42857143;
  vertical-align: top;
  border-top: 1px solid #ddd;
}
.btn-xs {
  padding: 1px 5px;
  font-size: 12px;
  line-height: 1.5;
  border-radius: 3px;
}
</style>

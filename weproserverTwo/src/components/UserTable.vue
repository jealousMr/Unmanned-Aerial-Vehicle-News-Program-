<template>
  <div class="mm">
    <div class="content-panel fixs">
      <h4>User Table</h4>
      <hr>
      <table class="table table-hover">
        <thead>
        <tr>
          <th>...</th>
          <th>用户ID</th>
          <th>用户昵称</th>
          <th>发布文章总数</th>
          <th>发布视频总数</th>
        </tr>
        </thead>
        <tbody>
        <tr v-for="(item,index) in userList" :key="index">
          <td>
            <img class="touxiang" :src="item.imgUrl">
          </td>
          <td class="hidden-phone">{{item.userId}}</td>
          <td>{{item.nickName}} </td>
          <td>{{item.articleSum}}</td>
          <td>{{item.videoSum}}</td>
          <td>
            <button class="btn btn-info btn-xs" @click="deletes(index)">
              <img src="../assets/trash.png" style="width: 20px;height: 20px">
            </button>
          </td>
        </tr>
        </tbody>
      </table>
      <div class="next">
        <button class="btn btn-info">上一页</button>
        <button class="btn btn-success">下一页</button>
      </div>
    </div>
  </div>
</template>

<script>
    export default {
        name: "UserTable",
      data(){
        return{
          userList:[],
          begin:1,
          end:10,
        }
      },
      methods:{
          deletes(index){
            var mes = confirm("确认删除？")
            if (mes == true){
              this.userList.splice(index,1)
            }
          }
      },
      mounted(){
        this.end=10
        this.begin=0
        this.userList.splice(0)
        var that=this
        this.$axios.get('/userLists',{
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
                that.userList.push(jsons[i])
            }
          })
          .catch((error)=>{
            console.log(error)
          })
      }
    }
</script>

<style scoped>

  .touxiang{
    width: 40px;
    height: 40px;
    border-radius: 100px;
  }
  .mm{
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
    text-align: left;
  }
  tbody {
    display: table-row-group;
    vertical-align: middle;
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
  .next{
    margin: 5px;
    padding-left: 36%;
  }
</style>

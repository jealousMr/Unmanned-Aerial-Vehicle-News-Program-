<template>
  <div class="mm">
    <div class="content-panel">
      <h4>Video Table</h4>
      <hr>
      <table class="table table-hover">
        <thead>
          <tr>
            <th>封面</th>
            <th>视频标题</th>
            <th>发布者ID</th>
          </tr>
        </thead>
        <tbody>
        <tr v-for="(item,index) in videoList" :key="index">
          <td>
            <img :src="item.imgUrl" class="smalls">
          </td>
          <td class="hidden-phone shen">{{item.title}}</td>
          <td>{{item.userId}} </td>
          <td>
            <button class="btn btn-info btn-xs" @click="deleteVideo(item.id,index)">
              <img src="../assets/trash.png" style="width: 20px;height: 20px">
            </button>
            <button class="btn btn-success btn-xs" data-toggle="modal" data-target="#detail" @click="currentVideo=item">
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
        <div class="modal-content">
          <div class="modal-header">
            <div class="writter">
              <label>
                发布者ID：
              </label>
              <span>
               {{currentVideo.userId}}
              </span>
            </div>
          </div>
          <div class="modal-body">
            <div class="titleContain">
              <label>视频标题</label>
              <p>{{currentVideo.title}}</p>
            </div>
            <video :src="currentVideo.url" controls="controls">
              your browser does not support the video tag
            </video>
            <input type="text" v-model="title" class="form-control" placeholder="（输入更改标题）">
          </div>
          <div class="modal-footer">
            <button type="button" class="btn btn-info" data-dismiss="modal">关闭</button>
            <button type="button" class="btn btn-primary" @click="upTitle">更新</button>
          </div>
        </div>
      </div>
    </div>

  </div>
</template>

<script>
    export default {
        name: "VideoTable",
      data() {
        return {
          datas: 10,
          videoList: [],
          begin:1,
          end:10,
          currentVideo:'',
          title:''
        }
      },
      mounted(){
        this.end=10
        this.begin=0
        this.videoList.splice(0)
        var that=this
        this.$axios.get('/videoOpList',{
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
                that.videoList.push(jsons[i])
            }
          })
          .catch((error)=>{
            console.log(error)
          })
      },
      methods:{
          next(){
            this.videoList.splice(0)
            this.begin=this.end+1
            this.end=this.end+10
            var that=this
            this.$axios.get('/videoOpList',{
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
                    that.videoList.push(jsons[i])
                }
              })
              .catch((error)=>{
                console.log(error)
              })
          },
          last(){
            this.videoList.splice(0)
            this.end=this.begin-1
            this.begin=this.begin-10
            if(this.begin<1){
              alert("已经第一页")
            }else{
              var that=this
              this.$axios.get('/videoOpList',{
                params:{
                  begin:that.begin,
                  end:that.end
                }
              })
                .then((response)=>{
                  var jsons= JSON.parse(response.data.list)
                  for(let i=0;i<jsons.length;i++)
                    that.videoList.push(jsons[i])
                })
                .catch((error)=>{
                  console.log(error)
                })
            }
          },
        deleteVideo(id,index) {
          var mes = confirm("确认删除？")
          if (mes == true){
            this.videoList.splice(index, 1)
          this.$axios.get('/deleteVideo', {
            params: {
              tableId: id
            }
          })
            .then((response) => {
            })
            .catch((error) => {
              console.log(error)
            })
        }
        },
        upTitle(){
          var that=this
          if(this.title.length>1)
          this.$axios.get('/updateVideoTitle',{
            params:{
              videoId:that.currentVideo.videoId,
              title:that.title
            }
          })
            .then((response)=>{
              if(response.data.state=='1')
                alert("更新成功")
            })
            .catch((error)=>{
              console.log(error)
            })
        }
      }
    }
</script>

<style scoped>
  video{
    width: 100%;
    height: auto;
    border: 3px solid #D1CFD4;
  }
  .titleContain{
    background-color: #eeefef;
    padding: 6px;
    border-top-left-radius: 30px;
    font-weight: 500;
    color: #6e6f6f;
    margin: 5px;
  }
  .titleContain label{
    border-left:3px solid red ;
    margin-left: 10px;
  }
  .titleContain p{
    margin-left: 10px;
  }
  .shen{
    white-space: nowrap;
    overflow: hidden;
    text-overflow: ellipsis;
    max-width: 160px;
  }
  .smalls{
    width: 40px;
    height: 40px;
    border-radius: 80px;
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

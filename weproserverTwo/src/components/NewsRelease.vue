<template>
  <div class="main-container">
    <h3>News - Release</h3>
    <div class="form-panel">
      <form class="form-horizontal style-form">
        <div class="form-group">
          <label class="control-label col-md-2">新闻主标题</label>
          <div class="col-md-3 col-xs-11">
            <input v-model="title" class="form-control" type="text" style="width: 700px">
          </div>
        </div>
        <div class="form-group">
          <label class="control-label col-md-2">新闻副标题</label>
          <div class="col-md-3 col-xs-11">
            <input v-model="titleTwo" class="form-control" type="text" style="width: 700px">
          </div>
        </div>
        <div class="form-group">
          <label class="control-label col-md-2">新闻内容.</label>
          <div class="col-md-3 col-xs-11">
            <textarea v-model="content" style="width: 700px;height: 100px" class="form-control"></textarea>
          </div>
        </div>
        <div class="form-group">
          <label class="control-label col-md-2 lab">Image Upload</label>
          <div class="col-md-9">
            <div class="fileupload" data-provides="fileupload" v-if="isImg==false">
              <input name="file" accept="image/*" id="open"  type="file" style="display: none" ref="open"  @change="tirggerFile" multiple="multiple">
              <div class="fileupload-new thumbnail" style="width: 200px; height: 150px;">
                <img src="../assets/not.png">
              </div>
            </div>
            <img :src="srcOthers" class="suit" v-else>
          </div>
          <div class="btns">
            <button type="button" class="btn btn-danger" @click="openChoice">Change</button>
            <button type="button" class="btn btn-success">Remove</button>
          </div>
        </div>
        <div class="form-group subm">
          <button type="button" class="btn btn-info" @click="upgo">submit</button>
        </div>
      </form >
    </div>
  </div>
</template>

<script>
    export default {
        name: "NewsRelease",
      data(){
          return{
            srcOthers:'',
            isImg:false,
            fd:null,
            title:'',
            titleTwo:'',
            content:''
          }
      },
      mounted(){
        this.isImg=false
        this.fd=new FormData()
      },
      methods: {
        openChoice() {
          this.$refs.open.dispatchEvent(new MouseEvent('click'))
        },
        tirggerFile(e) {
          let _this = this
          var files = e.target.files[0];
          console.log(e.target.files[0]);
          this.fd.append('file', e.target.files[0]);
          if (!e || !window.FileReader) return
          let reader = new FileReader()
          reader.readAsDataURL(files)
          reader.onloadend = function (e) {
              _this.srcOthers = this.result
              _this.isImg=true
          }
        },
        upgo() {
          if (this.title.length > 1 && this.titleTwo.length > 1 && this.content.length > 1 && this.isImg){
            this.fd.append('title', this.title);
          this.fd.append('titleTwo', this.titleTwo);
          this.fd.append('content', this.content);
          let config = {
            headers: {'Content-Type': 'multipart/form-data'}
          };
          var that = this
          this.$axios.post('/newsRelease', that.fd, config).then(function (response) {
            if(response.data=="success")
              alert("发表成功")
          })
          }else{
            alert("请输入新闻内容，标题及图片")
          }
        }
      }

    }
</script>

<style scoped>
.main-container{
  margin-top: 80px;
  color: #797979;
}
.suit{
  max-width: 300px;
  max-height: 200px;
  margin-left: 26px;
  margin-bottom: 10px;
  margin-top: 5px;
  box-shadow: 0px 3px 2px #aab2bd;
  border: 1px solid #d0d5da;
}
.subm{
  margin-left: 10%;
  border-radius: 80px;
}
.btns{
  margin-left: 35px;
}
h3 {
  margin-top: 20px;
  margin-bottom: 10px;
  font-size: 24px;
}
.form-panel {
  background: #ffffff;
  margin: 10px;
  padding: 10px;
  box-shadow: 0px 3px 2px #aab2bd;
  text-align: left;
}
form {
  display: block;
  margin-top: 0em;
}
label {
  text-align: left;
}
.control-label {
  padding-top: 7px;
  margin-bottom: 0;
  text-align: right;
  font-weight: 400;
}
.fileupload {
  margin-bottom: 9px;
}

.fileupload .thumbnail {
  display: inline-block;
  margin-bottom: 5px;
  overflow: hidden;
  text-align: center;
  vertical-align: middle;
}
.thumbnail {
  display: block;
  padding: 4px;
  margin-bottom: 20px;
  line-height: 1.42857143;
  background-color: #fff;
  border: 1px solid #ddd;
  border-radius: 4px;
  -webkit-transition: border .2s ease-in-out;
  -o-transition: border .2s ease-in-out;
  transition: border .2s ease-in-out;
}
.fileupload .thumbnail > img {
  display: inline-block;
  max-height: 100%;
  vertical-align: middle;
}
.thumbnail>img {
  margin-right: auto;
  margin-left: auto;
  margin-top: 20%;
}

</style>

<template>
  <div class="main-container">
    <h3>Video - Release</h3>
    <div class="form-panel">
      <form class="form-horizontal style-form">
        <div class="form-group">
          <label class="control-label col-md-2">视频主题</label>
          <div class="col-md-3 col-xs-11">
            <input v-model="title" class="form-control" type="text" style="width: 700px">
          </div>
        </div>
        <div class="form-group">
          <input type="file" ref="openVideo" @change="tirggerFile2" style="display: none" accept="video/*">
          <span class="btn btn-theme02 btn-file">
            <span class="fileupload-new" @click="choice2">
              Select video
            </span>
          </span>
          <span class="fileupload-preview" style="margin-left:5px;">
            <p>{{videoName}}</p>
          </span>
        </div>
        <div class="form-group">
            <span class="btn btn-theme02 btn-file">
            <span class="fileupload-new" @click="links=!links">
              or 链接发布
            </span>
          </span>
          <input v-model="link" type="text" class="form-control" placeholder="输入视频链接" style="width: 87%;margin-top: 10px;margin-left: 4%" v-if="links">
        </div>
        <div class="form-group">
          <label class="control-label col-md-2" style="margin-left: 4%">视频封面</label>
          <div class="col-md-9">
            <div v-if="isImg==false" class="fileupload fileupload-exists" data-provides="fileupload">
              <input type="file" ref="open" accept="image/*" @change="tirggerFile" style="display: none" >
              <div class="fileupload-new thumbnail" style="width: 200px; height: 150px;">
                <img src="../assets/not.png">
              </div>
            </div>
            <img :src="srcImg" class="suit" v-else>
          </div>
          <div class="btns">
            <button type="button" class="btn btn-danger" @click="choice">Change</button>
            <button type="button" class="btn btn-success">Remove</button>
          </div>
        </div>
        <div class="form-group subm">
          <button type="button" class="btn btn-info" @click="up">submit</button>
        </div>
      </form>
    </div>
  </div>
</template>

<script>
    export default {
        name: "VideoRelease",
        data(){
          return{
            srcImg:'',
            isImg:false,
            isVideo:false,
            title:'',
            fd:null,
            videoName:'',
            links:false,
            link:''
          }
        },
      mounted(){
        this.isImg=false
        this.fd=new FormData()
        this.videoName=''
        this.isVideo=false
        this.links=false
        this.link=''
      },
      methods:{
          choice(){
            this.$refs.open.dispatchEvent(new MouseEvent('click'))
          },
        choice2(){
          this.$refs.openVideo.dispatchEvent(new MouseEvent('click'))
        },
        tirggerFile(e) {
          let _this = this
          var files = e.target.files[0];
          this.fd.append('Imgfile', e.target.files[0]);
          if (!e || !window.FileReader) return
          let reader = new FileReader()
          reader.readAsDataURL(files)
          reader.onloadend = function (e) {
            _this.srcImg = this.result
            _this.isImg=true
          }
        },
        tirggerFile2(e) {
          let _this = this
          var files = e.target.files[0];
          this.fd.append('Videofile', e.target.files[0]);
          if (!e || !window.FileReader) return
          let reader = new FileReader()
          reader.readAsDataURL(files)
          reader.onloadend = function (e) {
            _this.videoName = this.result
            _this.isVideo=true
          }
        },
        up(){
           if(this.isImg  && this.title.length>0){
             this.fd.append("title",this.title)
             let config = {
               headers: {'Content-Type': 'multipart/form-data'}
             };
             var that = this
             if(this.links==false){
               if(this.isVideo){
                 this.$axios.post('/videoRelease', that.fd, config).then(function (response) {
                   if(response.data=="success")
                     alert("发表成功")
                 })
               }else {
                 alert("请选择视频文件")
               }
             }else{
               if (this.link.length>1){
                  that.fd.append("videoUrl",this.link)
                 this.$axios.post('/videoReleaseByLink', that.fd, config).then(function (response) {
                   if(response.data=="success")
                     alert("发表成功")
                 })
               } else{
                 alert("请输入视频链接")
               }
             }
           }else{
             alert("请输入电影标题，封面")
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
  .fileupload-preview p{
    white-space: nowrap;
    overflow: hidden;
    text-overflow: ellipsis;
    max-width: 260px;
    margin-left: 5%;
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
  .fileupload-exists .fileupload-new, .fileupload-new .fileupload-exists {
    display: none;
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
  .btn-theme02 {
    color: #fff;
    background-color: #ac92ec;
    border-color: #967adc;
  }
  .btn-file {
    position: relative;
    overflow: hidden;
    vertical-align: middle;
    margin-left: 30px;
  }
  .subm{
    margin-left: 10%;
    border-radius: 80px;
  }
</style>

<template>
  <div class="videoContain">
    <div class="imgContainer">
      <img :src="[imgUrl[0]||'/static/images/backgroud.png']">
    </div>
      <div class="imgNav">
        <div class="imgIcon" @click="chooseImg">
          <img src="/static/images/simg.png">
        </div>
        <div class="Tip">
          <h6>设置视频封面</h6>
          <p>← &nbsp;&nbsp;&nbsp;click to right</p>
        </div>
      </div>

    <div class="fileContainer">
      <div class="fileList">
        <div class="mainFile" @click="editeTitle">
          <img src="/static/images/titles.png">
          <p>设置视频title</p>
          <img src="/static/images/personIcon/next.png" class="next">
        </div>
        <div class="textAreas" v-if="isShowEdite">
         <input type="text" v-model="titleMes">
        </div>
        <div class="mainFile" @click="chooseVideo">
          <img src="/static/images/video.png">
          <p>选择视频文件</p>
          <img src="/static/images/personIcon/next.png" class="next">
        </div>
        <div class="submits">
          <button @click="goLoadVideo">SUBMIT</button>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
    export default {
        name: "sendVideo",
      data(){
          return{
            isShowEdite:false,
            titleMes:'',
            imgUrl:[],
            videoUrl:[],
          }
      },
      onLoad(){
        this.isShowEdite=false
        this.imgUrl=[]
        this.videoUrl=[]
        this.titleMes=''
      },
      methods:{
        editeTitle(){
          this.isShowEdite=!this.isShowEdite
        },
        chooseImg(){
          var that=this
          wx.chooseImage({
            count:1,
            sizeType:['original','compressed'],
            sourceType:['album','camera'],
            success:function (res) {
              that.imgUrl=res.tempFilePaths
            }
          })
        },
        chooseVideo(){
          var that=this
          wx.chooseVideo({
            sourceType:['album','camera'],
            maxDuration:60,
            camera:'back',
            success:function (res) {
              that.videoUrl=res.tempFilePath
            }
          })
        },
        goLoadImg(videoId){
          if (this.imgUrl!=''){
            var that=this
            wx.uploadFile({
              url:'http://localhost:8080/videoImg',
              filePath:that.imgUrl[0],
              name:'file',
              formData:{
                'videoId':videoId
              },
              success:function (res) {
                console.log("图片上传成功")
              }
            })
          }
        },
        goLoadVideo(){
          if (this.videoUrl!=''&&this.titleMes!=''){
            var that=this
            wx.uploadFile({
              url:'http://localhost:8080/video',
              filePath:that.videoUrl,
              name:'file',
              formData:{
                'title': that.titleMes,
                'userId':that.global.openId
              },
              success:function (res) {
                var temp=JSON.parse(res.data)
                if(temp.state=='success'){
                  var videoId=temp.videoId
                  console.log("视频上传成功,开始上传封面")
                  if(that.imgUrl.length!=0){
                    that.goLoadImg(videoId)
                  }
                  wx.showToast({
                    title: '上传成功',
                    icon: 'succes',
                    duration: 1000,
                    mask:true
                  })
                }
                else{
                  console.log("上传失败")
                }
              }
            })
          }
        },
      }
    }
</script>

<style scoped>
.videoContainer{
  margin: 0;
  padding: 0;
}
  .imgContainer{
    width: 100%;
    height: 360rpx;
  }
  .imgContainer img{
    width: 100%;
    height: 360rpx;
  }
  .imgNav{
    display: flex;
    width: 100%;
    height: 200rpx;
    background-color: #393939;
  }
  .imgIcon{
    width: 140rpx;
    height: 140rpx;
    border-radius: 100rpx;
    margin-top: 4%;
    margin-left: 4%;
    border: 1px solid #dfe357;
  }
  .imgIcon img{
    margin-top: -10rpx;
    margin-left: -10rpx;
    width: 160rpx;
    height: 160rpx;
    border-radius: 100rpx;
  }
  .Tip{
    margin-top: 2%;
    margin-left: 5%;
    padding: 10rpx;
  }
  .Tip h6{
    font-size: 50rpx;
    color: white;
  }
  .Tip p{
    font-size: 30rpx;
    color: white;
    margin-top: 20rpx;
    margin-left: 12%;
  }
.fileContainer{
  margin: 0;
  padding: 0;
}
.fileList{
  margin-top: 50rpx;
}
  .mainFile{
    display: flex;
    width: 100%;
    height: 70rpx;

    padding: 10rpx;
    margin-top: 20rpx;
  }
  .mainFile img{
    width: 80rpx;
    height: 80rpx;
    border-radius: 80rpx;
    margin-top: 5rpx;
    margin-left: 10rpx;
  }
  .mainFile p{
    border-bottom:1px solid #dddde0;
    margin-left: 20rpx;
    margin-top: 10rpx;
    width: 560rpx;
    font-size: 33rpx;
    color: #BFBFBF;
  }
  .next{
    width: 50rpx;
    height: 50rpx;
  }
  .submits{
    margin-top: 160rpx;
  }
  .submits button{
    width: 400rpx;
    height: 100rpx;
    border-radius: 50rpx;
    background-color: #dc4d50;
    color: white;
    text-align: center;
  }
  .textAreas{
    padding: 10rpx;
  }
  .textAreas input{
    width: 460rpx;
    height: 160rpx;
    border: 1px solid #c9c9cc;
    border-radius: 30rpx;
    margin-left: 20%;
  }



</style>

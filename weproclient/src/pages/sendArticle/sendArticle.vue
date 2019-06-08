<template>
  <div>

    <div class="editContainer">
      <div class="tip">
        <img src="/static/images/modifle.png">
      </div>
      <div class="mainTitle">
        <h1>文章主标题:</h1>
        <input type="text" v-model="title">
      </div>
      <div class="mainTitle">
        <h1>文章副标题:</h1>
        <input type="text" v-model="titleTwo">
      </div>
      <div class="content">
        <h1>文章内容:</h1>
         <textarea
           placeholder="编辑文章内容"
           placeholder-style="color:#7a7a7a; font-size: 30rpx;"
           v-model="content"
         />
      </div>
    </div>

    <div class="editContainer">
    <div class="j-pic-upload">
      <div class="j-upload-btn" @click="uploadImg()" :style="{'width':width || '120rpx','height':height || '120rpx'}">
        <span class="j-upload-add">+</span>
      </div>
      <img @click="previewImg(index)" v-for="(src,index) in urls" :key="src" :src="src" :style="{'width':width || '120rpx','height':height || '120rpx'}" class="img" >
    </div>
    <button v-on:click="go" class="go">文章上传</button>
    </div>

    <div class="editContainer">
      <div class="tip">
        <img src="/static/images/personIcon/heart.png">
      </div>
      <div class="mainTitle">
        <h1>文章实时预览：</h1>
      </div>
      <div class="now_title">
        <h1>{{title}}</h1>
      </div>
      <div class="now_titleTwo">
        <p>{{titleTwo}}</p>
      </div>
      <div class="now_content">
        <p>
        {{content}}
        </p>
      </div>
    </div>


  </div>
</template>

<script>
    export default {
        name: "sendArticle",
      props:["width","height","max","srcs"],
      data(){
          return{
            urls:[],
            title:'',
            titleTwo:'',
            content:''
          }
      },
      mounted(){
        this.urls = this.srcs || [];
      },
      methods:{
          go(){
            wx.showToast({
              title: '加载中',
              icon:'loading',
              duration:1000
            })
            if(this.title==''||this.titleTwo==''||this.content==''){
              wx.showModal({
                title: '提示',
                content: '请填写标题和内容',
                success:function(res){
                  if(res.confirm){
                    console.log('弹框后点取消')
                  }else{
                    console.log('弹框后点取消')
                  }
                }
            })
            }
            else {
              var that=this
              wx.uploadFile({
                url: 'http://localhost:8080/addArticle',
                filePath: this.urls[0],
                name: 'file',
                formData: {
                  userId:that.global.openId,
                  title:that.title,
                  titleTwo:that.titleTwo,
                  content:that.content
                },
                success(res) {
                  const data = res.data
                  console.log(data)
                  wx.showToast({
                    title:'发布成功',
                    icon:'success',
                    duration:2000
                  })
                }
              })
            }
          },
        uploadImg(){
          let that = this;
          wx.chooseImage({
            count: that.max || 3,
            sizeType: ['original', 'compressed'],
            sourceType: ['album', 'camera'],
            success: function (res) {
              res.tempFilePaths.forEach(v=>{
                console.log(v)
                that.urls.push(v);
              });
              that.$emit("choosed",{all:that.urls,currentUpload:res.tempFilePaths});
            }
          })
        },
        previewImg(index){
          let that = this;
          wx.showActionSheet({
            itemList:["预览","删除"],
            success: function(res) {
              if(res.tapIndex === 0){
                wx.previewImage({
                  current:that.urls[index],
                  urls:that.urls
                });
              } else {
                that.urls.splice(index,1);
                that.$emit("delete",that.urls);
              }
            },
          });
        }
     }
    }
</script>

<style scoped>
.editContainer{
  margin: 20rpx;
  margin-top: 30rpx;
  padding: 10rpx;
  display: block;
  border: 1rpx solid #eee;
  box-shadow: #D7D7D7 1px 1px 1px 1px;
}
.tip{
  width: 60rpx;
  height: 60rpx;
}
.tip img{
  width: 60rpx;
  height: 60rpx;
}
.mainTitle{
  margin-top: 20rpx;
  padding: 10rpx;
}
.mainTitle h1{
  font-size: 30rpx;
  color: #7a7a7a;
  margin-bottom: 10rpx;
}
.mainTitle input{
  border:2rpx solid #dcdcdc;
  height: 80rpx;
}
.content{
  margin-top: 20rpx;
  padding: 10rpx;
}
.content h1{
  font-size: 30rpx;
  color: #7a7a7a;
  margin-bottom: 10rpx;
}
.content textarea{
  width: 100%;
  height: 400rpx;
  border:1rpx solid #dcdcdc;
  box-shadow: #D7D7D7 1px 0px 1px 0px;
}
.now_title{
  padding: 10rpx;
}
.now_title h1{
  font-size: 34rpx;
}
.now_titleTwo{
  width: 90%;
  background-color: #f7f7f7;
  padding: 10rpx;
  border-left:10rpx solid #e1e1e1
}
.now_content{
  font-size: 30rpx;
  text-indent:2em;
  line-height: 60rpx;
}

  .j-pic-upload{
    padding: 10rpx;
    display: flex;
    flex-direction: row;
    align-items: center;
    flex-wrap: wrap;
  }
  .j-upload-btn{
    border: 1px dashed #ddd;
    display: flex;
    flex-direction: row;
    justify-content: center;
    align-items: center;
    margin-right: 20rpx;
  }
  .j-upload-add{
    font-size: 80rpx;
    font-weight: 500;
    color:#C9C9C9;
  }
  .img{
    margin:10rpx 20rpx 10rpx 0;
  }
  .go{
    width: 300rpx;
    background-color: transparent;
    border:1rpx solid #dcdcdc;
    font-size: 22rpx;
    color: #7a7a7a;
  }
</style>

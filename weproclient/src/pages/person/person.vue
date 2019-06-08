<template>
  <div class="personContainer">
    <img src="/static/images/backgroud.png" class="backImg">
    <div class="backgroundContainer">
        <div class="personPhoto">
        <img :src="[userInfo.avatarUrl||'/static/images/tu1.png']">
      </div>
      <div class="personInfo">
        <p>{{userInfo.nickName || '用户名'}}</p>
        <img src="/static/images/modifle.png">
      </div>
      <div class="countContainer">
        <div class="count">
          <p>1654</p>
          <p>阅读量</p>
        </div>
        <div class="count">
          <p>16</p>
          <p>赞</p>
        </div>
        <div class="count">
          <p>1654</p>
          <p>粉丝</p>
        </div>
      </div>
    </div>

    <div class="listContainer">
      <div class="title">
        <p>用户</p>
      </div>
      <div class="someList">

        <div class="box" v-on:click="getRoots">
          <img src="/static/images/person.png" class="iconOne">
          <p>
            我的信息
          </p>
          <img src="/static/images/personIcon/next.png" class="iconTwo">
        </div>
        <div class="box" v-on:click="goMyConcern">
          <img src="/static/images/personIcon/heart.png" class="iconOne">
          <p>我的发布</p>
          <img src="/static/images/personIcon/next.png" class="iconTwo">
        </div>
      </div>
      <div class="title">
        <p>阅读</p>
      </div>
      <div class="someList">
        <div class="box" v-on:click="goCollectionArticle">
          <img src="/static/images/personIcon/shou.png" class="iconOne">
          <p>我的收藏</p>
          <img src="/static/images/personIcon/next.png" class="iconTwo">
        </div>
        <div class="box" v-on:click="goLoadArticle">
          <img src="/static/images/personIcon/send.png" class="iconOne">
          <p>文章发布</p>
          <img src="/static/images/personIcon/next.png" class="iconTwo">
        </div>
        <div class="box" v-on:click="goSendVideo">
          <img src="/static/images/personIcon/zang.png" class="iconOne">
          <p>视频发布</p>
          <img src="/static/images/personIcon/next.png" class="iconTwo">
        </div>
        <div class="box" v-on:click="goReadingHistory">
          <img src="/static/images/personIcon/history.png" class="iconOne">
          <p>阅读历史</p>
          <img src="/static/images/personIcon/next.png" class="iconTwo">
        </div>
      </div>
      <div class="title">
        <p>其他</p>
      </div>
      <div class="someList">
        <div class="box">
          <img src="/static/images/modifle.png" class="iconOne">
          <p>设置</p>
          <img src="/static/images/personIcon/next.png" class="spe">
        </div>
        <div class="box">
          <button open-type="getUserInfo" class="roots" @getuserinfo="bindGetUserInfo" @click="getUserInfo1">...</button>
          <p id="pp">获取权限</p>
          <img src="/static/images/personIcon/next.png" class=" spe2">
        </div>
      </div>
    </div>

  </div>
</template>

<script>
    export default {
        name: "person",
    data(){
      return{
        userInfo:{}
      }
    },
      mounted(){
        this.getSetting()
      },
      methods:{
        goLoadArticle(){
          wx.navigateTo({
            url: '/pages/sendArticle/main'
          })
        },
        goCollectionArticle(){
          wx.navigateTo({
            url: '/pages/collectionArticle/main'
          })
        },
        goReadingHistory(){
          wx.navigateTo({
            url: '/pages/readingHistory/main'
          })
        },
        goMyConcern(){
          wx.navigateTo({
            url: '/pages/concern/main'
          })
        },
        goSendVideo(){
          wx.navigateTo({
            url: '/pages/sendVideo/main'
          })
        },
        getSetting(){
          var that=this
          wx.getSetting({
            success: function(res){
              if (res.authSetting['scope.userInfo']) {
                wx.getUserInfo({
                  success: function(res) {
                    console.log(res.userInfo)
                    that.userInfo=res.userInfo
                    //用户已经授权过
                    console.log('用户已经授权过')
                    that.getId()
                  }
                })
              }else{
                console.log('用户还未授权过')
              }
            }
          })
        },
        getUserInfo1(){
          console.log('click事件首先触发')
          // 判断小程序的API，回调，参数，组件等是否在当前版本可用。  为false 提醒用户升级微信版本
          if(wx.canIUse('button.open-type.getUserInfo')){
            // 用户版本可用
          }else{
            console.log('请升级微信版本')
          }
        },
        bindGetUserInfo(e) {
            console.log("----------------")
           console.log(e.mp.detail.rawData)
          this.userInfo=e.mp.detail.rawData

          if (e.mp.detail.rawData){
            //用户按了允许授权按钮
            console.log('用户按了允许授权按钮')
            this.getId()
          } else {
            //用户按了拒绝按钮
            console.log('用户按了拒绝按钮')
          }
        },
        getId(){
          var that=this
          if(this.global.openId=="null")
             wx.login({
            success: function (r) {
              var code = r.code;//登录凭证
              if (code) {
                //2、调用获取用户信息接口
                wx.getUserInfo({
                  success: function (res) {
                   // console.log({encryptedData: res.encryptedData, iv: res.iv, code: code})
                   //  var AppId="wxf17e681fda3c8379";
                   //  var AppSercet="d7e5fa9bff577bda1223a422c4a14df5";
                   that.$fly.post('http://localhost:8080/login',{
                     code:code,
                     nickName:that.userInfo.nickName,
                     img:that.userInfo.avatarUrl
                   })
                     .then(function (response) {
                       console.log(response.data);
                       that.global.setOpenId(response.data)
                       that.global.setNickName(that.userInfo.nickName)
                       that.global.setImgUrl(that.userInfo.avatarUrl)
                   })
                     .catch(function (error) {
                       console.log(error);
                     });

                  },
                  fail: function () {
                    console.log('获取用户信息失败')
                  }
                })

              } else {
                console.log('获取用户登录态失败！' + r.errMsg)
              }
            },
            fail: function () {
              callback(false)
            }
          })
        }
      }
    }
</script>

<style scoped>
  .roots{
    font-size: 16rpx;
    width: 80rpx;
    height: 80rpx;
    background-color: transparent;
    border-radius: 100rpx;
    border:0.6px solid #cfcfd2 ;
    line-height: 30rpx;
    margin-left: 0;
    margin-top: 10rpx;
  }
.personContainer{
  display: block;
  padding: 0;
  margin: 0;
}
.backImg{
  z-index: -1;
  width: 100%;
  height: 450rpx;
  border-bottom-left-radius: 2rpx;
  border-bottom-right-radius: 2rpx;
}
  .backgroundContainer{
    margin-top: -420rpx;
  }
  .personPhoto{
    margin-left: 36%;
  }
  .personPhoto img{
    width: 200rpx;
    height: 200rpx;
    border-radius: 100rpx;
    opacity: 0.9;
    border: 1rpx solid #b7e8ee;
  }
  .personInfo{
    margin-left: 38%;
    margin-top: 10rpx;

  }
  .personInfo img{
    width: 30rpx;
    height: 30rpx;
    position: relative;
    top: -24px;
    left: 39%;
  }
  .personInfo p{
    font-size: 28rpx;
   color: #ffffff;
    font-weight: bold;
    width: 180rpx;
    overflow:hidden;
    text-overflow:ellipsis;
    white-space:nowrap;
    text-align: center;
  }
  .countContainer{
    padding-left: 130rpx;
    display: flex;
    margin-top: -34rpx;
  }
  .count{
    margin-right: 24%;
  }
  .count p{
    font-size: 12px;
    color: #ffffff;
    font-weight: bold;
    word-break:keep-all;
    white-space:nowrap;
    text-align: center;
    padding: 5rpx;
  }
.listContainer{
  padding-top: 50rpx;
  background-color: #eeeeee;
}
  .title{
    width: 100%;
    height: 60rpx;
    padding: 10rpx;
  }
  .title p{
    font-size: 24rpx;
    color: #7a7a7a;
    margin-top: 2%;
    margin-left: 1%;
  }
  .someList{
    background-color: #ffffff;
    display: block;
    padding: 0px;
  }
  .box{
    display: flex;
    border-bottom:0.6px solid #cfcfd2;
    width: 100%;
    height: 100rpx;
    padding: 5rpx;
  }
  .box img{
    width: 60rpx;
    height: 60rpx;
  }
  .box .iconOne{
    margin-left: 2%;
    margin-top: 3%;
  }
  .box p{
    margin-left: 40rpx;
    margin-top: 4%;
    font-size: 30rpx;
  }
  .box .iconTwo{
    margin-top: 2%;
    margin-left: 58%;
  }
.box .spe{
    margin-top: 2%;
    margin-left: 66%;
}
  .spe2{
    margin-top: 2%;
    margin-right: 3%;
  }
  #pp{
    width: 300rpx;
    position: relative;
    left: -250rpx;
  }


</style>

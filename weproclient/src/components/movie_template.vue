<template>
  <div class="container">
      <div  v-for="(item,index) in videoList" :key="index" class="videoContainer">
        <div class="video-container">
          <div  class="video" :data-id="item.id" v-on:tap="videoPlay">
            <video v-if="curr_id == item.id" id="myVideo"   :src="item.src" :class="[item.isLong? 'vio-long':'vio-short']"
                   :poster="item.poster"  objectFit="cover"  controls></video>
            <div v-else >
              <img :class="[item.isLong? 'model-img':'model-img-short']"  mode="aspectFill" :src="item.poster||'/static/images/tu1.png'">
              <div class="model-btn">
                <div class="play-icon"></div>
              </div>
            </div>
          </div>
          <div class="authorInfo">
            <p class="at">{{item.title}}</p>
            <div class="tou">
              <img :src=item.writterImg>
              <p>{{item.writter}}</p>
               <img src="/static/images/personIcon/zang.png" style="width: 30rpx;height: 30rpx;">
                <p>{{Math.random()*100}}k</p>
            </div>
          </div>
          <div v-if="item.isLong" class="collection">
            <img src="/static/images/personIcon/heart.png">
            <img src="/static/images/personIcon/heart.png">
            <img src="/static/images/personIcon/heart.png">
            <img src="/static/images/personIcon/heart.png">
            <img src="/static/images/personIcon/heart.png">
            <img src="/static/images/personIcon/heart.png">
            <img src="/static/images/personIcon/heart.png">
            <img src="/static/images/personIcon/heart.png">
          </div>
        </div>
      </div>

    <div class="hot-box-more">
      <div v-if="!loadmorehidden" class="tipMores">
        <p>加载更多...</p>
      </div>
      <div class="tipMores" v-else>
        <p> 没有更多了... </p>
      </div>
    </div>
  </div>
</template>

<script>
    export default {
        name: "movie_template",
      data(){
          return{
            curr_id: '',
            loadmorehidden:false,
            videoList:[],
            count:0,
            items: [
              {
                id: 1,
                src:"http://wxsnsdy.tc.qq.com/105/20210/snsdyvideodownload?filekey=30280201010421301f0201690402534804102ca905ce620b1241b726bc41dcff44e00204012882540400&bizid=1023&hy=SH&fileparam=302c020101042530230204136ffd93020457e3c4ff02024ef202031e8d7f02030f42400204045a320a0201000400",
                poster: '/static/images/tu1.png',
                isLong:true
              },
              {
                id: 2,
                src:"http://wxsnsdy.tc.qq.com/105/20210/snsdyvideodownload?filekey=30280201010421301f0201690402534804102ca905ce620b1241b726bc41dcff44e00204012882540400&bizid=1023&hy=SH&fileparam=302c020101042530230204136ffd93020457e3c4ff02024ef202031e8d7f02030f42400204045a320a0201000400",
                poster: '/static/images/tu2.png',
                isLong:false
              },
              {
                id: 3,
                src:"http://wxsnsdy.tc.qq.com/105/20210/snsdyvideodownload?filekey=30280201010421301f0201690402534804102ca905ce620b1241b726bc41dcff44e00204012882540400&bizid=1023&hy=SH&fileparam=302c020101042530230204136ffd93020457e3c4ff02024ef202031e8d7f02030f42400204045a320a0201000400",
                poster: '/static/images/tu1.png',
                isLong:false
              },
              {
                id: 4,
                src:"http://wxsnsdy.tc.qq.com/105/20210/snsdyvideodownload?filekey=30280201010421301f0201690402534804102ca905ce620b1241b726bc41dcff44e00204012882540400&bizid=1023&hy=SH&fileparam=302c020101042530230204136ffd93020457e3c4ff02024ef202031e8d7f02030f42400204045a320a0201000400",
                poster: '/static/images/tu2.png',
                isLong:true
              },  {
                id: 5,
                src:"http://wxsnsdy.tc.qq.com/105/20210/snsdyvideodownload?filekey=30280201010421301f0201690402534804102ca905ce620b1241b726bc41dcff44e00204012882540400&bizid=1023&hy=SH&fileparam=302c020101042530230204136ffd93020457e3c4ff02024ef202031e8d7f02030f42400204045a320a0201000400",
                poster: '/static/images/tu1.png',
                isLong:false
              },
              {
                id: 6,
                src:"http://wxsnsdy.tc.qq.com/105/20210/snsdyvideodownload?filekey=30280201010421301f0201690402534804102ca905ce620b1241b726bc41dcff44e00204012882540400&bizid=1023&hy=SH&fileparam=302c020101042530230204136ffd93020457e3c4ff02024ef202031e8d7f02030f42400204045a320a0201000400",
                poster: '/static/images/tu2.png',
                isLong:false
              },
              {
                id: 7,
                src:"http://wxsnsdy.tc.qq.com/105/20210/snsdyvideodownload?filekey=30280201010421301f0201690402534804102ca905ce620b1241b726bc41dcff44e00204012882540400&bizid=1023&hy=SH&fileparam=302c020101042530230204136ffd93020457e3c4ff02024ef202031e8d7f02030f42400204045a320a0201000400",
                poster: '/static/images/tu1.png',
                isLong:true
              },
              {
                id: 8,
                src:"http://wxsnsdy.tc.qq.com/105/20210/snsdyvideodownload?filekey=30280201010421301f0201690402534804102ca905ce620b1241b726bc41dcff44e00204012882540400&bizid=1023&hy=SH&fileparam=302c020101042530230204136ffd93020457e3c4ff02024ef202031e8d7f02030f42400204045a320a0201000400",
                poster: '/static/images/tu2.png',
                isLong:false
              },
              {
                id: 9,
                src:"http://wxsnsdy.tc.qq.com/105/20210/snsdyvideodownload?filekey=30280201010421301f0201690402534804102ca905ce620b1241b726bc41dcff44e00204012882540400&bizid=1023&hy=SH&fileparam=302c020101042530230204136ffd93020457e3c4ff02024ef202031e8d7f02030f42400204045a320a0201000400",
                poster: '/static/images/tu2.png',
                isLong:false
              }
            ]
          }
      },
      onReady: function () {  //创建视频上下文对象
        this.videoContext = wx.createVideoContext('myVideo')
      },
      methods:{
        videoPlay(e) {
          this.curr_id= e.currentTarget.dataset.id
          this.videoContext.play()
        }
      },
      onLoad(){
          this.videoList.splice(0)
          this.loadmorehidden=false
          this.count=0
        var that=this
        this.$fly.get(that.global.domain+'/videoList?count='+that.count)
          .then(function (response) {
            that.count=response.data.length+1
              for(let i=0;i<response.data.length;i++){
                var isLong=((i+1)%3==1? true:false)
                let obj={
                  id:response.data[i].id,
                  src:response.data[i].url,
                  poster:response.data[i].imgUrl,
                  title:response.data[i].title,
                  isLong:isLong,
                  writter:response.data[i].writter,
                  writterImg:response.data[i].writterImg
              }
              that.videoList.push(obj)
              }
          })
          .catch(function (error) {
            console.log(error);
          });
      },
      onReachBottom(){
         var that=this
        this.$fly.get(that.global.domain+'/videoList?count='+that.count)
          .then(function (response) {
            var state=''
            try {
              state=response.data.state
            }catch (e) {
              console.log(e)
            }
            if(state=="full"){
              that.loadmorehidden=true
            }else{
              that.count=that.count+6
              var len=response.data.length
              for (let i=0;i<len;i++){
                var isLong=((i+1)%3==1? true:false)
                let obj={
                  id:response.data[i].id,
                  src:response.data[i].url,
                  poster:response.data[i].imgUrl,
                  title:response.data[i].title,
                  isLong:isLong,
                  writter:response.data[i].writter,
                  writterImg:response.data[i].writterImg
                }
                that.videoList.push(obj)
              }
            }
          })
          .catch(function (error) {
            console.log(error);
          });
      }

    }
</script>

<style scoped>

.container{
  display: block;
}

  .video{
    border-top-left-radius: 30rpx;
    border-top-right-radius: 30rpx;
    margin-bottom: 40rpx;
    position: relative;
  }


  .vio-long{
    width: 350rpx;
    height: calc(9 * 380rpx / 6);
  }
  .model-img{
    border-top-left-radius: 30rpx;
    border-top-right-radius: 30rpx;
    width: 350rpx;
    height: calc(9 * 380rpx / 6);
  }
  .vio-short{
    width: 350rpx;
    height: calc(9 * 340rpx / 11);
  }
  .model-img-short{
    border-top-left-radius: 30rpx;
    border-top-right-radius: 30rpx;
    width: 350rpx;
    height: calc(9 * 340rpx / 11);
  }



  .model-btn{
    position:absolute;
    left:0rpx;
    top:0;
    bottom:0;
    right:0px;
    margin:auto;
    width:100rpx;
    height:100rpx;
    border-radius:50%;
    background-color: rgba(0,0,0,.3);
  }
  .play-icon{
    margin:28rpx 40rpx;
    border-top:26rpx solid transparent;
    border-left:36rpx solid #fff;
    border-bottom:22rpx solid transparent;
  }
  .videoContainer{
    border-top-left-radius: 30rpx;
    border-top-right-radius: 30rpx;
    float: left;

  }

  .video-container{
    border-top-left-radius: 30rpx;
    border-top-right-radius: 30rpx;
    margin:10rpx;
    border: 3rpx solid #eaeaea;
  }
.authorInfo{
  padding: 0rpx;
  margin-top: -40rpx;
}
  .authorInfo img{
    width: 40rpx;
    height: 40rpx;
    border-radius: 100rpx;

  }
  .at{
    padding-left: 18rpx;
    font-size: 28rpx;
    width: 310rpx;
  }
  .tou{
    display: flex;
    margin-top: 20rpx;
    padding: 5rpx;
    margin-bottom: 5rpx;
  }
  .tou img{
    margin-bottom: 5rpx;
  }
  .tou p{
    margin-left: 5rpx;
   margin-top: 10rpx;
    font-size: 22rpx;
    color: #8a8a8a;
    width: 130rpx;
    overflow:hidden;
    text-overflow:ellipsis;
    white-space:nowrap
  }
  .collection{
    display: flex;
    padding-left: 16rpx;
    margin-top: 20rpx;
    margin-bottom: 50rpx;
  }
  .collection img{
    width: 40rpx;
    height: 40rpx;
  }

.hot-box-more{
  padding: 10rpx;
  margin: 0;
}

.tipMores p{
  font-size: 22rpx;
  color: #87878a;
  text-align: center;
}

</style>

<template>
  <div class="historyContainer">
    <div v-if="historyList.length!=0">
      <div class="collectionContainer">
        <div class="articleContainer" v-for="(item,index) in historyList" :key="index" v-on:click="goDetail(index)">
          <div class="article_img">
            <img  :src="item.imgUrl">
          </div>
          <div class="article_info">
            <p class="article_title">{{item.title}}</p>
            <p class="article_titleTwo">{{item.titleTwo}}</p>
          </div>
          <p class="article_count">{{item.good}}k</p>
        </div>
      </div>
    </div>
    <div v-else class="not">
      <p>没有浏览任何东西...</p>
    </div>
    <div class="clear" @click="clear">
      <p>清除浏览记录</p>
    </div>
  </div>
</template>

<script>
    export default {
        name: "readingHistory",
      data(){
          return{
            historyList:[]
          }
      },
      methods:{
        goDetail(index){
          var that=this
          wx.navigateTo({
            url: '/pages/article_detail/main?op='+that.historyList[index].articleId
          })
        },
        clear(){
          this.historyList.splice(0)
          var that=this
          this.$fly.get('http://localhost:8080/clearHistory?userId='+that.global.openId)
            .then(function (response) {
            })
            .catch(function (error) {
              console.log(error);
            });
        }
      },
      onLoad(){
        this.historyList.splice(0)
        var that=this
        this.$fly.get('http://localhost:8080/historyList?userId='+that.global.openId)
          .then(function (response) {
            for (let i=0;i<response.data.length;i++){
              that.historyList.push(response.data[i])
            }
          })
          .catch(function (error) {
            console.log(error);
          });
      }
    }
</script>

<style scoped>
.historyContainer{
  margin-top: 10rpx;
  border-top: 2rpx solid #d0d0d0;
}
.not{
  padding: 30rpx;
  margin-top: 30rpx;
}
.not p{
  font-size: 33rpx;
  text-align: center;
  color: #BFBFBF;
}
.articleContainer{
  display: flex;
  padding: 10rpx;
  border-bottom: 1rpx solid #eee;
}
.article_img{
  width: 300rpx;
  height: 200rpx;
  margin-right: 10rpx;
}
.article_img img{
  width: 300rpx;
  height: 200rpx;
}
.article_info{
  width: 70%;
}
.article_title{
  width: 380rpx;
  font-size: 40rpx;
  color: #333;
  overflow:hidden;
  text-overflow:ellipsis;
  white-space:nowrap
}
.article_titleTwo{
  width: 300rpx;
  font-size: 26rpx;
  color: #999;
  margin-top: 30%;
  white-space: nowrap;
  overflow: hidden;
  text-overflow: ellipsis;
}
.article_count{
  font-size: 18rpx;
  color: #999;
  margin-top: 25%;
  padding: 0;
}
  .clear{
    padding: 5rpx;
  }
  .clear p{
    text-align: center;
    font-size: 28rpx;
    color: #D7D7D7;
  }
</style>

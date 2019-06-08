<template>
  <div class="collectionContainer">
    <div v-for="(item,index) in concernList" :key="index">
    <div :class="[index%2==0?'title2':'title']">
      <img src="/static/images/concert.png">
    </div>
    <div class="articleList">
      <div class="a_title">
        <h2>{{item.title}}</h2>
        <p>{{item.sendTime}}</p>
      </div>
      <div class="ping">
        <p>评论</p>
      </div>
      <div class="mainContainer">
        <div class="authorList" v-for="(com,index2) in item.comment" :key="index2">
          <div class="infoImg">
            <img :src="com.imgUrl">
          </div>
           <div class="info">
              <h4>@评论人:{{com.nickName}}</h4>
              <p>
              {{com.mes}}
              </p>
           </div>
        </div>
      </div>
    </div>
    </div>
  </div>
</template>

<script>
    export default {
        name: "concern",
      data(){
          return {
            concernList:[]
          }
      },
      onLoad(){
        this.concernList.splice(0)
        var that=this
        this.$fly.post('http://localhost:8080/concern',{
              userId:that.global.openId
        })
          .then(function (response) {
          for(let i=0;i<response.data.length;i++){
            that.concernList.push(response.data[i])
          }
           // console.log(that.concernList)
          })
          .catch(function (error) {
            console.log(error);
          });
      }
    }
</script>

<style scoped>
.collectionContainer{
  padding: 0;
  margin: 0;
  display: block;
}
  .title{
    width: 400rpx;
    height: 50rpx;
    background-color: #6062dc;
    margin-top: 5%;
    border-top-right-radius: 30rpx;
    border-bottom-right-radius: 30rpx;
  }
  .title img{
    margin-right: 16rpx;
    margin-top: 5rpx;
    width: 40rpx;
    height: 40rpx;
    float: right;
  }

  .title2{
    width: 410rpx;
    height: 50rpx;
    background-color: #6062dc;
    margin-top: 5%;
    border-top-left-radius: 30rpx;
    border-bottom-left-radius: 30rpx;
    margin-left: 47%;
  }
.title2 img{
  margin-left: 16rpx;
  margin-top: 5rpx;
  width: 40rpx;
  height: 40rpx;
  float: left;
}




  .articleList{
    margin-top: 30rpx;
    margin-left: 20rpx;
    margin-right: 20rpx;
    margin-bottom: 10rpx;
    padding: 30rpx;
    border-bottom:0.6px solid #fcfcff;
    box-shadow: #D7D7D7 1px 1px 1px 1px;;
  }
  .a_title{
    background-color: #f7f7f7;
    padding: 20rpx;
    border-left:10rpx solid #e1e1e1
  }
  .a_title h2{
    font-size: 32rpx;
  }
  .a_title p{
    font-size: 22rpx;
    color: #959595;
    font-weight: bold;
    margin-left: 80%;
  }
  .ping{
    margin-top: 20rpx;
    text-align: center;
    font-size: 30rpx;
    color: #959595;
  }
  .ping p{
    margin-left: 20%;
    padding-bottom: 20rpx;
    width: 400rpx;
    border-bottom:1px solid #a0a0a3;
  }
  .mainContainer{
    display: block;
    margin-top: 50rpx;
    padding: 2rpx;
    border-top:1rpx solid #d1d1d4;
  }
  .authorList{
    border-bottom:1rpx solid #d1d1d4;
    width: 100%;
    display: flex;
    padding: 10rpx;
  }
  .infoImg{
    width: 80rpx;
    height: 80rpx;
  }
  .infoImg img{
    width: 80rpx;
    height: 80rpx;
    border-radius: 100rpx;
    margin-top: 10rpx;
  }
.info{
  display: block;
  margin-left: 30rpx;
}
  .info h4{
    font-size: 30rpx;
    color: #b9b9bc;
    font-weight: bold;
    width: 500rpx;
    overflow:hidden;
    text-overflow:ellipsis;
    white-space:nowrap
  }
  .info p{
    width: 500rpx;
    font-size: 26rpx;
    margin-top: 10rpx;
  }

</style>

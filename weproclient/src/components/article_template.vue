<template>
  <div>
    <div class="articleContainer" v-for="(item,index) in articleList" :key="index" v-on:click="goDetail(index)">
      <div class="article_img">
        <img  :src="item.imgUrl">
      </div>
      <div class="article_info">
        <p class="article_title">{{item.title}}</p>
        <p class="article_titleTwo">{{item.titleTwo}}</p>
      </div>
      <p class="article_count">{{goodList[index]}}k</p>
    </div>

    <div class="hot-box-more">
      <div v-if="!loadmorehidden" class="tipMore">
        <p>加载更多...</p>
      </div>
      <div class="tipMore" v-else>
        <p> 没有更多了... </p>
      </div>
    </div>

  </div>
</template>

<script>
  import mockdata from '../utils/mock'
    export default {
        name: "article_template",
      data(){
        return{
          loadmorehidden:false,
          articleList:[],
          goodList:[],
          count:0
        }
      },
      computed:{
          items:function () {
            return mockdata.articleList
          }
      },
      methods:{
          goDetail(id){
            var that=this
            wx.navigateTo({
              url: '/pages/article_detail/main?op='+that.articleList[id].articleId
          })
          }
      },
      mounted(){
        var that=this
        this.$fly.get(that.global.domain+'/articleList?count='+that.count)
          .then(function (response) {
            that.count=that.count+4
            var len=response.data.length-1
            for (let i=0;i<len;i++)
              that.articleList.push(response.data[i])
            var temp=response.data[len]
            for(let i=0;i<temp.length;i++)
              that.goodList.push(temp[i])
          })
          .catch(function (error) {
            console.log(error);
          });
      },
      onReachBottom(){
         // console.log("上拉啊----")
        var that=this
        this.$fly.get(that.global.domain+'/articleList?count='+that.count)
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
              that.count=that.count+4
              var len=response.data.length-1
              for (let i=0;i<len;i++)
                that.articleList.push(response.data[i])
              var temp=response.data[len]
              for(let i=0;i<temp.length;i++)
                that.goodList.push(temp[i])
            }
          })
          .catch(function (error) {
            console.log(error);
          });
      },
      onPullDownRefresh(){
          this.goodList.splice(0)
        this.articleList.splice(0)
        this.count=0
        var that=this
        this.$fly.get(that.global.domain+'/articleList?count='+that.count)
          .then(function (response) {
            that.count=that.count+4
            var len=response.data.length-1
            for (let i=0;i<len;i++)
              that.articleList.push(response.data[i])
            var temp=response.data[len]
            for(let i=0;i<temp.length;i++)
              that.goodList.push(temp[i])
          })
          .catch(function (error) {
            console.log(error);
          });

      }

    }
</script>

<style scoped>
  .hot-box-more{
    padding: 10rpx;
    margin: 0;
  }
  .tipMore{
    background-color: #ededed;
    border-radius: 30rpx;
  }
  .tipMore p{
    font-size: 22rpx;
    color: #87878a;
    text-align: center;
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
</style>

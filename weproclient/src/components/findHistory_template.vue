<template>
  <div>
    <div class="historyContainer">
      <div class="history_opr">
        <span class="one">搜索历史</span>
        <span class="two">清空历史</span>
      </div>
      <div class="history_content">
        <div class="history_list" v-for="(item,index) in historyList" :key="index">
          <p>{{item}}</p>
        </div>
      </div>
    </div>

    <div class="hotWordContainer">
      <div class="hotWord">
        <p>热搜词</p>
      </div>
      <div class="history_content">
        <div class="history_list" v-for="(item,index) in hotList" :key="index">
          <p>{{item}}</p>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
    export default {
        name: "findHistory_template",
      data(){
          return{
            historyList:[],
            hotList:[]
          }
      },
        onLoad(){
          this.historyList.splice(0)
          this.hotList.splice(0)
          var that=this
          this.$fly.post('http://localhost:8080/searchHistoryAndHot',{
            userId:that.global.openId
          })
            .then(function (response) {
              for(let i=0;i<response.data.history.length;i++)
                that.historyList.push(response.data.history[i])
              for(let j=0;j<response.data.hot.length;j++)
                that.hotList.push(response.data.hot[j])
            })
            .catch(function (error) {
              console.log(error);
            });
        }

    }
</script>

<style scoped>
.historyContainer{
  padding: 10rpx;
}
  .history_opr .one{
    margin-left: 6%;
    font-size: 30rpx;
  }
  .history_opr .two{
    margin-left: 60%;
    font-size: 20rpx;
    color: #c6c6c6;
  }
  .history_content{
    padding: 10rpx;
    width: 100%;
    height: auto;
    overflow: auto;
  }
  .history_list{
    margin-left: 26rpx;
    margin-right: 26rpx;
    margin-top: 10rpx;
    padding: 24rpx;
    width: auto;
    height: auto;
    background-color: #ebebeb;
    border-radius: 50rpx;
    position:relative;
    vertical-align: top;
    display:inline-block;
  }
  .history_list p{
    font-size: 24rpx;
    color: #6d6d6d;
    word-break:keep-all;
    white-space:nowrap;
    overflow:hidden;
    text-overflow:ellipsis;
    text-align: center;
  }

  .hotWordContainer{
    padding: 10rpx;
    margin-top: 5%;
  }
  .hotWord{
    margin-left: 6%;
    font-size: 30rpx;
  }


</style>

<template>
  <div>
    <div class="headerContainer">
      <div class="searchContainer">
        <div class="searchIcon">
          <img src="/static/images/search.png">
        </div>
        <input type="text" placeholder="请输入关键字" v-model="searchMes">
      </div>
      <div class="cancle" v-on:click="backIndex">
        <p>取消</p>
      </div>
    </div>

    <find-history-template v-if="showFindHistory"></find-history-template>
    <div v-else>
      <div class="listContainer">
        <div class="listShow" v-for="(item,index) in articleList" :key="index" @click="goDetail(index)">
          <div class="listImg">
            <img :src="item.imgUrl">
          </div>
          <div class="article_info">
            <p class="one">{{item.title}}</p>
            <p class="two">{{item.titleTwo}}</p>
          </div>
          <div class="article_count">
            <p>...k</p>
          </div>
        </div>
      </div>
    </div>

  </div>
</template>

<script>
  import findHistoryTemplate from './findHistory_template'
    export default {
        name: "find_template",
      data(){
          return{
            searchMes:'',
            showFindHistory:true,
            articleList:[]
          }
      },
      components:{
          findHistoryTemplate
      },
      methods:{
        backIndex(){
          this.$emit("showNavEvent","显示标题栏nav")
        },
        goDetail(index){
          var that=this
          wx.navigateTo({
            url: '/pages/article_detail/main?op='+that.articleList[index].articleId
          })
        }
      },
      watch:{
          searchMes(newVar,oldVar){
            //console.log(newVar)
            var that=this
            if (newVar.length==0)
              this.showFindHistory=true
            else{
              this.showFindHistory=false
              //搜索
              this.$fly.post('http://localhost:8080/search',{
                key:newVar
              })
                .then(function (response) {
                  //console.log(response.data)
                  for(let i=0;i<response.data.length;i++){
                    that.articleList.push(response.data[i])
                  }
                })
                .catch(function (error) {
                  console.log(error);
                });
            }
          }
      }
    }
</script>

<style scoped>
.headerContainer{
  display: flex;
  margin-bottom: 30rpx;
}
.searchContainer{
  background-color: #f7f7f7;
  width: 80%;
  height: 80rpx;
  margin-left: 2%;
  margin-top: 8rpx;
  border-radius: 20rpx;
}
  .searchIcon{
    width: 50rpx;
    height: 50rpx;
    margin-top: 2%;
    margin-left: 4%;
    padding: 0;
    padding-right: 10px;
    border-right: 2px solid #BFBFBF;
  }
  .searchIcon img{
    width: 60rpx;
    height: 60rpx;
  }
  .searchContainer input{
    position:relative;
    left:20%;
    top:-56%;
    font-size: 28rpx;
  }
  .cancle{
    width: 120rpx;
    margin-top: 8rpx;
    padding: 0;
  }
  .cancle p{
      margin-top: 17%;
      margin-left: 20%;
      font-size: 30rpx;
      color: #bdbdbd;
  }


.listContainer{
  display: block;
  margin: 20rpx;
  padding: 6rpx;
}
.listShow{
  margin-top: 6rpx;
  padding: 0;
  display: flex;
  width: 100%;
  height: 200rpx;
  border: 2rpx solid #eee;
  box-shadow: #EEEEEE 1px 1px 1px 1px;
}
.listImg{
  width: 220rpx;
  height: 180rpx;
  padding: 6rpx;
}
.listImg img{
  width: 220rpx;
  height: 180rpx;

}
.article_info{
  padding-left: 6%;
  width: 50%;
}
.article_info .one{
  margin-top: 10rpx;
  font-size: 40rpx;
  font-weight: bold;
  color: #1f1f1f;
  overflow:hidden;
  text-overflow:ellipsis;
  white-space:nowrap
}
.article_info .two{
  margin-top: 50rpx;
  font-size: 26rpx;
  color: #BFBFBF;
  overflow:hidden;
  text-overflow:ellipsis;
  white-space:nowrap
}
.article_count p{
  margin-top: 120rpx;
  margin-left: 5%;
  font-size: 26rpx;
  color: #BFBFBF;
}

</style>

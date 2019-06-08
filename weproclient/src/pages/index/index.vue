<template>
  <div>
    <div class="headerContainer" v-if="shownav">
      <div class="search" v-on:click="findClick">
        <img src="/static/images/search.png">
      </div>
      <div :class="[classMovie? 'isClick':'notClick']" v-on:click="movieClick">
        <span>视频</span>
      </div>
      <div :class="[classArticle? 'isClick':'notClick']" v-on:click="articleClick">
        <span>文章</span>
      </div>
      <div :class="[classFind? 'isClick':'notClick']" v-on:click="findClick">
        <span>发现</span>
      </div>
      <div class="person" v-on:click="goToPersonPage">
        <img src="/static/images/person.png">
      </div>
    </div>

    <div v-if="showSwiper">
      <swiper-template></swiper-template>
    </div>

    <div>
      <article-template v-if="classArticle"></article-template>
      <movie-template v-if="classMovie"></movie-template>
      <find-template v-if="classFind" v-on:showNavEvent="showNavFun"></find-template>
    </div>

  </div>
</template>

<script>
import swiperTemplate from '../../components/swiper_template'
import articleTemplate from '../../components/article_template'
import movieTemplate from '../../components/movie_template'
import findTemplate from '../../components/find_template'

export default {
  data(){
    return{
      classMovie:false,
      classArticle:true,
      classFind:false,
      showSwiper:true,
      shownav:true
    }
  },
  components:{
    swiperTemplate,
    articleTemplate,
    movieTemplate,
    findTemplate
  },
  methods:{
    movieClick(){
      this.classMovie=true
      this.classArticle=false
      this.classFind=false
      this.showSwiper=false
    },
    articleClick(){
      this.classArticle=true
      this.classMovie=false
      this.classFind=false
      this.showSwiper=true
    },
    findClick(){
      this.classFind=true
      this.classMovie=false
      this.classArticle=false
      this.showSwiper=false
      this.shownav=false
    },
    showNavFun(data){
      //console.log(data)
      this.shownav=true
      this.classArticle=true
      this.classMovie=false
      this.classFind=false
      this.showSwiper=true
    },
    goToPersonPage(){
      wx.navigateTo({
        url: '/pages/person/main'
      })
    }
  }
}
</script>

<style scoped>
  .notClick{
    white-space:nowrap;
    padding-top:5px;
    margin-left: 12%;
    font-size: 34rpx;
  }
  .isClick{
    white-space:nowrap;
    padding-top:3px;
    margin-left: 12%;
    font-size: 34rpx;
    border-bottom: 2px solid #7a7a7a;
  }
  .headerContainer{
    display: flex;
    width: 100%;
    height: 80rpx;
    border-bottom:0.6px solid #fcfcff;
    box-shadow: #D7D7D7 1px 1px 1px 1px;
    margin-bottom: 2px;
    padding: 0;
  }
  .search{
    margin-left: 5%;
  }
  .person{
    margin-left: 10%;
    padding-top: 3px;
  }
  .search img{
    width: 60rpx;
    height: 60rpx;
  }
  .person img{
    width: 60rpx;
    height: 60rpx;
  }
</style>

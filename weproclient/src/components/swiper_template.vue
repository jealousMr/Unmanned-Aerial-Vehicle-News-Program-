<template>
  <div :class="classIndex">
    <swiper autoplay="true" class="swiper-block" previous-margin="90rpx" next-margin="90rpx" current="0" v-on:change="swiperChange">
      <swiper-item class="swiper-item" @:click="fun(1)">
        <image mode="aspectFill" :src="imageURL[0]"  @click="fun(0)"  :class="[currentIndex=='0'? 'slide-image active':'slide-image']" />
      </swiper-item>
      <swiper-item class="swiper-item">
        <image mode="aspectFill" :src="imageURL[1]"  @click="fun(1)" :class="[currentIndex=='1'? 'slide-image active':'slide-image']"/>
      </swiper-item>
      <swiper-item class="swiper-item">
        <image mode="aspectFill" :src="imageURL[2]"  @click="fun(2)" :class="[currentIndex=='2'? 'slide-image active':'slide-image']" />
      </swiper-item>
      <swiper-item class="swiper-item">
        <image mode="aspectFill" :src="imageURL[3]"  @click="fun(3)" :class="[currentIndex=='3'? 'slide-image active':'slide-image']" />
      </swiper-item>
    </swiper>
  </div>
</template>

<script>
    export default {
      name: "swiper_template",
      data(){
        return{
          currentIndex:0,
          article:[],
          imageURL:[
            '/static/images/backgroud.png',
            '/static/images/backgroud.png',
            '/static/images/backgroud.png',
            '/static/images/backgroud.png'
          ],
          classIndex:'swiperContainer'
        }
      },
      methods:{
        fun(item){
          var obj=this.article[item]
          var url='/pages/article_detail/main?title='+obj.title+'&titleTwo='+obj.titleTwo+'&imgUrl='+obj.imgUrl+'&userId='+obj.userId+'&content='+obj.content+
              '&sendTime='+obj.sendTime+'&articleId='+obj.article+'&op=swiper'+'&articleId='+obj.articleId
          wx.navigateTo({
            url:url
          })
        },
        swiperChange(e){
          //console.log(e.mp.detail.current)
          this.currentIndex=e.mp.detail.current
          switch (this.currentIndex) {
            case 0:{
              this.classIndex= "swiperContainer"
              break
            }
            case 1:{
              this.classIndex= "swiperContainer2"
              break
            }
            case 2:{
              this.classIndex= "swiperContainer3"
              break
            }
            case 3:{
              this.classIndex= "swiperContainer4"
              break
            }
          }
        }
      },
      mounted(){
        var that=this
        this.$fly.post('http://localhost:8080/fourSwiperImg',{
          mes:"swiper"
        })
          .then(function (response) {
            for (let i=0;i<response.data.length;i++){
              that.article.push(response.data[i])
            }
            that.imageURL.splice(0)
            for(let j=0;j<that.article.length;j++)
              that.imageURL.push(that.article[j].imgUrl)
           // console.log(that.article)
          })
          .catch(function (error) {
            console.log(error);
          });
      },
      onPullDownRefresh(){
        var that=this
        this.$fly.post('http://localhost:8080/fourSwiperImg',{
          mes:"swiper"
        })
          .then(function (response) {
            that.article.splice(0)
            for (let i=0;i<response.data.length;i++){
              that.article.push(response.data[i])
            }
            that.imageURL.splice(0)
            for(let j=0;j<that.article.length;j++)
              that.imageURL.push(that.article[j].imgUrl)
             //console.log(that.article)
          })
          .catch(function (error) {
            console.log(error);
          });
      }
    }
</script>

<style scoped>
  .swiper-block{
    height: 480rpx;
    width: 100%;
  }
  .swiper-item{
    display: flex;
    flex-direction: column;
    justify-content: center;
    align-items: flex-start;
    overflow:unset;
  }

  .slide-image{
    height:320rpx;
    width: 520rpx;
    border-radius: 9rpx;
    box-shadow: 0px 0px 30rpx rgba(0, 0,0,.2);
    margin: 0rpx 30rpx;
    z-index: 1;
  }
  .active{
    transform: scale(1.14);
    transition:all .2s ease-in 0s;
    z-index: 20;
  }.swiper-block{
     height: 480rpx;
     width: 100%;
   }
  .swiper-item{
    display: flex;
    flex-direction: column;
    justify-content: center;
    align-items: flex-start;
    overflow:unset;
  }
  .slide-image{
    height:320rpx;
    width: 520rpx;
    border-radius: 9rpx;
    box-shadow: 0px 0px 30rpx rgba(0, 0,0,.2);
    margin: 0rpx 30rpx;
    z-index: 1;
  }
  .active{
    transform: scale(1.14);
    transition:all .2s ease-in 0s;
    z-index: 20;
  }
  .swiperContainer{
    margin:0;
    padding: 0px;
    border-bottom:0.6px solid #fcfcff;
    box-shadow: #f5f5f8 1px 1px 1px 1px;
    background: linear-gradient(to top right, #c7cbff 0%, #f6f7ff 25%, #f6f7ff 100%);
  }
  .swiperContainer2{
    margin:0;
    padding: 0px;
    border-bottom:0.6px solid #fcfcff;
    box-shadow: #f5f5f8 1px 1px 1px 1px;
    background: linear-gradient(to top right, #ffdcdb 0%, #f6f7ff 25%, #f6f7ff 100%);
  }
  .swiperContainer3{
    margin:0;
    padding: 0px;
    border-bottom:0.6px solid #fcfcff;
    box-shadow: #f5f5f8 1px 1px 1px 1px;
    background: linear-gradient(to top right, #ceffec 0%, #f6f7ff 25%, #f6f7ff 100%);
  }
  .swiperContainer4{
    margin:0;
    padding: 0px;
    border-bottom:0.6px solid #fcfcff;
    box-shadow: #f5f5f8 1px 1px 1px 1px;
    background: linear-gradient(to top right, #ffcff9 0%, #f6f7ff 25%, #f6f7ff 100%);
  }
</style>

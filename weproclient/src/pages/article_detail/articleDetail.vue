<template>
  <div class="detailContainer">
    <h1 class="mainTitle">{{articleInfo.title}}</h1>
    <div class="sendTime">
      <img :src="writter_img ||'/static/images/tu1.png'">
      <p>{{writter_name}}</p>
      <span>{{articleInfo.sendTime}}</span>
    </div>
    <div class="subTitle">
      <p>{{articleInfo.titleTwo}}</p>
    </div>
    <div class="detailImg">
      <img :src="articleInfo.imgUrl">
    </div>
    <div class="context">
      <p>
        {{articleInfo.content}}
      </p>
    </div>

    <div class="authorMes">
      <p>{{writter_name}}</p>
      <div class="ope" @click="addCollection">
        <div class="tip">
            <img :src="[isCollection? '/static/images/coll.png':'/static/images/personIcon/shou.png']">
        </div>
        <div class="tip" @click="addGood">
          <img :src="[isGood?'/static/images/personIcon/afterGood.png':'/static/images/personIcon/zang.png']">
        </div>
        <div class="tip">
          <img src="/static/images/personIcon/heart.png">
        </div>
      </div>
    </div>

    <div class="kong"></div>

    <div class="comment">
      <div class="sub">
        <input type="text" placeholder="评论" v-model="commentMes">
        <button @click="subComment(commentMes)">确认</button>
      </div>

      <div v-for="(item,index) in commentList" :key="index" :class="[index%2==0 ? 'polaroid rotate_left':'polaroid rotate_right']">
        <img :src="[item.imgUrl||'http://www.w3school.com.cn//i/ballade_dream.jpg']"  width="284" height="213" />
        <h4 class="caption">@{{item.nickName}}</h4>
        <p>{{item.mes}}</p>
      </div>
    </div>
  </div>
</template>

<script>
  import mockdata from '../../utils/mock'
    export default {
        name: "articleDetail",
      data(){
        return{
          commentMes:'',
          articleInfo:{},
          writter_name:'',
          writter_img:'',
          commentList:[],
          isGood:false,
          isCollection:false
        }
      },
      methods:{
          subComment(mes){
            var that=this
            if(this.global.openId!='null'){//用户已经认证过，可发表评论
              this.$fly.post('http://localhost:8080/sendComment',{
                mes:mes,
                articleId:that.articleInfo.articleId,
                commentPeopleId:that.global.openId
              })
                .then(function (response) {
                  let comObj={
                    mes:mes,
                    nickName:that.global.nickName,
                    imgUrl:that.global.imgUrl
                  }
                  that.commentList.push(comObj)
                })
                .catch(function (error) {
                  console.log(error);
                });
            }
          },
          addGood(){
           var that=this
            this.$fly.get("http://localhost:8080/addGood?articleId="+this.articleInfo.articleId)
              .then((d)=>{
                that.isGood=true
            }).catch(err=>{
              console.log(err.status,err.message)
            })
          },
          addCollection() {
            var that=this
            this.$fly.get("http://localhost:8080/addCollection?articleId="+this.articleInfo.articleId+"&userId="+that.global.openId)
              .then((d)=>{
                that.isCollection=true
                wx.showToast({
                  title: '收藏成功',
                  icon: 'success',
                  duration: 1000,
                  mask: true
                })
              }).catch(err=>{
              console.log(err.status,err.message)
            })
          }
      },
     computed:{
          items(){
            return mockdata.article
          }
     },
      onLoad(option) {
        this.isGood=false
        this.isCollection=false
        this.commentList.splice(0)
        //获取文章信息
        if(option.op=="swiper"){
          this.articleInfo=option
          var that=this
          this.$fly.get("http://localhost:8080/writterInfo?userId="+this.articleInfo.userId).then((d)=>{
            that.writter_name=d.data.nickName
            that.writter_img=d.data.img
          }).catch(err=>{
            console.log(err.status,err.message)
          })
          //添加浏览记录
          if(this.global.openId!="null"){
            //console.log(option.articleId)
            this.$fly.get("http://localhost:8080/addHistory?userId="+this.global.openId+"&articleId="+option.articleId)
              .then((d)=>{
              }).catch(err=>{
              console.log(err.status,err.message)
            })
          }
        }
        else{
          var articleId=option.op
          var that=this
          this.$fly.post('http://localhost:8080/articleDetail',{
            articleId:articleId
          })
            .then(function (response) {
            that.articleInfo={
              articleId:option.op,
              title:response.data.title,
              titleTwo:response.data.titleTwo,
              imgUrl:response.data.imgUrl,
              sendTime:response.data.time,
              content:response.data.content
            }
            that.writter_name=response.data.writter
              that.writter_img=response.data.imgUrl
            })
            .catch(function (error) {
              console.log(error);
            });
          if(this.global.openId!="null"){
            //console.log(option.op)
            this.$fly.get("http://localhost:8080/addHistory?userId="+this.global.openId+"&articleId="+option.op)
              .then((d)=>{
                //console.log("添加历史成功")
              }).catch(err=>{
              console.log(err.status,err.message)
            })
          }

        }
        //获取评论信息
        var aId=''
        var that=this
        if(option.op=="swiper")
          aId=option.articleId
        else
          aId=option.op
        this.$fly.post('http://localhost:8080/commentInfo',{
          articleId:aId
        })
          .then(function (response) {
           if(response.data!='not')
           for(let i=0;i<response.data.length;i++){
             that.commentList.push(response.data[i])
           }
          })
          .catch(function (error) {
            console.log(error);
          });
      }
    }
</script>

<style scoped>

  .polaroid {
    width:300rpx;
    height: auto;
    padding:10px;
    margin:50rpx;
    border:1px solid #BFBFBF;
    background-color:#FFFFFF;
    box-shadow:10px 10px 10px #999999;/*图片阴影的设置*/
  }
  .polaroid img{
    width:290rpx;
    height: 280rpx;
  }
  .polaroid h4{
    font-size: 24rpx;
    color: #7a7a7a;
    font-weight: bold;
  }
  .polaroid p{
    font-size: 30rpx;
  }
  .rotate_left {
    float:left;
    transform:rotate(8deg);/*图片向右倾倒*/
  }
  .rotate_right {
    margin-left: 40%;
    float:left;
    transform:rotate(-8deg);/*图片向左倾倒*/
  }





.detailContainer{
  padding: 0;
  margin: 0;
  display: block;
}
  .mainTitle{
    margin: 10rpx;
    font-size: 40rpx;
    border-left:12rpx solid #92a1ff;
    padding: 5rpx;
    border-top-left-radius: 10rpx;
    border-bottom-left-radius: 10rpx;
  }
  .sendTime{
    display: flex;
    margin-top: 20rpx;
    margin-left: 10rpx;
    padding-left: 20rpx;
  }
  .sendTime img{
    width: 120rpx;
    height: 120rpx;
    border-radius: 100rpx;
  }
  .sendTime p{
    margin-left: 30rpx;
    margin-top: 6%;
    font-size: 28rpx;
  }
  .sendTime span{
    margin-left: 50%;
    margin-top: 16%;
    font-size: 20rpx;
    color: #a3a3a6;
  }
  .subTitle{
    margin-top: 30rpx;
    margin-left: 20rpx;
    margin-right: 20rpx;
    background-color: #f7f7f7;
    padding: 20rpx;
    border-left:10rpx solid #e1e1e1
  }
  .subTitle p{
    font-size: 30rpx;
    line-height: 60rpx;
  }
  .detailImg{
    margin-top: 30rpx;
    padding: 0;
    width: 100%;
    height: 460rpx;
  }
  .detailImg img{
    width: 100%;
    height: 460rpx;
  }
  .context{
    margin-top: 20rpx;
    padding: 12rpx;
  }
  .context p{
    text-indent:2em;
    line-height: 60rpx;
  }

.authorMes{
  margin-top: 50rpx;
  padding-right: 10rpx;
  display: flex;
}

  .authorMes p{
    font-size: 30rpx;
    margin-left: 10rpx;
    font-weight: 400;

  }
  .ope{
    margin-left: 50%;
    display: flex;
    width: 280rpx;
  }
  .tip{
    width: 40rpx;
    height: 40rpx;
    margin-left: 30rpx;
    margin-right: 20rpx;
    margin-top: 66rpx;
  }
  .tip img{
    width: 40rpx;
    height: 40rpx;
  }
.kong{
  margin-top: 10rpx;
  width: 100%;
  height: 30rpx;
  border-top: 1rpx solid #e1e1e1;
}
  .comment{
    padding: 20rpx;
  }
  .sub{
    display: flex;
  }
  .sub input{
    width: 500rpx;
    height: 50rpx;
    border: 5rpx solid #e1e1e1;
    border-radius: 20rpx;
    font-size: 22rpx;
  }
  .sub button{
    margin-top: 5rpx;
    margin-right: 30rpx;
    width: 130rpx;
    height: 50rpx;
    background-color: transparent;
    font-size: 22rpx;
  }


</style>

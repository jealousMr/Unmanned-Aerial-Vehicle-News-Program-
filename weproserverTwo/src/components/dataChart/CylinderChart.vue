<template>
    <div class="wrapper">
      <div class="row">
        <div class="col-lg-12">
          <div class="border-head">
            <h3>USER COLLECTION</h3>
          </div>
          <div class="custom-bar-chart">
            <ul class="y-axis">
              <li v-for="it in Y" :key="it">
                <span>{{it}}</span>
              </li>
              <li><span>0</span></li>
            </ul>
            <div class="bar">
              <div class="title">阅读量</div>
              <div class="value tooltips"  data-toggle="tooltip" data-placement="top" :style="'height:'+M[0]"></div>
            </div>
            <div class="bar">
              <div class="title">文章量</div>
              <div class="value tooltips"  data-toggle="tooltip" data-placement="top" :style="'height:'+M[1]"></div>
            </div>
            <div class="bar ">
              <div class="title">视频量</div>
              <div class="value tooltips"  data-toggle="tooltip" data-placement="top" :style="'height:'+M[2]"></div>
            </div>
            <div class="bar">
              <div class="title">点赞量</div>
              <div class="value tooltips" data-toggle="tooltip" data-placement="top" :style="'height:'+M[3]"></div>
            </div>
          </div>
          <div class="forms">
            <input class="form-control" type="text" placeholder="输入用户ID进行查询" v-model="userId">
            <button class="btn btn-primary" @click="findMore" >查询</button>
          </div>
        </div>
      </div>
    </div>
</template>

<script>
    export default {
        name: "CylinderChart",
      data(){
          return{
            Y:[10,8,6,4,2],
            M:['32%','43%','55%','87%'],
            userId:'',
          }
      },
      methods:{
          findMore(){
            if(this.userId.length>2){
              var that=this;
                this.$axios.get('/cylinder?userId='+that.userId)
                  .then((response)=>{
                    if(response.data.state=='1'){
                      console.log(response.data)
                      that.Y=response.data.Y
                      that.M=response.data.M
                    }else{
                      console.log("不存在此用户")
                      that.userId='不存在此用户'
                    }
                  })
                  .catch((error)=>{
                    console.log(error)
                  })
            }
          }
      }
    }
</script>

<style scoped>
  .forms{
    display: flex;
    margin-top: 40px;
  }
  .forms input{
    width: 60%;
    margin-left: 10%;
    margin-right: 10px;
    z-index: 2;
  }
  .forms button{
    z-index: 2;
  }
  .wrapper {
    display: inline-block;
    margin-top: 100px;
    padding-left: 15px;
    padding-right: 15px;
    padding-top: 0px;
    background-color: #f1f6fb;
    margin-right: 30px;
  }

  .border-head h3 {
    border-bottom: 1px solid #c9cdd7;
    margin-top: 20px;
    margin-bottom: 20px;
    padding-bottom: 5px;
    font-weight: normal;
    font-size: 18px;
    display: inline-block;
    width: 100%;
    font-weight: 300;
  }
  .custom-bar-chart {
    height: 290px;
    margin-top: 20px;
    margin-left: 10px;
    position: relative;
    border-bottom: 1px solid #c9cdd7;
  }
  .y-axis {
    color: #555555;
    position: absolute;
    text-align: left;
    width: 100%;
    font-size: 11px;
  }
  ul {
    display: block;
    list-style-type: disc;
    margin-block-start: 1em;
    margin-block-end: 1em;
    margin-inline-start: 0px;
    margin-inline-end: 0px;
    padding-inline-start: 40px;
  }
  .y-axis li {
    border-top: 1px dashed #dbdce0;
    display: block;
    height: 58px;
    width: 100%;
  }
  .y-axis li span {
    display: block;
    margin: -10px 0 0 -60px;
    padding: 0 10px;
    width: 40px;
  }
  .custom-bar-chart .bar {
    height: 100%;
    position: relative;
    width: 12%;
    margin: 0px 6%;
    float: left;
    text-align: center;
    z-index: 10;
  }
  .title {
    position: absolute;
    bottom: -32px;
    width: 100%;
    text-align: center;
    font-size: 10px;
    color: #727272;
  }
  .custom-bar-chart .bar .value {
    position: absolute;
    bottom: 0;
    background: #4ECDC4;
    color: #4ECDC4;
    width: 100%;
    -webkit-border-radius: 5px 5px 0 0;
    -moz-border-radius: 5px 5px 0 0;
    border-radius: 5px 5px 0 0;
    -webkit-transition: all .3s ease;
    -moz-transition: all .3s ease;
    -ms-transition: all .3s ease;
    -o-transition: all .3s ease;
    transition: all .3s ease;
  }
</style>

<template>
  <div class="container down">
    <div class="row">
      <div class="col-md-4">
        <div class="ip3 pn">
          <i>
            <img src="../../assets/vi.png">
          </i>
          <p>@TABLE-Video<br> 统计
          </p>
          <p><i class="fa fa-comment"></i> {{sum[0]||0}} | <i class="fa fa-heart"></i> 条记录</p>
        </div>
      </div>
      <div class="col-md-4">
        <div class="instagram-panel pn">
          <i>
            <img src="../../assets/captain.png">
          </i>
          <p>@TABLE-User<br> 统计
          </p>
          <p><i class="fa fa-comment"></i> {{sum[1]||0}} | <i class="fa fa-heart"></i> 条记录</p>
        </div>
      </div>
      <div class="col-md-4">
        <div class="ip2 pn">
          <i>
            <img src="../../assets/ne.png">
          </i>
          <p>@TABLE-News<br> 统计
          </p>
          <p><i class="fa fa-comment"></i> {{sum[2]||0}} | <i class="fa fa-heart"></i> 条记录</p>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
    export default {
        name: "TableChart",
        data(){
          return{
            sum:[]
          }
        },
      mounted(){
          var that=this
        this.$axios.get('tableSum')
          .then((response)=>{
            that.sum.splice(0)
            that.sum.push(new Number(response.data.v))
            that.sum.push(new Number(response.data.u))
            that.sum.push(new Number(response.data.a))
          })
          .catch((error)=>{
            console.log(error)
          })
      }
    }
</script>

<style scoped>
  .down{
    margin-top: 30px;
    margin-bottom: 20px;
  }
  .instagram-panel {
    background: url(../../assets/table1.png) no-repeat center top;
    text-align: center;
    background-position: center center;
  }
  .ip2{
    background: url(../../assets/table2.jpg) no-repeat center top;
    text-align: center;
    background-position: center center;
  }
  .ip2 p{
    margin: 5px;
    color: white;
  }
  .ip3 p{
    margin: 5px;
    color: white;
  }
  .ip2  img {
    margin-top: 12%;
    margin-bottom: 8%;
    width: 80px;
    height: 80px;
  }
  .ip3 img {
    margin-top: 12%;
    margin-bottom: 8%;
    width: 80px;
    height: 80px;
  }
  .ip3{
    background: url(../../assets/table3.png) no-repeat center top;
    text-align: center;
    background-position: center center;
  }
  .pn {
    height: 250px;
    box-shadow: 0 2px 1px rgba(0, 0, 0, 0.2);
  }
  .instagram-panel img {
    margin-top: 12%;
    margin-bottom: 8%;
    width: 80px;
    height: 80px;

  }

  .instagram-panel p {
    margin: 5px;
    color: white;
  }

</style>

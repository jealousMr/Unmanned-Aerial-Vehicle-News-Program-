<template>
  <div class="con">
    <header class="header black-bg">
      <a href="#" class="logo">
        <b>Index<span>-data</span></b>
      </a>
      <div class="btngs pull-right">
        <button class="btn btn-info" data-toggle="modal" data-target="#myModal">Sign in</button>
        <button class="btn btn-primary"data-toggle="modal" data-target="#upSign" style="background-color: transparent;">Sign up</button>
      </div>
    </header>
     <div class="container" style="margin-left: -1.2%;">
       <div class="row">
         <aside class="col-lg-3">
           <div id="sidebar" class="nav-collapse">
             <ul class="sidebar-menu">
               <p class="centered">
                 <a href="#">
                   <img src="../assets/logot.jpg" class="img-circle" width="100px" height="80px">
                 </a>
               </p>
               <h5 class="centered">Jeff luys</h5>
               <li class="gt">
                 <router-link to="/dataShow"><!--active-->
                   <img src="../assets/formIcon.png" style="max-width: 30px;max-height: 30px;margin-bottom: 10px;">
                   <span>Data-Show</span>
                 </router-link>
               </li>
               <li class="sub-menu dcjq-parent-li" @click="ulStyle1=!ulStyle1">
                 <a href="#" class="dcjq-parent">
                   <img src="../assets/datas.png" style="max-width: 30px;max-height: 30px;margin-bottom: 10px;">
                   <span>List-Show</span>
                 </a>
                 <transition name="slide-fade">
                   <ul v-if="ulStyle1" class="sub" >
                     <li>
                       <router-link to="/userTable">User-Table</router-link>
                     </li>
                     <li>
                       <router-link to="/newsTable">News-Table</router-link>
                     </li>
                     <li>
                       <router-link to="/videoTable">Video-Table</router-link>
                     </li>
                   </ul>
                 </transition>
               </li>
               <li class="sub-menu dcjq-parent-li" @click="ulStyle2=!ulStyle2">
                 <a href="#" class="dcjq-parent">
                   <img src="../assets/news.png" style="max-width: 30px;max-height: 30px;margin-bottom: 10px;">
                   <span>News-Release</span>
                 </a>
                 <transition name="slide-fade">
                   <ul v-if="ulStyle2" class="sub" style="display: block">
                     <li>
                       <router-link to="/newsRelease">News-Release</router-link>
                     </li>
                     <li>
                       <router-link to="/videoRelease">Video-Release</router-link>
                     </li>
                   </ul>
                 </transition>
               </li>
               <li>
                 <a class="">
                   <img src="../assets/mes.png" style="max-width: 30px;max-height: 30px;margin-bottom: 10px;">
                   <span>Mail </span>
                   <span class="label label-theme pull-right mail-info">3</span>
                 </a>
               </li>
             </ul>
           </div>
         </aside>
         <transition name="bounce">
             <router-view  class="col-lg-9"></router-view>
         </transition>
       </div>
     </div>

<!--    模态框-->
    <div class="modal fade" id="myModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
      <div class="modal-dialog">
        <div class="modal-content">
          <div class="modal-header">
            <h6 class="modal-title">Sign in</h6>
          </div>
          <div class="modal-body">
            <form>
                <input type="text" class="form-control" placeholder="userName" style="margin-bottom: 20px">
                <input type="password" class="form-control" placeholder="password">
            </form>
          </div>
          <div class="modal-footer">
            <button type="button" class="btn btn-info" data-dismiss="modal">关闭</button>
            <button type="button" class="btn btn-primary" data-dismiss="modal">登录</button>
          </div>
        </div>
      </div>
    </div>

    <div class="modal fade" id="upSign" tabindex="-1" role="dialog" aria-hidden="true">
      <div class="modal-dialog">
        <div class="modal-content">
          <div class="modal-header">
            <h6 class="modal-title">Sign up</h6>
          </div>
          <div class="modal-body">
            <form>
              <input type="text" class="form-control" placeholder="NickName" style="margin-bottom: 20px">
              <input type="text" class="form-control" placeholder="userName" style="margin-bottom: 20px">
              <input type="password" class="form-control" placeholder="password">
            </form>
          </div>
          <div class="modal-footer">
            <button type="button" class="btn btn-info" data-dismiss="modal">关闭</button>
            <button type="button" class="btn btn-primary">注册</button>
          </div>
        </div>
      </div>
    </div>

  </div>
</template>

<script>
  import DataChart from "./DataChart"
    export default {
        name: "NavBar",
      data(){
        return{
          ulStyle1:false,
          ulStyle2:false
        }
      },
      components:{
        DataChart
      },
      methods: {

      }
    }
</script>

<style scoped>
  .bounce-enter-active {
    animation: bounce-in .8s;
  }
  .bounce-leave-active {
    animation: bounce-in .8s reverse;
  }
  @keyframes bounce-in {
    0% {
      transform: scale(0);
    }
    50% {
      transform: scale(1.5);
    }
    100% {
      transform: scale(1);
    }
  }



  .slide-fade-enter-active {
    transition: all .3s ease;
  }
  .slide-fade-leave-active {
    transition: all .8s cubic-bezier(1.0, 0.5, 0.8, 1.0);
  }
  .slide-fade-enter, .slide-fade-leave-to
    /* .slide-fade-leave-active below version 2.1.8 */ {
    transform: translateX(10px);
    opacity: 0;
  }
  .con{
    width: 100%;
    height:100%;
    margin:0 auto;
  }
  .header{
    min-height: 60px;
    padding: 0 15px;
    position:fixed;top:0;left:0%;
    z-index:2;width: 100%
  }
  .black-bg {
    background: #22242a;
    border-bottom: 1px solid #393d46;
  }
  a.logo {
    font-size: 24px;
    color: #f2f2f2;
    float: left;
    margin-top: 15px;
    text-transform: uppercase;
  }
  .btngs{
    float: right;
    margin-top: 15px;
    margin-left: 92px;
  }
  .btngs button{
    margin-right: 20px;
  }
  a.logo b {
    font-weight: 900;
  }
  a.logo span {
    color: #4ECDC4;
  }
  .nav {
    padding-left: 0;
    margin-bottom: 0;
    list-style: none;
  }

  .aside{
    display: block;
  }
  #sidebar{
    width: 210px;
    height: 100%;
    position: fixed;
    background: #2f323a;
    overflow: hidden; outline: none;
    margin-top: 4%;
  }
  ul.sidebar-menu {
    margin-top: 75px;
    margin: -2px 0 0;
    padding: 0;
  }
  p {
    display: block;
    margin-block-start: 1em;
    margin-block-end: 1em;
    margin-inline-start: 0px;
    margin-inline-end: 0px;
  }
  .img-circle {
    border-radius: 50%;
    vertical-align: middle;
  }
  .centered {
    text-align: center;
  }
  #sidebar h5 {
    color: #f2f2f2;
    font-weight: 700;
  }
  #sidebar ul li {
    position: relative;
  }
  ul.sidebar-menu li {
    margin-bottom: 5px;
    margin-left: 10px;
    margin-right: 10px;
  }
  ul li {
    list-style: none;
  }
  .gt{
    margin-top: 25px;
  }
  a.active, ul.sidebar-menu li a:hover, ul.sidebar-menu li a:focus {
    background: #4ECDC4;
    color: #fff;
    display: block;
    -webkit-transition: all 0.3s ease;
    -moz-transition: all 0.3s ease;
    -o-transition: all 0.3s ease;
    -ms-transition: all 0.3s ease;
    transition: all 0.3s ease;
  }
  ul.sidebar-menu li a {
    color: #aeb2b7;
    text-decoration: none;
    display: block;
    padding: 15px 0 15px 10px;
    font-size: 12px;
    outline: none;
    -webkit-transition: all 0.3s ease;
    -moz-transition: all 0.3s ease;
    -o-transition: all 0.3s ease;
    -ms-transition: all 0.3s ease;
    transition: all 0.3s ease;
  }

  ul a span{
    color: #fff;
    display: inline-block;
    font-size: 22px;
  }
  ul.sidebar-menu li {
    line-height: 15px;
    margin-bottom: 5px;
    margin-left: 10px;
    margin-right: 10px;
  }
  ul.sidebar-menu li ul.sub {
    margin: -2px 0 0;
    padding: 0;
  }
  ul.sidebar-menu li ul.sub li {
    background: #2f323a;
    margin-bottom: 0;
    margin-left: 20%;
    margin-right: 0;
    padding: 5px;

  }
  .label-theme {
    background-color: #4ECDC4;
  }
  .mail-info, .mail-info:hover {
    margin: -3px 6px 0 0;
    font-size: 11px;
  }
  .label {
    display: inline;
    padding: .2em .6em .3em;
    font-size: 75%;
    font-weight: 700;
    line-height: 1;
    color: #fff;
    text-align: center;
    white-space: nowrap;
    vertical-align: baseline;
    border-radius: .25em;
  }
  .pull-right {
    float: right!important;
  }
</style>

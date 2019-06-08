<template>
    <div class="content-panel">
      <h4>
        Mongo-Table
      </h4>
      <div class="recent-activity mt">
        <div class="activity-icon bg-theme">
          <i class="fa fa-check"></i>
        </div>
        <div class="activity-panel">
          <h5>@Table-collection</h5>
          <p>共 &amp; 16条记录</p>
        </div>
        <div class="activity-icon bg-theme02">
          <i class="fa fa-trophy"></i>
        </div>
        <div class="activity-panel">
          <h5>@Table-history</h5>
          <p>共 &amp; 20条记录</p>
        </div>
      </div>
    </div>
</template>

<script>
    export default {
        name: "WordChart"
    }
</script>

<style scoped>
  .content-panel {
    background: #ffffff;
    box-shadow: 0px 3px 2px #aab2bd;
    padding-top: 15px;
    padding-bottom: 5px;
    padding-left: 10px;
    padding-right: 10px;
    margin-top: 100px;
  }
  .content-panel h4 {
     margin-left: 10px;
    text-align: center;
   }
  .mt {
    margin-top: 25px;
  }
  .activity-icon {
    border-radius: 50%;
    -webkit-border-radius: 50%;
    color: #FFFFFF;
    height: 30px;
    line-height: 30px;
    text-align: center;
    width: 30px;
    margin: 20px auto 20px;
    position: relative;
  }
  .bg-theme {
    background-color: #4ECDC4;
  }
  .fa {
    display: inline-block;
    font: normal normal normal 14px/1 FontAwesome;
    font-size: inherit;
    text-rendering: auto;
    -webkit-font-smoothing: antialiased;
    -moz-osx-font-smoothing: grayscale;
  }
  .activity-panel {
    padding: 15px 30px;
    background: #f2f2f2;
    border-radius: 5px;
    -webkit-border-radius: 5px;
    position: relative;
    text-align: center;
  }
  .activity-panel h5 {
    font-weight: 700;
  }
  .activity-panel p {
    display: block;
    margin-block-start: 1em;
    margin-block-end: 1em;
    margin-inline-start: 0px;
    margin-inline-end: 0px;
  }
  .bg-theme02 {
    background-color: #ac92ec;
  }
</style>

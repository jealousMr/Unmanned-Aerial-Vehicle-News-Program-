<template>
  <div id="app">
    <router-view v-if="show"/>
  </div>
</template>

<script>
export default {
  name: 'App',
  data(){
    return{
      show:true
    }
  }
}
</script>

<style>
#app {
margin: 0;
  padding: 0;
}
</style>

import Vue from 'vue'
import App from './App'
import Fly from 'flyio/dist/npm/wx'
import GLOBAL from './global'

Vue.config.productionTip = false
App.mpType = 'app'

let fly=new Fly
Vue.prototype.$fly=fly
Vue.prototype.global=GLOBAL


const app = new Vue(App)
app.$mount()

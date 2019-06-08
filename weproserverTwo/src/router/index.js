import Vue from 'vue'
import Router from 'vue-router'
import NavBar from "../components/NavBar";
import DataChart from "../components/DataChart"
import NewsTable from "../components/NewsTable"
import VideoTable from "../components/VideoTable"
import UserTable from "../components/UserTable"
import NewsRelease from "../components/NewsRelease"
import VideoRelease from "../components/VideoRelease"


Vue.use(Router)

export default new Router({
  routes: [
    {
      path:'/',
      redirect:'/dataShow',
      name:'NavBar',
      component:NavBar,
      children:[
        {
          path:"/dataShow",
          component: DataChart
        },
        {
          path:"/newsTable",
          component:NewsTable
        },
        {
          path:"/videoTable",
          component:VideoTable
        },
        {
          path:"/userTable",
          component:UserTable
        },
        {
          path:"/newsRelease",
          component:NewsRelease
        },
        {
          path:"videoRelease",
          component:VideoRelease
        }
      ]
    }
  ]
})

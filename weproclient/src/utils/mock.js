import Mock from 'mockjs'

const Random=Mock.Random

var articleList=[]
for(let i=0;i<20;i++){
  let obj={
    article_img:Random.image('200x100', '#fb0a2a'),
    article_title:Random.csentence(5,30),
    article_titleTwo:Random.csentence(3,10),
    article_count: Random.natural(1, 800)
  }
  articleList.push(obj)
}

var historyList=[]
for (let j=0;j<12;j++){
  let obj={
    title:Random.csentence(1,8)
  }
  historyList.push(obj)
}
var article={
  title:Random.csentence(5,30),
  titleTwo:Random.csentence(5,30),
  articleImg:Random.image('200x100', '#fbacc5'),
  content:Random.csentence(100,400)
}


export default {
  articleList,historyList,article
}

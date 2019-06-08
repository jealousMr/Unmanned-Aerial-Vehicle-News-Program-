package weprosever.weprosever;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.util.FileCopyUtils;
import weprosever.dao.ArticleDao;
import weprosever.dao.UserDao;
import weprosever.dao.VideoDao;
import weprosever.model.*;
import weprosever.service.ArticleService;
import weprosever.service.UserService;
import weprosever.service.VideoService;
import weprosever.util.IdTool;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class WeproseverApplicationTests {


    @Autowired
    private UserService userService;

    @Autowired
    private ArticleDao articleDao;

    @Value("${web.upload-path}")
    private String path;

    @Autowired
    private MongoTemplate mongoTemplate;
    @Autowired
    ArticleService articleService;
    @Autowired
    UserDao userDao;
    @Autowired
    VideoService videoService;

    @Test
    public void contextLoads() {
        System.out.println(userService.hasUser("oIUrN4g4V60_52SGxJI944YO_mLI2"));
    }

    @Test
    public void getID(){
        IdTool idTool=new IdTool();
        String ur=idTool.getArticleId("kaskdkas","yep");
        System.out.println(ur);
    }
    @Test
    public void Time(){
        int y,m,d;
        Calendar cal=Calendar.getInstance();
        y=cal.get(Calendar.YEAR);
        m=cal.get(Calendar.MONTH);
        d=cal.get(Calendar.DATE);
        String s=y+"/"+(m+1)+"/"+d;
        System.out.println(s);
    }

    @Test
    public void DD(){
        Article article=articleDao.getById(1);
        Article article2=articleDao.getById(2);
        List<Article> list=new ArrayList<>();
        list.add(article);
        list.add(article2);
        JSONArray array=JSONArray.parseArray(JSON.toJSONString(list));
       // String s=JSON.toJSONString(article);
        System.out.println(array.toJSONString());
    }

    @Test
    public void img() throws IOException {
        File f = new File("C:/my/ppt/tu/rr.jpg");
        FileCopyUtils.copy(f, new File(path+"/1.jpg"));
    }

    @Test
    public void listFilesTest() {
        File file = new File(path);
        for(File f : file.listFiles()) {
            System.out.println("fileName : "+f.getName());
        }
    }
    @Test
    public void Alist(){
        Collection collection=new Collection();
        List<String> aid=new ArrayList<>();
        aid.add("kakak");
        aid.add("yeeyye");
        collection.setArticleList(aid);
        collection.setUserId("alal");
        mongoTemplate.insert(collection,"collection");
    }
    @Test
    public void ttt(){
        Update update=new Update();
        update.push("articleList","哈啊哈哈啊哈");
        Query query=Query.query(Criteria.where("userId").is("alal"));
        mongoTemplate.updateFirst(query,update,Comment.class,"collection");
    }
    @Test
    public void Mongotest1(){//创建评论
        CommentInfo info1=new CommentInfo("%dhasjdw*&","评论信达刷卡");
        CommentInfo info2=new CommentInfo("&&%dsacdw*&","评dsaxasd33");
        List<CommentInfo> list=new ArrayList<>();
        list.add(info1);
        list.add(info2);
        //Comment comment=new Comment("123456",33,list);
        Comment comment1=new Comment();
        comment1.setArticleId("5107");
        comment1.setGood(11);

        mongoTemplate.insert(comment1,"article");
    }
    @Test
    public void Mongon2(){//插入评论
        CommentInfo info=new CommentInfo("kaskdkasl","yeyeye");
        Update update=new Update();
        update.push("listInfo",info);
        Query query=Query.query(Criteria.where("articleId").is("oIUrN4g4V60_52SGxJI944YO_mLI也如3240"));
        mongoTemplate.updateFirst(query,update,Comment.class,"article");
    }
    @Test
    public void Mongo3(){//修改点赞
        Query query1=Query.query(Criteria.where("articleId").is("123456"));
        Comment comment=mongoTemplate.findOne(query1, Comment.class,"article");
        int good=comment.getGood();
        Query query = Query.query(Criteria.where("articleId").is("123456"));
        Update update = Update.update("good", good+1);
        mongoTemplate.updateFirst(query,update,Comment.class,"article");
    }
    @Test
    public void clearHis(){
        Query query1=Query.query(Criteria.where("userId").is("oIUrN4g4V60_52SGxJI944YO_mLI"));
        List<String> list=new ArrayList<>();
        Update update=Update.update("articleList",list);
        mongoTemplate.updateFirst(query1,update,Collection.class,"collection");
    }





    @Test
    public void mo(){
        List<Article> list=articleDao.findAllByUserId("oIUrN4g4V60_52SGxJI944YO_mLI");
    }

    @Test
    public void testHistory(){
//        List<String> list=new ArrayList<>();
//        list.add("dasda");
//        list.add("yeyeye");
//        userService.createHistory("kaaksd",list);

//        History h=userService.findHistoryByUserId("kaaksd");
//        System.out.println(h==null);

        userService.insertHistory("kaaksd","------sdas");
    }
    @Test
    public void getTil(){
        String s="哈";
        System.out.println("-------------------------------------");
        List<Article> list=articleService.findAllByTitleLike("%"+s+"%");
        for (int i=0;i<list.size();i++){
            System.out.println(list.get(i).getTitle());
        }
        System.out.println(list.size());
    }
    @Test
    public void video(){
        Article a=new Article();
        a.setId(1);
        a.setArticleId("oIUrN4g4V60_52SGxJI944YO_mLI哈哈哈6552");
        a.setUserId("oIUrN4g4V60_52SGxJI944YO_mLI");
        a.setTitle("修改之后");
        a.setTitleTwo("kkkk修改之后");
        a.setContent("yeyeyeyyyy修改之后");
        a.setImgUrl("http://localhost:8080/1553500128538wxf17e681fda3c8379.o6zAJszkkSNPm6mQiU3xvo9fk06o.mhE2AdMbwVLGa2177eb0feb19c6e96338882bab09e9e.jpg");
        a.setSendTime("2019/3/25");
        articleService.updateArticle(a.getTitle(),a.getTitleTwo(),a.getContent(),a.getArticleId());
    }
    @Autowired
    private VideoDao videoDao;
    @Test
    public void ttte(){
       List<User> list=userDao.getOpList(0,2);
       System.out.println(list.size());
   }

    @Test
    public void cacheT(){

    }

}

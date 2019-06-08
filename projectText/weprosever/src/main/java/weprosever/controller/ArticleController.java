package weprosever.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import weprosever.dao.ArticleDao;
import weprosever.model.Article;
import weprosever.model.Comment;
import weprosever.model.CommentInfo;
import weprosever.model.User;
import weprosever.service.ArticleService;
import weprosever.service.UserService;
import weprosever.util.IdTool;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@RestController
public class ArticleController {
    @Autowired
    private ArticleService articleService;
    @Autowired
    private UserService userService;

    @ResponseBody
    @PostMapping(path = "/addArticle")
    public String addArticle(HttpServletRequest request, @RequestParam(value = "file",required = false)MultipartFile file)throws IOException {
        request.setCharacterEncoding("UTF-8");
        String userId=request.getParameter("userId");
        String title=request.getParameter("title");
        String titleTwo=request.getParameter("titleTwo");
        String content=request.getParameter("content");
        String imgUrl=null;
        if(!file.isEmpty()){
            String fileName = file.getOriginalFilename();
            String path = null;
            String type = null;
            type = fileName.indexOf(".") != -1 ? fileName.substring(fileName.lastIndexOf(".") + 1, fileName.length()) : null;
            if(type!=null){
                if ("GIF".equals(type.toUpperCase())||"PNG".equals(type.toUpperCase())||"JPG".equals(type.toUpperCase())) {
                    // 项目在容器中实际发布运行的根路径
//                    String realPath= request.getSession().getServletContext().getRealPath("/upload/");
//                    System.out.println(realPath);
//                    File dir=new File(realPath);
//                   if(!dir.exists()){
//                      // System.out.println("创建文件加："+realPath);
//                       dir.mkdir();
//                   }
//
//                    // 自定义的文件名称
                    String trueFileName = String.valueOf(System.currentTimeMillis()) + fileName;
//                    // 设置存放图片文件的路径
//                    path = realPath + trueFileName;
                    path="C:/my/projectText/images/"+trueFileName;
                    //System.out.println(path);
                    file.transferTo(new File(path));
                    imgUrl="http://localhost:8080/"+trueFileName;
                }else {
                    return "error";
                }
            }else {
                return "error";
            }
        }else {
            return "error";
        }
        String articleId=new IdTool().getArticleId(userId,title);
        String time=new IdTool().getNowTime();
        Article article=new Article(articleId,userId,title,titleTwo,imgUrl,content,time);
        articleService.addArticle(article);
        //添加到mongo
        Comment comment=new Comment();
        comment.setArticleId(articleId);
        comment.setGood(0);
        articleService.saveComment(comment);
        return imgUrl;
    }

    @ResponseBody
    @PostMapping(path = "/fourSwiperImg")
    public String RandFourSwiperImg(@RequestBody JSONObject jsonObject){
        List<Article> list =articleService.getRandArticle();
        JSONArray array=JSONArray.parseArray(JSON.toJSONString(list));
        return array.toJSONString();
    }

    @GetMapping(path = "/articleList")
    public String getArticleList(@RequestParam("count") int temp){
        JSONObject js=new JSONObject();
        int count=temp;
        int maxCount=articleService.maxCount();
        if(count>maxCount){
            js.put("state","full");
            return js.toJSONString();
        }
        int endCount=(count+4>maxCount? maxCount:count+4);
        List<Article> list=articleService.findArticleById(count,endCount);
        JSONArray array=JSONArray.parseArray(JSON.toJSONString(list));

        List<Integer> goods=new ArrayList<>();
        for (int i=0;i<list.size();i++){
            goods.add(articleService.getArticleGoodByarticleId(list.get(i).getArticleId()));
        }
        JSONArray good=JSONArray.parseArray(JSON.toJSONString(goods));
        array.add(good);
        return array.toJSONString();
    }

    @ResponseBody
    @PostMapping(path = "/articleDetail")
    public String getArticleDetail(@RequestBody JSONObject jsonObject){
       String articleId=jsonObject.get("articleId").toString();
       Article article=articleService.findArticleByArticleId(articleId);
       User writter=userService.findById(article.getUserId());
       JSONObject js=new JSONObject();
       js.put("writter",writter.getNickName());
       js.put("writterImg",writter.getImgUrl());
       js.put("title",article.getTitle());
       js.put("titleTwo",article.getTitleTwo());
       js.put("imgUrl",article.getImgUrl());
       js.put("content",article.getContent());
       js.put("time",article.getSendTime());
       return js.toJSONString();
    }

    @ResponseBody
    @PostMapping(path = "/sendComment")
    public String sendComment(@RequestBody JSONObject jsonObject){
        String commentPeopleId=jsonObject.get("commentPeopleId").toString();
        String mes=jsonObject.get("mes").toString();
        String articleId=jsonObject.getString("articleId");
        articleService.addCommentInfo(commentPeopleId,articleId,mes);
        return "success";
    }

    @ResponseBody
    @PostMapping(path = "/commentInfo")
    public String getCommentInfo(@RequestBody JSONObject jsonObject){
        class Info implements Serializable{
            private String nickName;
            private String imgUrl;
            private String mes;

            public Info(){}
            public Info(String nickName,String imgUrl,String mes){
                this.nickName=nickName;
                this.imgUrl=imgUrl;
                this.mes=mes;
            }

            public void setNickName(String nickName) {
                this.nickName = nickName;
            }

            public void setImgUrl(String imgUrl) {
                this.imgUrl = imgUrl;
            }

            public String getImgUrl() {
                return imgUrl;
            }

            public String getNickName() {
                return nickName;
            }

            public void setMes(String mes) {
                this.mes = mes;
            }

            public String getMes() {
                return mes;
            }
        }
        String articleId=jsonObject.get("articleId").toString();
        List<CommentInfo> listInfo=articleService.getCommentInfoByArticleId(articleId);
        if(listInfo!=null){
            List<Info> list=new ArrayList<>();
            for (int i=0;i<listInfo.size();i++){
                User user=userService.findById(listInfo.get(i).getUserId());
                String nick=user.getNickName();
                String userImg=user.getImgUrl();
                String mes=listInfo.get(i).getMes();
                Info obj=new Info(nick,userImg,mes);
                list.add(obj);
            }
            JSONArray array=JSONArray.parseArray(JSON.toJSONString(list));
            return  array.toJSONString();
        }
     return "not";
    }

    @GetMapping(path = "/addGood")
    public String addGood(@RequestParam("articleId") String articleId){
        articleService.addGood(articleId);
        return "success";
    }
    @ResponseBody
    @PostMapping(path = "/concern")
    public String getConcernList(@RequestBody JSONObject jsonObject){
        String userId=jsonObject.get("userId").toString();
        List<Article> articleList=articleService.findAllByUserId(userId);
        List<JSONObject> list=new ArrayList<>();
        if (articleList!=null){
            for (int i=0;i<articleList.size();i++){
                JSONObject js=new JSONObject();
                js.put("title",articleList.get(i).getTitle());
                js.put("sendTime",articleList.get(i).getSendTime());
                List<CommentInfo> infoList=articleService.getCommentInfoByArticleId(articleList.get(i).getArticleId());
                JSONArray array=commentInfo(infoList);
                if (array!=null)
                    js.put("comment",array);
                list.add(js);
            }
           // System.out.println(JSONArray.parseArray(JSON.toJSONString(list)).toJSONString());
            return JSONArray.parseArray(JSON.toJSONString(list)).toJSONString();
        }
        return null;
    }
    private JSONArray commentInfo(List<CommentInfo> listInfo){
        class Info implements Serializable{
            private String nickName;
            private String imgUrl;
            private String mes;

            public Info(){}
            public Info(String nickName,String imgUrl,String mes){
                this.nickName=nickName;
                this.imgUrl=imgUrl;
                this.mes=mes;
            }

            public void setNickName(String nickName) {
                this.nickName = nickName;
            }

            public void setImgUrl(String imgUrl) {
                this.imgUrl = imgUrl;
            }

            public String getImgUrl() {
                return imgUrl;
            }

            public String getNickName() {
                return nickName;
            }

            public void setMes(String mes) {
                this.mes = mes;
            }

            public String getMes() {
                return mes;
            }
        }
        if (listInfo!=null){
            List<Info> list=new ArrayList<>();
            for (int i=0;i<listInfo.size();i++){
                User user=userService.findById(listInfo.get(i).getUserId());
                String nick=user.getNickName();
                String userImg=user.getImgUrl();
                String mes=listInfo.get(i).getMes();
                Info obj=new Info(nick,userImg,mes);
                list.add(obj);
            }
            return JSONArray.parseArray(JSON.toJSONString(list));
        }
        return null;
    }

    @ResponseBody
    @PostMapping(path = "/search")
    public String getSearchList(@RequestBody JSONObject jsonObject){
        String keyTitlt=jsonObject.get("key").toString();
        List<Article> articleList=articleService.findAllByTitleLike("%"+keyTitlt+"%");
        JSONArray array=JSONArray.parseArray(JSON.toJSONString(articleList));
        return array.toJSONString();
    }
}


package weprosever.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import weprosever.model.Article;
import weprosever.model.Comment;
import weprosever.model.User;
import weprosever.model.Video;
import weprosever.service.ArticleService;
import weprosever.service.UserService;
import weprosever.service.VideoService;
import weprosever.util.IdTool;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

@RestController
public class AdminController {
    @Autowired
    private UserService userService;
    @Autowired
    private ArticleService articleService;
    @Autowired
    private VideoService videoService;
    @GetMapping(path = "/cylinder")
    public String dealCylinder(@RequestParam("userId") String userId){
        JSONObject js=new JSONObject();
        User user=userService.findById(userId);
        if (user==null){
            js.put("state","-1");
            return js.toJSONString();
        }
        js.put("state","1");
        int readCount=(int)(1+Math.random()*(10-1+1));
        int articleCount=user.getArticleSum();
        int videoCount=user.getVideoSum();
        int goodCount=(int)(1+Math.random()*(20));
        int max= IdTool.getMax(readCount,articleCount,videoCount,goodCount);
        List<String> m=new ArrayList<>();
        m.add(String.valueOf(readCount/max*100)+"%");
        m.add(String.valueOf(articleCount/max*100)+"%");
        m.add(String.valueOf(videoCount/max*100)+"%");
        m.add(String.valueOf(goodCount/max*100)+"%");
        JSONArray M=JSONArray.parseArray(JSON.toJSONString(m));
        js.put("M",M);
        List<Integer> y=new ArrayList<>();
        int temp=max/4;
        y.add(max);
        y.add(3*temp);
        y.add(2*temp);
        y.add(temp);

        JSONArray Y=JSONArray.parseArray(JSON.toJSONString(y));
        js.put("Y",Y);
        return js.toJSONString();
    }
    @GetMapping(path = "/tableSum")
    public String getSumOfTable(){
        JSONObject js=new JSONObject();
        int userCount=userService.findMax();
        int articleCount=articleService.maxCount();
        long videoCount=videoService.maxCount();
        js.put("u",String.valueOf(userCount));
        js.put("a",String.valueOf(articleCount));
        js.put("v",String.valueOf(videoCount));
        return js.toJSONString();
    }
    @GetMapping(path = "/newsList")
    public String getNewsList(@RequestParam("begin")int begin,@RequestParam("end")int end){
        JSONObject res=new JSONObject();
        int max=articleService.maxCount();
        if(begin>=max){
            res.put("state","-1");
            return res.toJSONString();
        }
        if(end>max)
            end=max;
        res.put("state","1");
        List<Article> articleList=articleService.getOpList(begin,end);
        JSONArray Y=JSONArray.parseArray(JSON.toJSONString(articleList));
        res.put("list",Y.toJSONString());
        return res.toJSONString();
    }
    @ResponseBody
    @PostMapping(path = "/updateNews")
    public String updateNew(@RequestBody JSONObject jsonObject){
        String articleId=jsonObject.getString("articleId");
        String title=jsonObject.getString("title");
        String titleTwo=jsonObject.getString("titleTwo");
        String content=jsonObject.getString("content");
        articleService.updateArticle(title,titleTwo,content,articleId);
        JSONObject j=new JSONObject();
        j.put("state","1");
        return j.toJSONString();
    }
    @GetMapping(path = "/deleteNews")
    public String deleteNewsById(@RequestParam("tableId") String tableId){
        articleService.deleteByTableId(Integer.parseInt(tableId));
        return "success";
    }
    @GetMapping(path = "/videoOpList")
    public String getVideoList(@RequestParam("begin")int begin,@RequestParam("end")int end){
        JSONObject res=new JSONObject();
        long max=videoService.maxCount();
        if(begin>=max){
            res.put("state","-1");
            return res.toJSONString();
        }
        if(end>max)
            end=(int)max;
        res.put("state","1");
        List<Video> list=videoService.getOpList(begin,end);
        JSONArray Y=JSONArray.parseArray(JSON.toJSONString(list));
        res.put("list",Y.toJSONString());
        return res.toJSONString();
    }
    @GetMapping(path = "/deleteVideo")
    public String deleteVideoById(@RequestParam("tableId") String tableId){
        videoService.deleteByTableId(Integer.parseInt(tableId));
        return "success";
    }
    @GetMapping(path = "/userLists")
    public String getUserList(@RequestParam("begin")int begin,@RequestParam("end")int end){
        JSONObject res=new JSONObject();
        int max=userService.findMax();
        if(begin>=max){
            res.put("state","-1");
            return res.toJSONString();
        }
        if(end>max)
            end=(int)max;
        res.put("state","1");
        List<User> list=userService.getOpList(begin,end);
        JSONArray Y=JSONArray.parseArray(JSON.toJSONString(list));
        res.put("list",Y.toJSONString());
        return res.toJSONString();
    }
    @GetMapping(path = "/updateVideoTitle")
    public String updateVideoTitle(@RequestParam("videoId")String videoId,@RequestParam("title")String title){
        JSONObject js=new JSONObject();
        videoService.updateTitleByVideoId(title,videoId);
        js.put("state","1");
        return js.toJSONString();
    }
    @PostMapping(path = "newsRelease")
    public String upNews(@RequestParam(value = "file",required = false) MultipartFile file, HttpServletRequest request) throws IOException {
        request.setCharacterEncoding("UTF-8");
        String title=request.getParameter("title");
        String titleTwo=request.getParameter("titleTwo");
        String content=request.getParameter("content");
        String userId="123456";
        String imgUrl=null;
        if(!file.isEmpty()){
            String fileName = file.getOriginalFilename();
            String path = null;
            String type = null;
            type = fileName.indexOf(".") != -1 ? fileName.substring(fileName.lastIndexOf(".") + 1, fileName.length()) : null;
            if(type!=null){
                if ("GIF".equals(type.toUpperCase())||"PNG".equals(type.toUpperCase())||"JPG".equals(type.toUpperCase())) {
                    String trueFileName = String.valueOf(System.currentTimeMillis()) + fileName;
                    path="C:/my/projectText/images/"+trueFileName;
                    file.transferTo(new File(path));
                    imgUrl="http://localhost:8080/"+trueFileName;
                }else {
                    return "error";
                }
            }else {
                return "error";
            }
        }
        String articleId=new IdTool().getArticleId(userId,title);
        Article article=new Article();
        article.setArticleId(articleId);
        article.setContent(content);
        article.setTitle(title);
        article.setTitleTwo(titleTwo);
        article.setImgUrl(imgUrl);
        article.setUserId(userId);
        article.setSendTime(new IdTool().getNowTime());
        articleService.addArticle(article);
        //添加到mongo
        Comment comment=new Comment();
        comment.setArticleId(articleId);
        comment.setGood(0);
        articleService.saveComment(comment);

        return "success";
    }
    @PostMapping(path = "videoRelease")
    public String upVideo(@RequestParam(value = "Imgfile",required = false) MultipartFile img,@RequestParam(value = "Videofile",required = false) MultipartFile video,HttpServletRequest request) throws IOException {
        request.setCharacterEncoding("UTF-8");
        String userId="123456";
        String title=request.getParameter("title");
        String videoUrl=null;
        if(!video.isEmpty()){
            String fileName = video.getOriginalFilename();
            String path = null;
            String type = null;
            type = fileName.indexOf(".") != -1 ? fileName.substring(fileName.lastIndexOf(".") + 1, fileName.length()) : null;
            if(type!=null){
                if ("MP4".equals(type.toUpperCase())||"AVI".equals(type.toUpperCase())||"RMVB".equals(type.toUpperCase())) {
                    String trueFileName = String.valueOf(System.currentTimeMillis()) + fileName;
                    path="C:/my/projectText/images/"+trueFileName;
                    video.transferTo(new File(path));
                    videoUrl="http://localhost:8080/"+trueFileName;
                }else {
                    return "error";
                }
            }
        }
        String videoId=new IdTool().getVideoId(userId,title);
        Video v=new Video();
        v.setUrl(videoUrl);
        v.setUserId(userId);
        v.setTitle(title);
        v.setVideoId(videoId);
        //设置封面
        String imgUrl=null;
        if(!img.isEmpty()){
            String fileName = img.getOriginalFilename();
            String path = null;
            String type = null;
            type = fileName.indexOf(".") != -1 ? fileName.substring(fileName.lastIndexOf(".") + 1, fileName.length()) : null;
            if(type!=null){
                if ("GIF".equals(type.toUpperCase())||"PNG".equals(type.toUpperCase())||"JPG".equals(type.toUpperCase())) {
                    String trueFileName = String.valueOf(System.currentTimeMillis()) + fileName;
                    path="C:/my/projectText/images/"+trueFileName;
                    img.transferTo(new File(path));
                    imgUrl="http://localhost:8080/"+trueFileName;
                }else {
                    return "error";
                }
            }
        }
        v.setImgUrl(imgUrl);
        videoService.addVideo(v);
        return "success";
    }
    @PostMapping(path = "videoReleaseByLink")
    public String upLinkVideo(@RequestParam(value = "Imgfile",required = false) MultipartFile img,HttpServletRequest request) throws IOException {
        request.setCharacterEncoding("UTF-8");
        String userId="123456";
        String title=request.getParameter("title");
        String videoUrl=request.getParameter("videoUrl");
        String videoId=new IdTool().getVideoId(userId,title);
        Video v=new Video();
        v.setUrl(videoUrl);
        v.setUserId(userId);
        v.setTitle(title);
        v.setVideoId(videoId);
        //设置封面
        String imgUrl=null;
        if(!img.isEmpty()){
            String fileName = img.getOriginalFilename();
            String path = null;
            String type = null;
            type = fileName.indexOf(".") != -1 ? fileName.substring(fileName.lastIndexOf(".") + 1, fileName.length()) : null;
            if(type!=null){
                if ("GIF".equals(type.toUpperCase())||"PNG".equals(type.toUpperCase())||"JPG".equals(type.toUpperCase())) {
                    String trueFileName = String.valueOf(System.currentTimeMillis()) + fileName;
                    path="C:/my/projectText/images/"+trueFileName;
                    img.transferTo(new File(path));
                    imgUrl="http://localhost:8080/"+trueFileName;
                }else {
                    return "error";
                }
            }
        }
        v.setImgUrl(imgUrl);
        videoService.addVideo(v);
        return "success";
    }
}

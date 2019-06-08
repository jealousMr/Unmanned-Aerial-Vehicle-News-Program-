package weprosever.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.web.bind.annotation.*;
import weprosever.model.*;
import weprosever.service.ArticleService;
import weprosever.service.UserService;
import weprosever.util.IdTool;
import java.util.ArrayList;
import java.util.List;



@RestController
public class UserController {

    @Autowired
    private UserService userService;
    @Autowired
    private ArticleService articleService;
    @ResponseBody
    @PostMapping(path = "/login")
    public String userLogin(@RequestBody JSONObject jsonObject){
        String code=jsonObject.get("code").toString();
        IdTool tool=new IdTool();
        String ID=tool.getUserId(code);
        JSONObject jid= (JSONObject) JSON.parse(ID);
        String nickName=jsonObject.get("nickName").toString();
        String imgUrl=jsonObject.get("img").toString();
        //登录初始化
        String userID=jid.get("openid").toString();
        if(!userService.hasUser(userID)){
            User user=new User();
            user.setUserId(userID);
            user.setNickName(nickName);
            user.setImgUrl(imgUrl);
            user.setArticleSum(0);
            user.setVideoSum(0);
            userService.addUser(user);
        }
        return jid.get("openid").toString();
    }


    @GetMapping(path = "/writterInfo")
    public String getWritter(@RequestParam("userId") String id){
        JSONObject jsonObject=new JSONObject();
        User user=userService.findById(id);
        jsonObject.put("nickName",user.getNickName());
        jsonObject.put("img",user.getImgUrl());
        return jsonObject.toJSONString();
    }


    @GetMapping(path = "/addCollection")
    public String addCollection(@RequestParam("userId") String userId,@RequestParam("articleId") String articleId){
        if(userId!=null && articleId!=null){
            Collection temp=userService.findCollectionByUserId(userId);
            if (temp!=null){
                userService.insertCollection(userId,articleId);
            }else{
                List<String> list=new ArrayList<>();
                list.add(articleId);
                userService.createCollection(userId,list);
            }
        }
        return "success";
    }

    @GetMapping(path = "/collectionArticle")
    public String getDetailA(@RequestParam("userId") String userId){
        Collection collection=userService.findCollectionByUserId(userId);
        List<String> st=collection.getArticleList();
        List<JSONObject> list=new ArrayList<>();
        if(st!=null){
            for (int i=0;i<st.size();i++){
                Article article=articleService.findArticleByArticleId(st.get(i));
                JSONObject js=new JSONObject();
                js.put("title",article.getTitle());
                js.put("titleTwo",article.getTitleTwo());
                js.put("imgUrl",article.getImgUrl());
                js.put("articleId",article.getArticleId());
                int good=articleService.getArticleGoodByarticleId(article.getArticleId());
                js.put("good",good);
                list.add(js);
            }
        }
        return JSONArray.parseArray(JSON.toJSONString(list)).toJSONString();
    }


    @GetMapping(path = "/addHistory")
    public String addHistory(@RequestParam("userId") String userId,@RequestParam("articleId") String articleId){
        if(userId!=null&&articleId!=null){
            History history=userService.findHistoryByUserId(userId);
            if (history!=null){
                if(!userService.hasHistory(userId,articleId))
                    userService.insertHistory(userId,articleId);
            }else {
                List<String> list=new ArrayList<>();
                list.add(articleId);
                userService.createHistory(userId,list);
            }
        }
        return "success";
    }

    @GetMapping(path = "/historyList")
    public String getHistoryList(@RequestParam("userId")String userId){
        History history=userService.findHistoryByUserId(userId);
        List<String> IdList=history.getArticleList();
        if(IdList!=null){
            List<JSONObject> list=new ArrayList<>();
            for (int i=0;i<IdList.size();i++){
                Article article=articleService.findArticleByArticleId(IdList.get(i));
                JSONObject js=new JSONObject();
                js.put("title",article.getTitle());
                js.put("titleTwo",article.getTitleTwo());
                js.put("imgUrl",article.getImgUrl());
                js.put("articleId",article.getArticleId());
                int good=articleService.getArticleGoodByarticleId(article.getArticleId());
                js.put("good",good);
                list.add(js);
            }
            return JSONArray.parseArray(JSON.toJSONString(list)).toJSONString();
        }
        return null;
    }

    @ResponseBody
    @PostMapping(path = "/searchHistoryAndHot")
    public String getsearchHistoryAndHot(@RequestBody JSONObject jsonObject){
        String userId=jsonObject.get("userId").toString();
        //构造hot
        List<String> list=articleService.getHotWord();
        //构造history
        List<String> hist=new ArrayList<>();
        hist.add("ka");
        hist.add("ka yeye");
        hist.add("ka卡萨丁");
        hist.add("kaiood");
        hist.add("ka历史");
        hist.add("撒大ka");
        hist.add("他突然");
        JSONObject js=new JSONObject();
        js.put("history",hist);
        js.put("hot",list);
        return js.toJSONString();
    }

    @GetMapping(path = "/clearHistory")
    public String clearHistory(@RequestParam("userId")String userId){
        userService.clearHistory(userId);
        return "success";
    }
    @GetMapping(path = "/clearColl")
    public String clearColl(@RequestParam("userId")String userId){
        userService.clearColl(userId);
        return "success";
    }

}

package weprosever.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import weprosever.model.User;
import weprosever.model.Video;
import weprosever.service.UserService;
import weprosever.service.VideoService;
import weprosever.util.IdTool;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


@RestController
public class VideoController {
    @Autowired
    private VideoService videoService;
    @Autowired
    private UserService userService;
    @ResponseBody
    @PostMapping(path = "/videoImg")
    public String addVideoByImg(HttpServletRequest request, @RequestParam(value = "file",required = false) MultipartFile file)throws IOException {
        request.setCharacterEncoding("UTF-8");
        String videoId=request.getParameter("videoId");
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
            }
        }
        JSONObject js=new JSONObject();
        if(imgUrl==null){
            js.put("state","error");
            return js.toJSONString();
        }
        videoService.updateImg(imgUrl,videoId);
        js.put("state","success");
        return js.toJSONString();
    }
    @ResponseBody
    @PostMapping(path = "/video")
    public String addVideoByVideo(HttpServletRequest request, @RequestParam(value = "file",required = false) MultipartFile file)throws IOException{
        request.setCharacterEncoding("UTF-8");
        String userId=request.getParameter("userId");
        String title=request.getParameter("title");
        String videoUrl=null;
        if(!file.isEmpty()){
            String fileName = file.getOriginalFilename();
            String path = null;
            String type = null;
            type = fileName.indexOf(".") != -1 ? fileName.substring(fileName.lastIndexOf(".") + 1, fileName.length()) : null;
            if(type!=null){
                if ("MP4".equals(type.toUpperCase())||"AVI".equals(type.toUpperCase())||"RMVB".equals(type.toUpperCase())) {
                    String trueFileName = String.valueOf(System.currentTimeMillis()) + fileName;
                    path="C:/my/projectText/images/"+trueFileName;
                    file.transferTo(new File(path));
                    videoUrl="http://localhost:8080/"+trueFileName;
                }else {
                    return "error";
                }
            }
        }
        JSONObject js=new JSONObject();
        if (videoUrl==null){
            js.put("state","error");
            return js.toJSONString();
        }
        //构造videoId
        String id=new IdTool().getVideoId(userId,title);
        Video video=new Video();
        video.setUrl(videoUrl);
        video.setUserId(userId);
        video.setTitle(title);
        video.setVideoId(id);
        video.setImgUrl("not");//未设置封面
        videoService.addVideo(video);
        js.put("state","success");
        js.put("videoId",id);
        return js.toJSONString();
    }
    @GetMapping(path = "/videoList")
    public String getVideoList(@RequestParam("count") String temp){
        int beginCount=Integer.parseInt(temp);
        int max= new Long(videoService.maxCount()).intValue();
        if(beginCount>max){
            JSONObject j=new JSONObject();
            j.put("state","full");
            return j.toJSONString();
        }
        int endCount=beginCount+6>max? max:beginCount+6;//每次更新6条
        List<Video> list=videoService.getOpList(beginCount,endCount);
        List<JSONObject> listRes=new ArrayList<>();
        for (int i=0;i<list.size();i++){
            JSONObject js=new JSONObject();
            js.put("id",list.get(i).getId());
            js.put("videoId",list.get(i).getVideoId());
            js.put("title",list.get(i).getTitle());
            js.put("url",list.get(i).getUrl());
            js.put("imgUrl",list.get(i).getImgUrl());
            String userId=list.get(i).getUserId();
            User user=userService.findById(userId);
            js.put("writter",user.getNickName());
            js.put("writterImg",user.getImgUrl());
            listRes.add(js);
        }
        return  JSONArray.parseArray(JSON.toJSONString(listRes)).toJSONString();
    }
}

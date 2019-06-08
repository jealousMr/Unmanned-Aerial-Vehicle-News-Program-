package weprosever.service;

import weprosever.model.Video;

import java.util.List;

public interface VideoService {
    public void addVideo(Video video);
    public Video findAllByVideoId(String vid);
    public boolean hasVideo(String videoId);
    public int updateImg(String img_url,String video_id);
    public int updateUrlByVideoId(String url,String video_id);
    public List<Video> findById(int beginId,int endId);
    public Long maxCount();
    public List<Video> getOpList(int begin,int end);
    public void deleteByTableId(int id);
    public int updateTitleByVideoId(String title,String videoId);
}

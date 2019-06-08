package weprosever.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import weprosever.dao.VideoDao;
import weprosever.model.Video;

import java.util.List;

@Repository
public class VideoServiceImpl implements VideoService{
    @Autowired
    public VideoDao videoDao;
    @Override
    public void addVideo(Video video) {
        videoDao.save(video);
    }

    @Override
    public Video findAllByVideoId(String vid) {
        return videoDao.findByVideoId(vid);
    }
    @Override
    public boolean hasVideo(String videoId) {
        Video video=videoDao.findByVideoId(videoId);
        if (video!=null)
            return true;
        return false;
    }
    @Override
    public int updateImg(String img_url, String video_id) {
        return videoDao.updateImgUrlByVideoId(img_url,video_id);
    }

    @Override
    public int updateUrlByVideoId(String url, String video_id) {
        return videoDao.updateUrlByVideoId(url,video_id);
    }

    @Override
    public List<Video> findById(int beginId, int endId) {
        return videoDao.findById(beginId,endId);
    }

    @Override
    public Long maxCount() {
        return videoDao.count();
    }

    @Override
    public List<Video> getOpList(int begin, int end) {
        return videoDao.getOpList(begin,end);
    }

    @Override
    public void deleteByTableId(int id) {
        videoDao.deleteById(id);
    }

    @Override
    public int updateTitleByVideoId(String title, String videoId) {
        return videoDao.updateTitleByVideoId(title,videoId);
    }

}

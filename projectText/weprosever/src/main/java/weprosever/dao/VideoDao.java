package weprosever.dao;

import org.springframework.cache.annotation.CacheConfig;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import weprosever.model.Video;

import javax.transaction.Transactional;
import java.util.List;

@CacheConfig(cacheNames = {"video"})
public interface VideoDao extends JpaRepository<Video,Integer> {
    public Video findByVideoId(String vid);

    @Transactional
    @Modifying(clearAutomatically = true)
    @Query(value = "update video p set p.img_url =?1 where p.video_id = ?2",nativeQuery = true)
    public int updateImgUrlByVideoId(String img_url,String video_id);

    @Transactional
    @Modifying(clearAutomatically = true)
    @Query(value = "update video p set p.url =?1 where p.video_id = ?2",nativeQuery = true)
    public int updateUrlByVideoId(String url,String video_id);

    @Query(value = "select * from video where id>=:beginId and id<=:endId",nativeQuery = true)
    public List<Video> findById(@Param("beginId")int beginId,@Param("endId") int endId);

    @Query(value = "select * from video limit :b,:e",nativeQuery = true)
    public List<Video> getOpList(@Param("b")int b,@Param("e") int e);

    public void deleteById(int id);

    @Transactional
    @Modifying(clearAutomatically = true)
    @Query(value = "update video p set p.title =?1 where p.video_id = ?2",nativeQuery = true)
    public int updateTitleByVideoId(String title,String videoId);
}

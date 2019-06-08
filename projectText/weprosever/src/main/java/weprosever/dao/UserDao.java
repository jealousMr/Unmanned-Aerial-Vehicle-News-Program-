package weprosever.dao;

import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import weprosever.model.Collection;
import weprosever.model.History;
import weprosever.model.User;


import java.util.List;

@CacheConfig(cacheNames = {"users"})
public interface UserDao extends JpaRepository<User,Integer> {

    @Query(value = "select * from user limit :b,:e",nativeQuery = true)
    public List<User> getOpList(@Param("b")int b, @Param("e") int e);


    @Cacheable
    public User findByUserId(String userId);
    //个人文章收藏
    public Collection findCollectionByUserId(String userId);
    public void createCollection(String userId, List<String> articleList);
    public void insertCollection(String userId,String articleId);
    //个人文章阅读记录
    public History findHistoryByUserId(String userId);
    public void createHistory(String userId,List<String> articleId);
    public void insertHistory(String userId,String articleId);
    public boolean hasHistory(String userId,String articleId);

    public void clearHistory(String userId);
    public void clearColl(String userId);
}

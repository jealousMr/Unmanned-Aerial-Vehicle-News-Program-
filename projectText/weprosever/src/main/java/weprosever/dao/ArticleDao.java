package weprosever.dao;

import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import weprosever.model.Article;
import weprosever.model.Comment;
import weprosever.model.CommentInfo;
import weprosever.model.Video;

import javax.transaction.Transactional;
import java.util.List;

@CacheConfig(cacheNames = {"articles"})
@Transactional
public interface ArticleDao extends JpaRepository<Article,Integer> {
    @Cacheable
    public Article getById(int id);
    @Cacheable(cacheNames = "articleList")
    public List<Article> findAllByUserId(String userId);
    @Cacheable(cacheNames = "titleList")
    public List<Article>findAllByTitleLike(String title);

    @Query(value = "select * from article limit :beginId,:endId",nativeQuery = true)
    public List<Article> findById(@Param("beginId") int beginId,@Param("endId") int endId);

    @Query(value = "select * from article where article_id=:articleId",nativeQuery = true)
    public Article findByArticleId(@Param("articleId")String articleId);

    public void saveComment(Comment comment);

    public int getArticleGoodByarticleId(String articleId);


    public List<CommentInfo> getCommentInfoByArticleId(String articleId);

    public void addCommentInfo(String commentPeople,String articleId,String mes);
    public void addGood(String articleId);

    @Query(value = "update article set title =?1 where article_Id = ?2",nativeQuery = true)
    @Modifying
    public void updateArticleTitle(String title,String articleId);

    @Query(value = "update article set title_two =?1 where article_id = ?2",nativeQuery = true)
    @Modifying
    public void updateArticleTitleTwo(String titleTwo,String articleId);

    @Query(value = "update article  set content =?1 where article_id = ?2",nativeQuery = true)
    @Modifying
    public void updateArticleContent(String content,String articleId);

    public void deleteById(int id);


    @Query(value = "select * from article limit :b,:e",nativeQuery = true)
    public List<Article> getOpList(@Param("b")int b, @Param("e") int e);


    @Query(value = "select * FROM article ORDER BY RAND() LIMIT 4",nativeQuery = true)
    public List<Article> getRandArticle();

}

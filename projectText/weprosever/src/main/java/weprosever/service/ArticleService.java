package weprosever.service;

import weprosever.model.Article;
import weprosever.model.Comment;
import weprosever.model.CommentInfo;

import java.util.List;

public interface ArticleService {
    public void addArticle(Article article);
    public List<Article> findArticleById(int begin,int end);
    public int maxCount();
    public Article findArticleByArticleId(String articleId);
    public void saveComment(Comment comment);
    public int getArticleGoodByarticleId(String articleId);
    public List<CommentInfo> getCommentInfoByArticleId(String articleId);
    public void addCommentInfo(String commentPeople,String articleId,String mes);
    public void addGood(String articleId);
    public List<Article> findAllByUserId(String userId);
    public List<Article>findAllByTitleLike(String title);
    public void updateArticle(String title,String titleTwo,String content,String aid);
    public void deleteByTableId(int id);
    public List<Article> getOpList(int begin,int end);
    public List<Article> getRandArticle();
    public List<String> getHotWord();
}

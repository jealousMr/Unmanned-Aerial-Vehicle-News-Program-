package weprosever.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import weprosever.dao.ArticleDao;
import weprosever.model.Article;
import weprosever.model.Comment;
import weprosever.model.CommentInfo;
import weprosever.util.IdTool;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Service
public class ArticleServiceImpl implements ArticleService{

    @Autowired
    private ArticleDao articleDao;
    @Override
    public void addArticle(Article article) {
        articleDao.save(article);
    }



    @Override
    public List<Article> findArticleById(int begin, int end) {
        return articleDao.findById(begin,end);
    }

    @Override
    public int maxCount() {
        return (int) articleDao.count();
    }

    @Override
    public Article findArticleByArticleId(String articleId) {
        return articleDao.findByArticleId(articleId);
    }

    @Override
    public void saveComment(Comment comment) {
        articleDao.saveComment(comment);
    }

    @Override
    public int getArticleGoodByarticleId(String articleId) {
        return articleDao.getArticleGoodByarticleId(articleId);
    }

    @Override
    public List<CommentInfo> getCommentInfoByArticleId(String articleId) {
        return articleDao.getCommentInfoByArticleId(articleId);
    }

    @Override
    public void addCommentInfo(String commentPeople, String articleId, String mes) {
        articleDao.addCommentInfo(commentPeople,articleId,mes);
    }

    @Override
    public void addGood(String articleId) {
        articleDao.addGood(articleId);
    }

    @Override
    public List<Article> findAllByUserId(String userId) {
        return articleDao.findAllByUserId(userId);
    }

    @Override
    public List<Article> findAllByTitleLike(String title) {
        return articleDao.findAllByTitleLike(title);
    }

    @Override
    public void updateArticle(String title, String titleTwo, String content, String aid) {
        if(title!=null && title.length()>1)
            articleDao.updateArticleTitle(title,aid);
        if(titleTwo!=null && titleTwo.length()>1)
            articleDao.updateArticleTitleTwo(titleTwo,aid);
        if(content!=null && content.length()>1)
            articleDao.updateArticleContent(content,aid);
    }

    @Override
    public void deleteByTableId(int id) {
        articleDao.deleteById(id);
    }

    @Override
    public List<Article> getOpList(int begin, int end) {
        return articleDao.getOpList(begin,end);
    }

    @Override
    public List<Article> getRandArticle() {
        return articleDao.getRandArticle();
    }

    @Override
    public List<String> getHotWord() {

        List<Article> articleList=articleDao.findById(0,12);
        List<String> list=new ArrayList<>();
        for (int i=0;i<articleList.size();i++){
            String temp=articleList.get(i).getTitle();
            int c=(int)(Math.random()*10)+3;
            c=(c>temp.length()?temp.length():c);
            temp=temp.substring(0,c);
            list.add(temp);
        }
        return list;
    }


}

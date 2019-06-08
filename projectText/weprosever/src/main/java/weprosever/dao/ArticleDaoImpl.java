package weprosever.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Repository;
import weprosever.model.Article;
import weprosever.model.Comment;
import weprosever.model.CommentInfo;

import java.util.List;

@Repository
public class ArticleDaoImpl {
    @Autowired
    private MongoTemplate mongoTemplate;

    public void saveComment(Comment comment){
        mongoTemplate.insert(comment,"article");
    }

    public int getArticleGoodByarticleId(String articleId){
        Query query = Query.query(Criteria.where("articleId").is(articleId));
        Comment comment=mongoTemplate.findOne(query,Comment.class,"article");
        return comment.getGood();
    }
    public List<CommentInfo> getCommentInfoByArticleId(String articleId){
        Query query = Query.query(Criteria.where("articleId").is(articleId));
        Comment comment=mongoTemplate.findOne(query,Comment.class,"article");
        return comment.getListInfo();
    }
    public void addCommentInfo(String commentPeople,String articleId,String mes){
        CommentInfo commentInfo=new CommentInfo(commentPeople,mes);
        Update update=new Update();
        update.push("listInfo",commentInfo);
        Query query=Query.query(Criteria.where("articleId").is(articleId));
        mongoTemplate.updateFirst(query,update,Comment.class,"article");
    }
    public void addGood(String articleId){
        Query query1=Query.query(Criteria.where("articleId").is(articleId));
        Comment comment=mongoTemplate.findOne(query1, Comment.class,"article");
        int good=comment.getGood();
        Query query = Query.query(Criteria.where("articleId").is(articleId));
        Update update = Update.update("good", good+1);
        mongoTemplate.updateFirst(query,update,Comment.class,"article");
    }
}

package weprosever.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Repository;
import weprosever.model.Collection;
import weprosever.model.Comment;
import weprosever.model.History;

import java.util.ArrayList;
import java.util.List;

@Repository
public class UserDaoImpl {
    @Autowired
    private MongoTemplate mongoTemplate;

    public Collection findCollectionByUserId(String userId){
        Query query=Query.query(Criteria.where("userId").is(userId));
        Collection collection=mongoTemplate.findOne(query,Collection.class,"collection");
        return collection;
    }
    public void createCollection(String userId, List<String> articleList){
        Collection collection=new Collection();
        collection.setUserId(userId);
        collection.setArticleList(articleList);
        mongoTemplate.insert(collection,"collection");
    }
    public void insertCollection(String userId,String articleId){
        Update update=new Update();
        update.push("articleList",articleId);
        Query query=Query.query(Criteria.where("userId").is(userId));
        mongoTemplate.updateFirst(query,update, Comment.class,"collection");
    }
    public History findHistoryByUserId(String userId){
        Query query=Query.query(Criteria.where("userId").is(userId));
        History history=mongoTemplate.findOne(query,History.class,"history");
        return history;
    }
    public void createHistory(String userId,List<String> articleId){
        History history=new History();
        history.setUserId(userId);
        history.setArticleList(articleId);
        mongoTemplate.insert(history,"history");
    }
    public void insertHistory(String userId,String articleId){
        Update update=new Update();
        update.push("articleList",articleId);
        Query query=Query.query(Criteria.where("userId").is(userId));
        mongoTemplate.updateFirst(query,update,History.class,"history");
    }
    public boolean hasHistory(String userId,String articleId){
        Query query=Query.query(Criteria.where("userId").is(userId));
        History history=mongoTemplate.findOne(query,History.class,"history");
        List<String> list=history.getArticleList();
        for (int i=0;i<list.size();i++){
            if (list.get(i).equals(articleId))
                return true;
        }
        return false;
    }
    public void clearHistory(String userId){
        Query query1=Query.query(Criteria.where("userId").is(userId));
        List<String> list=new ArrayList<>();
        Update update=Update.update("articleList",list);
        mongoTemplate.updateFirst(query1,update,History.class,"history");
    }
    public void clearColl(String userId){
        Query query1=Query.query(Criteria.where("userId").is(userId));
        List<String> list=new ArrayList<>();
        Update update=Update.update("articleList",list);
        mongoTemplate.updateFirst(query1,update,Collection.class,"collection");
    }
}

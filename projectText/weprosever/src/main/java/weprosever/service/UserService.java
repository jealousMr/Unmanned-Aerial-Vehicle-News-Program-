package weprosever.service;

import weprosever.model.Collection;
import weprosever.model.History;
import weprosever.model.User;

import java.util.List;

public interface UserService {
    public void addUser(User user);
    public boolean hasUser(String userId);
    public User findById(String id);
    public Collection findCollectionByUserId(String userId);
    public void createCollection(String userId, List<String> articleList);
    public void insertCollection(String userId,String articleId);
    public History findHistoryByUserId(String userId);
    public void createHistory(String userId,List<String> articleId);
    public void insertHistory(String userId,String articleId);
    public boolean hasHistory(String userId,String articleId);
    public int findMax();
    public List<User> getOpList(int begin,int end);
    public void clearHistory(String userId);
    public void clearColl(String userId);
}

package weprosever.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import weprosever.dao.UserDao;
import weprosever.model.Article;
import weprosever.model.Collection;
import weprosever.model.History;
import weprosever.model.User;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserDao userDao;
    @Override
    public void addUser(User user) {
        userDao.save(user);
    }

    @Override
    public boolean hasUser(String userId) {
        User user=userDao.findByUserId(userId);
        if(user!=null&&user.getUserId().equals(userId))
            return true;
        return false;
    }


    @Override
    public User findById(String id) {
        return userDao.findByUserId(id);
    }

    @Override
    public Collection findCollectionByUserId(String userId) {
        return userDao.findCollectionByUserId(userId);
    }

    @Override
    public void createCollection(String userId, List<String> articleList) {
        userDao.createCollection(userId,articleList);
    }

    @Override
    public void insertCollection(String userId, String articleId) {
        userDao.insertCollection(userId,articleId);
    }

    @Override
    public History findHistoryByUserId(String userId) {
        return userDao.findHistoryByUserId(userId);
    }

    @Override
    public void createHistory(String userId, List<String> articleId) {
            userDao.createHistory(userId,articleId);
    }

    @Override
    public void insertHistory(String userId, String articleId) {
        userDao.insertHistory(userId,articleId);
    }

    @Override
    public boolean hasHistory(String userId, String articleId) {
        return userDao.hasHistory(userId,articleId);
    }

    @Override
    public int findMax() {
        return (int) userDao.count();
    }

    @Override
    public List<User> getOpList(int begin, int end) {
        return userDao.getOpList(begin,end);
    }

    public void clearHistory(String userId){
        userDao.clearHistory(userId);
    }

    @Override
    public void clearColl(String userId) {
        userDao.clearColl(userId);
    }
}

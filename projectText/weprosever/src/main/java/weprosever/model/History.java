package weprosever.model;

import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serializable;
import java.util.List;

@Document(collection = "history")
public class History implements Serializable {
    private String userId;
    private List<String> articleList;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public void setArticleList(List<String> articleList) {
        this.articleList = articleList;
    }

    public List<String> getArticleList() {
        return articleList;
    }

}

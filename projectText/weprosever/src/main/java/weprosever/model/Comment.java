package weprosever.model;

import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serializable;
import java.util.List;

@Document(collection = "article")
public class Comment implements Serializable {
    private String articleId;
    private int good;
    private List<CommentInfo> listInfo;

    public Comment(){}
    public Comment(String articleId,int good,List<CommentInfo> list){
        this.articleId=articleId;
        this.good=good;
        this.listInfo=list;
    }

    public List<CommentInfo> getListInfo() {
        return listInfo;
    }

    public void setListInfo(List<CommentInfo> listInfo) {
        this.listInfo = listInfo;
    }

    public int getGood() {
        return good;
    }

    public String getArticleId() {
        return articleId;
    }

    public void setArticleId(String articleId) {
        this.articleId = articleId;
    }

    public void setGood(int good) {
        this.good = good;
    }
}

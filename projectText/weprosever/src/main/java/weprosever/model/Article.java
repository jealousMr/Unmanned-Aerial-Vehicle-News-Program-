package weprosever.model;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import java.io.Serializable;
import java.util.Objects;

@Entity
public class Article implements Serializable {
    private static final long serialVersionUID = 1L;
    private int id;
    private String articleId;
    private String userId;
    private String title;
    private String titleTwo;
    private String imgUrl;
    private String content;
    private String sendTime;
    public Article(){}
    public Article(String articleId,String userId,String title,String titleTwo,String imgUrl,String content,String sendTime){
        this.articleId=articleId;
        this.userId=userId;
        this.title=title;
        this.titleTwo=titleTwo;
        this.imgUrl=imgUrl;
        this.content=content;
        this.sendTime=sendTime;
    }

    @Id
    @Column(name = "id", nullable = false)
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Basic
    @Column(name = "article_id", nullable = true, length = 225)
    public String getArticleId() {
        return articleId;
    }

    public void setArticleId(String articleId) {
        this.articleId = articleId;
    }

    @Basic
    @Column(name = "user_id", nullable = false, length = 100)
    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    @Basic
    @Column(name = "title", nullable = false, length = 100)
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @Basic
    @Column(name = "title_two", nullable = false, length = 60)
    public String getTitleTwo() {
        return titleTwo;
    }

    public void setTitleTwo(String titleTwo) {
        this.titleTwo = titleTwo;
    }

    @Basic
    @Column(name = "img_url", nullable = false, length = 225)
    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    @Basic
    @Column(name = "content", nullable = false, length = -1)
    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    @Basic
    @Column(name = "send_time", nullable = false, length = 30)
    public String getSendTime() {
        return sendTime;
    }

    public void setSendTime(String sendTime) {
        this.sendTime = sendTime;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Article article = (Article) o;
        return id == article.id &&
                Objects.equals(articleId, article.articleId) &&
                Objects.equals(userId, article.userId) &&
                Objects.equals(title, article.title) &&
                Objects.equals(titleTwo, article.titleTwo) &&
                Objects.equals(imgUrl, article.imgUrl) &&
                Objects.equals(content, article.content) &&
                Objects.equals(sendTime, article.sendTime);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, articleId, userId, title, titleTwo, imgUrl, content, sendTime);
    }
}

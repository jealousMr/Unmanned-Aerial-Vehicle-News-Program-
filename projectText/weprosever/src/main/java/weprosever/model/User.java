package weprosever.model;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import java.io.Serializable;
import java.util.Objects;

@Entity
public class User implements Serializable {
    private static final long serialVersionUID = 1L;
    private int id;
    private String userId;
    private String nickName;
    private String imgUrl;
    private Integer articleSum;
    private Integer videoSum;

    @Id
    @Column(name = "id", nullable = false)
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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
    @Column(name = "nick_name", nullable = false, length = 60)
    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
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
    @Column(name = "article_sum", nullable = true)
    public Integer getArticleSum() {
        return articleSum;
    }

    public void setArticleSum(Integer articleSum) {
        this.articleSum = articleSum;
    }

    @Basic
    @Column(name = "video_sum", nullable = true)
    public Integer getVideoSum() {
        return videoSum;
    }

    public void setVideoSum(Integer videoSum) {
        this.videoSum = videoSum;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return id == user.id &&
                Objects.equals(userId, user.userId) &&
                Objects.equals(nickName, user.nickName) &&
                Objects.equals(imgUrl, user.imgUrl) &&
                Objects.equals(articleSum, user.articleSum) &&
                Objects.equals(videoSum, user.videoSum);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, userId, nickName, imgUrl, articleSum, videoSum);
    }
}

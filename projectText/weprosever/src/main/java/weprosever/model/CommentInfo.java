package weprosever.model;

import java.io.Serializable;

public class CommentInfo implements Serializable {
    private String userId;
    private String mes;

    public CommentInfo(String id,String m){
        userId=id;
        mes=m;
    }
    public CommentInfo(){}
    public String getMes() {
        return mes;
    }

    public String getUserId() {
        return userId;
    }

    public void setMes(String mes) {
        this.mes = mes;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
}

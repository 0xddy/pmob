package model;

public class PickUp {
    private String uid;
    private String userNick;
    private String following;
    private String userImageUrl;
    private String picks;

    public PickUp(String uid, String userNick, String following, String userImageUrl,String picks) {
        this.uid = uid;
        this.userNick = userNick;
        this.following = following;
        this.userImageUrl = userImageUrl;
        this.picks = picks;
    }


    public String getPicks() {
        return picks;
    }

    public void setPicks(String picks) {
        this.picks = picks;
    }

    public String getUserImageUrl() {
        return userImageUrl;
    }

    public void setUserImageUrl(String userImageUrl) {
        this.userImageUrl = userImageUrl;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getUserNick() {
        return userNick;
    }

    public void setUserNick(String userNick) {
        this.userNick = userNick;
    }

    public String getFollowing() {
        return following;
    }

    public void setFollowing(String following) {
        this.following = following;
    }
}

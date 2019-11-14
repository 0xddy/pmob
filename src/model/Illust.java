package model;

import java.util.List;

public class Illust {

    /**
     * illustId : 77213595
     * illustTitle : 黒きつねちゃん
     * id : 77213595
     * title : 黒きつねちゃん
     * illustType : 0
     * xRestrict : 0
     * restrict : 0
     * sl : 2
     * url : https://i.pximg.net/c/250x250_80_a2/img-master/img/2019/10/10/20/25/42/77213595_p0_square1200.jpg
     * description :
     * tags : ["オリジナル","女の子","獣耳"]
     * userId : 201323
     * userName : 桜あに
     * width : 1061
     * height : 1500
     * pageCount : 1
     * isBookmarkable : null
     * bookmarkData : null
     * alt : #オリジナル 黒きつねちゃん - 桜あに的插画
     * isAdContainer : false
     */

    private String illustId;
    private String illustTitle;
    private String id;
    private String title;
    private Double illustType;
    private int xRestrict;
    private int restrict;
    private int sl;
    private String url;
    private String description;
    private String userId;
    private String userName;
    private int width;
    private int height;
    private int pageCount;
    private Object isBookmarkable;
    private Object bookmarkData;
    private String alt;
    private boolean isAdContainer;
    private List<String> tags;

    public String getIllustId() {
        return illustId;
    }

    public void setIllustId(String illustId) {
        this.illustId = illustId;
    }

    public String getIllustTitle() {
        return illustTitle;
    }

    public void setIllustTitle(String illustTitle) {
        this.illustTitle = illustTitle;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title.replaceAll("\\?", "")
                .replaceAll("\\\\", "")
                .replaceAll("/", "")
                .replaceAll("\\*", "")
                .replaceAll(":", "")
                .replaceAll(">", "")
                .replaceAll("<", "")
                .replaceAll("\\|", "").trim();
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Double getIllustType() {
        return illustType;
    }

    public void setIllustType(Double illustType) {
        this.illustType = illustType;
    }

    public int getXRestrict() {
        return xRestrict;
    }

    public void setXRestrict(int xRestrict) {
        this.xRestrict = xRestrict;
    }

    public int getRestrict() {
        return restrict;
    }

    public void setRestrict(int restrict) {
        this.restrict = restrict;
    }

    public int getSl() {
        return sl;
    }

    public void setSl(int sl) {
        this.sl = sl;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public int getPageCount() {
        return pageCount;
    }

    public void setPageCount(int pageCount) {
        this.pageCount = pageCount;
    }

    public Object getIsBookmarkable() {
        return isBookmarkable;
    }

    public void setIsBookmarkable(Object isBookmarkable) {
        this.isBookmarkable = isBookmarkable;
    }

    public Object getBookmarkData() {
        return bookmarkData;
    }

    public void setBookmarkData(Object bookmarkData) {
        this.bookmarkData = bookmarkData;
    }

    public String getAlt() {
        return alt;
    }

    public void setAlt(String alt) {
        this.alt = alt;
    }

    public boolean isIsAdContainer() {
        return isAdContainer;
    }

    public void setIsAdContainer(boolean isAdContainer) {
        this.isAdContainer = isAdContainer;
    }

    public List<String> getTags() {
        return tags;
    }

    public void setTags(List<String> tags) {
        this.tags = tags;
    }
}

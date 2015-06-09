
package com.cgearc.yummy.api.search;

import java.util.ArrayList;
import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ApiArticle {

    @Expose
    private String id;
    @SerializedName("public_at")
    @Expose
    private String publicAt;
    @SerializedName("site_category")
    @Expose
    private String siteCategory;
    @SerializedName("site_category_id")
    @Expose
    private String siteCategoryId;
    @Expose
    private String category;
    @SerializedName("category_id")
    @Expose
    private String categoryId;
    @Expose
    private String link;
    @Expose
    private String status;
    @SerializedName("is_top")
    @Expose
    private String isTop;
    @SerializedName("is_nl2br")
    @Expose
    private String isNl2br;
    @SerializedName("comment_perm")
    @Expose
    private String commentPerm;
    @SerializedName("comment_hidden")
    @Expose
    private String commentHidden;
    @Expose
    private String title;
    @Expose
    private String thumb;
    @Expose
    private String cover;
    @Expose
    private ApiHits hits;
    @Expose
    private ApiInfo info;
    @SerializedName("hit_uri")
    @Expose
    private String hitUri;
    @Expose
    private ApiSns sns;
    @Expose
    private List<Object> tags = new ArrayList<Object>();
    @Expose
    private ApiUser user;

    /**
     * 
     * @return
     *     The id
     */
    public String getId() {
        return id;
    }

    /**
     * 
     * @param id
     *     The id
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * 
     * @return
     *     The publicAt
     */
    public String getPublicAt() {
        return publicAt;
    }

    /**
     * 
     * @param publicAt
     *     The public_at
     */
    public void setPublicAt(String publicAt) {
        this.publicAt = publicAt;
    }

    /**
     * 
     * @return
     *     The siteCategory
     */
    public String getSiteCategory() {
        return siteCategory;
    }

    /**
     * 
     * @param siteCategory
     *     The site_category
     */
    public void setSiteCategory(String siteCategory) {
        this.siteCategory = siteCategory;
    }

    /**
     * 
     * @return
     *     The siteCategoryId
     */
    public String getSiteCategoryId() {
        return siteCategoryId;
    }

    /**
     * 
     * @param siteCategoryId
     *     The site_category_id
     */
    public void setSiteCategoryId(String siteCategoryId) {
        this.siteCategoryId = siteCategoryId;
    }

    /**
     * 
     * @return
     *     The category
     */
    public String getCategory() {
        return category;
    }

    /**
     * 
     * @param category
     *     The category
     */
    public void setCategory(String category) {
        this.category = category;
    }

    /**
     * 
     * @return
     *     The categoryId
     */
    public String getCategoryId() {
        return categoryId;
    }

    /**
     * 
     * @param categoryId
     *     The category_id
     */
    public void setCategoryId(String categoryId) {
        this.categoryId = categoryId;
    }

    /**
     * 
     * @return
     *     The link
     */
    public String getLink() {
        return link;
    }

    /**
     * 
     * @param link
     *     The link
     */
    public void setLink(String link) {
        this.link = link;
    }

    /**
     * 
     * @return
     *     The status
     */
    public String getStatus() {
        return status;
    }

    /**
     * 
     * @param status
     *     The status
     */
    public void setStatus(String status) {
        this.status = status;
    }

    /**
     * 
     * @return
     *     The isTop
     */
    public String getIsTop() {
        return isTop;
    }

    /**
     * 
     * @param isTop
     *     The is_top
     */
    public void setIsTop(String isTop) {
        this.isTop = isTop;
    }

    /**
     * 
     * @return
     *     The isNl2br
     */
    public String getIsNl2br() {
        return isNl2br;
    }

    /**
     * 
     * @param isNl2br
     *     The is_nl2br
     */
    public void setIsNl2br(String isNl2br) {
        this.isNl2br = isNl2br;
    }

    /**
     * 
     * @return
     *     The commentPerm
     */
    public String getCommentPerm() {
        return commentPerm;
    }

    /**
     * 
     * @param commentPerm
     *     The comment_perm
     */
    public void setCommentPerm(String commentPerm) {
        this.commentPerm = commentPerm;
    }

    /**
     * 
     * @return
     *     The commentHidden
     */
    public String getCommentHidden() {
        return commentHidden;
    }

    /**
     * 
     * @param commentHidden
     *     The comment_hidden
     */
    public void setCommentHidden(String commentHidden) {
        this.commentHidden = commentHidden;
    }

    /**
     * 
     * @return
     *     The title
     */
    public String getTitle() {
        return title;
    }

    /**
     * 
     * @param title
     *     The title
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * 
     * @return
     *     The thumb
     */
    public String getThumb() {
        return thumb;
    }

    /**
     * 
     * @param thumb
     *     The thumb
     */
    public void setThumb(String thumb) {
        this.thumb = thumb;
    }

    /**
     * 
     * @return
     *     The cover
     */
    public String getCover() {
        return cover;
    }

    /**
     * 
     * @param cover
     *     The cover
     */
    public void setCover(String cover) {
        this.cover = cover;
    }

    /**
     * 
     * @return
     *     The hits
     */
    public ApiHits getHits() {
        return hits;
    }

    /**
     * 
     * @param hits
     *     The hits
     */
    public void setHits(ApiHits hits) {
        this.hits = hits;
    }

    /**
     * 
     * @return
     *     The info
     */
    public ApiInfo getInfo() {
        return info;
    }

    /**
     * 
     * @param info
     *     The info
     */
    public void setInfo(ApiInfo info) {
        this.info = info;
    }

    /**
     * 
     * @return
     *     The hitUri
     */
    public String getHitUri() {
        return hitUri;
    }

    /**
     * 
     * @param hitUri
     *     The hit_uri
     */
    public void setHitUri(String hitUri) {
        this.hitUri = hitUri;
    }

    /**
     * 
     * @return
     *     The sns
     */
    public ApiSns getSns() {
        return sns;
    }

    /**
     * 
     * @param sns
     *     The sns
     */
    public void setSns(ApiSns sns) {
        this.sns = sns;
    }

    /**
     * 
     * @return
     *     The tags
     */
    public List<Object> getTags() {
        return tags;
    }

    /**
     * 
     * @param tags
     *     The tags
     */
    public void setTags(List<Object> tags) {
        this.tags = tags;
    }

    /**
     * 
     * @return
     *     The user
     */
    public ApiUser getUser() {
        return user;
    }

    /**
     * 
     * @param user
     *     The user
     */
    public void setUser(ApiUser user) {
        this.user = user;
    }


}

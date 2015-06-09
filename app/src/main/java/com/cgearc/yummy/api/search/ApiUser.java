
package com.cgearc.yummy.api.search;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


public class ApiUser {

    @Expose
    private String name;
    @SerializedName("display_name")
    @Expose
    private String displayName;
    @Expose
    private String avatar;
    @Expose
    private String cavatar;
    @Expose
    private String link;
    @SerializedName("is_vip")
    @Expose
    private Boolean isVip;
    @SerializedName("has_ad")
    @Expose
    private Boolean hasAd;

    /**
     * 
     * @return
     *     The name
     */
    public String getName() {
        return name;
    }

    /**
     * 
     * @param name
     *     The name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * 
     * @return
     *     The displayName
     */
    public String getDisplayName() {
        return displayName;
    }

    /**
     * 
     * @param displayName
     *     The display_name
     */
    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    /**
     * 
     * @return
     *     The avatar
     */
    public String getAvatar() {
        return avatar;
    }

    /**
     * 
     * @param avatar
     *     The avatar
     */
    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    /**
     * 
     * @return
     *     The cavatar
     */
    public String getCavatar() {
        return cavatar;
    }

    /**
     * 
     * @param cavatar
     *     The cavatar
     */
    public void setCavatar(String cavatar) {
        this.cavatar = cavatar;
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
     *     The isVip
     */
    public Boolean getIsVip() {
        return isVip;
    }

    /**
     * 
     * @param isVip
     *     The is_vip
     */
    public void setIsVip(Boolean isVip) {
        this.isVip = isVip;
    }

    /**
     * 
     * @return
     *     The hasAd
     */
    public Boolean getHasAd() {
        return hasAd;
    }

    /**
     * 
     * @param hasAd
     *     The has_ad
     */
    public void setHasAd(Boolean hasAd) {
        this.hasAd = hasAd;
    }


}

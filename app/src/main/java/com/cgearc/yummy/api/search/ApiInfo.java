
package com.cgearc.yummy.api.search;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


public class ApiInfo {

    @SerializedName("trackbacks_count")
    @Expose
    private Integer trackbacksCount;
    @SerializedName("comments_count")
    @Expose
    private Integer commentsCount;
    @Expose
    private Integer hit;

    /**
     * 
     * @return
     *     The trackbacksCount
     */
    public Integer getTrackbacksCount() {
        return trackbacksCount;
    }

    /**
     * 
     * @param trackbacksCount
     *     The trackbacks_count
     */
    public void setTrackbacksCount(Integer trackbacksCount) {
        this.trackbacksCount = trackbacksCount;
    }

    /**
     * 
     * @return
     *     The commentsCount
     */
    public Integer getCommentsCount() {
        return commentsCount;
    }

    /**
     * 
     * @param commentsCount
     *     The comments_count
     */
    public void setCommentsCount(Integer commentsCount) {
        this.commentsCount = commentsCount;
    }

    /**
     * 
     * @return
     *     The hit
     */
    public Integer getHit() {
        return hit;
    }

    /**
     * 
     * @param hit
     *     The hit
     */
    public void setHit(Integer hit) {
        this.hit = hit;
    }


}

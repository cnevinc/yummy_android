
package com.cgearc.yummy.api.search;

import com.google.gson.annotations.Expose;

public class ApiSns {

    @Expose
    private Object twitter;
    @Expose
    private Object facebook;
    @Expose
    private Object plurk;

    /**
     * 
     * @return
     *     The twitter
     */
    public Object getTwitter() {
        return twitter;
    }

    /**
     * 
     * @param twitter
     *     The twitter
     */
    public void setTwitter(Object twitter) {
        this.twitter = twitter;
    }

    /**
     * 
     * @return
     *     The facebook
     */
    public Object getFacebook() {
        return facebook;
    }

    /**
     * 
     * @param facebook
     *     The facebook
     */
    public void setFacebook(Object facebook) {
        this.facebook = facebook;
    }

    /**
     * 
     * @return
     *     The plurk
     */
    public Object getPlurk() {
        return plurk;
    }

    /**
     * 
     * @param plurk
     *     The plurk
     */
    public void setPlurk(Object plurk) {
        this.plurk = plurk;
    }


}

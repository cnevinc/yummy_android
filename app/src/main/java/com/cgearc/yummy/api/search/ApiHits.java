
package com.cgearc.yummy.api.search;

import com.google.gson.annotations.Expose;

public class ApiHits {

    @Expose
    private Integer total;
    @Expose
    private Integer daily;

    /**
     * 
     * @return
     *     The total
     */
    public Integer getTotal() {
        return total;
    }

    /**
     * 
     * @param total
     *     The total
     */
    public void setTotal(Integer total) {
        this.total = total;
    }

    /**
     * 
     * @return
     *     The daily
     */
    public Integer getDaily() {
        return daily;
    }

    /**
     * 
     * @param daily
     *     The daily
     */
    public void setDaily(Integer daily) {
        this.daily = daily;
    }


}

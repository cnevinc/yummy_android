
package com.cgearc.yummy.api.search;

import java.util.ArrayList;
import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ApiSearchResult {

    @Expose
    private List<ApiArticle> articles = new ArrayList<ApiArticle>();
    @Expose
    private Integer total;
    @SerializedName("per_page")
    @Expose
    private Integer perPage;
    @Expose
    private Integer page;
    @Expose
    private String message;
    @Expose
    private Integer error;

    /**
     * 
     * @return
     *     The articles
     */
    public List<ApiArticle> getArticles() {
        return articles;
    }

    /**
     * 
     * @param articles
     *     The articles
     */
    public void setArticles(List<ApiArticle> articles) {
        this.articles = articles;
    }

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
     *     The perPage
     */
    public Integer getPerPage() {
        return perPage;
    }

    /**
     * 
     * @param perPage
     *     The per_page
     */
    public void setPerPage(Integer perPage) {
        this.perPage = perPage;
    }

    /**
     * 
     * @return
     *     The page
     */
    public Integer getPage() {
        return page;
    }

    /**
     * 
     * @param page
     *     The page
     */
    public void setPage(Integer page) {
        this.page = page;
    }

    /**
     * 
     * @return
     *     The message
     */
    public String getMessage() {
        return message;
    }

    /**
     * 
     * @param message
     *     The message
     */
    public void setMessage(String message) {
        this.message = message;
    }

    /**
     * 
     * @return
     *     The error
     */
    public Integer getError() {
        return error;
    }

    /**
     * 
     * @param error
     *     The error
     */
    public void setError(Integer error) {
        this.error = error;
    }


}

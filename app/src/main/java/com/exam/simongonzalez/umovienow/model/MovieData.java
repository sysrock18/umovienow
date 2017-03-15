
package com.exam.simongonzalez.umovienow.model;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class MovieData {

    @SerializedName("page")
    @Expose
    private Integer page;
    @SerializedName("results")
    @Expose
    private List<Result> results = null;
    @SerializedName("total_results")
    @Expose
    private Integer total_results;
    @SerializedName("total_pages")
    @Expose
    private Integer total_pages;

    public Integer getPage() {
        return page;
    }

    public void setPage(Integer page) {
        this.page = page;
    }

    public List<Result> getResults() {
        return results;
    }

    public void setResults(List<Result> results) {
        this.results = results;
    }

    public Integer getTotal_results() {
        return page;
    }

    public void setTotal_results(Integer page) {
        this.page = page;
    }

    public Integer getTotal_pages() {
        return page;
    }

    public void setTotal_pages(Integer page) {
        this.page = page;
    }

}

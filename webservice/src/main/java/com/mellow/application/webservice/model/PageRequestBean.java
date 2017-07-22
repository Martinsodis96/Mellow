package com.mellow.application.webservice.model;

import javax.ws.rs.DefaultValue;
import javax.ws.rs.QueryParam;

public final class PageRequestBean {
    @QueryParam("page")
    private int page;

    @QueryParam("size")
    @DefaultValue("5")
    private int size;

    @QueryParam("sort")
    @DefaultValue("asc")
    private String sort;

    public int getPage() {
        return page;
    }

    public int getSize() {
        return size;
    }

    public String getSort() {
        return sort;
    }
}

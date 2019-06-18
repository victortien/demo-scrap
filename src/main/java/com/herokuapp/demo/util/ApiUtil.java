package com.herokuapp.demo.util;

public enum ApiUtil {

    LOGIN("/login"),
    AUTH("/api/auth"),
    PRODUCT("/api/products");

    private String name;

    private ApiUtil(String name) {
        this.name = name;
    }

    public String getApi() {
        return name;
    }
}

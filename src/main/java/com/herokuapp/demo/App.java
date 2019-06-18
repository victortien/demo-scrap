package com.herokuapp.demo;

import com.herokuapp.demo.crawler.Crawler;

public class App {

    public static void main(String[] arg) {
        Crawler.getInstance().doScrap();
    }

}

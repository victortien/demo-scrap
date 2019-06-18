package com.herokuapp.demo.util;

import java.io.IOException;
import java.util.HashMap;

import org.jsoup.Connection;
import org.jsoup.Connection.Method;
import org.jsoup.Connection.Response;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.jsoup.Jsoup;

import com.herokuapp.demo.exception.BaseException;
import com.herokuapp.demo.vo.RequestVO;

public class JsoupUtil {

    private static final String USER_AGENT = "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/75.0.3770.80 Safari/537.36";

    private JsoupUtil() {}

    public static Elements getDomElements(String html, String cssSelector) {
        Document dom = Jsoup.parse(html);
        return dom.select(cssSelector);
    }

    public static Response httpGet(RequestVO request) {
        try {
            Connection con = initConnection(request);
            return con.method(Method.GET).execute();
        } catch (IOException e) {
            throw new BaseException(e);
        }
    }

    public static Response httpPost(RequestVO request) {
        try {
            Connection con = initConnection(request);
            return con.method(Method.POST).execute();
        } catch (IOException e) {
            throw new BaseException(e);
        }
    }

    private static Connection initConnection(RequestVO request) {
        return Jsoup.connect(request.getUrl())
                .userAgent(USER_AGENT)
                .headers(request.getHeaders() == null ? new HashMap<String, String>() : request.getHeaders())
                .cookies(request.getCookies() == null ? new HashMap<String, String>() : request.getCookies())
                .data(request.getParameters() == null ? new HashMap<String, String>() : request.getParameters())
                .ignoreContentType(request.isIgnoreContentType());
    }

}

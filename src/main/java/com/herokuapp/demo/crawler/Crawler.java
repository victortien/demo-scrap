package com.herokuapp.demo.crawler;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

import org.jsoup.Connection.Response;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.herokuapp.demo.constant.Const;
import com.herokuapp.demo.util.ApiUtil;
import com.herokuapp.demo.util.FileUtil;
import com.herokuapp.demo.util.JsoupUtil;
import com.herokuapp.demo.util.StreamUtil;
import com.herokuapp.demo.vo.RequestVO;

public class Crawler {

    private static final String CSS_SELECTOR = "form input";

    private static final String ATTRIBUTE_NAME = "name";
    private static final String ATTRIBUTE_VALUE = "value";

    public static Crawler getInstance() {
        return new Crawler();
    }

    public void doScrap() {
        Map<String, String> submitData = fillingOutLoginPage();
        Response login = authenticate(submitData);
        Response fetch = fetchData(login);
        writeToFile(fetch);
    }

    private Map<String, String> fillingOutLoginPage() {
        RequestVO requestVO = new RequestVO();
        requestVO.setUrl(Const.TARGET_URL + ApiUtil.LOGIN.getApi());
        requestVO.setIgnoreContentType(true);
        Response page = JsoupUtil.httpGet(requestVO);

        Elements elements = JsoupUtil.getDomElements(page.body(), CSS_SELECTOR);

        return StreamUtil.ofNullable(elements)
                .map(this::mockLogin)
                .collect(Collectors.toMap(e -> e.attr(ATTRIBUTE_NAME), e -> e.attr(ATTRIBUTE_VALUE)));
    }

    private Element mockLogin(Element element) {
        if (element.attr(ATTRIBUTE_NAME).equals("username")) {
            element.attr(ATTRIBUTE_VALUE, Const.LOGIN_ID);
        }
        if (element.attr(ATTRIBUTE_NAME).equals("password")) {
            element.attr(ATTRIBUTE_VALUE, Const.LOGIN_PWD);
        }
        return element;
    }

    private Response authenticate(Map<String, String> submitData) {
        RequestVO requestVO2 = new RequestVO();
        requestVO2.setUrl(Const.TARGET_URL + ApiUtil.AUTH.getApi());
        requestVO2.setIgnoreContentType(true);
        requestVO2.setParameters(submitData);
        return JsoupUtil.httpPost(requestVO2);
    }

    private Response fetchData(Response login) {
        Map<String, String> query = new HashMap<>();
        query.put("offset", "0");
        query.put("limit", "1000");

        RequestVO requestVO3 = new RequestVO();
        requestVO3.setUrl(Const.TARGET_URL + ApiUtil.PRODUCT.getApi());
        requestVO3.setIgnoreContentType(true);
        requestVO3.setCookies(login.cookies());
        requestVO3.setParameters(query);
        return JsoupUtil.httpGet(requestVO3);
    }

    private void writeToFile(Response fetch) {
        String path = Const.FILE_PATH + Const.FILE_NAME;
        FileUtil.writeFile(fetch.body(), path);
    }

}

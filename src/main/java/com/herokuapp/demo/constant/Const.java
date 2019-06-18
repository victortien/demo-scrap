package com.herokuapp.demo.constant;

import com.herokuapp.demo.util.PropertiesUtil;

public class Const {

    private Const() {
    }

    public static final String CONFIG_FILE = "herokuapp";

    public static final String TARGET_URL = PropertiesUtil.getResourceBundle("herokuapp.url", CONFIG_FILE);
    public static final String LOGIN_ID = PropertiesUtil.getResourceBundle("herokuapp.username", CONFIG_FILE);
    public static final String LOGIN_PWD = PropertiesUtil.getResourceBundle("herokuapp.password", CONFIG_FILE);

    public static final String FILE_PATH = PropertiesUtil.getResourceBundle("output.file.path", CONFIG_FILE);
    public static final String FILE_NAME = PropertiesUtil.getResourceBundle("output.file.name", CONFIG_FILE);
}

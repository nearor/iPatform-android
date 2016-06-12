package com.nearor.framwork.util;

/**
 *
 */
public class URLUtil {

    // 接口地址
    private static final String BASE_URL = "http://mapi.52zhai.com";

    private static final String H5_BASE_URL = "http://weixin.52zhai.com";

    private static final String IMAGE_BASE_URL = "http://img.52zhai.com";

    public static String getBaseUrl() {
        return BASE_URL;
    }

    public static String getImageBaseUrl() {
        return IMAGE_BASE_URL;
    }

    public static String getCustomerServiceH5URL(String name) {
        return H5_BASE_URL + "/html5/html/weixin/" + name + ".html";
    }


    public static String getAboutH5URL() {
        return H5_BASE_URL + "/html5/html/illustrate/about_zhai.html";
    }

    public static String getAggrementH5URL() {
        return IMAGE_BASE_URL + "/static/registration_agreement.html";
    }

   }



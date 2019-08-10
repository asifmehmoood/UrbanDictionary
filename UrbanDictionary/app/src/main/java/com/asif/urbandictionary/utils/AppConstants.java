package com.asif.urbandictionary.utils;

public class AppConstants {
    public static final String BASE_URL = "https://mashape-community-urban-dictionary.p.rapidapi.com/";
    public static final String API_KEY = "ba08083f85msha151638d3c63a74p1c2f61jsnecc9bd3dddf5";
    public static final String PACKAGE_NAME = "com.asif.urbandictionary";
    public static final int CACHE_SIZE = 10 * 1024 * 1024; //10 MB

    public static interface THUMB{
        public static int ORDER_BY_NONE = 0;
        public static int ORDER_BY_UP = 1;
        public static int ORDER_BY_DOWN = 2;
    }
}

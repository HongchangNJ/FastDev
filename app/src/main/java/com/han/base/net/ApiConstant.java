package com.han.base.net;

import com.han.demo.BuildConfig;

/**
 * @author hongchang
 * @description: 网络地址常量
 * @date 2017/6/10  10:56
 */

public class ApiConstant {
    /**
     * 接口根地址
     */
    public static final String BASE_SERVER_URL = BuildConfig.HOST;
    public static final String API_PROJECT_NAME = "/my_xu_zhou/api";
    public static final String API_SOLR_PROJECT_NAME = "/my_xu_zhou/solr";
    public static final String RECOMMEND_PROJECT_NAME = "/my_reference/api";

    /*CDN_HOST*/
    /*public static final String BASE_CDN_HOST = BuildConfig.CDN_HOST;
    public static final String API_CDN_PROJECT_NAME = BASE_CDN_HOST + "/my_xu_zhou/api";*/

    /*// 图片服务器地址
    public static final String BASE_IMAGE_SERVER_HOST = BuildConfig.IMAGE_UPLOAD_HOST;
    public static final String IMAGE_SERVER_PATH = BASE_IMAGE_SERVER_HOST + "/upload";
    public static final String FILE_SERVER_PATH = BASE_IMAGE_SERVER_HOST + "/fileupload";
    public static final String IMAGE_DOWNLOAD_PATH = BuildConfig.IMAGE_DOWNLOAD_HOST;
    public static final String VIDEO_DOWNLOAD_PATH = BuildConfig.IMAGE_DOWNLOAD_HOST + "/filedownload";*/

    public static final int MULTI_DEVICE_CODE = 5;
    public static final int EXPIRE_CODE = 6;
    public static final int VERTIFICATION_CODE_ERROR = 2;
    public static final int BIND_ALREADY = 3;
    public static final int UNKNOWN_ERROR = -1;
    public static final int NORMAL_ERROR = 0;
}

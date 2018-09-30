package com.han.framework.utils;

import android.os.Environment;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.lang.reflect.Method;
import java.util.Properties;

/**
 * Created by hongchang on 17/1/11.
 */

public class OSUtils {
    public static final String SYS_EMUI = "sys_emui";
    public static final String SYS_MIUI = "sys_miui";
    public static final String SYS_FLYME = "sys_flyme";
    public static final String SYS_SAMSUNG = "sys_samsung";

    private static final String KEY_MIUI_VERSION_CODE = "ro.miui.ui.version.code";
    private static final String KEY_MIUI_VERSION_NAME = "ro.miui.ui.version.name";
    private static final String KEY_MIUI_INTERNAL_STORAGE = "ro.miui.internal.storage";
    private static final String KEY_EMUI_API_LEVEL = "ro.build.hw_emui_api_level";
    private static final String KEY_EMUI_VERSION = "ro.build.version.emui";
    private static final String KEY_EMUI_CONFIG_HW_SYS_VERSION = "ro.confg.hw_systemversion";

    // samsung
    private static final String KEY_DEVICE_BRAND = "ro.product.brand";
    private static final String KEY_BUILD_VERSION = "ro.build.version.release";
    private static final String KEY_PRODUCT_MODEL = "ro.product.model";

    private static final String VALUE_SAMSUNG_BRAND = "samsung";
    private static final String VALUE_BUILD_VERSION_601 = "6.0.1";
    private static final String VALUE_S8_9550 = "SM-G9550";
    private static final String VALUE_S8_9500 = "SM-G9500";

    public static String getSystem(){
        String SYS = "";
        try {
            Properties prop= new Properties();
            prop.load(new FileInputStream(new File(Environment.getRootDirectory(), "build.prop")));
            if(prop.getProperty(KEY_MIUI_VERSION_CODE, null) != null
                    || prop.getProperty(KEY_MIUI_VERSION_NAME, null) != null
                    || prop.getProperty(KEY_MIUI_INTERNAL_STORAGE, null) != null){
                SYS = SYS_MIUI;//小米
            }else if(prop.getProperty(KEY_EMUI_API_LEVEL, null) != null
                    ||prop.getProperty(KEY_EMUI_VERSION, null) != null
                    ||prop.getProperty(KEY_EMUI_CONFIG_HW_SYS_VERSION, null) != null){
                SYS = SYS_EMUI;//华为
            }else if(getMeizuFlymeOSFlag().toLowerCase().contains("flyme")){
                SYS = SYS_FLYME;//魅族
            };
        } catch (IOException e){
            e.printStackTrace();
            return SYS;
        }
        return SYS;
    }

    public static boolean isMIUI() {
        return SYS_MIUI.equals(getSystem()) ? true : false;
    }

    public static boolean isEMUI() {
        return SYS_EMUI.equals(getSystem()) ? true : false;
    }


    public static boolean isFlyMe() {
        return SYS_FLYME.equals(getSystem()) ? true : false;
    }

    public static boolean isSAMSUNG() {
        Properties prop= new Properties();
        try {
            prop.load(new FileInputStream(new File(Environment.getRootDirectory(), "build.prop")));
            String brand = prop.getProperty(KEY_DEVICE_BRAND, null);
            if (VALUE_SAMSUNG_BRAND.equals(brand)) {
                return true;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return false;
    }

    /**
     * 是否是s8 和 s8 plus
     * @return
     */
    public static boolean isS8_OR_S8PLUS() {
        Properties prop= new Properties();
        try {
            prop.load(new FileInputStream(new File(Environment.getRootDirectory(), "build.prop")));
            String brand = prop.getProperty(KEY_DEVICE_BRAND, null);
            String model = prop.getProperty(KEY_PRODUCT_MODEL, null);
            if (VALUE_SAMSUNG_BRAND.equals(brand)
                    && (VALUE_S8_9500.equals(model) || VALUE_S8_9550.equals(model))) {
                return true;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return  false;
    }

    /**
     * 是否是g9209
     * @return
     */
    public static boolean isG9209() {
        Properties prop= new Properties();
        try {
            prop.load(new FileInputStream(new File(Environment.getRootDirectory(), "build.prop")));
            String brand = prop.getProperty(KEY_DEVICE_BRAND, null);
            String model = prop.getProperty(KEY_PRODUCT_MODEL, null);
            if (VALUE_SAMSUNG_BRAND.equals(brand)
                    && "SM-G9209".equals(model)) {
                return true;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return  false;
    }

    public static String getMeizuFlymeOSFlag() {
        return getSystemProperty("ro.build.display.id", "");
    }

    private static String getSystemProperty(String key, String defaultValue) {
        try {
            Class<?> clz = Class.forName("android.os.SystemProperties");
            Method get = clz.getMethod("get", String.class, String.class);
            return (String)get.invoke(clz, key, defaultValue);
        } catch (Exception e) {
        }
        return defaultValue;
    }
}

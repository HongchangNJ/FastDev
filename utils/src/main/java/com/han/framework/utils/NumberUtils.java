package com.han.framework.utils;

import java.text.DecimalFormat;

/**
 * Created by hongchang on 2017/10/16.
 */

public class NumberUtils {
    // 格式化商品价格（保留2位数）
    public static String formatNumber(int number) {
        DecimalFormat df = new DecimalFormat("00");
        return df.format(number);
    }

    // Integer 拆箱
    public static int getInt(Integer integer) {
        return integer == null ? 0 : integer;
    }

    // Long 拆箱
    public static long getLong(Long longValue) {
        return longValue == null ? 0 : longValue;
    }
}

package com.han.framework.utils;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by hongchang on 17/8/11.
 */

public class URLUtils {

        public static Map<String, String> getQueryParams(String url) {
            try {
                Map<String, String> params = new HashMap<String, String>();
                String[] urlParts = url.split("\\?");
                if (urlParts.length > 1) {
                    String query = urlParts[1];
                    for (String param : query.split("&")) {
                        String[] pair = param.split("=");
                        String key = URLDecoder.decode(pair[0], "UTF-8");
                        String value = "";
                        if (pair.length > 1) {
                            value = URLDecoder.decode(pair[1], "UTF-8");
                        }

                        params.put(key, value);
                    }
                }
                return params;
            } catch (UnsupportedEncodingException ex) {
                throw new AssertionError(ex);
            }
    }

}

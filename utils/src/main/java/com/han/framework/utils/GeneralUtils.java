package com.han.framework.utils;

import android.content.Context;
import android.text.InputFilter;
import android.text.Spanned;
import android.widget.EditText;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by hongchang on 2017/11/16.
 */

public class GeneralUtils {

    // emoji 表情输入限制
    public static void filterEmoji(final Context context, EditText editText) {
        InputFilter emojiFilter = new InputFilter() {
            Pattern emoji = Pattern.compile("[\ud83c\udc00-\ud83c\udfff]|[\ud83d\udc00-\ud83d\udfff]|[\u2600-\u27ff]",
                    Pattern.UNICODE_CASE | Pattern.CASE_INSENSITIVE);

            @Override
            public CharSequence filter(CharSequence source, int start, int end, Spanned dest, int dstart, int dend) {
                Matcher emojiMatcher = emoji.matcher(source);
                if (emojiMatcher.find()) {
                    ToastUtils.showLongToast(context,"不支持输入表情");
                    return "";
                }
                return null;
            }
        };

        InputFilter[] emojiFilters = {emojiFilter};
        editText.setFilters(emojiFilters);
    }

    // emoji 表情输入限制
    public static void filterEmoji(final Context context, EditText editText, int length) {
        InputFilter emojiFilter = new InputFilter() {
            Pattern emoji = Pattern.compile("[\ud83c\udc00-\ud83c\udfff]|[\ud83d\udc00-\ud83d\udfff]|[\u2600-\u27ff]",
                    Pattern.UNICODE_CASE | Pattern.CASE_INSENSITIVE);

            @Override
            public CharSequence filter(CharSequence source, int start, int end, Spanned dest, int dstart, int dend) {
                Matcher emojiMatcher = emoji.matcher(source);
                if (emojiMatcher.find()) {
                    ToastUtils.showLongToast(context,"不支持输入表情");
                    return "";
                }
                return null;
            }
        };

        InputFilter[] emojiFilters = new InputFilter[]{emojiFilter, new InputFilter.LengthFilter(length)};
        editText.setFilters(emojiFilters);
    }
}

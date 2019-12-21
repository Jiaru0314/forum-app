package com.jit.utils;

import android.annotation.SuppressLint;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * author : XZQ
 * date   : 2019/12/4
 * description    :
 */
public class DateFormatUtil {

    public static String formatDate(Date date) {
        @SuppressLint("SimpleDateFormat") SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy年MM月dd日"); //使用了默认的格式创建了一个日期格式化对象。
        return dateFormat.format(date);
    }
}

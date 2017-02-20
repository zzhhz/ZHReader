package com.zzh.reader.util;

import android.content.Intent;

import com.zzh.reader.Constants;

import org.greenrobot.eventbus.EventBus;

/**
 * ----------Dragon be here!----------/
 * 　　　┏┓　　　┏┓
 * 　　┏┛┻━━━┛┻┓
 * 　　┃　　　　　　　┃
 * 　　┃　　　━　　　┃
 * 　　┃　┳┛　┗┳　┃
 * 　　┃　　　　　　　┃
 * 　　┃　　　┻　　　┃
 * 　　┃　　　　　　　┃
 * 　　┗━┓　　　┏━┛
 * 　　　　┃　　　┃神兽保佑
 * 　　　　┃　　　┃代码无BUG！
 * 　　　　┃　　　┗━━━┓
 * 　　　　┃　　　　　　　┣┓
 * 　　　　┃　　　　　　　┏┛
 * 　　　　┗┓┓┏━┳┓┏┛
 * 　　　　　┃┫┫　┃┫┫
 * 　　　　　┗┻┛　┗┻┛
 * ━━━━━━神兽出没━永无BUG━━━━━
 * Created by ZZH on 17/2/10.
 *
 * @Date: 17/2/10
 * @Email: zzh_hz@126.com
 * @QQ: 1299234582
 * @Author: zzh
 * @Description: 发送通知消息
 */

public class EventUtils {
    /**
     * 通知关闭图书
     */
    public static void sendEventCloseBook(){
        Intent intent = new Intent();
        intent.setAction(Constants.ACTION_CLOSE_BOOK);
        EventBus.getDefault().post(intent);
    }

    /**
     * 通知刷新图书列表
     */
    public static void sendEventRefreshBookList(){
        Intent intent = new Intent();
        intent.setAction(Constants.ACTION_REFRESH_BOOK_LIST);
        EventBus.getDefault().post(intent);
    }

    public static void sendEventDeleteShowBook() {
        Intent intent = new Intent();
        intent.setAction(Constants.ACTION_SHOW_DELETE_BOOK_LIST);
        EventBus.getDefault().post(intent);
    }

    public static void sendEventRefreshBookMarks() {
        Intent intent = new Intent();
        intent.setAction(Constants.ACTION_REFRESH_BOOK_MARK_LIST);
        EventBus.getDefault().post(intent);
    }
}

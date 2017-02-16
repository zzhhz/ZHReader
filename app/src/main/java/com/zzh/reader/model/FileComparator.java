package com.zzh.reader.model;

import java.io.File;
import java.util.Comparator;

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
 * Created by ZZH on 17/2/13.
 *
 * @Date: 17/2/13
 * @Email: zzh_hz@126.com
 * @QQ: 1299234582
 * @Author: zzh
 * @Description: 进行文件排序
 */
public class FileComparator implements Comparator<File>{

    @Override
    public int compare(File pre, File next) {
        if (pre.isDirectory() && next.isDirectory()){
            return pre.getName().compareTo(next.getName());
        }
        if (pre.isDirectory()){
            return 1;
        }
        if (next.isDirectory()){
            return 1;
        }
        return pre.getName().compareTo(next.getName());
    }
}

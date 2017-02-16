package com.zzh.reader.model;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Property;

import java.io.Serializable;

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
 * @Description: 文本标记，读书笔记
 */
@Entity(nameInDb = "book_mark")
public class BookMark implements Serializable{
    public static final long serialVersionUID = 2L;
    @Property(nameInDb = "bookMarkId")
    @Id(autoincrement = true)
    private Long bookMarkId;//
    @Property(nameInDb = "bookMark")
    private String bookMark;//记录的文本
    @Property(nameInDb = "begin")
    private int begin;
    @Property(nameInDb = "time")
    private long time;//时间
    @Property(nameInDb = "bookPath")
    private String bookPath;//书的位置

    @Generated(hash = 66778165)
    public BookMark(Long bookMarkId, String bookMark, int begin, long time,
            String bookPath) {
        this.bookMarkId = bookMarkId;
        this.bookMark = bookMark;
        this.begin = begin;
        this.time = time;
        this.bookPath = bookPath;
    }

    @Generated(hash = 1704575762)
    public BookMark() {
    }

    public Long getBookMarkId() {
        return bookMarkId;
    }

    public void setBookMarkId(Long bookMarkId) {
        this.bookMarkId = bookMarkId;
    }

    public String getBookMark() {
        return bookMark;
    }

    public void setBookMark(String bookMark) {
        this.bookMark = bookMark;
    }

    public int getBegin() {
        return begin;
    }

    public void setBegin(int begin) {
        this.begin = begin;
    }

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }

    public String getBookPath() {
        return bookPath;
    }

    public void setBookPath(String bookPath) {
        this.bookPath = bookPath;
    }
}

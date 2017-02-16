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
 * @Description: 书实体类
 */
@Entity(nameInDb = "books")
public class Book implements Serializable {

    public static final long serialVersionUID = 1L;

    @Property(nameInDb = "bookId")
    @Id(autoincrement = true)
    private Long bookId;
    @Property(nameInDb = "bookName")
    private String bookName;
    @Property(nameInDb = "bookPath")
    private String bookPath;
    @Property(nameInDb = "bookCover")
    private String bookCover;

    @Generated(hash = 1566138061)
    public Book(Long bookId, String bookName, String bookPath, String bookCover) {
        this.bookId = bookId;
        this.bookName = bookName;
        this.bookPath = bookPath;
        this.bookCover = bookCover;
    }

    @Generated(hash = 1839243756)
    public Book() {
    }

    public Long getBookId() {
        return bookId;
    }

    public void setBookId(Long bookId) {
        this.bookId = bookId;
    }

    public String getBookName() {
        return bookName;
    }

    public void setBookName(String bookName) {
        this.bookName = bookName;
    }

    public String getBookPath() {
        return bookPath;
    }

    public void setBookPath(String bookPath) {
        this.bookPath = bookPath;
    }

    public String getBookCover() {
        return bookCover;
    }

    public void setBookCover(String bookCover) {
        this.bookCover = bookCover;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Book)) return false;

        Book book = (Book) o;

        if (!bookName.equals(book.bookName)) return false;
        return bookPath.equals(book.bookPath);

    }

    @Override
    public int hashCode() {
        int result = bookName.hashCode();
        result = 31 * result + bookPath.hashCode();
        return result;
    }
}

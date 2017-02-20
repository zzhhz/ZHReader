package com.zzh.reader.model;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Property;

import java.io.Serializable;
import org.greenrobot.greendao.annotation.Generated;

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
 * Created by Administrator on 2017/2/20.
 *
 * @Date: 2017/2/20
 * @Email: zzh_hz@126.com
 * @QQ: 1299234582
 * @Author: zzh
 * @Description: 目录信息
 */
@Entity(nameInDb = "book_catalogue")
public class Catalogue implements Serializable{
    public static final long serialVersionUID = 3L;
    @Property(nameInDb = "catalogueId")
    private Long catalogueId;
    @Property(nameInDb = "position")
    private int position;//起始点
    @Property(nameInDb = "catalogue")
    private String catalogue;//目录名称
    @Property(nameInDb = "bookPath")
    private String bookPath; //路径

    @Generated(hash = 6269787)
    public Catalogue(Long catalogueId, int position, String catalogue,
            String bookPath) {
        this.catalogueId = catalogueId;
        this.position = position;
        this.catalogue = catalogue;
        this.bookPath = bookPath;
    }

    @Generated(hash = 1495294006)
    public Catalogue() {
    }

    public Long getCatalogueId() {
        return catalogueId;
    }

    public void setCatalogueId(Long catalogueId) {
        this.catalogueId = catalogueId;
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    public String getCatalogue() {
        return catalogue;
    }

    public void setCatalogue(String catalogue) {
        this.catalogue = catalogue;
    }

    public String getBookPath() {
        return bookPath;
    }

    public void setBookPath(String bookPath) {
        this.bookPath = bookPath;
    }

    @Override
    public String toString() {
        return "Catalogue{" +
                "catalogueId=" + catalogueId +
                ", position=" + position +
                ", catalogue='" + catalogue + '\'' +
                ", bookPath='" + bookPath + '\'' +
                '}';
    }
}

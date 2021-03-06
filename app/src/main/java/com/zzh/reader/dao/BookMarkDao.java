package com.zzh.reader.dao;

import android.database.Cursor;
import android.database.sqlite.SQLiteStatement;

import org.greenrobot.greendao.AbstractDao;
import org.greenrobot.greendao.Property;
import org.greenrobot.greendao.internal.DaoConfig;
import org.greenrobot.greendao.database.Database;
import org.greenrobot.greendao.database.DatabaseStatement;

import com.zzh.reader.model.BookMark;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT.
/** 
 * DAO for table "book_mark".
*/
public class BookMarkDao extends AbstractDao<BookMark, Long> {

    public static final String TABLENAME = "book_mark";

    /**
     * Properties of entity BookMark.<br/>
     * Can be used for QueryBuilder and for referencing column names.
     */
    public static class Properties {
        public final static Property BookMarkId = new Property(0, Long.class, "bookMarkId", true, "bookMarkId");
        public final static Property BookMark = new Property(1, String.class, "bookMark", false, "bookMark");
        public final static Property Begin = new Property(2, int.class, "begin", false, "begin");
        public final static Property Time = new Property(3, long.class, "time", false, "time");
        public final static Property BookPath = new Property(4, String.class, "bookPath", false, "bookPath");
    }


    public BookMarkDao(DaoConfig config) {
        super(config);
    }
    
    public BookMarkDao(DaoConfig config, DaoSession daoSession) {
        super(config, daoSession);
    }

    /** Creates the underlying database table. */
    public static void createTable(Database db, boolean ifNotExists) {
        String constraint = ifNotExists? "IF NOT EXISTS ": "";
        db.execSQL("CREATE TABLE " + constraint + "\"book_mark\" (" + //
                "\"bookMarkId\" INTEGER PRIMARY KEY AUTOINCREMENT ," + // 0: bookMarkId
                "\"bookMark\" TEXT," + // 1: bookMark
                "\"begin\" INTEGER NOT NULL ," + // 2: begin
                "\"time\" INTEGER NOT NULL ," + // 3: time
                "\"bookPath\" TEXT);"); // 4: bookPath
    }

    /** Drops the underlying database table. */
    public static void dropTable(Database db, boolean ifExists) {
        String sql = "DROP TABLE " + (ifExists ? "IF EXISTS " : "") + "\"book_mark\"";
        db.execSQL(sql);
    }

    @Override
    protected final void bindValues(DatabaseStatement stmt, BookMark entity) {
        stmt.clearBindings();
 
        Long bookMarkId = entity.getBookMarkId();
        if (bookMarkId != null) {
            stmt.bindLong(1, bookMarkId);
        }
 
        String bookMark = entity.getBookMark();
        if (bookMark != null) {
            stmt.bindString(2, bookMark);
        }
        stmt.bindLong(3, entity.getBegin());
        stmt.bindLong(4, entity.getTime());
 
        String bookPath = entity.getBookPath();
        if (bookPath != null) {
            stmt.bindString(5, bookPath);
        }
    }

    @Override
    protected final void bindValues(SQLiteStatement stmt, BookMark entity) {
        stmt.clearBindings();
 
        Long bookMarkId = entity.getBookMarkId();
        if (bookMarkId != null) {
            stmt.bindLong(1, bookMarkId);
        }
 
        String bookMark = entity.getBookMark();
        if (bookMark != null) {
            stmt.bindString(2, bookMark);
        }
        stmt.bindLong(3, entity.getBegin());
        stmt.bindLong(4, entity.getTime());
 
        String bookPath = entity.getBookPath();
        if (bookPath != null) {
            stmt.bindString(5, bookPath);
        }
    }

    @Override
    public Long readKey(Cursor cursor, int offset) {
        return cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0);
    }    

    @Override
    public BookMark readEntity(Cursor cursor, int offset) {
        BookMark entity = new BookMark( //
            cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0), // bookMarkId
            cursor.isNull(offset + 1) ? null : cursor.getString(offset + 1), // bookMark
            cursor.getInt(offset + 2), // begin
            cursor.getLong(offset + 3), // time
            cursor.isNull(offset + 4) ? null : cursor.getString(offset + 4) // bookPath
        );
        return entity;
    }
     
    @Override
    public void readEntity(Cursor cursor, BookMark entity, int offset) {
        entity.setBookMarkId(cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0));
        entity.setBookMark(cursor.isNull(offset + 1) ? null : cursor.getString(offset + 1));
        entity.setBegin(cursor.getInt(offset + 2));
        entity.setTime(cursor.getLong(offset + 3));
        entity.setBookPath(cursor.isNull(offset + 4) ? null : cursor.getString(offset + 4));
     }
    
    @Override
    protected final Long updateKeyAfterInsert(BookMark entity, long rowId) {
        entity.setBookMarkId(rowId);
        return rowId;
    }
    
    @Override
    public Long getKey(BookMark entity) {
        if(entity != null) {
            return entity.getBookMarkId();
        } else {
            return null;
        }
    }

    @Override
    public boolean hasKey(BookMark entity) {
        return entity.getBookMarkId() != null;
    }

    @Override
    protected final boolean isEntityUpdateable() {
        return true;
    }
    
}

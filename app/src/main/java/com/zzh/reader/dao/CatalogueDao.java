package com.zzh.reader.dao;

import android.database.Cursor;
import android.database.sqlite.SQLiteStatement;

import org.greenrobot.greendao.AbstractDao;
import org.greenrobot.greendao.Property;
import org.greenrobot.greendao.internal.DaoConfig;
import org.greenrobot.greendao.database.Database;
import org.greenrobot.greendao.database.DatabaseStatement;

import com.zzh.reader.model.Catalogue;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT.
/** 
 * DAO for table "book_catalogue".
*/
public class CatalogueDao extends AbstractDao<Catalogue, Void> {

    public static final String TABLENAME = "book_catalogue";

    /**
     * Properties of entity Catalogue.<br/>
     * Can be used for QueryBuilder and for referencing column names.
     */
    public static class Properties {
        public final static Property CatalogueId = new Property(0, Long.class, "catalogueId", false, "catalogueId");
        public final static Property Position = new Property(1, int.class, "position", false, "position");
        public final static Property Catalogue = new Property(2, String.class, "catalogue", false, "catalogue");
        public final static Property BookPath = new Property(3, String.class, "bookPath", false, "bookPath");
    }


    public CatalogueDao(DaoConfig config) {
        super(config);
    }
    
    public CatalogueDao(DaoConfig config, DaoSession daoSession) {
        super(config, daoSession);
    }

    /** Creates the underlying database table. */
    public static void createTable(Database db, boolean ifNotExists) {
        String constraint = ifNotExists? "IF NOT EXISTS ": "";
        db.execSQL("CREATE TABLE " + constraint + "\"book_catalogue\" (" + //
                "\"catalogueId\" INTEGER," + // 0: catalogueId
                "\"position\" INTEGER NOT NULL ," + // 1: position
                "\"catalogue\" TEXT," + // 2: catalogue
                "\"bookPath\" TEXT);"); // 3: bookPath
    }

    /** Drops the underlying database table. */
    public static void dropTable(Database db, boolean ifExists) {
        String sql = "DROP TABLE " + (ifExists ? "IF EXISTS " : "") + "\"book_catalogue\"";
        db.execSQL(sql);
    }

    @Override
    protected final void bindValues(DatabaseStatement stmt, Catalogue entity) {
        stmt.clearBindings();
 
        Long catalogueId = entity.getCatalogueId();
        if (catalogueId != null) {
            stmt.bindLong(1, catalogueId);
        }
        stmt.bindLong(2, entity.getPosition());
 
        String catalogue = entity.getCatalogue();
        if (catalogue != null) {
            stmt.bindString(3, catalogue);
        }
 
        String bookPath = entity.getBookPath();
        if (bookPath != null) {
            stmt.bindString(4, bookPath);
        }
    }

    @Override
    protected final void bindValues(SQLiteStatement stmt, Catalogue entity) {
        stmt.clearBindings();
 
        Long catalogueId = entity.getCatalogueId();
        if (catalogueId != null) {
            stmt.bindLong(1, catalogueId);
        }
        stmt.bindLong(2, entity.getPosition());
 
        String catalogue = entity.getCatalogue();
        if (catalogue != null) {
            stmt.bindString(3, catalogue);
        }
 
        String bookPath = entity.getBookPath();
        if (bookPath != null) {
            stmt.bindString(4, bookPath);
        }
    }

    @Override
    public Void readKey(Cursor cursor, int offset) {
        return null;
    }    

    @Override
    public Catalogue readEntity(Cursor cursor, int offset) {
        Catalogue entity = new Catalogue( //
            cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0), // catalogueId
            cursor.getInt(offset + 1), // position
            cursor.isNull(offset + 2) ? null : cursor.getString(offset + 2), // catalogue
            cursor.isNull(offset + 3) ? null : cursor.getString(offset + 3) // bookPath
        );
        return entity;
    }
     
    @Override
    public void readEntity(Cursor cursor, Catalogue entity, int offset) {
        entity.setCatalogueId(cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0));
        entity.setPosition(cursor.getInt(offset + 1));
        entity.setCatalogue(cursor.isNull(offset + 2) ? null : cursor.getString(offset + 2));
        entity.setBookPath(cursor.isNull(offset + 3) ? null : cursor.getString(offset + 3));
     }
    
    @Override
    protected final Void updateKeyAfterInsert(Catalogue entity, long rowId) {
        // Unsupported or missing PK type
        return null;
    }
    
    @Override
    public Void getKey(Catalogue entity) {
        return null;
    }

    @Override
    public boolean hasKey(Catalogue entity) {
        return false;
    }

    @Override
    protected final boolean isEntityUpdateable() {
        return true;
    }
    
}

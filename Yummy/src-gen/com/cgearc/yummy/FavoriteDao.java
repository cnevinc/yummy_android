package com.cgearc.yummy;

import java.util.List;
import java.util.ArrayList;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;

import de.greenrobot.dao.AbstractDao;
import de.greenrobot.dao.Property;
import de.greenrobot.dao.internal.SqlUtils;
import de.greenrobot.dao.internal.DaoConfig;
import de.greenrobot.dao.query.Query;
import de.greenrobot.dao.query.QueryBuilder;

import com.cgearc.yummy.Favorite;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT.
/** 
 * DAO for table FAVORITE.
*/
public class FavoriteDao extends AbstractDao<Favorite, Long> {

    public static final String TABLENAME = "FAVORITE";

    /**
     * Properties of entity Favorite.<br/>
     * Can be used for QueryBuilder and for referencing column names.
    */
    public static class Properties {
        public final static Property Blogger_id = new Property(0, String.class, "blogger_id", false, "BLOGGER_ID");
        public final static Property Article_id = new Property(1, Long.class, "article_id", true, "ARTICLE_ID");
    };

    private DaoSession daoSession;

    private Query<Favorite> article_FavoriteListQuery;

    public FavoriteDao(DaoConfig config) {
        super(config);
    }
    
    public FavoriteDao(DaoConfig config, DaoSession daoSession) {
        super(config, daoSession);
        this.daoSession = daoSession;
    }

    /** Creates the underlying database table. */
    public static void createTable(SQLiteDatabase db, boolean ifNotExists) {
        String constraint = ifNotExists? "IF NOT EXISTS ": "";
        db.execSQL("CREATE TABLE " + constraint + "'FAVORITE' (" + //
                "'BLOGGER_ID' TEXT," + // 0: blogger_id
                "'ARTICLE_ID' INTEGER PRIMARY KEY );"); // 1: article_id
    }

    /** Drops the underlying database table. */
    public static void dropTable(SQLiteDatabase db, boolean ifExists) {
        String sql = "DROP TABLE " + (ifExists ? "IF EXISTS " : "") + "'FAVORITE'";
        db.execSQL(sql);
    }

    /** @inheritdoc */
    @Override
    protected void bindValues(SQLiteStatement stmt, Favorite entity) {
        stmt.clearBindings();
 
        String blogger_id = entity.getBlogger_id();
        if (blogger_id != null) {
            stmt.bindString(1, blogger_id);
        }
 
        Long article_id = entity.getArticle_id();
        if (article_id != null) {
            stmt.bindLong(2, article_id);
        }
    }

    @Override
    protected void attachEntity(Favorite entity) {
        super.attachEntity(entity);
        entity.__setDaoSession(daoSession);
    }

    /** @inheritdoc */
    @Override
    public Long readKey(Cursor cursor, int offset) {
        return cursor.isNull(offset + 1) ? null : cursor.getLong(offset + 1);
    }    

    /** @inheritdoc */
    @Override
    public Favorite readEntity(Cursor cursor, int offset) {
        Favorite entity = new Favorite( //
            cursor.isNull(offset + 0) ? null : cursor.getString(offset + 0), // blogger_id
            cursor.isNull(offset + 1) ? null : cursor.getLong(offset + 1) // article_id
        );
        return entity;
    }
     
    /** @inheritdoc */
    @Override
    public void readEntity(Cursor cursor, Favorite entity, int offset) {
        entity.setBlogger_id(cursor.isNull(offset + 0) ? null : cursor.getString(offset + 0));
        entity.setArticle_id(cursor.isNull(offset + 1) ? null : cursor.getLong(offset + 1));
     }
    
    /** @inheritdoc */
    @Override
    protected Long updateKeyAfterInsert(Favorite entity, long rowId) {
        entity.setArticle_id(rowId);
        return rowId;
    }
    
    /** @inheritdoc */
    @Override
    public Long getKey(Favorite entity) {
        if(entity != null) {
            return entity.getArticle_id();
        } else {
            return null;
        }
    }

    /** @inheritdoc */
    @Override    
    protected boolean isEntityUpdateable() {
        return true;
    }
    
    /** Internal query to resolve the "favoriteList" to-many relationship of Article. */
    public List<Favorite> _queryArticle_FavoriteList(Long article_id) {
        synchronized (this) {
            if (article_FavoriteListQuery == null) {
                QueryBuilder<Favorite> queryBuilder = queryBuilder();
                queryBuilder.where(Properties.Article_id.eq(null));
                article_FavoriteListQuery = queryBuilder.build();
            }
        }
        Query<Favorite> query = article_FavoriteListQuery.forCurrentThread();
        query.setParameter(0, article_id);
        return query.list();
    }

    private String selectDeep;

    protected String getSelectDeep() {
        if (selectDeep == null) {
            StringBuilder builder = new StringBuilder("SELECT ");
            SqlUtils.appendColumns(builder, "T", getAllColumns());
            builder.append(',');
            SqlUtils.appendColumns(builder, "T0", daoSession.getArticleDao().getAllColumns());
            builder.append(" FROM FAVORITE T");
            builder.append(" LEFT JOIN ARTICLE T0 ON T.'ARTICLE_ID'=T0.'_id'");
            builder.append(' ');
            selectDeep = builder.toString();
        }
        return selectDeep;
    }
    
    protected Favorite loadCurrentDeep(Cursor cursor, boolean lock) {
        Favorite entity = loadCurrent(cursor, 0, lock);
        int offset = getAllColumns().length;

        Article article = loadCurrentOther(daoSession.getArticleDao(), cursor, offset);
        entity.setArticle(article);

        return entity;    
    }

    public Favorite loadDeep(Long key) {
        assertSinglePk();
        if (key == null) {
            return null;
        }

        StringBuilder builder = new StringBuilder(getSelectDeep());
        builder.append("WHERE ");
        SqlUtils.appendColumnsEqValue(builder, "T", getPkColumns());
        String sql = builder.toString();
        
        String[] keyArray = new String[] { key.toString() };
        Cursor cursor = db.rawQuery(sql, keyArray);
        
        try {
            boolean available = cursor.moveToFirst();
            if (!available) {
                return null;
            } else if (!cursor.isLast()) {
                throw new IllegalStateException("Expected unique result, but count was " + cursor.getCount());
            }
            return loadCurrentDeep(cursor, true);
        } finally {
            cursor.close();
        }
    }
    
    /** Reads all available rows from the given cursor and returns a list of new ImageTO objects. */
    public List<Favorite> loadAllDeepFromCursor(Cursor cursor) {
        int count = cursor.getCount();
        List<Favorite> list = new ArrayList<Favorite>(count);
        
        if (cursor.moveToFirst()) {
            if (identityScope != null) {
                identityScope.lock();
                identityScope.reserveRoom(count);
            }
            try {
                do {
                    list.add(loadCurrentDeep(cursor, false));
                } while (cursor.moveToNext());
            } finally {
                if (identityScope != null) {
                    identityScope.unlock();
                }
            }
        }
        return list;
    }
    
    protected List<Favorite> loadDeepAllAndCloseCursor(Cursor cursor) {
        try {
            return loadAllDeepFromCursor(cursor);
        } finally {
            cursor.close();
        }
    }
    

    /** A raw-style query where you can pass any WHERE clause and arguments. */
    public List<Favorite> queryDeep(String where, String... selectionArg) {
        Cursor cursor = db.rawQuery(getSelectDeep() + where, selectionArg);
        return loadDeepAllAndCloseCursor(cursor);
    }
 
}

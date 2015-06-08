package com.cgearc.yummy;

import android.database.sqlite.SQLiteDatabase;

import java.util.Map;

import de.greenrobot.dao.AbstractDao;
import de.greenrobot.dao.AbstractDaoSession;
import de.greenrobot.dao.identityscope.IdentityScopeType;
import de.greenrobot.dao.internal.DaoConfig;

import com.cgearc.yummy.Article;
import com.cgearc.yummy.Picture;
import com.cgearc.yummy.Favorite;

import com.cgearc.yummy.ArticleDao;
import com.cgearc.yummy.PictureDao;
import com.cgearc.yummy.FavoriteDao;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT.

/**
 * {@inheritDoc}
 * 
 * @see de.greenrobot.dao.AbstractDaoSession
 */
public class DaoSession extends AbstractDaoSession {

    private final DaoConfig articleDaoConfig;
    private final DaoConfig pictureDaoConfig;
    private final DaoConfig favoriteDaoConfig;

    private final ArticleDao articleDao;
    private final PictureDao pictureDao;
    private final FavoriteDao favoriteDao;

    public DaoSession(SQLiteDatabase db, IdentityScopeType type, Map<Class<? extends AbstractDao<?, ?>>, DaoConfig>
            daoConfigMap) {
        super(db);

        articleDaoConfig = daoConfigMap.get(ArticleDao.class).clone();
        articleDaoConfig.initIdentityScope(type);

        pictureDaoConfig = daoConfigMap.get(PictureDao.class).clone();
        pictureDaoConfig.initIdentityScope(type);

        favoriteDaoConfig = daoConfigMap.get(FavoriteDao.class).clone();
        favoriteDaoConfig.initIdentityScope(type);

        articleDao = new ArticleDao(articleDaoConfig, this);
        pictureDao = new PictureDao(pictureDaoConfig, this);
        favoriteDao = new FavoriteDao(favoriteDaoConfig, this);

        registerDao(Article.class, articleDao);
        registerDao(Picture.class, pictureDao);
        registerDao(Favorite.class, favoriteDao);
    }
    
    public void clear() {
        articleDaoConfig.getIdentityScope().clear();
        pictureDaoConfig.getIdentityScope().clear();
        favoriteDaoConfig.getIdentityScope().clear();
    }

    public ArticleDao getArticleDao() {
        return articleDao;
    }

    public PictureDao getPictureDao() {
        return pictureDao;
    }

    public FavoriteDao getFavoriteDao() {
        return favoriteDao;
    }

}
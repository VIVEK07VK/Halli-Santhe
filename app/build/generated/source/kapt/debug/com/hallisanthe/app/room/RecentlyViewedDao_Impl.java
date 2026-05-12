package com.hallisanthe.app.room;

import android.database.Cursor;
import androidx.annotation.NonNull;
import androidx.room.CoroutinesRoom;
import androidx.room.EntityDeletionOrUpdateAdapter;
import androidx.room.EntityInsertionAdapter;
import androidx.room.RoomDatabase;
import androidx.room.RoomSQLiteQuery;
import androidx.room.SharedSQLiteStatement;
import androidx.room.util.CursorUtil;
import androidx.room.util.DBUtil;
import androidx.sqlite.db.SupportSQLiteStatement;
import com.hallisanthe.app.models.RecentlyViewedProduct;
import java.lang.Class;
import java.lang.Exception;
import java.lang.Object;
import java.lang.Override;
import java.lang.String;
import java.lang.SuppressWarnings;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.Callable;
import javax.annotation.processing.Generated;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlinx.coroutines.flow.Flow;

@Generated("androidx.room.RoomProcessor")
@SuppressWarnings({"unchecked", "deprecation"})
public final class RecentlyViewedDao_Impl implements RecentlyViewedDao {
  private final RoomDatabase __db;

  private final EntityInsertionAdapter<RecentlyViewedProduct> __insertionAdapterOfRecentlyViewedProduct;

  private final EntityDeletionOrUpdateAdapter<RecentlyViewedProduct> __deletionAdapterOfRecentlyViewedProduct;

  private final SharedSQLiteStatement __preparedStmtOfClearAll;

  public RecentlyViewedDao_Impl(@NonNull final RoomDatabase __db) {
    this.__db = __db;
    this.__insertionAdapterOfRecentlyViewedProduct = new EntityInsertionAdapter<RecentlyViewedProduct>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "INSERT OR REPLACE INTO `recently_viewed` (`productId`,`name`,`imageUrl`,`price`,`category`,`timestamp`) VALUES (?,?,?,?,?,?)";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          @NonNull final RecentlyViewedProduct entity) {
        if (entity.getProductId() == null) {
          statement.bindNull(1);
        } else {
          statement.bindString(1, entity.getProductId());
        }
        if (entity.getName() == null) {
          statement.bindNull(2);
        } else {
          statement.bindString(2, entity.getName());
        }
        if (entity.getImageUrl() == null) {
          statement.bindNull(3);
        } else {
          statement.bindString(3, entity.getImageUrl());
        }
        statement.bindDouble(4, entity.getPrice());
        if (entity.getCategory() == null) {
          statement.bindNull(5);
        } else {
          statement.bindString(5, entity.getCategory());
        }
        statement.bindLong(6, entity.getTimestamp());
      }
    };
    this.__deletionAdapterOfRecentlyViewedProduct = new EntityDeletionOrUpdateAdapter<RecentlyViewedProduct>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "DELETE FROM `recently_viewed` WHERE `productId` = ?";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          @NonNull final RecentlyViewedProduct entity) {
        if (entity.getProductId() == null) {
          statement.bindNull(1);
        } else {
          statement.bindString(1, entity.getProductId());
        }
      }
    };
    this.__preparedStmtOfClearAll = new SharedSQLiteStatement(__db) {
      @Override
      @NonNull
      public String createQuery() {
        final String _query = "DELETE FROM recently_viewed";
        return _query;
      }
    };
  }

  @Override
  public Object insertProduct(final RecentlyViewedProduct product,
      final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        __db.beginTransaction();
        try {
          __insertionAdapterOfRecentlyViewedProduct.insert(product);
          __db.setTransactionSuccessful();
          return Unit.INSTANCE;
        } finally {
          __db.endTransaction();
        }
      }
    }, $completion);
  }

  @Override
  public Object deleteProduct(final RecentlyViewedProduct product,
      final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        __db.beginTransaction();
        try {
          __deletionAdapterOfRecentlyViewedProduct.handle(product);
          __db.setTransactionSuccessful();
          return Unit.INSTANCE;
        } finally {
          __db.endTransaction();
        }
      }
    }, $completion);
  }

  @Override
  public Object clearAll(final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        final SupportSQLiteStatement _stmt = __preparedStmtOfClearAll.acquire();
        try {
          __db.beginTransaction();
          try {
            _stmt.executeUpdateDelete();
            __db.setTransactionSuccessful();
            return Unit.INSTANCE;
          } finally {
            __db.endTransaction();
          }
        } finally {
          __preparedStmtOfClearAll.release(_stmt);
        }
      }
    }, $completion);
  }

  @Override
  public Flow<List<RecentlyViewedProduct>> getRecentProducts() {
    final String _sql = "SELECT * FROM recently_viewed ORDER BY timestamp DESC LIMIT 20";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    return CoroutinesRoom.createFlow(__db, false, new String[] {"recently_viewed"}, new Callable<List<RecentlyViewedProduct>>() {
      @Override
      @NonNull
      public List<RecentlyViewedProduct> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfProductId = CursorUtil.getColumnIndexOrThrow(_cursor, "productId");
          final int _cursorIndexOfName = CursorUtil.getColumnIndexOrThrow(_cursor, "name");
          final int _cursorIndexOfImageUrl = CursorUtil.getColumnIndexOrThrow(_cursor, "imageUrl");
          final int _cursorIndexOfPrice = CursorUtil.getColumnIndexOrThrow(_cursor, "price");
          final int _cursorIndexOfCategory = CursorUtil.getColumnIndexOrThrow(_cursor, "category");
          final int _cursorIndexOfTimestamp = CursorUtil.getColumnIndexOrThrow(_cursor, "timestamp");
          final List<RecentlyViewedProduct> _result = new ArrayList<RecentlyViewedProduct>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final RecentlyViewedProduct _item;
            final String _tmpProductId;
            if (_cursor.isNull(_cursorIndexOfProductId)) {
              _tmpProductId = null;
            } else {
              _tmpProductId = _cursor.getString(_cursorIndexOfProductId);
            }
            final String _tmpName;
            if (_cursor.isNull(_cursorIndexOfName)) {
              _tmpName = null;
            } else {
              _tmpName = _cursor.getString(_cursorIndexOfName);
            }
            final String _tmpImageUrl;
            if (_cursor.isNull(_cursorIndexOfImageUrl)) {
              _tmpImageUrl = null;
            } else {
              _tmpImageUrl = _cursor.getString(_cursorIndexOfImageUrl);
            }
            final double _tmpPrice;
            _tmpPrice = _cursor.getDouble(_cursorIndexOfPrice);
            final String _tmpCategory;
            if (_cursor.isNull(_cursorIndexOfCategory)) {
              _tmpCategory = null;
            } else {
              _tmpCategory = _cursor.getString(_cursorIndexOfCategory);
            }
            final long _tmpTimestamp;
            _tmpTimestamp = _cursor.getLong(_cursorIndexOfTimestamp);
            _item = new RecentlyViewedProduct(_tmpProductId,_tmpName,_tmpImageUrl,_tmpPrice,_tmpCategory,_tmpTimestamp);
            _result.add(_item);
          }
          return _result;
        } finally {
          _cursor.close();
        }
      }

      @Override
      protected void finalize() {
        _statement.release();
      }
    });
  }

  @NonNull
  public static List<Class<?>> getRequiredConverters() {
    return Collections.emptyList();
  }
}

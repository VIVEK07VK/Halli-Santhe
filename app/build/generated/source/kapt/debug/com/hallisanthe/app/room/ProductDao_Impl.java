package com.hallisanthe.app.room;

import android.database.Cursor;
import androidx.annotation.NonNull;
import androidx.room.CoroutinesRoom;
import androidx.room.EntityInsertionAdapter;
import androidx.room.RoomDatabase;
import androidx.room.RoomSQLiteQuery;
import androidx.room.util.CursorUtil;
import androidx.room.util.DBUtil;
import androidx.sqlite.db.SupportSQLiteStatement;
import com.hallisanthe.app.models.Product;
import java.lang.Class;
import java.lang.Exception;
import java.lang.Integer;
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
public final class ProductDao_Impl implements ProductDao {
  private final RoomDatabase __db;

  private final EntityInsertionAdapter<Product> __insertionAdapterOfProduct;

  public ProductDao_Impl(@NonNull final RoomDatabase __db) {
    this.__db = __db;
    this.__insertionAdapterOfProduct = new EntityInsertionAdapter<Product>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "INSERT OR REPLACE INTO `products` (`id`,`name`,`description`,`price`,`stock`,`category`,`sellerName`,`sellerId`,`imageUrl`,`unit`,`rating`,`discountPercent`,`tag`,`isFavorite`,`deliveryTime`) VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          @NonNull final Product entity) {
        if (entity.getId() == null) {
          statement.bindNull(1);
        } else {
          statement.bindString(1, entity.getId());
        }
        if (entity.getName() == null) {
          statement.bindNull(2);
        } else {
          statement.bindString(2, entity.getName());
        }
        if (entity.getDescription() == null) {
          statement.bindNull(3);
        } else {
          statement.bindString(3, entity.getDescription());
        }
        statement.bindDouble(4, entity.getPrice());
        statement.bindLong(5, entity.getStock());
        if (entity.getCategory() == null) {
          statement.bindNull(6);
        } else {
          statement.bindString(6, entity.getCategory());
        }
        if (entity.getSellerName() == null) {
          statement.bindNull(7);
        } else {
          statement.bindString(7, entity.getSellerName());
        }
        if (entity.getSellerId() == null) {
          statement.bindNull(8);
        } else {
          statement.bindString(8, entity.getSellerId());
        }
        if (entity.getImageUrl() == null) {
          statement.bindNull(9);
        } else {
          statement.bindString(9, entity.getImageUrl());
        }
        if (entity.getUnit() == null) {
          statement.bindNull(10);
        } else {
          statement.bindString(10, entity.getUnit());
        }
        statement.bindDouble(11, entity.getRating());
        if (entity.getDiscountPercent() == null) {
          statement.bindNull(12);
        } else {
          statement.bindLong(12, entity.getDiscountPercent());
        }
        if (entity.getTag() == null) {
          statement.bindNull(13);
        } else {
          statement.bindString(13, entity.getTag());
        }
        final int _tmp = entity.isFavorite() ? 1 : 0;
        statement.bindLong(14, _tmp);
        if (entity.getDeliveryTime() == null) {
          statement.bindNull(15);
        } else {
          statement.bindString(15, entity.getDeliveryTime());
        }
      }
    };
  }

  @Override
  public Object insertProducts(final List<Product> products,
      final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        __db.beginTransaction();
        try {
          __insertionAdapterOfProduct.insert(products);
          __db.setTransactionSuccessful();
          return Unit.INSTANCE;
        } finally {
          __db.endTransaction();
        }
      }
    }, $completion);
  }

  @Override
  public Flow<List<Product>> getAllProducts() {
    final String _sql = "SELECT * FROM products";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    return CoroutinesRoom.createFlow(__db, false, new String[] {"products"}, new Callable<List<Product>>() {
      @Override
      @NonNull
      public List<Product> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfName = CursorUtil.getColumnIndexOrThrow(_cursor, "name");
          final int _cursorIndexOfDescription = CursorUtil.getColumnIndexOrThrow(_cursor, "description");
          final int _cursorIndexOfPrice = CursorUtil.getColumnIndexOrThrow(_cursor, "price");
          final int _cursorIndexOfStock = CursorUtil.getColumnIndexOrThrow(_cursor, "stock");
          final int _cursorIndexOfCategory = CursorUtil.getColumnIndexOrThrow(_cursor, "category");
          final int _cursorIndexOfSellerName = CursorUtil.getColumnIndexOrThrow(_cursor, "sellerName");
          final int _cursorIndexOfSellerId = CursorUtil.getColumnIndexOrThrow(_cursor, "sellerId");
          final int _cursorIndexOfImageUrl = CursorUtil.getColumnIndexOrThrow(_cursor, "imageUrl");
          final int _cursorIndexOfUnit = CursorUtil.getColumnIndexOrThrow(_cursor, "unit");
          final int _cursorIndexOfRating = CursorUtil.getColumnIndexOrThrow(_cursor, "rating");
          final int _cursorIndexOfDiscountPercent = CursorUtil.getColumnIndexOrThrow(_cursor, "discountPercent");
          final int _cursorIndexOfTag = CursorUtil.getColumnIndexOrThrow(_cursor, "tag");
          final int _cursorIndexOfIsFavorite = CursorUtil.getColumnIndexOrThrow(_cursor, "isFavorite");
          final int _cursorIndexOfDeliveryTime = CursorUtil.getColumnIndexOrThrow(_cursor, "deliveryTime");
          final List<Product> _result = new ArrayList<Product>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final Product _item;
            final String _tmpId;
            if (_cursor.isNull(_cursorIndexOfId)) {
              _tmpId = null;
            } else {
              _tmpId = _cursor.getString(_cursorIndexOfId);
            }
            final String _tmpName;
            if (_cursor.isNull(_cursorIndexOfName)) {
              _tmpName = null;
            } else {
              _tmpName = _cursor.getString(_cursorIndexOfName);
            }
            final String _tmpDescription;
            if (_cursor.isNull(_cursorIndexOfDescription)) {
              _tmpDescription = null;
            } else {
              _tmpDescription = _cursor.getString(_cursorIndexOfDescription);
            }
            final double _tmpPrice;
            _tmpPrice = _cursor.getDouble(_cursorIndexOfPrice);
            final int _tmpStock;
            _tmpStock = _cursor.getInt(_cursorIndexOfStock);
            final String _tmpCategory;
            if (_cursor.isNull(_cursorIndexOfCategory)) {
              _tmpCategory = null;
            } else {
              _tmpCategory = _cursor.getString(_cursorIndexOfCategory);
            }
            final String _tmpSellerName;
            if (_cursor.isNull(_cursorIndexOfSellerName)) {
              _tmpSellerName = null;
            } else {
              _tmpSellerName = _cursor.getString(_cursorIndexOfSellerName);
            }
            final String _tmpSellerId;
            if (_cursor.isNull(_cursorIndexOfSellerId)) {
              _tmpSellerId = null;
            } else {
              _tmpSellerId = _cursor.getString(_cursorIndexOfSellerId);
            }
            final String _tmpImageUrl;
            if (_cursor.isNull(_cursorIndexOfImageUrl)) {
              _tmpImageUrl = null;
            } else {
              _tmpImageUrl = _cursor.getString(_cursorIndexOfImageUrl);
            }
            final String _tmpUnit;
            if (_cursor.isNull(_cursorIndexOfUnit)) {
              _tmpUnit = null;
            } else {
              _tmpUnit = _cursor.getString(_cursorIndexOfUnit);
            }
            final double _tmpRating;
            _tmpRating = _cursor.getDouble(_cursorIndexOfRating);
            final Integer _tmpDiscountPercent;
            if (_cursor.isNull(_cursorIndexOfDiscountPercent)) {
              _tmpDiscountPercent = null;
            } else {
              _tmpDiscountPercent = _cursor.getInt(_cursorIndexOfDiscountPercent);
            }
            final String _tmpTag;
            if (_cursor.isNull(_cursorIndexOfTag)) {
              _tmpTag = null;
            } else {
              _tmpTag = _cursor.getString(_cursorIndexOfTag);
            }
            final boolean _tmpIsFavorite;
            final int _tmp;
            _tmp = _cursor.getInt(_cursorIndexOfIsFavorite);
            _tmpIsFavorite = _tmp != 0;
            final String _tmpDeliveryTime;
            if (_cursor.isNull(_cursorIndexOfDeliveryTime)) {
              _tmpDeliveryTime = null;
            } else {
              _tmpDeliveryTime = _cursor.getString(_cursorIndexOfDeliveryTime);
            }
            _item = new Product(_tmpId,_tmpName,_tmpDescription,_tmpPrice,_tmpStock,_tmpCategory,_tmpSellerName,_tmpSellerId,_tmpImageUrl,_tmpUnit,_tmpRating,_tmpDiscountPercent,_tmpTag,_tmpIsFavorite,_tmpDeliveryTime);
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

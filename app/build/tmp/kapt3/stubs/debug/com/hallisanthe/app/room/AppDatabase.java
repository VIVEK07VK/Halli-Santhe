package com.hallisanthe.app.room;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000*\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\b\'\u0018\u00002\u00020\u0001B\u0005\u00a2\u0006\u0002\u0010\u0002J\b\u0010\u0003\u001a\u00020\u0004H&J\b\u0010\u0005\u001a\u00020\u0006H&J\b\u0010\u0007\u001a\u00020\bH&J\b\u0010\t\u001a\u00020\nH&J\b\u0010\u000b\u001a\u00020\fH&\u00a8\u0006\r"}, d2 = {"Lcom/hallisanthe/app/room/AppDatabase;", "Landroidx/room/RoomDatabase;", "()V", "cartDao", "Lcom/hallisanthe/app/room/CartDao;", "favoriteDao", "Lcom/hallisanthe/app/room/FavoriteDao;", "productDao", "Lcom/hallisanthe/app/room/ProductDao;", "recentSearchDao", "Lcom/hallisanthe/app/room/RecentSearchDao;", "recentlyViewedDao", "Lcom/hallisanthe/app/room/RecentlyViewedDao;", "app_debug"})
@androidx.room.Database(entities = {com.hallisanthe.app.models.Product.class, com.hallisanthe.app.models.CartItem.class, com.hallisanthe.app.models.FavoriteEntity.class, com.hallisanthe.app.models.RecentlyViewedProduct.class, com.hallisanthe.app.room.RecentSearchEntity.class}, version = 4, exportSchema = false)
public abstract class AppDatabase extends androidx.room.RoomDatabase {
    
    public AppDatabase() {
        super();
    }
    
    @org.jetbrains.annotations.NotNull()
    public abstract com.hallisanthe.app.room.ProductDao productDao();
    
    @org.jetbrains.annotations.NotNull()
    public abstract com.hallisanthe.app.room.CartDao cartDao();
    
    @org.jetbrains.annotations.NotNull()
    public abstract com.hallisanthe.app.room.FavoriteDao favoriteDao();
    
    @org.jetbrains.annotations.NotNull()
    public abstract com.hallisanthe.app.room.RecentlyViewedDao recentlyViewedDao();
    
    @org.jetbrains.annotations.NotNull()
    public abstract com.hallisanthe.app.room.RecentSearchDao recentSearchDao();
}
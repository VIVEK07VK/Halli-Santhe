package com.hallisanthe.app.room;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000.\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010\b\n\u0000\bg\u0018\u00002\u00020\u0001J\u0016\u0010\u0002\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u0005H\u00a7@\u00a2\u0006\u0002\u0010\u0006J\u0014\u0010\u0007\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\n0\t0\bH\'J\u0016\u0010\u000b\u001a\u00020\u00032\u0006\u0010\f\u001a\u00020\nH\u00a7@\u00a2\u0006\u0002\u0010\rJ\u0016\u0010\u000e\u001a\u00020\u000f2\u0006\u0010\u0004\u001a\u00020\u0005H\u00a7@\u00a2\u0006\u0002\u0010\u0006\u00a8\u0006\u0010"}, d2 = {"Lcom/hallisanthe/app/room/FavoriteDao;", "", "deleteFavoriteById", "", "productId", "", "(Ljava/lang/String;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "getAllFavorites", "Lkotlinx/coroutines/flow/Flow;", "", "Lcom/hallisanthe/app/models/FavoriteEntity;", "insertFavorite", "favorite", "(Lcom/hallisanthe/app/models/FavoriteEntity;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "isFavorite", "", "app_debug"})
@androidx.room.Dao()
public abstract interface FavoriteDao {
    
    @androidx.room.Query(value = "SELECT * FROM favorites")
    @org.jetbrains.annotations.NotNull()
    public abstract kotlinx.coroutines.flow.Flow<java.util.List<com.hallisanthe.app.models.FavoriteEntity>> getAllFavorites();
    
    @androidx.room.Insert(onConflict = 1)
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object insertFavorite(@org.jetbrains.annotations.NotNull()
    com.hallisanthe.app.models.FavoriteEntity favorite, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion);
    
    @androidx.room.Query(value = "DELETE FROM favorites WHERE productId = :productId")
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object deleteFavoriteById(@org.jetbrains.annotations.NotNull()
    java.lang.String productId, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion);
    
    @androidx.room.Query(value = "SELECT COUNT(*) FROM favorites WHERE productId = :productId")
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object isFavorite(@org.jetbrains.annotations.NotNull()
    java.lang.String productId, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super java.lang.Integer> $completion);
}
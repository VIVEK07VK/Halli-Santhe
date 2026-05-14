package com.hallisanthe.app.models;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000\f\n\u0002\u0018\u0002\n\u0002\u0010\u0010\n\u0002\b\u000b\b\u0086\u0081\u0002\u0018\u00002\b\u0012\u0004\u0012\u00020\u00000\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002j\u0002\b\u0003j\u0002\b\u0004j\u0002\b\u0005j\u0002\b\u0006j\u0002\b\u0007j\u0002\b\bj\u0002\b\tj\u0002\b\nj\u0002\b\u000b\u00a8\u0006\f"}, d2 = {"Lcom/hallisanthe/app/models/OrderStatus;", "", "(Ljava/lang/String;I)V", "PENDING", "WAITING_CONFIRMATION", "ACCEPTED", "PREPARING", "READY_FOR_PICKUP", "OUT_FOR_DELIVERY", "DELIVERED", "CANCELLED", "REJECTED", "app_debug"})
public enum OrderStatus {
    /*public static final*/ PENDING /* = new PENDING() */,
    /*public static final*/ WAITING_CONFIRMATION /* = new WAITING_CONFIRMATION() */,
    /*public static final*/ ACCEPTED /* = new ACCEPTED() */,
    /*public static final*/ PREPARING /* = new PREPARING() */,
    /*public static final*/ READY_FOR_PICKUP /* = new READY_FOR_PICKUP() */,
    /*public static final*/ OUT_FOR_DELIVERY /* = new OUT_FOR_DELIVERY() */,
    /*public static final*/ DELIVERED /* = new DELIVERED() */,
    /*public static final*/ CANCELLED /* = new CANCELLED() */,
    /*public static final*/ REJECTED /* = new REJECTED() */;
    
    OrderStatus() {
    }
    
    @org.jetbrains.annotations.NotNull()
    public static kotlin.enums.EnumEntries<com.hallisanthe.app.models.OrderStatus> getEntries() {
        return null;
    }
}
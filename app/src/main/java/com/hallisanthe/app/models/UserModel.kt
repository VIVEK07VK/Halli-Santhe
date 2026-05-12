package com.hallisanthe.app.models

import com.google.firebase.Timestamp

enum class UserRole { BUYER, SELLER }

data class UserModel(
    val uid: String = "",
    val fullName: String = "",
    val email: String = "",
    val phone: String = "",
    val role: String = UserRole.BUYER.name,
    val shopName: String = "",
    val businessAddress: String = "",
    val villageName: String = "",
    val createdAt: Timestamp = Timestamp.now(),
    val updatedAt: Timestamp? = null,
    val profileImageUrl: String = ""
) {
    /** Firestore-safe map (avoids null issues with serialization) */
    fun toMap(): Map<String, Any?> = mapOf(
        "uid"             to uid,
        "fullName"        to fullName,
        "email"           to email,
        "phone"           to phone,
        "role"            to role,
        "shopName"        to shopName,
        "businessAddress" to businessAddress,
        "villageName"     to villageName,
        "createdAt"       to createdAt,
        "updatedAt"       to updatedAt,
        "profileImageUrl" to profileImageUrl
    )

    companion object {
        fun fromMap(map: Map<String, Any?>): UserModel = UserModel(
            uid             = map["uid"] as? String ?: "",
            fullName        = map["fullName"] as? String ?: map["name"] as? String ?: "",
            email           = map["email"] as? String ?: "",
            phone           = map["phone"] as? String ?: "",
            role            = map["role"] as? String ?: UserRole.BUYER.name,
            shopName        = map["shopName"] as? String ?: "",
            businessAddress = map["businessAddress"] as? String ?: "",
            villageName     = map["villageName"] as? String ?: "",
            createdAt       = map["createdAt"] as? Timestamp ?: Timestamp.now(),
            updatedAt       = map["updatedAt"] as? Timestamp,
            profileImageUrl = map["profileImageUrl"] as? String ?: ""
        )
    }
}

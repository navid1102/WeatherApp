package com.android.weathercore.customs

import android.content.Context
import android.content.SharedPreferences

open class MyShared(var context: Context) {


    open fun setShared(key: String, value: Any?) {
        val userInfo: SharedPreferences =
            context.getSharedPreferences("login", Context.MODE_PRIVATE)
        val edit = userInfo.edit()
        when (value) {
            is String? -> edit.putString(key, value)
            is Int -> edit.putInt(key, value)
            is Boolean -> edit.putBoolean(key, value)
            is Float -> edit.putFloat(key, value)
            is Long -> edit.putLong(key, value)
            else -> throw UnsupportedOperationException("Not yet implemented") as Throwable
        }
        edit.apply()
    }

    open fun getShared(): SharedPreferences {

        return context.getSharedPreferences("login", 0)

    }

    fun clean() {

        apply {
            client_id = "IDMS"
            refreshToken = null
            merchantId = null
            merchantNumber = null
            lastMessageId = null
            memberID = null
            merchantBranchId = null
            employeeNO = null
            PoseType = null
            MerchantName = null
            userName = null
            name = null
            lastname = null
            memberShipNO = null
            token = null
            gender = null
            firstTimeFucose = null
            firstTimeFucose = null
            isNationalCode = false
            smsOtp = false
            emailOtp = false
            hours = 0L
            isLogIn = false
            accountId = null
        }
    }


    fun clearNationalCode() {
//        apply {
//            isNationalCode = false
//        }
    }

    var lastMessageId: String?
        get() = getShared().getString("lastMessageId", null)
        set(value) = setShared("lastMessageId", value)

    var email: String?
        get() = getShared().getString("email", "null")
        set(value) = setShared("email", value)
    var client_id: String?
        get() = getShared().getString("client_id", "null")
        set(value) = setShared("client_id", value)

    var refreshToken: String?
        get() = getShared().getString("refreshToken", null)
        set(value) = setShared("refreshToken", value)

    var merchantId: String?
        get() = getShared().getString("merchantId", null)
        set(value) = setShared("merchantId", value)
    var merchantNumber: String?
        get() = getShared().getString("merchantNumber", null)
        set(value) = setShared("merchantNumber", value)

    var memberID: String?
        get() = getShared().getString("memberID", null)
        set(value) = setShared("memberID", value)

    var memberShipNO: String?
        get() = getShared().getString("memberShipNO", null)
        set(value) = setShared("memberShipNO", value)

    var merchantBranchId: String?
        get() = getShared().getString("merchantBranchId", null)
        set(value) = setShared("merchantBranchId", value)

    var employeeNO: String?
        get() = getShared().getString("EmployeeNO", null)
        set(value) = setShared("EmployeeNO", value)

    var PoseType: String?
        get() = getShared().getString("setPoseType", null)
        set(value) = setShared("setPoseType", value)

    var MerchantName: String?
        get() = getShared().getString("MerchantName", null)
        set(value) = setShared("MerchantName", value)


    var userName: String?
        get() = getShared().getString("userName", null)
        set(value) = setShared("userName", value)

    var name: String?
        get() {
            return if (lastname!!.isNotEmpty())
                getShared().getString("name", "")
            else
                getShared().getString("name", "مانی‌بانی")
        }
        set(value) = setShared("name", value)

    var lastname: String?
        get() = getShared().getString("lastname", "")
        set(value) = setShared("lastname", value)

    var token: String?
        get() = getShared().getString("token", null)
        set(value) = setShared("token", value)


    var firstTimeFucose: Boolean?
        get() = getShared().getBoolean("firstTimeFucose", false)
        set(value) = setShared("firstTimeFucose", value)

    var phoneNumber: String?
        get() = getShared().getString("phoneNumber", "")
        set(value) = setShared("phoneNumber", value)

    var personId: String?
        get() = getShared().getString("personId", "")
        set(value) = setShared("personId", value)

    var gender: String?
        get() = getShared().getString("gender", "")
        set(value) = setShared("gender", value)

    /**
     * 1->blue
     * 2->silver
     * 3->gold
     * 4->platinum
     */
    var userLevel:Int?
        get() = getShared().getInt("level", 1)
        set(value) = setShared("level", value)


    var prefredUserName: String?
        get() = getShared().getString("prefreedUserName", "")
        set(value) = setShared("prefreedUserName", value)

    var isNationalCode: Boolean?
        get() = getShared().getBoolean("isNationalCode", false)
        set(value) = setShared("isNationalCode", value)

    var CheckFinger: Boolean?
        get() = getShared().getBoolean("CheckFinger", false)
        set(value) = setShared("CheckFinger", value)

    var passwordFinger: String?
        get() = getShared().getString("passwordFinger", "")
        set(value) = setShared("passwordFinger", value)

    var passwordSave: String?
        get() = getShared().getString("passwordSave", "")
        set(value) = setShared("passwordSave", value)

    var Checkpass: Boolean?
        get() = getShared().getBoolean("Checkpass", false)
        set(value) = setShared("Checkpass", value)

    var showIntro: Boolean?
        get() = getShared().getBoolean("showIntro", true)
        set(value) = setShared("showIntro", value)

    var carAgreement: Boolean?
        get() = getShared().getBoolean("carAgreement", false)
        set(value) = setShared("carAgreement", value)


    var smsOtp: Boolean?
        get() = getShared().getBoolean("smsOtp", false)
        set(value) = setShared("smsOtp", value)


    var emailOtp: Boolean?
        get() = getShared().getBoolean("emailOtp", false)
        set(value) = setShared("emailOtp", value)

    var hours: Long?
        get() = getShared().getLong("hours", 0L)
        set(value) = setShared("hours", value)
    var isLogIn: Boolean
        get() = getShared().getBoolean("isLogIn", false)
        set(value) = setShared("isLogIn", value)


    var checkPermisionAsk: Boolean
        get() = getShared().getBoolean("checkPermisionAsk", false)
        set(value) = setShared("checkPermisionAsk", value)

    var accountId: String?
        get() = getShared().getString("accountId", "")
        set(value) = setShared("accountId", value)

}





package com.navid.weatherapp.module.baseView

import android.view.View
import androidx.databinding.BaseObservable
import androidx.databinding.Bindable
import androidx.databinding.library.baseAdapters.BR


open class BaseModel : BaseObservable() {

    private var onclick: (() -> Unit)? = null


    @Bindable
    var mainLoading: Boolean? = false
        set(value) {
            value.let {
                field = it
            notifyPropertyChanged(BR.mainLoading)
            }
        }

    @Bindable
    var showLoading: Boolean? = false
        set(value) {
            value.let {
                field = it
                notifyPropertyChanged(BR.showLoading)
            }
        }

    @Bindable
    var buttonText: String? = ""
        set(value) {
            value.let {
                field = it
                notifyPropertyChanged(BR.buttonText)
            }
        }

    @Bindable
    var onButtonClicked: View.OnClickListener? = null
        set(value) {
            value.let {
                field = it
                 notifyPropertyChanged(BR.onButtonClicked)
            }
        }


    var errorText: String = ""
        @Bindable get() = if (field.isEmpty()) "Loading..." else field
        set(value) {
            value.let {
                field = it
                notifyPropertyChanged(BR.errorText)
            }
        }


    @Bindable
    var recivedOtpMessage: String? = ""
        set(value) {
            value.let {
                field = it
                notifyPropertyChanged(BR.recivedOtpMessage)
            }
        }

    var staionNames: ArrayList<String>? = null
        @Bindable get() = field

    fun showError(buttonText: String? = "تلاش مجدد", errorText: String, onclick: (() -> Unit)?) {
        this.onclick = onclick
        mainLoading = true
        showLoading = false
        this.buttonText = buttonText
        this.errorText = errorText
        onButtonClicked = View.OnClickListener { onclick!!() }

    }



    fun showLoading(buttonText: String = "", errorText: String = "") {
        mainLoading = true
        this.buttonText = buttonText
        showLoading = true
        if (errorText == ""){
            this.errorText = "در حال بارگذاری"
        }
        this.errorText = errorText
    }

    fun stopLoading() {
        mainLoading = false
        onclick = null
        showLoading = false

    }


}
package com.navid.weatherapp.module

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.graphics.drawable.Drawable
import android.os.Build
import android.os.Bundle
import android.text.Layout.JUSTIFICATION_MODE_INTER_WORD
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.widget.Button
import android.widget.CheckBox
import android.widget.CompoundButton
import android.widget.LinearLayout
import androidx.appcompat.widget.AppCompatCheckBox
import androidx.appcompat.widget.AppCompatImageView
import androidx.appcompat.widget.AppCompatTextView
import androidx.core.content.ContextCompat
import androidx.fragment.app.DialogFragment
import com.google.android.material.button.MaterialButton
import com.navid.weatherapp.R


class WeatherDialog : DialogFragment() {

    var title: AppCompatTextView? = null
    var text: AppCompatTextView? = null
    var btnleft: Button? = null
    var btncentr: Button? = null
    var btnright: Button? = null
    var icone: AppCompatImageView? = null


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.dialog_weather, container)

        this.title = view.findViewById(R.id.title)
        this.text = view.findViewById(R.id.text)
        this.btnleft = view.findViewById(R.id.leftBtn)
        this.btncentr = view.findViewById(R.id.centerBtn)
        this.btnright = view.findViewById(R.id.rightBtn)
        this.icone = view.findViewById(R.id.icon)


        if (dialog != null && dialog?.window != null) {
            dialog?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            dialog?.window?.requestFeature(Window.FEATURE_NO_TITLE)
            isCancelable =false


        }
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        when {

            arguments?.getInt("type") == 2 -> {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    text?.justificationMode = JUSTIFICATION_MODE_INTER_WORD
                }
                title?.text = arguments?.getString("title")
                text?.text = arguments?.getString("message")
                btnleft!!.text = arguments?.getString("titleBtn1")
                isCancelable = arguments?.getBoolean("isclose")!!

                context?.let {
                    text?.setTextColor(ContextCompat.getColor(it, arguments?.getInt("colorText")!!))
                }
                btnright!!.text = arguments?.getString("titleBtn2")
                icone?.setImageResource(arguments?.getInt("icon") ?: 0)
                // drawable = AppCompatResources.getDrawable(context!!, arguments?.getInt("icon")!!)
                //   icone!!.setImageDrawable(drawable)
                btnleft?.setOnClickListener {

                    action1()
                    dismiss()
                }
                btnright?.setOnClickListener {
                    action2()
                    dismiss()
                }
                btncentr?.visibility = View.GONE
                // title!!.visibility=View.GONE

            }

        }

    }

    companion object {
        lateinit var action1: (() -> Unit)
        lateinit var action2: (() -> Unit)
        lateinit var action3: (() -> Unit)
        lateinit var action4: (() -> Unit)

        fun alertNormal2Btn(
            icon: Int,
            title: String,
            message: String,
            tittleBtn1: String,
            titleBtn2: String,
            colorText: Int,
            isClose:Boolean,
            actionA: (() -> Unit),
            actionB: (() -> Unit)
        ): WeatherDialog {
            action1 = actionA
            action2 = actionB
            val myFragment = WeatherDialog()
            val args = Bundle()
            args.putInt("type", 2)
            args.putString("title", title)
            args.putString("message", message)
            args.putString("titleBtn1", tittleBtn1)
            args.putString("titleBtn2", titleBtn2)
            args.putInt("icon", icon)
            args.putInt("colorText", colorText)
            args.putBoolean("isclose",isClose)
            myFragment.arguments = args

            return myFragment
        }
        fun alertNormal(
            icon: Int,
            title: String,
            message: String,
            tittleBtn1: String,
            colorText: Int,
            isClose:Boolean,
            actionA: (() -> Unit)
        ): WeatherDialog {
            action1 = actionA
            val myFragment = WeatherDialog()
            val args = Bundle()
            args.putInt("type", 1)
            args.putString("title", title)
            args.putString("titleBtn1", tittleBtn1)
            args.putString("message", message)
            args.putInt("icon", icon)
            args.putInt("colorText", colorText)
            args.putBoolean("isclose",isClose)
            myFragment.arguments = args

            return myFragment
        }
    }






}
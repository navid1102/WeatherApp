package com.adtn.ghabzam.module.baseView

import android.content.Context
import android.graphics.Color
import android.graphics.Point
import android.graphics.Rect
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.MotionEvent
import android.view.View
import android.view.WindowManager
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import com.navid.weatherapp.R
import java.util.*

abstract class BaseActivity<B : ViewDataBinding> : AppCompatActivity() {
    companion object {
        val size = Point()
        var height = 0f
        var width = 0f

    }

    private fun setWindowFlag(bits: Int, on: Boolean) {
        val win = window
        val winParams = win.attributes
        if (on) {
            winParams.flags = winParams.flags or bits
        } else {
            winParams.flags = winParams.flags and bits.inv()
        }
        win.attributes = winParams
    }

    lateinit var binding: B
    // val res: Resources by inject()
    override fun onCreate(savedInstanceState: Bundle?) {
        val display = windowManager.defaultDisplay

        var currentTime=Calendar.getInstance().time.hours
        if (currentTime in 6..18){
            setTheme(R.style.AppThemeMorning)
        }else{
            setTheme(R.style.AppThemeEvening)
        }

        display.getSize(size)
        height = size.y.toFloat()
        width = size.x.toFloat()
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, getLayoutResourceId())
        oncreate()

        supportActionBar?.hide()

    }

    protected abstract fun getLayoutResourceId(): Int
    protected abstract fun oncreate()

    protected fun hideStatusBar(){

        // it is for gpu Ower Drawing
        //window.setBackgroundDrawable(null)

        Log.e("heightttt", "" + height)
        val handler = Handler()
        if (Build.VERSION.SDK_INT in 19..20) {
            setWindowFlag(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS, true)
        }
        if (Build.VERSION.SDK_INT >= 19) {
            window.decorView.systemUiVisibility =
                View.SYSTEM_UI_FLAG_LAYOUT_STABLE or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
        }
        if (Build.VERSION.SDK_INT >= 21) {
            setWindowFlag(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS, false)
            window.statusBarColor = Color.TRANSPARENT
        }
    }

    override fun dispatchTouchEvent(event: MotionEvent): Boolean {

        try {


            if (event.action == MotionEvent.ACTION_DOWN) {
                val v = currentFocus
                if (v is EditText) {
                    val outRect = Rect()
                    v.getGlobalVisibleRect(outRect)
                    if (!outRect.contains(event.rawX.toInt(), event.rawY.toInt())) {
                        v.clearFocus()
                        val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                        imm.hideSoftInputFromWindow(v.windowToken, 0)
                    }
                }
            }
        }catch (e:Exception){

        }
        return super.dispatchTouchEvent(event)
    }

    protected fun addFragment(fragment: Fragment, view: View) {
        val ft = supportFragmentManager.beginTransaction()
        ft.add(view.id, fragment)
        ft.addToBackStack(null)
        ft.commit()
    }
}
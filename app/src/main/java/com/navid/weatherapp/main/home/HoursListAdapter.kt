package com.navid.weatherapp.main.home

import android.os.Build
import androidx.annotation.RequiresApi
import com.android.weathercore.core.weather.pojo.Hourly
import com.mohamadamin.persianmaterialdatetimepicker.utils.PersianCalendar
import com.navid.weatherapp.R
import com.navid.weatherapp.databinding.ItemListHoursDataBinding
import com.navid.weatherapp.module.getDateTime
import com.navid.weatherapp.module.getHours
import com.navid.weatherapp.module.iconCodToImage
import java.lang.StringBuilder
import java.util.*
import kotlin.math.roundToInt
import kotlin.time.ExperimentalTime
import kotlin.time.hours

class HoursListAdapter(
    list: List<Hourly?>? = null
) : com.navid.weatherapp.module.baseView.BaseAdapter<Hourly, ItemListHoursDataBinding>(list) {

    override fun getLayoutResourceId(): Int {
        return R.layout.item_list_hours_data
    }

    @RequiresApi(Build.VERSION_CODES.O)
    @ExperimentalTime
    override fun onBindViewHolder(holder: ViewHolder<ItemListHoursDataBinding>, position: Int) {

        list.let {
            val context=holder.itemView.context

            binding.txtTemp.text= it?.get(position)?.temp?.roundToInt().toString()
            binding.imgIconWeather.setImageResource(iconCodToImage(it?.get(position)?.weather?.get(0)?.icon?:"01d",
            it?.get(position)?.dt?:0))

            binding.txtTime.text= (getDateTime((it?.get(position)?.dt?:0)*1000)!!.toString())
            //binding.txtTime.text= (getHours((it?.get(position)?.dt?:0)*1000)).toString()

        }
    }

    fun updateData(data: List<Hourly>) {
        list = data
        notifyDataSetChanged()
    }


}
package com.navid.weatherapp.main.home

import android.provider.Settings.Global.getString
import com.android.weathercore.core.weather.pojo.Daily
import com.mohamadamin.persianmaterialdatetimepicker.utils.PersianCalendar
import com.navid.weatherapp.R
import com.navid.weatherapp.databinding.ItemListDailyDataBinding
import com.navid.weatherapp.module.*
import java.util.*
import kotlin.collections.ArrayList
import kotlin.math.roundToInt
import kotlin.time.ExperimentalTime


class DayListAdapter( list: ArrayList<com.android.weathercore.core.weather.pojo.daily.Daily?>? = null
) : com.navid.weatherapp.module.baseView.BaseAdapter<com.android.weathercore.core.weather.pojo.daily.Daily, ItemListDailyDataBinding>(list){



    override fun getLayoutResourceId(): Int {
        return R.layout.item_list_daily_data
    }

    @ExperimentalTime
    override fun onBindViewHolder(holder: ViewHolder<ItemListDailyDataBinding>, position: Int) {
        list.let {
            val context=holder.itemView.context

            var cal= Calendar.getInstance()
            cal.timeInMillis = ((it!![position]?.dt)!!.toLong()) * 1000

            when (position) {
               0 -> {
                    binding.txtDay.text= context.getString(R.string.tomorrow)
                }
                else -> {
                    binding.txtDay.text= getDyOfWeek(cal.get(Calendar.DAY_OF_WEEK))
                }
            }

            binding.imgDailyIcon.setImageResource(getIconDaily(it!![position]?.weather!![0]?.icon))

            binding.txtDailyMaxTemp.text=it[position]!!.temp.min.roundToInt().toString()
            binding.txtDailyMinTemp.text="/ ${it[position]!!.temp.min.roundToInt().toString()}"








//            binding.txtDay.text= getDailyDate((it?.get(position)?.dt?:0))?.day.toString()

        }
    }



    fun updateData(data: ArrayList<com.android.weathercore.core.weather.pojo.daily.Daily>) {
        list = data
        notifyDataSetChanged()
    }


}
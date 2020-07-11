package com.adtn.ghabzam.module.baseView

import android.content.IntentFilter
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.OnLifecycleEvent
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import androidx.navigation.findNavController
import com.google.android.material.snackbar.Snackbar

abstract class BaseFragment<B : ViewDataBinding> : Fragment(), LifecycleObserver{
    lateinit var binding: B
    private var vieww: View? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // return super.onCreateView(inflater, container, savedInstanceState)

        if (vieww == null) {
            binding = DataBindingUtil.inflate(inflater, getLayoutResourceId(), container, false)

            vieww = binding.root

            oncreate()
        }



        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

    }



    protected abstract fun getLayoutResourceId(): Int
    protected abstract fun oncreate()


    open fun onBackPressed() = true





    fun showSnak(message: String) {
        Snackbar.make(binding.root, message, Snackbar.LENGTH_LONG).show()

    }

    fun showSnak(message: String, action: () -> Unit) {
        Snackbar.make(binding.root, message, Snackbar.LENGTH_INDEFINITE).setAction("ok") {
            action()

        }.show()

    }

    fun showSnak(message: String, actionTitle: String, action: () -> Unit) {
        Snackbar.make(binding.root, message, Snackbar.LENGTH_INDEFINITE).setAction(actionTitle) {
            action()
        }.show()
    }

    fun navigate(view: View, id: Int) {
        val navController = view.findNavController()
        navController.navigate(id)
    }

    override fun onResume() {
        super.onResume()
        //MainActivity.nearmeVisibility.hideNearMeView = false
    }




    var  modelCalback : ((String)->(Unit))? = null


    fun calll(modelCalback : ((String)->(Unit))?){
        this.modelCalback = modelCalback
    }

    override fun onDestroy() {
        super.onDestroy()

    }



}
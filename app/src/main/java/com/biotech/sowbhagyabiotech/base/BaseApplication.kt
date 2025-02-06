package com.biotech.sowbhagyabiotech.base

import android.app.Activity
import android.app.Application
import android.content.Context
import android.graphics.Bitmap
import android.os.Handler
import android.os.Looper
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.ProcessLifecycleOwner
import com.biotech.sowbhagyabiotech.roomdb.AppRoomDataBase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob
import java.lang.ref.WeakReference


/**
 * Base application class to manage basic things like current language, app background / foreground etc
 */
class BaseApplication : Application(), LifecycleEventObserver {



    init {
        appContext = WeakReference(this)
    }

    companion object {
        var IS_APP_IN_FOREGROUND = false
        var TOOLBAR_WORK_ORDER_SEARCH_ENABLED: Boolean = false
        var TOOLBAR_WORK_REQUEST_SEARCH_ENABLED: Boolean = false
        var SIGNATURE: Bitmap? = null
        var USER_NAME: String = ""
        var USER_PROFILE_PIC: String = ""
        var appContext: WeakReference<BaseApplication>? = null
        fun applicationContext(): Context? {
            return appContext!!.get()
        }
    }

    val applicationScope = CoroutineScope(SupervisorJob())
    lateinit var appRoomDataBase: AppRoomDataBase
    lateinit var handler: Handler
    override fun onCreate() {
        super.onCreate()
        // Branch logging for debugging
        handler = Handler(Looper.myLooper()!!)

        ProcessLifecycleOwner.get().lifecycle.addObserver(this)
        /*   val appRoomDataBase by lazy {
               Room.databaseBuilder(
                   applicationContext,
                   AppRoomDataBase::class.java, "farma-database"
               ).build()

           }*/
        val appRoomDataBase: AppRoomDataBase by lazy {
            AppRoomDataBase.getInstance(this)
        }
        this.appRoomDataBase = appRoomDataBase
    }

    private var mCurrentActivity: Activity? = null
    private var mCurrentFragment: Fragment? = null

    fun getCurrentActivity(): Activity? {
        return mCurrentActivity
    }

    fun setCurrentActivity(mCurrentActivity: Activity) {
        this.mCurrentActivity = mCurrentActivity
    }


    fun getCurrentFragment(): Fragment? {
        return mCurrentFragment
    }

    fun setCurrentFragment(mCurrentFragment: Fragment) {
        this.mCurrentFragment = mCurrentFragment
    }

//    fun getCurrentActivity(): BaseActivity<*, *>? {
//        return weakActivity?.get()
//    }


    override fun onStateChanged(source: LifecycleOwner, event: Lifecycle.Event) {
        when (event) {
            Lifecycle.Event.ON_STOP -> {
                IS_APP_IN_FOREGROUND = false
                //your code here
            }

            Lifecycle.Event.ON_START -> {
                IS_APP_IN_FOREGROUND = true
                //your code here
            }

            else -> {}
        }
    }


}
package ncatz.jvm.jodergenerator

import android.app.Application
import android.content.Context
import android.graphics.Typeface

class App : Application() {
    companion object {
        lateinit var canaroExtraBold: Typeface
    }

    override fun getApplicationContext(): Context {
        return super.getApplicationContext()
    }

    private val CANARO_EXTRA_BOLD = "fonts/canaro_extra_bold.otf"

    override fun onCreate() {
        super.onCreate()
        initTypeface()
    }

    private fun initTypeface() {
        canaroExtraBold = Typeface.createFromAsset(assets, CANARO_EXTRA_BOLD)
    }
}
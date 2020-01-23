package com.example.reminders.ui.activities

import android.os.Bundle
import com.example.reminders.R
import com.example.reminders.Utils
import kotlinx.android.synthetic.main.view_toolbar.*
import moxy.MvpAppCompatActivity

abstract class BaseActivity : MvpAppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(getLayoutId())

        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(isDisplayHomeAsUpEnabled())
        supportActionBar?.setDisplayShowTitleEnabled(false)
        supportActionBar?.setHomeAsUpIndicator(R.drawable.ic_left_arrow)

        toolBarCenteredTitleTV.text = getToolbarTitle()
    }

    override fun onSupportNavigateUp(): Boolean {
        Utils.hideKeyboard(this)
        onBackPressed()
        return true
    }

    abstract fun getLayoutId(): Int

    abstract fun getToolbarTitle(): String

    open fun isDisplayHomeAsUpEnabled(): Boolean = true

}

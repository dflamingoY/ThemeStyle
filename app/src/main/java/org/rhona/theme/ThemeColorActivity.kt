package org.rhona.theme

import android.view.MenuItem
import android.view.View
import kotlinx.android.synthetic.main.activity_theme_color.*
import org.greenrobot.eventbus.EventBus
import org.rhona.theme.core.BaseAct
import org.rhona.theme.eventBus.OnThemeEvent
import org.rhona.theme.utils.IConstant
import org.rhona.theme.wegidt.RippleAnimation
import skin.support.SkinCompatManager

class ThemeColorActivity : BaseAct() {
    private var themeName = ""
    override fun getLayoutId(): Int {
        return R.layout.activity_theme_color
    }

    override fun initView() {
        setSupportActionBar(toolbar)
        supportActionBar?.let {
            it.setDisplayHomeAsUpEnabled(true)
            it.setDisplayShowTitleEnabled(false)
        }
    }

    override fun initData() {

        when (SkinCompatManager.getInstance().curSkinName) {
            "" -> {
                iv_day.visibility = View.VISIBLE
            }
            IConstant.BLUENAME -> {
                iv_blue.visibility = View.VISIBLE
            }
            IConstant.NIGHTNAME -> {
                iv_black.visibility = View.VISIBLE
            }
            IConstant.ORANGENAME -> {
                iv_orange.visibility = View.VISIBLE
            }
            IConstant.PURPLENAME -> {
                iv_purple.visibility = View.VISIBLE
            }
            IConstant.REDNAME -> {
                iv_red.visibility = View.VISIBLE
            }
        }
    }

    override fun initEvent() {
        cardRed.setOnClickListener {
            themeName = IConstant.REDNAME
            hideView()
            iv_red.visibility = View.VISIBLE
        }
        cardOrange.setOnClickListener {
            themeName = IConstant.ORANGENAME
            hideView()
            iv_orange.visibility = View.VISIBLE
        }
        cardPurple.setOnClickListener {
            themeName = IConstant.PURPLENAME
            hideView()
            iv_purple.visibility = View.VISIBLE
        }
        cardBlue.setOnClickListener {
            themeName = IConstant.BLUENAME
            hideView()
            iv_blue.visibility = View.VISIBLE
        }
        cardDay.setOnClickListener {
            themeName = ""
            hideView()
            iv_day.visibility = View.VISIBLE
        }
        cardBlack.setOnClickListener {
            themeName = IConstant.NIGHTNAME
            hideView()
            iv_black.visibility = View.VISIBLE
        }
        btn_apply.setOnClickListener {
            EventBus.getDefault().post(OnThemeEvent(themeName))
            RippleAnimation.create(btn_apply).setDuration(1000).start(500)
        }
    }

    private fun hideView() {
        iv_red.visibility = View.GONE
        iv_orange.visibility = View.GONE
        iv_purple.visibility = View.GONE
        iv_blue.visibility = View.GONE
        iv_day.visibility = View.GONE
        iv_black.visibility = View.GONE
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> finish()
        }
        return true
    }
}
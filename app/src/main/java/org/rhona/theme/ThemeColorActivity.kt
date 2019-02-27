package org.rhona.theme

import android.view.MenuItem
import kotlinx.android.synthetic.main.activity_theme_color.*
import org.greenrobot.eventbus.EventBus
import org.rhona.theme.core.BaseAct
import org.rhona.theme.eventBus.OnThemeEvent
import org.rhona.theme.utils.IConstant
import org.rhona.theme.wegidt.RippleAnimation


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

    }

    override fun initEvent() {
        cardRed.setOnClickListener {
            themeName = IConstant.REDNAME
        }
        cardOrange.setOnClickListener {
            themeName = IConstant.ORANGENAME
        }
        cardPurple.setOnClickListener {
            themeName = IConstant.PURPLENAME
        }
        cardBlue.setOnClickListener {
            themeName = IConstant.BLUENAME
        }
        cardDay.setOnClickListener {
            themeName = ""
        }
        cardBlack.setOnClickListener {
            themeName = IConstant.NIGHTNAME
        }
        btn_apply.setOnClickListener {
            EventBus.getDefault().post(OnThemeEvent(themeName))
            RippleAnimation.create(btn_apply).setDuration(1000).start(500)
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> finish()
        }
        return true
    }
}
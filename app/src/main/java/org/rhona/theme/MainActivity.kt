package org.rhona.theme

import android.content.Intent

import kotlinx.android.synthetic.main.activity_main.*
import org.rhona.theme.core.BaseAct

class MainActivity : BaseAct() {

    override fun getLayoutId(): Int {
        return R.layout.activity_main
    }

    override fun initView() {
        
    }

    override fun initData() {

    }

    override fun initEvent() {
        fab.setOnClickListener { view ->
            /**
             * 直接换肤
             */
            startActivity(Intent(this, ThemeColorActivity::class.java))
        }
    }
}

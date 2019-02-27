package org.rhona.theme

import android.content.Intent
import android.os.Bundle

import kotlinx.android.synthetic.main.activity_main.*
import org.rhona.theme.core.BaseThemeActivity

class MainActivity : BaseThemeActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        fab.setOnClickListener { view ->
            /**
             * 直接换肤
             */
            startActivity(Intent(this, ThemeColorActivity::class.java))
        }
    }

}

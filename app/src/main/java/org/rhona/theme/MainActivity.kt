package org.rhona.theme

import android.os.Bundle
import android.support.design.widget.Snackbar

import kotlinx.android.synthetic.main.activity_main.*
import org.rhona.theme.core.BaseThemeActivity

class MainActivity : BaseThemeActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        fab.setOnClickListener { view ->
            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                .setAction("Action", null).show()
            /**
             * 直接换肤
             */
        }
    }

}

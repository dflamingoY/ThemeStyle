package org.rhona.theme

import android.app.Application
import org.rhona.theme.cardView.SkinCardViewInflater
import skin.support.SkinCompatManager
import skin.support.design.SkinMaterialManager
import skin.support.design.app.SkinMaterialViewInflater

class App : Application() {
    override fun onCreate() {
        super.onCreate()
        SkinMaterialManager.init(this)
        SkinCompatManager.init(this)                         // 基础控件换肤初始化
            .addInflater(SkinMaterialViewInflater())            // material design 控件换肤初始化[可选]
            .addInflater(SkinCardViewInflater())
            .loadSkin()
    }

}
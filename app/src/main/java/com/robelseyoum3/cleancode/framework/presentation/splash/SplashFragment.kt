package com.robelseyoum3.cleancode.framework.presentation.splash

import android.os.Bundle
import android.view.View
import com.robelseyoum3.cleancode.R
import com.robelseyoum3.cleancode.framework.presentation.common.BaseNoteFragment

class SplashFragment : BaseNoteFragment(R.layout.fragment_splash) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }


    override fun inject() {
        TODO("prepare dagger")
    }

}

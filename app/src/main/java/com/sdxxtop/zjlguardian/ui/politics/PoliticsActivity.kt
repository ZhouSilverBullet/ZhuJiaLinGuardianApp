package com.sdxxtop.zjlguardian.ui.politics

import android.content.Intent
import android.text.Editable
import android.text.method.KeyListener
import android.view.KeyEvent
import android.view.View
import android.view.inputmethod.EditorInfo
import android.widget.TextView
import androidx.lifecycle.ViewModelProviders
import com.sdxxtop.zjlguardian.R
import com.sdxxtop.zjlguardian.base.KBaseActivity
import com.sdxxtop.zjlguardian.databinding.ActivityPoliticsBinding
import org.jetbrains.anko.startActivity

class PoliticsActivity : KBaseActivity<ActivityPoliticsBinding>() {
    override fun initView() {
        mBinding.vm = ViewModelProviders.of(this)[PoliticsViewModel::class.java]
        mBinding.etSearch.setOnEditorActionListener(object : TextView.OnEditorActionListener {
            override fun onEditorAction(v: TextView?, actionId: Int, event: KeyEvent?): Boolean {
                if (actionId == EditorInfo.IME_ACTION_DONE) {
                    skip();
                    return true
                }
                return false;
            }
        })
    }

    private fun skip() {
        val empty = mBinding.vm?.searchContent?.isEmpty()
        if (!empty!!) {
            startActivity<PoliticsSearchActivity>("content" to mBinding.vm?.searchContent)
        }
    }

    override fun getLayoutId() = R.layout.activity_politics

    override fun loadData(isRefresh: Boolean) {

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        mBinding.phsvView.onActivityResult(requestCode, resultCode, data)
    }
}

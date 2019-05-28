package com.sdxxtop.zjlguardian.ui.politics

import android.content.Intent
import android.view.KeyEvent
import android.view.View
import android.view.inputmethod.EditorInfo
import android.widget.CheckBox
import android.widget.TextView
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.sdxxtop.zjlguardian.R
import com.sdxxtop.zjlguardian.base.KBaseActivity
import com.sdxxtop.zjlguardian.databinding.ActivityPoliticsBinding
import kotlinx.android.synthetic.main.activity_politics.*
import org.jetbrains.anko.startActivity
import org.jetbrains.anko.toast

class PoliticsActivity : KBaseActivity<ActivityPoliticsBinding>(), PartSelectDialogFragment.Listener {

    override fun getLayoutId() = R.layout.activity_politics

    override fun initView() {
        mBinding.vm = ViewModelProviders.of(this)[PoliticsViewModel::class.java]
        mBinding.etSearch.setOnEditorActionListener(object : TextView.OnEditorActionListener {
            override fun onEditorAction(v: TextView?, actionId: Int, event: KeyEvent?): Boolean {
                if (actionId == EditorInfo.IME_ACTION_DONE) {
                    skip();
                    return true
                }
                return false
            }
        })

        mBinding.vm?.pushSuccess?.observe(this, Observer {
            startActivity<PoliticsListActivity>("politicsId" to it)
        })
    }

    private fun skip() {
        val empty = mBinding.vm?.searchContent?.isEmpty()
        if (!empty!!) {
            startActivity<PoliticsSearchActivity>("content" to mBinding.vm?.searchContent
                    , "title" to "网络问政查询结果"
                    , "isSearch" to true)
        }
    }

    override fun loadData(isRefresh: Boolean) {
        mBinding.vm?.load()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        mBinding.phsvView.onActivityResult(requestCode, resultCode, data)
    }

    override fun onClick(v: View?) {
        when (v) {
            btn_login -> {

                var isCheckId = -1;
                if (mBinding.vm?.partBean != null) {
                    mBinding.vm?.partBean?.forEach {
                        if (it.isCheck) {
                            isCheckId = it.part_id
                        }
                    }
                }

                if (isCheckId == -1) {
                    toast("请选择问政对象")
                    return
                }

                val titleValue = mBinding.netvTitle.editValue
                if (titleValue.isEmpty()) {
                    toast("请填写问政标题")
                    return
                }

                val contentValue = mBinding.netvContent.editValue
                if (contentValue.isEmpty()) {
                    toast("请填写问政内容")
                    return
                }

                val imgList = mBinding.phsvView.imagePushPath
                mBinding.vm?.pushPolitics(isCheckId, titleValue, contentValue, imgList)
            }

            ll_select_part -> {
                if (mBinding.vm?.partBean != null) {
                    PartSelectDialogFragment.newInstance(mBinding.vm?.partBean!!)
                            .show(supportFragmentManager, "1")
                }
            }
        }
    }

    override fun onItemClicked(checkBox: CheckBox, position: Int) {
        if (mBinding.vm?.partBean != null) {

        }
        mBinding.vm?.partBean?.forEach {
            it.isCheck = false
        }
        val partBean = mBinding.vm?.partBean?.get(position)
        partBean?.isCheck = true
        mBinding.tvPart.text = partBean?.part_name
    }

    override fun onDestroy() {
        super.onDestroy()
        mBinding.vm?.remove()
    }
}

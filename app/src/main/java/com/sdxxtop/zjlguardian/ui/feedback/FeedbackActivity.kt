package com.sdxxtop.zjlguardian.ui.feedback

import android.content.Intent
import android.view.KeyEvent
import android.view.View
import android.view.inputmethod.EditorInfo
import android.widget.CheckBox
import android.widget.RadioButton
import android.widget.TextView
import androidx.core.view.get
import androidx.lifecycle.Observer
import com.amap.api.mapcore.util.it
import com.sdxxtop.zjlguardian.R
import com.sdxxtop.zjlguardian.base.KBaseActivity
import com.sdxxtop.zjlguardian.databinding.ActivityFeedbackBinding
import com.sdxxtop.zjlguardian.extens.toast
import com.sdxxtop.zjlguardian.ui.politics.PartSelectDialogFragment
import com.sdxxtop.zjlguardian.ui.politics.PoliticsListActivity
import com.sdxxtop.zjlguardian.ui.politics.PoliticsSearchActivity
import kotlinx.android.synthetic.main.activity_feedback.*
import org.jetbrains.anko.forEachChild
import org.jetbrains.anko.startActivity


class FeedbackActivity : KBaseActivity<ActivityFeedbackBinding>(), PartSelectDialogFragment.Listener {

    override fun getLayoutId() = R.layout.activity_feedback

    override fun initView() {
        mBinding.vm = bindViewModel(FeedbackViewModel::class.java)

        mBinding.netvTitle.setMaxLength(30)
        mBinding.netvContent.setMaxLength(500)

        mBinding.etSearch.setOnEditorActionListener(object : TextView.OnEditorActionListener {
            override fun onEditorAction(v: TextView?, actionId: Int, event: KeyEvent?): Boolean {
                if (actionId == EditorInfo.IME_ACTION_DONE) {
                    skip();
                    return true
                }
                return false
            }
        })

        mBinding.vm?.mProposalIdData?.observe(this, Observer {
            startActivity<PoliticsListActivity>("politicsId" to it)
        })
    }

    private fun skip() {
        val empty = mBinding.vm?.searchContent?.isEmpty()
        if (!empty!!) {
            startActivity<FeedbackSearchActivity>("content" to mBinding.vm?.searchContent
                    , "title" to "意见建议及投诉查询结果"
                    , "isSearch" to true
            )
        }
    }

    override fun initObserver() {

//        mBinding.vm?.pushFeedback()

    }

    override fun loadData(isRefresh: Boolean) {
        mBinding.vm?.load()
    }

    override fun onClick(v: View?) {
        when (v) {
            btn_login -> {

                var feedCheck = -1;

                when (rg_feed_group.checkedRadioButtonId) {
                    R.id.rb_feed -> {
                        feedCheck = 0;
                    }
                    R.id.rb_jianyi -> {
                        feedCheck = 1;
                    }
                    R.id.rb_toushu -> {
                        feedCheck = 2;
                    }
                }

                if (feedCheck == -1) {
                    toast("请选择反应类型")
                    return
                }

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
                mBinding.vm?.pushFeedback(feedCheck, isCheckId, titleValue, contentValue, imgList)
            }

            ll_select_part -> {
                if (mBinding.vm?.partBean != null) {
                    PartSelectDialogFragment.newInstance(mBinding.vm?.partBean!!)
                            .show(supportFragmentManager, "1")
                }
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        mBinding.phsvView.onActivityResult(requestCode, resultCode, data)
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
}

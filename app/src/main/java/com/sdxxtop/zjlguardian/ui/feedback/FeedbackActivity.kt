package com.sdxxtop.zjlguardian.ui.feedback

import android.content.Intent
import android.os.SystemClock
import android.view.KeyEvent
import android.view.View
import android.view.inputmethod.EditorInfo
import android.widget.CheckBox
import android.widget.TextView
import androidx.lifecycle.Observer
import com.sdxxtop.app.Constants
import com.sdxxtop.ui.dialog.IosAlertDialog
import com.sdxxtop.utils.SpUtil
import com.sdxxtop.zjlguardian.R
import com.sdxxtop.zjlguardian.base.KBaseActivity
import com.sdxxtop.zjlguardian.databinding.ActivityFeedbackBinding
import com.sdxxtop.zjlguardian.extens.toast
import com.sdxxtop.zjlguardian.ui.learn.news.NewsDetailsActivity
import com.sdxxtop.zjlguardian.ui.politics.PartComfirmDialogFragment
import com.sdxxtop.zjlguardian.ui.politics.PartSelectDialogFragment
import kotlinx.android.synthetic.main.activity_feedback.*
import org.jetbrains.anko.startActivity


class FeedbackActivity : KBaseActivity<ActivityFeedbackBinding>(), PartSelectDialogFragment.Listener, PartComfirmDialogFragment.Listener {

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
            startActivity<NewsDetailsActivity>("article_path" to it)
            finish()
        })
    }

    private fun skip() {
        val empty = mBinding.vm?.searchContent?.isEmpty()
        if (!empty!!) {
            startActivity<FeedbackSearchActivity>("content" to mBinding.vm?.searchContent
                    , "title" to "意见建议及投诉查询结果"
                    , "isSearch" to true
                    , "isToEditSkip" to true
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
                val isShowReal = SpUtil.getBoolean(Constants.IS_SHOW_REAL, false)
                if (isShowReal) {
                    nextConfirmDialog()
                } else {
                    showConfirmFragmentDialog()
                }
            }

            ll_select_part -> {
                if (mBinding.vm?.partBean != null) {
                    PartSelectDialogFragment.newInstance(mBinding.vm?.partBean!!)
                            .show(supportFragmentManager, "1")
                }
            }
        }
    }

    private fun showConfirmFragmentDialog() {
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

        PartComfirmDialogFragment.newInstance("岸堤镇意见建议投诉须知")
                .show(supportFragmentManager, "1")
    }

    override fun onConfirmClicked() {
        //点击确定，就保存为true
        SpUtil.putBoolean(Constants.IS_SHOW_REAL, true)

        nextConfirmDialog()

    }

    /**
     * 真正发布的时候确定的对话框
     */
    private fun nextConfirmDialog() {

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


        if (mBinding.vm?.open ?: false) {
            confirm()
        } else {

            IosAlertDialog(this)
                    .builder()
                    .setMsg("您将以公开身份对计生局发送信件\n是否确认发送")
                    .setPositiveButton("发送") {
                        confirm()

                    }
                    .setNegativeButton("再想想") {

                    }
                    .show()

        }
    }

    private fun confirm() {
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

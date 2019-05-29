package com.sdxxtop.zjlguardian.ui.server_people

import android.content.Context
import android.graphics.Color
import android.os.Bundle
import android.text.SpannableStringBuilder
import android.text.Spanned
import android.text.style.ForegroundColorSpan
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.bumptech.glide.Glide
import com.google.android.material.tabs.TabLayout
import com.sdxxtop.zjlguardian.base.KBaseFragment
import com.sdxxtop.zjlguardian.databinding.FragmentServerPeopleBinding
import com.sdxxtop.zjlguardian.helper.adapter.HomePagerAdapter
import com.sdxxtop.zjlguardian.ui.politics.PoliticsActivity
import com.sdxxtop.zjlguardian.widget.AutoTextViewManager
import com.youth.banner.loader.ImageLoader
import kotlinx.android.synthetic.main.fragment_server_people.*
import org.jetbrains.anko.support.v4.startActivity
import android.widget.LinearLayout
import android.util.TypedValue
import android.view.LayoutInflater
import androidx.core.content.ContextCompat.startActivity
import com.sdxxtop.zjlguardian.R
import com.sdxxtop.zjlguardian.data.PartBean
import com.sdxxtop.zjlguardian.ui.feedback.FeedbackActivity
import com.sdxxtop.zjlguardian.ui.notice.NoticeActivity
import com.sdxxtop.zjlguardian.ui.policy.PolicyQueryActivity
import com.sdxxtop.zjlguardian.ui.politics.ARG_ITEM_COUNT
import com.sdxxtop.zjlguardian.ui.politics.PartSelectDialogFragment


const val TYPE = "type"

/**
 * A simple [Fragment] subclass.
 *
 */
class ServerPeopleFragment : KBaseFragment<FragmentServerPeopleBinding>() {

    lateinit var autoTextViewManager: AutoTextViewManager


    override fun initView() {

        var int = arguments?.getInt(TYPE)
        if (int == 1) {
            topViewPadding(mBinding.tvTitle)
        }

        mBinding.vm = ViewModelProviders.of(this)[ServerPeopleViewModel::class.java]
        banner.setImageLoader(GlideImageLoader())

        autoTextViewManager = AutoTextViewManager(mBinding.atvAutoView)

        mBinding.vm?.serverPeopleData?.observe(this, Observer {
            //banner设置
            banner.setImages(it.imgs)
            banner.start()

            autoTextViewManager.setData(it.notices)
            autoTextViewManager.start()
        })

        val content = "最新公告";
        val stringBuilder = SpannableStringBuilder(content);
        val foregroundColorSpan = ForegroundColorSpan(Color.parseColor("#FF4040"));
        stringBuilder.setSpan(foregroundColorSpan, 1, 2, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        mBinding.tvTitle1.text = stringBuilder;

        initTab()
    }

    private fun initTab() {

        val arrayList = listOf("户政", "社保", "补助", "婚姻", "计生", "生活")
        val fragmentList = listOf(ServerTabFragment(), ServerTabFragment(),
                ServerTabFragment(), ServerTabFragment(), ServerTabFragment(), ServerTabFragment())


        val homePagerAdapter = HomePagerAdapter(childFragmentManager, arrayList, fragmentList)
        mBinding.vp.adapter = homePagerAdapter
        mBinding.tlTab.setupWithViewPager(mBinding.vp)

        val title = ((mBinding.tlTab.getChildAt(0) as LinearLayout).getChildAt(0) as LinearLayout).getChildAt(1) as TextView
        title.textSize = 18f
        title.setTextAppearance(activity, R.style.AppliedTabLayoutTextStyle)
        mBinding.tlTab.addOnTabSelectedListener(object : TabLayout.BaseOnTabSelectedListener<TabLayout.Tab> {
            override fun onTabReselected(tab: TabLayout.Tab?) {

            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {
                tab?.customView = null
            }

            override fun onTabSelected(tab: TabLayout.Tab?) {
                val textView = LayoutInflater.from(mContext).inflate(com.sdxxtop.zjlguardian.R.layout.tab_text_layout, null) as TextView
                textView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 16f)
                textView.text = tab?.text
                tab?.customView = textView
            }

        })

    }

    override fun loadData(isRefresh: Boolean) {
        mBinding.vm?.load()
    }

    override fun getLayoutId() = R.layout.fragment_server_people


    class GlideImageLoader : ImageLoader() {

        override fun displayImage(context: Context?, path: Any?, imageView: ImageView?) {
            Glide.with(context!!).load(path).into(imageView!!);
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        autoTextViewManager.removeAutoTextRunnable()
        mBinding.vm?.remove()
    }

    override fun onClick(v: View?) {
        when (v) {
            mBinding.llPolitics -> {
                startActivity<PoliticsActivity>()
            }

            ll_feed -> {
                startActivity<FeedbackActivity>()
            }

            ll_policy_query -> {
                startActivity<PolicyQueryActivity>()
            }

            tv_notice_more -> {
                startActivity<NoticeActivity>()
            }
        }
    }

    companion object {

        // TODO: Customize parameters
        /**
         * 这个type 1 是title加padding用的，因为，不在第一页的
         * fragment沉淀式，需要加一个padding
         */
        fun newInstance(type: Int): ServerPeopleFragment =
                ServerPeopleFragment().apply {
                    arguments = Bundle().apply {
                        putSerializable(TYPE, type)
                    }
                }

    }
}

package com.sdxxtop.zjlguardian.ui.policy

import android.view.View
import androidx.lifecycle.Observer
import com.sdxxtop.zjlguardian.R
import com.sdxxtop.zjlguardian.base.KBaseActivity
import com.sdxxtop.zjlguardian.databinding.ActivityPolicyQueryBinding
import com.sdxxtop.zjlguardian.extens.toast
import com.sdxxtop.zjlguardian.helper.adapter.HomePagerAdapter
import com.sdxxtop.zjlguardian.ui.policy.data.PolicyQueryBean
import kotlinx.android.synthetic.main.fragment_policy_query_tab.*
import org.jetbrains.anko.startActivity

class PolicyQueryActivity : KBaseActivity<ActivityPolicyQueryBinding>() {

    val arrayList = listOf("企业政策", "个人政策")
    var fragmentList: List<PolicyQueryTabFragment>? = null;

    override fun initView() {
        mBinding.vm = bindViewModel(PolicyQueryViewModel::class.java)

    }

    override fun initObserver() {
        mBinding.vm?.mPolicyQueryData?.observe(this, Observer {
            setViewPager(it)
        })
    }

    private fun setViewPager(beanList: List<PolicyQueryBean>?) {

        if (beanList == null || beanList.size <= 1) {
            toast("获取数据格式有误")
            return
        }

        fragmentList = listOf(PolicyQueryTabFragment.newInstance(beanList[0], 0),
                PolicyQueryTabFragment.newInstance(beanList[1], 1))

        val homePagerAdapter = HomePagerAdapter(supportFragmentManager, arrayList, fragmentList)

        mBinding.vp.adapter = homePagerAdapter
        mBinding.tlTab.setupWithViewPager(mBinding.vp)


    }

    override fun onClick(v: View?) {
        fragmentList?.forEach {
            if (it.userVisibleHint) {
                var mineId = 0
                var findId = 0
                when (it.index) {
                    0 -> {
                        if (!"全部".equals(it.tv_mine.text)) {
                            mineId = it.twoPoint
                        } else {
                            mineId = 0
                        }

                        if (!"全部".equals(it.tv_find.text)) {
                            findId = it.threePoint
                        } else {
                            findId = 0
                        }
                    }
                    1 -> {
                        if (!"全部".equals(it.tv_mine.text)) {
                            mineId = it.twoPoint
                        } else {
                            mineId = 0
                        }

                        if (!"全部".equals(it.tv_find.text)) {
                            findId = it.threePoint
                        } else {
                            findId = 0
                        }
                    }
                }

                startActivity<PolicyQueryListActivity>(
                        "title" to mBinding.vm?.searchContent,
                        "mineId" to mineId, "findId" to findId)
//                mBinding.vm?.push(mBinding.vm?.searchContent, mineId, findId)
            }
        }
    }

    override fun getLayoutId() = R.layout.activity_policy_query

    override fun loadData(isRefresh: Boolean) {
        mBinding.vm?.load()
    }

}

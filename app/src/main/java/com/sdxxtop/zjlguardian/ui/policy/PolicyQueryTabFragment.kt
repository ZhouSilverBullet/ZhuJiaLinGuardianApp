package com.sdxxtop.zjlguardian.ui.policy


import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RelativeLayout
import com.amap.api.mapcore.util.it

import com.sdxxtop.zjlguardian.R
import com.sdxxtop.zjlguardian.base.KBaseFragment
import com.sdxxtop.zjlguardian.data.PartBean
import com.sdxxtop.zjlguardian.databinding.FragmentPolicyQueryTabBinding
import com.sdxxtop.zjlguardian.ui.policy.data.PolicyQueryBean
import com.sdxxtop.zjlguardian.ui.policy.data.ThreeCate
import com.sdxxtop.zjlguardian.ui.policy.data.TwoCate
import com.sdxxtop.zjlguardian.ui.politics.ARG_ITEM_COUNT
import com.sdxxtop.zjlguardian.ui.politics.PartSelectDialogFragment
import com.sdxxtop.zjlguardian.widget.SingleStyleView
import kotlinx.android.synthetic.main.fragment_item_part_list_dialog.*
import kotlinx.android.synthetic.main.fragment_policy_query_tab.*
import java.io.Serializable
import javax.security.auth.login.LoginException


const val POLICY_QUERY_TYPE = "policy_query_type"

private const val TAG = "PolicyQueryTabFragment"

/**
 * A simple [Fragment] subclass.
 *
 */
class PolicyQueryTabFragment : KBaseFragment<FragmentPolicyQueryTabBinding>() {

    override fun getLayoutId() = R.layout.fragment_policy_query_tab

    var type: PolicyQueryBean? = null
    var twoPoint = -1;
    var threePoint = -1;
    var index: Int = 0;

    var threeCate: List<ThreeCate>? = null

    override fun initView() {
        type = arguments?.getSerializable(POLICY_QUERY_TYPE) as PolicyQueryBean
        index = arguments?.getInt("index", 0)!!
    }

    override fun loadData(isRefresh: Boolean) {

    }

    override fun onClick(v: View?) {
        when (v) {
            mBinding.rlMine -> {
                //清除我找的这个id
                val twoCate = type?.two_cate
                val list: MutableList<String> = ArrayList()
                for (two in twoCate!!) {
                    list.add(two.name)
                }
                showTwoCate(list)
            }

            mBinding.rlFind -> {

                if (twoPoint == -1) {
                    toast("请选择我是，再选择我找")
                    return
                }

                //先置为null
                threeCate = null

                type?.two_cate?.forEach {
                    if (it.id == twoPoint) {
                        threeCate = it.three_cate
                    }
                }

                val list: MutableList<String> = ArrayList()
                for (two in threeCate!!) {
                    list.add(two.name.trim())
                }
                showThreeCate(list)
            }

        }
    }

    companion object {

        // TODO: Customize parameters
        fun newInstance(type: PolicyQueryBean, index: Int): PolicyQueryTabFragment =
                PolicyQueryTabFragment().apply {
                    arguments = Bundle().apply {
                        putSerializable(POLICY_QUERY_TYPE, type)
                        putInt("index", index)
                    }
                }

    }


    var singleStyleView: SingleStyleView? = null;

    private fun showTwoCate(twoCate: List<String>) {
        if (singleStyleView == null) {
            singleStyleView = SingleStyleView(activity, twoCate)
            singleStyleView?.setOnItemSelectLintener(object : SingleStyleView.OnItemSelectLintener {
                override fun onItemSelect(result: String) {
                    tv_mine.setText(result)

                    val twoCate = type?.two_cate
                    twoCate?.forEach {
                        if (result == it.name.trim()) {
                            twoPoint = it.id
                        }
                    }

                    threePoint = -1
                    mBinding.tvFind.setText("全部")
                }
            })
        }
        singleStyleView?.show()
    }

    var singleStyleView2: SingleStyleView? = null;

    private fun showThreeCate(twoCate: List<String>) {
        singleStyleView2 = SingleStyleView(activity, twoCate)
        singleStyleView2?.setOnItemSelectLintener(object : SingleStyleView.OnItemSelectLintener {
            override fun onItemSelect(result: String) {
                tv_find.setText(result)
                threeCate?.forEach {
                    if (result == it.name.trim()) {
                        threePoint = it.id
                    }
                }
            }
        })
        singleStyleView2?.show()
    }


}

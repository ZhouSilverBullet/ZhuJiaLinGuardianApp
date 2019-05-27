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

    override fun initView() {
        type = arguments?.getSerializable(POLICY_QUERY_TYPE) as PolicyQueryBean
        index = arguments?.getInt("index", 0)!!
    }

    override fun loadData(isRefresh: Boolean) {

    }

    override fun onClick(v: View?) {
        when (v) {
            mBinding.rlMine -> {
                val twoCate = type?.two_cate
                val list: MutableList<String> = ArrayList()
                for (two in twoCate!!) {
                    list.add(two.name)
                }
                showTwoCate(list)
            }

            mBinding.rlFind -> {
                val threeCate = type?.two_cate?.get(0)?.three_cate
                val list: MutableList<String> = ArrayList()
                for (two in threeCate!!) {
                    list.add(two.name)
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
                        if (result == it.name) {
                            twoPoint = it.id
                        }
                    }
                }
            })
        }
        singleStyleView?.show()
    }

    var singleStyleView2: SingleStyleView? = null;

    private fun showThreeCate(twoCate: List<String>) {
        if (singleStyleView2 == null) {
            singleStyleView2 = SingleStyleView(activity, twoCate)
            singleStyleView2?.setOnItemSelectLintener(object : SingleStyleView.OnItemSelectLintener {
                override fun onItemSelect(result: String) {
                    tv_find.setText(result)
                    val threeCate = type?.two_cate?.get(0)?.three_cate
                    threeCate?.forEach {
                        if (result == it.name) {
                            threePoint = it.id
                        }
                    }
                }
            })
        }
        singleStyleView2?.show()
    }


}

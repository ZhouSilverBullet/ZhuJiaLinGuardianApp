package com.sdxxtop.zjlguardian.ui.server_people

import android.content.Context
import android.view.View
import android.widget.ImageView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.sdxxtop.zjlguardian.R
import com.sdxxtop.zjlguardian.base.KBaseFragment
import com.sdxxtop.zjlguardian.databinding.FragmentServerPeopleBinding
import com.sdxxtop.zjlguardian.ui.politics.PoliticsActivity
import com.sdxxtop.zjlguardian.widget.AutoTextViewManager
import com.youth.banner.loader.ImageLoader
import kotlinx.android.synthetic.main.fragment_server_people.*
import org.jetbrains.anko.support.v4.startActivity

/**
 * A simple [Fragment] subclass.
 *
 */
class ServerPeopleFragment : KBaseFragment<FragmentServerPeopleBinding>() {

    lateinit var autoTextViewManager: AutoTextViewManager


    override fun initView() {
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
    }

    override fun onClick(v: View?) {
        when (v) {
            mBinding.btnPolitics -> {
                startActivity<PoliticsActivity>()
            }
        }
    }
}

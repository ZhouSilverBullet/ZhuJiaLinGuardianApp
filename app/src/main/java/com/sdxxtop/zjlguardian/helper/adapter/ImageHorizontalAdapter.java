package com.sdxxtop.zjlguardian.helper.adapter;

import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;

import androidx.annotation.Nullable;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.sdxxtop.zjlguardian.R;
import com.sdxxtop.zjlguardian.ui.guardianapp.PhotoViewActivity;

import java.util.List;

public class ImageHorizontalAdapter extends BaseQuickAdapter<String, BaseViewHolder> {
    public ImageHorizontalAdapter(int layoutResId, @Nullable List<String> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, String item) {
        ImageView imageView = helper.getView(R.id.iv_img);
        if (!TextUtils.isEmpty(item)) {
            Glide.with(mContext).load(item).into(imageView);
        }
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PhotoViewActivity.start(mContext, item);
            }
        });
    }
}

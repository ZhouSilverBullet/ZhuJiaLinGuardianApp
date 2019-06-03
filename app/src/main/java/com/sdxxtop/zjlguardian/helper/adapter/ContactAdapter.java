package com.sdxxtop.zjlguardian.helper.adapter;

import android.content.Intent;
import android.net.Uri;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.sdxxtop.ui.dialog.IosAlertDialog;
import com.sdxxtop.zjlguardian.R;
import com.sdxxtop.zjlguardian.data.ContactIndexBean;
import com.timehop.stickyheadersrecyclerview.StickyRecyclerHeadersAdapter;

import de.hdodenhof.circleimageview.CircleImageView;

public class ContactAdapter extends BaseQuickAdapter<ContactIndexBean.ContactBean, BaseViewHolder>
        implements StickyRecyclerHeadersAdapter<RecyclerView.ViewHolder> {
    public ContactAdapter(int layoutResId) {
        super(layoutResId);
    }

    @Override
    protected void convert(BaseViewHolder helper, ContactIndexBean.ContactBean item) {
        CircleImageView ivIcon = helper.getView(R.id.iv_icon);
        TextView tvName = helper.getView(R.id.tv_name);
        TextView tvContent = helper.getView(R.id.tv_content);

        String name = item.getName();
        String img = item.getImage();

        if (!TextUtils.isEmpty(img)) {
            Glide.with(mContext).load(img).into(ivIcon);
        }

        tvName.setText(name);
        tvContent.setText(item.getPart_name());

        helper.getConvertView().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new IosAlertDialog(mContext)
                        .builder()
                        .setMsg(item.getMobile())
                        .setPositiveButton("呼叫", new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" +  item.getMobile()));
                                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                mContext. startActivity(intent);
                            }
                        })
                        .setNegativeButton("取消", new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                //填写事件
                            }
                        }).show();
//
//                Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + item.getMobile()));
//                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//                mContext.startActivity(intent);
            }
        });
    }

    @Override
    public long getHeaderId(int position) {
        return getData().get(position).sortLetters.charAt(0);
    }

    @Override
    public RecyclerView.ViewHolder onCreateHeaderViewHolder(ViewGroup parent) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_contact_header, parent, false);
        return new RecyclerView.ViewHolder(view) {

        };
    }

    @Override
    public void onBindHeaderViewHolder(RecyclerView.ViewHolder viewHolder, int position) {
        String showValue = String.valueOf(getData().get(position).sortLetters.charAt(0));
        ((TextView) viewHolder.itemView).setText(showValue);
    }

    public int getPositionForSection(char section) {
        for (int i = 0; i < getItemCount(); i++) {
            String sortStr = getData().get(i).sortLetters;
            char firstChar = sortStr.toUpperCase().charAt(0);
            if (firstChar == section) {
                return i;
            }
        }
        return -1;

    }
}

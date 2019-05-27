package com.sdxxtop.zjlguardian.widget;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.RelativeLayout;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.luck.picture.lib.PictureSelector;
import com.luck.picture.lib.config.PictureConfig;
import com.luck.picture.lib.config.PictureMimeType;
import com.luck.picture.lib.entity.LocalMedia;
import com.sdxxtop.zjlguardian.R;
import com.sdxxtop.zjlguardian.widget.adatper.PhotoHorRecyclerAdapter;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * Email: zhousaito@163.com
 * Created by zhousaito 2019-05-22 17:17
 * Version: 1.0
 * Description:
 */
public class PhotoHorSelectedView extends RelativeLayout implements PhotoHorRecyclerAdapter.HorListener {

    private RecyclerView mRecycleView;
    private PhotoHorRecyclerAdapter mAdapter;

    private static final int DEFAULT_MAX_COUNT = 10;

    private List<LocalMedia> localMediaList = new ArrayList<>();

    public PhotoHorSelectedView(Context context) {
        this(context, null);
    }

    public PhotoHorSelectedView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public PhotoHorSelectedView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        LayoutInflater.from(getContext()).inflate(R.layout.view_photo_hor_selected, this, true);
        mRecycleView = findViewById(R.id.rv);
        setPhotoRecycler(mRecycleView);
    }

    protected void setPhotoRecycler(RecyclerView recycler) {
        recycler.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
        mAdapter = new PhotoHorRecyclerAdapter(R.layout.item_event_report_recycler);
        recycler.setAdapter(mAdapter);
        LocalMedia localMedia = new LocalMedia();
        localMedia.setDuration(-100);
        localMediaList.add(localMedia);
        mAdapter.addData(localMediaList);
        mAdapter.setListener(this);
    }

    @Override
    public void click() {
        goGallery();
    }

    public void removeLocalListTemp() {
        for (int i = 0; i < localMediaList.size(); i++) {
            if (localMediaList.get(i).getDuration() == -100) {
                localMediaList.remove(localMediaList.get(i));
                break;
            }
        }
    }

    /**
     * 提交的时候不进行清除本身的localMediaList数据
     *
     * @return
     */
    public List<LocalMedia> getRemoveLocalListTemp() {
        List<LocalMedia> tempLocalMedia = new ArrayList<>();
        for (int i = 0; i < localMediaList.size(); i++) {
            if (localMediaList.get(i).getDuration() != -100) {
                tempLocalMedia.add(localMediaList.get(i));
            }
        }
        return tempLocalMedia;
    }

    public void goGallery() {
        removeLocalListTemp();

        PictureSelector.create((Activity) getContext())
                .openGallery(PictureMimeType.ofImage())
                .compress(true)
                .selectionMedia(localMediaList)
                .maxSelectNum(DEFAULT_MAX_COUNT).forResult(PictureConfig.CHOOSE_REQUEST);
    }

    @Override
    public void delete(LocalMedia item) {
        localMediaList.remove(item);
        for (int i = 0; i < localMediaList.size(); i++) {
            LocalMedia media = localMediaList.get(i);
            if (media.getDuration() == -100) {
                break;
            }
            if (i == localMediaList.size() - 1) {
                localMediaList.add(getTemp());
            }
        }

        //删除也是重新刷新数据
        //本来想定义 onDelete回调，发现也是 adapter.replaceData(),所以也用onResult 了
        onResult(localMediaList);
    }

    private void onResult(List<LocalMedia> selectList) {
        if (mAdapter != null) {
            mAdapter.replaceData(selectList);
        }
    }

    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
//        hideLoadingDialog();
        if (resultCode == Activity.RESULT_OK) {
            switch (requestCode) {
                case PictureConfig.CHOOSE_REQUEST:
                    // 图片、视频、音频选择结果回调
                    List<LocalMedia> selectList = PictureSelector.obtainMultipleResult(data);
                    if (selectList != null && selectList.size() > 0) {
                        localMediaList.clear();
                        int size = selectList.size();
                        if (size < DEFAULT_MAX_COUNT) {
                            localMediaList.addAll(selectList);
                            localMediaList.add(getTemp());
                        } else {
                            localMediaList.addAll(selectList);
                        }
                        onResult(localMediaList);
                    }
                    break;
                default:
                    break;
            }
        }
    }

    private LocalMedia getTemp() {
        LocalMedia localMedia = new LocalMedia();
        localMedia.setDuration(-100);
        return localMedia;
    }

    //图片上传
    public List<File> getImagePushPath() {

        List<LocalMedia> localListTemp = getRemoveLocalListTemp();
        //设置相片
        List<File> imgList = new ArrayList<>();
        if (localListTemp != null && localListTemp.size() > 0) {
            for (int i = 0; i < localListTemp.size(); i++) {
                String path = localListTemp.get(i).getPath();
                if (TextUtils.isEmpty(path)) {
                    path = localListTemp.get(i).getCompressPath();
                }
                imgList.add(new File(path));
            }
        }
        return imgList;
    }
}

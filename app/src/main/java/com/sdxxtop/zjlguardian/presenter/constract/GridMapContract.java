package com.sdxxtop.zjlguardian.presenter.constract;

import com.amap.api.maps.model.LatLng;
import com.sdxxtop.base.BasePresenter;
import com.sdxxtop.base.BaseView;
import com.sdxxtop.zjlguardian.data.MainMapBean;

import java.util.List;

public interface GridMapContract {
    interface IView extends BaseView {
        void showPolygon(int index, MainMapBean.UserBean middle, List<LatLng> list);
    }

    interface IPresenter extends BasePresenter<IView> {

    }
}

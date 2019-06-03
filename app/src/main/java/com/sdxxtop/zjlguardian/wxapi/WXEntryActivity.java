package com.sdxxtop.zjlguardian.wxapi;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import com.sdxxtop.app.Constants;
import com.sdxxtop.utils.UIUtils;
import com.tencent.mm.opensdk.modelbase.BaseReq;
import com.tencent.mm.opensdk.modelbase.BaseResp;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.IWXAPIEventHandler;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;

public class WXEntryActivity extends Activity implements IWXAPIEventHandler {
    private static String TAG = "WXEntryActivity";

    private IWXAPI api;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        api = WXAPIFactory.createWXAPI(this, Constants.WX_APP_ID, false);

        try {
            Intent intent = getIntent();
            api.handleIntent(intent, this);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);

        setIntent(intent);
        api.handleIntent(intent, this);
    }

    @Override
    public void onReq(BaseReq req) {
        finish();
    }

    @Override
    public void onResp(BaseResp resp) {
        int result = 0;

        switch (resp.errCode) {
            case BaseResp.ErrCode.ERR_OK:
                UIUtils.showToast("分享成功");
                break;
            case BaseResp.ErrCode.ERR_USER_CANCEL:
                UIUtils.showToast("分享取消");
                break;
            case BaseResp.ErrCode.ERR_AUTH_DENIED:
                UIUtils.showToast("授权失败");
                break;
            case BaseResp.ErrCode.ERR_UNSUPPORT:
                UIUtils.showToast("微信不支持");
                break;
            default:
                UIUtils.showToast("发送返回");
                break;
        }


        finish();
    }

}
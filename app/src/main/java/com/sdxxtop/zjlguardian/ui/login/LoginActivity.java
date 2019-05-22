package com.sdxxtop.zjlguardian.ui.login;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Handler;
import android.os.Message;
import android.text.InputFilter;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.jakewharton.rxbinding3.view.RxView;
import com.jakewharton.rxbinding3.widget.RxTextView;
import com.sdxxtop.app.Constants;
import com.sdxxtop.utils.SpUtil;
import com.sdxxtop.utils.UIUtils;
import com.sdxxtop.zjlguardian.MainActivity;
import com.sdxxtop.zjlguardian.R;
import com.sdxxtop.zjlguardian.base.GBaseMvpActivity;
import com.sdxxtop.zjlguardian.data.LoginBean;
import com.sdxxtop.zjlguardian.helper.control.DelTextWatcher;

import java.lang.ref.WeakReference;

import butterknife.BindView;
import io.reactivex.Observable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.BiFunction;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;

public class LoginActivity extends GBaseMvpActivity<LoginPresenter> implements LoginContract.IView, Handler.Callback {
    public static final String ACTION_LOGIN_CONFIRM_SUCCESS = "action_login_confirm_success";

    @BindView(R.id.btn_login)
    Button btnLogin;
    @BindView(R.id.fl_code)
    FrameLayout flCode;
    @BindView(R.id.tv_code)
    TextView tvCode;
    @BindView(R.id.ll_code)
    LinearLayout llCode;
    @BindView(R.id.tv_time)
    TextView tvTime;
    @BindView(R.id.pb_code)
    ProgressBar pbCode;
    @BindView(R.id.et_code)
    EditText etCode;
    @BindView(R.id.et_phone)
    EditText etPhone;
    @BindView(R.id.iv_phone_del)
    ImageView ivPhoneDel;
    @BindView(R.id.iv_code_del)
    ImageView ivCodeDel;
    private Handler mHandler;
    private boolean isSending;
    private LoginReceiver mLoginReceiver;

    @Override
    protected void initInject() {
        getMyActivityComponent().inject(this);
    }

    @Override
    protected int getLayout() {
        return R.layout.activity_login;
    }

    @Override
    protected void initView() {
        super.initView();

        statusBar(true);

        registerLoginReceiver();

        etPhone.setFilters(new InputFilter[]{new InputFilter.LengthFilter(11)});
        String mobile = SpUtil.getString(Constants.MOBILE);
        if (TextUtils.isEmpty(mobile)) {
            mobile = "";
        }
        etPhone.setText(mobile);
    }

    private void registerLoginReceiver() {
        mLoginReceiver = new LoginReceiver(this);
        IntentFilter intentFilter = new IntentFilter(ACTION_LOGIN_CONFIRM_SUCCESS);
        registerReceiver(mLoginReceiver, intentFilter);
    }

    @Override
    protected void initEvent() {
        super.initEvent();

        rxEditText();

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toLogin();
            }
        });

        flCode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendCode();
            }
        });

        etPhone.addTextChangedListener(new DelTextWatcher(etPhone, ivPhoneDel));
        etCode.addTextChangedListener(new DelTextWatcher(etCode, ivCodeDel));
    }

    private void rxEditText() {
        Disposable subscribe  = Observable.combineLatest(RxTextView.textChanges(etPhone).map(new Function<CharSequence, String>() {
            @Override
            public String apply(CharSequence charSequence) throws Exception {
                return String.valueOf(charSequence);
            }
        }), RxTextView.textChanges(etCode).map(new Function<CharSequence, String>() {
            @Override
            public String apply(CharSequence charSequence) throws Exception {
                return String.valueOf(charSequence);
            }
        }), new BiFunction<String, String, Boolean>() {
            @Override
            public Boolean apply(String name, String password) throws Exception {
                if (name.length() == 11 && !isSending) { //电话长度为11 && 不是正在发送
                    flCode.setEnabled(true);
                } else {
                    flCode.setEnabled(false);
                }
                return name.length() == 11 && password.length() >= 4;
            }
        }).subscribe(new Consumer<Boolean>() {
            @Override
            public void accept(Boolean aBoolean) throws Exception {
                btnLogin.setEnabled(aBoolean);
            }
        });
    }

    private void toLogin() {
        showLoadingDialog();

        String trim = etPhone.getText().toString().trim();
        if (TextUtils.isEmpty(trim)) {
            UIUtils.showToast("请输入正确的手机号码");
            hideLoadingDialog();
            return;
        }

        String code = etCode.getText().toString().trim();
        if (TextUtils.isEmpty(code)) {
            UIUtils.showToast("验证码不能为空");
            hideLoadingDialog();
            return;
        }

        mPresenter.login(trim, code, "0");
    }

    private void sendCode() {
        if (mHandler == null) {
            mHandler = new Handler(this);
        }

        String trim = etPhone.getText().toString().trim();
        if (TextUtils.isEmpty(trim)) {
            UIUtils.showToast("请输入正确的手机号码");
            return;
        }

        if (!isSending) {
            isSending = true;
            mPresenter.sendCode(flCode, tvCode, pbCode, trim);
        }
    }

    @Override
    public void showError(String error) {
        hideLoadingDialog();
    }

    @Override
    public void loginSuccess(LoginBean loginBean) {
        // 是新用户，然后就到新界面
        if (loginBean.getIs_new() == 1) {
            Intent intent = new Intent(this, RegisterActivity.class);
            intent.putExtra("phone", loginBean.getMobile());
            startActivity(intent);
            hideLoadingDialog();
            return;
        }


        String autoToken = loginBean.getAuto_token();
        int expireTime = loginBean.getExpire_time();
        int partId = loginBean.getPart_id();
        String partName = loginBean.getPart_name();
        String name = loginBean.getName();
        String mobile = loginBean.getMobile();
        int position = loginBean.getPosition();
        int userid = loginBean.getUserid();
        String img = loginBean.getImg();

        SpUtil.putString(Constants.AUTO_TOKEN, autoToken);
        SpUtil.putInt(Constants.EXPIRE_TIME, expireTime);
        SpUtil.putInt(Constants.PART_ID, partId);
        SpUtil.putInt(Constants.USER_ID, userid);
        SpUtil.putString(Constants.MOBILE, mobile);

        Intent intent = new Intent(this, LoginConfirmActivity.class);
        intent.putExtra("isAdmin", true);
        intent.putExtra("autoToken", autoToken);
        intent.putExtra("expireTime", expireTime);
        intent.putExtra("partId", partId);
        intent.putExtra("userid", userid);
        intent.putExtra("phone", mobile);
        intent.putExtra("name", name);
        intent.putExtra("partName", partName);
        intent.putExtra("position", position);
        intent.putExtra("img", img);
        startActivity(intent);
        finish();

        hideLoadingDialog();
    }

    @Override
    public boolean handleMessage(Message msg) {
        int value = (int) msg.obj;
        if (tvCode != null) {
            handleCode(value);
        }

        if (value == 0) {
            isSending = false;
            handleCode(value);
        } else {
            Message message = Message.obtain();
            message.what = 100;
            message.obj = value - 1;
            if (mHandler != null) {
                mHandler.sendMessageDelayed(message, 1000);
            }
        }

        return true;
    }

    private void handleCode(int value) {
        if (tvCode == null || llCode == null) {
            return;
        }
        if (isSending) {
            tvTime.setText(String.valueOf(value));
            tvCode.setVisibility(View.INVISIBLE);
            llCode.setVisibility(View.VISIBLE);
            flCode.setEnabled(false);
        } else {
            tvCode.setVisibility(View.VISIBLE);
            llCode.setVisibility(View.INVISIBLE);
            flCode.setEnabled(true);
        }

    }

    private void toFinish() {
        if (mLoginReceiver != null) {
            unregisterReceiver(mLoginReceiver);
        }

        finish();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mHandler != null) {
            mHandler.removeMessages(100);
            mHandler = null;
        }
    }

    @Override
    public void sendCodeSuccess() {
        if (mHandler != null) {
            mHandler.obtainMessage(100, 60).sendToTarget();
        }
    }

    @Override
    public void sendCodeError() {
        isSending = false;
    }

    private static class LoginReceiver extends BroadcastReceiver {
        private WeakReference<LoginActivity> mLoginActivityDef;

        public LoginReceiver(LoginActivity loginActivity) {
            mLoginActivityDef = new WeakReference<>(loginActivity);
        }

        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent == null) {
                return;
            }

            if (ACTION_LOGIN_CONFIRM_SUCCESS.equals(intent.getAction())) {
                if (mLoginActivityDef.get() != null) {
                    mLoginActivityDef.get().toFinish();
                }
            }
        }
    }
}

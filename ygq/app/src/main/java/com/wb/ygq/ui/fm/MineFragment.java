package com.wb.ygq.ui.fm;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;
import com.wb.ygq.R;
import com.wb.ygq.bean.LoginData;
import com.wb.ygq.bean.LoginResponseBean;
import com.wb.ygq.callback.DialogChooseListener;
import com.wb.ygq.ui.act.AlreadyBuyActivity;
import com.wb.ygq.ui.act.CollectActivity;
import com.wb.ygq.ui.act.KeFuActivity;
import com.wb.ygq.ui.act.RechargeActivity;
import com.wb.ygq.ui.base.BaseFragment;
import com.wb.ygq.ui.constant.PubConst;
import com.wb.ygq.utils.AppUtils;
import com.wb.ygq.utils.ClientUpgrade;
import com.wb.ygq.utils.ConfirmDialog;
import com.wb.ygq.utils.DialogUtil;
import com.wb.ygq.utils.HttpUrl;
import com.wb.ygq.utils.MyUtil;
import com.wb.ygq.utils.PublicUtil;
import com.wb.ygq.utils.SharedUtil;
import com.wb.ygq.utils.ToastUtil;
import com.wb.ygq.utils.VipDialog;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.Callback;

import java.io.IOException;

/**
 * Description：
 * Created on 2017/4/2
 */
public class MineFragment extends BaseFragment implements DialogChooseListener {

    private View view;

    private ImageView iv_header;
    private TextView tv_name;

    /**
     * 等级
     */
    private TextView tv_bt_rank;
    /**
     * 充值
     */
    private TextView tv_bt_recharge;
    /**
     * 已经购买的
     */
    private TextView tv_btmine_buy;
    /**
     * 收藏
     */
    private TextView tv_btmine_collect;
    private ImageView ima_recharge;

    /**
     * 检查更新
     */
    private TextView tv_update;

    /**
     * 客服
     */
    private TextView tv_kefu;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fm_mine, null);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initView();
        initData();
        setListener();
    }

    @Override
    public void onResume() {
        super.onResume();
        String sex = SharedUtil.getString(PubConst.KEY_SEX, "0");
        MyUtil.showLog("========="+sex);
        if (TextUtils.equals(sex, "1")) {
            iv_header.setBackgroundResource(R.drawable.man);
        } else if (TextUtils.equals(sex, "2")) {
            iv_header.setBackgroundResource(R.drawable.woman);
        }
    }

    @Override
    public void initTitle() {

    }

    @Override
    public void initView() {
        tv_bt_rank = (TextView) view.findViewById(R.id.tv_bt_rank);
        tv_bt_recharge = (TextView) view.findViewById(R.id.tv_bt_recharge);
        tv_btmine_buy = (TextView) view.findViewById(R.id.tv_btmine_buy);
        tv_btmine_collect = (TextView) view.findViewById(R.id.tv_btmine_collect);
        ima_recharge = (ImageView) view.findViewById(R.id.ima_recharge);
        iv_header = (ImageView) view.findViewById(R.id.iv_header);
        tv_name = (TextView) view.findViewById(R.id.tv_name);
        if (TextUtils.isEmpty(SharedUtil.getString("name", ""))) {
            tv_name.setText("尤果圈主角");
        } else {
            tv_name.setText(SharedUtil.getString("name", ""));
        }
        tv_kefu = (TextView) view.findViewById(R.id.tv_kefu);
        tv_update = (TextView) view.findViewById(R.id.tv_update);

    }

    @Override
    public void initData() {
        tv_bt_rank.setText(PublicUtil.formatTextView(mActivity, "1", "\n等级", R.style.textstyle_14_ff6633, R.style.textstyle_14_666666, 2));
        tv_bt_recharge.setText(PublicUtil.formatTextView(mActivity, "会员", "\n充值", R.style.textstyle_14_ff6633, R.style.textstyle_14_666666, 2));
        Glide.with(mActivity).load("http://7xwwfr.com1.z0.glb.clouddn.com/recharge_baoqi_bg.png").into(ima_recharge);
    }

    @Override
    public void setListener() {

        tv_bt_rank.setOnClickListener(this);
        tv_bt_recharge.setOnClickListener(this);
        tv_btmine_buy.setOnClickListener(this);
        tv_btmine_collect.setOnClickListener(this);
        ima_recharge.setOnClickListener(this);
        tv_name.setOnClickListener(this);
        tv_kefu.setOnClickListener(this);
        tv_update.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {
            case R.id.tv_bt_rank://等级
                skip(RechargeActivity.class, false);
                break;
            case R.id.tv_bt_recharge://充值

                skip(RechargeActivity.class, false);
                break;
            case R.id.tv_btmine_buy://已购买的
                skip(AlreadyBuyActivity.class, false);
                break;
            case R.id.tv_btmine_collect://收藏
                skip(CollectActivity.class, false);
                break;
            case R.id.ima_recharge://图片点击
//                DialogUtil.showPlaytour(mActivity, "id", "我的傻逼崔", this);
//                DialogUtil.showSingleText(mActivity,"50",this);
                break;
            case R.id.tv_name:
                VipDialog.getInputDialog(mActivity, this, "请输入名字", tv_name.getText().toString(), "");
                break;
            case R.id.tv_kefu:
                skip(KeFuActivity.class, false);
                break;
            case R.id.tv_update:
                requestLoginData(1);
                break;

            default:
                break;
        }
    }

    @Override
    public void getData(String data) {

    }

    @Override
    public void getInput(String data) {
        tv_name.setText(data);
        SharedUtil.setString("name", data);
        HideKeyboard(mActivity.getWindow().peekDecorView());
    }

    public static void HideKeyboard(View v) {
        InputMethodManager imm = (InputMethodManager) v.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
        if (imm.isActive()) {
            imm.hideSoftInputFromWindow(v.getApplicationWindowToken(), 0);

        }
    }

    /**
     * 请求登录接口http://youguo.fzjydqg.com/index.php/Api/User/login/version/2/devicenumber/36521/channel_name/Y001/sex/1
     */
    private void requestLoginData(final int key) {
        String sex = SharedUtil.getString(PubConst.KEY_SEX, "");
//        1男性 0 女性 可选参数
        String input_sex = "1";
        if (TextUtils.equals("2", sex)) {
            input_sex = "0";
        }
        MyUtil.showLog("=======" + input_sex);
        OkHttpUtils.get().url(HttpUrl.API.LOGIN).addParams("version", AppUtils.getVersionCode(getActivity()) + "").addParams("devicenumber", AppUtils.getDevice()).addParams("channel_name", AppUtils.getChannelID()).addParams("sex", input_sex).build().execute(new Callback() {
            @Override
            public Object parseNetworkResponse(Response response) throws IOException {
                final LoginResponseBean responseBean = new Gson().fromJson(response.body().string(), LoginResponseBean.class);
                MyUtil.showLog("登录陈宫===" + responseBean);
                mActivity.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        LoginData data = responseBean.getData();
                        if (data != null) {
                            SharedUtil.setString(PubConst.KEY_UID, data.getUserid());
                            if (TextUtils.equals(data.getIsUpdate(), "1"))//需要更新
                            {
                                doClientUpdate(data);
                            } else {
                                if (key == 1) {
                                    ToastUtil.showToast("您当前版本已经是最新版本");
                                }
                            }

                        }
                    }
                });

                return null;
            }


            @Override
            public void onError(Request request, Exception e) {

            }

            @Override
            public void onResponse(Object response) {

            }
        });
    }

    /**
     * 升级操作
     */
    private void doClientUpdate(LoginData data) {
        if (mActivity.isFinishing()) { // 如果页面不在了 直接退出
            return;
        }
        final String downloadUrl = data.getDownload();
        DialogUtil.showReminder(getActivity(), "更新提示：", "有新的版本，是否更新？", "取消", "确定",
                new ConfirmDialog() {
                    @Override
                    public void onOKClick(Bundle data) {
                        diashowpressBar(downloadUrl);
                    }

                    @Override
                    public void onCancleClick() {

                    }
                });
    }

    /**
     * 下载apk
     *
     * @param downloadUrl
     */
    private void diashowpressBar(String downloadUrl) {
//        downloadUrl = "http://kgtms.rybbaby.com/upload/apk/homeschool_home_1.3.3.apk";
        ClientUpgrade cuapk = new ClientUpgrade(mActivity);
        cuapk.downloadApk(downloadUrl, new ClientUpgrade.ClientUpgradeCallback()

        {
            @Override
            public void onSuccess() {

            }

            @Override
            public void onCancel() {

            }

            @Override
            public void onFailed() {
                ToastUtil.showToast("下载失败");
            }

        });
    }

}

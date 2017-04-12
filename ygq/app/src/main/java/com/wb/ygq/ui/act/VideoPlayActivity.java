package com.wb.ygq.ui.act;

import android.content.Context;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.media.MediaPlayer;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;
import com.commit451.nativestackblur.NativeStackBlur;
import com.google.gson.Gson;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;
import com.wb.ygq.R;
import com.wb.ygq.bean.CeshiBean;
import com.wb.ygq.bean.MediaInfor;
import com.wb.ygq.bean.VideoContentBean;
import com.wb.ygq.ui.adapter.VideoPlayAdapter;
import com.wb.ygq.ui.base.BaseActivity;
import com.wb.ygq.utils.HttpUrl;
import com.wb.ygq.utils.PublicUtil;
import com.wb.ygq.utils.SharedUtil;
import com.wb.ygq.utils.ToastUtil;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.Callback;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Formatter;
import java.util.List;
import java.util.Locale;
import java.util.Timer;
import java.util.TimerTask;

public class VideoPlayActivity extends BaseActivity {
    /**
     * 视频
     */
    private String path = "";
    private String title = "";
    private String endTime = "";
    private boolean mIsHandPause = false;
    private static final int MSG_SURFACE_PREPARE = 0x00000000;
    private static final int MSG_SURFACE_START = 0x00000001;
    private static final int MSG_SURFACE_DESTORY = 0x00000003;

    private static final int MSG_SCREEN_FULL = 0x00000004;
    private static final int MSG_SCREEN_WRAP = 0x00000005;

    private static final int MSG_UPDATE_PROGRESS = 0x00000006;
    private static final int MSG_MEDIA_CONTROLLER_HIDE = 0x00000007;

    private static final String KEY_CURRENT_POSITION = "KEY_CURRENT_POSITION";

    private int mResumePostion = 0;
    private int mDuration = -1;
    private Timer mServerTimer = null;
    private Timer mControllerTimer = null;

    /**
     * 由于mediaplay为异步加载，防止程序进入后台后播放
     * onPause()时，记录当前播放状态，重新onResume()恢复之前状态
     * mIsPrepred 参数为onPause()判断当前是否初始化完成
     */
    private boolean mIsOnPauseStatus = false;  //记录onPause()之前的播放状态 播放或者暂停
    private boolean mIsPrepred = false;        //记录onPause()之前 视频是否初始化完成
    private boolean mIsBackPrepared = false;           //由于mediaplay是异步加载，当home时可能会在后台播放的可能

    private VideoView mVideoView;
    private MediaPlayer mMediaPlayer;
    private RelativeLayout mTop_Controller;
    private RelativeLayout mBotton_Controller;
    private RelativeLayout mRela_Layout;
    private LinearLayout mLinear_Full_Wrap;
    private ImageView mImage_Full_Screen;
    private ImageView mImage_Back, iv_empty, iv_no_empty;
    private TextView mText_Current;
    private TextView mText_Durtion;
    private SeekBar mSeekBar;
    private ProgressBar mPro_Buffer;
    private ImageView mImage_PlayOrPause;
    private TextView mText_Title;

    private LinearLayout ll_bottom_layout;

    /**
     * 评论
     */
    private RecyclerView recycle_comment;

    private VideoPlayAdapter adapter;
    private TextView tv_input;
    /**
     * 存储更多内容
     */
    private List<CeshiBean> llList = new ArrayList<>();
    private List<VideoContentBean.DataBean.CommentListBean> dataList = new ArrayList<>();

    //输入框
    private RelativeLayout rl_input;
    private EditText et_input;
    private TextView tv_button_send;

    private VideoContentBean mVideoContentBean;
    private String id;

    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case MSG_SURFACE_PREPARE:
                    MediaInfor mediaInfor = (MediaInfor) msg.obj;
                    setDataSource(mediaInfor.mPath, mediaInfor.mName);
                    break;

                case MSG_SURFACE_START:
                    mIsPrepred = true;
                    mPro_Buffer.setVisibility(View.GONE);
                    mImage_PlayOrPause.setImageResource(R.drawable.jc_pause_normal);
                    mImage_PlayOrPause.setVisibility(View.VISIBLE);
                    mVideoView.setBackgroundColor(getResources().getColor(android.R.color.transparent));
                    mVideoView.start();
                    startProgressTimer();
                    startControllerShowOrHide();
                    break;

                case MSG_SURFACE_DESTORY:
                    if (mBotton_Controller.getVisibility() == View.VISIBLE) {
                        mediaControllerHide();
                    } else {
                        mediaControllerShow();
                    }
                    break;

                case MSG_SCREEN_FULL:
                    screenFullModeUI();
                    break;

                case MSG_SCREEN_WRAP:
                    screenWrapModeUI();
                    break;

                case MSG_UPDATE_PROGRESS:
                    setProgressController(0);
                    break;

                case MSG_MEDIA_CONTROLLER_HIDE:
                    mediaControllerHide();
                    break;

                default:
                    break;
            }
        }
    };

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video_play);
        initView();
        initData();
        setListener();
    }

    @Override
    public void initTitle() {

    }

    @Override
    public void initView() {

        id = SharedUtil.getString("VideoId", "");

        mVideoView = (VideoView) findViewById(R.id.surfaceview);
        ll_bottom_layout = (LinearLayout) findViewById(R.id.ll_bottom_layout);
        iv_empty = (ImageView) findViewById(R.id.iv_empty);
        iv_no_empty = (ImageView) findViewById(R.id.iv_no_empty);

        recycle_comment = (RecyclerView) findViewById(R.id.recycle_comment);
        tv_input = (TextView) findViewById(R.id.tv_input);
        tv_button_send = (TextView) findViewById(R.id.tv_button_send);
        rl_input = (RelativeLayout) findViewById(R.id.rl_input);
        et_input = (EditText) findViewById(R.id.et_input);
    }


    @Override
    public void initData() {
        adapter = new VideoPlayAdapter(this);
        getNetDatas();
    }

    public void getNetDatas() {
        OkHttpUtils.get()
                .url(String.format(HttpUrl.API.VIDEO_CONTENT, id))
                .build()
                .execute(new Callback() {
                    @Override
                    public Object parseNetworkResponse(final Response response) throws IOException {
                        String data = response.body().string();
                        final String finalData = data;
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                mVideoContentBean = new Gson().fromJson(finalData, VideoContentBean.class);
                                adapter = new VideoPlayAdapter(VideoPlayActivity.this);
                                mVideoContentBean = new Gson().fromJson(finalData, VideoContentBean.class);
                                adapter.setHeadView(getHeadView());
                                recycle_comment.setHasFixedSize(true);
                                recycle_comment.setLayoutManager(new GridLayoutManager(VideoPlayActivity.this, 1));
                                recycle_comment.setAdapter(adapter);
                                List<VideoContentBean.DataBean.CommentListBean> commentList = mVideoContentBean.getData().getCommentList();
                                if (commentList != null && !commentList.isEmpty()) {
                                    dataList.addAll(commentList);
                                    adapter.updateItems(dataList);
                                }
                                adapter.updateItems(mVideoContentBean.getData().getCommentList());
                                path = mVideoContentBean.getData().getVideoMessage().getUrl();
                                title = mVideoContentBean.getData().getVideoMessage().getName();
                                endTime = mVideoContentBean.getData().getVideoMessage().getEndtime();
                                Glide.with(VideoPlayActivity.this).load(mVideoContentBean.getData().getVideoMessage().getImg()).asBitmap().into(new SimpleTarget<Bitmap>() {
                                    @Override
                                    public void onResourceReady(Bitmap resource, GlideAnimation<? super Bitmap> glideAnimation) {
                                        Bitmap process = NativeStackBlur.process(resource, 8);
                                        iv_empty.setImageBitmap(process);
                                    }
                                });
                                Glide.with(VideoPlayActivity.this).load(mVideoContentBean.getData().getVideoMessage().getImg()).into(iv_no_empty);
                                Log.e("TAGTAG", "path=" + path + "===title=" + title + "====endtime=" + endTime);
                                initMediaController();
                                resetProgressAndTimer();
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


    private void initMediaController() {
        mTop_Controller = (RelativeLayout) findViewById(R.id.top_media_controller);
        mBotton_Controller = (RelativeLayout) findViewById(R.id.bottom_media_controller);
        mText_Current = (TextView) findViewById(R.id.text_currentpostion);
        mText_Durtion = (TextView) findViewById(R.id.text_durtionposition);
        mImage_Full_Screen = (ImageView) findViewById(R.id.image_full_screen);
        mPro_Buffer = (ProgressBar) findViewById(R.id.image_buffer);
        mText_Title = (TextView) findViewById(R.id.text_title);
        mImage_PlayOrPause = (ImageView) findViewById(R.id.image_playorpause);
        mImage_PlayOrPause.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mVideoView.isPlaying()) {
                    mVideoView.pause();
                    mImage_PlayOrPause.setImageResource(R.drawable.vrinfo_play);
                    mIsHandPause = true;
                } else {
                    mVideoView.start();
                    mImage_PlayOrPause.setImageResource(R.drawable.jc_pause_normal);
                    mIsHandPause = false;
                }
            }
        });
        mLinear_Full_Wrap = (LinearLayout) findViewById(R.id.linear_full_or_wrap);
        mLinear_Full_Wrap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (getScreenOrientation() == Configuration.ORIENTATION_PORTRAIT) {
                    setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
                    return;
                }
                setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
            }
        });
        mImage_Back = (ImageView) findViewById(R.id.image_back);
        mImage_Back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (getScreenOrientation() == Configuration.ORIENTATION_PORTRAIT) {
                    finish();
                    return;
                }
                setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
            }
        });
        mSeekBar = (SeekBar) findViewById(R.id.progress);
        mSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
//                try {
//                    int time = progress * getDuration() / 100;
//                    mMediaPlayer.seekTo(time);
//                } catch (IllegalStateException e) {
//                    e.printStackTrace();
//                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                ToastUtil.showToast("请充值会员");
            }
        });
        final ImageView imageFristPlay = (ImageView) findViewById(R.id.image_frist_play);
        imageFristPlay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!isNetWorkEnable(VideoPlayActivity.this)) {
                    Toast.makeText(VideoPlayActivity.this, "网络连接不可用", Toast.LENGTH_SHORT).show();
                    return;
                }
                imageFristPlay.setVisibility(View.GONE);
                iv_empty.setVisibility(View.GONE);
                iv_no_empty.setVisibility(View.GONE);
                playMedia(path, title);
            }
        });
    }

    //设置全屏
    public void screenFullModeUI() {
//        mRela_Layout.setVisibility(View.GONE);
        ll_bottom_layout.setVisibility(View.GONE);
        mImage_Full_Screen.setImageResource(R.drawable.jc_shrink);
        setScreenLayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
    }

    //设置半屏
    public void screenWrapModeUI() {
//        mRela_Layout.setVisibility(View.VISIBLE);
        ll_bottom_layout.setVisibility(View.VISIBLE);
        mImage_Full_Screen.setImageResource(R.drawable.jc_enlarge);
        setScreenLayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, dip2px(this, 220));
    }

    //设置全屏参数
    private void setScreenLayoutParams(int width, int height) {
        RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(width, height);
        if (width == ViewGroup.LayoutParams.MATCH_PARENT && height == ViewGroup.LayoutParams.MATCH_PARENT) {
            layoutParams.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
            layoutParams.addRule(RelativeLayout.ALIGN_PARENT_TOP);
            layoutParams.addRule(RelativeLayout.ALIGN_PARENT_LEFT);
            layoutParams.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
        }
        mVideoView.setLayoutParams(layoutParams);
        mVideoView.requestFocus();
    }

    /**
     * 根据手机的分辨率from dp 的单位 转成为 px(像素)
     */
    public static int dip2px(Context context, float dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }


    private void startProgressTimer() {
        cancleControllerTimer();
        mServerTimer = new Timer();
        mServerTimer.schedule(new TimerTask() {
            @Override
            public void run() {
                try {
                    mHandler.sendEmptyMessage(MSG_UPDATE_PROGRESS);
                } catch (IllegalStateException e) {
                    e.printStackTrace();
                }
            }
        }, 0, 500);
    }

    private void mediaControllerHide() {
        mTop_Controller.setVisibility(View.GONE);
        mBotton_Controller.setVisibility(View.GONE);
        mImage_PlayOrPause.setVisibility(View.GONE);
        cancleControllerTimer();
    }

    private void cancleControllerTimer() {
        if (mServerTimer != null) {
            mServerTimer.cancel();
            mServerTimer = null;
        }
        if (mControllerTimer != null) {
            mControllerTimer.cancel();
            mControllerTimer = null;
        }
    }

    private void resetProgressAndTimer() {
        mDuration = 0;
        mSeekBar.setProgress(0);
        mSeekBar.setSecondaryProgress(0);
        mText_Current.setText("00:00");
        mText_Durtion.setText(endTime);
    }

    /**
     * 变换视频
     *
     * @param path
     * @param name
     */
    public void playMedia(String path, String name) {
        if (!isNetWorkEnable(this)) {
            Toast.makeText(this, "网络连接不可用", Toast.LENGTH_SHORT).show();
            return;
        }
        Message msg = Message.obtain();
        MediaInfor mediaInfor = new MediaInfor();
        mediaInfor.mName = name;
        mediaInfor.mPath = path;
        msg.obj = mediaInfor;
        msg.what = MSG_SURFACE_PREPARE;
        mHandler.sendMessage(msg);
    }

    /**
     * 网络是否可用
     */
    public static boolean isNetWorkEnable(Context cxt) {
        ConnectivityManager connectivity = (ConnectivityManager) cxt.getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connectivity == null) {
        } else {
            NetworkInfo[] info = connectivity.getAllNetworkInfo();
            if (info != null) {
                for (int i = 0; i < info.length; i++) {
                    if (info[i].getState() == NetworkInfo.State.CONNECTED) {
                        return true;
                    }
                }
            }
        }
        return false;
    }


    private int getScreenOrientation() {
        return getResources().getConfiguration().orientation;
    }

    private View.OnTouchListener mOnTouchListener = new View.OnTouchListener() {
        @Override
        public boolean onTouch(View v, MotionEvent event) {
            int action = event.getAction();
            switch (action) {
                case MotionEvent.ACTION_DOWN:
                    mHandler.sendEmptyMessage(MSG_SURFACE_DESTORY);
                    break;
                default:
                    break;
            }
            return false;
        }
    };

    private MediaPlayer.OnErrorListener mOnErrorListener = new MediaPlayer.OnErrorListener() {
        @Override
        public boolean onError(MediaPlayer mp, int what, int extra) {
            ToastUtil.showToast("播放失败", Toast.LENGTH_SHORT);
            return true;
        }
    };

    private MediaPlayer.OnPreparedListener mOnPreparedListener = new MediaPlayer.OnPreparedListener() {
        @Override
        public void onPrepared(MediaPlayer mp) {
            if (mIsBackPrepared) {
                mVideoView.pause();
                mIsBackPrepared = false;
            } else {
                mHandler.sendEmptyMessage(MSG_SURFACE_START);
            }
            if (mMediaPlayer == null) {
                mMediaPlayer = mp;
                mMediaPlayer.setOnInfoListener(mOnInfoListener);
                mMediaPlayer.setOnBufferingUpdateListener(mOnBufferingUpdateListener);
            }
        }

    };
    private MediaPlayer.OnBufferingUpdateListener mOnBufferingUpdateListener = new MediaPlayer.OnBufferingUpdateListener() {
        @Override
        public void onBufferingUpdate(MediaPlayer mp, int percent) {
            if (mServerTimer != null && percent > 0) {
                setProgressController(percent);
            }
        }
    };

    private void setProgressController(int percent) {
        int currentPostion = 0;
        int duration = -1;
        try {
            currentPostion = mVideoView.getCurrentPosition();
            duration = getDuration();
        } catch (IllegalStateException e) {
            e.printStackTrace();
        }
        int progress = currentPostion * 100 / (duration == 0 ? 1 : duration);
        setProgressAndTime(currentPostion, duration, progress, percent);
    }

    public int getDuration() {
        int du = mVideoView.getDuration();
        int duration = du != -1 ? du : mDuration;  //home键恢复后 有可能拿不到总长度值 故
        return duration;
    }

    private void setProgressAndTime(int current, int duration, int progress, int secProgress) {
        mSeekBar.setProgress(progress > 0 ? progress : 0);
        if (secProgress > 0) {
            mSeekBar.setSecondaryProgress(secProgress);
        }
        mText_Current.setText(stringForTime(current));
        mText_Durtion.setText(stringForTime(duration));
    }

    public String stringForTime(int timeMs) {
        if (timeMs <= 0 || timeMs >= 24 * 60 * 60 * 1000) {
            return "00:00";
        }
        int totalSeconds = timeMs / 1000;
        int seconds = totalSeconds % 60;
        int minutes = (totalSeconds / 60) % 60;
        int hours = totalSeconds / 3600;
        StringBuilder stringBuilder = new StringBuilder();
        Formatter mFormatter = new Formatter(stringBuilder, Locale.getDefault());
        if (hours > 0) {
            return mFormatter.format("%d:%02d:%02d", hours, minutes, seconds).toString();
        } else {
            return mFormatter.format("%02d:%02d", minutes, seconds).toString();
        }
    }

    private MediaPlayer.OnInfoListener mOnInfoListener = new MediaPlayer.OnInfoListener() {
        @Override
        public boolean onInfo(MediaPlayer mp, int what, int extra) {
            switch (what) {
                case MediaPlayer.MEDIA_INFO_BUFFERING_START:
                    mPro_Buffer.setVisibility(View.VISIBLE);
                    if (mImage_PlayOrPause.getVisibility() == View.VISIBLE) {
                        mImage_PlayOrPause.setVisibility(View.GONE);
                    }
                    break;

                case MediaPlayer.MEDIA_INFO_BUFFERING_END:
                    if (mVideoView.isPlaying() || mIsHandPause) {
                        mPro_Buffer.setVisibility(View.GONE);
                        mImage_PlayOrPause.setVisibility(mBotton_Controller.getVisibility() == View.VISIBLE ? View.VISIBLE : View.GONE);
                    }
                    break;

                default:
                    break;
            }
            return true;
        }
    };
    private MediaPlayer.OnCompletionListener mOnCompletionListener = new MediaPlayer.OnCompletionListener() {
        @Override
        public void onCompletion(MediaPlayer mp) {
            if (mp.getDuration() != -1 && mp.getCurrentPosition() >= mp.getDuration() - 1000) {
                cancleControllerTimer();
                mImage_PlayOrPause.setImageResource(R.drawable.vrinfo_play);
                mediaControllerShow();
                Toast.makeText(VideoPlayActivity.this, "播放完成", Toast.LENGTH_LONG).show();

            }

        }
    };

    private void mediaControllerShow() {
        cancleControllerTimer();
        mTop_Controller.setVisibility(View.VISIBLE);
        mBotton_Controller.setVisibility(View.VISIBLE);
        mImage_PlayOrPause.setVisibility(mPro_Buffer.getVisibility() == View.VISIBLE ? View.GONE : View.VISIBLE);
        mImage_PlayOrPause.setImageResource(mVideoView.isPlaying() ? R.drawable.jc_pause_normal : R.drawable.vrinfo_play);
        startProgressTimer();
        startControllerShowOrHide();
    }

    private void startControllerShowOrHide() {
        mControllerTimer = new Timer();
        mControllerTimer.schedule(new TimerTask() {
            @Override
            public void run() {
                mHandler.sendEmptyMessage(MSG_MEDIA_CONTROLLER_HIDE);
            }
        }, 3000, 1);
    }

    public void setDataSource(String path, String name) {
        if (TextUtils.isEmpty(path)) return;
        mPro_Buffer.setVisibility(View.VISIBLE);
        mImage_PlayOrPause.setVisibility(View.GONE);
        resetProgressAndTimer();
        mVideoView.requestFocus();
        mVideoView.setBackgroundColor(0xFF333333);
        try {
            setListener();
            File localFile = new File(path);
            if (localFile.isFile()) {
                mVideoView.setVideoURI(Uri.fromFile(localFile));
            } else {
                mVideoView.setVideoPath(path);
            }
            mVideoView.start();
            mText_Title.setText(name);
        } catch (IllegalStateException e) {
            e.printStackTrace();
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        int value = getScreenOrientation();
        if (value == Configuration.ORIENTATION_LANDSCAPE) {
            mHandler.sendEmptyMessage(MSG_SCREEN_FULL);
        } else if (value == Configuration.ORIENTATION_PORTRAIT) {
            mHandler.sendEmptyMessage(MSG_SCREEN_WRAP);
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if (getScreenOrientation() == Configuration.ORIENTATION_LANDSCAPE) {
                setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
                return false;
            }
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    protected void onPause() {
        super.onPause();
        mIsBackPrepared = true;
        mIsOnPauseStatus = mVideoView.isPlaying();
        try {
            if (mVideoView != null) {
                mVideoView.pause();
                mIsHandPause = true;
                mResumePostion = getMediaCurrentPostion();
            }
        } catch (IllegalStateException e) {
            e.printStackTrace();
        }
    }

    private int getMediaCurrentPostion() {
        if (mVideoView != null) {
            return mVideoView.getCurrentPosition();
        }
        return 0;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mVideoView != null) {
            mVideoView = null;
        }
        cancleControllerTimer();
    }

    @Override
    public void setListener() {
        mVideoView.setOnTouchListener(mOnTouchListener);
        mVideoView.setOnErrorListener(mOnErrorListener);
        mVideoView.setOnCompletionListener(mOnCompletionListener);
        mVideoView.setOnPreparedListener(mOnPreparedListener);
        tv_input.setOnClickListener(this);
        tv_button_send.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {
            case R.id.tv_input://点击弹出输入框
                inPutComment();
                break;
            case R.id.tv_button_send://发送按钮
                String etString = et_input.getText().toString().trim();
                if (TextUtils.isEmpty(etString)) {
                    ToastUtil.showToast("请输入您的评论内容");
                    return;
                } else {
                    saveTOList(etString);
                }
                break;

            default:
                break;
        }
    }

    /**
     * 存到集合
     *
     * @param etString
     */
    private void saveTOList(String etString) {
        VideoContentBean.DataBean.CommentListBean cb = new VideoContentBean.DataBean.CommentListBean();
        cb.setImg("http://shtml.asia-cloud.com/ZZSY/list_test1.png");
        cb.setMessage(etString);
        cb.setName("我");
        cb.setTime("刚刚");
        dataList.add(cb);
        adapter.updateItems(dataList);
        rl_input.setVisibility(View.GONE);
        et_input.setText("");
        colseKeyBoard();
    }

    /**
     * 关闭软键盘
     */
    private void colseKeyBoard() {
        View view = getWindow().peekDecorView();
        if (view != null) {
            InputMethodManager inputmanger = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            inputmanger.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }

    /**
     * 输入评论
     */
    private void inPutComment() {
        rl_input.setVisibility(View.VISIBLE);
        et_input.setFocusable(true);
        et_input.requestFocus();
        et_input.setText("");
        InputMethodManager imm = (InputMethodManager) this.getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS);
    }

    /**
     * 头布局
     *
     * @return
     */
    public View getHeadView() {
        View v = LayoutInflater.from(this).inflate(R.layout.headview_videopalyer, null);
        LinearLayout ll_moreaddview = (LinearLayout) v.findViewById(R.id.ll_moreaddview);
        TextView tv_title_num = (TextView) v.findViewById(R.id.tv_title_num);
        for (int i = 0; i < mVideoContentBean.getData().getOrderVideo().size(); i++) {
            ll_moreaddview.addView(foramtSlideView(i));
        }
        String str = "\n" + mVideoContentBean.getData().getVideoMessage().getCount();
        tv_title_num.setText(PublicUtil.formatTextView(this, mVideoContentBean.getData().getVideoMessage().getName(), str, R.style.textstyle_14_666666, R.style.textstyle_10_5DBCF4, str.length()));
        return v;
    }

    /**
     * 左右滑动数据
     *
     * @param i
     * @return
     */
    private View foramtSlideView(final int i) {
        View v = LayoutInflater.from(this).inflate(R.layout.layout_ima_twotext, null);
        ImageView image_head = (ImageView) v.findViewById(R.id.image_head);
        Glide.with(this).load(mVideoContentBean.getData().getOrderVideo().get(i).getImg()).into(image_head);
        TextView text_title = (TextView) v.findViewById(R.id.text_title);
        text_title.setText(mVideoContentBean.getData().getOrderVideo().get(i).getTitle());
        TextView text_count = (TextView) v.findViewById(R.id.text_count);
        text_count.setText(mVideoContentBean.getData().getOrderVideo().get(i).getCount());
        image_head.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                id = mVideoContentBean.getData().getOrderVideo().get(i).getId();
                SharedUtil.setString("VideoId", id);
                recreate();
            }
        });
        return v;
    }

}

package com.zzh.reader.view;

import android.app.Activity;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.PixelFormat;
import android.util.AttributeSet;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.Animation;
import android.widget.AbsoluteLayout;
import android.widget.ImageView;
import android.widget.PopupWindow;

import com.zzh.reader.R;
import com.zzh.reader.util.EventUtils;
import com.zzh.zlibs.utils.ZUtils;

/**
 * 说明：BookView在被点击时会在WindowManager中添加一个AbsoluteLayout,然后再添加一个克隆体cover,在根据位置放置一个content。  ps:cover书的封面   content书的内容页
 * cover播放放大翻转动画  content播放放大动画
 * 再次点击，播放关闭书本动画
 * <p>
 * Created by jayce on 15-2-3.
 */
public class BookView extends ImageView implements Animation.AnimationListener {
    private boolean mIsOpen;
    private WindowManager mWindowManager;
    private AbsoluteLayout wmRootView;
    private PopupWindow pop;

    private ImageView cover;
    private ImageView content;

    private float scaleTimes;
    public static final int ANIMATION_DURATION = 1000;
    private int[] location = new int[2];

    private ContentScaleAnimation contentAnimation;
    private Rotate3DAnimation coverAnimation;

    private boolean isFirstload = true;
    private int animationCount = 0;  //动画加载计数器  0 默认  1一个动画执行完毕   2二个动画执行完毕
    //  动画播完后要进行处理

    private OnAnimationListener mListener;

    public void setListener(OnAnimationListener listener) {
        this.mListener = listener;
    }

    public BookView(Context context) {
        this(context, null);
    }

    public BookView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public BookView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        mWindowManager = (WindowManager) getContext().getSystemService(Context.WINDOW_SERVICE);
        initView();
        initListener();
    }

    void initView() {

        wmRootView = new AbsoluteLayout(getContext());
        pop = new PopupWindow(wmRootView, AbsoluteLayout.LayoutParams.MATCH_PARENT, AbsoluteLayout.LayoutParams.MATCH_PARENT, false);
    }

    void initListener() {
        setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!mIsOpen) {
                    openBook();
                }
            }
        });

        setOnLongClickListener(new OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                wmRootView.removeAllViews();
                EventUtils.sendEventDeleteShowBook();
                return true;
            }
        });
    }

    void initAnimation() {
        AccelerateInterpolator interpolator = new AccelerateInterpolator();

        getLocationInWindow(location);
        Context ctx = getContext();
        float scale1 = 0;
        float scale2 = 0;

        if (ctx instanceof Activity) {
            scale1 = ((Activity) ctx).getWindow().getDecorView().getMeasuredWidth() / (float) getWidth();
            scale2 = ((Activity) ctx).getWindow().getDecorView().getMeasuredHeight() / (float) getHeight();
        } else {
            scale1 = ZUtils.getDisplayWidth(getContext()) / (float) getWidth();
            scale2 = ZUtils.getDisplayHeight(getContext()) / (float) getHeight();
        }
        scaleTimes = scale1 > scale2 ? scale1 : scale2;  //计算缩放比例
        contentAnimation = new ContentScaleAnimation(location[0], location[1], scaleTimes, false);
        contentAnimation.setInterpolator(interpolator);
        contentAnimation.setDuration(ANIMATION_DURATION);
        contentAnimation.setFillAfter(true);
        contentAnimation.setAnimationListener(this);


        coverAnimation = new Rotate3DAnimation(0, -180, location[0], location[1], scaleTimes, false);
        coverAnimation.setInterpolator(interpolator);
        coverAnimation.setDuration(ANIMATION_DURATION);
        coverAnimation.setFillAfter(true);
        coverAnimation.setAnimationListener(this);


    }

    public void openBook() {
        if (isFirstload) {
            isFirstload = false;
            initAnimation();
        }
        mWindowManager.addView(wmRootView, getDefaultWindowParams());
        //wmRootView.setBackgroundColor(getResources().getColor(android.R.color.holo_blue_dark));
        cover = new ImageView(getContext());
        cover.setScaleType(getScaleType());
        cover.setImageDrawable(getDrawable());
        content = new ImageView(getContext());
        content.setScaleType(getScaleType());
//        content.setBackground(getResources().getDrawable(R.drawable.content));
        //content.setBackgroundDrawable(getResources().getDrawable(R.drawable.cover_default_new));
        content.setBackgroundColor(getResources().getColor(R.color.read_background_paperYellow));
        AbsoluteLayout.LayoutParams params = new AbsoluteLayout.LayoutParams(getLayoutParams());
        params.x = location[0];
        params.y = location[1];
        wmRootView.addView(content, params);
        wmRootView.addView(cover, params);

//        cover.setX(location[0]);
//        cover.setY(location[1]);
//        content.setX(location[0]);
//        content.setY(location[1]);

        //一个不合理的方案，把关闭书本动画放到这
        wmRootView.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mIsOpen) {
                    closeBook();
                }
            }
        });


        if (contentAnimation.getMReverse()) {
            contentAnimation.reverse();
        }

        if (coverAnimation.getMReverse()) {
            coverAnimation.reverse();
        }

        content.clearAnimation();
        content.startAnimation(contentAnimation);
        cover.clearAnimation();
        cover.startAnimation(coverAnimation);
    }

    public void closeBook() {
        if (!contentAnimation.getMReverse()) {
            contentAnimation.reverse();
        }

        if (!coverAnimation.getMReverse()) {
            coverAnimation.reverse();
        }

        content.clearAnimation();
        content.startAnimation(contentAnimation);
        cover.clearAnimation();
        cover.startAnimation(coverAnimation);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    private WindowManager.LayoutParams getDefaultWindowParams() {
        WindowManager.LayoutParams params = new WindowManager.LayoutParams(
                WindowManager.LayoutParams.MATCH_PARENT,
                WindowManager.LayoutParams.MATCH_PARENT,
                0, 0,
                WindowManager.LayoutParams.TYPE_APPLICATION_PANEL,
                WindowManager.LayoutParams.FLAG_FULLSCREEN,
                PixelFormat.RGBA_8888);

        return params;
    }


    @Override
    public void onAnimationStart(Animation animation) {

    }

    @Override
    public void onAnimationEnd(Animation animation) {
        if (!mIsOpen) {
            animationCount++;
            if (animationCount >= 2) {
                mIsOpen = true;
                if (mListener != null) {
                    mListener.onAnimationOpenEnd(this);
                }
            }
        } else {
            animationCount--;

            if (animationCount <= 0) {
                mIsOpen = false;
                mWindowManager.removeView(wmRootView);
                if (mListener != null) {
                    mListener.onAnimationCloseEnd(this);
                }
            }
        }
    }

    @Override
    public void onAnimationRepeat(Animation animation) {

    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
    }

    public interface OnAnimationListener {
        /**
         * 打开动画执行结束时，执行此方法
         *
         * @param bookView
         */
        void onAnimationOpenEnd(BookView bookView);

        /**
         * 关闭动画执行结束时，执行此方法
         *
         * @param bookView
         */
        void onAnimationCloseEnd(BookView bookView);
    }
}

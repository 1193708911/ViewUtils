package supersports.com.ssports;

import android.content.Context;
import android.os.Handler;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.LinearLayout;

import com.bumptech.glide.Glide;

import java.util.List;

import supersports.com.net.R;


public class AutoViewPager extends FrameLayout implements OnPageChangeListener {
    private List<String> imageUrls;
    private ViewPager mViewPager;
    private LinearLayout mDotsLayout;
    private static final long DELAY_TIME = 2000;
    Handler UIHandler = new Handler();

    public AutoViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
        LayoutInflater.from(context).inflate(R.layout.auto_viewpager_item, this);
        initViewLayout();
    }


    private void initViewLayout() {
        mViewPager = findViewById(R.id.viewPager);
        mDotsLayout = findViewById(R.id.mDotsLayout);
        mViewPager.setOnTouchListener(new OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {

                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN | MotionEvent.ACTION_MOVE:
                        UIHandler.removeCallbacks(runnable);
                        break;
                    case MotionEvent.ACTION_UP:
                        UIHandler.postDelayed(runnable, DELAY_TIME);
                        break;
                }
                return false;
            }
        });
    }

    Runnable runnable = new Runnable() {
        @Override
        public void run() {
            int index = mViewPager.getCurrentItem();
            index++;
            mViewPager.setCurrentItem(index);
            UIHandler.postDelayed(runnable, DELAY_TIME);
        }
    };


    private PagerAdapter mPagerAdapter = new PagerAdapter() {

        @Override
        public int getCount() {
            // TODO Auto-generated method stub
            return Integer.MAX_VALUE;
        }

        @Override
        public boolean isViewFromObject(View arg0, Object arg1) {
            // TODO Auto-generated method stub
            return arg0 == arg1;
        }


        public void destroyItem(ViewGroup container, int position, Object object) {
            ImageView imageView = (ImageView) object;
            container.removeView(imageView);
        }

        ;

        public Object instantiateItem(ViewGroup container, int position) {
            ImageView imageView = new ImageView(getContext());
            container.addView(imageView);
            imageView.setScaleType(ScaleType.FIT_XY);
            imageView.getLayoutParams().width = getResources().getDisplayMetrics().widthPixels;
            imageView.getLayoutParams().height = 200;
            Glide.with(getContext()).load(imageUrls.get(position % 3)).into(imageView);
            return imageView;

        }

    };


    public void setUpWithImages(List<String> imageUrls) {
        this.imageUrls = imageUrls;
        setUpWithDots();
        mViewPager.setAdapter(mPagerAdapter);
        mViewPager.setOnPageChangeListener(this);
        UIHandler.postDelayed(runnable, DELAY_TIME);
    }


    private void setUpWithDots() {

        if (imageUrls == null) {
            throw new NullPointerException("图片地址为空");
        }
        for (int index = 0; index < imageUrls.size(); index++) {

            ImageView imageDot = new ImageView(getContext());
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT);
            imageDot.setImageResource(index == 0 ? R.mipmap.img_select : R.mipmap.img_normal);
            mDotsLayout.addView(imageDot, params);

        }
    }


    @Override
    protected void onDetachedFromWindow() {
        // TODO Auto-generated method stub
        super.onDetachedFromWindow();
        UIHandler.removeCallbacks(runnable);

    }


    private void resetImgDots(int index) {
        for (int pos = 0; pos < mDotsLayout.getChildCount(); pos++) {
            if (pos == index) {
                ((ImageView) mDotsLayout.getChildAt(pos)).setImageResource(R.mipmap.img_select);
            } else {
                ((ImageView) mDotsLayout.getChildAt(pos)).setImageResource(R.mipmap.img_normal);
            }
        }
    }


    @Override
    public void onPageScrollStateChanged(int arg0) {

    }


    @Override
    public void onPageScrolled(int arg0, float arg1, int arg2) {

    }


    @Override
    public void onPageSelected(int position) {
        resetImgDots(position % imageUrls.size());
    }


}

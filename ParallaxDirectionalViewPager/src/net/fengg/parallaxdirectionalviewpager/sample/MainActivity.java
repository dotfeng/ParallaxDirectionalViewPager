package net.fengg.parallaxdirectionalviewpager.sample;

import java.util.ArrayList;
import java.util.List;

import net.fengg.parallaxdirectionalviewpager.DirectionalViewPager;
import net.fengg.parallaxdirectionalviewpager.R;
import net.fengg.parallaxdirectionalviewpager.DirectionalViewPager.OnPageChangeListener;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.ViewGroup.LayoutParams;
import android.widget.ImageView;
import android.widget.LinearLayout;


public class MainActivity extends FragmentActivity implements
		OnPageChangeListener {
	
	private DirectionalViewPager mDirectionalViewPager;
	private int mSize;
	private int mCurrentItem;
	private ImageView mBg;
	
	//or DirectionalViewPager.HORIZONTAL
	private final static int DIRECTION = DirectionalViewPager.VERTICAL;
	private final static float SCALE = 1.2f;
	private final static float OVER_PERCENTAGE = 3;
	
	private List<ImageView> imageViewIndicatorList = null;
	private LinearLayout linearLayoutIndicator;
	
	private void initIndicator() {
		imageViewIndicatorList = new ArrayList<ImageView>();
		for (int i = 0; i < 4; i++) {
			ImageView iv = new ImageView(this);
			iv.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, 
					LinearLayout.LayoutParams.WRAP_CONTENT));
			iv.setPadding(0, 15, 0, 0);
			if (i == 0) {
				// 默认选中第一张图片
				iv.setImageResource(R.drawable.shape_sel);
			} else {
				iv.setImageResource(R.drawable.shape_nor);
			}
			imageViewIndicatorList.add(iv);
			linearLayoutIndicator.addView(iv);
		}
	}
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		// Set up the pager
		mDirectionalViewPager = (DirectionalViewPager) findViewById(R.id.pager);
		linearLayoutIndicator = (LinearLayout) findViewById(R.id.indicator_ll);
		mBg = (ImageView) findViewById(R.id.mainBgImage);
		if(DIRECTION == DirectionalViewPager.HORIZONTAL) {	
			mBg.setScaleX(SCALE);
		}else {
			mBg.setScaleY(SCALE);
		}
		
		mDirectionalViewPager.setAdapter(new TestFragmentAdapter(
				getSupportFragmentManager()));
		mDirectionalViewPager.setOrientation(DIRECTION);// 设置方向垂直即可。
		mDirectionalViewPager.setOnPageChangeListener(this);
		initIndicator();
		
		
		int screenHeight = getScreenHeigh();
		int screenWidth = getScreenWidth();
		if(DIRECTION == DirectionalViewPager.HORIZONTAL) {
			//Horizontal scroll  ↓
			mSize = (int) (screenWidth * OVER_PERCENTAGE/100);
		}else {
			mSize = (int) (screenHeight * OVER_PERCENTAGE/100);
		}
		
		mCurrentItem = 0;
	}

	@Override
	protected void onSaveInstanceState(Bundle outState) {
		//super.onSaveInstanceState(outState);
	}
	public int getScreenHeigh() {
		DisplayMetrics dm = getResources().getDisplayMetrics();
		return dm.heightPixels;
	}
	public int getScreenWidth() {
		DisplayMetrics dm = getResources().getDisplayMetrics();
		return dm.widthPixels;
	}
	@Override
	public void onPageScrollStateChanged(int state) {
		Log.i("way", "onPageScrollStateChanged...  state = " + state
				+ ",  mCurrentItem = " + mCurrentItem);
		if (state == ViewPager.SCROLL_STATE_IDLE) {
			if(DIRECTION == DirectionalViewPager.HORIZONTAL) {	
				mBg.setX(-mCurrentItem * mSize);
			}else {
				mBg.setY(-mCurrentItem * mSize);
			}
		}
	}

	@Override
	public void onPageScrolled(int position, float positionOffset,
			int positionOffsetPixels) {
		Log.i("way", "onPageScrolled...  position=" + position
				+ ", positionOffset=" + positionOffset
				+ ", positionOffsetPixels=" + positionOffsetPixels);
		if (positionOffset == 0.0f)
			return;
		if(DIRECTION == DirectionalViewPager.HORIZONTAL) {			
			mBg.setX(-((position + positionOffset) * mSize));
		}else {
			mBg.setY(-((position + positionOffset) * mSize));
		}
	}

	@Override
	public void onPageSelected(int position) {
		Log.i("way", "onPageSelected....  position=" + position);
		mCurrentItem = position;
		
		for (int i = 0; i < imageViewIndicatorList.size(); i++) {
			if (position == i) {
				imageViewIndicatorList.get(i).setImageResource(
						R.drawable.shape_sel);
			} else {
				imageViewIndicatorList.get(i).setImageResource(
						R.drawable.shape_nor);
			}
		}
		
	}
}

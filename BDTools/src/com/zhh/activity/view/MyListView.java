package com.zhh.activity.view;

import com.zhh.sdkdabao.R;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.ListView;

/**
 * 作者： zzhh
 * 
 * 时间：@2017年1月10日 下午4:51:42
 * 
 */
public class MyListView extends ListView implements OnScrollListener {
	
	
	final int DOWNREFRESH = 1;
	final int UPREFRESH = 0;
	
	int startfirstItemIndex;
	int startlastItemIndex;
	
	int endfirstItemIndex;
	int endlastItemIndex;
	
	View view;
	Animation animation;
	Handler handler;
	Runnable run;
	Message message;
	
	Context context;
	Boolean init = true;
	
	public MyListView(Context context) {
		super(context);
		if(init){
			this.context = context;
			init();
			init = false;
		}
	}
	
	public MyListView(Context context, AttributeSet attrs) {
		super(context, attrs);
		if(init){
			this.context = context;
			init();
			init = false;
		}
	}
	
	public MyListView(final Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
		if(init){
			this.context = context;
			init();
			init = false;
		}
	}

	
	private void init(){
		setOnScrollListener(this);
		handler = new Handler(){
			@Override
			public void handleMessage(Message msg) {
				super.handleMessage(msg);
				
				int result = (Integer) msg.obj;
				
				switch (result) {
				case DOWNREFRESH:{
					view = getChildAt(getChildCount() - 1);
					break;
				}
				case UPREFRESH:{
					view = getChildAt(0);
					break;
				}
				default:
					break;
				}
				if (null != view ) {
					//加载动画
					animation = AnimationUtils.loadAnimation(context, result == DOWNREFRESH ? R.anim.list_anim_bottom_to_top : R.anim.list_anim_top_to_bottom);
//					animation = AnimationUtils.loadAnimation(context, R.anim.translate);
					view.startAnimation(animation);
				}
				
			}
		};
	}
	
	@Override
	public void onScrollStateChanged(AbsListView view, int scrollState) {
		
	}

	@Override
	public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
		
		startfirstItemIndex = firstVisibleItem;
		startlastItemIndex = firstVisibleItem + visibleItemCount - 1;
		
		// 判断向下或者向上滑动了
		if ((endfirstItemIndex > startfirstItemIndex) && (endfirstItemIndex > 0)) {
			RunThread(UPREFRESH);
		} else if ((endlastItemIndex < startlastItemIndex) && (endlastItemIndex > 0)) {
			RunThread(DOWNREFRESH);
		}
		
		endfirstItemIndex = startfirstItemIndex;
		endlastItemIndex = startlastItemIndex;
		
	}
	
	private void RunThread(final int state) {
		// TODO Auto-generated method stub
		run = new Runnable() {

			@Override
			public void run() {
				// TODO Auto-generated method stub
				message = handler.obtainMessage(1, state);
				handler.sendMessage(message);
			}
		};
		run.run();
	}

	

}

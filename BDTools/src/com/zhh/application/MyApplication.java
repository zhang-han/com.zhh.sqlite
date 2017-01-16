package com.zhh.application;

import java.util.ArrayList;
import java.util.List;

import com.zhh.domin.Student;
import com.zhh.sqlite.tools.DataBaseFactory;

import android.app.Application;


/**
 * 作者： zzhh
 * 
 * 时间：@2016年12月22日 下午5:00:16
 * 
 */
public class MyApplication extends Application{
	
	private static MyApplication application = null;
	
	public static MyApplication getInstance(){
		if(application == null){
			synchronized (application) {
				application = new MyApplication();
			}
		}
		return application;
	}
	
	
	@Override
	public void onCreate() {
		super.onCreate();
		
		List<Class<?>> list = new ArrayList<Class<?>>();
		list.add(Student.class);
//		list.add(Teacher.class);
//		list.add(School.class);
		new DataBaseFactory(this, 1, list);
		
	}

}

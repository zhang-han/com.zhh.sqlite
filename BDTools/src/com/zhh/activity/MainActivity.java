package com.zhh.activity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.zhh.activity.view.MyListView;
import com.zhh.adapter.tools.CommonAdapter;
import com.zhh.adapter.tools.CommonViewHolder;
import com.zhh.domin.Student;
import com.zhh.sdkdabao.R;
import com.zhh.sqlite.tools.BaseService;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * 作者： zzhh
 * 
 * 时间：@2016年12月22日 下午4:59:46
 * 
 */
public class MainActivity extends Activity{
	
	Activity activity;
	List<Student> list = new ArrayList<Student>();
	CommonAdapter<Student> adapter = null;
	MyListView listView;
	int num = -1;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		activity = this;
		setContentView(R.layout.activity_main);
		
		init();
		setClick();
		
	}
	
	private void init(){
		listView = (MyListView) findViewById(R.id.listView);
		
		list = BaseService.getInstance().findAll(Student.class);
		if(list == null)
			getData();
		
		adapter = new CommonAdapter<Student>(activity, list, R.layout.item_list_main_activity) {

			@Override
			public void setViewData(CommonViewHolder commonViewHolder, View currentView, Student item,
					int position) {
				// TODO Auto-generated method stub
				TextView tv_id = (TextView) commonViewHolder.get(commonViewHolder, currentView, R.id.tv_id);
				TextView tv_name = (TextView) commonViewHolder.get(commonViewHolder, currentView, R.id.tv_name);
				TextView tv_gender = (TextView) commonViewHolder.get(commonViewHolder, currentView, R.id.tv_gender);
				TextView tv_age = (TextView) commonViewHolder.get(commonViewHolder, currentView, R.id.tv_age);
				TextView tv_clazz = (TextView) commonViewHolder.get(commonViewHolder, currentView, R.id.tv_clazz);
				TextView tv_teacherName = (TextView) commonViewHolder.get(commonViewHolder, currentView, R.id.tv_teacherName);
				TextView tv_teacherId = (TextView) commonViewHolder.get(commonViewHolder, currentView, R.id.tv_teacherId);
				TextView tv_phone = (TextView) commonViewHolder.get(commonViewHolder, currentView, R.id.tv_phone);
				ImageView imageView = (ImageView) commonViewHolder.get(commonViewHolder, currentView, R.id.imageView);
				
				tv_id.setText("学号：" + item.getId());
				tv_name.setText("姓名：" + item.getName());
				tv_gender.setText("性别：" + (item.getGender() == 0 ? "男" : "女"));
				tv_age.setText("年龄：" + item.getAge());
				tv_clazz.setText("班级：" + item.getGrade() + "年级" + item.getClazz() + "班");
				tv_teacherName.setText("班主任：" + item.getTeacherName());
				tv_teacherId.setText("班主任ID：" + item.getTeacherId());
				tv_phone.setText("联系方式：" + item.getPhone());
				
				imageView.setImageResource(item.getPhoto());
			}
		};
		
		listView.setAdapter(adapter);
	}
	
	private void setClick(){
		
		listView.setOnItemClickListener(new OnItemClickListener() {

			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				Intent intent = new Intent(activity, StudentInfoActivity.class);
				intent.putExtra("student", (Serializable)list.get(position));
				intent.putExtra("position", position);
				startActivityForResult(intent, position);
				num = position;
			}
		});
		
	}
	
	private void getData(){
		
		list = new ArrayList<Student>();
		int[] photos = new int[]{R.mipmap.photo1, R.mipmap.photo2, R.mipmap.photo3, R.mipmap.photo4, R.mipmap.photo5, R.mipmap.photo6};
		String[] teacherNames = new String[]{"李华", "王勇", "玛丽莲", "张国裕", "赵云鹏"};
		for(int i = 1; i <= 100; i ++){
			Student student = new Student();
			
			student.setId(((long)(10000000 + (Math.random()*(89999999)))) + "");
			student.setName("张三" + i);
			student.setGender(i%2);
			student.setAge(10 + (int)(Math.random()*(5)));
			student.setPhone("13" + String.valueOf((int)(100000000 + Math.random()*(899999999))));
			student.setGrade(1 + (int)(Math.random()*(5)));
			student.setClazz(1 + (int)(Math.random()*(12)));
			student.setTeacherId((int)(100000 + (Math.random()*(899999))));
			student.setPhoto(photos[(int)(Math.random()*(5.9))]);
			student.setTeacherName(teacherNames[(int)(Math.random()*(4.9))]);
			
			list.add(student);
		}
		BaseService.getInstance().insert(list);
	
	}
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		
		if(num >= 0 && resultCode != -1){
			list.set(num, (Student)data.getSerializableExtra("student"));
			adapter.notifyDataSetChanged();
		}
		
		
	}
	
}

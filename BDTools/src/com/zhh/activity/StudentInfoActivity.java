package com.zhh.activity;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import com.zhh.domin.Student;
import com.zhh.sdkdabao.R;
import com.zhh.sqlite.tools.BaseService;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

/**
 * 作者： zzhh
 * 
 * 时间：@2017年1月12日 下午5:18:42
 * 
 */
public class StudentInfoActivity extends Activity{
	
	Activity activity;
	
	private Student student;
	
	private Button btn_submit;
	private EditText et_id, et_name, et_gender, et_age, et_clazz, et_teacherName, et_teacherId, et_phone;
	private ImageView imageView;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		activity = this;
		setContentView(R.layout.activity_student_info);
		
		init();
		setClick();
		
	}
	
	private void init(){
		
		btn_submit = (Button) findViewById(R.id.btn_submit);
		et_id = (EditText) findViewById(R.id.et_id);
		et_name = (EditText) findViewById(R.id.et_name);
		et_gender = (EditText) findViewById(R.id.et_gender);
		et_age = (EditText) findViewById(R.id.et_age);
		et_clazz = (EditText) findViewById(R.id.et_clazz);
		et_teacherName = (EditText) findViewById(R.id.et_teacherName);
		et_teacherId = (EditText) findViewById(R.id.et_teacherId);
		et_phone = (EditText) findViewById(R.id.et_phone);
		imageView = (ImageView) findViewById(R.id.imageView);
		
		student = (Student) getIntent().getSerializableExtra("student");
		
		
		if(student != null){
			et_id.setText("" + student.getId());
			et_name.setText("" + student.getName());
			et_gender.setText("" + (student.getGender() == 0 ? "男" : "女"));
			et_age.setText("" + student.getAge());
			et_clazz.setText("" + student.getGrade() + "年级" + student.getClazz() + "班");
			et_teacherName.setText("" + student.getTeacherName());
			et_teacherId.setText("" + student.getTeacherId());
			et_phone.setText("" + student.getPhone());
			
			imageView.setImageResource(student.getPhoto());
		}
		
	}
	
	private void setClick(){
		//性别
		et_gender.setOnTouchListener(new OnTouchListener() {
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				et_gender.setEnabled(false);
				final String[] str = new String[]{"男", "女"};
				dialog("性别", str, new MyCallBack() {
					@Override
					public void callBack(int position) {
						if(position >= 0){
							student.setGender(position);
							et_gender.setText(str[position]);
						}
						et_gender.setEnabled(true);
					}
				});
				return true;
			}
		});
		//班级
		et_clazz.setOnTouchListener(new OnTouchListener() {
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				et_clazz.setEnabled(false);
				
				String s1 = "select distinct grade from t_a_student order by grade ";
				String s2 = "select distinct clazz from t_a_student order by clazz ";
				List<Map<String, Object>> listGrade = BaseService.getInstance().queryForMaps(s1, null);
				List<Map<String, Object>> listClazz = BaseService.getInstance().queryForMaps(s2, null);
				
				final String[] grade = new String[listGrade.size()];
				final String[] clazz = new String[listClazz.size()];
				
				for (int i = 0; i < listGrade.size(); i ++) {
					grade[i] = listGrade.get(i).get("grade") + "年级";
				}
				for (int i = 0; i < listClazz.size(); i ++) {
					clazz[i] = listClazz.get(i).get("clazz") + "班级";
				}
				
				dialog("年级", grade, new MyCallBack() {
					@Override
					public void callBack(int position) {
						if(position >= 0){
							student.setGrade(Integer.valueOf(grade[position].substring(0, grade[position].length() - 2)));
							et_clazz.setText(grade[position]);
						
							dialog("班级", clazz, new MyCallBack() {
								@Override
								public void callBack(int position) {
									if(position < 0){
										position = 0;
									}
									student.setClazz(Integer.valueOf(clazz[position].substring(0, clazz[position].length() - 2)));
									et_clazz.setText(et_clazz.getText() + clazz[position]);
									et_clazz.setEnabled(true);
								}
							});
						} else {
							et_clazz.setEnabled(true);
						}
					}
				});
				return true;
			}
		});
		//班主任
		et_teacherName.setOnTouchListener(new OnTouchListener() {
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				et_teacherName.setEnabled(false);
				
				String s1 = "select distinct teacherName from t_a_student ";
				List<Map<String, Object>> listTeacherName = BaseService.getInstance().queryForMaps(s1, null);
				
				final String[] teacherName = new String[listTeacherName.size()];
				
				for (int i = 0; i < listTeacherName.size(); i ++) {
					teacherName[i] = listTeacherName.get(i).get("teacherName") + "";
				}
				
				dialog("班主任", teacherName, new MyCallBack() {
					@Override
					public void callBack(int position) {
						if(position >= 0){
							student.setTeacherName(teacherName[position]);
							et_teacherName.setText(teacherName[position]);
						}
						et_teacherName.setEnabled(true);
					}
				});
				return true;
			}
		});
		//班主任Id
		et_teacherId.setOnTouchListener(new OnTouchListener() {
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				et_teacherId.setEnabled(false);
				
				String s1 = "select distinct teacherId from t_a_student ";
				List<Map<String, Object>> listTeacherName = BaseService.getInstance().queryForMaps(s1, null);
				
				final String[] teacherId = new String[listTeacherName.size()];
				
				for (int i = 0; i < listTeacherName.size(); i ++) {
					teacherId[i] = listTeacherName.get(i).get("teacherId") + "";
				}
				
				dialog("班主任ID", teacherId, new MyCallBack() {
					@Override
					public void callBack(int position) {
						if(position >= 0){
							student.setTeacherId(Integer.valueOf(teacherId[position]));
							et_teacherId.setText(teacherId[position]);
						}
						et_teacherId.setEnabled(true);
					}
				});
				return true;
			}
		});
		//提交
		btn_submit.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				
				student.setName(et_name.getText() + "");
				student.setAge(Integer.valueOf(et_age.getText() + ""));
				student.setPhone(et_phone.getText() + "");
				
				Intent intent = new Intent();
				intent.putExtra("student", (Serializable)student);
				setResult(getIntent().getIntExtra("position", -1), intent);
				BaseService.getInstance().insert(student);
				finish();
				
			}
		});
	}
	
	private void dialog(final String title, final String[] items, final MyCallBack callBack) {  

		//dialog参数设置  
        AlertDialog.Builder builder=new AlertDialog.Builder(this);  //先得到构造器  
        builder.setTitle(title); //设置标题  
        builder.setIcon(R.mipmap.ic_launcher);//设置图标，图片id即可  
        //设置列表显示，注意设置了列表显示就不要设置builder.setMessage()了，否则列表不起作用。  
        builder.setItems(items,new DialogInterface.OnClickListener() {  
            @Override  
            public void onClick(DialogInterface dialog, int which) {  
            	callBack.callBack(which);
                dialog.dismiss();  
            }  
        });  
        builder.setPositiveButton("取消",new DialogInterface.OnClickListener() {  
            @Override  
            public void onClick(DialogInterface dialog, int which) {  
            	callBack.callBack(which);
                dialog.dismiss();  
            }  
        });  
        builder.create().show();  
    }  
	
	private interface MyCallBack{
		void callBack(int position);
	}
	
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if(keyCode == KeyEvent.KEYCODE_BACK){

			Intent intent = new Intent();
			intent.putExtra("student", (Serializable)student);
			setResult(-1, intent);
			finish();
			
		return true;
		}else{
			return false;
		}
		
	}

}

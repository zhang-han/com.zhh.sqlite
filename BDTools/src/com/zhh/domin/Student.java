package com.zhh.domin;

import java.io.Serializable;

import com.zhh.sqlite.tools.Id;
import com.zhh.sqlite.tools.Table;

import android.R.integer;

/**
 * 作者： zzhh
 * 
 * 时间：@2016年12月23日 下午3:45:43
 * 
 */
@Table(name = "t_a_student")
@Id(name = "id")
public class Student implements Serializable{
	
	private String id;			//学生ID
	private String name;		//姓名
	private Integer gender;			//性别
	private Integer age;			//年龄
	private String phone;		//联系电话
	private Integer grade;			//年级号
	private Integer clazz;			//班级号
	private String teacherName;	//班主任名称
	private Integer teacherId;	//班主任ID
	private Integer photo;			//照片url
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Integer getGender() {
		return gender;
	}
	public void setGender(Integer gender) {
		this.gender = gender;
	}
	public Integer getAge() {
		return age;
	}
	public void setAge(Integer age) {
		this.age = age;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public Integer getGrade() {
		return grade;
	}
	public void setGrade(Integer grade) {
		this.grade = grade;
	}
	public Integer getClazz() {
		return clazz;
	}
	public void setClazz(Integer clazz) {
		this.clazz = clazz;
	}
	public Integer getTeacherId() {
		return teacherId;
	}
	public void setTeacherId(Integer teacherId) {
		this.teacherId = teacherId;
	}
	public void setPhoto(Integer photo) {
		this.photo = photo;
	}
	public Integer getPhoto() {
		return photo;
	}
	public String getTeacherName() {
		return teacherName;
	}
	public void setTeacherName(String teacherName) {
		this.teacherName = teacherName;
	}
	
}

package com.yqz.websolution.model;

import java.util.Date;

import javax.xml.bind.annotation.XmlRootElement;

public class Person {
	private int age;
	private String name;
	private Sex sex;
	private Date birthday;

	public Date getBirthday() {
		return birthday;
	}

	public void setBirthday(Date birthday) {
		this.birthday = birthday;
	}

	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Sex getSex() {
		return sex;
	}

	public void setSex(Sex sex) {
		this.sex = sex;
	}

	@XmlRootElement
	public static enum Sex {
		Male, Female
	}
}

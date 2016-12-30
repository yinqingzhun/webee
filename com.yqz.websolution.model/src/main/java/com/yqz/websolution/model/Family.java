package com.yqz.websolution.model;

import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;
@JacksonXmlRootElement(localName="family")
public class Family {
	@JacksonXmlProperty(localName = "count")
	private int count;
	@JacksonXmlElementWrapper(localName = "members", useWrapping = true)
	@JacksonXmlProperty(localName = "member")
	private List<Person> employees;

	public Family() {
	}

	public Family(List<Person> employees) {
		this.employees = employees;
		this.count = employees.size();
	}

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}

	public List<Person> getEmployees() {
		return employees;
	}

	public void setEmployees(List<Person> employees) {
		this.employees = employees;
	}

}

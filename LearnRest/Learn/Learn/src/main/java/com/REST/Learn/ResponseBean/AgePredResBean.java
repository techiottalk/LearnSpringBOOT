package com.REST.Learn.ResponseBean;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonProperty;

public class AgePredResBean  implements Serializable {
	
	@JsonProperty("name")
	public String name;
	
	@JsonProperty("age")
	public String age ;
	
	@JsonProperty("count")
	public String count;
	
	
	

	

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getAge() {
		return age;
	}

	public void setAge(String age) {
		this.age = age;
	}

	public String getCount() {
		return count;
	}

	public void setCount(String count) {
		this.count = count;
	}

	@Override
	public String toString() {
		return "AgePredResBean [name=" + name + ", age=" + age + ", count=" + count + "]";
	}
	
	
	
	
	
	

}

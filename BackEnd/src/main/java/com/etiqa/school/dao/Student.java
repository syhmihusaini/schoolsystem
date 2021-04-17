package com.etiqa.school.dao;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Student {
	  @Id
	  @GeneratedValue(strategy=GenerationType.AUTO)
	  private Integer studentId;

	  private String studentName;

	  public Integer getStudentId() {
	    return studentId;
	  }

	  public void setStudentId(Integer studentId) {
	    this.studentId = studentId;
	  }

	  public String getStudentName() {
	    return studentName;
	  }

	  public void setStudentName(String studentName) {
	    this.studentName = studentName;
	  }
}

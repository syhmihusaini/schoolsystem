package com.etiqa.school.repository;
import org.springframework.data.repository.CrudRepository;

import com.etiqa.school.dao.Student;

//This will be AUTO IMPLEMENTED by Spring into a Bean called userRepository
//CRUD refers Create, Read, Update, Delete

public interface StudentRepository extends CrudRepository<Student, Integer> {

}
package com.example.imaginnovate;


import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
 @Repository
 public interface EmpRepo extends CrudRepository<Employee,Integer> {


    }


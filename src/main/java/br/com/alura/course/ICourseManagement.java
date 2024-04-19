package br.com.alura.course;

import java.util.List;
import java.util.NoSuchElementException;

import br.com.alura.core.dto.CreateCourseDTO;
import br.com.alura.core.dto.GetCourseDTO;
import br.com.alura.core.dto.ParamsListCoursesDTO;

public interface ICourseManagement {

	GetCourseDTO createCourse( CreateCourseDTO dto ) throws NoSuchElementException;
	Void inactivateCourse( String code ) throws NoSuchElementException;
	List< GetCourseDTO > listCoursesByParams( ParamsListCoursesDTO params );
	
}

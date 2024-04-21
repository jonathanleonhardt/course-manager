package br.com.alura.course;

import java.util.List;
import java.util.NoSuchElementException;

import org.springframework.stereotype.Service;

import br.com.alura.core.dto.CreateCourseDTO;
import br.com.alura.core.dto.GetCourseDTO;
import br.com.alura.core.dto.ParamsListCoursesDTO;
import br.com.alura.persistence.course.useCase.CreateCourse;
import br.com.alura.persistence.course.useCase.InactivateCourse;
import br.com.alura.persistence.course.useCase.ListCoursesByStatusAndPage;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CourseManagement implements ICourseManagement {

	private final CreateCourse createCourse;
	private final InactivateCourse inactivateCourse;
	private final ListCoursesByStatusAndPage listCoursesByStatusAndPage;

	@Override
	public GetCourseDTO createCourse( CreateCourseDTO dto ) throws NoSuchElementException {
		return this.createCourse.execute( dto );
	}

	@Override
	public Void inactivateCourse( String code ) throws NoSuchElementException {
		return this.inactivateCourse.execute( code );
	}

	@Override
	public List< GetCourseDTO > listCoursesByParams( ParamsListCoursesDTO params ) {
		return this.listCoursesByStatusAndPage.execute( params );
	}

}

package br.com.alura.challenge.api;

import java.util.List;
import java.util.NoSuchElementException;

import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import br.com.alura.challenge.domain.Course;
import br.com.alura.challenge.useCase.course.InactivateCourse;
import br.com.alura.challenge.useCase.course.ListCoursesByStatusAndPage;
import br.com.alura.challenge.useCase.course.dto.ListCoursesParamDTO;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping( "/api/course" )
public class CourseResource {

	private final InactivateCourse inactivateCourse;
	private final ListCoursesByStatusAndPage listCourses;

	@GetMapping( "/list" )
	public ResponseEntity< List< Course > > getUserByUsername( Pageable pageable, @RequestParam( value = "active" ) Boolean active ) {
		try {
			ListCoursesParamDTO params = new ListCoursesParamDTO( active, pageable );
			return ResponseEntity.ok( this.listCourses.execute( params ) );
		} catch ( Exception e ) {
			return ResponseEntity.badRequest().build();
		}
	}

	@PutMapping( "/inactive/{code}" )
	public ResponseEntity< Void > getUserByUsername( @PathVariable String code ) {
		try {
			return ResponseEntity.ok( this.inactivateCourse.execute( code ) );
		} catch ( NoSuchElementException e) {
			return ResponseEntity.notFound().build();
		}
	}

	
}

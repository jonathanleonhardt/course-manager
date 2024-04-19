package br.com.alura.gateway;

import java.util.List;
import java.util.NoSuchElementException;

import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import br.com.alura.core.dto.CreateCourseDTO;
import br.com.alura.core.dto.GetCourseDTO;
import br.com.alura.core.dto.ListCoursesParamDTO;
import br.com.alura.course.userCase.CreateCourse;
import br.com.alura.course.userCase.InactivateCourse;
import br.com.alura.course.userCase.ListCoursesByStatusAndPage;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping( "/api/course" )
public class CourseResource {

	private final InactivateCourse inactivateCourse;
	private final ListCoursesByStatusAndPage listCourses;
	private final CreateCourse createCourse;

	@PostMapping( "/create" )
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity< GetCourseDTO > createUser( @Valid @RequestBody CreateCourseDTO course ) {
		try {
			return ResponseEntity.ok( this.createCourse.execute( course ) );
		} catch ( Exception e) {
			return ResponseEntity.badRequest().build();
		}
	}

	@GetMapping( "/list" )
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity< List< GetCourseDTO > > listCourses( Pageable pageable, @RequestParam( value = "active" ) Boolean active ) {
		try {
			ListCoursesParamDTO params = new ListCoursesParamDTO( active, pageable );
			return ResponseEntity.ok( this.listCourses.execute( params ) );
		} catch ( Exception e ) {
			return ResponseEntity.badRequest().build();
		}
	}

	@PutMapping( "/inactive/{code}" )
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity< Void > inactivateCourse( @PathVariable String code ) {
		try {
			return ResponseEntity.ok( this.inactivateCourse.execute( code ) );
		} catch ( NoSuchElementException e) {
			return ResponseEntity.notFound().build();
		}
	}

	
}

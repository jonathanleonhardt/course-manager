package br.com.alura.persistence.course.useCase;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import br.com.alura.core.dto.GetCourseDTO;
import br.com.alura.core.dto.GetUserDTO;
import br.com.alura.core.dto.ParamsListCoursesDTO;
import br.com.alura.core.shared.UseCase;
import br.com.alura.persistence.course.repository.CourseRepository;
import br.com.alura.persistence.user.domain.User;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ListCoursesByStatusAndPage implements UseCase< ParamsListCoursesDTO, List< GetCourseDTO > > {

	private static final int DEFAULT_PAGE_SIZE = 10;
	private static final Sort sort = Sort.by( "createdAt" ).ascending();

	private final CourseRepository courseRepository;

	@Override
	public List< GetCourseDTO > execute( ParamsListCoursesDTO params ) {
		if ( !Objects.isNull( params.pageable() ) ) {
			PageRequest page = PageRequest.of( 0, DEFAULT_PAGE_SIZE, sort );
			return this.getCourseList( params, page );
		}
		return this.getCourseList( params, params.pageable() );
	}

	private List< GetCourseDTO > getCourseList( ParamsListCoursesDTO params, Pageable pageable ) {
		return this.courseRepository
				.listCourseByStatus( params.active(), pageable )
				.stream()
				.map( course -> {
					User instructor = course.getInstructor();
					GetUserDTO instructorDTO = new GetUserDTO(
							instructor.getName(),
							instructor.getEmail(),
							null );
					return new GetCourseDTO(
							course.getId(),
							course.getName(),
							course.getDescription(),
							course.getCode(),
							instructorDTO,
							course.getActive(),
							course.getCreatedAt(),
							course.getInactivatedAt() );
				} )
				.collect( Collectors.toList() );
	}

}

package br.com.alura.course.userCase;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import br.com.alura.core.dto.GetCourseDTO;
import br.com.alura.core.dto.GetUserDTO;
import br.com.alura.core.dto.ListCoursesParamDTO;
import br.com.alura.core.shared.UseCase;
import br.com.alura.course.repository.CourseRepository;
import br.com.alura.user.domain.User;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ListCoursesByStatusAndPage implements UseCase< ListCoursesParamDTO, List< GetCourseDTO > > {

	private static final int DEFAULT_PAGE_SIZE = 10;
	private static final Sort sort = Sort.by( "createdAt" ).ascending();

	private final CourseRepository courseRepository;

	@Override
	public List< GetCourseDTO > execute( ListCoursesParamDTO params ) {
		if ( !Objects.isNull( params.pageable() ) ) {
			PageRequest page = PageRequest.of( 0, DEFAULT_PAGE_SIZE, sort );
			return this.getCourseList( params, page );
		}
		return this.getCourseList( params, params.pageable() );
	}

	private List< GetCourseDTO > getCourseList( ListCoursesParamDTO params, Pageable pageable ) {
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

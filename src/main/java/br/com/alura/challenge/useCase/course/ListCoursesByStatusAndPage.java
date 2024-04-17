package br.com.alura.challenge.useCase.course;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import br.com.alura.challenge.domain.Course;
import br.com.alura.challenge.repository.CourseRepository;
import br.com.alura.challenge.shared.UseCase;
import br.com.alura.challenge.useCase.course.dto.ListCoursesParamDTO;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ListCoursesByStatusAndPage implements UseCase< ListCoursesParamDTO, List< Course > > {

	private static final int DEFAULT_PAGE_SIZE = 10;
	private static final Sort sort = Sort.by( "createdAt" ).ascending();

	private final CourseRepository courseRepository;

	@Override
	public List< Course > execute( ListCoursesParamDTO params ) {
		if ( !Objects.isNull( params.pageable() ) ) {
			PageRequest page = PageRequest.of( 0, DEFAULT_PAGE_SIZE, sort );
			return this.getCourseList( params, page );
		}
		return this.getCourseList( params, params.pageable() );
	}

	private List< Course > getCourseList( ListCoursesParamDTO params, Pageable pageable ) {
		return this.courseRepository
				.listCourseByStatus( params.active(), pageable )
				.stream()
				.collect( Collectors.toList() );
	}

}

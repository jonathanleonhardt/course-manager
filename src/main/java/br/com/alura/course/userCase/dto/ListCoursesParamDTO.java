package br.com.alura.course.userCase.dto;

import org.springframework.data.domain.Pageable;

public record ListCoursesParamDTO( Boolean active, Pageable pageable ) {
	
}

package br.com.alura.challenge.useCase.course.dto;

import org.springframework.data.domain.Pageable;

public record ListCoursesParamDTO( Boolean active, Pageable pageable ) {
	
}

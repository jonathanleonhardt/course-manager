package br.com.alura.core.dto;

import org.springframework.data.domain.Pageable;

public record ListCoursesParamDTO( Boolean active, Pageable pageable ) {
	
}

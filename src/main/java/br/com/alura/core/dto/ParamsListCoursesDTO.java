package br.com.alura.core.dto;

import org.springframework.data.domain.Pageable;

public record ParamsListCoursesDTO( Boolean active, Pageable pageable ) {
	
}

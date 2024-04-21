package br.com.alura.core.dto;

import org.springframework.data.domain.Pageable;

public record ParamsListCoursesDTO( boolean active, Pageable pageable ) {
	
}

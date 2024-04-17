package br.com.alura.challenge.useCase.enroll.dto;

import jakarta.validation.constraints.NotBlank;

public record CreateEnrollDTO(
		@NotBlank
		String studentId,
		@NotBlank
		String courseId ) {
	
}

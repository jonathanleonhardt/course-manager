package br.com.alura.core.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;

public record CreateFeedbackDTO(
			@NotBlank
			String student_id,
			@NotBlank
			String couse_id,
			@Min(0)
			@Max(10)
			Long rating,
			String description
) {
}

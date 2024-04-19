package br.com.alura.core.dto;

import java.util.Calendar;

public record GetEnrollDTO(
		String id,
		String studentId,
		String courseId,
		Calendar createdAt ) {

}

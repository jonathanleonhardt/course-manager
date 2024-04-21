package br.com.alura.persistence.enroll.domain;

import java.util.Calendar;

import org.hibernate.annotations.GenericGenerator;

import br.com.alura.persistence.course.domain.Course;
import br.com.alura.persistence.user.domain.User;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder( setterPrefix = "with" )
@Entity
@Table( name = "enroll" )
public class Enroll {

	@Id
	@GeneratedValue( generator = "uuid" )
	@GenericGenerator( name = "uuid", strategy = "uuid2" )
	private String id;

	@OneToOne( fetch = FetchType.EAGER )
	@JoinColumn( name = "student_id", referencedColumnName = "id" )
	private User student;

	@OneToOne( fetch = FetchType.EAGER )
	@JoinColumn( name = "course_id", referencedColumnName = "id" )
	private Course course;

	@Builder.Default
	@Column( name = "created_at", columnDefinition = "TIMESTAMP" )
	private Calendar createdAt = Calendar.getInstance();

}

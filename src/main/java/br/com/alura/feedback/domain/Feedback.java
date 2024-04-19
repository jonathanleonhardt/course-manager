package br.com.alura.feedback.domain;

import java.util.Calendar;

import org.hibernate.annotations.GenericGenerator;

import br.com.alura.course.domain.Course;
import br.com.alura.user.domain.User;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
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
@Table( name = "feedback" )
public class Feedback {

	@Id
	@GeneratedValue( generator = "uuid" )
	@GenericGenerator( name = "uuid", strategy = "uuid2" )
	private String id;

	@ManyToOne( fetch = FetchType.EAGER, cascade = CascadeType.REMOVE )
	@JoinColumn( name = "student_id", referencedColumnName = "id", nullable = false )
	private User student;

	@ManyToOne( fetch = FetchType.EAGER, cascade = CascadeType.REMOVE )
	@JoinColumn( name = "course_id", referencedColumnName = "id", nullable = false )
	private Course course;

	@Column( name = "rating" )
	private Long rating;

	@Column( name = "description" )
	private String description;

	@Builder.Default
	@Column( name = "created_at", columnDefinition = "TIMESTAMP" )
	private Calendar createdAt = Calendar.getInstance();

}

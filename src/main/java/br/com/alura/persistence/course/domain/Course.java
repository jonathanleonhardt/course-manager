package br.com.alura.persistence.course.domain;

import java.util.Calendar;

import org.hibernate.annotations.GenericGenerator;

import br.com.alura.core.shared.Patterns;
import br.com.alura.persistence.user.domain.User;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder( setterPrefix = "with" )
@Entity
@Table( name = "course" )
public class Course {

	@Id
	@GeneratedValue( generator = "uuid" )
	@GenericGenerator( name = "uuid", strategy = "uuid2" )
	private String id;

	@Column( name = "name", columnDefinition = "text", nullable = false )
	private String name;

	@Column( name = "description", length = 20, nullable = false )
	@Size( max = 20, message = "You cant use more than 20 caracteres in username" )
	private String description;
	
	@Column( name = "code", length = 20, nullable = false )
	@Pattern(regexp = Patterns.COURSE_CODE, message = "Invalid course code")
	private String code;

	@ManyToOne( fetch = FetchType.EAGER )
	@JoinColumn( name = "instructor_id", referencedColumnName = "id", nullable = false )
	private User instructor;
	
	@Column( name = "active" )
	private Boolean active;

	@Builder.Default
	@Column( name = "created_at", columnDefinition = "TIMESTAMP" )
	private Calendar createdAt = Calendar.getInstance();

	@Column( name = "inactivated_at", columnDefinition = "TIMESTAMP" )
	private Calendar inactivatedAt;

}

package br.com.alura.challenge.domain;

import java.util.Calendar;

import org.hibernate.annotations.GenericGenerator;

import br.com.alura.challenge.shared.Patterns;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
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
@Table( name = "alura_user" )
public class User {

	@Id
	@GeneratedValue( generator = "uuid" )
	@GenericGenerator( name = "uuid", strategy = "uuid2" )
	private String id;

	@Column( name = "name", columnDefinition = "text", nullable = false )
	private String name;

	@Column( name = "username", length = 20, unique = true )
	@Pattern(regexp = Patterns.USERNAME, message = "Invalid Username")
	@Size( max = 20, message = "You cant use more than 20 caracteres in username" )
	private String username;

	@Column( name = "password", columnDefinition = "text", nullable = false )
	private String password;

	// Role(ESTUDANTE, INSTRUTOR, ADMIN)
	@Column( name = "roles" )
	private String roles;

	@Column( name = "email", columnDefinition = "text", unique = true )
	@Pattern(regexp = Patterns.EMAIL, message = "Invalid Email")
	private String email;

	@Builder.Default
	@Column( name = "created_at", columnDefinition = "TIMESTAMP" )
	private Calendar createdAt = Calendar.getInstance();

}

package br.com.alura.persistence.user.domain;

import java.util.Calendar;

import org.hibernate.annotations.GenericGenerator;

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
@Table( name = "alura_user" )
public class User {

	@Id
	@GeneratedValue( generator = "uuid" )
	@GenericGenerator( name = "uuid", strategy = "uuid2" )
	private String id;

	@Column( name = "name", columnDefinition = "text", nullable = false )
	private String name;

	@Column( name = "username", length = 20, unique = true )
	private String username;

	@Column( name = "password", columnDefinition = "text", nullable = false )
	private String password;

	@ManyToOne( fetch = FetchType.EAGER )
	@JoinColumn( name = "role_id", referencedColumnName = "id", nullable = false )
	private Role role;

	@Column( name = "email", columnDefinition = "text", unique = true )
	private String email;

	@Builder.Default
	@Column( name = "created_at", columnDefinition = "TIMESTAMP" )
	private Calendar createdAt = Calendar.getInstance();

}

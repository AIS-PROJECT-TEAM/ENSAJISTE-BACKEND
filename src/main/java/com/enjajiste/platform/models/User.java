package com.enjajiste.platform.models;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.sun.istack.NotNull;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(uniqueConstraints = { @UniqueConstraint(columnNames = { "nom", "prenom" }) })
public class User {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int id;
	@NotNull
	@Column(unique = true, nullable = false)
	private String cne;
	@Column(nullable = false)
	private String nom;
	@Column(nullable = false)
	private String prenom;
	@NotNull
	@Column(unique = true, nullable = false)
	private String email;
	@Column(nullable = false)
	private String password;
	@NotNull
	@Column(unique = true, nullable = false)
	private String cin;
	private String niveau_etude;
	private String photo;
	private String gender;

	@LazyCollection(LazyCollectionOption.FALSE)
	@ManyToMany()
	private Collection<Role> roles = new ArrayList<>();
	@LazyCollection(LazyCollectionOption.FALSE)
	@OneToMany(mappedBy = "user")
	@JsonIgnore
	private List<Document> documents;
}

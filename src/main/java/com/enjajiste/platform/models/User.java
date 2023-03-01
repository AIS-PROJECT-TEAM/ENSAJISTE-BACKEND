package com.enjajiste.platform.models;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.sun.istack.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.aspectj.lang.annotation.DeclareError;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;
import org.springframework.validation.annotation.Validated;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(uniqueConstraints = {
        @UniqueConstraint(columnNames = {"nom", "prenom"})
})
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
    @OneToMany(mappedBy="user")
    @JsonIgnore
    private List<Document> documents;
}

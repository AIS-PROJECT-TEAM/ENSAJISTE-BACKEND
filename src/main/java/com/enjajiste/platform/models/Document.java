package com.enjajiste.platform.models;

import com.sun.istack.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name="document")
public class Document {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private int id;
    @NotNull
    private String enseignant;
    @NotNull
    private String module;
    @NotNull
    private String annee;
    @NotNull
    private String type;
    @NotNull
    private String semestre;
    private double rating=0.0;
    private int nbr_telechargements=0;
    private int nbr_votes=0;
    @NotNull
    private String url;
    private boolean etat=false;
    @ManyToOne(cascade = CascadeType.PERSIST)
    @NotNull
    private User user;

    @Override
    public String toString() {
        return "Document{" +
                "id=" + id +
                ", enseignant='" + enseignant + '\'' +
                ", module='" + module + '\'' +
                ", anne" +
                "e=" + annee +
                ", type='" + type + '\'' +
                ", semestre='" + semestre + '\'' +
                ", rating=" + rating +
                ", etat=" + etat +
                ", user=" + user +
                '}';
    }

}

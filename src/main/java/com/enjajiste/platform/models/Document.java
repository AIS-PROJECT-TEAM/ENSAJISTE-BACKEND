package com.enjajiste.platform.models;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.sun.istack.NotNull;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

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

    public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getEnseignant() {
		return enseignant;
	}

	public void setEnseignant(String enseignant) {
		this.enseignant = enseignant;
	}

	public String getModule() {
		return module;
	}

	public void setModule(String module) {
		this.module = module;
	}

	public String getAnnee() {
		return annee;
	}

	public void setAnnee(String annee) {
		this.annee = annee;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getSemestre() {
		return semestre;
	}

	public void setSemestre(String semestre) {
		this.semestre = semestre;
	}

	public double getRating() {
		return rating;
	}

	public void setRating(double rating) {
		this.rating = rating;
	}

	public int getNbr_telechargements() {
		return nbr_telechargements;
	}

	public void setNbr_telechargements(int nbr_telechargements) {
		this.nbr_telechargements = nbr_telechargements;
	}

	public int getNbr_votes() {
		return nbr_votes;
	}

	public void setNbr_votes(int nbr_votes) {
		this.nbr_votes = nbr_votes;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public boolean isEtat() {
		return etat;
	}

	public void setEtat(boolean etat) {
		this.etat = etat;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

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

package com.ensta.librarymanager.modele;

import java.sql.Date; //plutot date ou local Date??
import java.time.LocalDate;

import com.ensta.librarymanager.dao.daoInterfaces.IEmpruntDao;
import com.ensta.librarymanager.service.servicesInterfaces.IEmpruntService;

//import com.ensta.librarymanager.dao.DaoException;
//import com.ensta.librarymanager.service.ServiceException;

public class Emprunt /*implements EmpruntDao, EmpruntService*/{

    /*
	 * ** Attributs
	 */
    private int id;
    private int idMembre;//private Membre membre;
    private int idLivre;//private Livre idL
    private LocalDate dateEmprunt;
    private LocalDate dateRetour;

     /*
	 * ** Constructeur
	 */
	public Emprunt(int id, int idMembre, int idLivre,/*Membre membre, Livre livre,*/ LocalDate dateEmprunt, LocalDate dateRetour) {
		this.id = id;
        this.idMembre = idMembre;
        this.idLivre = idLivre;
        this.dateEmprunt = dateEmprunt;
        this.dateRetour = dateRetour;
	}

    /*
	 * ** Méthodes
	 */
    @Override
    public String toString(){
        String str = "Emprunt " + id + " du livre " + idLivre + " par le membre " + idMembre + " du " + dateEmprunt + " au " + dateRetour;
        return(str);
    }

	public int getIdMembre() {
		return this.idMembre;
	}

	public int getIdLivre() {
		return this.idLivre;
	}

	public LocalDate getDateEmprunt() {
		return this.dateEmprunt;
	}

	public LocalDate getDateRetour() {
		return this.dateRetour;
	}

	public void setDateRetour(LocalDate dateRetour_arg) {
		this.dateRetour = dateRetour_arg;
	}

    public int getId() {
		return this.id;
	}
}

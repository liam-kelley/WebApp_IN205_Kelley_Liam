package com.ensta.librarymanager.modele;

import java.sql.Date; //plutot date ou local Date??
import java.time.LocalDate;

import com.ensta.librarymanager.dao.IEmpruntDao;
import com.ensta.librarymanager.service.IEmpruntService;

//import com.ensta.librarymanager.dao.DaoException;
//import com.ensta.librarymanager.service.ServiceException;

public class Emprunt /*implements EmpruntDao, EmpruntService*/{

    /*
	 * ** Attributs
	 */
    private int id;
    private Membre membre;
    private Livre livre;
    private LocalDate dateEmprunt;
    private LocalDate dateRetour;

     /*
	 * ** Constructeur
	 */
	public Emprunt(int id, Membre membre, Livre livre, LocalDate dateEmprunt, LocalDate dateRetour) {
		this.id = id;
        this.membre = membre;
        this.livre = livre;
        this.dateEmprunt = dateEmprunt;
        this.dateRetour = dateRetour;
	}

    /*
	 * ** Méthodes
	 */
    @Override
    public String toString(){
        String str = "Emprunt " + id + " du livre " + livre + " par le membre " + membre + " du " + dateEmprunt + " au " + dateRetour;
        return(str);
    }
}

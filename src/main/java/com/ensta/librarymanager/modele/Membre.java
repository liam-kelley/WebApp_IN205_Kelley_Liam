package com.ensta.librarymanager.modele;

import java.util.List;


import com.ensta.librarymanager.dao.MembreDao;
import com.ensta.librarymanager.service.MembreService;
//import com.ensta.librarymanager.dao.DaoException;
//import com.ensta.librarymanager.service.ServiceException;

public class Membre /*implements MembreDao, MembreService*/{
    enum Abonnement {
        BASIC,
        PREMIUM,
        VIP
    }

    /*
	 * ** Attributs
	 */
    private int id;
    private String nom;
    private String prenom;
    private String adresse;
    private String email;
    private String telephone;
    private Abonnement abonnement;

    /*
	 * ** Constructeur
	 */
	public Membre(int id, String nom, String prenom, String adresse, String email, String telephone, Abonnement abonnement) {
		this.id = id;
        this.nom =nom;
        this.prenom = prenom;
        this.adresse = adresse;
        this.email = email;
        this.telephone = telephone;
        this.abonnement = abonnement;
	}

}

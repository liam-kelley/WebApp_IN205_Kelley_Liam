package com.ensta.librarymanager.modele;

import java.util.List;

import com.ensta.librarymanager.dao.daoInterfaces.ILivreDao;
import com.ensta.librarymanager.service.servicesInterfaces.ILivreService;

public class Livre /*implements LivreDao, LivreService*/{
    /*
	 * ** Attributs
	 */
    private int id;
    private String titre;
    private String auteur;
    private String isbn;

     /*
	 * ** Constructeur
	 */
	public Livre(int id, String titre, String auteur, String isbn) {
		this.id = id;
        this.titre = titre;
        this.auteur = auteur;
        this.isbn = isbn;
	}

    /*
	 * ** Méthodes
	 */
	public String getTitre() {
		return this.titre;
	}

	public String getAuteur() {
		return this.auteur;
	}

	public String getIsbn() {
		return this.isbn;
	}

    public int getId() {
		return this.id;
	}

}

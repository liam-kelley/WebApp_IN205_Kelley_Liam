package com.ensta.librarymanager.modele;

import java.util.List;

import com.ensta.librarymanager.dao.daoInterfaces.ILivreDao;
import com.ensta.librarymanager.service.ILivreService;
//import com.ensta.librarymanager.dao.DaoException;
//import com.ensta.librarymanager.service.ServiceException;

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

}

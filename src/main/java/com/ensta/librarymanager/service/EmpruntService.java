package com.ensta.librarymanager.service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import com.ensta.librarymanager.dao.EmpruntDao;
import com.ensta.librarymanager.dao.LivreDao;
import com.ensta.librarymanager.exception.DaoException;
import com.ensta.librarymanager.exception.ServiceException;
import com.ensta.librarymanager.modele.Abonnement;
import com.ensta.librarymanager.modele.Emprunt;
import com.ensta.librarymanager.modele.Livre;
import com.ensta.librarymanager.modele.Membre;
import com.ensta.librarymanager.service.servicesInterfaces.IEmpruntService;

public class EmpruntService implements IEmpruntService{

    //Design pattern Singleton
    private static EmpruntService instance;
    private EmpruntService() {}
    public static EmpruntService getInstance() {
        if (instance == null) {
            instance = new EmpruntService();
        }
        return instance;
    }

    private EmpruntDao empruntDao = EmpruntDao.getInstance();
    private LivreDao livreDao = LivreDao.getInstance();

    @Override
    public List<Emprunt> getList() throws ServiceException {
        try {
            return empruntDao.getList();
        } catch (DaoException e) {
            e.printStackTrace();
            throw new ServiceException();
        }
    }

    @Override
    public List<Emprunt> getListCurrent() throws ServiceException {
        try {
            return empruntDao.getListCurrent();
        } catch (DaoException e) {
            e.printStackTrace();
            throw new ServiceException();
        }
    }

    @Override
    public List<Emprunt> getListCurrentByMembre(int idMembre) throws ServiceException {
        try {
            return empruntDao.getListCurrentByMembre(idMembre);
        } catch (DaoException e) {
            e.printStackTrace();
            throw new ServiceException();
        }
    }

    @Override
    public List<Emprunt> getListCurrentByLivre(int idLivre) throws ServiceException {
        try {
            return empruntDao.getListCurrentByLivre(idLivre);
        } catch (DaoException e) {
            e.printStackTrace();
            throw new ServiceException();
        }
    }

    @Override
    public Emprunt getById(int idEmprunt) throws ServiceException {
        try {
            Optional<Emprunt> empruntOpt = empruntDao.getById(idEmprunt);
            if (empruntOpt.isPresent()) { return empruntOpt.get();} //Convertir opt en normal
            else {
                System.out.println("L'emprunt n'est pas dans la base de données!");
                return null;
            }
        } catch (DaoException e) {
            e.printStackTrace();
            throw new ServiceException();
        }
    }

    @Override
    public void create(int idMembre, int idLivre, LocalDate dateEmprunt) throws ServiceException {
        try {
            empruntDao.create(idMembre, idLivre, dateEmprunt);
        } catch (DaoException e) {
            e.printStackTrace();
            throw new ServiceException();
        }
    }

    @Override
    public void returnBook(int id) throws ServiceException { //utilisant update
        try {
            Optional<Emprunt> empruntOpt = empruntDao.getById(id);
            if (empruntOpt.isPresent()) {
                Emprunt emprunt = empruntOpt.get();
                LocalDate dateRetour = LocalDate.now();
                emprunt.setDateRetour(dateRetour);
                empruntDao.update(emprunt);
            } else {
                System.out.println("L'emprunt n'est pas dans la base de données!");
            }
        } catch (DaoException e) {
            e.printStackTrace();
            throw new ServiceException();
        }
    }

    @Override
    public int count() throws ServiceException {
        try {
            int result = empruntDao.count();
            return result;
        } catch (DaoException e) {
            e.printStackTrace();
            throw new ServiceException();
        }
    }
    
    //Envoie un booléen indiquant si le livre indiqué est disponible à l’emprunt ou non (on ne peut évidemment pas emprunter un livre qui n’a pas encore été rendu).
    @Override
    public boolean isLivreDispo(int idLivre) throws ServiceException {
        try {
            if (livreDao.getById(idLivre).isPresent()) {
                Livre livre = livreDao.getById(idLivre).get();
                for (Emprunt emprunt : empruntDao.getListCurrent()) { //est-ce que le livre n'est pas emprunté 
                    if (livre.getId() == emprunt.getIdLivre()) { return false;}
                }
            }
            else{ System.out.println("Le livre n'existe pas dans la base de données."); }
            return true;
        } catch (DaoException e) {
            e.printStackTrace();
            throw new ServiceException();
        }
    }

    @Override
    public boolean isEmpruntPossible(Membre membre) throws ServiceException {
        try {
            int books = empruntDao.getListCurrentByMembre(membre.getId()).size();
            int seuil;
            if (membre.getAbonnement() == Abonnement.BASIC) { seuil = 2;}
            else if (membre.getAbonnement() == Abonnement.PREMIUM) { seuil = 5; }
            else { seuil = 20; }
            
            if (books < seuil) { return true; }
            else { return false; }
        } catch (DaoException e) {
            e.printStackTrace();
            throw new ServiceException();
        }
    }
}

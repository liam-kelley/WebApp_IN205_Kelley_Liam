package com.ensta.librarymanager.service;

import java.util.List;

import com.ensta.librarymanager.dao.MembreDao;
import com.ensta.librarymanager.exception.ServiceException;
import com.ensta.librarymanager.modele.Membre;
import com.ensta.librarymanager.service.servicesInterfaces.IMembreService;

public class MembreService implements IMembreService{

    //Design pattern Singleton
    private static MembreService instance;
    private MembreService() {}
    public static MembreService getInstance() {
        if (instance == null) {
            instance = new MembreService();
        }
        return instance;
    }

    private MembreDao membreDao = MembreDao.getInstance();
    private EmpruntService empruntService = EmpruntService.getInstance();

    @Override
    public List<Membre> getList() throws ServiceException {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public List<Membre> getListMembreEmpruntPossible() throws ServiceException {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Membre getById(int id) throws ServiceException {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public int create(String nom, String prenom, String adresse, String email, String telephone)
            throws ServiceException {
        // TODO Auto-generated method stub
        return 0;
    }

    @Override
    public void update(Membre membre) throws ServiceException {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void delete(int id) throws ServiceException {
        // TODO Auto-generated method stub
        
    }

    @Override
    public int count() throws ServiceException {
        // TODO Auto-generated method stub
        return 0;
    }
    
}

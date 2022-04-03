package com.ensta.librarymanager.dao;

import java.time.LocalDate;
import java.util.List;

import com.ensta.librarymanager.dao.daoInterfaces.IEmpruntDao;
import com.ensta.librarymanager.exception.DaoException;
import com.ensta.librarymanager.modele.Emprunt;

import java.util.ArrayList;
import java.util.Optional;
import com.ensta.librarymanager.persistence.ConnectionManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Connection;
import java.sql.Date;

public class EmpruntDao implements IEmpruntDao {

    //Design pattern Singleton
    private static EmpruntDao instance;
    private EmpruntDao() {}
    public static EmpruntDao getInstance() {
        if (instance == null) {
            instance = new EmpruntDao();
        }
        return instance;
    }

    @Override
    public List<Emprunt> getList() throws DaoException {
        try (Connection conn = ConnectionManager.getConnection();
        PreparedStatement stmt = conn.prepareStatement("SELECT e.id AS id, idMembre, nom, prenom, adresse, email, telephone, abonnement, idLivre, titre, auteur, isbn, dateEmprunt, dateRetour FROM emprunt AS e INNER JOIN membre ON membre.id = e.idMembre INNER JOIN livre ON livre.id = e.idLivre ORDER BY dateRetour DESC"))
        {
            List<Emprunt> liste = new ArrayList<>();
            
            ResultSet res = stmt.executeQuery();

            while (res.next()) {
                LocalDate dateRetour;
                if (res.getDate("dateRetour") != null) { dateRetour = res.getDate("dateRetour").toLocalDate();}
                else {dateRetour = null;}
                
                liste.add(new Emprunt(res.getInt("id"), res.getInt("idMembre"), res.getInt("idLivre"), res.getDate("dateEmprunt").toLocalDate(), dateRetour));
            }
            return liste;
        } catch (SQLException e) {
            e.printStackTrace();
            throw new DaoException();
        }
    }

    @Override
    public List<Emprunt> getListCurrent() throws DaoException {
        try (Connection conn = ConnectionManager.getConnection();
        PreparedStatement stmt = conn.prepareStatement("SELECT e.id AS id, idMembre, nom, prenom, adresse, email, telephone, abonnement, idLivre, titre, auteur, isbn, dateEmprunt, dateRetour FROM emprunt AS e INNER JOIN membre ON membre.id = e.idMembre INNER JOIN livre ON livre.id = e.idLivre WHERE dateRetour IS NULL"))
        {
            List<Emprunt> liste = new ArrayList<>();
            ResultSet res = stmt.executeQuery();

            while (res.next()) { //les dates de retour seront toutes null
                liste.add(new Emprunt(res.getInt("id"), res.getInt("idMembre"), res.getInt("idLivre"), res.getDate("dateEmprunt").toLocalDate(), null));
            }
            return liste;
        } catch (SQLException e) {
            e.printStackTrace();
            throw new DaoException();
        }
    }

    @Override
    public List<Emprunt> getListCurrentByMembre(int idMembre) throws DaoException {
        try (Connection conn = ConnectionManager.getConnection();
        PreparedStatement stmt = conn.prepareStatement("SELECT e.id AS id, idMembre, nom, prenom, adresse, email, telephone, abonnement, idLivre, titre, auteur, isbn, dateEmprunt, dateRetour FROM emprunt AS e INNER JOIN membre ON membre.id = e.idMembre INNER JOIN livre ON livre.id = e.idLivre WHERE dateRetour IS NULL AND membre.id = ?"))
        {
            stmt.setInt(1, idMembre); //Gestion du ?

            List<Emprunt> liste = new ArrayList<>();
            
            ResultSet res = stmt.executeQuery();

            while (res.next()) {
                LocalDate dateRetour;
                if (res.getDate("dateRetour") != null) { dateRetour = res.getDate("dateRetour").toLocalDate();}
                else {dateRetour = null;}
                
                liste.add(new Emprunt(res.getInt("id"), res.getInt("idMembre"), res.getInt("idLivre"), res.getDate("dateEmprunt").toLocalDate(), dateRetour));
            }
            return liste;
        } catch (SQLException e) {
            e.printStackTrace();
            throw new DaoException();
        }
    }

    @Override
    public List<Emprunt> getListCurrentByLivre(int idLivre) throws DaoException {
        try (Connection conn = ConnectionManager.getConnection();
        PreparedStatement stmt = conn.prepareStatement("SELECT e.id AS id, idMembre, nom, prenom, adresse, email, telephone, abonnement, idLivre, titre, auteur, isbn, dateEmprunt, dateRetour FROM emprunt AS e INNER JOIN membre ON membre.id = e.idMembre INNER JOIN livre ON livre.id = e.idLivre WHERE dateRetour IS NULL AND livre.id = ?"))
        {
            stmt.setInt(1, idLivre); //Gestion du ?

            List<Emprunt> liste = new ArrayList<>();
            
            ResultSet res = stmt.executeQuery();

            while (res.next()) {
                LocalDate dateRetour;
                if (res.getDate("dateRetour") != null) { dateRetour = res.getDate("dateRetour").toLocalDate();}
                else {dateRetour = null;}
                
                liste.add(new Emprunt(res.getInt("id"), res.getInt("idMembre"), res.getInt("idLivre"), res.getDate("dateEmprunt").toLocalDate(), dateRetour));
            }
            return liste;
        } catch (SQLException e) {
            e.printStackTrace();
            throw new DaoException();
        }
    }

    @Override
    public Emprunt getById(int id) throws DaoException {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public void create(int idMembre, int idLivre, LocalDate dateEmprunt) throws DaoException {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void update(Emprunt emprunt) throws DaoException {
        // TODO Auto-generated method stub
        
    }

    @Override
    public int count() throws DaoException {
        // TODO Auto-generated method stub
        return 0;
    }
    
}

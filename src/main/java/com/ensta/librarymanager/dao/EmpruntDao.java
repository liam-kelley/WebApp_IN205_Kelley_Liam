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
    public Optional<Emprunt> getById(int id) throws DaoException {
        try(Connection conn = ConnectionManager.getConnection();
        PreparedStatement pstmt = conn.prepareStatement("SELECT e.id AS idEmprunt, idMembre, nom, prenom, adresse, email, telephone, abonnement, idLivre, titre, auteur, isbn, dateEmprunt, dateRetour FROM emprunt AS e INNER JOIN membre ON membre.id = e.idMembre INNER JOIN livre ON livre.id = e.idLivre WHERE e.id = ?"))
        {
            pstmt.setInt(1, id); // Gestion du ?

            ResultSet res = pstmt.executeQuery();

            if (res.next()) {
                LocalDate dateRetour;
                if (res.getDate("dateRetour") != null) { dateRetour = res.getDate("dateRetour").toLocalDate();}
                else {dateRetour = null;}

                return Optional.ofNullable(new Emprunt(id, res.getInt("idMembre"), res.getInt("idLivre"), res.getDate("dateEmprunt").toLocalDate(), dateRetour));
            }
            else { return Optional.empty();}
        }
        catch(SQLException e){
            e.printStackTrace();
            throw new DaoException();
        }
    }

    @Override
    public int create(int idMembre, int idLivre, LocalDate dateEmprunt) throws DaoException {
        try(Connection conn = ConnectionManager.getConnection();
        PreparedStatement pstmt = conn.prepareStatement("INSERT INTO emprunt(idMembre, idLivre, dateEmprunt, dateRetour) VALUES (?, ?, ?, ?)", Statement.RETURN_GENERATED_KEYS))
        {
            pstmt.setInt(1, idMembre);
            pstmt.setInt(2, idLivre);
            pstmt.setDate(3, Date.valueOf(dateEmprunt));
            pstmt.setDate(4, null);

            pstmt.executeUpdate();

            ResultSet res = pstmt.getGeneratedKeys();

            int id = -1;
            if (res.next()) { id = res.getInt(1); }
            return id;
        }
        catch(SQLException e){
            e.printStackTrace();
            throw new DaoException();
        }
    }

    @Override
    public void update(Emprunt emprunt) throws DaoException {
        try(Connection conn = ConnectionManager.getConnection();
        PreparedStatement pstmt = conn.prepareStatement("UPDATE emprunt SET idMembre = ?, idLivre = ?, dateEmprunt = ?, dateRetour = ? WHERE id = ?"))
        {
            pstmt.setInt(1, emprunt.getIdMembre());
            pstmt.setInt(2, emprunt.getIdLivre());
            pstmt.setDate(3, Date.valueOf(emprunt.getDateEmprunt()));
            pstmt.setDate(4, Date.valueOf(emprunt.getDateRetour()));
            pstmt.setInt(5, emprunt.getId());

            pstmt.executeUpdate();
        }
        catch(SQLException e){
            e.printStackTrace();
            throw new DaoException();
        }
    }

    @Override
    public int count() throws DaoException {
        try (Connection conn = ConnectionManager.getConnection();
        PreparedStatement pstmt = conn.prepareStatement("SELECT COUNT(id) AS count FROM emprunt"))
        {
            ResultSet res = pstmt.executeQuery();

            if(res.next()){return res.getInt("count");}
            else{return(0);}
        } catch (SQLException e) {
            e.printStackTrace();
            throw new DaoException();
        }
    }
    
}

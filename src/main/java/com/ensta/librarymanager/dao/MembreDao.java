package com.ensta.librarymanager.dao;

import java.util.List;

import com.ensta.librarymanager.dao.daoInterfaces.IMembreDao;
import com.ensta.librarymanager.exception.DaoException;
import com.ensta.librarymanager.modele.Membre;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import com.ensta.librarymanager.persistence.ConnectionManager;
import com.ensta.librarymanager.modele.Abonnement;

public class MembreDao implements IMembreDao{

    //Design pattern Singleton
    private static MembreDao instance;
    private MembreDao() {}
    public static MembreDao getInstance() {
        if (instance == null) {
            instance = new MembreDao();
        }
        return instance;
    }

    @Override
    public List<Membre> getList() throws DaoException {
        try(Connection conn = ConnectionManager.getConnection();
        PreparedStatement pstmt = conn.prepareStatement( "SELECT id, nom, prenom, adresse, email, telephone, abonnement FROM membre ORDER BY nom, prenom"))
        {
            List<Membre> liste = new ArrayList<>();
            ResultSet res = pstmt.executeQuery();

            while(res.next()){
                liste.add(new Membre(res.getInt("id"), res.getString("nom"), res.getString("prenom"), res.getString("adresse"), res.getString("email"), res.getString("telephone"), Abonnement.valueOf(res.getString("abonnement").toUpperCase())));
            }
            return liste;

        } catch(SQLException e){
            e.printStackTrace();
            throw new DaoException();
        }
    }

    @Override
    public Optional<Membre> getById(int id) throws DaoException {
        try(Connection conn = ConnectionManager.getConnection();
        PreparedStatement pstmt = conn.prepareStatement("SELECT id, nom, prenom, adresse, email, telephone, abonnement FROM membre WHERE id = ?")) 
        {
            pstmt.setInt(1, id); // Gestion du ?

            ResultSet res = pstmt.executeQuery();

            if (res.next()) { return Optional.ofNullable(new Membre(res.getInt("id"), res.getString("nom"), res.getString("prenom"), res.getString("adresse"), res.getString("email"), res.getString("telephone"), Abonnement.valueOf(res.getString("abonnement").toUpperCase())));}
            else { return Optional.empty();}
        }
        catch(SQLException e){
            e.printStackTrace();
            throw new DaoException();
        }
    }

    @Override
    public int create(String nom, String prenom, String adresse, String email, String telephone) throws DaoException {
        try(Connection conn = ConnectionManager.getConnection();
        PreparedStatement pstmt = conn.prepareStatement("INSERT INTO membre(nom, prenom, adresse, email, telephone, abonnement) VALUES (?, ?, ?, ?, ?, ?)", Statement.RETURN_GENERATED_KEYS))
        {
            pstmt.setString(1, nom); //Gestions des ?
            pstmt.setString(2, prenom);
            pstmt.setString(3, adresse);
            pstmt.setString(4, email);
            pstmt.setString(5, telephone);
            pstmt.setString(6, "BASIC");

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
    public void update(Membre membre) throws DaoException {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void delete(int id) throws DaoException {
        // TODO Auto-generated method stub
        
    }

    @Override
    public int count() throws DaoException {
        // TODO Auto-generated method stub
        return 0;
    }
    
}

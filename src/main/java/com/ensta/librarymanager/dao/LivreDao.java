package com.ensta.librarymanager.dao;

import java.util.List;
import java.util.ArrayList;
import java.util.Optional;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import com.ensta.librarymanager.dao.daoInterfaces.ILivreDao;
import com.ensta.librarymanager.exception.DaoException;
import com.ensta.librarymanager.modele.Livre;
import com.ensta.librarymanager.persistence.ConnectionManager;

public class LivreDao implements ILivreDao{

    //Design pattern Singleton
    private static LivreDao instance;
    private LivreDao() {}
    public static LivreDao getInstance() {
        if (instance == null) {
            instance = new LivreDao();
        }
        return instance;
    }

    @Override
    public List<Livre> getList() throws DaoException {
        try(Connection conn = ConnectionManager.getConnection();
        PreparedStatement pstmt = conn.prepareStatement("SELECT id, titre, auteur, ISBN FROM livre"))
        {
            List<Livre> liste = new ArrayList<>();
            ResultSet res = pstmt.executeQuery();

            while(res.next()){
                liste.add(new Livre(rs.getInt("id"), rs.getString("titre"), rs.getString("auteur"), rs.getString("ISBN")));
            }
            return liste;

        } catch(SQLException e){
            e.printStackTrace();
            throw new DaoException();
        }
    }

    @Override
    public Optional<Livre> getById(int id) throws DaoException { //Modified ILivreDao
        try(Connection conn = ConnectionManager.getConnection();
        PreparedStatement pstmt = conn.prepareStatement("SELECT id, titre, auteur, isbn FROM livre WHERE id = ?"))
        {
            pstmt.setInt(1, id); // Gestion du ?

            List<Livre> liste = new ArrayList<>();
            ResultSet res = pstmt.executeQuery();

            if (res.next()) { return Optional.ofNullable(new Livre(res.getInt("id"), res.getString("titre"), res.getString("auteur"), res.getString("isbn")));}
            else { return Optional.empty();}
        }
        catch(SQLException e){
            e.printStackTrace();
            throw new DaoException();
        }
    }

    @Override
    public int create(String titre, String auteur, String isbn) throws DaoException {
        try(Connection conn = ConnectionManager.getConnection();
        PreparedStatement pstmt = conn.prepareStatement("INSERT INTO livre(titre, auteur, isbn) VALUES (?, ?, ?)", Statement.RETURN_GENERATED_KEYS))
        {
            pstmt.setString(1, titre); //Gestions des ?
            pstmt.setString(2, auteur);
            pstmt.setString(3, isbn);

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
    public void update(Livre livre) throws DaoException {
        try(Connection conn = ConnectionManager.getConnection();
        PreparedStatement pstmt = conn.prepareStatement("UPDATE livre SET titre = ?, auteur = ?, isbn = ? WHERE id = ?"))
        {
            pstmt.setString(1, livre.getTitre()); //Gestions des ?
            pstmt.setString(2, livre.getAuteur());
            pstmt.setString(3, livre.getIsbn());
            pstmt.setInt(4, livre.getId());

            pstmt.executeUpdate();
        }
        catch(SQLException e){
            e.printStackTrace();
            throw new DaoException();
        }
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

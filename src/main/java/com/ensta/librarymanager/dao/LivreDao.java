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
    public Livre getById(int id) throws DaoException {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public int create(String titre, String auteur, String isbn) throws DaoException {
        // TODO Auto-generated method stub
        return 0;
    }

    @Override
    public void update(Livre livre) throws DaoException {
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

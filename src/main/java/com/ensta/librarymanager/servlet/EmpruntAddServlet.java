package com.ensta.librarymanager.servlet;

import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.ensta.librarymanager.exception.ServiceException;
import com.ensta.librarymanager.modele.Emprunt;
import com.ensta.librarymanager.modele.Livre;
import com.ensta.librarymanager.modele.Membre;
import com.ensta.librarymanager.service.EmpruntService;
import com.ensta.librarymanager.service.LivreService;
import com.ensta.librarymanager.service.MembreService;

@WebServlet("/emprunt_add")
public class EmpruntAddServlet extends HttpServlet {
    LivreService livreService = LivreService.getInstance();
    MembreService membreService = MembreService.getInstance();
    EmpruntService empruntService = EmpruntService.getInstance();

    //La méthode doGet() permet d’afficher un formulaire d’ajout
    // d’emprunt, basé sur deux champs de type <select> qui ne devront
    // contenir respectivement que les livres disponibles à l’emprunt et
    // que les membres pouvant réaliser un nouvel emprunt.
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            request.setAttribute("listLivre", livreService.getListDispo());
            request.setAttribute("listMembre", membreService.getListMembreEmpruntPossible());
            
            this.getServletContext().getRequestDispatcher("/WEB-INF/View/emprunt_add.jsp").forward(request, response);
        }
        catch (ServiceException e) {
            e.printStackTrace();
            throw new ServletException();
        }
    }

    // La méthode doPost() a pour unique rôle de traiter le formulaire de
    // création d’un nouvel emprunt à partir des données récupérées via
    // le formulaire précédent. objectif mise à jour de la base de données.
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            //Création emprunt à partir des données récupérées
            int idLivre = Integer.parseInt(request.getParameter("idLivre"));
            int idMembre = Integer.parseInt(request.getParameter("idMembre"));
            LocalDate dateEmprunt = LocalDate.now();
            empruntService.create(idMembre, idLivre, dateEmprunt);
            
            //Mise à jour de la BDD
            List<Emprunt> listEmprunt;
            listEmprunt = this.empruntService.getListCurrent();
            List<Livre> listLivre = new ArrayList<>();
            List<Membre> listMembre = new ArrayList<>();
            for (Emprunt emprunt : listEmprunt) {
                listLivre.add(livreService.getById(emprunt.getIdLivre()));
                listMembre.add(membreService.getById(emprunt.getIdMembre()));
            }
            request.setAttribute("listEmprunt", listEmprunt);
            request.setAttribute("listMembre", listMembre);
            request.setAttribute("listLivre", listLivre);

            this.getServletContext().getRequestDispatcher("/WEB-INF/View/emprunt_list.jsp").forward(request, response);          
        } catch (ServiceException e) {
            e.printStackTrace();
            throw new ServletException();
        }
    }
}

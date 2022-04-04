package com.ensta.librarymanager.servlet;

import java.io.IOException;
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
            request.setAttribute("listLivreDispo", livreService.getListDispo());
            request.setAttribute("listMembreEP", membreService.getListMembreEmpruntPossible());
        } catch (ServiceException e) {
            e.printStackTrace();
        }
    }

    // La méthode doPost() a pour unique rôle de traiter le formulaire de
    // création d’un nouvel emprunt à partir des données récupérées via
    // le formulaire précédent.
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
    }
}

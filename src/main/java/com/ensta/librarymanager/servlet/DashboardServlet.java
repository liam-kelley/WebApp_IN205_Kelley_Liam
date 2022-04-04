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

//permet d’afficher les informations du tableau du bord : nombre de livres, nombre de membres, nombre d’emprunts, liste des emprunts en cours.
@WebServlet("/dashboard")
public class DashboardServlet extends HttpServlet {
    LivreService livreService = LivreService.getInstance();
    MembreService membreService = MembreService.getInstance();
    EmpruntService empruntService = EmpruntService.getInstance();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            List<Emprunt> listEmpruntEnCours = this.empruntService.getListCurrent();
            List<Livre> listLivres = new ArrayList<>();
            List<Membre> listMembres = new ArrayList<>();
            for (Emprunt emprunt : listEmpruntEnCours) {
                listLivres.add(livreService.getById(emprunt.getIdLivre()));
                listMembres.add(membreService.getById(emprunt.getIdMembre()));
            }
            //Set variables to send to jsps
            request.setAttribute("livreCount", this.livreService.count());
            request.setAttribute("membreCount", this.membreService.count());
            request.setAttribute("empruntCount", this.empruntService.count());
            request.setAttribute("listEmpruntEnCours", listEmpruntEnCours);
            request.setAttribute("listMembres", listMembres);
            request.setAttribute("listLivres", listLivres);

            this.getServletContext().getRequestDispatcher("/WEB-INF/View/dashboard.jsp").forward(request, response);
        }
        catch (ServiceException e) {
            e.printStackTrace();
            throw new ServletException();
        }
    }
    
    //doPost == doGet??
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        this.doGet(request, response);
    }
}

# WebApp_IN205_Kelley_Liam

initializing BDD: "mvn clean install exec:java"

running website: "mvn tomcat7:run"

Notes:

en gros dans un servlet t'as deux fonctions doget et dopost

Doget permet d'envoyer des valeurs pour l'affichage et dopost permet de traiter les formulaires remplis sur le site web pour mettre à jour la base de donnée en gros

request.setAttribute("livreCount",this.livreService.count()); t'envoie au fichier jsp associé à ton servlet la variable livreCount qui prend la valeur renvoyée par la fonction

et dans le fichier jsp si tu veux utiliser la valeur t'écris ${livreCount}

pour récupérer une donnée c'est  : String idMembreString = request.getParameter("idMembre");

Pour rediriger vers une autre page response.sendRedirect("/TP3Ensta/emprunt_list");

et attention y a des petites erreurs dans les fichiers jsp il faut remplacer des fois LibraryManager par TP3Ensta je crois bien
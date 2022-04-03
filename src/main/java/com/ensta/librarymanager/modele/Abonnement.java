package com.ensta.librarymanager.modele;


public enum Abonnement {
    BASIC(2), PREMIUM(5), VIP(20);

    public final int nlivres;

	private Abonnement(int nlivres) {
		this.nlivres = nlivres;
	}

}

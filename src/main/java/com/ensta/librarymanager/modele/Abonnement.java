package com.ensta.librarymanager.modele;


public enum Abonnement {
    BASIC(2), PREMIUM(5), VIP(20);

    private int increment;

	private Abonnement(int increment) {
		this.increment = increment;
	}
	
	public int getIncrement() {
		return increment;
	}


}

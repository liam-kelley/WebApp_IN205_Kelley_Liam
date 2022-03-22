package com.ensta.librarymanager.modele;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public enum Abonnement {
    BASIC(2), PREMIUM(5), VIP(20);

    private static final List<Abonnement> VALUES = Collections.unmodifiableList(Arrays.asList(values()));
	private static final int SIZE = VALUES.size();
	private static final Random RANDOM = new Random();
	private int increment;

	private Abonnement(int increment) {
		this.increment = increment;
	}
	
	public int getIncrement() {
		return increment;
	}

	public static Orientation randomOrientation() {
		return VALUES.get(RANDOM.nextInt(SIZE));
	}

}

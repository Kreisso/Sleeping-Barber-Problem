package com.company;

public class Main {

    public static void main(String[] args) {
	    BarberShop b = new BarberShop();
	    Barber g = new Barber(b);
	    ClientsMaker k = new ClientsMaker(b);

	    g.start();
	    k.start();

    }
}

package com.company;

public class ClientsMaker extends Thread {

    BarberShop shop;

    public ClientsMaker(BarberShop shop) {
        this.shop = shop;
    }

    public void run() {
        for (int i = 0; i < 13; i++) {
            try {
                sleep((int) (Math.random() * 1000));
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            shop.lock.lock();
            try {
                shop.addClient(new Integer(i));
            } catch (Exception e) {

            } finally {
                shop.lock.unlock();
            }
        }
    }
}

package com.company;

public class Barber extends Thread{

    BarberShop work;

    public Barber(BarberShop work)
    {
        this.work = work;
    }

    public void run() {
        while (true) {
            // praca.lock.lock();

            try {
                work.getClient();
                System.out.println("Cut client " + work.shavenClient);
                sleep((int) (Math.random() * 1000));
                System.out.println("Client " + work.shavenClient +" went home");
                work.shavenClient = null;

            } catch (InterruptedException exc) {
                System.out.println(exc);
            } finally {
                //     praca.lock.unlock();
            }

        }
    }
}

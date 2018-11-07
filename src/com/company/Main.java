package com.company;

import com.sun.jmx.remote.internal.ArrayQueue;

import java.util.List;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

class Golibroda extends Thread{

    Budynek praca;
    Integer klientStrzyzony;

    public Golibroda(Budynek praca)
    {
        this.praca = praca;
    }

    public void run() {
        while (true) {
           // praca.lock.lock();

            try {
                praca.getKlient();
                System.out.println("strzyzony klient nr: " + praca.klientNaKrzesle);
                sleep((int) (Math.random() * 1000));
                System.out.println("Skończył strzyc klienta nr: " + praca.klientNaKrzesle);
                praca.klientNaKrzesle = null;

            } catch (InterruptedException exc) {
                System.out.println(exc);
            } finally {
           //     praca.lock.unlock();
            }

        }
    }


}

class Klient extends Thread{

    Budynek barber;

    public Klient(Budynek barber)
    {
        this.barber = barber;
    }

    public void run()
    {
        for(int i =0; i < 3 ; i++)
        {
            try {
                sleep((int) (Math.random() * 1000));
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            barber.lock.lock();
            try {
                barber.addKlient(new Integer(i));
            }catch (Exception e){

            }finally {
                barber.lock.unlock();
            }


        }
    }
}

class Budynek{
    Lock lock = new ReentrantLock();
    boolean krzeslo = true;
    List<Integer> poczekalnia = new ArrayQueue<Integer>(3);
    Integer klientNaKrzesle = null;

    synchronized Integer getKlient()
    {

        while (poczekalnia.isEmpty())
        {
            try {
                wait();
            }catch (InterruptedException e)
            {

            }
        }
        lock.lock();
        try{
            if(klientNaKrzesle == null) {
                klientNaKrzesle = poczekalnia.get(0);
                poczekalnia.remove(0);
                krzeslo = false;
            }
            notifyAll();
        }catch (Exception e){
            System.out.println("Błąd przy wzięciu klienta z poczekalni "+ e);
        }finally {
            lock.unlock();
        }

        return klientNaKrzesle;
    }

    synchronized void addKlient(Integer i)
    {
        lock.lock();
        try
        {
            if(krzeslo)
            {
                klientNaKrzesle = i;
                krzeslo = false;
                System.out.println("Na krześle wolnym po przyjściu siada klient "+ klientNaKrzesle);
                notifyAll();
            }
            else
            {
                System.out.println("Dodany klient "+i);
                poczekalnia.add(i);
                notifyAll();
            }
        }catch (Exception e){

        }
        finally {
            lock.unlock();
        }



    }
}


public class Main {

    public static void main(String[] args) {
	    Budynek b = new Budynek();
	    Golibroda g = new Golibroda(b);
	    Klient k = new Klient(b);

	    g.start();
	    k.start();
        //System.out.println("test");

    }
}

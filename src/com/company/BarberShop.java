package com.company;

import com.sun.jmx.remote.internal.ArrayQueue;

import java.util.List;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class BarberShop {
        Lock lock = new ReentrantLock();
        boolean emptyBarberChair = true;
        List<Integer> lobby = new ArrayQueue<Integer>(8);
        Integer shavenClient = null;

        synchronized Integer getClient()
        {

            while (lobby.isEmpty()) {
                try {
                    wait();
                }catch (InterruptedException e) {
                    System.out.println(e);
                }
            }
            lock.lock();
            try{
                if(shavenClient == null) {
                    shavenClient = lobby.remove(0);
                    emptyBarberChair = false;
                }
                notifyAll();
            }catch (Exception e){
                System.out.println("Error Client "+ e);
            }finally {
                lock.unlock();
            }
            return shavenClient;
        }

        synchronized void addClient(Integer i)
        {
            lock.lock();
            try {
                if(emptyBarberChair) {
                    shavenClient = i;
                    emptyBarberChair = false;
                    System.out.println("Chair is empty. Barber invites a client "+ shavenClient);
                    notifyAll();
                }
                else {
                    System.out.println("Adding a client "+i+" to the waiting room");
                    lobby.add(i);
                    notifyAll();
                }
            }catch (Exception e){
                System.out.println(e);
            }
            finally {
                lock.unlock();
            }
        }
    }


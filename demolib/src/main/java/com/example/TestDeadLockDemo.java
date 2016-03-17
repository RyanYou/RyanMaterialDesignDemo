package com.example;

/**
 * 死锁例子
 */
public class TestDeadLockDemo {

    public static void main(String[] args){
        Object phone = new Object();
        Object pad = new Object();
        Thread action1 = new Thread(new Ryan(phone,pad),"Ryan");
        Thread action2 = new Thread(new Maggie(phone,pad),"Maggie");
        action1.start();
        action2.start();
    }

    public static class Ryan implements Runnable{

        private Object phone,pad;
        public Ryan(Object phone , Object pad){
            this.phone = phone;
            this.pad = pad;
        }

        @Override
        public void run() {
            synchronized (phone){
                System.out.println("Ryan pick up phone!");
                synchronized (pad){
                    System.out.println("Ryan pick up phone , then pick up pad!");
                }
            }
        }
    }

    public static class Maggie implements Runnable{

        private Object phone,pad;
        public Maggie(Object phone , Object pad){
            this.phone = phone;
            this.pad = pad;
        }

        @Override
        public void run() {
            synchronized (pad){
                System.out.println("Maggie pick up pad!");
                synchronized (phone){
                    System.out.println("Maggie pick up phone , then pick up phone!");
                }
            }
        }
    }

}

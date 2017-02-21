package com.part.rbt;

/**
 * Created by Administrator on 2017/2/15.
 */
public class Main {
    public static void main(String[] args){

        Thread t = new Thread(new Runnable() {
            public void run() {
                Const.M.put("A","haha");
                RabbitMQR r = new RabbitMQR();
                r.receive();
            }
        });
        t.start();

        Thread t1 = new Thread(new Runnable() {
            public void run() {
                while(true){
                    System.out.println(Const.M.get("A"));
                    try {
                        Thread.currentThread().sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
        t1.start();

        Thread t2 = new Thread(new Runnable(){
            public void run() {
                while (true) {
                    if (!Const.M.containsKey("A")) {
                        Const.M.put("A", "haha");
                        RabbitMQR r = new RabbitMQR();
                        r.receive();
                    }
                    try {
                        Thread.sleep(600000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
        t2.start();
    }
}

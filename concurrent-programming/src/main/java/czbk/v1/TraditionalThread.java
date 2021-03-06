package czbk.v1;

/**
 * 传统创建线程的两种方式
 * Created by liguodong on 2016/2/27.
 */

public class TraditionalThread {

    public static void main(String[] args) {
        //方式一
        Thread thread = new Thread(){
            @Override
            public void run() {
                while (true){
                    try {
                        Thread.sleep(500);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    System.out.println("1---1:"+Thread.currentThread().getName()+"...");
                    System.out.println("1---2:"+this.getName());
                }

            }
        };
        thread.start();

        //方式二
        Thread thread2 = new Thread(new Runnable() {
            public void run() {
                while (true){
                    try {
                        Thread.sleep(500);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    System.out.println("2---1:"+Thread.currentThread().getName()+"...");
                    //System.out.println("2---2:"+this.getName());
                }
            }
        });
        thread2.start();



        new Thread(
            new Runnable() {
                public void run() {
                    while (true){
                        try {
                            Thread.sleep(500);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        System.out.println("Runnable...");
                    }

                }
            }

        ){
            @Override
            public void run() {
                while (true){
                    try {
                        Thread.sleep(500);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    System.out.println("Thread...");
                }
            }
        }.start();


    }

}



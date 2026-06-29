package com.dashboard.saas.singletonloading;

public class Singleton {

        private static Singleton instance= new Singleton();

        private Singleton() {
            System.out.println("Object Created");
        }

        public static Singleton  getInstance(){

            if(instance ==null){
                instance = new Singleton();
            }
            return instance;
        }
    }



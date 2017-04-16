package com.snow.structxlee.imp;

import java.util.Observable;
import java.util.Observer;



public class SimpleObserver implements Observer {

    public  SimpleObserver(SimpleObservable observable){
        observable.addObserver(this);
    }

    public  void update(Observable observable, Object object){
        System.out.println("data is changed:"+((SimpleObservable)observable).getData());
    }

}

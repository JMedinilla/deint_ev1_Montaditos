package com.jmedinilla.medinillamontaditos;

import java.util.ArrayList;

class Repository extends ArrayList<Product> {
    private static Repository instance;

    private Repository() {
        this.add(new Product("Número 1", Product.TYPE_FOOD));
        this.add(new Product("Número 2", Product.TYPE_FOOD));
        this.add(new Product("Número 3", Product.TYPE_FOOD));
        this.add(new Product("Número 4", Product.TYPE_FOOD));
        this.add(new Product("Número 5", Product.TYPE_FOOD));
        this.add(new Product("Coca Cola", Product.TYPE_DRINK));
        this.add(new Product("Fanta", Product.TYPE_DRINK));
        this.add(new Product("Cerveza", Product.TYPE_DRINK));
    }

    static Repository getInstance() {
        if (instance == null) {
            instance = new Repository();
        }
        return instance;
    }
}

package com.jmedinilla.medinillamontaditos;

import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.NonNull;

import java.util.Comparator;
import java.util.UUID;

class Product implements Comparable<Product>, Parcelable {
    static final int TYPE_FOOD = 123;
    static final int TYPE_DRINK = 321;

    private String id;
    private String name;
    private int type;
    private int quantity;

    Product(String name, int type) {
        this.id = UUID.randomUUID().toString();
        this.type = type;
        this.name = name;
        this.quantity = 0;
    }

    private Product(Parcel in) {
        id = in.readString();
        name = in.readString();
        type = in.readInt();
        quantity = in.readInt();
    }

    public static final Creator<Product> CREATOR = new Creator<Product>() {
        @Override
        public Product createFromParcel(Parcel in) {
            return new Product(in);
        }

        @Override
        public Product[] newArray(int size) {
            return new Product[size];
        }
    };

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getType() {
        return type;
    }

    int getQuantity() {
        return quantity;
    }

    void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    @Override
    public String toString() {
        return this.name;
    }

    @Override
    public boolean equals(Object obj) {
        boolean result = false;
        if (obj != null) {
            if (obj instanceof Product) {
                result = ((Product) obj).getName().equalsIgnoreCase(this.getName());
            }
        }
        return result;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(id);
        parcel.writeString(name);
        parcel.writeInt(type);
        parcel.writeInt(quantity);
    }

    @Override
    public int compareTo(@NonNull Product product) {
        return product.getName().compareToIgnoreCase(this.name);
    }

    static final Comparator<Product> NAME_ASC = new Comparator<Product>() {
        @Override
        public int compare(Product product, Product t1) {
            return product.getName().compareToIgnoreCase(t1.getName());
        }
    };

    static final Comparator<Product> NAME_DESC = new Comparator<Product>() {
        @Override
        public int compare(Product product, Product t1) {
            return t1.getName().compareToIgnoreCase(product.getName());
        }
    };

    static final Comparator<Product> TYPE_ASC = new Comparator<Product>() {
        @Override
        public int compare(Product product, Product t1) {
            return product.getType() - t1.getType();
        }
    };

    static final Comparator<Product> TYPE_DESC = new Comparator<Product>() {
        @Override
        public int compare(Product product, Product t1) {
            return t1.getType() - product.getType();
        }
    };

    static final Comparator<Product> QUANT_ASC = new Comparator<Product>() {
        @Override
        public int compare(Product product, Product t1) {
            return t1.getQuantity() - product.getQuantity();
        }
    };

    static final Comparator<Product> QUANT_DESC = new Comparator<Product>() {
        @Override
        public int compare(Product product, Product t1) {
            return product.getQuantity() - t1.getQuantity();
        }
    };
}

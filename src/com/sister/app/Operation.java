package com.sister.app;

import java.io.Serializable;

public class Operation implements Serializable {
    //    site-id: sumber operasi
    //    value: karakter
    //    type: tipe operasi
    //    position: posisi karakter yang ingin dilakukan operasi
    public int site_id;
    public char value;
    public String type;
    public int position;
    public int counter;

    public Operation (int s, char v, String t, int p, int c) {
        site_id = s;
        value = v;
        type = t;
        position = p;
        counter = c;
    }

    public int getSite_id() {
        return site_id;
    }

    public void setSite_id(int site_id) {
        this.site_id = site_id;
    }

    public char getValue() {
        return value;
    }

    public void setValue(char value) {
        this.value = value;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    public int getCounter() {
        return counter;
    }

    public void setCounter(int counter) {
        this.counter = counter;
    }

    @Override
    public String toString() {
        return "Operation{" +
                "site_id=" + site_id +
                ", value=" + value +
                ", type='" + type + '\'' +
                ", position=" + position +
                ", counter=" + counter +
                '}';
    }
}

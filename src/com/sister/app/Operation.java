package com.sister.app;

public class Operation {
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
}
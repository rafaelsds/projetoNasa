package com.unesc.app.projetonasa;


import android.widget.ImageView;

public class Aviao  {

    private Integer id;
    private Float x, y, a, d, v, r;
    private boolean checado;
    private ImageView aviao;

    public Aviao(){

    }

    public Aviao(Integer id, Float x, Float y, Float a, Float d, Float v, Float r, ImageView aviao) {
        this.id = id;
        this.x = x;
        this.y = y;
        this.a = a;
        this.d = d;
        this.v = v;
        this.r = r;
        this.aviao = aviao;
    }

    public Float getR() {
        return r;
    }

    public void setR(Float r) {
        this.r = r;
    }

    public Float getV() {
        return v;
    }

    public void setV(Float v) {
        this.v = v;
    }

    public boolean isChecado() {
        return checado;
    }

    public void setChecado(boolean checado) {
        this.checado = checado;
    }

    public Float getD() {
        return d;
    }

    public void setD(Float d) {
        this.d = d;
    }

    public Float getA() {
        return a;
    }

    public void setA(Float a) {
        this.a = a;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Float getX() {
        return x;
    }

    public void setX(Float x) {
        this.x = x;
    }

    public Float getY() {
        return y;
    }

    public void setY(Float y) {
        this.y = y;
    }

    public ImageView getAviao() {
        return aviao;
    }

    public void setAviao(ImageView aviao) {
        this.aviao = aviao;
    }
}

package com.unesc.app.projetonasa;


import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

public class AdapterCard extends RecyclerView.Adapter<ViewAviao>{
    List<Aviao> list = new ArrayList<>();
    List<ViewAviao> myViewHoldersReference = new ArrayList<>();

    public AdapterCard() {}

    public void itemSet(Integer id, Float x, Float y, Float a, Float d, Float v, Float r, ImageView imgAviao, Context context) {
        Aviao aviao = new Aviao();

        aviao.setId(id);
        aviao.setA(a);
        aviao.setX(x);
        aviao.setY(y);
        aviao.setD(d);
        aviao.setV(v);
        aviao.setR(r);
        aviao.setAviao(imgAviao);

        list.add(aviao);
    }

    @Override
    public ViewAviao onCreateViewHolder(ViewGroup viewGroup, int itemType) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.card_data_grid,viewGroup,false);
        return new ViewAviao(view);
    }

    public void onBindViewHolder(final ViewAviao myViewHolder, final int position) {
        final Aviao myObject = list.get(position);
        //myViewHoldersReference.add(myViewHolder);
        //myViewHolder.bind(myObject);

        DecimalFormat df = new DecimalFormat("#.##");
        df.format(0.912385);

        myViewHolder.txtId.setText(myObject.getId().toString());
        myViewHolder.txtA.setText((myObject.getA()).toString());
        myViewHolder.txtX.setText(df.format(myObject.getX()).toString());
        myViewHolder.txtY.setText(df.format(myObject.getY()).toString());
        myViewHolder.txtD.setText((myObject.getD()).toString());
        myViewHolder.txtV.setText((myObject.getV()).toString());
        myViewHolder.txtR.setText(df.format(myObject.getR()).toString());

        if(myObject.isChecado()){
            myViewHolder.ckSelect.setChecked(true);
        }

        myViewHolder.ckSelect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(myViewHolder.ckSelect.isChecked()){
                    list.get(position).setChecado(true);
                }else{
                    list.get(position).setChecado(false);
                }

            }
        });

    }

    public void translandar(Integer x, Integer y, Integer id){
        for (int position=0; position < list.size(); position++)
            if (list.get(position).getId() == id){
                list.get(position).setY(list.get(position).getY() + y);
                list.get(position).setX(list.get(position).getX() + x);
            }
    }

    public void escalonar(Float x, Float y, Integer id) {
        for (int position = 0; position < list.size(); position++) {
            if (list.get(position).getId() == id) {
                list.get(position).setY(list.get(position).getY() * (y / 100f));
                list.get(position).setX(list.get(position).getX() * (x / 100f));
                return;
            }
        }

    }


    public void rotacionar(Float xP, Float yP,  Float anguloP, Integer id) {

        for (int position=0; position < list.size(); position++){
            if (list.get(position).getId() == id) {

                Double angulo  = anguloP / (180 / Math.PI);
                Float x, y;

                x = Float.parseFloat(String.valueOf((Math.cos(angulo) * xP))) - Float.parseFloat(String.valueOf(Math.sin(angulo) * yP));
                y = Float.parseFloat(String.valueOf((Math.cos(angulo) * yP))) + Float.parseFloat(String.valueOf(Math.sin(angulo) * xP));

                list.get(position).setY(y);
                list.get(position).setX(x);

                return;
            }
        }
    }

    public List<Aviao> getList(){
        return list;
    }



    public void limparCard(){
        for (int position=0; position < list.size(); position++) {
            list.remove(list.get(position));
        }

        if(list.size()>0)
            limparCard();
    }

    @Override
    public int getItemCount() {
        return list.size();
    }
}

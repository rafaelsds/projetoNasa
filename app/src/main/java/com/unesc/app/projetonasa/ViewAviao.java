package com.unesc.app.projetonasa;


import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.CheckBox;
import android.widget.TextView;

class ViewAviao extends RecyclerView.ViewHolder{

    CardView cardView;
    TextView txtId, txtY, txtX, txtA, txtR, txtD, txtV;
    CheckBox ckSelect;

    public ViewAviao(View itemView){
        super(itemView);

        txtId = (TextView) itemView.findViewById(R.id.dataGridId);
        txtY = (TextView) itemView.findViewById(R.id.dataGridY);
        txtX = (TextView) itemView.findViewById(R.id.dataGridX);
        txtA = (TextView) itemView.findViewById(R.id.dataGridA);
        txtR = (TextView) itemView.findViewById(R.id.dataGridR);
        txtD = (TextView) itemView.findViewById(R.id.dataGridD);
        txtV = (TextView) itemView.findViewById(R.id.dataGridV);
        ckSelect = (CheckBox)itemView.findViewById(R.id.dataGridCk);

        cardView = (CardView) itemView.findViewById(R.id.cardDataGrid);
    }

    public void bind(Aviao r){
    }

//    public void setSelectds(Aviao a) {
//        txtY.setText(a.getY());
//        txtX.setText(a.getX());
//    }



}
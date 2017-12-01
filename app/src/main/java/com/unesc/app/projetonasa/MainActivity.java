package com.unesc.app.projetonasa;

import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import java.util.ArrayList;
import java.util.List;

import cn.pedant.SweetAlert.SweetAlertDialog;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private Integer idAviao;
    private List<Aviao> list = new ArrayList<>();
    private RecyclerView recyclerView;
    private LinearLayout linearFiltroInputs, linearInputs, linearFiltroDataGrid, linearFiltroTranslandar, linearTranslandar;
    private LinearLayout linearFiltroEscalonar, linearEscalonar, linearRemoverAvioes, linearInputsCarteriano, linearInputsPolar;
    private LinearLayout linearFiltroRotacionar, linearRotacionar, linearDistanciaMinima, linearFiltroDistanciaMinima;

    private EditText edtCoordenadaX, edtCoordenadaY, edtRaio, edtAngulo, edtDirecao, edtTranslandarX, edtTranslandarY;
    private EditText edtEscalonarX, edtEscalonarY, edtVelocidade, edtRotacionarX, edtRotacionarY, edtRotacionarAngulo, edtDistancia;

    private Button btnAdicionar, btnTranslandar, btnEscalonar, btnRotacionar, btnAvioesProximos;
    private ImageView desenhoAviao, centro;
    private RelativeLayout aeroporto;
    private AlertDialog alerta;
    private AdapterCard adapterCard;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        adapterCard = new AdapterCard();

        inicialise();
        botoes();

    }



    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {

        int id = item.getItemId();

        if (id == R.id.nav_data_grid) {
            dataGrid();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }


    public void inicialise(){
        idAviao=0;
        centro = (ImageView)findViewById(R.id.centro);
        aeroporto = (RelativeLayout)findViewById(R.id.aeroporto);
        edtCoordenadaX = (EditText) findViewById(R.id.edtCoordenadaX);
        edtCoordenadaY = (EditText) findViewById(R.id.edtCoordenadaY);
        edtTranslandarX = (EditText) findViewById(R.id.edtTranslandarX);
        edtTranslandarY = (EditText) findViewById(R.id.edtTranslandarY);
        edtEscalonarY = (EditText) findViewById(R.id.edtEscalonarY);
        edtEscalonarX = (EditText) findViewById(R.id.edtEscalonarX);
        edtVelocidade = (EditText)findViewById(R.id.edtVelocidade);
        edtRotacionarX = (EditText)findViewById(R.id.edtRotacionarX);
        edtRotacionarY = (EditText)findViewById(R.id.edtRotacionarY);
        edtRotacionarAngulo = (EditText)findViewById(R.id.edtRotacionarAngulo);
        btnAdicionar = (Button)findViewById(R.id.btnPosicionar);
        btnTranslandar = (Button)findViewById(R.id.btnTranslandar);
        btnEscalonar = (Button)findViewById(R.id.btnEscalonar);
        btnRotacionar = (Button)findViewById(R.id.btnRotacionar);
        btnAvioesProximos = (Button)findViewById(R.id.btnAvioesProximos);
        edtRaio = (EditText)findViewById(R.id.edtRaio);
        edtAngulo = (EditText) findViewById(R.id.edtAngulo);
        edtDirecao = (EditText)findViewById(R.id.edtDirecao);
        edtDistancia = (EditText)findViewById(R.id.edtDistancia);
        linearFiltroInputs = (LinearLayout)findViewById(R.id.linearFiltroInputs);
        linearInputs = (LinearLayout)findViewById(R.id.linearInputs);
        linearFiltroDataGrid = (LinearLayout)findViewById(R.id.linearFiltroDataGrid);
        linearFiltroTranslandar = (LinearLayout)findViewById(R.id.linearFiltroTranslandar);
        linearTranslandar = (LinearLayout)findViewById(R.id.linearTranslandar);
        linearEscalonar = (LinearLayout)findViewById(R.id.linearEscalonar);
        linearFiltroEscalonar = (LinearLayout)findViewById(R.id.linearFiltroEscalonar);
        linearRemoverAvioes = (LinearLayout)findViewById(R.id.linearRemoverAvioes);
        linearInputsCarteriano = (LinearLayout)findViewById(R.id.linearInputsCartesiano);
        linearInputsPolar = (LinearLayout)findViewById(R.id.linearInputsPolar);
        linearFiltroRotacionar = (LinearLayout)findViewById(R.id.linearFiltroRotacionar);
        linearRotacionar = (LinearLayout)findViewById(R.id.linearRotacionar);
        linearDistanciaMinima = (LinearLayout)findViewById(R.id.linearDistanciaMinima);
        linearFiltroDistanciaMinima = (LinearLayout)findViewById(R.id.linearFiltroDistanciaMinima);
    }

    public void botoes(){
        btnAdicionar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                desenhoAviao = new ImageView(MainActivity.this);
                desenhoAviao.setImageResource(R.drawable.airplane);

                Integer id = ++idAviao;
                Float raio, angulo, x, y;


                if(!edtRaio.getText().toString().isEmpty()){
                    raio = obterCoordenadaDataGrid("R");
                    x = polartoCartesiano(Integer.parseInt(edtAngulo.getText().toString()), Integer.parseInt(edtRaio.getText().toString()),"X");
                }else{
                    raio = Float.parseFloat(String.valueOf(obterRaio(obterCoordenadaDataGrid("X"),obterCoordenadaDataGrid("Y"))));
                    x = obterCoordenadaCentro("X");
                }

                if(!edtAngulo.getText().toString().isEmpty()){
                    angulo = obterCoordenadaDataGrid("A");
                    y = polartoCartesiano(Integer.parseInt(edtAngulo.getText().toString()), Integer.parseInt(edtRaio.getText().toString()),"Y");
                }else{
                    angulo = obterAngulo(obterCoordenadaCentro("X"),obterCoordenadaDataGrid("Y"));
                    y = obterCoordenadaDataGrid("Y");
                }


                Aviao a = new Aviao(id, x,
                        y*(-1),
                        angulo,
                        obterCoordenadaCentro("D"),
                        obterCoordenadaCentro("V"),
                        raio,
                        desenhoAviao);


                adapterCard.itemSet(id, x,
                        y,
                        angulo,
                        obterCoordenadaDataGrid("D"),
                        obterCoordenadaDataGrid("V"),
                        raio,
                        desenhoAviao,
                        MainActivity.this);

                //Esconde o teclado para não redenrizar o aeroporto
                HideSoftkeyBoard.hideSoftKeyboard(MainActivity.this);

                //Esconde filtros
                linearInputs.setVisibility(View.GONE);

                adicionaAviaoAeroporto(a);

            }
        });

        linearFiltroInputs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //Esconde o teclado para não redenrizar o aeroporto
                HideSoftkeyBoard.hideSoftKeyboard(MainActivity.this);

                if(linearInputs.getVisibility() == View.GONE){
                    linearInputs.setVisibility(View.VISIBLE);
                }else{
                    linearInputs.setVisibility(View.GONE);
                }
            }
        });


        linearFiltroTranslandar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //Esconde o teclado para não redenrizar o aeroporto
                HideSoftkeyBoard.hideSoftKeyboard(MainActivity.this);

                if(linearTranslandar.getVisibility() == View.GONE){
                    linearTranslandar.setVisibility(View.VISIBLE);
                }else{
                    linearTranslandar.setVisibility(View.GONE);
                }
            }
        });


        linearFiltroEscalonar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //Esconde o teclado para não redenrizar o aeroporto
                HideSoftkeyBoard.hideSoftKeyboard(MainActivity.this);

                if(linearEscalonar.getVisibility() == View.GONE){
                    linearEscalonar.setVisibility(View.VISIBLE);
                }else{
                    linearEscalonar.setVisibility(View.GONE);
                }
            }
        });


        linearFiltroDistanciaMinima.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //Esconde o teclado para não redenrizar o aeroporto
                HideSoftkeyBoard.hideSoftKeyboard(MainActivity.this);

                if(linearDistanciaMinima.getVisibility() == View.GONE){
                    linearDistanciaMinima.setVisibility(View.VISIBLE);
                }else{
                    linearDistanciaMinima.setVisibility(View.GONE);
                }
            }
        });


        linearFiltroDataGrid.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //Esconde o teclado para não redenrizar o aeroporto
                HideSoftkeyBoard.hideSoftKeyboard(MainActivity.this);

                dataGrid();
            }
        });

        btnTranslandar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Boolean cardChecado=false;
                for(Aviao a : adapterCard.getList()){
                    if(a.isChecado()){
                        cardChecado=true;
                        if(validaDados(edtTranslandarX.getText().toString(),
                                edtTranslandarY.getText().toString(),"TRANSLANDAR")){

                            //Esconde o teclado para não redenrizar o aeroporto
                            HideSoftkeyBoard.hideSoftKeyboard(MainActivity.this);

                            //Esconde filtros
                            linearEscalonar.setVisibility(View.GONE);

                            adapterCard.translandar(Integer.parseInt(edtTranslandarX.getText().toString()),
                                    Integer.parseInt(edtTranslandarY.getText().toString()),
                                    a.getId());

                            translandar(Float.parseFloat(edtTranslandarX.getText().toString()),
                                    Float.parseFloat(edtTranslandarY.getText().toString()),
                                    a.getId());

                        }
                    }
                }

                if(!cardChecado){
                    new SweetAlertDialog(MainActivity.this, SweetAlertDialog.ERROR_TYPE)
                            .setTitleText("Oops...")
                            .setContentText("Nenhum aviao selecionado no DataGrid!")
                            .show();
                }

            }
        });


        btnEscalonar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Boolean cardChecado=false;
                for(Aviao a : adapterCard.getList()){
                    if(a.isChecado()){
                        cardChecado=true;
                        if(validaDados(edtEscalonarX.getText().toString(),
                                edtEscalonarY.getText().toString(),"ESCALONAR")){

                            //Esconde o teclado para não redenrizar o aeroporto
                            HideSoftkeyBoard.hideSoftKeyboard(MainActivity.this);

                            //Esconde filtros
                            linearEscalonar.setVisibility(View.GONE);

                            adapterCard.escalonar(Float.parseFloat(edtEscalonarX.getText().toString()),
                                    Float.parseFloat(edtEscalonarY.getText().toString()),
                                    a.getId());

                            escalonar(Float.parseFloat(edtEscalonarX.getText().toString()),
                                    Float.parseFloat(edtEscalonarY.getText().toString()),
                                    a.getId());

                        }
                    }
                }

                if(!cardChecado){
                    new SweetAlertDialog(MainActivity.this, SweetAlertDialog.ERROR_TYPE)
                            .setTitleText("Oops...")
                            .setContentText("Nenhum aviao selecionado no DataGrid!")
                            .show();
                }

            }
        });


        btnRotacionar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Boolean cardChecado=false;
                for(Aviao a : adapterCard.getList()){
                    if(a.isChecado()){
                        cardChecado=true;
                        if(validaDados(edtRotacionarX.getText().toString(),
                                edtRotacionarY.getText().toString(),"ROTACIONAR")){

                            //Esconde o teclado para não redenrizar o aeroporto
                            HideSoftkeyBoard.hideSoftKeyboard(MainActivity.this);

                            //Esconde filtros
                            linearRotacionar.setVisibility(View.GONE);

                            adapterCard.rotacionar(Float.parseFloat(edtRotacionarX.getText().toString()),
                                    Float.parseFloat(edtRotacionarY.getText().toString()),
                                    Float.parseFloat(edtRotacionarAngulo.getText().toString()),
                                    a.getId());

                            rotacionar(Float.parseFloat(edtRotacionarX.getText().toString()),
                                    Float.parseFloat(edtRotacionarY.getText().toString()),
                                    Float.parseFloat(edtRotacionarAngulo.getText().toString()),
                                    a.getId());

                        }
                    }
                }

                if(!cardChecado){
                    new SweetAlertDialog(MainActivity.this, SweetAlertDialog.ERROR_TYPE)
                            .setTitleText("Oops...")
                            .setContentText("Nenhum aviao selecionado no DataGrid!")
                            .show();
                }

            }
        });




        btnAvioesProximos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Boolean cardChecado=false;
                for(Aviao a : adapterCard.getList()){
                    if(a.isChecado()){
                        cardChecado=true;
                        if(validaDados(edtDistancia.getText().toString(),"","DISTANCIA")){

                            //Esconde o teclado para não redenrizar o aeroporto
                            HideSoftkeyBoard.hideSoftKeyboard(MainActivity.this);

                            //Esconde filtros
                            linearEscalonar.setVisibility(View.GONE);

                            adapterCard.escalonar(Float.parseFloat(edtEscalonarX.getText().toString()),
                                    Float.parseFloat(edtEscalonarY.getText().toString()),
                                    a.getId());

                            escalonar(Float.parseFloat(edtEscalonarX.getText().toString()),
                                    Float.parseFloat(edtEscalonarY.getText().toString()),
                                    a.getId());

                        }
                    }
                }

                if(!cardChecado){
                    new SweetAlertDialog(MainActivity.this, SweetAlertDialog.ERROR_TYPE)
                            .setTitleText("Oops...")
                            .setContentText("Nenhum aviao selecionado no DataGrid!")
                            .show();
                }

            }
        });




        linearRemoverAvioes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //Esconde o teclado para não redenrizar o aeroporto
                HideSoftkeyBoard.hideSoftKeyboard(MainActivity.this);

                limparAeroporto();
                adapterCard.limparCard();
            }
        });

        edtCoordenadaX.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(!hasFocus){
                    if(!edtCoordenadaX.getText().toString().isEmpty()||!edtCoordenadaY.getText().toString().isEmpty()){
                        linearInputsPolar.setVisibility(View.GONE);
                    }else{
                        linearInputsPolar.setVisibility(View.VISIBLE);
                    }
                }
            }
        });

        edtCoordenadaY.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(!hasFocus){
                    if(!edtCoordenadaY.getText().toString().isEmpty()||!edtCoordenadaX.getText().toString().isEmpty()){
                        linearInputsPolar.setVisibility(View.GONE);
                    }else{
                        linearInputsPolar.setVisibility(View.VISIBLE);
                    }
                }
            }
        });

        edtAngulo.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(!hasFocus){
                    if(!edtAngulo.getText().toString().isEmpty()||!edtRaio.getText().toString().isEmpty()){
                        linearInputsCarteriano.setVisibility(View.GONE);
                    }else{
                        linearInputsCarteriano.setVisibility(View.VISIBLE);
                    }
                }
            }
        });

        edtRaio.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(!hasFocus){
                    if(!edtRaio.getText().toString().isEmpty()||!edtAngulo.getText().toString().isEmpty()){
                        linearInputsCarteriano.setVisibility(View.GONE);
                    }else{
                        linearInputsCarteriano.setVisibility(View.VISIBLE);
                    }
                }
            }
        });

    }

    public void limparAeroporto(){
        for (int position=0; position < list.size(); position++) {
            aeroporto.removeView(list.get(position).getAviao());
            list.remove(list.get(position));
        }

        if(list.size()>0)
            limparAeroporto();
    }

    public boolean validaDados(String x, String y, String operacao) {
        Boolean isValid = true;

        if(operacao.equals("TRANSLANDAR")){
            if (x.isEmpty()) {
                edtTranslandarX.setError(getString(R.string.campo_obrigatorio));
                isValid = false;
            }

            if (y.isEmpty()) {
                edtTranslandarY.setError(getString(R.string.campo_obrigatorio));
                isValid = false;
            }

        }else if(operacao.equals("ESCALONAR")){
            if (x.isEmpty()) {
                edtEscalonarX.setError(getString(R.string.campo_obrigatorio));
                isValid = false;
            }

            if (y.isEmpty()) {
                edtEscalonarY.setError(getString(R.string.campo_obrigatorio));
                isValid = false;
            }
        }else if(operacao.equals("ROTACIONAR")){
            if (x.isEmpty()) {
                edtRotacionarX.setError(getString(R.string.campo_obrigatorio));
                isValid = false;
            }

            if (y.isEmpty()) {
                edtRotacionarY.setError(getString(R.string.campo_obrigatorio));
                isValid = false;
            }
        }else if(operacao.equals("DISTANCIA")){
            if (x.isEmpty()) {
                edtDistancia.setError(getString(R.string.campo_obrigatorio));
                isValid = false;
            }

        }



        return isValid;
    }

    public void dataGrid(){
        if(list.size()>0) {
            LayoutInflater li = getLayoutInflater();

            View view = li.inflate(R.layout.data_grid, null);

            view.findViewById(R.id.dataGridBtnOk).setOnClickListener(new View.OnClickListener() {
                public void onClick(View arg0) {
                    alerta.dismiss();
                }
            });


            recyclerView=(RecyclerView)view.findViewById(R.id.recyclerViewDataGrid);

            recyclerView.setLayoutManager(new LinearLayoutManager(MainActivity.this));
            recyclerView.setAdapter(adapterCard);

            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("Data Grid");
            builder.setView(view);
            alerta = builder.create();
            alerta.show();
        }
    }

    public void adicionaAviaoAeroporto(Aviao av){
        list.add(av);
        aeroporto.addView(av.getAviao());

        av.getAviao().setX(av.getX());
        av.getAviao().setY(av.getY());
        av.getAviao().setRotation(av.getD());
    }

    public void translandar(Float x, Float y, Integer id){

        desenhoAviao = new ImageView(MainActivity.this);
        desenhoAviao.setImageResource(R.drawable.airplane);

        for (int position=0; position < list.size(); position++){
            if (list.get(position).getId() == id) {

                y = (y*(-1)) + list.get(position).getY();
                x = x + list.get(position).getX();

                Aviao a = new Aviao(id, x, y,
                        list.get(position).getA(),
                        list.get(position).getD(),
                        list.get(position).getV(),
                        list.get(position).getR(),
                        desenhoAviao);

                aeroporto.removeView(list.get(position).getAviao());
                list.remove(list.get(position));

                adicionaAviaoAeroporto(a);
                return;
            }
        }

    }


    public void escalonar(Float x, Float y, Integer id){

        desenhoAviao = new ImageView(MainActivity.this);
        desenhoAviao.setImageResource(R.drawable.airplane);

        for (int position=0; position < list.size(); position++){
            if (list.get(position).getId() == id) {

                y = ((list.get(position).getY()*(-1)) * (y/100f)) * (-1);
                x = list.get(position).getX() * (x/100f);
//                y = ((list.get(position).getY()*(-1)) * y) * (-1);
//                x = list.get(position).getX() * x;

                Aviao a = new Aviao(id, Float.parseFloat(x.toString()), Float.parseFloat(y.toString()),
                        Float.parseFloat(String.valueOf(list.get(position).getA())),
                        list.get(position).getD(),
                        list.get(position).getV(),
                        list.get(position).getR(),
                        desenhoAviao);

                aeroporto.removeView(list.get(position).getAviao());
                list.remove(list.get(position));

                adicionaAviaoAeroporto(a);
                return;
            }
        }

    }


    public void rotacionar(Float xP, Float yP, Float anguloP, Integer id){

        desenhoAviao = new ImageView(MainActivity.this);
        desenhoAviao.setImageResource(R.drawable.airplane);

        for (int position=0; position < list.size(); position++){
            if (list.get(position).getId() == id) {

                Double angulo  = anguloP / (180 / Math.PI);
                Float x, y;

                x = Float.parseFloat(String.valueOf((Math.cos(angulo) * xP))) - Float.parseFloat(String.valueOf(Math.sin(angulo) * yP));
                y = Float.parseFloat(String.valueOf((Math.cos(angulo) * yP))) + Float.parseFloat(String.valueOf(Math.sin(angulo) * xP));

                Aviao a = new Aviao(id, Float.parseFloat(x.toString()), Float.parseFloat(y.toString()),
                        Float.parseFloat(String.valueOf(list.get(position).getA())),
                        list.get(position).getD(),
                        list.get(position).getV(),
                        list.get(position).getR(),
                        desenhoAviao);

                aeroporto.removeView(list.get(position).getAviao());
                list.remove(list.get(position));

                adicionaAviaoAeroporto(a);
                return;

            }
        }
    }


    public Float polartoCartesiano(Integer angulo, Integer raio, String eixo){
        Double calc;

        if(eixo.equals("Y")){
            calc = Math.cos((angulo / 180d * Math.PI));
        }else{
            calc = Math.sin(angulo / 180d * Math.PI);
        }

        return Float.parseFloat(String.valueOf(calc * raio));
    }


    public Float obterCoordenadaCentro(String eixo){
        if(eixo.equals("X") && edtCoordenadaX.getText() != null && !edtCoordenadaX.getText().toString().isEmpty()) {

            Float x = Float.parseFloat(edtCoordenadaX.getText().toString());

            if(x < aeroporto.getWidth()){
                return x;
            }else{
                return Float.parseFloat(String.valueOf(aeroporto.getWidth()));
            }

        }else if(eixo.equals("Y") && edtCoordenadaY.getText()!=null && !edtCoordenadaY.getText().toString().isEmpty()){

            Integer y = Integer.parseInt(edtCoordenadaY.getText().toString());

            if(y < aeroporto.getHeight()){
                return y * (-1f);
            }else{
                return Float.parseFloat(String.valueOf(aeroporto.getHeight()));
            }
        }else if(eixo.equals("A")){
            if(edtAngulo.getText()!=null && !edtAngulo.getText().toString().isEmpty())
                return Float.parseFloat(edtAngulo.getText().toString());
        }
        else if(eixo.equals("D")){
            if(edtDirecao.getText()!=null && !edtDirecao.getText().toString().isEmpty())
                return Float.parseFloat(edtDirecao.getText().toString())*(-1);

        }else if(eixo.equals("V")) {
            if (edtVelocidade.getText() != null && !edtVelocidade.getText().toString().isEmpty())
                return Float.parseFloat(edtVelocidade.getText().toString());

        }else if(eixo.equals("R")) {
            if (edtRaio.getText() != null && !edtRaio.getText().toString().isEmpty())
                return Float.parseFloat(edtRaio.getText().toString());
        }

        return 0f;

    }


    public Double obterRaio(Float x, Float y){
        return Math.sqrt((Math.pow(x, 2))+(Math.pow(y, 2)));
    }


    public Float obterAngulo(Float x, Float y){
        Float resultAlfa2 = Float.parseFloat(String.valueOf(Math.atan(x/y))) * Float.parseFloat(String.valueOf(180f/Math.PI)) ;

        if(x==0){
            if(y==0){
                resultAlfa2=0f;
            }
            if(y>0){
                resultAlfa2=90f;
            }if(y<0){
                resultAlfa2=270f;
            }
        }
        if(y==0){
            if(x<0){
                resultAlfa2=180f;
            }if(x>0){
                resultAlfa2=0f;
            }
        }


        if(x>0){
            if(y>0){
                resultAlfa2=resultAlfa2;
            }
            if(y<0) {
                resultAlfa2=360f+resultAlfa2;
            }
        }
        if(x<0){
            if(y>0){
                resultAlfa2=180f+resultAlfa2;
            }
            if(y<0) {
                resultAlfa2=180f+resultAlfa2;
            }
        }
        return resultAlfa2;
    }

    public Float obterCoordenadaDataGrid(String eixo){
        if(eixo.equals("X") && edtCoordenadaX.getText() != null && !edtCoordenadaX.getText().toString().isEmpty()) {

            Integer x = Integer.parseInt(edtCoordenadaX.getText().toString());

            if(x < aeroporto.getWidth()){
                return Float.parseFloat(String.valueOf(x));
            }else{
                return Float.parseFloat(String.valueOf(aeroporto.getWidth()));
            }

        }else if(eixo.equals("Y") && edtCoordenadaY.getText()!=null && !edtCoordenadaY.getText().toString().isEmpty()){

            Integer y = Integer.parseInt(edtCoordenadaY.getText().toString());

            if(y < aeroporto.getHeight()){
                return Float.parseFloat(String.valueOf(y));
            }else{
                return Float.parseFloat(String.valueOf(aeroporto.getHeight()));
            }
        }else if(eixo.equals("A")){
            if(edtAngulo.getText()!=null && !edtAngulo.getText().toString().isEmpty())
                return Float.parseFloat(edtAngulo.getText().toString());
        }
        else if(eixo.equals("D")){
            if(edtDirecao.getText()!=null && !edtDirecao.getText().toString().isEmpty())
                return Float.parseFloat(edtDirecao.getText().toString());

        }else if(eixo.equals("V")) {
            if (edtVelocidade.getText() != null && !edtVelocidade.getText().toString().isEmpty())
                return Float.parseFloat(edtVelocidade.getText().toString());

        }else if(eixo.equals("R")) {
            if (edtRaio.getText() != null && !edtRaio.getText().toString().isEmpty())
                return Float.parseFloat(edtRaio.getText().toString());
        }

        return 0f;

    }

}

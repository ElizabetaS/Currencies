package com.example.elisp.valuti;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.elisp.valuti.api.RestApi;
import com.example.elisp.valuti.klasi.Valuti;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class detali extends AppCompatActivity {


    @BindView(R.id.name)
    TextView nameValuta;
    @BindView(R.id.slika)
    ImageView slikaValuta;
    @BindView(R.id.rank)
    TextView rankValuta;
    @BindView(R.id.priceUSD)
    TextView cenausdValuta;
    @BindView(R.id.priceBTC)
    TextView cenabtcValuta;
    @BindView(R.id.supply)
    TextView supply;
    RestApi api;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detali);
        ButterKnife.bind(this);
        Intent intent = getIntent();
        if (intent.hasExtra("Valuta")) {

            final Valuti valuta = (Valuti) intent.getSerializableExtra("Valuta");
            api = new RestApi(detali.this);
            Call<ArrayList<Valuti>> call = api.getBtc(valuta.getId());
            call.enqueue(new Callback<ArrayList<Valuti>>() {
                @Override
                public void onResponse(Call<ArrayList<Valuti>> call, Response<ArrayList<Valuti>> response) {
                    if (response.isSuccessful()) {
                        ArrayList<Valuti> valuti = response.body();
                        int position = 0;

                        Valuti valutaOdbrana = valuti.get(position);
                        nameValuta.setText(valutaOdbrana.getName());
                        String rang = valutaOdbrana.getRank();
                        rankValuta.setText(rang+"");
                        String url = "https://files.coinmarketcap.com/static/img/coins/64x64/" + valutaOdbrana.getId() + ".png";
                        Picasso.with(detali.this).load(url).centerInside().fit().into(slikaValuta);
                        double cenaUSD = valutaOdbrana.getPrice_usd();

                        double cenaBTC = valutaOdbrana.getPrice_btc();
                        double supp = valutaOdbrana.available_supply;
                        cenausdValuta.append(cenaUSD+"");
                        cenabtcValuta.append(cenaBTC+"");
                        supply.append(supp+"");

                    }
                }

                @Override
                public void onFailure(Call<ArrayList<Valuti>> call, Throwable t) {
                    Toast.makeText(detali.this, "Error", Toast.LENGTH_SHORT).show();
                }
            });

        }
    }
}
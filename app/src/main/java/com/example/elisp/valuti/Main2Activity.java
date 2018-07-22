package com.example.elisp.valuti;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.transition.Visibility;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import com.example.elisp.valuti.api.RestApi;
import com.example.elisp.valuti.klasi.Model;
import com.example.elisp.valuti.klasi.Valuti;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Main2Activity extends AppCompatActivity {

    @BindView(R.id.recVtor)
    RecyclerView recycle;
    BroadcastReceiver mReceiver;

    AdapterVtoraStrana adapter;
    ArrayList<Valuti> coins;

    public Model favo;
    RestApi api;
    FavoritePreferences preferences;
    String conv;
    int lim;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        ButterKnife.bind(this);

        favo = FavoritePreferences.getFav(this);
        if(favo==null){
            favo= new Model();
        }
        api = new RestApi(Main2Activity.this);
        conv = preferences.getConvert(this);
        lim = preferences.getLimit(this);
        recycle.setHasFixedSize(true);
        recycle.setLayoutManager(new LinearLayoutManager(this));
            initialize();
    }

    private void initialize() {
        Call<ArrayList<Valuti>> call = api.getCoins(conv,lim);
        call.enqueue(new Callback<ArrayList<Valuti>>() {
            @Override
            public void onResponse(Call<ArrayList<Valuti>> call, Response<ArrayList<Valuti>> response) {

                if (response.code() == 200) {
                    coins = response.body();
                    adapter = new AdapterVtoraStrana(Main2Activity.this, coins, new OnValutaClickListener() {
                        @Override
                        public void onValutaLayoutClick(Valuti valuta, int position) {
                            Intent intent = new Intent(Main2Activity.this, detali.class);
                            intent.putExtra("Valuta",valuta);
                            //intent.putExtra("pozicija", position);
                            startActivity(intent);
                        }

                        @Override
                        public void onLongClick(Valuti valuti, final int position) {

                        }

                    });
                    recycle.setAdapter(adapter);


                } else if (response.code() == 401) {
                    Toast.makeText(Main2Activity.this, "Greska", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ArrayList<Valuti>> call, Throwable t) {

            }
        });


    }

    @Override
    protected void onResume() {
        super.onResume();
        IntentFilter intentFilter = new IntentFilter("android.intent.action.FIREBASE");
        mReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                try
                {
                    String msg_for_me = intent.getStringExtra("notification");
                    String notificationBody = intent.getStringExtra("notificationBody");
                    if(msg_for_me != null && !msg_for_me.equals(""))
                    {
                        handleFirebaseNotification(msg_for_me,notificationBody);
                    }
                }catch (Exception e)
                {
                    e.printStackTrace();
                }
            }
        };
        registerReceiver(mReceiver,intentFilter);
    }
    public  void handleFirebaseNotification(String notification, String notificationBody)
    {
        Toast.makeText(this,notification + "-//-" + notificationBody,Toast.LENGTH_LONG).show();
        initialize();
    }

    @Override
    public void onBackPressed() {
        FavoritePreferences.addFav(favo, this);
        Intent intent = new Intent(Main2Activity.this,MainActivity.class);
        intent.putExtra("Lista",favo);
        setResult(RESULT_OK, intent);
        finish();

    }
}
package com.example.elisp.valuti;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.elisp.valuti.klasi.Model;
import com.example.elisp.valuti.klasi.Valuti;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {
    @BindView(R.id.recyclerPrv)
    RecyclerView prvaStrana;
    Model favoriti = new Model();
    AdapterPrvaStrana adapter;
    String con="";
    BroadcastReceiver mReceiver;
    int lim=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ButterKnife.bind(this);
        favoriti.valuti = new ArrayList<>();
        favoriti = FavoritePreferences.getFav(this);
        if (favoriti == null) {
            favoriti = new Model();
        }
        else
        {
            adapter = new AdapterPrvaStrana(this, favoriti.valuti, new OnValutaClickListener() {
                @Override
                public void onValutaLayoutClick(Valuti valuta, int position) {
                    Intent intent = new Intent(MainActivity.this, detali.class);
                    intent.putExtra("Valuta", favoriti.valuti.get(position).getId());
                    startActivity(intent);
                }

                @Override
                public void onLongClick(Valuti valuti, final int position) {
                    AlertDialog.Builder dialog = new AlertDialog.Builder(MainActivity.this);
                    dialog.setMessage("Are you sure to delete?");
                    dialog.setPositiveButton("Yes", new DialogInterface.OnClickListener() {

                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            favoriti = FavoritePreferences.getFav(MainActivity.this);
                            favoriti.valuti.remove(position);
                            FavoritePreferences.addFav(favoriti,MainActivity.this);
                            adapter.notifyDataSetChanged();
                        }
                    });
                    dialog.setNegativeButton("No", new DialogInterface.OnClickListener() {

                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            dialogInterface.dismiss();
                        }
                    });
                    dialog.create().show();
                }

            });
            prvaStrana.setHasFixedSize(true);
            prvaStrana.setLayoutManager(new LinearLayoutManager(this));
            prvaStrana.setAdapter(adapter);
        }

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, Main2Activity.class);
                startActivityForResult(intent, 1000);
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
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            Intent intent = new Intent(MainActivity.this,Settings.class);
            startActivity(intent);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode==1000 && resultCode==RESULT_OK) {


            favoriti = FavoritePreferences.getFav(this);

            adapter = new AdapterPrvaStrana(this, favoriti.valuti, new OnValutaClickListener() {
                @Override
                public void onValutaLayoutClick(Valuti valuta, int position) {
                    Intent intent = new Intent(MainActivity.this, detali.class);
                    intent.putExtra("details", favoriti.valuti.get(position).getId());
                    //intent.putExtra("pozicija", position);
                    startActivity(intent);
                }

                @Override
                public void onLongClick(Valuti valuti, int position) {

                }

            });

            prvaStrana.setAdapter(adapter);

        }

    }

}

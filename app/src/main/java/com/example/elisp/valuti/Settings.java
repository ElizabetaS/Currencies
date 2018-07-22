package com.example.elisp.valuti;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.elisp.valuti.api.RestApi;
import com.example.elisp.valuti.klasi.Model;
import com.example.elisp.valuti.klasi.User;
import com.example.elisp.valuti.klasi.Valuti;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Set;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Settings extends AppCompatActivity {
    private static final String TAG = LoginActivity.class.getSimpleName();
    @BindView(R.id.convert)
    RadioGroup convertGroup;
    @BindView(R.id.limit)
    RadioGroup limitGroup;
    @BindView(R.id.usd)
    RadioButton usd;
    @BindView(R.id.euro)
    RadioButton euro;
    @BindView(R.id.ime)
    TextView ime;
    @BindView(R.id.show50)
    RadioButton show50;
    @BindView(R.id.show100)
    RadioButton show100;
    @BindView(R.id.showall)
    RadioButton showall;
    @BindView(R.id.logOut)
    Button logOut;
    FavoritePreferences favoritePreferences;
    RadioButton conv;
    RadioButton lim;
    String convert="";
    int limit=0;
    private DatabaseReference mFirebaseDatabase;
    private FirebaseDatabase  mFirebaseInstance;
    String userId;
    User user;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        ButterKnife.bind(this);
        mFirebaseInstance = FirebaseDatabase.getInstance();
        userId = PreferencesManager.getUserID(this);
        convert  = FavoritePreferences.getConvert(Settings.this);
        if(convert!=null)
        {
            if(convert.equals("USD"))
            {
                usd.setChecked(true);
            }
            else
            {
                euro.setChecked(true);
            }
        }
        limit = FavoritePreferences.getLimit(Settings.this);
        if(limit!=0)
        {
            if(limit==50)
            {
                show50.setChecked(true);
            }
            else if(limit==100)
            {
                show100.setChecked(true);
            }
            else
            {
                showall.setChecked(true);
            }
        }
        ime.setText(userId.toString());
        mFirebaseDatabase = mFirebaseInstance.getReference("users");
        convertGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                int selectedID = radioGroup.getCheckedRadioButtonId();
                conv = (RadioButton) findViewById(selectedID);
              if(conv.getText().toString().equals("USD"))
              {
                  favoritePreferences.addConvert(Settings.this,"USD");
              }
              else
              {
                  favoritePreferences.addConvert(Settings.this,"EUR");
              }
            }
        });
        limitGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                int selectedID = radioGroup.getCheckedRadioButtonId();
                lim = (RadioButton) findViewById(selectedID);
                if(lim.getText().toString().equals("Show 100"))
                {
                    favoritePreferences.addLimit(Settings.this,100);
                }
                else if(lim.getText().toString().equals("Show 50"))
                {
                    favoritePreferences.addLimit(Settings.this,50);
                }
                else
                {
                    favoritePreferences.addLimit(Settings.this,0);
                }
            }
        });
        addUserChangeListener();

}
public void createUser()
{
    if(FavoritePreferences.getFav(this) != null) {
        user.favourites = FavoritePreferences.getFav(this).valuti;
        mFirebaseDatabase.child(userId).setValue(user);
        PreferencesManager.addUser(user,this);
        PreferencesManager.setUserID(userId,this);
        addUserChangeListener();

    }
}
@OnClick(R.id.logOut)
public void setLogOut(View view)
{
    PreferencesManager.removeUserID(this);
    FavoritePreferences.removeFavorites(this);
    Intent intent = new Intent(Settings.this,LoginActivity.class);
    startActivity(intent);
    finish();
}
@OnClick(R.id.save)
public void save(View view)
{
    createUser();
    Intent intent = new Intent(Settings.this, MainActivity.class);
    startActivity(intent);

}

    private void addUserChangeListener() {
        // User data change listener
        mFirebaseDatabase.child(userId).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
            user = dataSnapshot.getValue(User.class);
                // Check for null
                if (user == null) {
                    Log.e(TAG, "User data is null!");
                    return;
                }

                Log.e(TAG, "User data is changed!" + user.name + ", " + user.mail);

                // Display newly updated name and email
                ime.setText(user.name + ", " + user.mail);
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.e(TAG, "Failed to read user", error.toException());
            }
        });
    }

    }

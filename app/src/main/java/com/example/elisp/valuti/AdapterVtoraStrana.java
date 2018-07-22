package com.example.elisp.valuti;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.elisp.valuti.klasi.Model;
import com.example.elisp.valuti.klasi.Valuti;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by elisp on 25.1.2018.
 */

public class AdapterVtoraStrana extends RecyclerView.Adapter<AdapterVtoraStrana.ViewHolder> {


    ArrayList<Valuti> valuti;
    OnValutaClickListener onValutaClickListener;
    Context context;
    String json;

    public AdapterVtoraStrana(Context context, ArrayList<Valuti> favoritesLis, OnValutaClickListener listener2) {
        this.context = context;
        this.valuti = favoritesLis;
        this.onValutaClickListener= listener2;

    }

    public void addValuti (ArrayList<Valuti> value,OnValutaClickListener listener){
        valuti = value;
        onValutaClickListener = listener;
        notifyDataSetChanged();
    }

    public AdapterVtoraStrana(Context context,OnValutaClickListener _onValutaClickListener) {
        this.context = context;
        this.onValutaClickListener = _onValutaClickListener;
    }

    @Override
    public AdapterVtoraStrana.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.row_recycler_vtor, parent, false);
        ViewHolder holder = new ViewHolder(view);

        return holder;
    }

    @Override
    public void onBindViewHolder(AdapterVtoraStrana.ViewHolder holder, final int position) {
        final Valuti valuta = valuti.get(position);
        String ime = valuti.get(position).getName();
        double usd = valuti.get(position).getPrice_usd();
        String rank = valuti.get(position).getRank();
        String id = valuti.get(position).getId();
        double euro = valuti.get(position).price_eur;

        String url = "https://files.coinmarketcap.com/static/img/coins/64x64/" + id + ".png";
        Picasso.with(context).load(url).centerInside().fit().into(holder.pic);
        String conv="";
        conv  = FavoritePreferences.getConvert(context);
        if(!conv.isEmpty())
        {
            if(conv.equals("USD"))
            {
                holder.usd.setText("USD: " + usd);
            }
            else
            {
                holder.usd.setText("EUR: " + euro);
            }
        }
        holder.ime.setText(ime);
        holder.rank.setText(rank + "");

        holder.addValuta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((Main2Activity) context).favo.valuti.add(valuta);
            }
        });
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onValutaClickListener.onValutaLayoutClick(valuta,position);
            }
        });
        holder.itemView.setOnLongClickListener(new View.OnLongClickListener()
        {

            @Override
            public boolean onLongClick(View view) {
                onValutaClickListener.onLongClick(valuta, position);
                return true;
            }
        });


    }
    @Override
    public int getItemCount() {
        return valuti.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        @BindView(R.id.valutaIme)TextView ime;
        @BindView(R.id.valutaUSD)TextView usd;
        @BindView(R.id.valutaRank)TextView rank;
        @BindView(R.id.slika)ImageView pic;
        @BindView(R.id.plus)ImageView addValuta;
        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }
}

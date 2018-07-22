package com.example.elisp.valuti;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
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

public class AdapterPrvaStrana extends RecyclerView.Adapter<AdapterPrvaStrana.ViewHolder> {


    Context context;
    ArrayList<Valuti>valutiArrayList;
    OnValutaClickListener listener;
    String json;

    public void addValuti (ArrayList<Valuti> value){
       valutiArrayList = value;
        notifyDataSetChanged();
    }

    public AdapterPrvaStrana(Context context, ArrayList<Valuti> coinsList,OnValutaClickListener listener) {
        this.context = context;
        this.valutiArrayList = coinsList;
        this.listener = listener;

    }


    @Override
    public AdapterPrvaStrana.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.row_recyclerprv, parent, false);
        ViewHolder holder = new ViewHolder(view);

        return holder;
    }

    @Override
    public void onBindViewHolder(AdapterPrvaStrana.ViewHolder holder, final int position) {
        final Valuti valuta = valutiArrayList.get(position);
        String url = "https://files.coinmarketcap.com/static/img/coins/64x64/" + valuta.getId() + ".png";
        Picasso.with(context).load(url).centerInside().fit().into(holder.pic);
        holder.ime.setText(valuta.getName());
        holder.rank.setText(valuta.getRank());
        String conv="";
        conv  = FavoritePreferences.getConvert(context);
        if(!conv.isEmpty())
        {
            if(conv.equals("USD"))
            {
                holder.usd.setText("USD: " + valuta.getPrice_usd());
            }
            else
            {
                holder.usd.setText("EUR: " + valuta.price_eur);
            }
        }
        holder.itemView.setOnLongClickListener(new View.OnLongClickListener()
        {

            @Override
            public boolean onLongClick(View view) {
                listener.onLongClick(valuta, position);
                return true;
            }
        });



    }
    @Override
    public int getItemCount() {
        return valutiArrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        @BindView(R.id.valutaIme)TextView ime;
        @BindView(R.id.valutaUSD)TextView usd;
        @BindView(R.id.valutaRank)TextView rank;
        @BindView(R.id.slika)ImageView pic;


        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }
}

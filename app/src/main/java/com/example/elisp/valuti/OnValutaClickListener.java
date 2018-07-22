package com.example.elisp.valuti;


import com.example.elisp.valuti.klasi.Valuti;

public interface OnValutaClickListener {
    public void onValutaLayoutClick(Valuti valuta, int position);
    public void onLongClick(Valuti valuti,int position);
}

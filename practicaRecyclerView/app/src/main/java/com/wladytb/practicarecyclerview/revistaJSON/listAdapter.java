package com.wladytb.practicarecyclerview.revistaJSON;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;
import com.wladytb.practicarecyclerview.R;
import com.wladytb.practicarecyclerview.modelo.revista;

import java.util.Date;
import java.util.List;

public class listAdapter extends RecyclerView.Adapter<listAdapter.ViewHolder> {
    private List<revista> revistas;
    private LayoutInflater minflater;
    private Context contexto;

    public listAdapter(List<revista> items, Context contexto) {
        this.minflater = LayoutInflater.from(contexto);
        this.contexto = contexto;
        this.revistas = items;
    }

    @Override
    public listAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View vista = minflater.inflate(R.layout.list_revista, null);
        return new listAdapter.ViewHolder(vista);
    }

    @Override
    public void onBindViewHolder(final listAdapter.ViewHolder holder, final int position) {
        holder.bindData(revistas.get(position));
    }

    @Override
    public int getItemCount() {
        return revistas.size();
    }

    public void setItems(List<revista> items) {
        revistas = items;
    }
    public class ViewHolder extends RecyclerView.ViewHolder{
        ImageView imgRevista;
        TextView vol,num,name,publi,doi;
        ViewHolder(View itView){
            super(itView);
            imgRevista=itView.findViewById(R.id.imgRevista);
            vol=itView.findViewById(R.id.txtVol);
            num=itView.findViewById(R.id.txtNum);
            name=itView.findViewById(R.id.txtNameRevista);
            publi=itView.findViewById(R.id.txtpublicacion);
            doi=itView.findViewById(R.id.txtdoi);
        }
        void bindData(final revista rv)
        {
            Picasso.get().load(rv.getCover()).into(imgRevista);
            vol.setText("Volumen: "+rv.getVolume());
            num.setText("Número: "+rv.getNumber() +" ("+rv.getYear()+")");
            name.setText(rv.getTitle());
            publi.setText("Publicado: "+rv.getDate_published());
            doi.setText("Doi: "+rv.getDoi());
        }
    }
}

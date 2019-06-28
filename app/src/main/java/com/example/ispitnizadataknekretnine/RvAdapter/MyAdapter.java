package com.example.ispitnizadataknekretnine.RvAdapter;

import android.graphics.BitmapFactory;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.ispitnizadataknekretnine.DataBase.Nekretnina;
import com.example.ispitnizadataknekretnine.R;

import java.util.List;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder> {


    private List<Nekretnina> nekretnine;
    private ItemClickListener listener;


    public MyAdapter(List<Nekretnina> nekretnine, ItemClickListener listener) {
        this.nekretnine = nekretnine;
        this.listener=listener;
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder{

        TextView tvNaslovCv;
        TextView tvAdresaCv;
        ImageView ivSlikaCv;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            tvNaslovCv = itemView.findViewById(R.id.tv_naslov_cv);
            tvAdresaCv = itemView.findViewById(R.id.tv_adresa_cv);
            ivSlikaCv = itemView.findViewById(R.id.iv_slika_cv);
        }

            public void bind (final Nekretnina nekretnina, final ItemClickListener listener){
            tvNaslovCv.setText(nekretnina.getNaziv());
            tvAdresaCv.setText(nekretnina.getAdresa());
            ivSlikaCv.setImageBitmap(BitmapFactory.decodeFile(nekretnina.getSlika()));
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.onItemClicked(nekretnina);
                }
            });
            }
    }


    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.single_item, viewGroup, false);
        MyViewHolder myViewHolder = new MyViewHolder(view);
        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder myViewHolder, int i) {
    myViewHolder.bind(nekretnine.get(i), listener);

    }

    @Override
    public int getItemCount() {
        return nekretnine.size();
    }


    public interface ItemClickListener {
        void onItemClicked (Nekretnina nekretnina);
    }

}

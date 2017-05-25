package com.example.thongle.bluetooth_hc05;

import android.app.Activity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import java.util.ArrayList;


public class AdapterEffect extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private LayoutInflater mInflater;
    private ArrayList<Effect> items;
    private Activity mActivity;

    public AdapterEffect(ArrayList<Effect> data, ConnectActivity activity) {
        this.items = data;
        this.mActivity = activity;
        this.mInflater = LayoutInflater.from(mActivity);
    }

    public void addItem(Effect result) {
        items.add(result);
    }

    public void setInflater(LayoutInflater layoutInflater){
        this.mInflater =layoutInflater;
    }

    public void replaceItems(ArrayList<Effect> newItems) {
        this.items.clear();
        for(Effect item: newItems)
            this.items.add(item);
    }

    public void insertItem(Effect item) {
        items.add(item);
    }

    public void clearItems(){
        items.clear();
    }

    public void AddResults(ArrayList<Effect> result) {
        items.addAll(result);
    }

    public Effect getItemsAt(int position){
        return  items.get(position);
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        Effect model = items.get(position);
        MessageViewHolder messageViewHolder = (MessageViewHolder) holder;
        messageViewHolder.imageView_logo.setBackgroundResource(model.getImage());
        messageViewHolder.textView_title.setText(model.getTitle());
        messageViewHolder.textView_code.setText("Code: " + model.getCode());
        messageViewHolder.textView_content.setText(model.getContent());
    }

    @Override
    public int getItemViewType(int position) {
        return  super.getItemViewType(position);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View rootCategoryView = mInflater.inflate(R.layout.item_effect_view, parent, false);
        return new MessageViewHolder(rootCategoryView, this);
    }

    private class MessageViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private ImageView imageView_logo;
        private TextView textView_title;
        private TextView textView_code;
        private TextView textView_content;
        private CardView cardView_effect;

        private MessageViewHolder(View itemView, AdapterEffect adapter) {
            super(itemView);
            imageView_logo = (ImageView) itemView.findViewById(R.id.img_logo);
            textView_title = (TextView) itemView.findViewById(R.id.txtv_title);
            textView_code = (TextView) itemView.findViewById(R.id.txtv_code);
            textView_content = (TextView) itemView.findViewById(R.id.txtv_content);
            cardView_effect = (CardView) itemView.findViewById(R.id.cardView_effect);

            cardView_effect.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            final int pos = getAdapterPosition();
            if (pos >= 0) {
                Toast.makeText(mActivity, "Selected Item Position "+pos, Toast.LENGTH_SHORT).show();
            }
        }
    }
}
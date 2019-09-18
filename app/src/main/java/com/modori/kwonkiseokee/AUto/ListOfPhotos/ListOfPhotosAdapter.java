package com.modori.kwonkiseokee.AUto.ListOfPhotos;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.CircularProgressDrawable;

import com.bumptech.glide.Glide;
import com.modori.kwonkiseokee.AUto.PhotoDetail.PhotoDetailView;
import com.modori.kwonkiseokee.AUto.R;
import com.modori.kwonkiseokee.AUto.data.data.Results;
import com.modori.kwonkiseokee.AUto.showPhotoOnly;

import java.util.ArrayList;

public class ListOfPhotosAdapter extends RecyclerView.Adapter<ListOfPhotosAdapter.ViewHolder> {

    private ArrayList<Results> photoData;
    Context context;
    CircularProgressDrawable circularProgressDrawable;

    int cnt = 0;

    public ListOfPhotosAdapter(Context context, ArrayList<Results> photoListData) {
        this.context = context;
        this.photoData = photoListData;
    }

    public ListOfPhotosAdapter(Context context) {
        this.context = context;
    }

    @NonNull
    @Override
    public ListOfPhotosAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.photo_item, parent, false);
        ViewGroup.MarginLayoutParams params = (ViewGroup.MarginLayoutParams) view.getLayoutParams();

        circularProgressDrawable = new CircularProgressDrawable(context);
        circularProgressDrawable.setStrokeWidth(5f);
        circularProgressDrawable.setCenterRadius(30f);
        circularProgressDrawable.start();

        if(cnt % 2 == 0){
            params.leftMargin = 14;
            params.rightMargin = 7;

        }else{
            params.rightMargin = 14;
            params.leftMargin = 7;
        }

        return new ListOfPhotosAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ListOfPhotosAdapter.ViewHolder holder, final int position) {
        String photoUrl = this.photoData.get(position).getUrls().getRegular();
        final String photoID = this.photoData.get(position).getId();


        Glide.with(context).load(photoUrl)
                .placeholder(circularProgressDrawable)
                .centerCrop().into(holder.imageCleanView);

        holder.photoCardofList.setOnClickListener(v -> {
            Context context = v.getContext();
            Log.d("ListOfPhotosAdapter", String.valueOf(position));

            Intent intent = new Intent(context, PhotoDetailView.class);
            intent.putExtra("id", photoID);
            Log.d("id", photoID);

            context.startActivity(intent);

        });

        holder.photoCardofList.setOnLongClickListener(v -> {

            Intent goPhotoOnly = new Intent(context, showPhotoOnly.class);
            goPhotoOnly.putExtra("photoUrl", photoUrl);
            context.startActivity(goPhotoOnly);

            return false;
        });
    }

    @Override
    public int getItemCount() {
        return photoData.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        ImageView imageCleanView;
        CardView photoCardofList;

        ViewHolder(@NonNull View itemView) {
            super(itemView);

            photoCardofList = itemView.findViewById(R.id.photoCardofList);
            imageCleanView = itemView.findViewById(R.id.imageCleanView);
        }
    }
}

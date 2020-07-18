package adapter;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Environment;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.dailyuse.clothes.dhobicount.MainActivity;
import com.dailyuse.clothes.dhobicount.R;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import object.DateAndCount;

import static android.os.Environment.DIRECTORY_PICTURES;

/**
 * Created by Sai sreenivas on 11/6/2017.
 */

public class HashtagImageAdapter extends RecyclerView.Adapter<HashtagImageAdapter.ViewHolder> {

    List<String> imageList;
    Context context;

    public HashtagImageAdapter(Context context, List<String> imageList) {
        super();
        this.context = context;
        this.imageList = imageList;
    }

    @Override
    public HashtagImageAdapter.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.item_grid_row_selected, viewGroup, false);
        HashtagImageAdapter.ViewHolder viewHolder = new HashtagImageAdapter.ViewHolder(v);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final HashtagImageAdapter.ViewHolder viewHolder, int position) {
//        viewHolder.imageUrl.setText(imageList.get(position));
        File f = Environment.getExternalStoragePublicDirectory(DIRECTORY_PICTURES + "/Count/" + imageList.get(position));
        Picasso.with(context).load(f).resize(80,120).into(viewHolder.image);
//        viewHolder.countView.setText(Integer.toString(dateAndCountsList.get(position).getCount()));

        viewHolder.setClickListener(new MainActivity.ItemClickListener() {
            @Override
            public void onClick(View view, int position, boolean isLongClick) {
                if (isLongClick) {
                    Toast.makeText(context, "#" + position + " - " + imageList.get(position) +
                            " (Long click)", Toast.LENGTH_SHORT).show();
                    context.startActivity(new Intent(context, MainActivity.class));
                } else {
                    Toast.makeText(context, "#" + position + " - " + imageList.get(position),
                            Toast.LENGTH_SHORT).show();
                    if(viewHolder.check.isChecked()) {
                        viewHolder.check.setChecked(false);
                    }else if(!viewHolder.check.isChecked()){
                        viewHolder.check.setChecked(true);
                    }
                }
            }

            @Override
            public void onClickData(View view, int position, boolean isLongClick) {

            }
        });

    }


    @Override
    public int getItemCount() {
        return imageList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnLongClickListener {

        public TextView imageUrl;
        public ImageView image;
        public CheckBox check;
        //TODO save the list of selected items and save(OK_btn) with count(count_textView)
        private MainActivity.ItemClickListener clickListener;

        public ViewHolder(View itemView) {
            super(itemView);
            imageUrl = (TextView) itemView.findViewById(R.id.each_cloth_txt);
            image = (ImageView) itemView.findViewById(R.id.each_cloth_img);
            check = (CheckBox) itemView.findViewById(R.id.each_cloth_check);
//            countView = (TextView) itemView.findViewById(R.id.count_row);

            itemView.setOnClickListener(this);
            itemView.setOnLongClickListener(this);
        }

        public void setClickListener(MainActivity.ItemClickListener itemClickListener) {
            this.clickListener = itemClickListener;
        }

        @Override
        public void onClick(View view) {
            clickListener.onClick(view, getPosition(), false);
        }

        @Override
        public boolean onLongClick(View view) {
            clickListener.onClick(view, getPosition(), true);
            return true;
        }
    }

}

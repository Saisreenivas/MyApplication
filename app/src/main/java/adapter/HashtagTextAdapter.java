package adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Environment;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.method.KeyListener;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.dailyuse.clothes.dhobicount.MainActivity;
import com.dailyuse.clothes.dhobicount.R;

import java.io.File;
import java.io.FilenameFilter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

import object.DateAndCount;

import static android.R.attr.width;
import static android.os.Environment.DIRECTORY_PICTURES;
import static android.view.View.GONE;
import static android.view.View.VISIBLE;

/**
 * Created by Sai sreenivas on 10/29/2017.
 */

public class HashtagTextAdapter extends RecyclerView.Adapter<HashtagTextAdapter.ViewHolder>{

    ArrayList<String> alName;
    ArrayList<Integer> alImage;
    Context context;
    DateAndCount dateAndCount;
    int positionData = 0;

    public HashtagTextAdapter(Context context, ArrayList<String> alName) {
        super();
        this.context = context;
        this.alName = alName;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.hashtag_text_format, viewGroup, false);
        ViewHolder viewHolder = new ViewHolder(v);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final ViewHolder viewHolder, int i) {
        viewHolder.tvSpecies.setText(alName.get(i));
        dateAndCount = new DateAndCount();
        viewHolder.setClickListener(new MainActivity.ItemClickListener() {
            @Override
            public void onClick(View view, int position, boolean isLongClick) {
                if (isLongClick) {
                    Toast.makeText(context, "#" + position + " - " + alName.get(position) + " (Long click)", Toast.LENGTH_SHORT).show();
                    context.startActivity(new Intent(context, MainActivity.class));
                } else {
                    Toast.makeText(context, "#" + position + " - " + alName.get(position), Toast.LENGTH_SHORT).show();
//                        viewHolder.add_data_layout.setLayoutParams(new FrameLayout.LayoutParams(FrameLayout.LayoutParams.FILL_PARENT,width));
//                    if(viewHolder.add_data_layout.getVisibility() != VISIBLE){
//                        viewHolder.addDataToImages(alName.get(position), context);

                    Intent intentX = new Intent("message-data");
                    intentX.putExtra("command",alName.get(position));
                    LocalBroadcastManager.getInstance(context).sendBroadcast(intentX);
//                  }
                }

            }

            @Override
            public void onClickData(View view, int position, boolean isLongClick) {
//                String data = alName.get(position);
//                if(data == "#shirts"){
////                    Log.v("getMet", Integer.parseInt(viewHolder.add_data_text.getText().toString()) + " ");
//                    dateAndCount.setShirts(Integer.parseInt(viewHolder.add_data_text.getText().toString()));
//                }
//                if(data == "#pants"){
//                    dateAndCount.setPants(Integer.parseInt(viewHolder.add_data_text.getText().toString()));
//                }
//                if(data == "#inners"){
//                    dateAndCount.setInners(Integer.parseInt(viewHolder.add_data_text.getText().toString()));
//                }
//                if(dateAndCount.getShirts()!=0 &&dateAndCount.getPants()!=0 && dateAndCount.getInners()!=0){
//                    Intent intent = new Intent("custom-message");
//                    intent.putExtra("#shirts",dateAndCount.getShirts());
//                    intent.putExtra("#pants",dateAndCount.getPants());
//                    intent.putExtra("#inners",dateAndCount.getInners());
//                    LocalBroadcastManager.getInstance(context).sendBroadcast(intent);
//                    notifyDataSetChanged();
//                }
//                viewHolder.add_data_layout.setVisibility(GONE);
            }
        });
    }

    @Override
    public int getItemCount() {
        return alName.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnLongClickListener {

        public TextView tvSpecies;
        public LinearLayout add_data_layout;
        public EditText add_data_text;
        public Button add_data_btn;
        public RecyclerView add_data_images_recycler;
        private MainActivity.ItemClickListener clickListener;

        public ViewHolder(View itemView) {
            super(itemView);
            tvSpecies = (TextView) itemView.findViewById(R.id.tv_species);
//            add_data_layout  = (LinearLayout) itemView.findViewById(R.id.add_specific_layout);
//            add_data_text = (EditText) itemView.findViewById(R.id.cloth_specific_count);
//            add_data_btn = (Button) itemView.findViewById(R.id.cloth_specific_btn);
//            add_data_images_recycler = (RecyclerView) itemView.findViewById(R.id.cloth_specific_recycler);
//            add_data_images_recycler.setHasFixedSize(true);
//            add_data_btn.setOnClickListener(this);

//            add_data_images.setOnClickListener(this);
            itemView.setOnClickListener(this);
            itemView.setOnLongClickListener(this);
        }

        public void setClickListener(MainActivity.ItemClickListener itemClickListener) {
            this.clickListener = itemClickListener;
        }

        @Override
        public void onClick(View view) {
            if (view.getId() != R.id.cloth_specific_btn) {
                clickListener.onClick(view, getPosition(), false);
            } else {
                clickListener.onClickData(view, getPosition(), false);
            }
        }

        @Override
        public boolean onLongClick(View view) {
            clickListener.onClick(view, getPosition(), true);
            return true;
        }
    }

}
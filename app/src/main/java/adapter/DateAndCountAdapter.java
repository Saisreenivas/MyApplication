package adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;
import android.widget.Toast;

import com.dailyuse.clothes.dhobicount.MainActivity;
import com.dailyuse.clothes.dhobicount.R;

import java.util.ArrayList;

import object.DateAndCount;

/**
 * Created by Sai sreenivas on 10/30/2017.
 */

public class DateAndCountAdapter extends RecyclerView.Adapter<DateAndCountAdapter.ViewHolder> {

    ArrayList<DateAndCount> dateAndCountsList;
    Context context;
    Animation slideUp;

    public DateAndCountAdapter(Context context, ArrayList<DateAndCount> dateAndCountsList) {
        super();
        this.context = context;
        this.dateAndCountsList = dateAndCountsList;
        animDeclare();
    }

    @Override
    public DateAndCountAdapter.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.row_layout, viewGroup, false);
        DateAndCountAdapter.ViewHolder viewHolder = new DateAndCountAdapter.ViewHolder(v);
        v.setAnimation(slideUp);
        return viewHolder;
    }

    private void animDeclare() {
        slideUp = AnimationUtils.loadAnimation(context, R.anim.slide_up);
    }

    @Override
    public void onBindViewHolder(DateAndCountAdapter.ViewHolder viewHolder, int i) {
        Log.v("viewHolder", dateAndCountsList.get(i).getDate() + " ");
        Log.v("viewHolder", dateAndCountsList.get(i).getCount() + " ");
        viewHolder.dateView.setText(dateAndCountsList.get(i).getDate());
        viewHolder.countView.setText(Integer.toString(dateAndCountsList.get(i).getCount()));

        viewHolder.setClickListener(new MainActivity.ItemClickListener() {
            @Override
            public void onClick(View view, int position, boolean isLongClick) {
                if (isLongClick) {
                    Toast.makeText(context, "#" + position + " - " + dateAndCountsList.get(position) +
                            " (Long click)", Toast.LENGTH_SHORT).show();
                    context.startActivity(new Intent(context, MainActivity.class));
                } else {
                    Toast.makeText(context, "#" + position + " - " + dateAndCountsList.get(position),
                            Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onClickData(View view, int position, boolean isLongClick) {

            }
        });
    }

    @Override
    public int getItemCount() {
        return dateAndCountsList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnLongClickListener {

        public TextView dateView, countView;
        //TODO create a edit and delete button to connect to every data
        //TODO show the list of photos after showing the hashtag-texts
        private MainActivity.ItemClickListener clickListener;

        public ViewHolder(View itemView) {
            super(itemView);
            dateView = (TextView) itemView.findViewById(R.id.date_row);
            countView = (TextView) itemView.findViewById(R.id.count_row);
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
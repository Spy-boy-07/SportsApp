package com.example.varun.finalproject;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.ArrayList;

/**
 * Created by prade on 09-06-2017.
 */

public class DerpAdapter extends RecyclerView.Adapter<DerpAdapter.DerpHolder> {

    private ArrayList<Event> listData;                               //
    private LayoutInflater inflator;
    private Context ctx;
    private Tab2Events T = new Tab2Events();
    private String emailcurrent;
    private String emailadmin;
    private admin a = new admin();

    public DerpAdapter(ArrayList<Event> listData, Context c, String s) {//
        this.emailadmin = s;
        this.inflator = LayoutInflater.from(c);
        this.listData = listData;
        this.ctx = c;
    }

    @Override
    public DerpHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflator.inflate(R.layout.list_item, parent, false);

        return new DerpHolder(view, ctx, listData);
    }

    @Override
    public void onBindViewHolder(DerpHolder holder, int position) {

        Event event = listData.get(position);
        holder.title.setText(event.getTitle());
        holder.Date.setText(event.getDate());
        holder.Day.setText(event.getDay());
        holder.Location.setText(event.getLocation());
        holder.Time.setText(event.getTime());

       // holder.icon.setImageResource(item.getImageResId());
    }

    @Override
    public int getItemCount() {
        return listData.size();
    }

    class DerpHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private TextView title;
        private ImageView icon;
        private TextView Date;
        private TextView Day;
        private TextView Location;
        private TextView Time;
        private View container;
        ArrayList<Event> Events = new ArrayList<>();
        Context ctx;

        public DerpHolder (View itemView, Context ctx, ArrayList<Event> Events) {
            super(itemView);
            this.Events = Events;
            this.ctx = ctx;
            itemView.setOnClickListener(this);
            Date = (TextView)itemView.findViewById(R.id.Date);
            Day = (TextView)itemView.findViewById(R.id.Day);
            Location = (TextView)itemView.findViewById(R.id.Location);
            Time = (TextView)itemView.findViewById(R.id.Time);
            title = (TextView) itemView.findViewById(R.id.lbl_item_text);
            icon = (ImageView)itemView.findViewById(R.id.im_item_icon);
            container = itemView.findViewById(R.id.cont_item_root);

        }

        @Override
        public void onClick(View view) {


            FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
            if (user != null) {
                // Name, email address, and profile photo Url
                emailcurrent = user.getEmail();
            }

            boolean isadmin = emailcurrent.equals(emailadmin);

            int position = getAdapterPosition();
            Event event = this.Events.get(position);
            Intent intent = new Intent (this.ctx, SingleEventActivity.class);
            Intent intentadmin = new Intent (this.ctx, SingleEventActivityAdmin.class);


            intent.putExtra("Date", event.getDate());
            intent.putExtra("Day", event.getDay());
            intent.putExtra("Time", event.getTime());
            intent.putExtra("Location", event.getLocation());
            intent.putExtra("Title", event.getTitle());
            intent.putExtra("Event No", event.getEventNo());

            intentadmin.putExtra("Date", event.getDate());
            intentadmin.putExtra("Day", event.getDay());
            intentadmin.putExtra("Time", event.getTime());
            intentadmin.putExtra("Location", event.getLocation());
            intentadmin.putExtra("Title", event.getTitle());
            intentadmin.putExtra("EventNo", event.getEventNo());

            System.out.println("******************"+event.getEventNo()+"******************");

            if (isadmin) this.ctx.startActivity(intentadmin);
            else this.ctx.startActivity(intent);


        }
    }

}

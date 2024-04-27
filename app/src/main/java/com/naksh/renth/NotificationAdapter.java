package com.naksh.renth;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import java.util.Collections;
import java.util.List;

public class NotificationAdapter extends RecyclerView.Adapter<NotificationAdapter.NotificationViewHolder> {

    private Context context;
    private List<String> notificationList;

    public NotificationAdapter(Context context, List<String> notificationList) {
        this.context = context;
        this.notificationList = notificationList;
    }

    @NonNull
    @Override
    public NotificationViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.notification_item, parent, false);
        return new NotificationViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull NotificationViewHolder holder, int position) {
        String notification = notificationList.get(position);
        holder.bind(notification);
        holder.cardView.startAnimation(AnimationUtils.loadAnimation(holder.itemView.getContext(),R.anim.anim_one));

    }

    @Override
    public int getItemCount() {
        return notificationList.size();
    }

    public class NotificationViewHolder extends RecyclerView.ViewHolder {
        CardView cardView;
        private TextView textViewNotification;

        public NotificationViewHolder(@NonNull View itemView) {
            super(itemView);
            textViewNotification = itemView.findViewById(R.id.textViewNotification);
            cardView = itemView.findViewById(R.id.cardview3);

        }

        public void bind(String notification) {
            textViewNotification.setText(notification);
        }
    }
    public void reverseList() {
        Collections.reverse(notificationList);
        notifyDataSetChanged();
    }
}

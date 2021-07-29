package com.example.contactsapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.List;

public class CustomAdapter extends RecyclerView.Adapter<CustomAdapter.ViewHolder> {

    private List<Contact> localDataSet;
    private final ContactClickListener contactClickListener;
    private Context context;

    /**
     * Initialize the dataset of the Adapter.
     *
     * @param dataSet              String[] containing the data to populate views to be used
     *                             by RecyclerView.
     * @param contactClickListener
     */
    public CustomAdapter(List<Contact> dataSet, ContactClickListener contactClickListener, Context context) {
        localDataSet = dataSet;
        this.contactClickListener = contactClickListener;
        this.context = context;
    }


    // Create new views (invoked by the layout manager)
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        // Create a new view, which defines the UI of the list item
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.contant_item, viewGroup, false);

        return new ViewHolder(view);
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(ViewHolder viewHolder, final int position) {

        // Get element from your dataset at this position and replace the
        // contents of the view with that element
        Contact contact = localDataSet.get(position);

        viewHolder.textView.setText(contact.getName());
        String img = contact.getImg();
        Glide.with(this.context).load(img).error(R.drawable.person).placeholder(R.drawable.person).into(viewHolder.imageView);

        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //option 1: open activity here
                //option 2 move the event up in the chain
                contactClickListener.onContactClicked(contact);
            }
        });

    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return localDataSet.size();
    }


    /**
     * Provide a reference to the type of views that you are using
     * (custom ViewHolder).
     */
    public class ViewHolder extends RecyclerView.ViewHolder {
        private final TextView textView;
        private final ImageView imageView;

        public ViewHolder(View view) {
            super(view);
            textView = (TextView) view.findViewById(R.id.contact_name);
            imageView = (ImageView) view.findViewById(R.id.contact_img);
        }
    }
}

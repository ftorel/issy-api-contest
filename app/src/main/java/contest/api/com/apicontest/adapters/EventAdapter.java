package contest.api.com.apicontest.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.koushikdutta.ion.Ion;

import java.util.ArrayList;
import java.util.List;

import contest.api.com.apicontest.R;
import contest.api.com.apicontest.models.events.Event;

/**
 * Created by floriantorel on 04/03/16.
 */
public class EventAdapter extends RecyclerView.Adapter<EventAdapter.ViewHolder> {
    private List<Event> eventList;

    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder
    public static class ViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        public TextView mTitleTextView;
        public TextView mDateTextView;
        public ImageView mImageView;

        public ViewHolder(View v) {
            super(v);

            mTitleTextView =  (TextView) v.findViewById(R.id.title_textview);
            mDateTextView = (TextView) v.findViewById(R.id.date_textview);
            mImageView = (ImageView) v.findViewById(R.id.picture_imageview);

        }

    }

    // Provide a suitable constructor (depends on the kind of dataset)
    public EventAdapter(List<Event> eventList) {
        this.eventList = eventList;
    }

    // Create new views (invoked by the layout manager)
    @Override
    public EventAdapter.ViewHolder onCreateViewHolder(ViewGroup parent,
                                                   int viewType) {
        // create a new view
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_event, parent, false);
        // set the view's size, margins, paddings and layout parameters

        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    public void update( List<Event> events ){
        this.eventList = new ArrayList<>();
        this.eventList = events;
        this.notifyDataSetChanged();
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        // - get element from your dataset at this position
        // - replace the contents of the view with that element

        Event event = eventList.get(position);

        holder.mTitleTextView.setText(event.title);

        if ( ! event.getDate().equals("") ){
            holder.mDateTextView.setText(event.getDate());
        }

        Ion.with(holder.mImageView)
                .placeholder(R.drawable.background)
                .error(R.drawable.background)
                .load( event.getUrlImage() );

    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return eventList.size();
    }
}
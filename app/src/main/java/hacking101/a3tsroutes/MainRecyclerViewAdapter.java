package hacking101.a3tsroutes;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;

public class MainRecyclerViewAdapter extends RecyclerView.Adapter<MainRecyclerViewAdapter.MainViewHolder> {
    private ArrayList<Route> mainDataset;
    private Activity activity;

    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder
    public static class MainViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        public LinearLayout mainTextView;
        private Activity activity;

        public MainViewHolder(LinearLayout v, Activity mainActivity) {
            super(v);
            mainTextView = v;
            activity = mainActivity;
            v.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent mapIntent = new Intent(activity, MapActivity.class);
                    activity.startActivity(mapIntent);
//                    Log.d("OnClickTag", "You Clicked : " +
//                            ((TextView) mainTextView.findViewById(R.id.Recycler_View_Next_Stop)).getText());
                }
            });
        }


    }

    // Provide a suitable constructor (depends on the kind of dataset)
    public MainRecyclerViewAdapter(ArrayList<Route> myDataset, Activity mainActivity) {
        mainDataset = myDataset;
        activity = mainActivity;
    }

    // Create new views (invoked by the layout manager)
    @Override
    public MainRecyclerViewAdapter.MainViewHolder onCreateViewHolder(ViewGroup parent,
                                                     int viewType) {
        // create a new view
        LinearLayout v = (LinearLayout) LayoutInflater.from(parent.getContext())
                .inflate(R.layout.recycler_view_item, parent, false);
//        v.setText("Hello");

        MainViewHolder vh = new MainViewHolder(v, activity);
        return vh;
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(MainViewHolder holder, int position) {
        // - get element from your dataset at this position
        // - replace the contents of the view with that element

        ((TextView) holder.mainTextView.findViewById(R.id.Recycler_View_Route_Number)).setText(mainDataset.get(position).getRouteID());
//        ((TextView) holder.mainTextView.findViewById(R.id.Recycler_View_Bottom_Text)).setText(mainDataset[position+1]);
//                setText(mainDataset[position]);

    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return mainDataset.size();
    }

    public void deleteItem(int position){
        mainDataset.remove(position);
        this.notifyItemRemoved(position);    //notifies the RecyclerView Adapter that data in adapter has been removed at a particular position.
        this.notifyItemRangeChanged(position, this.getItemCount());
    }

    public void addItem(Route newItem){
        mainDataset.add(0, newItem);
        this.notifyDataSetChanged();
    }

    public void addItems(ArrayList<Route> newItems){
        mainDataset.addAll(0, newItems);
        this.notifyDataSetChanged();
    }
}

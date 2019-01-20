package hacking101.a3tsroutes;

import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper.Callback;

import static android.support.v7.widget.helper.ItemTouchHelper.*;
import static android.support.v7.widget.helper.ItemTouchHelper.LEFT;
import static android.support.v7.widget.helper.ItemTouchHelper.RIGHT;

//import android.support.v7.widget.helper.ItemTouchHelper;

public class SwipeController extends Callback {
    private MainRecyclerViewAdapter myAdapter;

    public SwipeController(MainRecyclerViewAdapter mainRecyclerViewAdapter){
        super();
        myAdapter = mainRecyclerViewAdapter;
    }

    @Override
    public int getMovementFlags(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder) {
        myAdapter.updateItems();
        return makeMovementFlags(0, LEFT | RIGHT);
    }

    @Override
    public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
        myAdapter.updateItems();
        return false;
    }

    @Override
    public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
        Integer position = viewHolder.getAdapterPosition();
//        viewHolder.deleteItem(position);
        //Log.d("SwipeController", position.toString());
        myAdapter.deleteItem(position);

    }

//    @Override
//    public int convertToAbsoluteDirection(int flags, int layoutDirection) {
//        if (swipeBack) {
//            swipeBack = false;
//            return 0;
//        }
//        return super.convertToAbsoluteDirection(flags, layoutDirection);
//    }
}

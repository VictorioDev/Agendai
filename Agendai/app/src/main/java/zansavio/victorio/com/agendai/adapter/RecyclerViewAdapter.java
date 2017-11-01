package zansavio.victorio.com.agendai.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

import zansavio.victorio.com.agendai.R;
import zansavio.victorio.com.agendai.domain.Test;

/**
 * Created by Victorio Zansavio on 24/09/2017.
 */

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.RecyclerViewHolder> {
    private Context mContext;
    private ArrayList<Test> testArrayList;
    private ArrayList<String> keys;
    private final DatabaseReference mDatabaseReference;

    public void setKeys(ArrayList<String> keys){
        this.keys = keys;
    }

    public RecyclerViewAdapter(Context mContext, ArrayList<Test> testArrayList) {
        this.mContext = mContext;
        this.testArrayList = testArrayList;
        this.mDatabaseReference = FirebaseDatabase.getInstance().getReference().child("tests");

    }

    @Override
    public RecyclerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(mContext).inflate(R.layout.item_layout, parent, false);
        RecyclerViewHolder viewHolder = new RecyclerViewHolder(v);
        Log.i("test", "Passou aqui!");
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerViewHolder holder, int position) {
        holder.setData(testArrayList.get(position));
    }

    @Override
    public int getItemCount() {
        return testArrayList.size();
    }

    public class RecyclerViewHolder extends RecyclerView.ViewHolder{
        private TextView title;
        private TextView date;
        private ImageView imgExcluir;
        public RecyclerViewHolder(View itemView) {
            super(itemView);
            title = (TextView) itemView.findViewById(R.id.titleTextView);
            date = (TextView) itemView.findViewById(R.id.dateTextView);
            imgExcluir = (ImageView) itemView.findViewById(R.id.imgExcluir);
            imgExcluir.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mDatabaseReference.child(keys.get(getAdapterPosition())).removeValue();
                    keys.remove(getAdapterPosition());
                }
            });
        }

        public void setData(Test test){
            title.setText(test.getTitle());
            date.setText(test.getDate());
        }
    }

}


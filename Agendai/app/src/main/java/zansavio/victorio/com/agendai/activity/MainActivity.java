package zansavio.victorio.com.agendai.activity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import zansavio.victorio.com.agendai.R;
import zansavio.victorio.com.agendai.adapter.RecyclerViewAdapter;
import zansavio.victorio.com.agendai.domain.Test;

public class MainActivity extends AppCompatActivity{
    private RecyclerView mRecyclerView;
    private RecyclerViewAdapter mRecyclerViewAdapter;
    private ArrayList<String> keys;
    private DatabaseReference mDatabaseReference;
    private ArrayList<Test> mListTests;
    private ProgressBar mpProgressBar;
    private View mAppBar;
    private TextView mSubtitleTextView;

    private FloatingActionButton mFloatingActionButton;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initViews();
        setupDatabaseListener();
    }

    private void initViews(){
        mFloatingActionButton = (FloatingActionButton) findViewById(R.id.fab);
        mFloatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getBaseContext(), CadastroActivity.class);
                startActivity(i);
            }
        });
        mAppBar = findViewById(R.id.appBar);
        mSubtitleTextView = (TextView) mAppBar.findViewById(R.id.mainSubTitletextView);
        mRecyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        mListTests = new ArrayList<>();
        keys = new ArrayList<>();
        mRecyclerViewAdapter = new RecyclerViewAdapter(this, mListTests);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(layoutManager);
        DividerItemDecoration itemDecorator = new DividerItemDecoration(this, DividerItemDecoration.VERTICAL);
        itemDecorator.setDrawable(ContextCompat.getDrawable(this, R.drawable.divider));
        mRecyclerView.addItemDecoration(itemDecorator);
        mRecyclerView.setAdapter(mRecyclerViewAdapter);
        mpProgressBar = (ProgressBar) findViewById(R.id.progressBar);



    }

    private void setupDatabaseListener(){
        mDatabaseReference = FirebaseDatabase.getInstance().getReference().child("tests");
        ValueEventListener eventListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                clearLists();
                for (DataSnapshot ds: dataSnapshot.getChildren()) {
                    Log.i("test", ds.getKey());
                    keys.add(ds.getKey());
                    Test test = new Test(ds.child("title").getValue().toString(), ds.child("date").getValue().toString());
                    mListTests.add(test);
                }
                updateInterface();
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        };
        mDatabaseReference.addValueEventListener(eventListener);
    }


    private void clearLists(){
        mListTests.clear();
        keys.clear();
    }

    private void updateInterface(){
        if(mListTests.size() == 0){
            mSubtitleTextView.setText("Você não possui avaliações, aproveite!");
        }else if(mListTests.size() == 1){
            mSubtitleTextView.setText("Você possui um total de " + mListTests.size() + " avaliação");
        }else {
            mSubtitleTextView.setText("Você possui um total de " + mListTests.size() + " avaliações");
        }

        mRecyclerViewAdapter.setKeys(keys);
        mRecyclerViewAdapter.notifyDataSetChanged();
        mpProgressBar.setVisibility(View.GONE);
        mAppBar.setVisibility(View.VISIBLE);
    }

}

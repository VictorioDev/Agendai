package zansavio.victorio.com.agendai.activity;

import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.app.DatePickerDialog;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;
import java.util.TimeZone;

import zansavio.victorio.com.agendai.R;
import zansavio.victorio.com.agendai.domain.Test;

public class CadastroActivity extends AppCompatActivity implements DatePickerDialog.OnDateSetListener, View.OnClickListener{
    private TextView mDataTextView;
    private ImageView imgData;
    private DatePickerDialog pickerDialog;
    private DatabaseReference mDatabaseReference;
    private Button btnSalvar;
    private TextView dataTextView;
    private EditText titleEditText;

    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        String date = view.getDayOfMonth() + "/" + (view.getMonth() + 1) + "/" + view.getYear();
        mDataTextView.setText(date);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro);
        initViews();
    }

    private void initViews(){
        GregorianCalendar calendar = new GregorianCalendar(TimeZone.getDefault());
        Log.i("test", "Data: " + calendar.get(GregorianCalendar.DAY_OF_MONTH)
                + "/" + calendar.get(GregorianCalendar.MONTH) + "/"
                +calendar.get(GregorianCalendar.YEAR));
        pickerDialog = new DatePickerDialog(this, this, calendar.get(GregorianCalendar.YEAR),
                calendar.get(GregorianCalendar.MONTH),
                calendar.get(GregorianCalendar.DAY_OF_MONTH));
        imgData = (ImageView) findViewById(R.id.imgData);
        mDataTextView = (TextView) findViewById(R.id.dataTextView);
        mDataTextView.setOnClickListener(this);
        imgData.setOnClickListener(this);
        mDatabaseReference = FirebaseDatabase.getInstance().getReference().child("tests");
        dataTextView = (TextView) findViewById(R.id.dataTextView);
        btnSalvar = (Button) findViewById(R.id.btnSalvar);
        titleEditText = (EditText) findViewById(R.id.titleEditText);

        btnSalvar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(dataTextView.getText().toString().isEmpty()){
                    ObjectAnimator animation = ObjectAnimator.ofFloat(imgData, "translationX", -20f);
                    animation.setDuration(50);
                    animation.setRepeatCount(5);
                    animation.setRepeatMode(ValueAnimator.REVERSE);
                    animation.start();
                }else if(titleEditText.getText().toString().isEmpty()){
                    ObjectAnimator animation = ObjectAnimator.ofFloat(titleEditText, "translationX", -20f);
                    animation.setDuration(50);
                    animation.setRepeatCount(5);
                    animation.setRepeatMode(ValueAnimator.REVERSE);
                    animation.start();
                }else {
                    Test t =  new Test();
                    t.setTitle(titleEditText.getText().toString());
                    t.setDate(dataTextView.getText().toString());
                    mDatabaseReference.push().setValue(t);
                    finish();
                }


            }
        });

    }

    private void showDialog() {
        pickerDialog.show();
    }


    @Override
    public void onClick(View v) {
        if(v.getId() == mDataTextView.getId() || v.getId() == imgData.getId()){
            showDialog();
        }
    }
}

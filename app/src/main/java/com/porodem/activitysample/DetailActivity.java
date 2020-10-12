package com.porodem.activitysample;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.porodem.activitysample.database.IventUtils;

import java.util.Calendar;
import java.util.Date;
import java.util.UUID;

public class DetailActivity extends AppCompatActivity {

    private static final String EXTRA_EVENT_ID = "com.porodem.activitysample.event_id";

    ImageView imageViewD;
    TextView ttl, tvDura;
    Button btnEditDate;

    String data1, data2;
    int image;
    Ivent ivent;
    Date d1;

    public static Intent newIntent(Context packageContext, UUID eventId) {
        Intent intent = new Intent(packageContext, DetailActivity.class);
        intent.putExtra(EXTRA_EVENT_ID, eventId);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        imageViewD = findViewById(R.id.imgBig);
        ttl = findViewById(R.id.txtTitleD);
        tvDura = findViewById(R.id.tvPeriod);
        btnEditDate = findViewById(R.id.btnEditStartDate);



        UUID eventId = (UUID) getIntent().getSerializableExtra(EXTRA_EVENT_ID);

        ivent = IventLab.get(this).getIvent(eventId);
        d1 = ivent.getDate();

        ttl.setText(ivent.getTitle());
        tvDura.setText(IventUtils.getDateString(d1) + " - " + IventUtils.getDateString(new Date()));
        /*getData();
        setData();*/

        //DatePickerDialog dialog =  new DatePickerDialog(getApplicationContext(), this, 2020, 10, 12);

        final Calendar newCalendar = Calendar.getInstance();
        final DatePickerDialog  StartTime = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                Calendar newDate = Calendar.getInstance();
                newDate.set(year, monthOfYear, dayOfMonth);
                ivent.setmDate(newDate.getTime());
                IventLab.get(getApplicationContext()).updateIvent(ivent);
                //activitydate.setText(dateFormatter.format(newDate.getTime()));
            }

        }, newCalendar.get(Calendar.YEAR), newCalendar.get(Calendar.MONTH), newCalendar.get(Calendar.DAY_OF_MONTH));

        btnEditDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                StartTime.show();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.activity_detail, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId()) {
            case R.id.del_event:
                IventLab iventLab = IventLab.get(this);
                iventLab.deleteIvent(ivent);
                finish();
                //TODO refresh list after deletion
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
        //confirmEventDialog();
        //return true;
    }



    /*private void confirmEventDelete() {
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("delete this track?").setTitle("Attention");
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        })

    }*/
}
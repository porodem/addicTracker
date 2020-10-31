package com.porodem.activitysample;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.porodem.activitysample.database.IventUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

public class DetailActivity extends AppCompatActivity {

    private static final String EXTRA_EVENT_ID = "com.porodem.activitysample.event_id";

    ImageView imageViewD;
    TextView ttl, tvDura, tvPrevDuration, mTvPrevDura, tvTopDura, tvFailsCount;
    Button btnEditDate;
    Button btnFail;

    String data1, data2, dayDeclension;
    int image, failsCount;
    long duration, diff, prevDura, topDura;
    Ivent ivent;
    Date d1, topDate;

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
        tvPrevDuration = findViewById(R.id.tvPrevDura);
        tvTopDura = findViewById(R.id.tvTopResult);
        tvFailsCount = findViewById(R.id.tvFailsCount);


        btnFail = findViewById(R.id.btnFailD);

        UUID eventId = (UUID) getIntent().getSerializableExtra(EXTRA_EVENT_ID);

        ivent = IventLab.get(this).getOneIvent(eventId);
        d1 = ivent.getDate();
        prevDura = ivent.getPrevDura();

        diff = (new Date()).getTime() - d1.getTime();
        duration = TimeUnit.DAYS.convert(diff, TimeUnit.MILLISECONDS);
        topDura = ivent.getTopDuration();
        topDate = ivent.getTopDate();
        failsCount = ivent.getFailsCount();


        dayDeclension = IventUtils.getDeclension((int)duration);

        String title = ivent.getTitle();
        if(title.matches("alco.*|алк.*|бух.*")) {
            imageViewD.setImageResource(R.drawable.alco);
        }
        if(title.matches("candy.*|shuga.*|сладк.*|конфет.*|сахар.*")) {
            imageViewD.setImageResource(R.drawable.candy);
        }
        if(title.matches("fastfood.*|junkfood.*|фастфуд.*|жир.*|фастфуд.*|жарен.*|вкусн.*")) {
            imageViewD.setImageResource(R.drawable.food);
        }
        if(title.matches(".*game.*|.*игры.*|dota.*")) {
            imageViewD.setImageResource(R.drawable.games);
        }
        if(title.matches("smoke.*|курение.*|сигарет.*|табак.*|никотин")) {
            imageViewD.setImageResource(R.drawable.smoke);
        }
        ttl.setText(ivent.getTitle());
        tvDura.setText(IventUtils.getDateString(d1) + " - " + IventUtils.getDateString(new Date())
                + "\n" + duration + " " + dayDeclension) ;
        tvPrevDuration.setText("прошлый раз: " + prevDura + " " + IventUtils.getDeclension((int)prevDura));

        tvTopDura.setText(topDura + " " + IventUtils.getDeclension((int)topDura) + " (" +IventUtils.getDateString(topDate) + ")");


        tvFailsCount.setText((failsCount>0?("провалов: " + failsCount ):""));
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

        btnFail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialogFail();
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


    private void dialogFail() {
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        final LayoutInflater inflater = getLayoutInflater();
        final View dialogView = inflater.inflate(R.layout.dialog_event, null);
        final EditText etDate = (EditText) dialogView.findViewById(R.id.et_event_date);
        final Date date = new Date();
        final SimpleDateFormat sdf = new SimpleDateFormat("yyyy.MM.dd");
        etDate.setText(sdf.format(date));

        builder.setView(dialogView).
                setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        EditText etTitle = (EditText) dialogView.findViewById(R.id.et_newevent_name);

                        //String dialogNewEvent = etTitle.getText().toString();
                        UUID trackID = ivent.getId();
                        Date failDate = date;

                        try {
                            failDate = sdf.parse(etDate.getText().toString());
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }
                        IventLab.get(getApplicationContext()).failTrack(trackID, failDate);

                        Ivent newIvent = new Ivent();
                        newIvent.setPrevDura(duration);
                        newIvent.setTitle(ivent.getTitle());
                        newIvent.setFailsCount(ivent.getFailsCount() + 1);
                        //set top duratiion
                        if(duration > topDura) {
                            //update top duration
                            newIvent.setTopDuration(duration);
                            newIvent.setTopDate(failDate);
                        } else {
                            newIvent.setTopDuration(ivent.getTopDuration());
                            newIvent.setTopDate(ivent.getTopDate());
                        }
                        try {
                            newIvent.setmDate(sdf.parse(etDate.getText().toString()));
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }

                        IventLab.get(getApplicationContext()).addIvent(newIvent);
                        IventLab.get(getApplicationContext()).deleteIvent(ivent);
                        //updateUI();

                        Intent intent = DetailActivity.newIntent(getApplicationContext(), newIvent.getId());
            /*intent.putExtra("data2", data2[position]);
            intent.putExtra("img", images[position]);*/
                        //itemPosition = getAdapterPosition();
                        finish();
                        startActivity(intent);
                    }
                });
        builder.show();
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
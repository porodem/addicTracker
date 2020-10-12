package com.porodem.activitysample;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class MainActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    IventAdapter myAdapter;

    int itemPosition;

    String s1[], s2[];
    int images[] = {R.drawable.apple, R.drawable.banana, R.drawable.b, R.drawable.c, R.drawable.cucumber, R.drawable.donat, R.drawable.finik,
                    R.drawable.mango, R.drawable.d, R.drawable.ginger, R.drawable.juice};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        updateUI();

        /*s1 = getResources().getStringArray(R.array.meals);
        s2 = getResources().getStringArray(R.array.desctiptions);*/

        /*myAdapter = new MyAdapter(this, s1, s2, images);
        recyclerView.setAdapter(myAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));*/
    }

    private void updateUI() {
        IventLab iventLab = IventLab.get(this);
        List<Ivent> ivents = iventLab.getIvents();
        if(myAdapter == null) {
            myAdapter = new IventAdapter(ivents);
            recyclerView.setAdapter(myAdapter);
        } else {
            myAdapter.notifyDataSetChanged();
        }

    }

    private class IventHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private Ivent mIvent;

        private TextView mTitleTextView;
        private TextView mDateTextView;
        private TextView mTopDurationTextView;
        private TextView mDuration;

        public IventHolder(LayoutInflater inflater, ViewGroup parent) {
            super(inflater.inflate(R.layout.my_row, parent, false));
            itemView.setOnClickListener(this);

            mTitleTextView = itemView.findViewById(R.id.txtMeal);
            mDateTextView = itemView.findViewById(R.id.txtLastEvent);
            mDuration = itemView.findViewById(R.id.tvDurationRow);

        }

        public IventHolder(@NonNull View v) {
            super(v);
            itemView.setOnClickListener(this);

            mTitleTextView = itemView.findViewById(R.id.txtTitleD);

        }

        public void bind(Ivent ivent) {
            mIvent = ivent;
            Date bdate = ivent.getDate();
            mTitleTextView.setText(mIvent.getTitle());
            SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy");
            mDateTextView.setText(sdf.format(bdate));

            Date date2 = new Date();
            long diff = date2.getTime() - bdate.getTime();
            long duration = TimeUnit.DAYS.convert(diff, TimeUnit.MILLISECONDS);
            mDuration.setText(duration + " days");

        }

        @Override
        public void onClick(View v) {
            Intent intent = DetailActivity.newIntent(getApplicationContext(), mIvent.getId());
            /*intent.putExtra("data2", data2[position]);
            intent.putExtra("img", images[position]);*/
            itemPosition = getAdapterPosition();
            startActivity(intent);

        }
    }

    private class IventAdapter extends RecyclerView.Adapter<IventHolder> {

        private List<Ivent> mIvents;

        public IventAdapter(List<Ivent> ivents) {
            mIvents = ivents;
        }

        @NonNull
        @Override
        public IventHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            LayoutInflater layoutInflater = LayoutInflater.from(getApplicationContext()); // TODO check !
            return new IventHolder(layoutInflater, parent);
        }

        @Override
        public void onBindViewHolder(@NonNull IventHolder holder, int position) {
            Ivent ivent = mIvents.get(position);
            holder.bind(ivent);
        }

        @Override
        public int getItemCount() {
            return mIvents.size();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.activity_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        /*switch (item.getItemId()) {
            case R.id.new_ivent:
                Ivent ivent = new Ivent();
                IventLab.get(this).addIvent(ivent);
                Intent intent = IventPagerActivity
        }*/
        newEventDialog();
        return true;
    }

    String dialogNewEvent = "";

    private void newEventDialog() {
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

                dialogNewEvent = etTitle.getText().toString();

                Ivent ivent = new Ivent();
                ivent.setTitle(dialogNewEvent);
                try {
                    ivent.setmDate(sdf.parse(etDate.getText().toString()));
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                IventLab.get(getApplicationContext()).addIvent(ivent);
                updateUI();
                finish();
                startActivity(getIntent());
            }
        });
        builder.show();
    }
}
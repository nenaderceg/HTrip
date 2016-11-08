package com.example.htrip3;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.CalendarView;
import android.widget.TextView;
import android.widget.Toast;

import com.microsoft.windowsazure.mobileservices.MobileServiceClient;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;


public class MainActivity extends AppCompatActivity {

    String[] myItems = {"Event 1", "Event 2", "Event 3", "Event 4", "Event 5", "Event 6"
            , "Event 7", "Event 8", "Event 9", "Event 10", "Event 11", "Event 12", "Event 13", "Event 14", "Event 15"};

    String[] myItems2 = {"Proba 1", "Proba 2", "Proba 3"};
    String[] myItems3 = {""};

    private MobileServiceClient mClient;
    private ListView eventList;
    private CalendarView calendar;
    TextView dateDisplay;
    TextView noEvents;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar myToolbar = (Toolbar) findViewById(R.id.main_toolbar);
        setSupportActionBar(myToolbar);
        myToolbar.setLogo(R.drawable.ic_logo);

        eventList = (ListView) findViewById(R.id.lwList);

        noEvents = (TextView) findViewById(R.id.tvNoEvents);
        //noEvents.setText("NO EVENTS");


        //eventList.setAdapter(new ArrayAdapter<String>(this, R.layout.da_item, myItems));


        calendar = (CalendarView) findViewById(R.id.calendarView4);
        //calendar.setD
        dateDisplay = (TextView) findViewById(R.id.tvDate);


        Calendar c = Calendar.getInstance();
        SimpleDateFormat ss = new SimpleDateFormat("MM / dd / yyyy");
        Date date = new Date();
        int day = date.getDay();
        String currentdate = "Date: " + ss.format(date);
        dateDisplay.setText(currentdate);

        setEvent(eventList, noEvents, day);

        Toast.makeText(getApplicationContext(), "Selected Date:\n" + "Day = " + day , Toast.LENGTH_SHORT).show();

        calendar.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(CalendarView calendarView, int i, int i1, int i2) {
                dateDisplay.setText("Date: " + i2 + " / " + (++i1) + " / " + i);
                //calendar.setDateTextAppearance();

                setEvent(eventList, noEvents, i2);
                //Toast.makeText(getApplicationContext(), "Selected Date:\n" + "Day = " + i2 + "\n" + "Month = " + i1 + "\n" + "Year = " + i, Toast.LENGTH_LONG).show();

            }
        });



        eventList.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Intent intent = new Intent(MainActivity.this, JoinInActivity.class);
                startActivity(intent);

            }
        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.about) {
            return true;
        }
        else if (id == R.id.action_add) {
            Intent intent = new Intent(MainActivity.this, CreateEventActivity.class);
            startActivity(intent);
            return true;
        }
        else if (id == R.id.action_log_out) {
            Intent intent = new Intent(MainActivity.this, LoginActivity.class);
            startActivity(intent);
            finish();
            return true;
        }

        else if (id == R.id.action_my_events) {
            Intent intent = new Intent(MainActivity.this, MyEventsActivity.class);
            startActivity(intent);
            return true;
        }

        else

        return super.onOptionsItemSelected(item);
    }
    private void setEvent(ListView eventList, TextView error, int i){
        if(i == 3){

            eventList.setAdapter(new ArrayAdapter<String>(this, R.layout.da_item, myItems));
            eventList.setVisibility(View.VISIBLE);
            error.setVisibility(View.GONE);
        }
        else if(i==7){
            eventList.setAdapter(new ArrayAdapter<String>(this, R.layout.da_item, myItems2));
            eventList.setVisibility(View.VISIBLE);
            error.setVisibility(View.GONE);
        }
        else {

            eventList.setVisibility(View.GONE);
            error.setVisibility(View.VISIBLE);
            error.setText("NO EVENTS");
        }
    }
}

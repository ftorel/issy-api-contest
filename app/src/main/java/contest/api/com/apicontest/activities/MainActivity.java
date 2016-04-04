package contest.api.com.apicontest.activities;

import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import java.util.List;

import contest.api.com.apicontest.Constant;
import contest.api.com.apicontest.R;
import contest.api.com.apicontest.adapters.EventAdapter;
import contest.api.com.apicontest.listener.RecyclerItemClickListener;
import contest.api.com.apicontest.logic.EventManager;
import contest.api.com.apicontest.models.events.Event;

public class MainActivity extends AppCompatActivity {


    private BroadcastReceiver eventBroadcastReceiver;

    private RecyclerView eventRecyclerView;
    private EventAdapter eventAdapter;
    private LinearLayoutManager mLayoutManager;

    private List<Event> events;

    private boolean loading = true;

    private ProgressDialog progress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initRecyclerView();

        initBroadCast();

        createDialog();

    }

    private void createDialog(){
        progress = new ProgressDialog(this);
        progress.setMessage("Télechargement ");
        progress.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progress.setIndeterminate(true);
        progress.show();
    }

    private void initRecyclerView(){

        eventRecyclerView = (RecyclerView) findViewById(R.id.event_recycler_view);

        eventRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(this);
        eventRecyclerView.setLayoutManager(mLayoutManager);

        eventRecyclerView.addOnItemTouchListener(new RecyclerItemClickListener(getApplicationContext(), new RecyclerItemClickListener.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {

                Intent intent = new Intent(MainActivity.this, DetailActivity.class);
                intent.putExtra(Constant.INTENT.POSITION, position);
                startActivity(intent);

            }
        }));

        eventRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);

                int pastVisiblesItems, visibleItemCount, totalItemCount;

                if(dy > 0) //check for scroll down
                {
                    visibleItemCount = mLayoutManager.getChildCount();
                    totalItemCount = mLayoutManager.getItemCount();
                    pastVisiblesItems = mLayoutManager.findFirstVisibleItemPosition();

                    if ( ! loading)
                    {
                        if ( (visibleItemCount + pastVisiblesItems) >= totalItemCount)
                        {
                            loading = true;

                            EventManager.getInstance().updateEventsData();

                        }
                    }
                }
            }
        });

    }


    private void initBroadCast(){

        eventBroadcastReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {

                events = EventManager.getInstance().getAllEvent();

                if ( eventAdapter == null ){
                    eventAdapter = new EventAdapter( events );
                    eventRecyclerView.setAdapter(eventAdapter);
                } else {
                    eventAdapter.update(events);
                }

                if ( progress != null ){
                    progress.dismiss();
                    progress = null;
                }

                loading = false;

            }
        };

    }

    private void registerBroadcastReceiver(){
        LocalBroadcastManager.getInstance(this).registerReceiver( eventBroadcastReceiver, new IntentFilter( Constant.FILTER.EVENTS ));
    }

    private void unregisterBroadcastReceiver(){
        LocalBroadcastManager.getInstance(this).unregisterReceiver(eventBroadcastReceiver);
    }

    @Override
    protected void onPause() {
        super.onPause();
        unregisterBroadcastReceiver();
    }

    @Override
    protected void onResume() {
        super.onResume();

        registerBroadcastReceiver();

        EventManager.getInstance().updateEventsData();

    }
}

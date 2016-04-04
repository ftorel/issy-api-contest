package contest.api.com.apicontest.activities;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.koushikdutta.ion.Ion;

import java.util.ArrayList;
import java.util.List;

import contest.api.com.apicontest.Constant;
import contest.api.com.apicontest.R;
import contest.api.com.apicontest.logic.BeParkManager;
import contest.api.com.apicontest.logic.EventManager;
import contest.api.com.apicontest.logic.VelibManager;
import contest.api.com.apicontest.models.events.BePark;
import contest.api.com.apicontest.models.events.Event;
import contest.api.com.apicontest.models.events.Velib;

public class DetailActivity extends ActionBarActivity {

    private GoogleMap mMap; // Might be null if Google Play services APK is not available.

    private Event event;

    private ImageView topPicture;

    private boolean isMaxLineActived = false;

    private List<Velib> velibs = new ArrayList<>();
    private List<BePark> beParkList = new ArrayList<>();
    private List<Marker> markers = new ArrayList<>();

    private BroadcastReceiver broadcastReceiver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        int position = getIntent().getIntExtra(Constant.INTENT.POSITION, -1);

        event = EventManager.getInstance().getEventByPosition(position);
        velibs = VelibManager.getInstance().getTenClosestStation(event.latitude, event.longitude);
        beParkList = BeParkManager.getInstance().getTenClosestStation(event.latitude, event.longitude);

        setUpMapIfNeeded();

        initViews();

        initBroadCastReceiver();

    }


    private void initBroadCastReceiver(){

        broadcastReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {

                velibs = VelibManager.getInstance().getTenClosestStation(event.latitude, event.longitude);
                beParkList = BeParkManager.getInstance().getTenClosestStation(event.latitude, event.longitude);

                mMap.clear();

                updateMarkers();


            }
        };

    }


    private void initViews(){

        TextView title = (TextView) findViewById(R.id.title_textview);
        title.setText(event.title);

        TextView lieu = (TextView) findViewById(R.id.lieu_textview);
        lieu.setText(event.lieu);



        if ( event.intro != null ){
            TextView intro = (TextView) findViewById(R.id.intro_textview);
            intro.setText(event.intro);

        }

        if( event.corps != null ){
            final TextView description = (TextView) findViewById(R.id.description_textview);
            description.setText( event.corps);
            description.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    int maxLine = Integer.MAX_VALUE ;

                    if ( isMaxLineActived ){
                        maxLine = 5;
                    }
                    isMaxLineActived = ! isMaxLineActived;
                    description.setMaxLines(maxLine);
                }
            });
        }

        topPicture = (ImageView) findViewById(R.id.top_imageview);

        Ion.with(topPicture)
                .placeholder(R.drawable.background)
                .error(R.drawable.background)
                .load(event.getUrlImage());

    }

    @Override
    protected void onResume() {
        super.onResume();

        BeParkManager.getInstance().updateData();
        VelibManager.getInstance().updateVelibsData();

        LocalBroadcastManager.getInstance(getApplicationContext()).registerReceiver(broadcastReceiver, new IntentFilter(Constant.ACTION.UPDATE_DETAIL));

        setUpMapIfNeeded();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        LocalBroadcastManager.getInstance(getApplicationContext()).unregisterReceiver(broadcastReceiver);

    }

    /**
     * Sets up the map if it is possible to do so (i.e., the Google Play services APK is correctly
     * installed) and the map has not already bePK on their device.
     * <p/>
     * A user can return to this FragmentActiviten instantiated.. This will ensure that we only ever
     * call {@link #setUpMap()} once when {@link #mMap} is not null.
     * <p/>
     * If it isn't installed {@link SupportMapFragment} (and
     * {@link com.google.android.gms.maps.MapView MapView}) will show a prompt for the user to
     * install/update the Google Play services Ay after following the prompt and correctly
     * installing/updating/enabling the Google Play services. Since the FragmentActivity may not
     * have been completely destroyed during this process (it is likely that it would only be
     * stopped or paused), {@link #onCreate(Bundle)} may not be called again so we should call this
     * method in {@link #onResume()} to guarantee that it will be called.
     */
    private void setUpMapIfNeeded() {
        // Do a null check to confirm that we have not already instantiated the map.
        if (mMap == null) {
            // Try to obtain the map from the SupportMapFragment.
            mMap = ((SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map))
                    .getMap();
            // Check if we were successful in obtaining the map.
            if (mMap != null) {
                setUpMap();
            }
        }
    }

    /**
     * This is where we can add markers or lines, add listeners or move the camera. In this case, we
     * just add a marker near Africa.
     * <p/>
     * This should only be called once and when we are sure that {@link #mMap} is not null.
     */


    private void updateMarkers(){

        markers = new ArrayList<>();

        LatLng eventPosition = new LatLng(event.latitude, event.longitude);

        Marker eventMarker = mMap.addMarker(new MarkerOptions().position(eventPosition).title(event.title));
        markers.add(eventMarker);

        if ( velibs.size() != 0 ){

            Bitmap icon = BitmapFactory.decodeResource(getResources(),
                    R.drawable.byke);

            for ( Velib velib : velibs ){


                String v = " vélibs disponibles";
                if ( Integer.parseInt(velib.available_bikes) < 2){
                    v = " vélib disponible";
                }

                LatLng velibPosition = new LatLng(velib.latitude, velib.longitude);
                Marker marker = mMap.addMarker(new MarkerOptions()
                                .position(velibPosition)
                                .title(velib.name)
                                .snippet(velib.available_bikes + v)
                                .icon(BitmapDescriptorFactory.fromBitmap(icon))
                );
                markers.add(marker);
            }

        }

        if ( beParkList.size() != 0 ){

            Bitmap icon = BitmapFactory.decodeResource(getResources(),
                    R.drawable.park);

            for ( BePark bePark : beParkList ){
                LatLng velibPosition = new LatLng(bePark.lat, bePark.lng);
                Marker marker = mMap.addMarker(new MarkerOptions()
                                .position(velibPosition)
                                .title(bePark.name)
                                .icon(BitmapDescriptorFactory.fromBitmap(icon))
                );
                markers.add(marker);
            }


        }

    }

    private void setUpMap() {

        updateMarkers();

        mMap.setOnMapLoadedCallback(new GoogleMap.OnMapLoadedCallback() {
            @Override
            public void onMapLoaded() {

                LatLngBounds.Builder builder = new LatLngBounds.Builder();
                for (Marker marker : markers) {
                    builder.include(marker.getPosition());
                }
                final LatLngBounds bounds = builder.build();

                int padding = 200; // offset from edges of the map in pixels
                CameraUpdate cu = CameraUpdateFactory.newLatLngBounds(bounds, padding);
                mMap.moveCamera(cu);

            }
        });

        mMap.setOnInfoWindowClickListener(new GoogleMap.OnInfoWindowClickListener() {
            @Override
            public void onInfoWindowClick(Marker marker) {

                Intent intent = new Intent(android.content.Intent.ACTION_VIEW,
                        Uri.parse("geo:0,0?q=" + marker.getPosition().latitude  + "," + marker.getPosition().longitude + "(" + marker.getTitle() + ")"));
                startActivity(intent);

            }
        });

        mMap.getUiSettings().setScrollGesturesEnabled(false);

    }
}

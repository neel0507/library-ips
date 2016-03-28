/*
package indoor_positioning_system.indoorpositioningsystem20;

import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.text.TextUtils;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.indooratlas.android.sdk.IALocation;
import com.indooratlas.android.sdk.IALocationListener;
import com.indooratlas.android.sdk.IALocationManager;
import com.indooratlas.android.sdk.IALocationRequest;
import com.indooratlas.android.sdk.IARegion;

public class MapsActivity extends FragmentActivity implements IALocationListener {

    private GoogleMap mMap;
    private Marker mMarker;
    private IALocationManager mIALocationManager;
    private static final float HUE_IABLUE = 200.0f;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        mIALocationManager = IALocationManager.create(this);

        final String floorPlanId = getString(R.string.indooratlas_floor_plan_id);
        if (!TextUtils.isEmpty(floorPlanId)) {
            final IALocation FLOOR_PLAN_ID = IALocation.from(IARegion.floorPlan(floorPlanId));
            mIALocationManager.setLocation(FLOOR_PLAN_ID);
        }
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        mIALocationManager.destroy();
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (mMap == null) {
            mMap = ((SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map))
                    .getMap();
        }
        mIALocationManager.requestLocationUpdates(IALocationRequest.create(), this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (mIALocationManager != null) {
            mIALocationManager.removeLocationUpdates(this);
        }
    }

    */
/**
     * Callback for receiving locations.
     * This is where location updates can be handled by moving markers or the camera.
     *//*

    public void onLocationChanged(IALocation location) {
        LatLng latLng = new LatLng(location.getLatitude(), location.getLongitude());
        if (mMarker == null) {
            if (mMap != null) {
                mMarker = mMap.addMarker(new MarkerOptions().position(latLng)
                        .icon(BitmapDescriptorFactory.defaultMarker(HUE_IABLUE)));
                mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 17.0f));
            }
        } else {
            mMarker.setPosition(latLng);
        }
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {
        // N/A
    }
   */
/* public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        // Add a marker in Sydney and move the camera
        LatLng sydney = new LatLng(-34, 151);
        mMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));
    }*//*

}*/

/*
package indoor_positioning_system.indoorpositioningsystem20;

import android.content.Intent;
import android.graphics.PointF;
import android.os.Bundle;
import android.os.Looper;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.widget.ImageView;
import android.widget.Toast;

import com.indooratlas.android.sdk.IALocation;
import com.indooratlas.android.sdk.IALocationListener;
import com.indooratlas.android.sdk.IALocationManager;
import com.indooratlas.android.sdk.IALocationRequest;
import com.indooratlas.android.sdk.IARegion;
import com.indooratlas.android.sdk.resources.IAFloorPlan;
import com.indooratlas.android.sdk.resources.IALatLng;
import com.indooratlas.android.sdk.resources.IAResourceManager;
import com.indooratlas.android.sdk.resources.IAResult;
import com.indooratlas.android.sdk.resources.IAResultCallback;
import com.indooratlas.android.sdk.resources.IATask;
import com.squareup.picasso.Picasso;


public class FloorPlanLoader extends FragmentActivity
{
    //private static final float dotRadius = 1.0f;
    private IALocationManager mIALocationManager;
    private IAResourceManager mResourceManager;
    private IATask<IAFloorPlan> mPendingAsyncResult;
    private ImageView mFloorPlanImage;
    //private IAFloorPlan mFloorPlan;
    //private BlueDotView mImageView;
    //private long mDownloadId;
    //private DownloadManager mDownloadManager;
    public static final String TAG ="FloorPlanLoader";

    private IALocationListener mIALocationListener = new IALocationListener()
    {

        // Called when the location has changed.
        @Override
        public void onLocationChanged(IALocation location) {
            Log.d(TAG, "location is: " + location.getLatitude() + "," + location.getLongitude());

            */
/*if (mImageView != null && mImageView.isReady()) {
                IALatLng latLng = new IALatLng(location.getLatitude(), location.getLongitude());
                PointF point = mFloorPlan.coordinateToPoint(latLng);
                mImageView.setDotCenter(point);
                mImageView.postInvalidate();
            }*//*

        }

        @Override
        public void onStatusChanged(String s, int i, Bundle bundle) {

        }
    };
    private IARegion.Listener mRegionListener = new IARegion.Listener()
    {

        @Override
        public void onEnterRegion(IARegion region) {
            if (region.getType() == IARegion.TYPE_FLOOR_PLAN) {
                fetchFloorPlan(region.getId());
            }
        }

        @Override
        public void onExitRegion(IARegion region) {
            startActivity(new Intent(FloorPlanLoader.this, MainActivity.class));
            Toast.makeText(getApplicationContext(), "You have left the library", Toast.LENGTH_LONG).show();
        }

    };

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_floor_plan_loader);
        findViewById(android.R.id.content).setKeepScreenOn(true);
        mFloorPlanImage = (ImageView) findViewById(R.id.imageView);
        //mImageView = (BlueDotView) findViewById(R.id.view);
        //mDownloadManager = (DownloadManager) getSystemService(Context.DOWNLOAD_SERVICE);
        mIALocationManager = IALocationManager.create(this);
        mResourceManager = IAResourceManager.create(this);
    }
    @Override
    protected void onResume()
    {
        super.onResume();
        mIALocationManager.requestLocationUpdates(IALocationRequest.create(), mIALocationListener);
        mIALocationManager.registerRegionListener(mRegionListener);
        */
/*registerReceiver(onComplete, new IntentFilter(DownloadManager.ACTION_DOWNLOAD_COMPLETE));*//*

    }
    @Override
    protected void onPause() {
        super.onPause();
        mIALocationManager.removeLocationUpdates(mIALocationListener);
        */
/*unregisterReceiver(onComplete);*//*

    }
    @Override
    protected void onDestroy()
    {
        mIALocationManager.destroy();
        super.onDestroy();
    }

    private void fetchFloorPlan(String id) {
        cancelPendingNetworkCalls();
        if (mPendingAsyncResult != null && !mPendingAsyncResult.isCancelled()) {
            mPendingAsyncResult.cancel();
        }
        mPendingAsyncResult = mResourceManager.fetchFloorPlanWithId(id);
        if (mPendingAsyncResult != null) {
            mPendingAsyncResult.setCallback(new IAResultCallback<IAFloorPlan>() {
                @Override
                public void onResult(IAResult<IAFloorPlan> result) {
                    Log.d(TAG, "onResult: %s" + result);

                    if (result.isSuccess()) {
                        handleFloorPlanChange(result.getResult());
                    } else {
                        Toast.makeText(FloorPlanLoader.this, "", Toast.LENGTH_LONG).show();
                        */
/*Toast.makeText(FloorPlanLoader.this, "loading floor plan failed: " + result.getError(), Toast.LENGTH_LONG).show();*//*

                    }
                }
            }, Looper.getMainLooper()); // deliver callbacks in main thread
        }
    }

   private void handleFloorPlanChange(IAFloorPlan floorPlan)
   {
       Picasso.with(this)
               .load(floorPlan.getUrl())
               .rotate(90)
                       //.resize(500,500)
               .into(mFloorPlanImage);
   }
    private void cancelPendingNetworkCalls() {
        if (mPendingAsyncResult != null && !mPendingAsyncResult.isCancelled()) {
            mPendingAsyncResult.cancel();
        }
    }
}

*/

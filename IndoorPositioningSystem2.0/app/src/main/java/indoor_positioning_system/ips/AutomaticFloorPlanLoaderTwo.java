/*
package indoor_positioning_system.indoorpositioningsystem20;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.PointF;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Looper;
import android.support.v4.app.FragmentActivity;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Toast;

import com.davemorrissey.labs.subscaleview.ImageSource;
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
import com.squareup.picasso.RequestCreator;
import com.squareup.picasso.Target;


public class AutomaticFloorPlanLoaderTwo extends FragmentActivity
{
    private static final float dotRadius = 1.0f;
    private static final int MAX_DIMENSION = 2048;
    private IALocationManager mIALocationManager;
    private IAResourceManager mResourceManager;
    private IATask<IAFloorPlan> mPendingAsyncResult;
    //private IAFloorPlan mFloorPlan;
    private BlueDotView mImageView;
    private Target mLoadTarget;
    private static final String TAG ="FloorPlanLoader";
    private ProgressDialog progressDialog;
    private IALatLng latLng;



    private IALocationListener mIALocationListener = new IALocationListener()
    {
        @Override
        public void onLocationChanged(IALocation location) {
            Log.d(TAG, "location is: " + location.getLatitude() + "," + location.getLongitude());
            if (mImageView != null && mImageView.isReady()) {
                latLng = new IALatLng(location.getLatitude(), location.getLongitude());
                //PointF point = mFloorPlan.coordinateToPoint(latLng);
                //mImageView.setDotCenter(point);
                //mImageView.postInvalidate();
            }

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
                String id = region.getId();
                Log.d(TAG, "floorPlan changed to " + id);
                fetchFloorPlan(id);
            }
        }

        @Override
        public void onExitRegion(IARegion region) {
            // leaving a previously entered region
            */
/*startActivity(new Intent(AutomaticFloorPlanLoader.this, IndoorPositioning.class));
            Toast.makeText(getApplicationContext(), "You have left the library", Toast.LENGTH_LONG).show();*//*

        }

    };

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        findViewById(android.R.id.content).setKeepScreenOn(true);
        setContentView(R.layout.activity_indoor_positioning);
        progressDialog = ProgressDialog.show(AutomaticFloorPlanLoaderTwo.this, "Loading...", "Loading Indoor Positioning System, please wait..", false, false);

        Toast.makeText(getApplicationContext(), "The application will keep loading until you are in the library.", Toast.LENGTH_LONG).show();
        Toast.makeText(getApplicationContext(), "The application will keep loading until you are in the library.", Toast.LENGTH_LONG).show();

        progressDialog.setCancelable(true);
        mImageView = (BlueDotView) findViewById(R.id.imageView);

        Intent intent = getIntent();
        String location1 = intent.getExtras().getString("location");
        Log.d("location1===", location1);

        mImageView.setLocation(location1);

        mIALocationManager = IALocationManager.create(this);

        mResourceManager = IAResourceManager.create(this);

        final String floorPlanId = getString(R.string.indooratlas_floor_plan_id);
        if (!TextUtils.isEmpty(floorPlanId))
        {
            final IALocation location = IALocation.from(IARegion.floorPlan(floorPlanId));
            mIALocationManager.setLocation(location);
        }
    }

    @Override
    protected void onResume()
    {
        super.onResume();
        mIALocationManager.requestLocationUpdates(IALocationRequest.create(), mIALocationListener);
        mIALocationManager.registerRegionListener(mRegionListener);
    }
    @Override
    protected void onPause() {
        super.onPause();
        mIALocationManager.removeLocationUpdates(mIALocationListener);
        mIALocationManager.unregisterRegionListener(mRegionListener);
    }
    @Override
    protected void onDestroy()
    {
        super.onDestroy();
        mIALocationManager.destroy();
    }

   private void fetchFloorPlanBitmap(final IAFloorPlan floorPlan) {
        final String filePath = floorPlan.getUrl();

        if (mLoadTarget == null)
        {
            mLoadTarget = new Target()
            {

                @Override
                public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from)
                {
                    Log.d(TAG, "onBitmap loaded with dimensions: " + bitmap.getWidth() + "x"
                            + bitmap.getHeight());
                    mImageView.setImage(ImageSource.bitmap(bitmap.copy(bitmap.getConfig(), true)));
                    mImageView.setRadius(floorPlan.getMetersToPixels() * dotRadius);
                    PointF point = floorPlan.coordinateToPoint(latLng);
                    mImageView.setDotCenter(point);
                    mImageView.postInvalidate();
                }

                @Override
                public void onPrepareLoad(Drawable placeHolderDrawable) {

                }

                @Override
                public void onBitmapFailed(Drawable placeHolderDrawable) {
                    Toast.makeText(AutomaticFloorPlanLoaderTwo.this, "Failed to load bitmap",
                            Toast.LENGTH_SHORT).show();
                }
            };
        }

        RequestCreator request = Picasso.with(this).load(filePath).rotate(90); //.resize(0,0)

        final int bitmapWidth = floorPlan.getBitmapWidth();
        final int bitmapHeight = floorPlan.getBitmapHeight();

        if (bitmapHeight > MAX_DIMENSION) {
            request.resize(0, MAX_DIMENSION);
        } else if (bitmapWidth > MAX_DIMENSION) {
            request.resize(MAX_DIMENSION, 0);
        }
       request.into(mLoadTarget);
       Log.w(TAG, "showFloorPlanImage: " + filePath);
       progressDialog.dismiss();
   }

    private void fetchFloorPlan(String id) {
        cancelPendingNetworkCalls();
        final IATask<IAFloorPlan> asyncResult = mResourceManager.fetchFloorPlanWithId(id);
        mPendingAsyncResult = asyncResult;
        if (mPendingAsyncResult != null)
        {
            mPendingAsyncResult.setCallback(new IAResultCallback<IAFloorPlan>() {
                @Override
                public void onResult(IAResult<IAFloorPlan> result) {
                    Log.d(TAG, "fetch floor plan result:" + result);
                    if (result.isSuccess() && result.getResult() != null)
                    {
                        //mFloorPlan = result.getResult();
                        fetchFloorPlanBitmap(result.getResult());
                        //mImageView.setRadius(mFloorPlan.getMetersToPixels() * dotRadius);
                        //progressDialog.dismiss();
                    } else {
                        if (!asyncResult.isCancelled()) {
                            Toast.makeText(AutomaticFloorPlanLoaderTwo.this, (result.getError() != null
                                            ? "error loading floor plan: " + result.getError()
                                            : "access to floor plan denied"), Toast.LENGTH_LONG)
                                    .show();
                        }
                    }
                }
            }, Looper.getMainLooper());
        }
    }

    private void cancelPendingNetworkCalls() {
        if (mPendingAsyncResult != null && !mPendingAsyncResult.isCancelled()) {
            mPendingAsyncResult.cancel();
        }
    }
}



*/

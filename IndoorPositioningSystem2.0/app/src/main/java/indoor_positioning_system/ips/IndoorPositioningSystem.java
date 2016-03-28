package indoor_positioning_system.ips;

import android.app.DownloadManager;
import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.Cursor;
import android.graphics.PointF;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Looper;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.davemorrissey.labs.subscaleview.ImageSource;
import com.indooratlas.android.sdk.IALocation;
import com.indooratlas.android.sdk.IALocationListener;
import com.indooratlas.android.sdk.IALocationManager;
import com.indooratlas.android.sdk.IALocationRequest;
import com.indooratlas.android.sdk.IARegion;
import com.indooratlas.android.sdk.resources.IAFloorPlan;
import com.indooratlas.android.sdk.resources.IALatLng;
import com.indooratlas.android.sdk.resources.IALocationListenerSupport;
import com.indooratlas.android.sdk.resources.IAResourceManager;
import com.indooratlas.android.sdk.resources.IAResult;
import com.indooratlas.android.sdk.resources.IAResultCallback;
import com.indooratlas.android.sdk.resources.IATask;


import java.io.File;


public class IndoorPositioningSystem extends AppCompatActivity       //FragmentActivity
{
    private static final String TAG = "FloorPlanLoader";
    private static final float dotRadius = 1.0f;
    private IALocationManager mIALocationManager;
    private IAResourceManager mResourceManager;
    private IATask<IAFloorPlan> mPendingAsyncResult;
    private IAFloorPlan mFloorPlan;
    private BlueDotView mImageView;
    private long mDownloadId;
    private DownloadManager mDownloadManager;
    private ProgressDialog progressDialog;
    Button button;


    /*The following object and its method is used to identify the user's position and if the user
    is inside the library the floor plan and the blue dot are displayed to the user.*/
    private IALocationListener mIALocationListener = new IALocationListenerSupport()
    {
        // Called when the location has changed.
        @Override
        public void onLocationChanged(IALocation location) {
            Log.d(TAG, "location is: " + location.getLatitude() + "," + location.getLongitude());
            if (mImageView != null && mImageView.isReady()) {
                IALatLng latLng = new IALatLng(location.getLatitude(), location.getLongitude());
                PointF point = mFloorPlan.coordinateToPoint(latLng);
                mImageView.setDotCenter(point);
                mImageView.postInvalidate();
            }
        }
    };

    /*The following object and its method fetches floor plan from the server if the user is inside the library*/
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
            /*If the user leaves the library, then this method can be used to display a message saying "you have left the library".*/
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_indoor_positioning);
        findViewById(android.R.id.content).setKeepScreenOn(true);
        progressDialog = ProgressDialog.show(IndoorPositioningSystem.this, "Loading Indoor Positioning System...", "Indoor Positioning System will keep loading until you are inside the library.", false, false);
        progressDialog.setCancelable(true);
        button = (Button) findViewById(R.id.buttonfi);
        mImageView = (BlueDotView) findViewById(R.id.imageView);

        /*The following three lines of code are associated with displaying the pointer image.*/
        Intent intent = getIntent();
        String location1 = intent.getExtras().getString("location");
        mImageView.setLocation(location1);

        mDownloadManager = (DownloadManager) getSystemService(Context.DOWNLOAD_SERVICE);
        mIALocationManager = IALocationManager.create(this);
        mResourceManager = IAResourceManager.create(this);


    }

    /*The following method rotates the floor plan at 90 degrees each time the user clicks on "flip image" button.*/
    public void rotateImage(View v)
    {
        mImageView.setRotation(mImageView.getRotation()+90);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.help:
                startActivity(new Intent(IndoorPositioningSystem.this, Help.class));
                return true;
            /*case R.id.history:
                startActivity(new Intent(IndoorPositioningSystem.this, SearchHistory.class));
                return true;*/
            default:
                return false;
        }
    }

    /*The following three methods are associated with updating and removing locations when the Indoor Positioning System is on hold. */
    @Override
    protected void onDestroy()
    {
        super.onDestroy();
        mIALocationManager.destroy();
    }

    @Override
    protected void onResume()
    {
        super.onResume();
        mIALocationManager.requestLocationUpdates(IALocationRequest.create(), mIALocationListener);
        mIALocationManager.registerRegionListener(mRegionListener);
        registerReceiver(onComplete, new IntentFilter(DownloadManager.ACTION_DOWNLOAD_COMPLETE));
    }
    @Override
    protected void onPause()
    {
        super.onPause();
        mIALocationManager.removeLocationUpdates(mIALocationListener);
        mIALocationManager.unregisterRegionListener(mRegionListener);
        unregisterReceiver(onComplete);
    }

    /*The following object and its method downloads the floor plan from the server on the user's smartphone.*/
    private BroadcastReceiver onComplete = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            long id = intent.getLongExtra(DownloadManager.EXTRA_DOWNLOAD_ID, 0L);
            if (id != mDownloadId) {
                Log.w(TAG, "Ignore unrelated download");
                return;
            }
            Log.w(TAG, "Image download completed");
            Bundle extras = intent.getExtras();
            DownloadManager.Query q = new DownloadManager.Query();
            q.setFilterById(extras.getLong(DownloadManager.EXTRA_DOWNLOAD_ID));
            Cursor c = mDownloadManager.query(q);

            if (c.moveToFirst()) {
                int status = c.getInt(c.getColumnIndex(DownloadManager.COLUMN_STATUS));
                if (status == DownloadManager.STATUS_SUCCESSFUL) {
                    // process download
                    String filePath = c.getString(c.getColumnIndex(
                            DownloadManager.COLUMN_LOCAL_FILENAME));
                    showFloorPlanImage(filePath);
                }
            }
            c.close();
        }
    };

    /*The following method displays floor plan image and gets called with onComplete object and fetchFloorPlan method.*/
    private void showFloorPlanImage(String filePath) {
        Log.w(TAG, "showFloorPlanImage: " + filePath);
        mImageView.setRadius(mFloorPlan.getMetersToPixels() * dotRadius);
        mImageView.setImage(ImageSource.uri(filePath));
        mImageView.setRotation(270);
        mImageView.setZoomEnabled(false); //disables zooming in the floor plan
        mImageView.setPanEnabled(false);  //disables panning of the floor plan, for example, moving the floor plan with two fingers
        progressDialog.dismiss();       // The dialog will dismiss when the Indoor Positioning System executes.
    }

    /*The following method fetches floor plan from the server and gets called with mRegionListener object.*/
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
                        mFloorPlan = result.getResult();
                        String fileName = mFloorPlan.getId() + ".img";
                        String filePath = Environment.getExternalStorageDirectory() + "/"
                                + Environment.DIRECTORY_DOWNLOADS + "/" + fileName;
                        File file = new File(filePath);
                        if (!file.exists()) {
                            DownloadManager.Request request =
                                    new DownloadManager.Request(Uri.parse(mFloorPlan.getUrl()));
                            request.setDescription("Wembley Library floor plan");
                            request.setTitle("Floor plan");
                            // requires android 3.2 or later to compile
                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
                                request.allowScanningByMediaScanner();
                                request.setNotificationVisibility(DownloadManager.
                                        Request.VISIBILITY_HIDDEN);
                            }
                            request.setDestinationInExternalPublicDir(Environment.
                                    DIRECTORY_DOWNLOADS, fileName);

                            mDownloadId = mDownloadManager.enqueue(request);
                        } else {
                            showFloorPlanImage(filePath);
                        }
                    } else {
                        if (!asyncResult.isCancelled()) {
                            show((result.getError() != null ? "error loading floor plan: " + result.getError() : "access to floor plan denied"));
                            //Toast.makeText(AutomaticFloorPlanLoader.this, (result.getError() != null ? "error loading floor plan: " + result.getError() : "access to floor plan denied"), Toast.LENGTH_LONG).show();
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
    private void show(String str)
    {
        Toast.makeText(this, str, Toast.LENGTH_LONG).show();
    }
}




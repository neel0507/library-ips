package indoor_positioning_system.indoorpositioningsystem20;

import android.app.DownloadManager;
import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.PointF;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Looper;
import android.support.v4.app.FragmentActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
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
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;


public class AutomaticFloorPlanLoader extends FragmentActivity
{
    private static final String TAG = "FloorPlanLoader";
    private static final float dotRadius = 1.0f;
    //private static final int MAX_DIMENSION = 2048;
    private IALocationManager mIALocationManager;
    private IAResourceManager mResourceManager;
    private IATask<IAFloorPlan> mPendingAsyncResult;
    private IAFloorPlan mFloorPlan;
    private BlueDotView mImageView;
    private long mDownloadId;
    //public static Bitmap bitmap;
    private DownloadManager mDownloadManager;
    private ProgressDialog progressDialog;
    Button button;


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
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_automatic_floor_plan_loader);
        findViewById(android.R.id.content).setKeepScreenOn(true);
        progressDialog = ProgressDialog.show(AutomaticFloorPlanLoader.this, "Loading...", "Loading Indoor Positioning System, Please Wait..", false, false);
        show("The application will keep loading until you are inside the library.");
        show("The application will keep loading until you are inside the library.");
        progressDialog.setCancelable(true);
        button = (Button) findViewById(R.id.button);
        mImageView = (BlueDotView) findViewById(R.id.imageView);


        //bitmap = getBitmapFromURL("http://i.imgur.com/oz6np8M.png?1");
        //Intent intent2 = getIntent();
        //String location2 = intent2.getExtras().getString(String.valueOf(bitmap));
        //mImageView.setLocation(String.valueOf(bitmap));     //Do not use setImage.

        Intent intent = getIntent();
        String location1 = intent.getExtras().getString("location");
        Log.d("location1===" , location1);
        mImageView.setLocation(location1);

        mDownloadManager = (DownloadManager) getSystemService(Context.DOWNLOAD_SERVICE);
        mIALocationManager = IALocationManager.create(this);
        mResourceManager = IAResourceManager.create(this);

        /*final String floorPlanId = getString(R.string.indooratlas_floor_plan_id);         //maybe important
        if (!TextUtils.isEmpty(floorPlanId)) {
            final IALocation location = IALocation.from(IARegion.floorPlan(floorPlanId));
            mIALocationManager.setLocation(location);
        }*/
    }
    public void flipImage(View v)
    {
        mImageView.setRotation(mImageView.getRotation()+90);
    }

    /*public static Bitmap getBitmapFromURL(String src)
    {
        try
        {
            URL url = new URL(src);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setDoInput(true);
            connection.connect();
            InputStream input = connection.getInputStream();
            Bitmap myBitmap = BitmapFactory.decodeStream(input);
            return myBitmap;
        }
        catch (Exception e)
        {
            e.printStackTrace();
            return  null;
        }
    }*/

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_indoor_positioning, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.help:
                startActivity(new Intent(AutomaticFloorPlanLoader.this, Help.class));
                return true;
            case R.id.history:
                startActivity(new Intent(AutomaticFloorPlanLoader.this, SearchHistory.class));
                return true;
            default:
                return false;
        }
    }

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


    private void showFloorPlanImage(String filePath) {
        Log.w(TAG, "showFloorPlanImage: " + filePath);
        mImageView.setRadius(mFloorPlan.getMetersToPixels() * dotRadius);
        mImageView.setImage(ImageSource.uri(filePath));
        /*LinearLayout.LayoutParams layoutParams=new LinearLayout.LayoutParams(1000, 1500);
        layoutParams.setMargins(100, 100, 100, 100);
        mImageView.setLayoutParams(layoutParams);*/
        mImageView.setRotation(270);  //important
        mImageView.setZoomEnabled(false);
        mImageView.setPanEnabled(false);
        //mImageView.setX(70);          // sets image width position
        //mImageView.setY(70);          // sets image height position
        progressDialog.dismiss();       // The dialog will dismiss when the Indoor Positioning System executes.
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
                        mFloorPlan = result.getResult();
                        String fileName = mFloorPlan.getId() + ".img";
                        String filePath = Environment.getExternalStorageDirectory() + "/"
                                + Environment.DIRECTORY_DOWNLOADS + "/" + fileName;
                        File file = new File(filePath);
                        if (!file.exists()) {
                            DownloadManager.Request request =
                                    new DownloadManager.Request(Uri.parse(mFloorPlan.getUrl()));
                            request.setDescription("IndoorAtlas floor plan");
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
                        // do something with error
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
    public void show(String str)
    {
        Toast.makeText(this, str, Toast.LENGTH_LONG).show();
    }
}




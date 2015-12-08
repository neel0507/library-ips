package indoor_positioning_system.indoorpositioningsystem20;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PointF;
import android.util.AttributeSet;
import android.util.Log;

import com.davemorrissey.labs.subscaleview.SubsamplingScaleImageView;

public class BlueDotView extends SubsamplingScaleImageView
{
        private float radius = 1.0f;
        private PointF dotCenter = null;
        Paint paint = new Paint();
        static String location = "";

        public String getLocation() {
            return location;
        }

        public void setLocation(String location) {
            this.location = location;
        }

        public void setRadius(float radius) {
            this.radius = radius;
        }

        public void setDotCenter(PointF dotCenter) {
            this.dotCenter = dotCenter;
        }

        public BlueDotView(Context context) {
            this(context, null);
        }

        public BlueDotView(Context context, AttributeSet attr) {
            super(context, attr);
            initialise();

        }

    private void initialise() {
        setWillNotDraw(false);
        setPanLimit(SubsamplingScaleImageView.PAN_LIMIT_CENTER);
        }

        @Override
        protected void onDraw(Canvas canvas) {
            super.onDraw(canvas);
            if (!isReady()) {
                return;
            }

            if (dotCenter != null) {
                PointF vPoint = sourceToViewCoord(dotCenter);
                //float scaledRadius = getScale() * radius;
                //paint.setStrokeWidth(5);
                paint.setAntiAlias(true);
                paint.setStyle(Paint.Style.FILL);

                paint.setColor(Color.BLUE);
                canvas.drawCircle(vPoint.x, vPoint.y, 20, paint);
                //canvas.setBitmap(AutomaticFloorPlanLoader.bitmap);
                /*try
                {
                    Bitmap x = AutomaticFloorPlanLoader.bitmap;
                    //Log.d("location=========", locationImage);
                    //float a = 100;
                    //float b = 100;
                    canvas.drawBitmap(x, null, null);
                    Log.d("Location URL:", "" + x);
                }
                catch (Exception e)
                {
                    e.printStackTrace();
                }*/



                String locationArray[] = getLocation().split(",");
                Log.d("location=========", locationArray[0]);
                float x= Float.parseFloat(locationArray[0]);
                float y= Float.parseFloat(locationArray[1]);
                canvas.drawBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.smalllogo),x ,y, null);
                Log.d("custom point", "" + x + " " + y);

            }
        }
}

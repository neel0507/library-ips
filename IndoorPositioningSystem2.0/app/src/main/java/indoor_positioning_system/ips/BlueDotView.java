package indoor_positioning_system.ips;

import android.content.Context;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PointF;
import android.util.AttributeSet;

import com.davemorrissey.labs.subscaleview.SubsamplingScaleImageView;

public class BlueDotView extends SubsamplingScaleImageView
{
        private float radius = 1.0f;
        private PointF dotCenter = null;
        Paint paint = new Paint();
        static String location = ""; // should be public?

        /*The following get and set methods are associated with displaying the pointer image.*/
        public String getLocation()
        {
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

        /*The following method draws the blue dot as well as displays the pointer image from the "drawable" folder.*/
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

                paint.setColor(Color.BLUE); //defines the blue dot
                canvas.drawCircle(vPoint.x, vPoint.y, 20, paint);   //defines the radius of the blue dot

                /*The following string array splits the "Location" column values into two values
                and displays the pointer image by reading those values.*/
                String locationArray[] = getLocation().split(","); //getLocation key method
                //Log.d("location=========", locationArray[0]);
                float x= Float.parseFloat(locationArray[0]);
                float y= Float.parseFloat(locationArray[1]);
                canvas.drawBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.logo),x ,y, null);
                //Log.d("custom point", "" + x + " " + y);

            }
        }
}

package indoor_positioning_system.indoorpositioningsystem20;

import android.content.Context;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PointF;
import android.support.annotation.NonNull;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Display;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;

import com.davemorrissey.labs.subscaleview.SubsamplingScaleImageView;

public class BlueDotViewTwo extends SubsamplingScaleImageView
{
    private float displayWidth;
    private float displayHeight;
    private boolean dragged = false;
    private static float MIN_ZOOM = 1f;
    private static float MAX_ZOOM = 5f;
    private float scaleFactor = 1.f;
    private ScaleGestureDetector detector;
    private static int NONE = 0;
    private static int DRAG = 1;
    private static int ZOOM = 2;
    private int mode;
    private float startX = 0f;
    private float startY = 0f;
    private float translateX = 0f;
    private float translateY = 0f;
    private float previousTranslateX = 0f;
    private float previousTranslateY = 0f;


    //private static final float RATIO = 4f / 3f;
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

    public BlueDotViewTwo(Context context) {
        this(context, null);
    }

    public BlueDotViewTwo(Context context, AttributeSet attr) {
        super(context, attr);
        initialise();

        detector = new ScaleGestureDetector(getContext(), new ScaleListener());
        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        Display display = wm.getDefaultDisplay();
        displayWidth = display.getWidth();
        displayHeight = display.getHeight();
    }

    /*public BlueDotView(Context context, AttributeSet attr, int defStyle)
    {
        super(context,attr,defStyle);
    }*/

    private void initialise() {
        setWillNotDraw(false);
        //setPanLimit(SubsamplingScaleImageView.PAN_LIMIT_CENTER);

    }
    @Override
    public boolean onTouchEvent(@NonNull MotionEvent event) {
        switch (event.getAction() & MotionEvent.ACTION_MASK) {
            case MotionEvent.ACTION_DOWN:
                mode = DRAG;
                startX = event.getX() - previousTranslateX;
                startY = event.getY() - previousTranslateY;
                break;
            case MotionEvent.ACTION_MOVE:
                translateX = event.getX() - startX;
                translateY = event.getY() - startY;
                double distance = Math.sqrt(Math.pow(event.getX() - (startX + previousTranslateX), 2) +
                        Math.pow(event.getY() - (startY + previousTranslateY), 2)
                );
                if(distance > 0) {
                    dragged = true;
                    distance*=scaleFactor;
                }
                break;
            case MotionEvent.ACTION_POINTER_DOWN:
                mode = ZOOM;
                break;
            case MotionEvent.ACTION_UP:
                mode = NONE;
                dragged = false;
                previousTranslateX = translateX;
                previousTranslateY = translateY;
                break;
            case MotionEvent.ACTION_POINTER_UP:
                mode = DRAG;
                previousTranslateX = translateX;
                previousTranslateY = translateY;
                break;
        }
        detector.onTouchEvent(event);
        if ((mode == DRAG && scaleFactor != 1f && dragged) || mode == ZOOM) {
            invalidate();
        }
        return true;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.save();
        canvas.scale(scaleFactor, scaleFactor);
        if((translateX * -1) < 0) {
            translateX = 0;
        }
        else if((translateX * -1) > (scaleFactor - 1) * displayWidth) {
            translateX = (1 - scaleFactor) * displayWidth;
        }
        if(translateY * -1 < 0) {
            translateY = 0;
        }
        else if((translateY * -1) > (scaleFactor - 1) * displayHeight) {
            translateY = (1 - scaleFactor) * displayHeight;
        }
        canvas.translate(translateX / scaleFactor, translateY / scaleFactor);
        canvas.restore();
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
            canvas.drawCircle(vPoint.x, vPoint.y, 25, paint); // a blue circle with size of 25

            String locarray[] = getLocation().split(",");
            Log.d("location=========",locarray[0]);
            float x= Float.parseFloat(locarray[0]);
            float y= Float.parseFloat(locarray[1]);
            canvas.drawBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.logo),x , y, null);
            Log.d("custom point", "" + x + " " + y);

        }
    }
    private class ScaleListener extends ScaleGestureDetector.SimpleOnScaleGestureListener {
        @Override
        public boolean onScale(ScaleGestureDetector detector) {
            scaleFactor *= detector.getScaleFactor();
            scaleFactor = Math.max(MIN_ZOOM, Math.min(scaleFactor, MAX_ZOOM));
            return true;
        }
    }
}
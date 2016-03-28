package indoor_positioning_system.ips;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

public class Help extends AppCompatActivity //AppCompatActivity
{
    TextView tv,tv2,tv3,tv4,tv5,tv6,tv7,tv8,tv9,gn,ips;
    ImageView imageView;
    @Override
    protected  void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_help);

        gn  = (TextView) findViewById(R.id.general);
        ips = (TextView) findViewById(R.id.ipsguide);
        tv  = (TextView) findViewById(R.id.textView);
        tv2 = (TextView) findViewById(R.id.textView2);
        tv3 = (TextView) findViewById(R.id.textView3);
        tv4 = (TextView) findViewById(R.id.textView4);
        tv5 = (TextView) findViewById(R.id.textView5);
        tv6 = (TextView) findViewById(R.id.textView6);
        tv7 = (TextView) findViewById(R.id.textView7);
        tv8 = (TextView) findViewById(R.id.textView8);
        tv9 = (TextView) findViewById(R.id.textView9);
        imageView = (ImageView) findViewById(R.id.imageView);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        switch (item.getItemId())
        {
            case R.id.help:
                startActivity(new Intent(Help.this, Help.class));
                return true;
           /* case R.id.history:
                startActivity(new Intent(Help.this, SearchHistory.class));
                return true;*/
            default:
                return false;
        }
    }
}

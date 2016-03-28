package indoor_positioning_system.ips;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

public class SearchHistory extends AppCompatActivity    //AppCompatActivity
{
    public static final String DEFAULT = "No data";
    ListView listView;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor sp;
    public ArrayAdapter<String> adapter;
    //String loadSearchHistory = MainActivity.loadSearchHistory;
    //ArrayList<String> searchHistory = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listview);

        listView = (ListView) findViewById(R.id.listViewMain);
        /*String loadSearchHistory = MainActivity.loadSearchHistory;
        //searchHistory.clear();
        for(int i=0; i<searchHistory.size(); i++) {
            searchHistory.add(loadSearchHistory);
        }
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, searchHistory);
        listView.setAdapter(adapter);
        adapter.notifyDataSetChanged();*/

        //sharedPreferences = getSharedPreferences("MyStorage", Context.MODE_PRIVATE);
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        sp = sharedPreferences.edit();

        String bt = sharedPreferences.getString("bt", DEFAULT);
        String an = sharedPreferences.getString("an", DEFAULT);
        String cn = sharedPreferences.getString("cn", DEFAULT);
        String in = sharedPreferences.getString("in", DEFAULT);

        if (bt.equals(DEFAULT) || an.equals(DEFAULT) || cn.equals(DEFAULT) || in.equals(DEFAULT))
        {
            Toast.makeText(this, "No search history found", Toast.LENGTH_LONG).show();
        }
        else
        {
            /*The following code needs to be improved in future as it does not display multiple search history inputs and
             only shows one previously entered input.*/
            String [] values = new String[]{bt,an,cn,in};
            adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, values);
            listView.setAdapter(adapter);
            adapter.notifyDataSetChanged();
            sp.apply();
        }
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
        switch (item.getItemId()) {
            case R.id.help:
                startActivity(new Intent(SearchHistory.this, Help.class));
                return true;
            /*case R.id.history:
                startActivity(new Intent(SearchHistory.this, SearchHistory.class));
                return true;*/
            default:
                return false;
        }
    }
}
package indoor_positioning_system.indoorpositioningsystem20;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.ListView;


public class SearchHistory extends Activity
{
    ListView listView,listView2;
    SharedPreferences FeedPref,FeedPref2;
    SharedPreferences.Editor fd,fd2;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listview);

        listView = (ListView) findViewById(R.id.listViewMain);
        listView2 = (ListView) findViewById(R.id.listViewMain2);

        /*listView3 = (ListView) findViewById(R.id.listViewMain3);
        listView4 = (ListView) findViewById(R.id.listViewMain4);*/

        FeedPref= PreferenceManager.getDefaultSharedPreferences(this);
        fd=FeedPref.edit();
        FeedPref2= PreferenceManager.getDefaultSharedPreferences(this);
        fd2=FeedPref.edit();

        String  bt1=FeedPref.getString("bt1" , null);
        String  an1=FeedPref.getString("an1" , null);
        String  cn1=FeedPref.getString("cn1" , null);
        String  in1=FeedPref.getString("in1" , null);

        String  bt2=FeedPref.getString("bt2" , null);
        String  an2=FeedPref.getString("an2" , null);
        String  cn2=FeedPref.getString("cn2" , null);
        String  in2=FeedPref.getString("in2" , null);

        String[] values  = new String[] {bt1,an1,cn1,in1};
        String[] values2 = new String[] {bt2,an2,cn2,in2};

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, values);
        ArrayAdapter<String> adapter2 = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, values2);
        /*ArrayAdapter<String> adapter2 = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, android.R.id.text1, an);
        ArrayAdapter<String> adapter3 = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, android.R.id.text1, cn);
        ArrayAdapter<String> adapter4 = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, android.R.id.text1, in);*/

        listView.setAdapter(adapter);
        listView2.setAdapter(adapter2);
        fd.apply();


        /*listView2.setAdapter(adapter2);
        listView3.setAdapter(adapter3);
        listView4.setAdapter(adapter4);*/
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_indoor_positioning, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        switch (item.getItemId()) {
            case R.id.help:
                startActivity(new Intent(SearchHistory.this, Help.class));
                return true;
            default:
                return false;
        }
    }
}
//TextView txt1,txt2,tx3,tx4,tx5;
//TextView textView;
    /*private String[] arrText = new String[]{"Text1","Text2","Text3","Text4"
                    ,"Text5", "Text6", "Text7","Text8","Text9","Text10"
                    , "Text11", "Text12","Text13","Text14","Text15"
                    ,"Text16","Text17","Text18","Text19","Text20"
                    ,"Text21","Text22","Text23","Text24"};
    private String[] arrTemp;*/

/*arrTemp = new String[arrText.length];
        MyListAdapter myListAdapter = new MyListAdapter();
        ListView listView = (ListView) findViewById(R.id.listViewMain);
        listView.setAdapter(myListAdapter);*/

        /*textView = (TextView) findViewById(R.id.textView);
        Bundle bundle = getIntent().getExtras();
        String s1 = bundle.getString("s1");
        textView.setText(s1);*/

/*private class MyListAdapter extends BaseAdapter
    {

        @Override
        public int getCount() {
            if(arrText != null && arrText.length != 0){
                return arrText.length;
            }
            return 0;
        }

        @Override
        public Object getItem(int position) {
            return arrText[position];
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent)
        {

            //ViewHolder holder = null;
            final ViewHolder holder;
            if (convertView == null) {

                holder = new ViewHolder();
                LayoutInflater inflater = SearchHistory.this.getLayoutInflater();
                convertView = inflater.inflate(R.layout.activity_search_history, parent,false);
                holder.textView1 = (TextView) convertView.findViewById(R.id.textView1);
                holder.editText1 = (EditText) convertView.findViewById(R.id.editText1);

                Bundle bundle = getIntent().getExtras();
                String s1 = bundle.getString("s1");
                holder.editText1.setText(s1);

                convertView.setTag(holder);

            } else {

                holder = (ViewHolder) convertView.getTag();
            }

            holder.ref = position;

            holder.textView1.setText(arrText[position]);                //error line
            holder.editText1.setText(arrTemp[position]);                //error line

            holder.editText1.addTextChangedListener(new TextWatcher()
            {

                @Override
                public void onTextChanged(CharSequence arg0, int arg1, int arg2, int arg3) {
                }

                @Override
                public void beforeTextChanged(CharSequence arg0, int arg1, int arg2,
                                              int arg3) {
                }

                @Override
                public void afterTextChanged(Editable arg0) {
                    arrTemp[holder.ref] = arg0.toString();
                }
            });

            return convertView;
        }

        private class ViewHolder
        {
            TextView textView1;
            EditText editText1;
            int ref;
        }
    }*/


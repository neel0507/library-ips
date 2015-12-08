package indoor_positioning_system.indoorpositioningsystem20;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AutoCompleteTextView;

public class IndoorPositioning extends AppCompatActivity {
    ImageButton indoorMap,bookInformation,gps,reset;
    AutoCompleteTextView bookName,authorName,callNumber,isbn;
    TextView textView,textView2,textView3,textView4;
    BookLocatorDatabaseAdapter.BookLocatorDatabase bookLocator;
    BookLocatorDatabaseAdapter bookLocatorNew;
    public String location="";
    SQLiteDatabase db=null;
    public static String bookStatusTwo;
    SharedPreferences.Editor fd,fd2;
    SharedPreferences FeedPref,FeedPref2;
    Intent intent;

    public String getLocation()
    {
        return location;
    }

    public void setLocation(String location)
    {
        this.location = location;
    }
    @SuppressLint("SetJavaScriptEnabled")

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        bookLocatorNew = new BookLocatorDatabaseAdapter(this);
        bookLocator = new BookLocatorDatabaseAdapter.BookLocatorDatabase(IndoorPositioning.this);
        db=bookLocator.getWritableDatabase();
        intent = new Intent(IndoorPositioning.this, AutomaticFloorPlanLoader.class);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_indoor_positioning);

        textView = (TextView) findViewById(R.id.textView);
        textView2 = (TextView) findViewById(R.id.textView2);
        textView3 = (TextView) findViewById(R.id.textView3);
        textView4 = (TextView) findViewById(R.id.textView4);

        bookInformation = (ImageButton) findViewById(R.id.imageButton);
        indoorMap = (ImageButton) findViewById(R.id.imageButtonTwo);
        reset = (ImageButton) findViewById(R.id.imageButtonThree);
        gps = (ImageButton) findViewById(R.id.imageButtonFour);

        bookName = (AutoCompleteTextView) findViewById(R.id.editText);
        authorName = (AutoCompleteTextView) findViewById(R.id.editTextTwo);
        callNumber = (AutoCompleteTextView) findViewById(R.id.editTextThree);
        isbn = (AutoCompleteTextView) findViewById(R.id.editTextFour);

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, R.layout.list_item, BOOKTITLES);
        ArrayAdapter<String> adapter2 = new ArrayAdapter<>(this, R.layout.list_item, AUTHORNAMES);
        ArrayAdapter<String> adapter3 = new ArrayAdapter<>(this, R.layout.list_item, CALLNUMBERS);
        ArrayAdapter<String> adapter4 = new ArrayAdapter<>(this, R.layout.list_item, ISBN);

        bookName.setAdapter(adapter);
        bookName.setDropDownWidth(getResources().getDisplayMetrics().widthPixels);
        authorName.setAdapter(adapter2);
        callNumber.setAdapter(adapter3);
        isbn.setAdapter(adapter4);

        bookName.setOnTouchListener(new View.OnTouchListener()
        {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                bookName.showDropDown();
                /*bookName.setInputType(InputType.TYPE_CLASS_TEXT);
                bookName.requestFocus();
                InputMethodManager mgr = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                mgr.showSoftInput(bookName, InputMethodManager.SHOW_FORCED);*/
                //bookName.setInputType(InputType.TYPE_NULL);
                return false;
            }
        });

        authorName.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                authorName.showDropDown();
                return false;
            }
        });

        callNumber.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                callNumber.showDropDown();
                return false;
            }
        });

        isbn.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                isbn.showDropDown();
                return false;
            }
        });


        bookName.addTextChangedListener(new TextWatcher() {

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                bookName.setError(null);
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                /*InputMethodManager inputManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE); //hides the keyboard after editing
                inputManager.hideSoftInputFromWindow(bookName.getWindowToken(), 0);*/
                authorName.setError(null);
                callNumber.setError(null);
                isbn.setError(null);
            }
        });

        authorName.addTextChangedListener(new TextWatcher() {

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                authorName.setError(null);
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                bookName.setError(null);
                callNumber.setError(null);
                isbn.setError(null);
            }
        });

        callNumber.addTextChangedListener(new TextWatcher() {

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                callNumber.setError(null);
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                bookName.setError(null);
                authorName.setError(null);
                isbn.setError(null);
            }
        });
        isbn.addTextChangedListener(new TextWatcher() {

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                isbn.setError(null);
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                bookName.setError(null);
                authorName.setError(null);
                callNumber.setError(null);
            }
        });
        /*AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Are you sure?").setPositiveButton("Yes", dialogClickListener)
                .setNegativeButton("No", dialogClickListener).show();*/
    }
    /*@Override
    public boolean onKeyPreIme(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == 1) {
            super.onKeyPreIme(keyCode, event);
            hideKeyboard();
            return true;
        }
        return super.onKeyPreIme(keyCode, event);
    }*/
    static final String[] BOOKTITLES = new String[] { "The best man to die", "Dry bones in the valley",
            "The following girls", "Winterland", "I still dream about you", "The asylum", "Brotherhood of blades", "Eversea",
            "Easy learning english spelling", "O my america", "Tales from the special forces club", "I left my tent in san fransisco",
            "The picador book of wedding poems", "Wish i was there", "Intern nation", "The alternate day diet", "Big bang disruption",
            "Brilliant illustrator cs 6", "Artemis fowl", "Earthfall", "Dark storm", "At the coalface", "Goldfinger"};

    static final String[] AUTHORNAMES = new String[] { "Ruth Rendell", "Tom Bouman", "Louise Levene", "Alan Glynn", "Fannie Flagg",
            "John Harwoord", "Linda Regan", "Natasha Boyd", "Ian Brookes", "Sara Wheeler", "Sean Rayment", "Emma Kennedy",
            "Peter Forbes", "Emily Lloyd", "Ross Perlin", "James Johnson", "Larry Downes", "Steve Johnson",
            "Eoin Colfer", "Mark Walden", "Sarah Singleton", "Catherine Black", "Ian Fleming"};

    static final String[] CALLNUMBERS = new String[] { "823.914F", "AF", "BF", "823.92F", "GEN", "CF", "DF", "EF", "423.1", "973.5092", "940.541", "920.KEN", "808.8193", "791.43028", "650.0715",
            "613.25", "338.064", "006.686", "823.92J", "JF", "TF", "331.4822", "J428.64"};

    static final String[] ISBN = new String[] {"9781409068525", "9780571320646", "9781408842904", "9780571255573", "9780099555483",
            "9780099578840", "9781780295091", "9781472219657", "9780008100810", "9780099541349", "9780007452538", "9780091935962",
            "9780330456869", "9781782197539", "9781844678839", "9780399534904", "9780241003527", "9780273773382", "9781408448854",
            "9781408815663", "9780857070753", "9781471318122", "9781405080604"};

    public void showBookInformation(View v)
    {
        String bt = bookName.getText().toString();
        String an = authorName.getText().toString();
        String cn = callNumber.getText().toString();
        String in = isbn.getText().toString();

        Intent intent = new Intent(IndoorPositioning.this, BookStatus.class);

        bookStatusTwo = bookLocatorNew.getData(bt, an, cn, in);

        if (bt.equals("") && an.equals("") && cn.equals("") && in.equals(""))
        {
            errorMessage();
        }
        else if(!bt.equals("") && !an.equals("") || !bt.equals("") && !cn.equals("") || !bt.equals("") && !in.equals("") ||
                !an.equals("") && !cn.equals("") || !an.equals("") && !in.equals("") || !cn.equals("") && !in.equals(""))
        {
            show("No results found due to multiple inputs.");
        }
        else
        {
            Cursor c=db.rawQuery("select * from Book_Information where Book_Title='" + bt + "' or Author_First_Name || ' ' || Author_Last_Name ='"+an+"' or Call_Number ='" + cn + "' or ISBN ='" + in + "'" ,null);
            c.moveToFirst();
            if(c.getCount()>0)
            {
                //saveNewBookHistory();
                saveHistoryForBI();
                startActivity(intent);
            }
            else {
                show("The book is not in database.");
            }
            c.close();
        }
    }

    public void showIndoorMap(View v)
    {
        String bt=bookName.getText().toString();
        String an=authorName.getText().toString();
        String cn=callNumber.getText().toString();
        String in=isbn.getText().toString();

        if (bt.equals("") && an.equals("") && cn.equals("") && in.equals("")) {
            errorMessage();
        }
        else if (!bt.equals("") && !an.equals("") || !bt.equals("") && !cn.equals("") || !bt.equals("") && !in.equals("") ||
                 !an.equals("") && !cn.equals("") || !an.equals("") && !in.equals("") || !cn.equals("") && !in.equals("")) {
            show("Please only type in one of the search.");
        }
        else {
            Cursor c = db.rawQuery("select * from Book_Information where Book_Title ='" + bt + "' or Author_First_Name || ' ' || Author_Last_Name ='" + an + "' or Call_Number ='" + cn + "' or ISBN ='" + in + "'", null);
            c.moveToFirst();
            if (c.getCount() > 0)
            {
                //saveNewBookHistory();
                saveHistoryForIM();
                showAlertMessage();
                //Message.message(this, bookStatusTwo);
            } else {
                show("The book is not in database.");
            }
            c.close();
        }
    }

    public void showGPS(View v)
    {
        showMap(Uri.parse("geo:51.55877841538128,-0.2811156213283539?q=Wembley Library, Brent Civic Centre, Engineers Way, Wembley"));
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
                startActivity(new Intent(IndoorPositioning.this, Help.class));
                return true;
            case R.id.database:
                startActivity(new Intent(IndoorPositioning.this, AndroidDatabaseManager.class));
                return true;
            case R.id.history:
                startActivity(new Intent(IndoorPositioning.this, SearchHistory.class));
                return true;
            default:
                return false;
        }
    }

    public void showAlertMessage()
    {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(IndoorPositioning.this);
        alertDialog.setTitle("Help");
        alertDialog.setMessage("Do you want to refer to help section before using Indoor Positioning?");

        alertDialog.setPositiveButton("Yes",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        startActivity(new Intent(IndoorPositioning.this, Help.class));
                        dialog.dismiss();
                    }
                });

        alertDialog.setNegativeButton("No",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String bt = bookName.getText().toString();
                        String an = authorName.getText().toString();
                        String cn = callNumber.getText().toString();
                        String in = isbn.getText().toString();
                        String location = bookLocatorNew.getLocationCoordinate(bt, an, cn, in);
                        intent.putExtra("location", location);
                        startActivity(intent);
                    }
                });
        alertDialog.show();
    }

    public void errorMessage()
    {
        bookName.setError("Please type in one of the search");
        authorName.setError("Please type in one of the search");
        callNumber.setError("Please type in one of the search");
        isbn.setError("Please type in one of the search");
    }

    public void showMap(Uri geoLocation)
    {
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setData(geoLocation);
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        }
    }

    public void reset(View v)
    {
        bookName.setText("");
        authorName.setText("");
        callNumber.setText("");
        isbn.setText("");
    }

    public void saveHistoryForBI()
    {
        FeedPref=PreferenceManager.getDefaultSharedPreferences(this);
        fd=FeedPref.edit();
        String bt1 = bookName.getText().toString();
        String an1 = authorName.getText().toString();
        String cn1 = callNumber.getText().toString();
        String in1 = isbn.getText().toString();
        fd.putString("bt1" , bt1);
        fd.putString("an1" , an1);
        fd.putString("cn1" , cn1);
        fd.putString("in1" , in1);
        fd.apply();
    }

    public void saveHistoryForIM()
    {
        FeedPref2=PreferenceManager.getDefaultSharedPreferences(this);
        fd2=FeedPref2.edit();
        String bt2 = bookName.getText().toString();
        String an2 = authorName.getText().toString();
        String cn2 = callNumber.getText().toString();
        String in2 = isbn.getText().toString();
        fd2.putString("bt2" , bt2);
        fd2.putString("an2" , an2);
        fd2.putString("cn2" , cn2);
        fd2.putString("in2" , in2);
        fd2.apply();
    }

    public void show(String str)
    {
        Toast.makeText(this, str, Toast.LENGTH_LONG).show();
    }

   /* public void saveNewBookHistory()
    {
        String bt2 = bookName.getText().toString();
        Cursor c=db.rawQuery("select * from Book_Information where Book_Title ='" + bt2 + "'", null);
        c.moveToFirst();
        c.close();
    }*/
    /*public void saveBookHistory()
    {
        FeedPref=PreferenceManager.getDefaultSharedPreferences(getBaseContext());
        fd=FeedPref.edit();
        String bt1 = bookName.getText().toString();
        fd.putString("bt1" ,bt1);
        fd.apply();
    }
    public void saveAuthorHistory()
    {
        FeedPref2=PreferenceManager.getDefaultSharedPreferences(getBaseContext());
        fd2=FeedPref2.edit();
        String an1 = authorName.getText().toString();
        fd2.putString("an1", an1);
        fd2.apply();
    }
    public void saveNumberHistory()
    {
        FeedPref3=PreferenceManager.getDefaultSharedPreferences(getBaseContext());
        fd3=FeedPref3.edit();
        String cn1 = callNumber.getText().toString();
        fd3.putString("cn1", cn1);
        fd3.apply();
    }
    public void saveisbnHistory()
    {
        FeedPref4=PreferenceManager.getDefaultSharedPreferences(getBaseContext());
        fd4=FeedPref4.edit();
        String in1 = isbn.getText().toString();
        fd4.putString("in1", in1);
        fd4.apply();
    }*/
    /*public void showIndoorMap(View v)
    {

        String bt = bookName.getText().toString();
        String an = authorName.getText().toString();
        String cn = callNumber.getText().toString();
        String in = isbn.getText().toString();

        Intent intent = new Intent(IndoorPositioning.this, AutomaticFloorPlanLoader.class);

        String location = bookLocator.getLocationImage(bt, an, cn, in);

        if(location.equals(""))
        {
            errorMessage();
        }
        else
        {
            intent.putExtra("location", location);
            startActivity(intent);
        }
    }
*/
}


package indoor_positioning_system.ips;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.Network;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.provider.Settings;
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

public class MainActivity extends AppCompatActivity {
    ImageButton bookLocation,bookInformation,libraryLocation,reset;
    AutoCompleteTextView bookName,authorName,callNumber,isbn;
    TextView textView,textView2,textView3,textView4;
    BookLocatorDatabaseAdapter bookLocator; //to access database data
    public String location = "";
    public static String loadbookInformation; //loadSearchHistory
    private String locationCoordinates;
    SharedPreferences.Editor fd;
    SharedPreferences sharedPreferences;
    Intent intent;
    SharedPreferences mPrefs;
    final String alertMessagePref = "alertMessageShown";
    //private static final String STOREVALUES = "myvalues";

    /*The following get and set methods are associated with displaying the pointer image.*/
    /*The following get and set methods shows that they are never used, it is an unexpected behaviour.*/
    public String getLocation()
    {
        return location;
    }

    public void setLocation(String location)
    {
        this.location = location;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        bookLocator = new BookLocatorDatabaseAdapter(this);
        intent = new Intent(MainActivity.this, IndoorPositioningSystem.class);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textView = (TextView) findViewById(R.id.textView);
        textView2 = (TextView) findViewById(R.id.textView2);
        textView3 = (TextView) findViewById(R.id.textView3);
        textView4 = (TextView) findViewById(R.id.textView4);

        bookInformation = (ImageButton) findViewById(R.id.imageButton);
        bookLocation = (ImageButton) findViewById(R.id.imageButtonTwo);
        reset = (ImageButton) findViewById(R.id.imageButtonThree);
        libraryLocation = (ImageButton) findViewById(R.id.imageButtonFour);

        bookName = (AutoCompleteTextView) findViewById(R.id.editText);
        authorName = (AutoCompleteTextView) findViewById(R.id.editTextTwo);
        callNumber = (AutoCompleteTextView) findViewById(R.id.editTextThree);
        isbn = (AutoCompleteTextView) findViewById(R.id.editTextFour);

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, R.layout.list_item, BOOKTITLES);
        ArrayAdapter<String> adapter2 = new ArrayAdapter<>(this, R.layout.list_item, AUTHORNAMES);
        ArrayAdapter<String> adapter3 = new ArrayAdapter<>(this, R.layout.list_item, CALLNUMBERS);
        ArrayAdapter<String> adapter4 = new ArrayAdapter<>(this, R.layout.list_item, ISBN);

        bookName.setAdapter(adapter);
        bookName.setDropDownWidth(getResources().getDisplayMetrics().widthPixels); //increases dropdown width to the size of the screen
        authorName.setAdapter(adapter2);
        callNumber.setAdapter(adapter3);
        isbn.setAdapter(adapter4);

        /*The following four methods display dropdowns for each search field*/
        bookName.setOnTouchListener(new View.OnTouchListener()
        {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                bookName.showDropDown();
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

        /*The following textWatchers are used to remove the search field highlighting
        * and warning the users about search field inputs.*/
        bookName.addTextChangedListener(new TextWatcher() {

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count)
            {
                bookName.setError(null);
                String bt= bookName.getText().toString();
                if(bt.length() > 0 && bt.startsWith(" "))
                {
                    show("Do no enter space in the beginning");
                }
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                String bt= bookName.getText().toString();
                if(bt.length() > 49)
                {
                    show("You have reached the input limit");
                }
                authorName.setError(null);
                callNumber.setError(null);
                isbn.setError(null);
            }
        });

        authorName.addTextChangedListener(new TextWatcher() {

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                authorName.setError(null);
                String an= authorName.getText().toString();
                if(an.length() > 0 && an.startsWith(" "))
                {
                    show("Do no enter space in the beginning");
                }
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                String an= authorName.getText().toString();
                if(an.length() > 39)
                {
                    show("You have reached the input limit");
                }
                bookName.setError(null);
                callNumber.setError(null);
                isbn.setError(null);
            }
        });

        callNumber.addTextChangedListener(new TextWatcher() {

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                callNumber.setError(null);
                String cn= callNumber.getText().toString();
                if(cn.length() > 0 && cn.startsWith(" ")){
                    show("Do no enter space in the beginning");
                }
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                String cn= callNumber.getText().toString();
                if(cn.length() > 9)
                {
                    show("You have reached the input limit");
                }
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
                String in= isbn.getText().toString();
                if(in.length() > 12)
                {
                    show("You have reached the input limit");
                }
                bookName.setError(null);
                authorName.setError(null);
                callNumber.setError(null);
            }
        });
    }

    /*The following four array strings display search suggestion lists.*/
    static final String[] BOOKTITLES = new String[] { "The best man to die", "Dry bones in the valley",
            "The following girls", "Winterland", "I still dream about you", "The asylum", "Brotherhood of blades", "Eversea",
            "Easy learning english spelling", "O my america", "Tales from the special forces club", "I left my tent in san fransisco",
            "The picador book of wedding poems", "Wish i was there", "Intern nation", "The alternate day diet", "Big bang disruption",
            "Brilliant illustrator cs 6", "Artemis fowl", "Earthfall", "Dark storm", "At the coalface", "Goldfinger"};

    static final String[] AUTHORNAMES = new String[] { "Ruth Rendell", "Tom Bouman", "Louise Levene", "Alan Glynn", "Fannie Flagg",
            "John Harwoord", "Linda Regan", "Natasha Boyd", "Ian Brookes", "Sara Wheeler", "Sean Rayment", "Emma Kennedy",
            "Peter Forbes", "Emily Lloyd", "Ross Perlin", "James Johnson", "Larry Downes", "Steve Johnson",
            "Eoin Colfer", "Mark Walden", "Sarah Singleton", "Catherine Black", "Ian Fleming"};

    static final String[] CALLNUMBERS = new String[] { "823.914F", "801.35", "005.312", "823.92F", "F321.34", "501.3", "A342.75",
            "623.31", "423.1", "973.5092", "940.541", "920.KEN", "808.8193", "791.43028", "650.0715",
            "613.25", "338.064", "006.686", "823.92J", "B53.21", "873.07", "331.4822", "J428.64"};

    static final String[] ISBN = new String[] { "9781409068525", "9780571320646", "9781408842904", "9780571255573", "9780099555483",
            "9780099578840", "9781780295091", "9781472219657", "9780008100810", "9780099541349", "9780007452538", "9780091935962",
            "9780330456869", "9781782197539", "9781844678839", "9780399534904", "9780241003527", "9780273773382", "9781408448854",
            "9781408815663", "9780857070753", "9781471318122", "9781405080604"};

    /*The following method resets search fields*/
    public void reset(View v)
    {
        bookName.setText("");
        authorName.setText("");
        callNumber.setText("");
        isbn.setText("");
    }

    /*The following method displays book information from BookInformation class*/
    public void displayBookInformation(View v)
    {
        if(isAirplaneModeOn(getApplicationContext()) & isConnectedToInternet())
        {
            bookInformation();
        }
        else if(!isAirplaneModeOn(getApplicationContext())) {
            bookInformation();
        }
        else if(isAirplaneModeOn(getApplicationContext()))
        {
            show("Please connect to internet or network");
        }
    }

    /*The following method gets called in displayBookInformation method*/
    private void bookInformation()
    {
        String bt = bookName.getText().toString();
        String an = authorName.getText().toString();
        String cn = callNumber.getText().toString();
        String in = isbn.getText().toString();

        Intent intent = new Intent(MainActivity.this, BookInformation.class);

        loadbookInformation = bookLocator.getBookInformation(bt, an, cn, in);

        if (bt.equals("") && an.equals("") && cn.equals("") && in.equals(""))
        {
            errorMessage();
        } else if (!bt.equals("") && !an.equals("") || !bt.equals("") && !cn.equals("") || !bt.equals("") && !in.equals("") ||
                !an.equals("") && !cn.equals("") || !an.equals("") && !in.equals("") || !cn.equals("") && !in.equals("")) {
            show("No results found due to multiple inputs");
        }
        else if (!loadbookInformation.equals("")) {
            startActivity(intent);
            saveHistory();
            //saveSearchHistory();
        } else if (loadbookInformation.equals("")) {
            show("The book is not in database");
        }
    }

    /*The following method displays book location with the help of IndoorPositioningSystem and BlueDotView class*/
    public void displayBookLocation(View v)
    {
        if(isAirplaneModeOn(getApplicationContext()) & isConnectedToInternet()) {
            bookLocation();
        }
        else if(!isAirplaneModeOn(getApplicationContext()))
        {
            bookLocation();
        }
        else if(isAirplaneModeOn(getApplicationContext())) {
            show("Please connect to internet or network");
        }
    }

    /*The following method gets called in displayBookLocation method*/
    private void bookLocation()
    {
        String bt = bookName.getText().toString();
        String an = authorName.getText().toString();
        String cn = callNumber.getText().toString();
        String in = isbn.getText().toString();

        locationCoordinates = bookLocator.getBookLocation(bt, an, cn, in);

        if (bt.equals("") && an.equals("") && cn.equals("") && in.equals(""))
        {
            errorMessage();
        } else if (!bt.equals("") && !an.equals("") || !bt.equals("") && !cn.equals("") || !bt.equals("") && !in.equals("") ||
                !an.equals("") && !cn.equals("") || !an.equals("") && !in.equals("") || !cn.equals("") && !in.equals("")) {
            show("No results found due to multiple inputs");
        } else if (!locationCoordinates.equals("")) {
            showAlertMessage();
            saveHistory();
        } else if (locationCoordinates.equals("")) {
            show("The book is not in database");
        }
    }

    /*The following method displays alertmessage to the user for only one time and gets called in displayBookLocation class*/
    private void showAlertMessage()
    {
        mPrefs = PreferenceManager.getDefaultSharedPreferences(this);
        Boolean alertMessageShown = mPrefs.getBoolean(alertMessagePref, false);
        if(!alertMessageShown)
        {
            AlertDialog.Builder alertDialog = new AlertDialog.Builder(MainActivity.this);
            alertDialog.setTitle("Help");
            alertDialog.setMessage("Do you want to refer to help section before using Indoor Positioning System?");

            alertDialog.setPositiveButton("Yes",
                    new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            startActivity(new Intent(MainActivity.this, Help.class));
                            dialog.dismiss();
                        }
                    });

            alertDialog.setNegativeButton("No",
                    new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            intent.putExtra("location", locationCoordinates);
                            startActivity(intent);
                            dialog.dismiss();
                        }
                    });
            alertDialog.show();
            SharedPreferences.Editor editor = mPrefs.edit();
            editor.putBoolean(alertMessagePref, true);
            editor.apply();
       }
        else
        {
            intent.putExtra("location", locationCoordinates);
            startActivity(intent);
        }
    }

    /*The following method highlights search fields with a message.*/
    private void errorMessage()
    {
        bookName.setError("An input is required in one of the search box");
        authorName.setError("An input is required in one of the search box");
        callNumber.setError("An input is required in one of the search box");
        isbn.setError("An input is required in one of the search box");
    }

    /*The following method displays library location with the help of Google Maps application.*/
    public void displayLibraryLocation(View v) {
        if(isAirplaneModeOn(getApplicationContext()) & isConnectedToInternet())
        {
            showMap(Uri.parse("geo:51.55877841538128,-0.2811156213283539?q=Wembley Library, Brent Civic Centre, Engineers Way, Wembley"));
        }
        else if(!isAirplaneModeOn(getApplicationContext()))
        {
            showMap(Uri.parse("geo:51.55877841538128,-0.2811156213283539?q=Wembley Library, Brent Civic Centre, Engineers Way, Wembley"));
        }
        else if(isAirplaneModeOn(getApplicationContext())) {
            show("Please connect to internet or network");
        }
    }

    /*The following method starts google maps application.*/
    private void showMap(Uri geoLocation)
    {
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setData(geoLocation);
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        }
    }

    /*The following method checks if the the aeroplane mode is on.*/
    @SuppressWarnings("deprecation")
    private static boolean isAirplaneModeOn(Context context) {
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.JELLY_BEAN_MR1){
            return Settings.Global.getInt(context.getContentResolver(), Settings.Global.AIRPLANE_MODE_ON, 0) != 0;
        } else {
            return Settings.System.getInt(context.getContentResolver(), Settings.System.AIRPLANE_MODE_ON, 0) != 0;
        }
    }

    /*The following method checks if the smartphone has internet connection.*/
    public boolean isConnectedToInternet()
    {
        ConnectivityManager connectivityManager = (ConnectivityManager) this.getSystemService(Context.CONNECTIVITY_SERVICE);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Network[] networks = connectivityManager.getAllNetworks();
            NetworkInfo networkInfo;
            for (Network mNetwork : networks) {
                networkInfo = connectivityManager.getNetworkInfo(mNetwork);
                if (networkInfo.getState().equals(NetworkInfo.State.CONNECTED)) {
                    return true;
                }
            }
        }
        else {
            if (connectivityManager != null) {
                NetworkInfo[] info = connectivityManager.getAllNetworkInfo();
                if (info != null) {
                    for (NetworkInfo anInfo : info) {
                        if (anInfo.getState() == NetworkInfo.State.CONNECTED) {
                            return true;
                        }
                    }
                }
            }
        }
       return false;
    }

    /*The following method saves search history and only gets called through displayBookInformation and displayLibraryLocation methods.*/
    private void saveHistory()
    {
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        fd=sharedPreferences.edit();

        String bt = bookName.getText().toString();
        String an = authorName.getText().toString();
        String cn = callNumber.getText().toString();
        String in = isbn.getText().toString();

        fd.putString("bt" , bt);
        fd.putString("an" , an);
        fd.putString("cn" , cn);
        fd.putString("in" , in);
        /*Set<String> faveSet = sharedPreferences.getStringSet(STOREVALUES, new HashSet<String>());
        faveSet.add(bt + "::" + an + "::" + cn + "::" + in + ",");
        fd.putStringSet(STOREVALUES, faveSet);*/
        fd.apply();
    }
    /*private void saveSearchHistory()
    {
        String bt = bookName.getText().toString();
        loadSearchHistory = bookLocator.getSearchHistory(bt);
    }*/

    /*The following method is defined to display a message rather than using toast.maketext every time*/
    private void show(String str)
    {
        Toast.makeText(this, str, Toast.LENGTH_LONG).show();
    }

    /*The following method creates options menu in the action bar.*/
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        return true;
    }

    /*The following method displays options in the options menu.*/
    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        switch (item.getItemId()) {
            case R.id.help:
                startActivity(new Intent(MainActivity.this, Help.class));
                return true;
            /*case R.id.database:
                startActivity(new Intent(MainActivity.this, AndroidDatabaseManager.class));
                return true;*/
            /*case R.id.history:
                startActivity(new Intent(MainActivity.this, SearchHistory.class));
                return true;*/
            default:
                return false;
        }
    }
}
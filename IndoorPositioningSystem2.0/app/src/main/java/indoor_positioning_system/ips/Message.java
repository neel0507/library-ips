/*
package indoor_positioning_system.indoorpositioningsystem20;

import android.content.Context;
import android.widget.Toast;

public class Message
{
    public static void message(Context context, String message)
    {
        Toast.makeText(context, message, Toast.LENGTH_LONG).show();
    }
}
*/
/*public String getLocationImage(String bt, String an, String cn, String in )    //Important method
    {
        db = helper.getWritableDatabase();
        String[] columns={BookLocatorDatabase.locationImage};
        String[] selectionArgs= {bt,an,cn,in};
        Cursor cursor = db.query
                (BookLocatorDatabase.tableName, columns,
                        BookLocatorDatabase.bookTitle + " =? OR " + BookLocatorDatabase.authorFN + "|| ' ' ||" + BookLocatorDatabase.authorLN + " =? OR " + BookLocatorDatabase.callNum + " =? OR " + BookLocatorDatabase.isbn + " =?",
                        selectionArgs, null, null, null, null);


        StringBuilder buffer = new StringBuilder();
        while(cursor.moveToNext())
        {
            int index0 = cursor.getColumnIndex(BookLocatorDatabase.locationImage);
            String bs=cursor.getString(index0);
            buffer.append(bs).append("\n");
            //cursor.close();
        }
        return  buffer.toString();
    }*/


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


/*public int deleteRow()
    {
        db= helper.getWritableDatabase();
        String[] whereArgs={"abc", "def", "xyz", "852"};
        int count =db.delete(BookLocatorDatabase.tableName, BookLocatorDatabase.bookTitle + " =? AND " + BookLocatorDatabase.authorFN +" =? AND " + BookLocatorDatabase.callNum +" =? AND " + BookLocatorDatabase.isbn + " =?", whereArgs);
        return  count;

    }*/


/*final String floorPlanId = getString(R.string.indooratlas_floor_plan_id);
        if (!TextUtils.isEmpty(floorPlanId)) {
            final IALocation location = IALocation.from(IARegion.floorPlan(floorPlanId));
            mIALocationManager.setLocation(location);
        }*/


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

//bitmap = getBitmapFromURL("http://i.imgur.com/oz6np8M.png?1");
//Intent intent2 = getIntent();
//String location2 = intent2.getExtras().getString(String.valueOf(bitmap));
//mImageView.setLocation(String.valueOf(bitmap));     //Do not use setImage.



/* String[] selectionArgs= {bt,an,cn,in};
            Cursor c = db.rawQuery("select Book_Information_Link from Book_Information where Book_Title = ? or Author_First_Name || ' ' || Author_Last_Name = ? or Call_Number = ? or ISBN = ? ", selectionArgs);
            c.moveToFirst();*/
            /*if(c.getCount()>0)*/
//{
//startActivity(intent);
  //      saveHistoryForBI();
//}
            /*else
            {
                show("The book is not in database.");
            }*/
//c.close();




/*
String[] selectionArgs= {bt,an,cn,in};
        Cursor c = db.rawQuery("select Location from Book_Information where Book_Title = ? or Author_First_Name || ' ' || Author_Last_Name = ? or Call_Number = ? or ISBN = ? ", selectionArgs);
        c.moveToFirst();
        if (c.getCount() > 0)
        {
        showAlertMessage();
        saveHistoryForIM();
        } else {
        show("The book is not in database.");
        }
        c.close();*/


//BookLocatorDatabaseAdapter.BookLocatorDatabase bookLocatorDatabase; // to access the database and table and the data
//SQLiteDatabase db;
//bookLocatorDatabase = new BookLocatorDatabaseAdapter.BookLocatorDatabase(MainActivity.this);
//db=bookLocatorDatabase.getWritableDatabase();

/*bt = bookName.getText().toString();
        an = authorName.getText().toString();
        cn = callNumber.getText().toString();
        in = isbn.getText().toString();*/


/*public String getSearchHistory(String bt, String an, String cn, String in)
    {
        db = helper.getWritableDatabase();

        String[] columns = {BookLocatorDatabase.BOOKTITLE, BookLocatorDatabase.AUTHORFN, BookLocatorDatabase.AUTHORLN, BookLocatorDatabase.CALLNUMBER, BookLocatorDatabase.ISBN};
        String[] selectionArgs= {bt,an,cn,in};
        Cursor cursor = db.query
                (BookLocatorDatabase.TABLENAME, columns,
                        BookLocatorDatabase.BOOKTITLE + " =? OR " + BookLocatorDatabase.AUTHORFN + "|| ' ' ||" + BookLocatorDatabase.AUTHORLN + " =? OR " + BookLocatorDatabase.CALLNUMBER + " =? OR " + BookLocatorDatabase.ISBN + " =?",
                        selectionArgs, null, null, null, null);

        StringBuilder buffer = new StringBuilder();
        while(cursor.moveToNext())
        {
            int index0 = cursor.getColumnIndex(BookLocatorDatabase.BOOKTITLE);
            String bte=cursor.getString(index0);

            int index1 = cursor.getColumnIndex(BookLocatorDatabase.AUTHORFN);
            String fn = cursor.getString(index1);

            int index2 = cursor.getColumnIndex(BookLocatorDatabase.AUTHORLN);
            String ln = cursor.getString(index2);

            int index3 = cursor.getColumnIndex(BookLocatorDatabase.CALLNUMBER);
            String cnr = cursor.getString(index3);

            int index4 = cursor.getColumnIndex(BookLocatorDatabase.ISBN);
            String isbn = cursor.getString(index4);

            buffer.append(bte).append(" ").append(fn).append(" ").append(ln).append(" ").append(cnr).append(" ").append(isbn).append("\n");
        }
        cursor.close();
        return  buffer.toString();
        //return db.query(BookLocatorDatabase.TABLENAME,columns,null, null, null, null, null);
    }*/



//ListView listView;
//ArrayAdapter<String> adapter;
//ArrayList<String> searchHistory = new ArrayList<>();



//String loadSearchHistory = MainActivity.loadSearchHistory;
//searchHistory.clear();
//searchHistory.add(loadSearchHistory);
//adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, searchHistory);
//listView.setAdapter(adapter);

    /*private void saveSearchHistory()
    {
        String bt = bookName.getText().toString();
        String an = authorName.getText().toString();
        String cn = callNumber.getText().toString();
        String in = isbn.getText().toString();

        loadSearchHistory = bookLocator.getSearchHistory(bt,an,cn,in);
    }*/
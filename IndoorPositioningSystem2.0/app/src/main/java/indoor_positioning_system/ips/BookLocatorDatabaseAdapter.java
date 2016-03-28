package indoor_positioning_system.ips;

import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

public class BookLocatorDatabaseAdapter
{
    BookLocatorDatabase helper;
    SQLiteDatabase db;

    public BookLocatorDatabaseAdapter(Context context)
    {
        helper = new BookLocatorDatabase(context);
    }
    public static void message(Context context, String message)
    {
        Toast.makeText(context, message, Toast.LENGTH_LONG).show();
    }


    /*public String getSearchHistory(String bt)
    {
        db = helper.getWritableDatabase();

        String[] columns = {BookLocatorDatabase.BOOKTITLE};
        String[] selectionArgs= {bt};
        Cursor cursor = db.query
                (BookLocatorDatabase.TABLENAME, columns,
                        BookLocatorDatabase.BOOKTITLE + " =?",
                        selectionArgs, null, null, null, null);

        StringBuilder buffer = new StringBuilder();
        while(cursor.moveToNext())
        {
            int index0 = cursor.getColumnIndex(BookLocatorDatabase.BOOKTITLE);
            String bte=cursor.getString(index0);

            buffer.append(bte).append("\n");
        }
        cursor.close();
        return  buffer.toString();
    }*/


    /*The following method returns book location based on either of the four search inputs*/
    public String getBookLocation(String bt, String an, String cn, String in )
    {
        db = helper.getWritableDatabase();

        String[] columns={BookLocatorDatabase.LOCATIONCOORDINATES};
        String[] selectionArgs= {bt,an,cn,in};
        //Cursor cursor = db.rawQuery("select Location from Book_Information where Book_Title = ? or Author_First_Name || ' ' || Author_Last_Name = ? or Call_Number = ? or ISBN = ? ", selectionArgs);
        Cursor cursor = db.query
                (BookLocatorDatabase.TABLENAME, columns,
                        BookLocatorDatabase.BOOKTITLE + " =? OR " + BookLocatorDatabase.AUTHORFN + "|| ' ' ||" + BookLocatorDatabase.AUTHORLN + " =? OR " + BookLocatorDatabase.CALLNUMBER + " =? OR " + BookLocatorDatabase.ISBN + " =?",
                        selectionArgs, null, null, null, null);

        StringBuilder buffer = new StringBuilder();
        while(cursor.moveToNext())
        {
            int index0 = cursor.getColumnIndex(BookLocatorDatabase.LOCATIONCOORDINATES);
            String lc=cursor.getString(index0);
            buffer.append(lc).append("\n");
        }
        cursor.close();
        return  buffer.toString();
    }

    /*The following method returns book information based on either of the four search inputs*/
    public String getBookInformation(String bt, String an, String cn, String in )
    {
        db = helper.getWritableDatabase();

        String[] columns={BookLocatorDatabase.BOOKINFORMATION};
        String[] selectionArgs= {bt,an,cn,in};
        //Cursor cursor = db.rawQuery("select Book_Information_Link from Book_Information where Book_Title = ? or Author_First_Name || ' ' || Author_Last_Name = ? or Call_Number = ? or ISBN = ? ", selectionArgs);
        Cursor cursor = db.query
                (BookLocatorDatabase.TABLENAME, columns,
                BookLocatorDatabase.BOOKTITLE + " =? OR " + BookLocatorDatabase.AUTHORFN + "|| ' ' ||" + BookLocatorDatabase.AUTHORLN + " =? OR " + BookLocatorDatabase.CALLNUMBER + " =? OR " + BookLocatorDatabase.ISBN + " =?",
                selectionArgs, null, null, null, null);

        StringBuilder buffer = new StringBuilder();
        while(cursor.moveToNext())
        {
            int index0 = cursor.getColumnIndex(BookLocatorDatabase.BOOKINFORMATION);
            String bi=cursor.getString(index0);
            buffer.append(bi).append("\n");
        }
        cursor.close();
        return  buffer.toString();
    }

   static class BookLocatorDatabase extends SQLiteOpenHelper
    {
        private static final String DBNAME = "BookLocator.db";
        private static final String TABLENAME = "Book_Information";
        private static final String UID = "_bookID";
        private static final String BOOKTITLE = "Book_Title";
        private static final String AUTHORFN = "Author_First_Name";
        private static final String AUTHORLN = "Author_Last_Name";
        private static final String CALLNUMBER = "Call_Number";
        private static final String ISBN = "ISBN";
        private static final String BOOKINFORMATION = "Book_Information_Link";
        private static final String LOCATIONCOORDINATES = "Location";
        private static final int dbVersion=5;  //increment the value if any change takes place inside the database
        private Context context;

        public BookLocatorDatabase (Context context)
        {
            super(context, DBNAME, null, dbVersion);
            this.context = context;
            //message(context, "constructor called");
        }
        @Override
        public void onCreate(SQLiteDatabase db)
        {
            try
            {
                db.execSQL("CREATE TABLE IF NOT EXISTS " + TABLENAME + " (" + UID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + BOOKTITLE + " VARCHAR(50) NOT NULL, " + AUTHORFN + " VARCHAR(20) NOT NULL, " + AUTHORLN + " VARCHAR(20) NOT NULL, " + CALLNUMBER + " VARCHAR(10) NOT NULL, " + ISBN + " INTEGER(13) NOT NULL, " + BOOKINFORMATION + " VARCHAR(30) NOT NULL, " + LOCATIONCOORDINATES + " VARCHAR(10) NOT NULL);");
                //message(context, "OnCreate called");

                db.execSQL("INSERT OR IGNORE INTO " + TABLENAME + " (Book_Title, Author_First_Name, Author_Last_Name, Call_Number, ISBN, Book_Information_Link, Location) VALUES('The best man to die'                    , 'Ruth'        , 'Rendell'     , '823.914F'    , 9781409068525     , 'https://goo.gl/FOjpM7'   , '253,457' )");
                db.execSQL("INSERT OR IGNORE INTO " + TABLENAME + " (Book_Title, Author_First_Name, Author_Last_Name, Call_Number, ISBN, Book_Information_Link, Location) VALUES('Dry bones in the valley'                , 'Tom'         , 'Bouman'      , '801.35'      , 9780571320646     , 'https://goo.gl/BLGWdm'   , '278,457' )");
                db.execSQL("INSERT OR IGNORE INTO " + TABLENAME + " (Book_Title, Author_First_Name, Author_Last_Name, Call_Number, ISBN, Book_Information_Link, Location) VALUES('The following girls'                    , 'Louise'      , 'Levene'      , '005.312'     , 9781408842904     , 'https://goo.gl/7YNcC9'   , '358,457' )");
                db.execSQL("INSERT OR IGNORE INTO " + TABLENAME + " (Book_Title, Author_First_Name, Author_Last_Name, Call_Number, ISBN, Book_Information_Link, Location) VALUES('Winterland'                             , 'Alan'        , 'Glynn'       , '823.92F'     , 9780571255573     , 'https://goo.gl/zT30cj'   , '408,457' )");
                db.execSQL("INSERT OR IGNORE INTO " + TABLENAME + " (Book_Title, Author_First_Name, Author_Last_Name, Call_Number, ISBN, Book_Information_Link, Location) VALUES('I still dream about you'                , 'Fannie'      , 'Flagg'       , 'F321.34'     , 9780099555483     , 'https://goo.gl/KhCsGj'   , '650,457' )");
                db.execSQL("INSERT OR IGNORE INTO " + TABLENAME + " (Book_Title, Author_First_Name, Author_Last_Name, Call_Number, ISBN, Book_Information_Link, Location) VALUES('The asylum'                             , 'John'        , 'Harwood'     , '501.3'       , 9780099578840     , 'https://goo.gl/cM7KNG'   , '623,457' )");
                db.execSQL("INSERT OR IGNORE INTO " + TABLENAME + " (Book_Title, Author_First_Name, Author_Last_Name, Call_Number, ISBN, Book_Information_Link, Location) VALUES('Brotherhood of blades'                  , 'Linda'       , 'Regan'       , 'A342.75'     , 9781780295091     , 'https://goo.gl/nY5qc0'   , '776,457' )");
                db.execSQL("INSERT OR IGNORE INTO " + TABLENAME + " (Book_Title, Author_First_Name, Author_Last_Name, Call_Number, ISBN, Book_Information_Link, Location) VALUES('Eversea'                                , 'Natasha'     , 'Boyd'        , '623.31'      , 9781472219657     , 'https://goo.gl/Qc1PQU'   , '756,457' )");
                db.execSQL("INSERT OR IGNORE INTO " + TABLENAME + " (Book_Title, Author_First_Name, Author_Last_Name, Call_Number, ISBN, Book_Information_Link, Location) VALUES('Easy learning english spelling'         , 'Ian'         , 'Brookes'     , '423.1'       , 9780008100810     , 'https://goo.gl/c0cEhK'   , '692,562' )");
                db.execSQL("INSERT OR IGNORE INTO " + TABLENAME + " (Book_Title, Author_First_Name, Author_Last_Name, Call_Number, ISBN, Book_Information_Link, Location) VALUES('O my america'                           , 'Sara'        , 'Wheeler'     , '973.5092'    , 9780099541349     , 'https://goo.gl/2Sq2P6'   , '740,592' )");
                db.execSQL("INSERT OR IGNORE INTO " + TABLENAME + " (Book_Title, Author_First_Name, Author_Last_Name, Call_Number, ISBN, Book_Information_Link, Location) VALUES('Tales from the special forces club'     , 'Sean'        , 'Rayment'     , '940.541'     , 9780007452538     , 'https://goo.gl/27ntTE'   , '633,662' )");
                db.execSQL("INSERT OR IGNORE INTO " + TABLENAME + " (Book_Title, Author_First_Name, Author_Last_Name, Call_Number, ISBN, Book_Information_Link, Location) VALUES('I left my tent in san fransisco'        , 'Emma'        , 'Kennedy'     , '920.KEN'     , 9780091935962     , 'https://goo.gl/VLSFGe'   , '647,677' )");
                db.execSQL("INSERT OR IGNORE INTO " + TABLENAME + " (Book_Title, Author_First_Name, Author_Last_Name, Call_Number, ISBN, Book_Information_Link, Location) VALUES('The picador book of wedding poems'      , 'Peter'       , 'Forbes'      , '808.8193'    , 9780330456869     , 'https://goo.gl/CX07bx'   , '516,672' )");
                db.execSQL("INSERT OR IGNORE INTO " + TABLENAME + " (Book_Title, Author_First_Name, Author_Last_Name, Call_Number, ISBN, Book_Information_Link, Location) VALUES('Wish i was there'                       , 'Emily'       , 'Lloyd'       , '791.43028'   , 9781782197539     , 'https://goo.gl/jcpn3S'   , '516,727' )");
                db.execSQL("INSERT OR IGNORE INTO " + TABLENAME + " (Book_Title, Author_First_Name, Author_Last_Name, Call_Number, ISBN, Book_Information_Link, Location) VALUES('Intern nation'                          , 'Ross'        , 'Perlin'      , '650.0715'    , 9781844678839     , 'https://goo.gl/TVMKQn'   , '413,642' )");
                db.execSQL("INSERT OR IGNORE INTO " + TABLENAME + " (Book_Title, Author_First_Name, Author_Last_Name, Call_Number, ISBN, Book_Information_Link, Location) VALUES('The alternate day diet'                 , 'James'       , 'Johnson'     , '613.25'      , 9780399534904     , 'https://goo.gl/dQkEmx'   , '398,667' )");
                db.execSQL("INSERT OR IGNORE INTO " + TABLENAME + " (Book_Title, Author_First_Name, Author_Last_Name, Call_Number, ISBN, Book_Information_Link, Location) VALUES('Big bang disruption'                    , 'Larry'       , 'Downes'      , '338.064'     , 9780241003527     , 'https://goo.gl/6Ir5e9'   , '290,587' )");
                db.execSQL("INSERT OR IGNORE INTO " + TABLENAME + " (Book_Title, Author_First_Name, Author_Last_Name, Call_Number, ISBN, Book_Information_Link, Location) VALUES('Brilliant illustrator cs 6'             , 'Steve'       , 'Johnson'     , '006.686'     , 9780273773382     , 'https://goo.gl/pP6p2x'   , '338,560' )");
                db.execSQL("INSERT OR IGNORE INTO " + TABLENAME + " (Book_Title, Author_First_Name, Author_Last_Name, Call_Number, ISBN, Book_Information_Link, Location) VALUES('Artemis fowl'                           , 'Eoin'        , 'Colfer'      , '823.92J'     , 9781408448854     , 'https://goo.gl/VCH5Sp'   , '317,807' )");
                db.execSQL("INSERT OR IGNORE INTO " + TABLENAME + " (Book_Title, Author_First_Name, Author_Last_Name, Call_Number, ISBN, Book_Information_Link, Location) VALUES('Earthfall'                              , 'Mark'        , 'Walden'      , 'B53.21'      , 9781408815663     , 'https://goo.gl/ybmeg1'   , '353,767' )");
                db.execSQL("INSERT OR IGNORE INTO " + TABLENAME + " (Book_Title, Author_First_Name, Author_Last_Name, Call_Number, ISBN, Book_Information_Link, Location) VALUES('Dark storm'                             , 'Sarah'       , 'Singleton'   , '873.07'      , 9780857070753     , 'https://goo.gl/tTgZ3d'   , '516,877' )");
                db.execSQL("INSERT OR IGNORE INTO " + TABLENAME + " (Book_Title, Author_First_Name, Author_Last_Name, Call_Number, ISBN, Book_Information_Link, Location) VALUES('At the coalface'                        , 'Catherine'   , 'Black'       , '331.4822'    , 9781471318122     , 'https://goo.gl/8g6MYA'   , '538,902' )");
                db.execSQL("INSERT OR IGNORE INTO " + TABLENAME + " (Book_Title, Author_First_Name, Author_Last_Name, Call_Number, ISBN, Book_Information_Link, Location) VALUES('Goldfinger'                             , 'Ian'         , 'Fleming'     , 'J428.64'     , 9781405080604     , 'https://goo.gl/PMdc2j'   , '668,774' )");
            }
            catch(SQLException e) {
                message(context, "" +e);
            }
        }
        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion)
        {
            try
            {
                //message(context, "OnUpgrade called");
                db.execSQL("DROP TABLE IF EXISTS " + TABLENAME + " ");
                onCreate(db);
            }
            catch (SQLException e)
            {
                message(context, "" + e);
            }
        }
       /* public ArrayList<Cursor> getData(String Query)
        {
            //get writable database
            SQLiteDatabase sqlDB = this.getWritableDatabase();
            String[] columns = new String[] { "message" };
            //an array list of cursor to save two cursors one has results from the query
            //other cursor stores error message if any errors are triggered
            ArrayList<Cursor> alc = new ArrayList<>(2);
            MatrixCursor Cursor2= new MatrixCursor(columns);
            alc.add(null);
            alc.add(null);


            try{
                //execute the query results will be save in Cursor c
                Cursor c = sqlDB.rawQuery(Query, null);


                //add value to cursor2
                Cursor2.addRow(new Object[] { "Success" });

                alc.set(1,Cursor2);
                if (null != c && c.getCount() > 0) {


                    alc.set(0,c);
                    c.moveToFirst();

                    return alc ;
                }
                return alc;
            } catch(SQLException sqlEx){
                Log.d("printing exception", sqlEx.getMessage());
                //if any exceptions are triggered save the error message to cursor an return the arraylist
                Cursor2.addRow(new Object[] { ""+sqlEx.getMessage() });
                alc.set(1,Cursor2);
                return alc;
            } catch(Exception ex){

                Log.d("printing exception", ex.getMessage());

                //if any exceptions are triggered save the error message to cursor an return the arraylist
                Cursor2.addRow(new Object[] { ""+ex.getMessage() });
                alc.set(1,Cursor2);
                return alc;
            }


        }*/
    }
}

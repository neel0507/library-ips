package indoor_positioning_system.indoorpositioningsystem20;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.MatrixCursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;

public class BookLocatorDatabaseAdapter
{
    BookLocatorDatabase helper;
    SQLiteDatabase db;
    public BookLocatorDatabaseAdapter(Context context)
    {
        helper = new BookLocatorDatabase(context);
    }
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

    public String getLocationCoordinate(String bt, String an, String cn, String in )
    {
        db = helper.getWritableDatabase();
        String[] columns={BookLocatorDatabase.locationCoordinates};
        String[] selectionArgs= {bt,an,cn,in};
        //or Author_First_Name = ? or Call_Number = ? or ISBN = ?
        //Cursor cursor = db.rawQuery("select Book_Status_Link from Book_Information where Book_Title = ? or Author_First_Name || ' ' || Author_Last_Name = ? or Call_Number = ? or ISBN = ? ", selectionArgs);
        Cursor cursor = db.query
                (BookLocatorDatabase.tableName, columns,
                        BookLocatorDatabase.bookTitle + " =? OR " + BookLocatorDatabase.authorFN + "|| ' ' ||" + BookLocatorDatabase.authorLN + " =? OR " + BookLocatorDatabase.callNum + " =? OR " + BookLocatorDatabase.isbn + " =?",
                        selectionArgs, null, null, null, null);


        StringBuilder buffer = new StringBuilder();
        while(cursor.moveToNext())
        {
            int index0 = cursor.getColumnIndex(BookLocatorDatabase.locationCoordinates);
            String bs=cursor.getString(index0);
            buffer.append(bs).append("\n");
            //cursor.close();
        }
        return  buffer.toString();
    }

    public String getData(String bt, String an, String cn, String in )
    {
        db = helper.getWritableDatabase();

        String[] columns={BookLocatorDatabase.bookInformation};
        String[] selectionArgs= {bt,an,cn,in};
        //or Author_First_Name = ? or Call_Number = ? or ISBN = ?
        //Cursor cursor = db.rawQuery("select Book_Status_Link from Book_Information where Book_Title = ? or Author_First_Name || ' ' || Author_Last_Name = ? or Call_Number = ? or ISBN = ? ", selectionArgs);
        Cursor cursor = db.query
                (BookLocatorDatabase.tableName, columns,
                BookLocatorDatabase.bookTitle + " =? OR " + BookLocatorDatabase.authorFN + "|| ' ' ||" + BookLocatorDatabase.authorLN + " =? OR " + BookLocatorDatabase.callNum + " =? OR " + BookLocatorDatabase.isbn + " =?",
                selectionArgs, null, null, null, null);


        StringBuilder buffer = new StringBuilder();
        while(cursor.moveToNext())
        {
            int index0 = cursor.getColumnIndex(BookLocatorDatabase.bookInformation);
            String bs=cursor.getString(index0);
            buffer.append(bs).append("\n");
            //cursor.close();
        }
        return  buffer.toString();
    }

    /*public int deleteRow()
    {
        db= helper.getWritableDatabase();
        String[] whereArgs={"abc", "def", "xyz", "852"};
        int count =db.delete(BookLocatorDatabase.tableName, BookLocatorDatabase.bookTitle + " =? AND " + BookLocatorDatabase.authorFN +" =? AND " + BookLocatorDatabase.callNum +" =? AND " + BookLocatorDatabase.isbn + " =?", whereArgs);
        return  count;

    }*/
   static class BookLocatorDatabase extends SQLiteOpenHelper
    {
        private static final String dbName = "BookLocatorNew.db";
        private static final String tableName = "Book_Information";
        private static final String uID = "_bookID";
        private static final String bookTitle = "Book_Title";
        private static final String authorFN = "Author_First_Name";
        private static final String authorLN = "Author_Last_Name";
        private static final String callNum = "Call_Number";
        private static final String isbn = "ISBN";
        private static final String bookInformation = "Book_Information_Link";
        private static final String locationCoordinates = "Location";
        private static final String locationImage = "Location_Image";
        private static final int dbVersion=19;
        private Context context;
        public BookLocatorDatabase (Context context)
        {
            super(context, dbName, null, dbVersion);
            this.context = context;
            //Message.message(context, "constructor called");
        }
        @Override
        public void onCreate(SQLiteDatabase db)
        {
            try
            {

                db.execSQL("CREATE TABLE IF NOT EXISTS " + tableName + " (" + uID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + bookTitle + " VARCHAR(255), " + authorFN + " VARCHAR(255), " + authorLN + " VARCHAR(255), " + callNum + " VARCHAR(255), " + isbn + " INTEGER, " + bookInformation + " VARCHAR(255), " + locationCoordinates + " VARCHAR(255), " + locationImage + " VARCHAR(255));");
                Message.message(context, "OnCreate called");

                db.execSQL("INSERT OR IGNORE INTO " + tableName + " VALUES( 1,     'The best man to die'                    , 'Ruth'        , 'Rendell'     , '823.914F'    , 9781409068525     , 'https://goo.gl/FOjpM7'   , '240,520'     ,   '    '       )");
                db.execSQL("INSERT OR IGNORE INTO " + tableName + " VALUES( 2,     'Dry bones in the valley'                , 'Tom'         , 'Bouman'      , 'AF'          , 9780571320646     , 'https://goo.gl/BLGWdm'   , '265,520'     ,   '    '       )");
                db.execSQL("INSERT OR IGNORE INTO " + tableName + " VALUES( 3,     'The following girls'                    , 'Louise'      , 'Levene'      , 'BF'          , 9781408842904     , 'https://goo.gl/7YNcC9'   , '345,520'     ,   '    '       )");
                db.execSQL("INSERT OR IGNORE INTO " + tableName + " VALUES( 4,     'Winterland'                             , 'Alan'        , 'Glynn'       , '823.92F'     , 9780571255573     , 'https://goo.gl/zT30cj'   , '395,520'     ,   '    '       )");
                db.execSQL("INSERT OR IGNORE INTO " + tableName + " VALUES( 5,     'I still dream about you'                , 'Fannie'      , 'Flagg'       , 'GEN'         , 9780099555483     , 'https://goo.gl/KhCsGj'   , '637,520'     ,   '    '       )");
                db.execSQL("INSERT OR IGNORE INTO " + tableName + " VALUES( 6,     'The asylum'                             , 'John'        , 'Harwood'     , 'CF'          , 9780099578840     , 'https://goo.gl/cM7KNG'   , '610,520'     ,   '    '       )");
                db.execSQL("INSERT OR IGNORE INTO " + tableName + " VALUES( 7,     'Brotherhood of blades'                  , 'Linda'       , 'Regan'       , 'DF'          , 9781780295091     , 'https://goo.gl/nY5qc0'   , '763,520'     ,   '    '       )");
                db.execSQL("INSERT OR IGNORE INTO " + tableName + " VALUES( 8,     'Eversea'                                , 'Natasha'     , 'Boyd'        , 'EF'          , 9781472219657     , 'https://goo.gl/Qc1PQU'   , '743,520'     ,   '    '       )");
                db.execSQL("INSERT OR IGNORE INTO " + tableName + " VALUES( 9,     'Easy learning english spelling'         , 'Ian'         , 'Brookes'     , '423.1'       , 9780008100810     , 'https://goo.gl/c0cEhK'   , '679,625'     ,   '    '       )");
                db.execSQL("INSERT OR IGNORE INTO " + tableName + " VALUES( 10,    'O my america'                           , 'Sara'        , 'Wheeler'     , '973.5092'    , 9780099541349     , 'https://goo.gl/2Sq2P6'   , '727,655'     ,   '    '       )");
                db.execSQL("INSERT OR IGNORE INTO " + tableName + " VALUES( 11,    'Tales from the special forces club'     , 'Sean'        , 'Rayment'     , '940.541'     , 9780007452538     , 'https://goo.gl/27ntTE'   , '620,725'     ,   '    '       )");
                db.execSQL("INSERT OR IGNORE INTO " + tableName + " VALUES( 12,    'I left my tent in san fransisco'        , 'Emma'        , 'Kennedy'     , '920.KEN'     , 9780091935962     , 'https://goo.gl/VLSFGe'   , '634,750'     ,   '    '       )");
                db.execSQL("INSERT OR IGNORE INTO " + tableName + " VALUES( 13,    'The picador book of wedding poems'      , 'Peter'       , 'Forbes'      , '808.8193'    , 9780330456869     , 'https://goo.gl/CX07bx'   , '503,735'     ,   '    '       )");
                db.execSQL("INSERT OR IGNORE INTO " + tableName + " VALUES( 14,    'Wish i was there'                       , 'Emily'       , 'Lloyd'       , '791.43028'   , 9781782197539     , 'https://goo.gl/jcpn3S'   , '503,790'     ,   '    '       )");
                db.execSQL("INSERT OR IGNORE INTO " + tableName + " VALUES( 15,    'Intern nation'                          , 'Ross'        , 'Perlin'      , '650.0715'    , 9781844678839     , 'https://goo.gl/TVMKQn'   , '400,705'     ,   '    '       )");
                db.execSQL("INSERT OR IGNORE INTO " + tableName + " VALUES( 16,    'The alternate day diet'                 , 'James'       , 'Johnson'     , '613.25'      , 9780399534904     , 'https://goo.gl/dQkEmx'   , '385,730'     ,   '    '       )");
                db.execSQL("INSERT OR IGNORE INTO " + tableName + " VALUES( 17,    'Big bang disruption'                    , 'Larry'       , 'Downes'      , '338.064'     , 9780241003527     , 'https://goo.gl/6Ir5e9'   , '277,650'     ,   '    '       )");
                db.execSQL("INSERT OR IGNORE INTO " + tableName + " VALUES( 18,    'Brilliant illustrator cs 6'             , 'Steve'       , 'Johnson'     , '006.686'     , 9780273773382     , 'https://goo.gl/pP6p2x'   , '325,623'     ,   '    '       )");
                db.execSQL("INSERT OR IGNORE INTO " + tableName + " VALUES( 19,    'Artemis fowl'                           , 'Eoin'        , 'Colfer'      , '823.92J'     , 9781408448854     , 'https://goo.gl/VCH5Sp'   , '304,870'     ,   '    '       )");
                db.execSQL("INSERT OR IGNORE INTO " + tableName + " VALUES( 20,    'Earthfall'                              , 'Mark'        , 'Walden'      , 'JF'          , 9781408815663     , 'https://goo.gl/ybmeg1'   , '340,830'     ,   '    '       )");
                db.execSQL("INSERT OR IGNORE INTO " + tableName + " VALUES( 21,    'Dark storm'                             , 'Sarah'       , 'Singleton'   , 'TF'          , 9780857070753     , 'https://goo.gl/tTgZ3d'   , '503,940'     ,   '    '       )");
                db.execSQL("INSERT OR IGNORE INTO " + tableName + " VALUES( 22,    'At the coalface'                        , 'Catherine'   , 'Black'       , '331.4822'    , 9781471318122     , 'https://goo.gl/8g6MYA'   , '525,965'     ,   '    '       )");
                db.execSQL("INSERT OR IGNORE INTO " + tableName + " VALUES( 23,    'Goldfinger'                             , 'Ian'         , 'Fleming'     , 'J428.64'     , 9781405080604     , 'https://goo.gl/PMdc2j'   , '655,837'     ,   '    '       )");




            }
            catch(SQLException e) {
                Message.message(context, "" +e);
            }
        }
        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion)
        {
            try
            {
                Message.message(context, "OnUpgrade called");
                db.execSQL("DROP TABLE IF EXISTS " + tableName + " ");
                onCreate(db);
            }
            catch (SQLException e)
            {
                Message.message(context, "" + e);
            }

        }
        public ArrayList<Cursor> getData(String Query)
        {
            //get writable database
            SQLiteDatabase sqlDB = this.getWritableDatabase();
            String[] columns = new String[] { "message" };
            //an array list of cursor to save two cursors one has results from the query
            //other cursor stores error message if any errors are triggered
            ArrayList<Cursor> alc = new ArrayList<Cursor>(2);
            MatrixCursor Cursor2= new MatrixCursor(columns);
            alc.add(null);
            alc.add(null);


            try{
                String maxQuery = Query ;
                //execute the query results will be save in Cursor c
                Cursor c = sqlDB.rawQuery(maxQuery, null);


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


        }
    }
}

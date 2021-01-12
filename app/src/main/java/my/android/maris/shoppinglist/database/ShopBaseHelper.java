package my.android.maris.shoppinglist.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import my.android.maris.shoppinglist.database.ShopDbSchema.ShoppingTable;

public class ShopBaseHelper extends SQLiteOpenHelper
{
    private static final int VERSION = 1;
    private static final String DATABASE_NAME = "shopBase.db";

    public ShopBaseHelper (Context context)
    {
        super(context, DATABASE_NAME, null, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db)
    {
        db.execSQL("create table " + ShoppingTable.NAME+ "(" +
                " _id integer primary key autoincrement, " +
                ShoppingTable.Cols.UUID + ", " +
                ShoppingTable.Cols.NAME + ", " +
                ShoppingTable.Cols.PRICE + ", " +
                ShoppingTable.Cols.SHOP + ", " +
                ShoppingTable.Cols.CHECKED +
                ")"
        );
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion)
    {

    }
}

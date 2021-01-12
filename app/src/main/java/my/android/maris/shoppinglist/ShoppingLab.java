package my.android.maris.shoppinglist;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import my.android.maris.shoppinglist.database.ShopBaseHelper;
import my.android.maris.shoppinglist.database.ShopDbSchema;
import my.android.maris.shoppinglist.database.ShoppingCursorWrapper;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class ShoppingLab
{
    private static ShoppingLab sShoppingLab;
    private Context mContext;
    private SQLiteDatabase mDatabase;

    public static ShoppingLab get(Context context)
    {
        if(sShoppingLab == null)
        {
            sShoppingLab = new ShoppingLab(context);
        }
        return sShoppingLab;
    }

    private ShoppingLab(Context context)
    {
        mContext = context.getApplicationContext();
        mDatabase = new ShopBaseHelper(mContext).getWritableDatabase();
    }

    public void addShoppingItem(ShoppingItem sI)
    {
        ContentValues values = getContentValues(sI);
        mDatabase.insert(ShopDbSchema.ShoppingTable.NAME, null, values);
    }

    public void deleteShoppingItem(int i)
    {
        List<ShoppingItem> items = getShoppingItems();
        UUID uuid = items.get(i).getId();

        ShoppingCursorWrapper cursor = queryShoppingItems(
                ShopDbSchema.ShoppingTable.Cols.UUID + " = ?",
                new String[] { uuid.toString() }
        );
        try {
            if (cursor.getCount() == 0) {
                return;
            }
            cursor.moveToFirst();
            mDatabase.delete(ShopDbSchema.ShoppingTable.NAME, ShopDbSchema.ShoppingTable.Cols.UUID + " = ?",
                    new String[]{uuid.toString()});
        } finally {
            cursor.close();
        }
    }

    public void setItemChecked(ShoppingItem shoppingItem)
    {
        String uuidString = shoppingItem.getId().toString();
        ContentValues values = getContentValues(shoppingItem);
        mDatabase.update(ShopDbSchema.ShoppingTable.NAME, values,
                ShopDbSchema.ShoppingTable.Cols.UUID + " = ?",
                new String[] { uuidString });
    }

    public List<ShoppingItem> getShoppingItems()
    {
        List<ShoppingItem> shoppingItems = new ArrayList<>();

        ShoppingCursorWrapper cursor = queryShoppingItems(null, null);

        try
        {
            cursor.moveToFirst();
            while (!cursor.isAfterLast())
            {
                shoppingItems.add(cursor.getShoppingItem());
                cursor.moveToNext();
            }
        }
        finally {
            cursor.close();
        }
        return shoppingItems;
    }

    public ShoppingItem getShoppingItem(UUID id)
    {
        ShoppingCursorWrapper cursor = queryShoppingItems(
                ShopDbSchema.ShoppingTable.Cols.UUID + " = ?",
                new String[] {id.toString()}
        );

        try {
            if (cursor.getCount() == 0)
            {
                return null;
            }
            cursor.moveToFirst();
            return cursor.getShoppingItem();
        }
        finally {
            cursor.close();
        }
    }

    private static ContentValues getContentValues (ShoppingItem sI)
    {
        ContentValues values = new ContentValues();
        values.put(ShopDbSchema.ShoppingTable.Cols.UUID, sI.getId().toString());
        values.put(ShopDbSchema.ShoppingTable.Cols.NAME, sI.getName());
        values.put(ShopDbSchema.ShoppingTable.Cols.PRICE, sI.getPrice());
        values.put(ShopDbSchema.ShoppingTable.Cols.SHOP, sI.getShop());
        values.put(ShopDbSchema.ShoppingTable.Cols.CHECKED, sI.isChecked() ? 1 : 0);
        return values;
    }

    private ShoppingCursorWrapper queryShoppingItems (String whereClause, String[] whereArgs)
    {
        Cursor cursor = mDatabase.query(
                ShopDbSchema.ShoppingTable.NAME,
                null,
                whereClause,
                whereArgs,
                null,
                null,
                null
        );
        return new ShoppingCursorWrapper(cursor);
    }
}

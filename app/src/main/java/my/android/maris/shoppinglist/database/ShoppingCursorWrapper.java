package my.android.maris.shoppinglist.database;

import android.database.Cursor;
import android.database.CursorWrapper;

import my.android.maris.shoppinglist.ShoppingItem;

import java.util.UUID;

public class ShoppingCursorWrapper extends CursorWrapper
{
    public ShoppingCursorWrapper (Cursor cursor)
    {
        super(cursor);
    }

    public ShoppingItem getShoppingItem() {
        String uuidString = getString(getColumnIndex(ShopDbSchema.ShoppingTable.Cols.UUID));
        String name = getString(getColumnIndex(ShopDbSchema.ShoppingTable.Cols.NAME));
        String price = getString(getColumnIndex(ShopDbSchema.ShoppingTable.Cols.PRICE));
        String shop = getString(getColumnIndex(ShopDbSchema.ShoppingTable.Cols.SHOP));
        int isChecked = getInt(getColumnIndex(ShopDbSchema.ShoppingTable.Cols.CHECKED));

        ShoppingItem shoppingItem = new ShoppingItem(UUID.fromString(uuidString));
        shoppingItem.setName(name);
        shoppingItem.setPrice(price);
        shoppingItem.setShop(shop);
        shoppingItem.setChecked(isChecked !=0);

        return shoppingItem;
    }
}

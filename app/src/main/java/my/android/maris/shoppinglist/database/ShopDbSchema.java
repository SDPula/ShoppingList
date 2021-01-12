package my.android.maris.shoppinglist.database;

public class ShopDbSchema
{
    public static final class ShoppingTable
    {
        public static final String NAME = "shoppingItems";

        public static final class Cols
        {
            public static final String UUID = "uuid";
            public static final String NAME = "name";
            public static final String PRICE = "price";
            public static final String SHOP = "shop";
            public static final String CHECKED = "checked";
        }
    }
}

package my.android.maris.shoppinglist;

import java.util.UUID;

public class ShoppingItem
{
    private UUID mId;
    private String mName;
    private String mPrice;
    private String mShop;
    private boolean isChecked;

    public ShoppingItem()
    {
        this(UUID.randomUUID());
    }

    public ShoppingItem(UUID id)
    {
        mId = id;
    }

    public UUID getId() {
        return mId;
    }

    public String getName() {
        return mName;
    }

    public void setName(String name) {
        mName = name;
    }

    public String getPrice() {
        return mPrice;
    }

    public void setPrice(String price) {
        mPrice = price;
    }

    public String getShop() {
        return mShop;
    }

    public void setShop(String shop) {
        mShop = shop;
    }

    public boolean isChecked() {
        return isChecked;
    }

    public void setChecked(boolean checked) {
        isChecked = checked;
    }
}

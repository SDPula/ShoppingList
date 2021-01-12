package my.android.maris.shoppinglist;

import androidx.fragment.app.Fragment;

public class ShoppingListActivity extends SingleFragmentActivity
{
    @Override
    protected Fragment createFragment()
    {
        return ShoppingListFragment.newInstance();
    }
}

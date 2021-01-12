package my.android.maris.shoppinglist;

import androidx.fragment.app.Fragment;

public class ShoppingActivity extends SingleFragmentActivity {

    @Override
    protected Fragment createFragment()
    {
        return ShoppingFragment.newInstance();
    }
}

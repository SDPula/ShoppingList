package my.android.maris.shoppinglist;

import android.content.Intent;
import android.os.Bundle;

import my.android.maris.shoppinglist.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.ItemTouchHelper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import java.util.List;

public class ShoppingListFragment extends Fragment
{
    private RecyclerView mRecyclerView;
    private ShoppingListAdapter mShoppingListAdapter;
    private FloatingActionButton mFloatingActionButton;
    private TextView mInfoTextView;

    public static ShoppingListFragment newInstance()
    {
        return new ShoppingListFragment();
    }

    @Override
    public View onCreateView (LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        View v = inflater.inflate(R.layout.fragment_shopping_list, container, false);

        mInfoTextView = (TextView) v.findViewById(R.id.recycler_view_info_text_view);
        if(ShoppingLab.get(getActivity()).getShoppingItems().size() == 0)
        {
            mInfoTextView.setText("List is empty. Please add item to list.");
        }
        else
        {
            mInfoTextView.setVisibility(View.INVISIBLE);
        }

        mRecyclerView = (RecyclerView) v.findViewById(R.id.shopping_list_recycler_view);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        mFloatingActionButton = (FloatingActionButton) v.findViewById(R.id.floating_button);
        mFloatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getActivity(), ShoppingActivity.class);
                startActivity(i);
            }
        });

        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(simpleItemTouchCallback);
        itemTouchHelper.attachToRecyclerView(mRecyclerView);

        updateUI();

        return v;
    }

    @Override
    public void onResume()
    {
        super.onResume();
        updateUI();
    }

    private void updateUI()
    {
        ShoppingLab shoppingLab = ShoppingLab.get(getActivity());
        List<ShoppingItem> shoppingItems = shoppingLab.getShoppingItems();

        if(mShoppingListAdapter == null) {

            mShoppingListAdapter = new ShoppingListAdapter(shoppingItems);
            mRecyclerView.setAdapter(mShoppingListAdapter);
        }
        else
        {
            mShoppingListAdapter.setShoppingItems(shoppingItems);
            mShoppingListAdapter.notifyDataSetChanged();
        }
    }

    ItemTouchHelper.SimpleCallback simpleItemTouchCallback = new ItemTouchHelper
            .SimpleCallback(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT | ItemTouchHelper.DOWN | ItemTouchHelper.UP) {

        @Override
        public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
            return false;
        }

        @Override
        public void onSwiped(RecyclerView.ViewHolder viewHolder, int swipeDir) {
            int position = viewHolder.getAdapterPosition();
            ShoppingLab.get(getActivity()).deleteShoppingItem(position);
            updateUI();
            mShoppingListAdapter.notifyDataSetChanged();
        }
    };

    private class ShoppingListHolder extends RecyclerView.ViewHolder implements View.OnClickListener
    {
        private ShoppingItem mShoppingItem;
        private TextView mNameTextView;
        private TextView mShopTextView;
        private TextView mPriceTextView;
        private CheckBox mCheckBox;

        public ShoppingListHolder(final View itemView)
        {
            super(itemView);
            itemView.setOnClickListener(this);
            mNameTextView = (TextView) itemView.findViewById(R.id.list_item_name_text_view);
            mShopTextView = (TextView) itemView.findViewById(R.id.list_item_shop_text_view);
            mPriceTextView = (TextView) itemView.findViewById(R.id.list_item_price_text_view);
            mCheckBox = (CheckBox) itemView.findViewById(R.id.list_item_check_box);
        }

        public void bindShoppingItem(ShoppingItem shoppingItem)
        {
            mShoppingItem = shoppingItem;
            mNameTextView.setText(mShoppingItem.getName());
            mShopTextView.setText(mShoppingItem.getShop());
            mPriceTextView.setText(mShoppingItem.getPrice());
            mCheckBox.setChecked(mShoppingItem.isChecked());
            mCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    mShoppingItem.setChecked(isChecked);
                    ShoppingLab.get(getActivity()).setItemChecked(mShoppingItem);
                    if (!mRecyclerView.isComputingLayout())
                    {
                        updateUI();
                    }
                }
            });

        }

        @Override
        public void onClick(View v)
        {
        }
    }



    private class ShoppingListAdapter extends RecyclerView.Adapter<ShoppingListHolder>
    {
        private List<ShoppingItem> mShoppingItems;

        public ShoppingListAdapter(List<ShoppingItem> items)
        {
            mShoppingItems = items;
        }

        @Override
        public ShoppingListHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            LayoutInflater layoutInflater = LayoutInflater.from(getActivity());
            View v = layoutInflater.inflate(R.layout.list_shopping_items, parent, false);
            return new ShoppingListHolder(v);
        }

        @Override
        public void onBindViewHolder(ShoppingListHolder holder, int position) {
            ShoppingItem shoppingItem = mShoppingItems.get(position);
            holder.bindShoppingItem(shoppingItem);
        }

        @Override
        public int getItemCount() {
            return mShoppingItems.size();
        }

        public void setShoppingItems(List<ShoppingItem> items)
        {
            mShoppingItems = items;
        }
    }
}

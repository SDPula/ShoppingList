package my.android.maris.shoppinglist;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;

import my.android.maris.shoppinglist.R;

public class ShoppingFragment extends Fragment
{
    private ShoppingItem mShoppingItem;
    private EditText mItemName;
    private EditText mItemPrice;
    private EditText mItemShop;
    private CheckBox mCheckBox;
    private Button mSaveButton;

    public static ShoppingFragment newInstance()
    {
        return new ShoppingFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        mShoppingItem = new ShoppingItem();
    }

    @Override
    public View onCreateView (LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        View v = inflater.inflate(R.layout.fragment_shopping, container, false);

        mCheckBox = (CheckBox) v.findViewById(R.id.item_checked);

        mItemName = (EditText) v.findViewById(R.id.item_name);
        InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, InputMethodManager.HIDE_IMPLICIT_ONLY);
        mItemName.setText(mShoppingItem.getName());
        mItemName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                mShoppingItem.setName(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });

        mItemPrice = (EditText) v.findViewById(R.id.item_price);
        mItemPrice.setText(mShoppingItem.getPrice());
        mItemPrice.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                mShoppingItem.setPrice(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        mItemShop = (EditText) v.findViewById(R.id.item_shop);
        mItemShop.setText(mShoppingItem.getShop());
        mItemShop.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                mShoppingItem.setShop(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        mSaveButton = (Button) v.findViewById(R.id.saveButton);
        mSaveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ShoppingItem shoppingItem = new ShoppingItem();
                shoppingItem.setName(mShoppingItem.getName());
                shoppingItem.setPrice(mShoppingItem.getPrice());
                shoppingItem.setShop(mShoppingItem.getShop());
                shoppingItem.setChecked(mShoppingItem.isChecked());
                ShoppingLab.get(getActivity()).addShoppingItem(shoppingItem);
                InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(mItemName.getWindowToken(), 0);
                Intent i = new Intent(getActivity(), ShoppingListActivity.class);
                startActivity(i);
            }
        });

        return v;
    }
}

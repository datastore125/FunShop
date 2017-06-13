package com.example.animeshpatra.funshop;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class CartItems extends AppCompatActivity {

    Toolbar toolbar;
    ListView lv_dataShow;
    TextView btn_placeOrder;
    TextView tv_totalAmount;
    Double amount = 0.0, totalAmount = 0.0;
    SQLiteDatabase db;
    Cursor cursor;
    public static final String SQL_DISPLAY_QUERY = "SELECT * FROM ItemLists";
    ArrayList <CartData> arrayList = new ArrayList<>();
    CartData cartData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart_items);
        toolbar = (Toolbar) findViewById(R.id.app_bar);
        setSupportActionBar(toolbar);
        lv_dataShow = (ListView) findViewById(R.id.lv_dataShow);
        tv_totalAmount = (TextView) findViewById(R.id.tv_totalAmount);
        btn_placeOrder = (TextView) findViewById(R.id.btn_placeOrder);

        btn_placeOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CartItems.this, PlaceOrder.class);
                String sendAmount = tv_totalAmount.getText().toString();
                intent.putExtra("Amount", sendAmount);
                startActivity(intent);
            }
        });
        OpenDatabase();
        cursor = db.rawQuery(SQL_DISPLAY_QUERY, null);
        cursor.moveToFirst();
        StoreRecord();
        ShowRecord();
        AmountCalculation();
    }
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_category_tab, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.shoppingCart){
            startActivity(new Intent(this, CartItems.class));
        }
        if (id == R.id.home){
            startActivity(new Intent(this, CategoryTabActivity.class));
        }
        return super.onOptionsItemSelected(item);
    }

    private void AmountCalculation() {
        if(cursor != null && cursor.getCount() > 0){
            cursor.moveToFirst();
            do {
                {
                    String price = cursor.getString(5);
                    amount = Double.parseDouble(price);
                }
                totalAmount = amount + totalAmount;
            }while (cursor.moveToNext());
            tv_totalAmount.setText(String.valueOf(totalAmount));
        }
    }


    private void ShowRecord() {
        if (arrayList.size() > 0){
            lv_dataShow.setAdapter(new MyCartAdapter());
        }
        else{
            AlertDialog.Builder dialog = new AlertDialog.Builder(CartItems.this);
            dialog.setMessage("Cart is Empty")
                    .setNegativeButton("Okay", null)
                    .create()
                    .show();
        }
    }

    public class MyCartAdapter extends BaseAdapter{

        @Override
        public int getCount() {
            return arrayList.size();
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {
            convertView = getLayoutInflater().inflate(R.layout.cart_item_view,parent,false);
            ImageView iv_productImage = (ImageView) convertView.findViewById(R.id.iv_Image);
            TextView tv_itemName = (TextView) convertView.findViewById(R.id.tv_itemName);
            TextView tv_marketPrice = (TextView) convertView.findViewById(R.id.tv_marketPrice);
            TextView tv_webPrice = (TextView) convertView.findViewById(R.id.tv_webPrice);
            TextView tv_availibility = (TextView) convertView.findViewById(R.id.tv_avalibility);
            ImageView btn_remove = (ImageView) convertView.findViewById(R.id.btn_remove);

            btn_remove.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String serial_remove = arrayList.get(position).getsNo();
                    String sql_remove = "DELETE FROM ItemLists where SerialNo ="+serial_remove;
                    db.execSQL(sql_remove);
                    AmountCalculation();
                    finish();
                    startActivity(getIntent());
                }
            });

            tv_itemName.setText(arrayList.get(position).getpName());
            tv_marketPrice.setText(arrayList.get(position).getmPrice());
            tv_webPrice.setText(arrayList.get(position).getwPrice());
            tv_availibility.setText(arrayList.get(position).getAvail());
            Picasso.with(CartItems.this).load(arrayList.get(position).getImage()).into(iv_productImage);
            return convertView;
        }
    }

    private void StoreRecord() {
        if(cursor != null && cursor.getCount() > 0){
            cursor.moveToFirst();
            do {
                String SNO = cursor.getString(0);
                String ProductID = cursor.getString(1);
                String CategoryID = cursor.getString(2);
                String ProductName = cursor.getString(3);
                String MarketPrice = cursor.getString(4);
                String WebPrice = cursor.getString(5);
                String Availability = cursor.getString(6);
                String Image = cursor.getString(7);
                cartData = new CartData();
                cartData.setsNo(SNO);
                cartData.setpID(ProductID);
                cartData.setcID(CategoryID);
                cartData.setpName(ProductName);
                cartData.setmPrice(MarketPrice);
                cartData.setwPrice(WebPrice);
                cartData.setAvail(Availability);
                cartData.setImage(Image);
                arrayList.add(cartData);} while (cursor.moveToNext());
            }

        }


    private void OpenDatabase() {
        db = openOrCreateDatabase("ShoppingCart", MODE_PRIVATE, null);

    }
}

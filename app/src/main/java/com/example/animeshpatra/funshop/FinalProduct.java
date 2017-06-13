package com.example.animeshpatra.funshop;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Paint;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

public class FinalProduct extends AppCompatActivity implements View.OnClickListener{
    Toolbar toolbar;
    ImageView iv_singleProductImage;
    TextView tv_productID, tv_categoryID, tv_itemName, tv_itemDescription, tv_marketPrice, tv_webPrice, tv_avalibility;
    ImageView btn_BuyNow, btn_addToCart;
    String productID = "", categoryID = "", itemName = "", itemDescription ="", marketPrice = "", webPrice = "", avalibility = "", productImage = "";
    private SQLiteDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_final_product);
        toolbar = (Toolbar) findViewById(R.id.app_bar);
        setSupportActionBar(toolbar);
        CreateDatabase();

        productID = getIntent().getExtras().getString("ID");
        categoryID = getIntent().getExtras().getString("CatID");
        itemName = getIntent().getExtras().getString("ItemName");
        itemDescription = getIntent().getExtras().getString("ItemDesc");
        marketPrice = getIntent().getExtras().getString("MarketPrice");
        webPrice = getIntent().getExtras().getString("WebPrice");
        avalibility = getIntent().getExtras().getString("Avalibility");
        productImage = getIntent().getExtras().getString("ProductImage");
        tv_itemName = (TextView) findViewById(R.id.tv_itemName);
        tv_marketPrice = (TextView) findViewById(R.id.tv_marketPrice);
        tv_webPrice = (TextView) findViewById(R.id.tv_webPrice);
        //tv_avalibility = (TextView) findViewById(R.id.tv_avalibility);
        iv_singleProductImage = (ImageView) findViewById(R.id.iv_singleProductImage);
        btn_BuyNow = (ImageView) findViewById(R.id.btn_BuyNow);
        btn_addToCart = (ImageView) findViewById(R.id.btn_addToCart);
        btn_BuyNow.setOnClickListener(this);
        btn_addToCart.setOnClickListener(this);
        tv_itemName.setText(itemName);
        tv_marketPrice.setText(marketPrice);
        tv_marketPrice.setPaintFlags(tv_marketPrice.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
        tv_webPrice.setText(webPrice);
        //tv_avalibility.setText(avalibility);
        Picasso.with(FinalProduct.this).load(productImage).into(iv_singleProductImage);
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

    private void CreateDatabase() {
        db = openOrCreateDatabase("ShoppingCart", MODE_PRIVATE, null);
        db.execSQL("CREATE TABLE IF NOT EXISTS ItemLists(SerialNo INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, ItemID VERCHAR, ItemCategory VERCHAR, ItemName VERCHAR, MarketPrice VERCHAR, WebPrice VERCHAR, Availability VERCHAR, Image VERCHAR);");
    }

    @Override
    public void onClick(View v) {
        if (v == btn_BuyNow){
            Intent intent = new Intent(FinalProduct.this, PlaceSingleProduct.class);
            intent.putExtra("Price", webPrice);
            startActivity(intent);
        }
        if (v == btn_addToCart){
            InsertIntoDataBase();
        }

    }

    private void InsertIntoDataBase() {
        String p_id = productID.trim();
        String c_id = categoryID.trim();
        String p_name = itemName.trim();
        String m_price = marketPrice.trim();
        String w_price = webPrice.trim();
        String p_availability = avalibility.trim();
        String p_image = productImage.trim();
        if (p_name.equals("") || w_price.equals("")){
            Toast.makeText(this, "Item has null values, please check", Toast.LENGTH_SHORT).show();
        }
        else {
            db.execSQL("INSERT INTO ItemLists (ItemID, ItemCategory, ItemName, MarketPrice, WebPrice, Availability, Image) VALUES ('"+p_id+"', '"+c_id+"', '"+p_name+"', '"+m_price+"', '"+w_price+"', '"+p_availability+"', '"+p_image+"');");
            Toast.makeText(this, "Successfully added to Cart: "+ p_name, Toast.LENGTH_LONG).show();
        }
    }
}

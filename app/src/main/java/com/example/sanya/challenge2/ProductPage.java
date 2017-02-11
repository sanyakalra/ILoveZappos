package com.example.sanya.challenge2;

import android.app.Activity;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.sanya.challenge2.databinding.ActivityProductPageBinding;
import com.gospelware.liquidbutton.LiquidButton;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.example.sanya.challenge2.R.id.btn_buy;


public class ProductPage extends Activity{

    private TextView productName;

    String pName="", pPrice="", pBname="", pImageUrl="";

    @Bind(R.id.liquid_button)
    LiquidButton liquidButton;
    @Bind(R.id.productBname)
    TextView productBname;
    @Bind(R.id.productPrice)
    TextView productPrice;
    @Bind(R.id.productImage)
    ImageView productImage;





    //Start the animation when button is clicked
    @OnClick(btn_buy)
    void startanimation()
    {
        productBname.setVisibility(View.INVISIBLE);
        productImage.setVisibility(View.INVISIBLE);
        productPrice.setVisibility(View.INVISIBLE);
        liquidButton.startPour();
        liquidButton.setAutoPlay(true);


    }

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_page);
        Bundle receiveBundle = getIntent().getExtras();
        pName     = receiveBundle.getString("ProductName");
        pPrice    = receiveBundle.getString("OriginalPrice");
        pBname    = receiveBundle.getString("BrandName");
        initializeIDs();
        ActivityProductPageBinding binding = DataBindingUtil.setContentView(this, R.layout.activity_product_page);
        Product product = new Product(pBname, pName, pImageUrl, pPrice);
        binding.setProduct(product);

        ButterKnife.bind(this);

        //Ending of the animation
        liquidButton.setPourFinishListener(new LiquidButton.PourFinishListener() {
            @Override
            public void onPourFinish() {
                Toast.makeText(ProductPage.this, "Added to Cart", Toast.LENGTH_SHORT).show();
                productBname.setVisibility(View.VISIBLE);
                productImage.setVisibility(View.VISIBLE);
                productPrice.setVisibility(View.VISIBLE);
            }

            @Override
            public void onProgressUpdate(float progress) {

            }
        });
    }

    //Initialization of all the layout elements
    private void initializeIDs() {
        productName  = (TextView)findViewById(R.id.productName);
        productPrice = (TextView)findViewById(R.id.productPrice);
        productImage = (ImageView)findViewById(R.id.productImage);
        productBname = (TextView)findViewById(R.id.productBname);
    }
}

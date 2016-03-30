package com.example.htmsl.loginpage;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class CoffeeOrder extends AppCompatActivity {
    int quantity = 2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.content_coffee_order);


        Button orderButton = (Button) findViewById(R.id.order_button);
        Button incButton = (Button) findViewById(R.id.incr_button);
        Button decButton = (Button) findViewById(R.id.decr_button);

        incButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (quantity >= 99) {
                    Toast.makeText(CoffeeOrder.this,"More than 99 coffee is not allowed",Toast.LENGTH_SHORT).show();
                    return;
                }
                quantity = quantity + 1;
                display(quantity);

            }
        });

        decButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(quantity<=1) {
                    Toast.makeText(CoffeeOrder.this,"Less than 1 coffee is not possible",Toast.LENGTH_SHORT).show();
                    return;
                }
                quantity = quantity - 1;

                display(quantity);
            }
        });

        orderButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View vi) {
                CheckBox hasCream = (CheckBox)findViewById(R.id.has_whipped_cream);
                CheckBox hasChocolate = (CheckBox) findViewById(R.id.has_chocolate);
                boolean cream = hasCream.isChecked();
                boolean chocolate = hasChocolate.isChecked();
                EditText textName = (EditText) findViewById(R.id.name_id);
                String name = textName.getText().toString();
                int price = calculatePrice(cream, chocolate, quantity);
                String summary = orderSummary(quantity, cream, chocolate, name, price);
                Intent intent = new Intent(Intent.ACTION_SENDTO);
                intent.setData(Uri.parse("mailto:"));
                intent.putExtra(Intent.EXTRA_SUBJECT,name+" has ordered coffee");
                intent.putExtra(Intent.EXTRA_TEXT,summary);
                startActivity(intent);
            }
        });
    }

    public int calculatePrice(boolean hasCream,boolean hasChocolate,int quant) {
        int baseprice = 5;
        if(hasCream) baseprice = baseprice +1;
        if(hasChocolate) baseprice =baseprice +2 ;
        return (baseprice * quant);
    }

    public String orderSummary(int quantity, boolean cream, boolean chocolate,String name, int summaryprice) {
        String summ = "Customer name is" +name;
        summ = summ + "\nAdd whipped cream?" +cream;
        summ += "\nAdd chocolate? " +chocolate;
        summ += "\nQuantity- " + quantity;
        summ += "\ntotal amount- " +summaryprice+ " Rs.";
        summ += "\nThank you !!!!";
        return summ;

    }

    private void display(int quantity) {
        TextView textQuan = (TextView) findViewById(R.id.quant_id);
        textQuan.setText(""+quantity);
    }


 /*  public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_coffee_order, menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}  */
}
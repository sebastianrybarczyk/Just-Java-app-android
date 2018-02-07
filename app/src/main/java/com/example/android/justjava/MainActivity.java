/**
 * IMPORTANT: Add your package below. Package name can be found in the project's AndroidManifest.xml file.
 * This is the package name our example uses:
 * <p>
 * package com.example.android.justjava;
 */

package com.example.android.justjava;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.text.NumberFormat;

/**
 * This app displays an order form to order coffee.
 */
public class MainActivity extends AppCompatActivity {

    int quantity = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    /**
     * This method is called when the order button is clicked.
     */
    public void submitOrder(View view) {

        int price = calculatePrice();


        CheckBox checkbox = findViewById(R.id.checkbox);
        boolean hasWhippedCream = checkbox.isChecked();
        CheckBox chocoCheckbox = findViewById(R.id.chocolate);
        boolean hasChocolate = chocoCheckbox.isChecked();

        if (hasWhippedCream && hasChocolate) {
            price = calculatePrice() + quantity + (quantity * 2);
        } else if (hasChocolate) {
            price = calculatePrice() + (quantity * 2);
        }else if (hasWhippedCream) {
            price = calculatePrice() + quantity;
        } else {
                calculatePrice();
        }


        EditText nameEntered = findViewById(R.id.name);
        //String name = nameEntered.getText().toString();    //------->> other possibility --> have to change the fourth argument in createOrderSummary as String
        Editable name = nameEntered.getEditableText();

        String priceMessage = createOrderSummary(price, hasWhippedCream, hasChocolate, name );

        Intent intent = new Intent(Intent.ACTION_SENDTO);
        intent.setData(Uri.parse("mailto:"));
        intent.putExtra(Intent.EXTRA_SUBJECT, "Just java order from: " + name);
        intent.putExtra(Intent.EXTRA_TEXT, priceMessage);
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        }

        displayMessage(priceMessage);




    }

    /**
     * Calculates the price of the order.
     *
     * @return The total price
     */
    private int calculatePrice() {
        return quantity * 5;

    }

    /**
     * This method is called when the plus button is clicked.
     */
    public void increment(View view) {
        if (quantity == 100){
            Toast.makeText(this, "You cannot order more than 100 coffees", Toast.LENGTH_SHORT).show();
            return;
        }
        quantity++;
        displayQuantity(quantity);

    }

    /**
     * This method is called when the minus button is clicked.
     * It has condition that if quantity is zero it will not decrement so we can not order minus coffees
     */
    public void decrement(View view) {
        if (quantity == 0) {
            Toast.makeText(this, "You cannot order less than 0 coffees", Toast.LENGTH_SHORT).show();
            return;
        }
        quantity--;
        displayQuantity(quantity);
    }

    /**
     * This method displays the given quantity value on the screen.
     */
    private void displayQuantity(int nummer) {
        if (quantity < 0) {
            displayQuantity(0);
        }
        TextView quantityTextView = (TextView) findViewById(R.id.quantity_text_view); // (TextView) guarantee that right side part will be TextView object - otherwise not working
        quantityTextView.setText("" + nummer);
    }


    /**
     * This method displays the given text on the screen.
     *
     * @param message
     */
    private void displayMessage(String message) {
        TextView orderSummaryTextView = findViewById(R.id.order_summary_text_view);
        orderSummaryTextView.setText(message);
    }

    private String createOrderSummary(int price, boolean hasWhippedCream, boolean hasChocolate, Editable name) {
        String message = getString(R.string.order_summary_name, name) + "\n"
                + getString(R.string.order_summary_whipped_cream, hasWhippedCream) + "\n"
                + getString(R.string.order_summary_chocolate, hasChocolate) + "\n"
                + getString(R.string.order_summary_quantity, quantity) + "\n"
                + getString(R.string.order_summary_price,
                            NumberFormat.getCurrencyInstance().format(price)) + "\n"
                + getString(R.string.thank_you);

        return message;

    }


}
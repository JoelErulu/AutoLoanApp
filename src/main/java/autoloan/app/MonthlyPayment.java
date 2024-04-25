package autoloan.app;


import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import java.text.DecimalFormat;

public class MonthlyPayment extends AppCompatActivity {


    private TextView totalCostTextView;
    private TextView monthlyPaymentTextView;
    private TextView loanTermTextView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_monthly_price);

        // Find the EditText for monthly payment and the TextViews for loan information
;
        totalCostTextView = findViewById(R.id.totalCostTextView);
        monthlyPaymentTextView = findViewById(R.id.monthlyPaymentTextView);
        loanTermTextView = findViewById(R.id.loanTermTextView);


    }

    public void calculateTotalCost(View view) {

        EditText monthlyPaymentEditText = findViewById(R.id.monthlyPaymentEditText);
        EditText loanTermEditText = findViewById(R.id.loanTermEditText);


        // Get the values entered by the user
        double monthlyPayment = Double.parseDouble(monthlyPaymentEditText.getText().toString());
        int loanTerm = Integer.parseInt(loanTermEditText.getText().toString());


        double totalCost = TotalCost(monthlyPayment, loanTerm);

        DecimalFormat decimalFormat = new DecimalFormat("#,###.##");

        monthlyPaymentTextView.setText("Monthly Payment: $" + decimalFormat.format(monthlyPayment));
        loanTermTextView.setText("Loan Term: " + decimalFormat.format(loanTerm) + " months");
        totalCostTextView.setText("Total Cost: $" + decimalFormat.format(totalCost));
    }

    private double TotalCost(double monthlyPayment, int loanTerm) {
        // Calculate the monthly interest rate
        double totalcost = monthlyPayment * loanTerm;

        return totalcost;
    }

}

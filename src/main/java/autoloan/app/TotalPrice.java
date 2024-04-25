package autoloan.app; // Corrected package name

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast; // Import Toast for error handling

import java.text.DecimalFormat;

public class TotalPrice extends AppCompatActivity {

    private TextView monthlyPaymentTextView;

    private TextView totalLoanTextView;
    private TextView salesTaxTextView;
    private TextView downPaymentTextView;
    private TextView totalLoanPaymentsTextView;
    private TextView totalLoanInterestTextView;
    private TextView totalCostTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_total_price);

        // Find the TextViews
        monthlyPaymentTextView = findViewById(R.id.monthlyPaymentTextView);
        totalLoanTextView = findViewById(R.id.totalLoanTextView);
        salesTaxTextView = findViewById(R.id.salesTaxTextView);
        downPaymentTextView = findViewById(R.id.downPaymentTextView);
        totalLoanPaymentsTextView = findViewById(R.id.totalLoanPaymentsTextView);
        totalLoanInterestTextView = findViewById(R.id.totalLoanInterestTextView);
        totalCostTextView = findViewById(R.id.totalCostTextView);
    }

    // Method to calculate total price of auto loan
    public void calculatePrice(View view) {
        try {
            EditText autoPriceEditText = findViewById(R.id.autoPriceEditText);
            EditText loanTermEditText = findViewById(R.id.loanTermEditText);
            EditText interestRateEditText = findViewById(R.id.interestRateEditText);
            EditText cashIncentivesEditText = findViewById(R.id.cashIncentivesEditText);
            EditText downPaymentEditText = findViewById(R.id.downPaymentEditText);
            EditText tradeInValueEditText = findViewById(R.id.tradeInValueEditText);
            EditText tradeInAmountOwedEditText = findViewById(R.id.tradeInAmountOwedEditText);
            EditText salesTaxEditText = findViewById(R.id.salesTaxEditText);
            EditText otherFeesEditText = findViewById(R.id.otherFeesEditText);

            // Get the values entered by the user
            int autoPrice = Integer.parseInt(autoPriceEditText.getText().toString());
            int loanTerm = Integer.parseInt(loanTermEditText.getText().toString());
            double interestRate = Double.parseDouble(interestRateEditText.getText().toString());
            int cashIncentives = Integer.parseInt(cashIncentivesEditText.getText().toString());
            int downPayment = Integer.parseInt(downPaymentEditText.getText().toString());
            int tradeInValue = Integer.parseInt(tradeInValueEditText.getText().toString());
            int tradeInAmountOwed = Integer.parseInt(tradeInAmountOwedEditText.getText().toString());
            double salesTax = Double.parseDouble(salesTaxEditText.getText().toString());
            int otherFees = Integer.parseInt(otherFeesEditText.getText().toString());

            // Calculate the total price of the auto loan
            double totalLoan = calculateTotalLoan(autoPrice, loanTerm, interestRate, cashIncentives, downPayment, tradeInValue, tradeInAmountOwed, salesTax, otherFees);
            double salesTaxAmount = salesTaxAmount(autoPrice, salesTax);
            double totalLoanInterest = calculateTotalLoanInterest(autoPrice, loanTerm, interestRate, cashIncentives, downPayment, tradeInValue, tradeInAmountOwed, salesTax, otherFees);
            double totalCost = totalLoan + totalLoanInterest + downPayment;
            double totalLoanPayments =  totalCost - downPayment;
            double monthlyPayment = totalLoanPayments/loanTerm;

            DecimalFormat decimalFormat = new DecimalFormat("#,###.##");

            monthlyPaymentTextView.setText("Monthly Payment: $" + decimalFormat.format(monthlyPayment));
            totalLoanTextView.setText("Total Loan: $" + decimalFormat.format(totalLoan));
            salesTaxTextView.setText("Sales Tax: $" + decimalFormat.format(salesTaxAmount));
            downPaymentTextView.setText("Down Payment: $" + decimalFormat.format(downPayment));
            totalLoanPaymentsTextView.setText("Total Loan Payments: $" + decimalFormat.format(totalLoanPayments));
            totalLoanInterestTextView.setText("Total Loan Interest: $" + decimalFormat.format(totalLoanInterest));
            totalCostTextView.setText("Total Cost (price, interest, tax, fees): $" + decimalFormat.format(totalCost));

        } catch (NumberFormatException e) {
            // Handle invalid input
            Toast.makeText(this, "Please enter valid numbers in all fields", Toast.LENGTH_SHORT).show();
        }
    }

    // Method to calculate total price of auto loan
    private double calculateTotalLoan(int autoPrice, int loanTerm, double interestRate, int cashIncentives, int downPayment, int tradeInValue, int tradeInAmountOwed, double salesTax, int otherFees) {
        double loanAmount = autoPrice - downPayment + tradeInAmountOwed + otherFees - cashIncentives;
        double salesTaxAmount = autoPrice * salesTax / 100;
        return loanAmount + salesTaxAmount;
    }

    // Method to calculate sales tax amount
    private double salesTaxAmount(int autoPrice, double salesTax) {
        return autoPrice * salesTax / 100;
    }

    // Method to calculate total loan interest
    private double calculateTotalLoanInterest(int autoPrice, int loanTerm, double interestRate, int cashIncentives, int downPayment, int tradeInValue, int tradeInAmountOwed, double salesTax, int otherFees) {
        // Calculate the total loan amount
        double totalLoanAmount = calculateTotalLoan(autoPrice, loanTerm, interestRate, cashIncentives, downPayment, tradeInValue, tradeInAmountOwed, salesTax, otherFees);

        // Calculate the monthly interest rate
        double monthlyInterestRate = interestRate / 100 / 12;

        // Calculate the monthly payment using the formula for an amortizing loan
        double monthlyPayment = totalLoanAmount * monthlyInterestRate / (1 - Math.pow(1 + monthlyInterestRate, -loanTerm));

        // Initialize total interest
        double totalInterest = 0;
        double remainingBalance = totalLoanAmount;

        // Calculate interest for each period
        for (int i = 0; i < loanTerm; i++) {
            double interest = remainingBalance * monthlyInterestRate;
            totalInterest += interest;
            remainingBalance -= (monthlyPayment - interest); // Subtract the portion of the payment that goes towards the principal
        }

        return totalInterest;
    }
}

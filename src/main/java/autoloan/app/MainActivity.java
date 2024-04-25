package autoloan.app;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.widget.ImageView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;



public class MainActivity extends AppCompatActivity {

    private ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        imageView = findViewById(R.id.car);

        startCarAnimation();

    }


    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.appbar_menu, menu);
        return true;
    }

    public void onTotalPriceClick(View view) {
        Intent intent = new Intent(this, TotalPrice.class);
        Toast.makeText(MainActivity.this, "Total Price selected", Toast.LENGTH_SHORT).show();
        startActivity(intent);
    }

    public void onMonthlyPaymentClick(View view) {
        Intent intent = new Intent(this, MonthlyPayment.class);
        Toast.makeText(MainActivity.this, "Monthly Payment selected", Toast.LENGTH_SHORT).show();

        startActivity(intent);
    }

    public void onTips(View view) {
        Intent intent = new Intent(this, Tips.class);
        Toast.makeText(MainActivity.this, "Tips selected", Toast.LENGTH_SHORT).show();

        startActivity(intent);
    }

    public void onAbout(View view) {
        Intent intent = new Intent(this, About.class);
        Toast.makeText(MainActivity.this, "About selected", Toast.LENGTH_SHORT).show();

        startActivity(intent);
    }

    private void startCarAnimation() {
        // Create ObjectAnimator to scale up the image
        ObjectAnimator scaleX = ObjectAnimator.ofFloat(imageView, View.SCALE_X, 0.2f, 1.0f);
        ObjectAnimator scaleY = ObjectAnimator.ofFloat(imageView, View.SCALE_Y, 0.2f, 1.0f);

        // Create ObjectAnimator to move the image diagonally towards the viewer
        ObjectAnimator translationX = ObjectAnimator.ofFloat(imageView, View.TRANSLATION_X, -200f, 0f);
        ObjectAnimator translationY = ObjectAnimator.ofFloat(imageView, View.TRANSLATION_Y, -200f, 0f);

        // Combine the scaling and translation animations
        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.playTogether(scaleX, scaleY, translationX, translationY);
        animatorSet.setDuration(2000); // Duration of the animation in milliseconds
        animatorSet.setInterpolator(new AccelerateDecelerateInterpolator());
        animatorSet.start();
    }

}





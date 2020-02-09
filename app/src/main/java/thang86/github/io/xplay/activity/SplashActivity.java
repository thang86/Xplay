package thang86.github.io.xplay.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import thang86.github.io.xplay.R;
import thang86.github.io.xplay.activity.popular_movie.PopularActivity;

/**
 * Created by TranThang on 2/8/2020.
 */

public class SplashActivity extends AppCompatActivity {
    private static final String TAG = SplashActivity.class.getSimpleName();
    @BindView(R.id.app_logo)
    ImageView imgAppLogo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        Log.d(TAG, "onCreate: started!!");
        ButterKnife.bind(this);
        Animation animation = AnimationUtils.loadAnimation(this, R.anim.splashtransiton);
        imgAppLogo.startAnimation(animation);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                SplashActivity.this.startActivity(new Intent(SplashActivity.this, PopularActivity.class));
                SplashActivity.this.finish();
            }
        }, 5000);

    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}

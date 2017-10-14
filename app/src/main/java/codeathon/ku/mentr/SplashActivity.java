package codeathon.ku.mentr;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
    }

    public void GotoLogin(View view)
    {
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
    }
}

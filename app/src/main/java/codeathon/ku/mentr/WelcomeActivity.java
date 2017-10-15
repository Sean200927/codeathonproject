package codeathon.ku.mentr;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.util.Log;
import android.widget.*;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;


public class WelcomeActivity extends AppCompatActivity implements View.OnClickListener {

    private TextView mUsername;
    private TextView mPassword;

    //Constants
    private static final String TAG = "UsernamePassword";

    //Declare Firebase Authorization
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        //Views
        mUsername = (TextView) findViewById(R.id.usernameLogin);
        mPassword = (TextView) findViewById(R.id.passwordLogin);

        //Buttons

        findViewById(R.id.loginButton).setOnClickListener(this);
        findViewById(R.id.signUpButton).setOnClickListener(this);

        //Initialize authorization
        mAuth = FirebaseAuth.getInstance();
    }

    @Override
    public void onStart() {
        super.onStart();

        //To see if user is signed in and refresh user interface
        FirebaseUser currentUser = mAuth.getCurrentUser();
    }

    private void createAccount(String username, String password) {
        Log.d(TAG, "createAccount:" + username);
        if(!validateForm()){
            return;
        }

        //Email create user
        mAuth.createUserWithEmailAndPassword(username, password).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    //Success
                    Log.d(TAG, "createUserWithEmail:success");
                    FirebaseUser user = mAuth.getCurrentUser();
                }
                else {
                    //Failure
                    Log.w(TAG, "createUserWithEmail:failure", task.getException());
                    Toast.makeText(WelcomeActivity.this, "Authentication failed.", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void signIn(String username, String password) {
        Log.d(TAG, "signIn:" + username);
        if(!validateForm()) {
            return;
        }

        //Email sign in
        mAuth.signInWithEmailAndPassword(username, password).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    //Success
                    Log.d(TAG, "signInWithEmail:success");
                    FirebaseUser user = mAuth.getCurrentUser();
                }
                else {
                    //Failure
                    Log.w(TAG, "signInWithEmail:failure", task.getException());
                    Toast.makeText(WelcomeActivity.this, "Authentication failed.", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private boolean validateForm() {
        boolean valid = true;

        String email = mUsername.getText().toString();
        if(TextUtils.isEmpty(email)) {
            mUsername.setError("Required.");
            valid = false;
        }
        else {
            mUsername.setError(null);
        }

        String password = mPassword.getText().toString();
        if(TextUtils.isEmpty(password)) {
            mPassword.setError("Required.");
            valid = false;
        }
        else {
            mPassword.setError(null);
        }

        return valid;
    }

    @Override
    public void onClick(View v) {
        int i = v.getId();
        if( i == R.id.signUpButton) {
            if(validateForm()) {
                //Success
                createAccount(mUsername.getText().toString(), mPassword.getText().toString());
                Intent intent = new Intent(this, SignupActivity.class);
                startActivity(intent);
                Log.d(TAG, "Create your account");
            }
            else {
                //Failure
                Log.w(TAG, "signInWithEmail:failure");
                Toast.makeText(WelcomeActivity.this, "Authentication failed.", Toast.LENGTH_SHORT).show();
            }
        }
        if( i == R.id.loginButton) {
            if(1 == 1) {
                Intent intent = new Intent(this, mentorActivity.class);
                startActivity(intent);
            }
            else {
                Intent intent = new Intent(this, studentActivity.class);
                startActivity(intent);
            }
        }
    }
}

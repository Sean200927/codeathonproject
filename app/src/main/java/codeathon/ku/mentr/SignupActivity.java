package codeathon.ku.mentr;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.*;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.IgnoreExtraProperties;

import static codeathon.ku.mentr.R.id.firstName;
import static codeathon.ku.mentr.R.id.lastName;

public class SignupActivity extends AppCompatActivity {

    private static final String TAG = "NewSignUpActivity";
    private static final String REQUIRED = "Required";
    private static final String NOMATCH = "Passwords Must Match";

    //Declare database reference
    private DatabaseReference mDatabase;


    private EditText mFirstName;
    private EditText mLastName;
    private EditText mEmail;
    private EditText mPassword;
    private EditText mPasswordConfirm;
    private Button mSignUpButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        //Initialize Database
        mDatabase = FirebaseDatabase.getInstance().getReference();

        mFirstName = (EditText) findViewById(firstName);
        mLastName = (EditText) findViewById(lastName);
        mEmail = (EditText) findViewById(R.id.email);
        mPassword = (EditText) findViewById(R.id.password);
        mPasswordConfirm = (EditText) findViewById(R.id.passwordConfirm);
        mSignUpButton = (Button) FindViewById(R.id.signUpButton);

        mSignUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                submitPost();
            }
        });
    }

    private void createNew() {
        final String first = mFirstName.getText().toString();
        final String last = mLastName.getText().toString();
        final String email = mEmail.getText().toString();
        final String pass = mPassword.getText().toString();
        final String passc = mPasswordConfirm.getText().toString();

        //Name required
        if (TextUtils.isEmpty(first)) {
            mFirstName.setError(REQUIRED);
            return;
        }
        if (TextUtils.isEmpty(last)) {
            mLastName.setError(REQUIRED);
            return;
        }

        //Email required
        if(TextUtils.isEmpty(email)) {
            mEmail.setError(REQUIRED);
            return;
        }

        //Password required. Also passwords must match
        if (TextUtils.isEmpty(pass)) {
            mPassword.setError(REQUIRED);
            return;
        }
        if (TextUtils.isEmpty(passc)) {
            mPasswordConfirm.setError(REQUIRED);
            return;
        }

        if (pass != passc) {
            mPasswordConfirm.setError(NOMATCH);
            return;
        }

        //Restrict multiple taps of button
        setEditingEnabled(false);
        Toast.makeText(this, "Signing up...", Toast.LENGTH_SHORT).show();

        final String userID = getUid();
        mDatabase.child("users").addListenerForSingleValueEvent(
                createNewUser(first, last, email, pass));

    }

    private void setEditingEnabled(boolean enabled){
        mFirstName.setEnabled(enabled);
        mLastName.setEnabled(enabled);
        mEmail.setEnabled(enabled);
        mPassword.setEnabled(enabled);
        mPasswordConfirm.setEnabled(enabled);

        if(enabled) {
            mSignUpButton.setVisibility(View.VISIBLE);
        }
        else {
            mSignUpButton.setVisibility(View.GONE);
        }
    }
    private void createNewUser(String first, String last, String email, String password){
        String key = mDatabase.child("users").push().getKey();
        User user = new User(first, last, email, password);

        mDatabase.child("users").child(userId).setValue(user);
    }

    @IgnoreExtraProperties
    public class User {

        public String firstName;
        public String lastName;
        public String email;

        public User(){

        }

        public User(String first, String last, String email){
            this.firstName = first;
            this.lastName = last;
            this.email = email;
        }
    }


}

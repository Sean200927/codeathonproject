package codeathon.ku.mentr;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.*;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.IgnoreExtraProperties;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.Map;

import static codeathon.ku.mentr.R.id.firstName;
import static codeathon.ku.mentr.R.id.lastName;
import static codeathon.ku.mentr.R.id.username;

public class SignupActivity extends AppCompatActivity {

    private static final String REQUIRED = "Required";
    private static final String NOMATCH = "Passwords Must Match";

    //Initialize Database
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference users = database.getReference("users");

    private EditText newFirstName;
    private EditText newLastName;
    private EditText newUsername;
    private EditText newEmail;
    private EditText newPassword;
    private EditText newPasswordConfirm;
    private Button newSignUpButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        newFirstName = (EditText) findViewById(firstName);
        newLastName = (EditText) findViewById(lastName);
        newUsername = (EditText) findViewById(username);
        newEmail = (EditText) findViewById(R.id.email);
        newPassword = (EditText) findViewById(R.id.password);
        newPasswordConfirm = (EditText) findViewById(R.id.passwordConfirm);
        newSignUpButton = (Button) findViewById(R.id.signUpButton);

        newSignUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createNewUser();
            }
        });

    }

    private void createNewUser() {
        final String first = newFirstName.getText().toString();
        final String last = newLastName.getText().toString();
        final String username = newUsername.getText().toString();
        final String email = newEmail.getText().toString();
        final String pass = newPassword.getText().toString();
        final String passc = newPasswordConfirm.getText().toString();

        //Name required
        if (TextUtils.isEmpty(first)) {
            newFirstName.setError(REQUIRED);
            return;
        }
        if (TextUtils.isEmpty(last)) {
            newLastName.setError(REQUIRED);
            return;
        }

        //Email required
        if (TextUtils.isEmpty(email)) {
            newEmail.setError(REQUIRED);
            return;
        }

        //Password required. Also passwords must match
        if (TextUtils.isEmpty(pass)) {
            newPassword.setError(REQUIRED);
            return;
        }
        if (TextUtils.isEmpty(passc)) {
            newPasswordConfirm.setError(REQUIRED);
            return;
        }

        if (!pass.equals(passc)) {
            newPasswordConfirm.setError(NOMATCH);
            return;
        }

        //Restrict multiple taps of button
        setEditingEnabled(false);
        Toast.makeText(this, "Signing up...", Toast.LENGTH_SHORT).show();

        users.child("users").setValue(username);
        users.child(username).child("first").setValue(first);
        users.child(username).child("last").setValue(last);
        users.child(username).child("email").setValue(email);

    }

    @IgnoreExtraProperties
    public static class User {

        public String firstName;
        public String lastName;
        public String username;

        public User(String firstName, String lastName) {
            // ...
        }

        public User(String firstName, String lastName, String username) {
            // ...
        }

    }

    private void setEditingEnabled(boolean enabled) {
        newFirstName.setEnabled(enabled);
        newLastName.setEnabled(enabled);
        newUsername.setEnabled(enabled);
        newEmail.setEnabled(enabled);
        newPassword.setEnabled(enabled);
        newPasswordConfirm.setEnabled(enabled);

        if (enabled) {
            newSignUpButton.setVisibility(View.VISIBLE);
        } else {
            newSignUpButton.setVisibility(View.GONE);
        }
    }


}

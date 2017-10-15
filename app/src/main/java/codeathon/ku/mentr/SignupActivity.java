package codeathon.ku.mentr;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.*;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class SignupActivity extends AppCompatActivity {

    //Finals for required items
    private static final String REQUIRED = "Required";

    //Initialize Database
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference users = database.getReference("users");

    //Initialize variables
    private EditText newFirstName;
    private EditText newLastName;
    private Button newSignUpButton;
    private RadioButton newStudentButton;
    private RadioButton newMentorButton;

    //Happens when opened.
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        newFirstName = (EditText) findViewById(R.id.firstName);
        newLastName = (EditText) findViewById(R.id.lastName);
        newSignUpButton = (Button) findViewById(R.id.signUpButton);
        newStudentButton = (RadioButton) findViewById(R.id.studentRadio);
        newMentorButton = (RadioButton) findViewById(R.id.mentorRadio);

        //Listens for click, when clicked run createNewUser
        newSignUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createNewUser();
            }
        });

    }

    //Create new user. Check for proper inputs. Input in Firebase.
    private void createNewUser() {
        final String FIRST = newFirstName.getText().toString();
        final String LAST = newLastName.getText().toString();
        final String EMAIL = "FILLER";
        //Name required
        if (TextUtils.isEmpty(FIRST)) {
            newFirstName.setError(REQUIRED);
            return;
        }
        if (TextUtils.isEmpty(LAST)) {
            newLastName.setError(REQUIRED);
            return;
        }

        //Restrict multiple taps of button
        setEditingEnabled(false);
        Toast.makeText(this, "Signing up...", Toast.LENGTH_SHORT).show();

        //Add user to Database.
        users.child(EMAIL).child("first").setValue(FIRST);
        users.child(EMAIL).child("last").setValue(LAST);
         if(newStudentButton.isChecked()) {
            users.child(EMAIL).child("mentor").setValue(false);
        } else if (newMentorButton.isChecked()){
             users.child(EMAIL).child("mentor").setValue(true);
        }
    }

    //Disable editing while creating user.
    private void setEditingEnabled(boolean enabled) {
        newFirstName.setEnabled(enabled);
        newLastName.setEnabled(enabled);

        //Makes Sign Up Button disappear
        if (enabled) {
            newSignUpButton.setVisibility(View.VISIBLE);
        } else {
            newSignUpButton.setVisibility(View.GONE);
        }
    }
}

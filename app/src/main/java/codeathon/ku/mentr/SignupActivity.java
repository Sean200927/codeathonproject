package codeathon.ku.mentr;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

public class SignupActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
    }

    public void BeginSignup(View view)
    {
        //Tracks if the username/password combo was correct
        boolean isValid = false;
        //Tracks whether the user is a mentor
        boolean isMentor = false;

        EditText usernameText = (EditText) findViewById(R.id.emailEditText);
        String username = usernameText.getText().toString();

        EditText passText = (EditText) findViewById(R.id.passwordEditText);
        String password = passText.getText().toString();

        EditText passConfirmText = (EditText) findViewById(R.id.passConfirmEditText);
        String passwordConfirm = passConfirmText.getText().toString();
        /* Should compare username to database - if username exists, check password.
         * If password is correct, grab mentor property and use it to continue to correct page
         */
        if(isValid)
        {
            if(isMentor)
            {
                //Goto mentor management screen
            }


            //Goto mentor selection
        }
    }
}

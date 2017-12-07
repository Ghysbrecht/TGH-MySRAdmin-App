package be.chickendinnerinc.school.dbtest;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class RegisterActivity extends AppCompatActivity implements View.OnClickListener{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        Button registerButton = findViewById(R.id.submitButton);
        registerButton.setOnClickListener(this);
    }

    private String hashPassword(String password) throws NoSuchAlgorithmException {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hash = digest.digest(password.getBytes(StandardCharsets.UTF_8));
            return new String(hash);
    }

    public void registerHandler(View view){
        String firstname = ((EditText) findViewById(R.id.firstnameField)).getText().toString();
        String lastname = ((EditText) findViewById(R.id.lastnameField)).getText().toString();
        String email = ((EditText) findViewById(R.id.emailField)).getText().toString();
        String password = ((EditText) findViewById(R.id.passwordField)).getText().toString();

        try {
            String hashedPassword = hashPassword(password);
            User newUser = new User(firstname, lastname, hashedPassword, email);
            UserRegisterTask registerTask = new UserRegisterTask(newUser);
            registerTask.execute((Void) null);
        }catch(NoSuchAlgorithmException e){
            Toast.makeText(getApplicationContext(), "Oh no! Our little minions failed to hash you password :(", Toast.LENGTH_SHORT).show();
        }

    }

    @Override
    public void onClick(View view) {
        registerHandler(view);
    }


    public class UserRegisterTask extends AsyncTask<Void, Void, Boolean> {

        private final User user;

        UserRegisterTask(User user) {
            this.user = user;
        }

        @Override
        protected Boolean doInBackground(Void... params) {
            AppDatabase.getInstance(getApplicationContext()).userDao().insertAll(user);
            return true;
        }

        @Override
        protected void onPostExecute(final Boolean success) {
            if (success) {
                finish();
            } else {
                Toast.makeText(getApplicationContext(), "Create user failed!", Toast.LENGTH_SHORT).show();
            }
        }
    }


}

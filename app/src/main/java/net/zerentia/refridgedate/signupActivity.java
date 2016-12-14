package net.zerentia.refridgedate;

import android.app.LoaderManager;
import android.content.Loader;
import android.database.Cursor;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.math.BigInteger;
import java.net.URL;
import java.nio.charset.Charset;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import static net.zerentia.refridgedate.R.id.button;

/**
 * Created by Elev on 12/14/2016.
 */

public class signupActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<Cursor>
{

    private UserSignUpTask mAuthTask = null;

    Button signUpButton;
    private EditText passwordField;
    private EditText password2Field;

    private EditText emailField;
    private EditText surNameField;
    private EditText firstNameField;

    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        signUpButton= (Button) findViewById(R.id.sign_up_go);



        signUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                attemptSignup();
            }
        });


        passwordField = (EditText) findViewById(R.id.password_input);
        password2Field = (EditText) findViewById(R.id.password_input2);
        emailField = (EditText) findViewById(R.id.email_input);
        firstNameField = (EditText) findViewById(R.id.fname_input);
        surNameField = (EditText) findViewById(R.id.sname_input);
    }







    private boolean attemptSignup()
    {
        boolean res = false;
        boolean succ = true;

        String p1 = passwordField.getText().toString();
        String p2 = password2Field.getText().toString();
        if(!p1.equals(p2))
        {
            succ = false;
            Log.d("Signup fail", "password missmatch");
            Log.d("Signup Pass1", passwordField.getText().toString());
            Log.d("Signup Pass2", password2Field.getText().toString());
        }

        if(passwordField.getText().toString().length() < 5)
        {
            succ = false;
            Log.d("Signup fail", "password to short");
        }

        if(!emailField.getText().toString().contains("@"))
        {
            succ = false;
            Log.d("Signup fail", "invalid email");
        }

        if(surNameField.getText().toString().length() == 0 || firstNameField.getText().toString().length() == 0)
        {
            succ = false;
            Log.d("Signup fail", "no name");
        }

        if(succ)
        {
            mAuthTask = new UserSignUpTask(emailField.getText().toString(),passwordField.getText().toString(), surNameField.getText().toString(), firstNameField.getText().toString());
            mAuthTask.execute((Void) null);
        }

        return res;
    }

//    private boolean doSignUp()
//    {
//        boolean response = false;
//
//        String sPassword = md5(passwordField.getText().toString());
//        String sEmail = emailField.getText().toString();
//
//        String sSurName = surNameField.getText().toString();
//        String sFirstName = firstNameField.getText().toString();
//
//        String url =
//
//        try {
//            JSONObject json = readJsonFromUrl(url);
//            response = json.getBoolean("result");
//            Log.d("Signup fail", "Json response: " + String.valueOf(response));
//        } catch (IOException e) {
//            e.printStackTrace();
//        } catch (JSONException e) {
//            e.printStackTrace();
//        }
//
//        return response;
//    }





    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        return null;
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {

    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {

    }

    public class UserSignUpTask extends AsyncTask<Void, Void, Boolean> {

        private String sEmail;
        private String sPassword;
        private String sFirstName;
        private String sSurName;


        UserSignUpTask(String email, String password, String surName, String firstName) {
            sEmail = email;
            sPassword = md5(password);
            sSurName = surName;
            sFirstName = firstName;
        }


        @Override
        protected Boolean doInBackground(Void... params) {
            // TODO: attempt authentication against a network service.


            boolean result = false;

            //TODO MAKE HTTPS
            String requestUrl = "http://www.zerentia.net/workspace/refdate/requests/signup.php?email=" + sEmail + "&pass=" + sPassword + "&fname=" + sFirstName + "&sname=" + sSurName;

            try {
                JSONObject json = readJsonFromUrl(requestUrl);

                result = json.getBoolean("result");
            } catch (IOException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            }

            return result;
        }

        @Override
        protected void onPostExecute(final Boolean success) {
            mAuthTask = null;


            if (success) {
                finish();
            } else {
            }
        }

        @Override
        protected void onCancelled() {
            mAuthTask = null;
        }
    }

    public static String md5(String s) {
        MessageDigest digest;
        try {
            digest = MessageDigest.getInstance("MD5");
            digest.update(s.getBytes(Charset.forName("US-ASCII")), 0, s.length());
            byte[] magnitude = digest.digest();
            BigInteger bi = new BigInteger(1, magnitude);
            String hash = String.format("%0" + (magnitude.length << 1) + "x", bi);
            return hash;
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }

        return "";
    }


    public static JSONObject readJsonFromUrl(String url) throws IOException, JSONException {

        InputStream is = new URL(url).openStream();
        try {
            BufferedReader rd = new BufferedReader(new InputStreamReader(is, Charset.forName("UTF-8")));
            String jsonText = readAll(rd);
            JSONObject json = new JSONObject(jsonText);
            return json;
        } finally {
            is.close();
        }
    }

    private static String readAll(Reader rd) throws IOException {
        StringBuilder sb = new StringBuilder();
        int cp;
        while ((cp = rd.read()) != -1) {
            sb.append((char) cp);
        }
        return sb.toString();
    }
}


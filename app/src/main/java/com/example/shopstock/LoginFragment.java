package com.example.shopstock;


import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.shopstock.sql.DatabaseHelper;

import butterknife.BindView;

import static android.app.Activity.RESULT_OK;


/**
 * A simple {@link Fragment} subclass.
 */
public class LoginFragment extends Fragment implements View.OnClickListener{
    private static final String TAG = "Login";
    private static final int REQUEST_SIGNUP = 0;

    public LoginFragment() {
        // Required empty public constructor
    }

    @BindView(R.id.btn_login) Button login_button;
    @BindView(R.id.et_username) EditText login_username;
    @BindView(R.id.et_password) EditText login_password;
    private DatabaseHelper databaseHelper;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_login, container, false);
        login_button = view.findViewById(R.id.btn_login);
        login_username = view.findViewById(R.id.et_username);
        login_password = view.findViewById(R.id.et_password);

        login_button.setOnClickListener(this);
        initObjects();
        return view;
    }

    @Override
    public void onClick(View v){
        login();
    }

    private void initObjects() {
        databaseHelper = new DatabaseHelper(getActivity());
    }

    private void login() {
        Log.d(TAG, "Login");

        if (!validate()) {
            onLoginFailed();
            return;
        }

        login_button.setEnabled(false);

        String username = login_username.getText().toString();
        String password = login_password.getText().toString();

        if(!databaseHelper.checkUser(username, password)){
            onLoginFailed();
            return;
        }

        final ProgressDialog progressDialog = new ProgressDialog(getActivity());
        progressDialog.setIndeterminate(true);
        progressDialog.setMessage("Authenticating...");
        progressDialog.show();


        new android.os.Handler().postDelayed(
                new Runnable() {
                    public void run() {
                        // On complete call either onLoginSuccess or onLoginFailed
                        onLoginSuccess();
                        // onLoginFailed();
                        progressDialog.dismiss();
                    }
                }, 3000);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_SIGNUP) {
            if (resultCode == RESULT_OK) {

                Toast.makeText(getActivity().getBaseContext(), "Login Success", Toast.LENGTH_LONG).show();
                this.getActivity().finish();
                Intent intent = new Intent(getActivity(), HomeActivity.class);
                startActivity(intent);
            }
        }
    }

     public void onLoginSuccess() {
        login_button.setEnabled(true);
        getActivity().finish();
        Intent intent = new Intent(getActivity(), HomeActivity.class);
        startActivity(intent);
    }

    public void onLoginFailed() {
        Toast.makeText(getActivity().getBaseContext(), "Login failed", Toast.LENGTH_LONG).show();

        login_button.setEnabled(true);
    }

    public boolean validate() {
        boolean valid = true;

        String username = login_username.getText().toString();
        String password = login_password.getText().toString();

        if (username.isEmpty() || !android.util.Patterns.EMAIL_ADDRESS.matcher(username).matches()) {
            login_username.setError("enter a valid email address");
            valid = false;
        } else {
            login_username.setError(null);
        }

        if (password.isEmpty() || password.length() < 8 || password.length() > 20) {
            login_password.setError("between 8 and 20 alphanumeric characters");
            valid = false;
        } else {
            login_password.setError(null);
        }

        return valid;
    }
}


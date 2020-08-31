package com.example.shopstock;


import android.app.ProgressDialog;
import android.os.Bundle;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.shopstock.model.User;
import com.example.shopstock.sql.DatabaseHelper;

import butterknife.BindView;

import static android.app.Activity.RESULT_OK;


/**
 * A simple {@link Fragment} subclass.
 */
public class RegisterFragment extends Fragment implements View.OnClickListener {
    private static final String TAG = "SignUpFragment";

    public RegisterFragment() {
        // Required empty public constructor
    }

    @BindView(R.id.btn_register) Button register_button;
    @BindView(R.id.et_name) EditText register_name;
    @BindView(R.id.et_email) EditText register_email;
    @BindView(R.id.et_password) EditText register_password;
    @BindView(R.id.et_repassword) EditText register_re_password;
    private DatabaseHelper databaseHelper;
    private User user;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = (View) inflater.inflate(R.layout.fragment_register, container, false);
        register_button = (Button) view.findViewById(R.id.btn_register);
        register_name = (EditText) view.findViewById(R.id.et_name);
        register_email = (EditText) view.findViewById(R.id.et_email);
        register_password = (EditText) view.findViewById(R.id.et_password);
        register_re_password = (EditText) view.findViewById(R.id.et_repassword);

        register_button.setOnClickListener(this);
        initObjects();
        return view;
    }

    @Override
    public void onClick(View v) {
        signUp();
    }

    private void initObjects() {
        databaseHelper = new DatabaseHelper(getActivity());
        user = new User();
    }

    private void signUp() {
        Log.d(TAG, "SignUp");

        if (!validate()) {
            onSignUpFailed();
            return;
        }

        register_button.setEnabled(false);

        final ProgressDialog progressDialog = new ProgressDialog(getActivity());
        progressDialog.setIndeterminate(true);
        progressDialog.setMessage("Creating Account...");
        progressDialog.show();

        String name = register_name.getText().toString();
        String email = register_email.getText().toString();
        String password = register_password.getText().toString();
        String re_password = register_re_password.getText().toString();

        user.setName(name);
        user.setEmail(email);
        user.setPassword(password);
        databaseHelper.addUser(user);

        new android.os.Handler().postDelayed(
                new Runnable() {
                    public void run() {
                        // On complete call either onSignupSuccess or onSignupFailed
                        // depending on success
                        onSignUpSuccess();
                        // onSignUpFailed();
                        progressDialog.dismiss();
                    }
                }, 3000);
    }


    public void onSignUpSuccess() {
        register_button.setEnabled(true);
        getActivity().setResult(RESULT_OK, null);
        Toast.makeText(getActivity().getBaseContext(), "Success Signing Up! Swipe to login", Toast.LENGTH_LONG).show();
    }

    public void onSignUpFailed() {
        Toast.makeText(getActivity().getBaseContext(), "Sign up failed", Toast.LENGTH_LONG).show();

        register_button.setEnabled(true);
    }

    public boolean validate() {
        boolean valid = true;

        String name = register_name.getText().toString();
        String email = register_email.getText().toString();
        String password = register_password.getText().toString();
        String re_password = register_re_password.getText().toString();

        if (name.isEmpty() || name.length() < 2) {
            register_name.setError("at least 2 characters");
            valid = false;
        } else {
            register_name.setError(null);
        }

        if (email.isEmpty() || !android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            register_email.setError("enter a valid email address");
            valid = false;
        } else {
            register_email.setError(null);
        }

        if (password.isEmpty() || password.length() < 8 || password.length() > 20) {
            register_password.setError("between 8 and 20 alphanumeric characters");
            valid = false;
        } else {
            register_password.setError(null);
        }

        if (re_password.isEmpty() || !re_password.equals(password)) {
            register_re_password.setError("re-typed password does not match");
            valid = false;
        } else {
            register_re_password.setError(null);
        }

        return valid;
    }

}

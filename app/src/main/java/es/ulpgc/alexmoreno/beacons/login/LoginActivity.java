package es.ulpgc.alexmoreno.beacons.login;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import es.ulpgc.alexmoreno.beacons.R;
import es.ulpgc.alexmoreno.beacons.data.LoginUser;

public class LoginActivity
        extends AppCompatActivity implements LoginContract.View {

    public static String TAG = LoginActivity.class.getSimpleName();

    private LoginContract.Presenter presenter;

    // view attributes
    private EditText userEmail;
    private EditText userPassword;
    private TextView notAccount;
    private TextView forgotPassword;
    private Button loginButton;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        LoginScreen.configure(this);

        userEmail = findViewById(R.id.emailLogin);
        userPassword = findViewById(R.id.passwordLogin);
        progressBar = findViewById(R.id.progressbar);

        loginButton = findViewById(R.id.loginButton);
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startProgressBar();
                LoginUser user = new LoginUser(userEmail.getText().toString(), userPassword.getText().toString());
                presenter.onLoginButtonPressed(user);
            }
        });

        notAccount = findViewById(R.id.createAccount);
        notAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                presenter.onRegisterButtonPressed();
            }
        });

        forgotPassword = findViewById(R.id.forgottenButton);
        forgotPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                presenter.onForgottenButtonPressed();
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    public void injectPresenter(LoginContract.Presenter presenter) {
        this.presenter = presenter;
    }

    @Override
    public Activity getActivity() {
        return this;
    }

    @Override
    public void finishActivity() {
        finish();
    }

    /* ---------- UI Methods ---------- */

    @Override
    public void displayFillEmailAlert() {
        stopProgressBar();
        showAlert(getString(R.string.emailError),getString(R.string.emailMissedText));
    }

    @Override
    public void displayPasswordTooShort() {
        stopProgressBar();
        showAlert(getString(R.string.passwordError),getString(R.string.passwordTooShort));
    }

    @Override
    public void displayLoginErrorAlert() {
        stopProgressBar();
        showAlert(getString(R.string.loginError),getString(R.string.incorrectCredentials));
    }

    /**
     * Method to show Alert
     * @param title of the Alert
     * @param message of the Alert
     */
    private void showAlert(String title, String message) {
        AlertDialog.Builder alertBuilder = new AlertDialog.Builder(this);
        alertBuilder.setTitle(title);
        alertBuilder.setMessage(message);
        alertBuilder.setCancelable(true);

        alertBuilder.setNegativeButton(
                R.string.dismiss,
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });

        AlertDialog alertDialog = alertBuilder.create();
        alertDialog.show();
    }

    /**
     * Method to start the progress bar
     */
    private void startProgressBar() {
        progressBar.setVisibility(View.VISIBLE);
        progressBar.setEnabled(true);
    }

    /**
     * Method to stop the progress bar
     */
    private void stopProgressBar() {
        progressBar.setVisibility(View.INVISIBLE);
        progressBar.setEnabled(false);
    }
}

package es.ulpgc.alexmoreno.beacons.register;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import androidx.appcompat.app.AppCompatActivity;
import es.ulpgc.alexmoreno.beacons.R;
import es.ulpgc.alexmoreno.beacons.data.LoginUser;

public class RegisterActivity
        extends AppCompatActivity implements RegisterContract.View {

    public static String TAG = RegisterActivity.class.getSimpleName();

    private RegisterContract.Presenter presenter;

    // view attributes
    private EditText userName;
    private EditText userSurname;
    private EditText userEmail;
    private EditText userPassword;
    private Button registerButton;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        RegisterScreen.configure(this);

        userName = findViewById(R.id.nameRegister);
        userSurname = findViewById(R.id.surnameRegister);
        userEmail = findViewById(R.id.emailRegister);
        userPassword = findViewById(R.id.passwordRegister);
        progressBar = findViewById(R.id.progressbar);

        registerButton = findViewById(R.id.registerButton);
        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startProgressBar();
                LoginUser user = new LoginUser(
                        userName.getText().toString(),
                        userSurname.getText().toString(),
                        userEmail.getText().toString(),
                        userPassword.getText().toString()
                );
                presenter.onRegisterButtonPressed(user);
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    public void injectPresenter(RegisterContract.Presenter presenter) {
        this.presenter = presenter;
    }

    /* ---------- UI Methods ---------- */

    @Override
    public void displayFillAllFieldsMessage() {
        stopProgressBar();
        showAlert(getString(R.string.registerError),getString(R.string.emptyFields));
    }

    @Override
    public void displayPasswordTooShort() {
        stopProgressBar();
        showAlert(getString(R.string.registerError),getString(R.string.passwordTooShort));
    }

    @Override
    public void displayError() {
        stopProgressBar();
        showAlert(getString(R.string.registerError),getString(R.string.somethingWasWrong));
    }

    @Override
    public void displayRegisteredSuccessful() {
        stopProgressBar();
        showAlert(getString(R.string.registerSuccessfulTitle),getString(R.string.registerSuccessful));
        presenter.routeToLogin();
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
     * Method to star the progress bar
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

package es.ulpgc.alexmoreno.beacons.forgotten;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import androidx.appcompat.app.AppCompatActivity;
import es.ulpgc.alexmoreno.beacons.R;

public class ForgottenActivity
        extends AppCompatActivity implements ForgottenContract.View {

    public static String TAG = ForgottenActivity.class.getSimpleName();

    private ForgottenContract.Presenter presenter;

    private Button sendPasswordButton;
    private EditText userEmail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgotten);

        ForgottenScreen.configure(this);

        userEmail = findViewById(R.id.emailForgotten);

        sendPasswordButton = findViewById(R.id.sendPasswordButton);
        sendPasswordButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                presenter.onSendPasswordButtonPressed(userEmail.getText().toString());
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    public void injectPresenter(ForgottenContract.Presenter presenter) {
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

    @Override
    public void displayFillEmailAlert() {
        showAlert(getString(R.string.emailError),getString(R.string.emailMissedText));
    }

    @Override
    public void displaySendPasswordErrorAlert() {
        showAlert(getString(R.string.resetPasswordError),getString(R.string.wrongEmail));
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
}

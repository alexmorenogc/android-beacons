package es.ulpgc.alexmoreno.beacons.detail;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import es.ulpgc.alexmoreno.beacons.R;
import es.ulpgc.alexmoreno.beacons.data.Local;

public class DetailActivity
        extends AppCompatActivity implements DetailContract.View, OnMapReadyCallback {

    public static String TAG = DetailActivity.class.getSimpleName();

    private DetailContract.Presenter presenter;
    private TextView name;
    private TextView description;
    private TextView phone;
    private TextView website;
    private Local local;
    private GoogleMap map;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        name = findViewById(R.id.valueNameDetail);
        description = findViewById(R.id.valueDescriptionDetail);
        phone = findViewById(R.id.valuePhoneDetail);
        website = findViewById(R.id.valueWebsiteDetail);


        androidx.appcompat.app.ActionBar toolbar = getSupportActionBar();
        if (toolbar != null) {
            toolbar.setDisplayHomeAsUpEnabled(true);
            toolbar.setDisplayShowHomeEnabled(true);
        }

        MapFragment mapFragment = (MapFragment) getFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        DetailScreen.configure(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    public void injectPresenter(DetailContract.Presenter presenter) {
        this.presenter = presenter;
    }

    @Override
    public void displayData(DetailViewModel viewModel) {
        if (viewModel.localSelected != null) {
            name.setText(viewModel.localSelected.getName());
            description.setText(viewModel.localSelected.getDescription());
            phone.setText(viewModel.localSelected.getPhone());
            website.setText(viewModel.localSelected.getWebsite());
            local = viewModel.localSelected;
            LatLng localPosition = new LatLng(local.getLatitude(), local.getLongitude());
            map.addMarker(new MarkerOptions().position(localPosition)
                    .title(local.getName()));
            map.moveCamera(CameraUpdateFactory.newLatLngZoom(localPosition,10));
            map.animateCamera(CameraUpdateFactory.zoomTo(15));
        }
    }

    @Override
    public void showErrorGettingItem() {
        AlertDialog.Builder alertBuilder = new AlertDialog.Builder(this);
        alertBuilder.setTitle(R.string.errorLoading);
        alertBuilder.setMessage(R.string.errorLoadingLocal);
        alertBuilder.setCancelable(true);

        alertBuilder.setNegativeButton(R.string.dismiss,
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                        onBackPressed();
                    }
                });

        AlertDialog alertDialog = alertBuilder.create();
        alertDialog.show();
    }


    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        map = googleMap;
        presenter.fetchDetailData();
    }
}

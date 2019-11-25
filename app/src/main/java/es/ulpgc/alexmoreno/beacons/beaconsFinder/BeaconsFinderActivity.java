package es.ulpgc.alexmoreno.beacons.beaconsFinder;

import android.Manifest;
import android.app.AlertDialog;
import android.bluetooth.BluetoothAdapter;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.os.RemoteException;
import android.provider.Settings;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;


import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;
import com.skyfishjy.library.RippleBackground;

import org.altbeacon.beacon.Beacon;
import org.altbeacon.beacon.BeaconConsumer;
import org.altbeacon.beacon.BeaconManager;
import org.altbeacon.beacon.BeaconParser;
import org.altbeacon.beacon.Identifier;
import org.altbeacon.beacon.RangeNotifier;
import org.altbeacon.beacon.Region;

import java.util.ArrayList;
import java.util.Collection;

import es.ulpgc.alexmoreno.beacons.R;

import static android.Manifest.permission.ACCESS_COARSE_LOCATION;

public class BeaconsFinderActivity
        extends AppCompatActivity implements BeaconsFinderContract.View, BeaconConsumer, RangeNotifier {

    public static String TAG = BeaconsFinderActivity.class.getSimpleName();

    private BeaconsFinderContract.Presenter presenter;

    // attributes for permissions
    private static final int PERMISSION_REQUEST_COARSE_LOCATION = 1;
    private static final int REQUEST_ENABLE_BLUETOOTH = 1;

    // attributes for location
    private FusedLocationProviderClient fusedLocationClient;
    Location lastLocation;

    // attributes for Beacons services
    private BeaconManager beaconManager;
    private Region beaconRegion;
    private static final String ALL_BEACONS_REGION = "AllBeaconsRegion";
    private Button toMaster;


    // background effect
    boolean effect = false;
    RippleBackground rippleBackground;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_beacons_finder);

        BeaconsFinderScreen.configure(this);

        toMaster = findViewById(R.id.toMaster);
        toMaster.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                presenter.onToMasterPressed();
            }
        });
        toMaster.setEnabled(false);

        // init beacon service values
        beaconManager = BeaconManager.getInstanceForApplication(this);
        beaconManager.getBeaconParsers().add(new BeaconParser().
                setBeaconLayout(BeaconParser.EDDYSTONE_UID_LAYOUT));
        beaconManager.getBeaconParsers().add(new BeaconParser()
                .setBeaconLayout(BeaconParser.EDDYSTONE_URL_LAYOUT));
        beaconManager.getBeaconParsers().add(new BeaconParser()
                .setBeaconLayout(BeaconParser.EDDYSTONE_TLM_LAYOUT));
        // creating the region for the beacons
        ArrayList<Identifier> identifiers = new ArrayList<>();
        beaconRegion = new Region(ALL_BEACONS_REGION, identifiers);

        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this);

        // radar effect
        rippleBackground = findViewById(R.id.content);
        ImageView imageView = findViewById(R.id.beaconIcon);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (effect) {
                    stopAnimation();
                    stopDetectingBeacons();
                } else {
                    startAnimation();
                    prepareAllPermissions();
                }
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();

        fusedLocationClient.getLastLocation()
                .addOnSuccessListener(this, new OnSuccessListener<Location>() {
                    @Override
                    public void onSuccess(Location location) {
                        if (location != null) {
                            lastLocation = location;
                        }
                    }
                });
    }

    @Override
    protected void onDestroy() {
        try {
            beaconManager.stopRangingBeaconsInRegion(beaconRegion);
        } catch (RemoteException e) {
            Log.e(TAG, "stopBeaconRanging: exception", e);
        }
        beaconManager.removeAllRangeNotifiers();
        beaconManager.unbind(this);
        super.onDestroy();
    }

    @Override
    public void injectPresenter(BeaconsFinderContract.Presenter presenter) {
        this.presenter = presenter;
    }

    /* ---------- Beacons Methods ---------- */

    @Override
    public void onBeaconServiceConnect() {
        try {
            beaconManager.startRangingBeaconsInRegion(beaconRegion);
        } catch (RemoteException e) {
            Log.e(TAG, "onBeaconServiceConnect: ", e);
        }
        beaconManager.addRangeNotifier(this);
    }

    /**
     * Method to stop the beacon's detection
     */
    private void stopDetectingBeacons() {
        try {
            beaconManager.stopRangingBeaconsInRegion(beaconRegion);
        } catch (RemoteException e) {
            Log.e(TAG, "stopDetectingBeacons: ", e);
        }
        beaconManager.removeAllRangeNotifiers();
        beaconManager.unbind(this);
    }

    @Override
    public void didRangeBeaconsInRegion(Collection<Beacon> collection, Region region) {
        if (collection.size() == 0) {
            Log.d(TAG, "didRangeBeaconsInRegion: No beacons found");
        } else {
            for (Beacon beacon : collection) {
                Log.d(TAG, "didRangeBeaconsInRegion: Beacon found... \n"
                        + "Name: " + beacon.getBluetoothName() + "\n"
                        + "Address: " + beacon.getBluetoothAddress() + "\n"
                        + "Type: " + beacon.getBeaconTypeCode() + "\n"
                        + "ID1:" + beacon.getId1() + "\n"
                        //+ "ID2:" + beacon.getId2() + "\n" ------>>>>>> only works in iBKS105
                        + "Manufacturer:" + beacon.getManufacturer() + "\n"
                );
                onBeaconFoundEnableMasterDetail(beacon);
            }
        }
    }

    /**
     * Method to show a toast with the beacon information and call the presenter with it
     * @param beacon
     */
    private void onBeaconFoundEnableMasterDetail(Beacon beacon) {
        makeToastShort(getString(R.string.found) + beacon.getBluetoothName());
        presenter.onBeaconFound(beacon.getId1().toString(), lastLocation);
    }

    /**
     * Method to start the detection after check every permission and services enable
     */
    private void onAllPermissionsEnabledStartDetecting() {
        beaconManager.setForegroundScanPeriod(BeaconManager.DEFAULT_BACKGROUND_SCAN_PERIOD);
        beaconManager.bind(this);
    }

    @Override
    public void enableToMasterButtonAndStopSearch() {
        stopDetectingBeacons();
        enableToMasterAndStopAnimation();
    }

    /* ---------- Services Methods ---------- */

    /**
     * Method to check if Location and Bluetooth are enabled
     */
    private void checkLocationAndBluetoothIsEnabled() {
        if (!isLocationEnabled()) {
            askToTurnOnLocation();
        } else {
            BluetoothAdapter bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
            if (bluetoothAdapter != null) {
                if (bluetoothAdapter.isEnabled()) {
                    onAllPermissionsEnabledStartDetecting();
                } else {
                    Intent intent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
                    startActivityForResult(intent, REQUEST_ENABLE_BLUETOOTH);
                }
            } else {
                Log.d(TAG, "checkLocationAndBluetoothIsEnabled: BT not supported");
            }
        }
    }

    /**
     * Method to ask user to turn on the Location
     */
    private void askToTurnOnLocation() {
        AlertDialog.Builder dialog = new AlertDialog.Builder(this);
        dialog.setMessage(R.string.turnOnLocationMessage);
        dialog.setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface paramDialogInterface, int paramInt) {
                Intent myIntent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                startActivity(myIntent);
            }
        });
        dialog.show();
    }

    /**
     * Method to check the Location enabled
     * @return boolean
     */
    private boolean isLocationEnabled() {
        LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        try {
            final boolean gpsEnabled = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
            final boolean networkEnabled = locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
            return gpsEnabled || networkEnabled;
        } catch (Exception e) {
            Log.e(TAG, "isLocationEnabled: ", e);
            return false;
        }
    }

    /* ---------- Permissions Methods ---------- */

    /**
     * Method to check the SDK version because it changed the way to check the permissions
     */
    private void prepareAllPermissions() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (checkSelfPermission(ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                checkLocationAndBluetoothIsEnabled();
                fusedLocationClient = LocationServices.getFusedLocationProviderClient(this);
            } else {
                askForLocationPermissions();
            }
        } else {
            checkLocationAndBluetoothIsEnabled();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        if (requestCode == PERMISSION_REQUEST_COARSE_LOCATION) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                checkLocationAndBluetoothIsEnabled();
            } else {
                showAlert(getString(R.string.permissionsRequired), getString(R.string.grantLocationPermissions));
                askForLocationPermissions();
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (requestCode == REQUEST_ENABLE_BLUETOOTH) {
            if (resultCode == RESULT_OK) {
                onAllPermissionsEnabledStartDetecting();
            } else if (resultCode == RESULT_CANCELED) {
                Log.d(TAG, "onActivityResult: BT not enabled");
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    /**
     * Method to create an alert asking for the permissions
     */
    private void askForLocationPermissions() {
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(R.string.askForLocationPermissionsTitle);
        builder.setPositiveButton(android.R.string.ok, null);
        builder.setOnDismissListener(new DialogInterface.OnDismissListener() {
            @RequiresApi(api = Build.VERSION_CODES.M)
            public void onDismiss(DialogInterface dialog) {
                requestPermissions(new String[]{Manifest.permission.ACCESS_COARSE_LOCATION},
                        PERMISSION_REQUEST_COARSE_LOCATION);
            }
        });
        builder.show();
    }

    /* ---------- UI Methods ---------- */

    /**
     * Method to stop the background radar animation and enable the toMaster button
     */
    private void enableToMasterAndStopAnimation() {
        stopAnimation();
        toMaster.setEnabled(true);
    }

    @Override
    public void showErrorLoadingPlace() {
        showAlert(getString(R.string.errorLoading),
                getString(R.string.errorLoadingPlaceInBeaconsFinder));
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
     * Method to create a custom toast for the beacon found
     * @param message of the toast
     */
    private void makeToastShort(String message) {
        LayoutInflater inflater = getLayoutInflater();
        View layout = inflater.inflate(R.layout.toast_layout,
                (ViewGroup) findViewById(R.id.toast_layout));

        TextView text = layout.findViewById(R.id.textToShow);
        text.setText(message);

        Toast toast = new Toast(getApplicationContext());
        toast.setGravity(Gravity.CENTER_VERTICAL, 0, 0);
        toast.setDuration(Toast.LENGTH_LONG);
        toast.setView(layout);
        toast.show();
    }

    /* ---------- Animation Methods ---------- */

    /**
     * Method to start the background radar animation
     */
    private void startAnimation() {
        rippleBackground.startRippleAnimation();
        this.effect = true;
    }

    /**
     * Method to stop the background radar animation
     */
    private void stopAnimation() {
        rippleBackground.stopRippleAnimation();
        this.effect = false;
    }
}

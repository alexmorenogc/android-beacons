package es.ulpgc.alexmoreno.beacons.app;

import android.content.Context;
import android.location.Location;
import android.util.Log;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import es.ulpgc.alexmoreno.beacons.R;
import es.ulpgc.alexmoreno.beacons.data.Checkin;
import es.ulpgc.alexmoreno.beacons.data.Local;
import es.ulpgc.alexmoreno.beacons.data.LoginUser;
import es.ulpgc.alexmoreno.beacons.data.MasterItem;

public class Repository implements RepositoryInterface {
    public static String TAG = Repository.class.getSimpleName();

    // singleton attributes
    private static Repository INSTANCE;
    private Context context;

    // firebase attributes
    private FirebaseAuth mAuth;
    private String currentUserUID = "";
    private FirebaseDatabase database;

    /**
     * Constructor with context
     * @param context of the activity
     */
    private Repository(Context context) {
        this.context = context;
    }

    /**
     * getInstance of Repository
     * @param context of the activity
     * @return the instance
     */
    public static Repository getInstance(Context context) {
        if (INSTANCE == null) {
            INSTANCE = new Repository(context);
        }
        return INSTANCE;
    }

    @Override
    public void registerNewCheckInAndGetPlace(final String beaconUUID, Location location,
                                              final RepositoryInterface.OnPlaceFetchedCallback callback) {
        final long time = System.currentTimeMillis();
        final String locationLatitude = Double.toString(location.getLatitude());
        final String locationLongitude = Double.toString(location.getLongitude());

        database = FirebaseDatabase.getInstance();
        database.getReference().child("beacons")
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        String placeUUID = "";
                        String temp;
                        for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                            temp = (String) postSnapshot.child("UUID").getValue();
                            if (beaconUUID.equals(temp)) {
                                placeUUID = (String) postSnapshot.child("placeUUID").getValue();
                                callback.setPlaceUUID(placeUUID, false);
                            }
                        }
                        Checkin checkin = new Checkin(mAuth.getCurrentUser().getUid(),
                                beaconUUID,placeUUID, locationLongitude, locationLatitude, time);
                        onPlaceFoundSaveToFirebase(checkin);
                    }
                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {
                        Log.e(TAG, "onCancelled: ", databaseError.toException());
                        Checkin checkin = new Checkin(mAuth.getCurrentUser().getUid(),
                                beaconUUID,beaconUUID, locationLongitude, locationLatitude, time);
                        callback.setPlaceUUID(context.getString(R.string.notFound), true);
                        onPlaceFoundSaveToFirebase(checkin);
                    }
                });
    }

    @Override
    public void loadDetailData(final String localUUID, final OnDetailDataFetchedCallback callback) {
        database = FirebaseDatabase.getInstance();
        database.getReference().child("locals").child(localUUID)
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        String name = (String) dataSnapshot.child("name").getValue();
                        String category = (String) dataSnapshot.child("category").getValue();
                        String description = (String) dataSnapshot.child("description").getValue();
                        String phone = (String) dataSnapshot.child("phone").getValue();
                        String website = (String) dataSnapshot.child("website").getValue();
                        Double longitude = (Double) dataSnapshot.child("longitude").getValue();
                        Double latitude = (Double) dataSnapshot.child("latitude").getValue();

                        Local local = new Local(localUUID,name,category,description,phone,website,
                                longitude,latitude);
                        callback.setDetailData(local);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {
                        Log.e(TAG, "onCancelled: ", databaseError.toException());
                    }
                });
    }

    @Override
    public void loadMasterItems(final String placeUUID, final OnMasterItemListFetchedCallback callback) {
        database = FirebaseDatabase.getInstance();
        database.getReference().child("locals")
                .addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                int i = 0;
                List<MasterItem> masterItemList = new ArrayList<>();
                for (DataSnapshot child : dataSnapshot.getChildren()) {
                    String name = child.child("name").getValue().toString();
                    String uuid = child.getKey();
                    masterItemList.add(new MasterItem(i,name,uuid));
                    i++;
                }
                onLocalsFetchedSetOnlyLocalsFromThePlace(placeUUID, masterItemList, callback);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.e(TAG, "onCancelled: error ", databaseError.toException() );
                callback.setMasterItemList(new ArrayList<MasterItem>());
            }
        });
    }

    /**
     * Method called by loadMasterItems to filter the local list
     * @param placeUUID used to filter the relation
     * @param masterItemList with all the locals
     * @param callback when the list has been filtered
     */
    private void onLocalsFetchedSetOnlyLocalsFromThePlace(final String placeUUID, final List<MasterItem> masterItemList, final OnMasterItemListFetchedCallback callback) {
        database.getReference().child("relations").orderByChild("placeUUID")
                .addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                List<MasterItem> masterItemListFiltered = masterItemList;
                for (DataSnapshot child : dataSnapshot.getChildren()) {
                    if (placeUUID.equals(child.child("placeUUID").getValue().toString())){
                        ArrayList<HashMap<String, String>> localUUIDs = (ArrayList<HashMap<String, String>>) child.child("localUUIDs").getValue();
                        if (!localUUIDs.isEmpty()) {
                            Log.d(TAG, "onDataChange: filtering by placeUUID:" + placeUUID);
                            masterItemListFiltered = new ArrayList<>();
                            for (HashMap<String, String> local: localUUIDs) {
                                for (MasterItem item: masterItemList) {
                                    if (local.get("value").equals(item.uuid)) {
                                        masterItemListFiltered.add(item);
                                    }
                                }
                            }
                            Log.d(TAG, "onDataChange: masterItemList has been filtered");
                        }
                    }
                }
                callback.setMasterItemList(masterItemListFiltered);
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.e(TAG, "onCancelled: ", databaseError.toException());
                callback.setMasterItemList(masterItemList);
            }
        });
    }

    /**
     * Method called to save into Firebase de new Check-in
     * @param checkin used to be saved
     */
    private void onPlaceFoundSaveToFirebase(Checkin checkin) {
        database = FirebaseDatabase.getInstance();
        database.getReference().child("checkin").push().setValue(checkin);
    }

    @Override
    public void registerNewUser(LoginUser user, RegisterNewUser callback) {
        mAuth = FirebaseAuth.getInstance();

        String password = user.getPassword();

        if (user.getEmail().equals("") || user.getName().equals("") || password.length() == 0) {
            callback.onNewUserRegistered(false, false, false);
        } else if (password.length() < 6) {
            callback.onNewUserRegistered(false, true,  true);
        }  else {
            createNewUser(user, callback);
        }
    }

    @Override
    public void sendNewPassword(String userMail, final NewPasswordInterface callback) {
        mAuth = FirebaseAuth.getInstance();

        if (userMail.equals("")){
            callback.onNewPasswordSent(true,false);
        } else {
            mAuth.sendPasswordResetEmail(userMail).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    if (task.isSuccessful()) {
                        callback.onNewPasswordSent(false,true);
                    } else {
                        Log.e(TAG, "onComplete: ", task.getException());
                        callback.onNewPasswordSent(true,true);
                    }
                }
            });
        }
    }

    /**
     * Method to create the new user into Firebase calling createUserWithEmailAndPassword
     * we could call another method like create with Facebook or another provider
     * @param user to be created
     * @param callback when the user has been created
     */
    private void createNewUser(final LoginUser user, final RegisterNewUser callback) {
        mAuth = FirebaseAuth.getInstance();
        createUserWithEmailAndPassword(user, callback);
    }

    /**
     * Method used to create the user into Firebase
     * @param user to be created
     * @param callback when the user has been created
     */
    private void createUserWithEmailAndPassword(final LoginUser user,
                                                final RegisterNewUser callback) {
        mAuth.createUserWithEmailAndPassword(user.getEmail(), user.getPassword())
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            currentUserUID = mAuth.getCurrentUser().getUid();

                            Map<String, String> newUser = new HashMap<String, String>();
                            newUser.put("name", user.getName());
                            newUser.put("surname", user.getSurname());
                            newUser.put("email", user.getEmail());
                            saveUserToDatabase(currentUserUID,newUser,callback);
                        } else {
                            Log.e(TAG, "onComplete: ", task.getException());
                            callback.onNewUserRegistered(true, false, true);
                        }
                    }
                });
    }

    /**
     * Method to save the user information into Firebase
     * @param userUID identifier used to save the data
     * @param newUser information of the user
     * @param callback when the information has been saved
     */
    private void saveUserToDatabase(String userUID, Map<String, String> newUser,
                                    final RegisterNewUser callback) {
        database = FirebaseDatabase.getInstance();

        database.getReference().child("users").child(userUID).setValue(newUser)
                .addOnCompleteListener(
                new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            callback.onNewUserRegistered(false, false,true);
                        } else {
                            Log.e(TAG, "onComplete: ", task.getException());
                            callback.onNewUserRegistered(true, false,true);
                        }
                    }
                });
    }

    @Override
    public void isUserLoggedIn(CheckIfUserIsLoggedIn callback) {
        mAuth = FirebaseAuth.getInstance();

        if (mAuth.getCurrentUser() != null) {
            currentUserUID = mAuth.getCurrentUser().getUid();
            callback.onUserIsLoggedIn(true);
        } else {
            callback.onUserIsLoggedIn(false);
        }
    }

    @Override
    public void logInUser(LoginUser user, final LoginUserInterface callback) {
        mAuth = FirebaseAuth.getInstance();

        String email = user.getEmail();
        String password = user.getPassword();

        if (email.equals("")) {
            callback.onUserLoggedIn(false, false, false);
        } else if (password.length() < 6) {
            callback.onUserLoggedIn(false, true, true);
        } else {
            mAuth.signInWithEmailAndPassword(email,password)
                    .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                Log.d(TAG, "signInWithEmail:success");
                                callback.onUserLoggedIn(false,false,true);
                            } else {
                                Log.e(TAG, "onComplete: ", task.getException());
                                callback.onUserLoggedIn(true,false,true);
                            }
                        }
                    });
        }
    }
}

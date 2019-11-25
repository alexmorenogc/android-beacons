package es.ulpgc.alexmoreno.beacons.master;

import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import androidx.appcompat.app.AppCompatActivity;
import es.ulpgc.alexmoreno.beacons.R;
import es.ulpgc.alexmoreno.beacons.data.MasterItem;

public class MasterActivity
        extends AppCompatActivity implements MasterContract.View {

    public static String TAG = MasterActivity.class.getSimpleName();

    private MasterContract.Presenter presenter;
    private ListView listView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_master);

        androidx.appcompat.app.ActionBar toolbar = getSupportActionBar();
        if (toolbar != null) {
            toolbar.setDisplayHomeAsUpEnabled(true);
            toolbar.setDisplayShowHomeEnabled(true);
        }

        listView = findViewById(R.id.master_list);

        MasterScreen.configure(this);
    }

    @Override
    protected void onResume() {
        super.onResume();

        // do some work
        presenter.fetchMasterItemsData();
    }

    @Override
    public void injectPresenter(MasterContract.Presenter presenter) {
        this.presenter = presenter;
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    @Override
    public void displayData(MasterViewModel viewModel) {
        listView.setAdapter(new MasterAdapter(this, viewModel.masterItemList, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MasterItem item = (MasterItem) v.getTag();
                presenter.selectMasterItemData(item);
            }
        }));
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}

package es.udc.fic.ipm.beers;

import android.accounts.AccountManager;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.NavUtils;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import com.google.api.client.extensions.android.http.AndroidHttp;
import com.google.api.client.googleapis.extensions.android.gms.auth.GoogleAccountCredential;
import com.google.api.client.googleapis.extensions.android.gms.auth.GooglePlayServicesAvailabilityIOException;
import com.google.api.client.googleapis.extensions.android.gms.auth.UserRecoverableAuthIOException;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.client.util.ExponentialBackOff;
import com.google.api.services.sheets.v4.SheetsScopes;

import java.util.Arrays;
import java.util.List;

import pub.devrel.easypermissions.AfterPermissionGranted;
import pub.devrel.easypermissions.EasyPermissions;


/**
 * Created by wifopc on 3/11/17.
 */
/**
 * An activity representing a single Beer detail screen. This
 * activity is only used on narrow width devices. On tablet-size devices,
 * item details are presented side-by-side with a list of items
 * in a {@link BeerListActivity}.
 */
public class BeerDetailActivity extends AppCompatActivity {

    GoogleAccountCredential mCredential;

    static final int REQUEST_ACCOUNT_PICKER = 1000;
    static final int REQUEST_AUTHORIZATION = 1001;
    static final int REQUEST_GOOGLE_PLAY_SERVICES = 1002;
    static final int REQUEST_PERMISSION_GET_ACCOUNTS = 1003;


    private static final String PREF_ACCOUNT_NAME = "accountName";
    private static final String[] SCOPES = {SheetsScopes.SPREADSHEETS};
    private String newComment;
    private EditText editText;
    private int beerIndex;
    private ProgressBar mProgress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mCredential = GoogleAccountCredential.usingOAuth2(
                getApplicationContext(), Arrays.asList(SCOPES))
                .setBackOff(new ExponentialBackOff());
        //cojo el nombre de la cuenta de las sharedPreferences
        SharedPreferences sharedPref = getSharedPreferences("SharedPref", Context.MODE_PRIVATE);
        String pruebaCuenta = sharedPref.getString("googleAccount", "");
        mCredential.setSelectedAccountName(pruebaCuenta);


        //si tengo los detalles de una cerveza abiertos y giro la pantalla, vuelvo a la
        //actividad principal
        if (getResources().getConfiguration().orientation != 1) {
            finish();
        }
        setContentView(R.layout.activity_beer_detail);
        Toolbar toolbar = (Toolbar) findViewById(R.id.detail_toolbar);
        setSupportActionBar(toolbar);

        // Show the Up button in the action bar.
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        // savedInstanceState is non-null when there is fragment state
        // saved from previous configurations of this activity
        // (e.g. when rotating the screen from portrait to landscape).
        // In this case, the fragment will automatically be re-added
        // to its container so we don't need to manually add it.
        // For more information, see the Fragments API guide at:
        //
        // http://developer.android.com/guide/components/fragments.html
        //
        if (savedInstanceState == null) {
            // Create the detail fragment and add it to the activity
            // using a fragment transaction.
            Bundle arguments = new Bundle();
            arguments.putString(BeerDetailFragment.ARG_ITEM_ID,
                    getIntent().getStringExtra(BeerDetailFragment.ARG_ITEM_ID));
            BeerDetailFragment fragment = new BeerDetailFragment();
            fragment.setArguments(arguments);
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.beer_detail_container, fragment)
                    .commit();
        }
        editText = (EditText) findViewById(R.id.comment_edit_text);
        mProgress = (ProgressBar) findViewById(R.id.comment_progress_bar);
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab_message);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                /*Snackbar.make(view, "Replace with your own detail action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();*/
                //si estaba sin mostrar, lo mostramos
                if (editText.getVisibility() == View.INVISIBLE) {
                    //ponemos el foco sobre el campo de texto
                    editText.requestFocus();
                    editText.setVisibility(View.VISIBLE);
                } else {
                    //sino, cogemos el texto que se haya escrito, y lo volvemos invisible
                    //cogemos lo que ha introducido el usuario
                    String textInput = editText.getText().toString();
                    //miramos que haya introducido algo
                    if (!TextUtils.isEmpty(textInput)) {
                        newComment = textInput;
                        //para saber en que cerveza estamos, podemos hacer un atributo en el modelo (int) que nos diga cual
                        //ha sido la ultima cerveza seleccionada, cogiendo el titulo del toolbar me parece mas elegante
                        CollapsingToolbarLayout ctl = (CollapsingToolbarLayout)findViewById(R.id.toolbar_layout);
                        beerIndex = BeerModel.findByName(ctl.getTitle().toString());
                        //llamamos al post
                        makePostOnApi();
                    }
                    editText.setText("");
                    editText.setVisibility(View.INVISIBLE);
                }
                //cuando clicamos en el boton, actualizamos el comentario
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            // This ID represents the Home or Up button. In the case of this
            // activity, the Up button is shown. Use NavUtils to allow users
            // to navigate up one level in the application structure. For
            // more details, see the Navigation pattern on Android Design:
            //
            // http://developer.android.com/design/patterns/navigation.html#up-vs-back
            //
            NavUtils.navigateUpTo(this, new Intent(this, BeerListActivity.class));
            return true;
        }
        return super.onOptionsItemSelected(item);
    }






    public void makePostOnApi() {
        new BeerDetailActivity.MakePutRequest(mCredential).execute();
    }

    public void makePostOnApi(String userComment, int index) {
        new BeerDetailActivity.MakePutRequest(mCredential).execute();
    }

    /**
     * An asynchronous task that handles the Google Sheets API call.
     * Placing the API calls in their own task ensures the UI stays responsive.
     */
    public class MakePutRequest extends AsyncTask<Void, Void, Integer> {
        private com.google.api.services.sheets.v4.Sheets mService = null;
        private Exception mLastError = null;


        MakePutRequest(GoogleAccountCredential credential) {
            HttpTransport transport = AndroidHttp.newCompatibleTransport();
            JsonFactory jsonFactory = JacksonFactory.getDefaultInstance();
            mService = new com.google.api.services.sheets.v4.Sheets.Builder(
                    transport, jsonFactory, credential)
                    .setApplicationName(getString(R.string.app_name))
                    .build();
        }

        /**
         * Background task to call Google Sheets API.
         *
         * @param params no parameters needed for this task.
         */
        @Override
        protected Integer doInBackground(Void... params) {
            try {
                Beer beer = BeerModel.getBeers().get(beerIndex);
                return BeerModel.updateDataOnApi(mService, beer, newComment, mCredential.getSelectedAccountName());
            } catch (Exception e) {
                mLastError = e;
                cancel(true);
                return null;
            }
        }

        @Override
        protected void onPreExecute() {
            //mOutputText.setText("");
            mProgress.setVisibility(View.VISIBLE);
            mProgress.animate();
            //fab.setVisibility(View.INVISIBLE);

        }

        @Override
        protected void onPostExecute(Integer entero) {
            mProgress.setVisibility(View.INVISIBLE);
            if (entero == 0 || entero == null) {
                Snackbar.make(findViewById(android.R.id.content), getString(R.string.no_api_results), Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            } else {
                Snackbar.make(findViewById(android.R.id.content), getString(R.string.comment_ok), Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
            //actualizamos el texto en la pantalla
            TextView commentTextView = (TextView) findViewById(R.id.content_comment);
            commentTextView.setText(BeerModel.getBeers().get(beerIndex).getComment());
        }

        @Override
        protected void onCancelled() {
            mProgress.setVisibility(View.INVISIBLE);
            //fab.setVisibility(View.VISIBLE);
            if (mLastError != null) {
                if (mLastError instanceof UserRecoverableAuthIOException) {
                    startActivityForResult(
                            ((UserRecoverableAuthIOException) mLastError).getIntent(),
                            BeerListActivity.REQUEST_AUTHORIZATION);
                } else {
                    Snackbar.make(findViewById(android.R.id.content), mLastError.getMessage(), Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();
                }
            } else {
                Snackbar.make(findViewById(android.R.id.content), R.string.request_cancelled, Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        }
    }


}

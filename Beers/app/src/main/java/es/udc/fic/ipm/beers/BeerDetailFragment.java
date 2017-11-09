package es.udc.fic.ipm.beers;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.google.api.client.extensions.android.http.AndroidHttp;
import com.google.api.client.googleapis.extensions.android.gms.auth.GoogleAccountCredential;
import com.google.api.client.googleapis.extensions.android.gms.auth.UserRecoverableAuthIOException;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.client.util.ExponentialBackOff;
import com.google.api.services.sheets.v4.SheetsScopes;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

import java.util.Arrays;

/**
 * Created by wifopc on 3/11/17.
 */

/**
 * A fragment representing a single Beer detail screen.
 * This fragment is either contained in a {@link BeerListActivity}
 * in two-pane mode (on tablets) or a {@link BeerDetailActivity}
 * on handsets.
 */
public class BeerDetailFragment extends Fragment {
    /**
     * The fragment argument representing the item ID that this fragment
     * represents.
     */
    public static final String ARG_ITEM_ID = "item_id";

    private Beer beer;
    private EditText editText;
    private String newComment;
    private int beerIndex;
    private View rootView;
    private BeerModel model;
    FloatingActionButton fab;
    GoogleAccountCredential mCredential;
    private static final String[] SCOPES = {SheetsScopes.SPREADSHEETS};
    ViewGroup viewGroup;
    ProgressBar mProgress;
    private int previousETHeight = 0;

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public BeerDetailFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments().containsKey(ARG_ITEM_ID)) {

            mCredential = GoogleAccountCredential.usingOAuth2(
                    getActivity().getApplicationContext(), Arrays.asList(SCOPES))
                    .setBackOff(new ExponentialBackOff());
            SharedPreferences sharedPref = getActivity().getSharedPreferences("SharedPref", Context.MODE_PRIVATE);
            String googleAccount = sharedPref.getString("googleAccount", "");
            mCredential.setSelectedAccountName(googleAccount);

            model = BeerModel.getBeerModel(null, getContext());
            int index = model.findByName(getArguments().getString(ARG_ITEM_ID));
            beer = model.getBeers().get(index);

            Activity activity = this.getActivity();
            CollapsingToolbarLayout appBarLayout = (CollapsingToolbarLayout) activity.
                    findViewById(R.id.toolbar_layout);
            if (appBarLayout != null) {
                //appBarLayout.setTitle(mItem.content);
                appBarLayout.setTitle(beer.getName());
            }
        }



    }

    @Override
    public View onCreateView(final LayoutInflater inflater, final ViewGroup container,
                             Bundle savedInstanceState) {

        rootView = inflater.inflate(R.layout.beer_detail, container, false);

        editText = (EditText) rootView.findViewById(R.id.comment_edit_text_detail);
        viewGroup = container;
        mProgress = (ProgressBar) rootView.findViewById(R.id.comment_progress_bar);
        fab = (FloatingActionButton) rootView.findViewById(R.id.fab_message_detail);
        fab.setVisibility(View.VISIBLE);

        //esta parte del código sólo se ejecuta cuadno estoy en la actividad BeerListActivity
        //cuando estoy en BeerDetailActivity, se meten los datos desde ahí
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //si estaba sin mostrar, lo mostramos
                if (editText.getVisibility() == View.INVISIBLE) {
                    previousETHeight = editText.getLayoutParams().height;
                    //adaptamos el tamaño al contenido y lo mostramos
                    editText.getLayoutParams().height = LinearLayout.LayoutParams.WRAP_CONTENT;
                    editText.setVisibility(View.VISIBLE);
                    //ponemos el foco sobre el campo de texto para poder escribir directamente
                    editText.requestFocus();
                } else {
                    //sino, cogemos el texto que se haya escrito, y lo volvemos invisible
                    //cogemos lo que ha introducido el usuario
                    String textInput = editText.getText().toString();
                    //miramos que haya introducido algo
                    if (!TextUtils.isEmpty(textInput)) {
                        newComment = textInput;
                        /*para saber en que cerveza estamos, podemos hacer un atributo en el
                        modelo (int) que nos diga cual ha sido la ultima cerveza seleccionada,
                        cogiendo el titulo del toolbar me parece mas elegante*/
                        beerIndex = model.findByName(beer.getName());
                        //BeerListActivity beerListActivity = (BeerListActivity)getActivity();
                        //beerListActivity.makePostOnApi(newComment, beerIndex);
                        makePostOnApi();
                    }
                    editText.setText("");
                    editText.setVisibility(View.INVISIBLE);
                    editText.getLayoutParams().height = previousETHeight;
                }
            }
            //cuando clicamos en el boton, actualizamos el comentario
        });


        // Mostramos los datos de la cerveza
        if (beer != null) {
            String name, date, madeIn, type, comment, moreInfo, photoURL;
            //comprobamos que los datos son validos antes de insertarlos
            date = beer.getDate();
            if (date == null)
                date = getString(R.string.no_date_available);
            madeIn = beer.getMadeIn();
            if (madeIn == null)
                madeIn = getString(R.string.no_made_in_available);
            type = beer.getType();
            if (type == null)
                type = getString(R.string.no_type_available);
            comment = beer.getComment();
            if (comment == null)
                comment = getString(R.string.no_comment_available);
            moreInfo = beer.getMoreInfo();
            if (moreInfo == null)
                moreInfo = getString(R.string.no_more_info_available);
            photoURL = beer.getPhotoURL();
            if (photoURL == null)
                photoURL = getString(R.string.no_photo_available);
            ((TextView) rootView.findViewById(R.id.content_date)).setText(date);
            ((TextView) rootView.findViewById(R.id.content_made_in)).setText(madeIn);
            ((TextView) rootView.findViewById(R.id.content_type)).setText(type);
            ((TextView) rootView.findViewById(R.id.content_comment)).setText(comment);
            ((TextView) rootView.findViewById(R.id.content_more_info)).setText(moreInfo);
            //((TextView) rootView.findViewById(R.id.content_name)).setText(beer.getName());


            ImageView imageview = (ImageView) rootView.findViewById(R.id.beer_photo_viewer);
            ImageLoader imageLoader = ImageLoader.getInstance();
            //si el imageloader ya está iniciado, no hacemos nada
            if (!imageLoader.isInited())
                imageLoader.init(ImageLoaderConfiguration.createDefault(getContext()));
            Activity activity = this.getActivity();
            ImageView toolbarImage = (ImageView) activity.findViewById(R.id.toolbar_imageview);
            //si estoy en pantalla única, muestro la imagen en el toolbar, sino, la muestro
            //en un campo más, y lo mismo con los campos del nombre, si estoy en modo unica
            //pantalla los oculto, sino, los muestro
            TextView textViewNameContent = (TextView) rootView.findViewById(R.id.content_name);
            if (toolbarImage != null) {
                imageLoader.displayImage(beer.getPhotoURL(), toolbarImage);
                textViewNameContent.getLayoutParams().height = 0;
            } else {
                textViewNameContent.setText(beer.getName());
                imageLoader.displayImage(beer.getPhotoURL(), imageview);
            }
        }
        return rootView;
    }

    public void makePostOnApi() {
        new MakePutRequest(mCredential).execute();
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
            model.setMService(mService);
        }

        /**
         * Background task to call Google Sheets API.
         *
         * @param params no parameters needed for this task.
         */
        @Override
        protected Integer doInBackground(Void... params) {
            try {
                /*Beer beer = BeerModel.getBeers().get(beerIndex);
                return BeerModel.updateDataOnApi(mService, beer, newComment, mCredential.getSelectedAccountName());*/


                Beer beer = model.getBeers().get(beerIndex);
                model.setMService(mService);
                return model.updateDataOnApi(beer, newComment, mCredential.getSelectedAccountName());
            } catch (Exception e) {
                mLastError = e;
                cancel(true);
                return null;
            }
        }

        @Override
        protected void onPreExecute() {
            mProgress.setVisibility(View.VISIBLE);
            mProgress.animate();
        }

        @Override
        protected void onPostExecute(Integer entero) {
            mProgress.setVisibility(View.INVISIBLE);
            if (entero == 0 || entero == null) {
                Snackbar.make(viewGroup, getString(R.string.no_api_results), Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            } else {
                Snackbar.make(viewGroup, getString(R.string.comment_ok), Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
            //actualizamos el texto en la pantalla
            TextView commentTextView = (TextView) rootView.findViewById(R.id.content_comment);
            commentTextView.setText(model.getBeers().get(beerIndex).getComment());
        }

        @Override
        protected void onCancelled() {
            mProgress.setVisibility(View.INVISIBLE);
            if (mLastError != null) {
                if (mLastError instanceof UserRecoverableAuthIOException) {
                    startActivityForResult(
                            ((UserRecoverableAuthIOException) mLastError).getIntent(),
                            BeerListActivity.REQUEST_AUTHORIZATION);
                } else {
                    Snackbar.make(viewGroup, mLastError.getMessage(), Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();
                }
            } else {
                Snackbar.make(viewGroup, R.string.request_cancelled, Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        }
    }
}

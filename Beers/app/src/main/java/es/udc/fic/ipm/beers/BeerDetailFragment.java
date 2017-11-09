package es.udc.fic.ipm.beers;

import android.app.Activity;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

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


        fab = (FloatingActionButton) rootView.findViewById(R.id.fab_message_detail);
        //if (getResources().getConfiguration().orientation != 1)
        //quiero que cuando los detalles se muestren en una única pantalla se oculte el botón
        //que debería mostrarse cuando los detalles se están mostrando en pantalla doble, es
        //decir, cuando la clase padre es BeerDetailActivity se oculta, y cuando es
        //BeerListActivity, se muestran
        if (!getActivity().getLocalClassName().equals("BeerDetailActivity"))
            fab.setVisibility(View.VISIBLE);
        else
            fab.setVisibility(View.INVISIBLE);



        //esta parte del código sólo se ejecuta cuadno estoy en la actividad BeerListActivity
        //cuando estoy en BeerDetailActivity, se meten los datos desde ahí
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //detalles en una única pantalla
                if (!getActivity().getLocalClassName().equals("BeerDetailActivity")) {
                    //si estaba sin mostrar, lo mostramos
                    if (editText.getVisibility() == View.INVISIBLE) {
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
                            BeerListActivity beerListActivity = (BeerListActivity)getActivity();
                            beerListActivity.makePostOnApi(newComment, beerIndex);
                        }
                        editText.setText("");
                        editText.setVisibility(View.INVISIBLE);
                    }
                }
                //cuando clicamos en el boton, actualizamos el comentario
            }
        });


        // Mostramos los datos de la cerveza
        if (beer != null) {
            String date, madeIn, type, comment, moreInfo, photoURL;
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


            ImageView imageview = (ImageView) rootView.findViewById(R.id.beer_photo_viewer);
            ImageLoader imageLoader = ImageLoader.getInstance();
            //si el imageloader ya está iniciado, no hacemos nada
            if (!imageLoader.isInited())
                imageLoader.init(ImageLoaderConfiguration.createDefault(getContext()));
            Activity activity = this.getActivity();
            ImageView toolbarImage = (ImageView) activity.findViewById(R.id.toolbar_imageview);
            TextView textView = (TextView) rootView.findViewById(R.id.title_photo_viewer);
            //si estoy en pantalla única, muestro la imagen en el toolbar, sino, la muestro
            //en un campo más
            if (toolbarImage != null) {
                imageLoader.displayImage(beer.getPhotoURL(), toolbarImage);
                textView.setText("");
            } else {
                imageLoader.displayImage(beer.getPhotoURL(), imageview);
            }
        }

        return rootView;
    }
}

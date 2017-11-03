package es.udc.fic.ipm.beers;

import android.app.Activity;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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

    /**
     * The dummy content this fragment is presenting.
     */
    //private DummyContent.DummyItem mItem;
    private Beer beer;

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
            int index = BeerModel.findByName(getArguments().getString(ARG_ITEM_ID));
            beer = BeerModel.getBeers().get(index);
            // Load the dummy content specified by the fragment
            // arguments. In a real-world scenario, use a Loader
            // to load content from a content provider.
            //mItem = DummyContent.ITEM_MAP.get(getArguments().getString(ARG_ITEM_ID));

            Activity activity = this.getActivity();
            CollapsingToolbarLayout appBarLayout = (CollapsingToolbarLayout) activity.findViewById(R.id.toolbar_layout);
            if (appBarLayout != null) {
                //appBarLayout.setTitle(mItem.content);
                appBarLayout.setTitle(beer.getName());
            }
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.beer_detail, container, false);
        // Show the dummy content as text in a TextView.
        if (beer != null) {
            int cantidad = BeerModel.getBeers().size();
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
            ((TextView) rootView.findViewById(R.id.content_photo_url)).setText(photoURL);
            ImageView imageview = (ImageView) rootView.findViewById(R.id.beer_photo_viewer);
            ImageLoader imageLoader = ImageLoader.getInstance();
            imageLoader.displayImage(beer.getPhotoURL(), imageview);
        }

        return rootView;
    }
}

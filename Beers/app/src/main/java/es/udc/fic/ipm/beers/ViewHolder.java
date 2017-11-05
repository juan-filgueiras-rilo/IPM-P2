package es.udc.fic.ipm.beers;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.makeramen.roundedimageview.RoundedImageView;
import com.mikhaellopez.circularimageview.CircularImageView;

import de.hdodenhof.circleimageview.CircleImageView;


/**
 * Created by wifopc on 3/11/17.
 */

public class ViewHolder extends RecyclerView.ViewHolder {
    //final TextView mIdView;
    //final TextView mContentView;

    final TextView nameView;
    //final CircleImageView circleImageView;
    //final ImageView imageView;
    final RoundedImageView roundedImageView;
    //final CircularImageView circularImageView;
            /*final TextView dateView;
            final TextView madeInView;
            final TextView typeView;
            final TextView commentView;
            final TextView moreInfoView;*/


    ViewHolder(View view) {
        super(view);
        //mIdView = (TextView) view.findViewById(R.id.id_text);
        //mContentView = (TextView) view.findViewById(R.id.content);
        nameView = (TextView) view.findViewById(R.id.name_text);
        //imageView = (CircleImageView) view.findViewById(R.id.beer_thumbnail_viewer);
        //imageView = (ImageView) view.findViewById(R.id.beer_thumbnail_viewer);
        //circleImageView = (CircleImageView) view.findViewById(R.id.beer_thumbnail_viewer_circle);
        roundedImageView = (RoundedImageView) view.findViewById(R.id.beer_thumbnail_viewer_rounded);
        //circularImageView = (CircularImageView) view.findViewById(R.id.beer_thumbnail_viewer_circular);
                /*dateView = (TextView) view.findViewById(R.id.date_text);
                madeInView = (TextView) view.findViewById(R.id.madeIn_text);
                typeView = (TextView) view.findViewById(R.id.type_text);
                commentView = (TextView) view.findViewById(R.id.comment_text);
                moreInfoView = (TextView) view.findViewById(R.id.moreInfo_text);*/

    }
}

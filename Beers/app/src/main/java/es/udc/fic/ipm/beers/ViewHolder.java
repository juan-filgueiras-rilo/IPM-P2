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


/**
 * ViewHolder es la vista de cada elemento del RecyclerView (creo), que tendrá un TextView (nombre
 * de la cerveza) y una RoundedImageView (miniatura de la imagen de la cerveza redondeada)
 */
public class ViewHolder extends RecyclerView.ViewHolder {

    final TextView nameView;
    //final CircleImageView circleImageView;
    //final ImageView imageView;
    final RoundedImageView roundedImageView;


    ViewHolder(View view) {
        super(view);
        //recupero los elementos de la vista
        nameView = (TextView) view.findViewById(R.id.name_text);
        roundedImageView = (RoundedImageView) view.findViewById(R.id.beer_thumbnail_viewer_rounded);
        //imageView = (CircleImageView) view.findViewById(R.id.beer_thumbnail_viewer);
        //imageView = (ImageView) view.findViewById(R.id.beer_thumbnail_viewer);
        //circleImageView = (CircleImageView) view.findViewById(R.id.beer_thumbnail_viewer_circle);
        //circularImageView = (CircularImageView) view.findViewById(R.id.beer_thumbnail_viewer_circular);

    }
}

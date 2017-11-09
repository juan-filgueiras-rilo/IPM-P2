package es.udc.fic.ipm.beers;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.makeramen.roundedimageview.RoundedImageView;



/**
 * Created by wifopc on 3/11/17.
 */


/**
 * ViewHolder es la vista de cada elemento del RecyclerView (creo), que tendrá un TextView (nombre
 * de la cerveza) y una RoundedImageView (miniatura de la imagen de la cerveza redondeada)
 */
public class ViewHolder extends RecyclerView.ViewHolder {

    final TextView nameView;
    final RoundedImageView roundedImageView;


    ViewHolder(View view) {
        super(view);
        //recupero los elementos de la vista
        nameView = (TextView) view.findViewById(R.id.name_text);
        roundedImageView = (RoundedImageView) view.findViewById(R.id.beer_thumbnail_viewer_rounded);

    }
}

package es.udc.fic.ipm.beers;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.List;

/**
 * Created by wifopc on 3/11/17.
 */

public class BeerToRecyclerViewAdapter
        extends RecyclerView.Adapter<ViewHolder> {

    private final BeerListActivity mParentActivity;
    private final List<Beer> mValues;
    private final boolean mTwoPane;

    /**
     * Método que se ejecuta cuando se hace selecciona un elemento de
     * la lista.
     * Si la pantalla está girada se actualiza el fragmento (ubicado en la parte derecha)
     * que muestra los detalles del elemento seleccionado, pasándole al fragmento dicho
     * elemento.
     * Si la pantalla no está girada, se inicia una nueva actividad (BeerDetailActivity)
     * asociándole el fragmento que muestra los detalles de una nueva cerveza.
     */
    private final View.OnClickListener mOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            //DummyContent.DummyItem item = (DummyContent.DummyItem) view.getTag();
            Beer beer = (Beer) view.getTag();
            if (mTwoPane) {
                //por aqui entro cuando la pantalla SI esta girada
                Bundle arguments = new Bundle();
                //establecemos los argumentos que pasaremos al fragmento (posicion de la cerveza
                //y nombre)
                arguments.putString(BeerDetailFragment.ARG_ITEM_ID, beer.getName());
                //creo el nuevo fragmento
                BeerDetailFragment fragment = new BeerDetailFragment();
                //le paso los parámetros
                fragment.setArguments(arguments);
                //cambio el fragmento anterior por el nuevo, mostrando así los detalles de
                //la última cerveza seleccionada
                mParentActivity.getSupportFragmentManager().beginTransaction()
                        .replace(R.id.beer_detail_container, fragment)
                        .commit();
            } else {
                //por aqui entro cuando la pantalla NO esta girada
                Context context = view.getContext();
                //creamos la nueva actividad
                Intent intent = new Intent(context, BeerDetailActivity.class);
                //le asoaciamos el fragmento que muestra los detalles de la cerveza
                intent.putExtra(BeerDetailFragment.ARG_ITEM_ID, beer.getName());
                //lanzamos la actividad
                context.startActivity(intent);
            }
        }
    };

    BeerToRecyclerViewAdapter(BeerListActivity parent,
                              List<Beer> items,
                              boolean twoPane) {
        mValues = items;
        mParentActivity = parent;
        mTwoPane = twoPane;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.beer_list_content, parent, false);
        return new ViewHolder(view);
    }

    /**
     *
     * @param holder
     * @param position
     */
    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {

        holder.nameView.setText(mValues.get(position).getName());
        ImageLoader imageLoader = ImageLoader.getInstance();

        //imageLoader.displayImage(mValues.get(position).getPhotoURL(), holder.imageView);
        //imageLoader.displayImage(mValues.get(position).getPhotoURL(), holder.circleImageView);
        imageLoader.displayImage(mValues.get(position).getPhotoURL(), holder.roundedImageView);
        //imageLoader.displayImage(mValues.get(position).getPhotoURL(), holder.circularImageView);

        holder.itemView.setTag(mValues.get(position));
        holder.itemView.setOnClickListener(mOnClickListener);
    }

    @Override
    public int getItemCount() {
        return mValues.size();
    }


}
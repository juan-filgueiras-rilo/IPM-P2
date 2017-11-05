package es.udc.fic.ipm.beers;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

/**
 * Created by wifopc on 3/11/17.
 */

public class BeerToRecyclerViewAdapter
        extends RecyclerView.Adapter<ViewHolder> {

    private final BeerListActivity mParentActivity;
    //private final List<DummyContent.DummyItem> mValues;
    private final List<Beer> mValues;
    private final boolean mTwoPane;


    private final View.OnClickListener mOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            //DummyContent.DummyItem item = (DummyContent.DummyItem) view.getTag();
            Beer beer = (Beer) view.getTag();
            if (mTwoPane) {
                //por aqui entro cuando la pantalla SI esta girada
                Bundle arguments = new Bundle();
                //arguments.putString(BeerDetailFragment.ARG_ITEM_ID, item.id);
                arguments.putString(BeerDetailFragment.ARG_ITEM_ID, beer.getName());
                BeerDetailFragment fragment = new BeerDetailFragment();
                fragment.setArguments(arguments);
                mParentActivity.getSupportFragmentManager().beginTransaction()
                        .replace(R.id.beer_detail_container, fragment)
                        .commit();
            } else {
                //por aqui entro cuando la pantalla NO esta girada
                Context context = view.getContext();
                Intent intent = new Intent(context, BeerDetailActivity.class);
                //intent.putExtra(BeerDetailFragment.ARG_ITEM_ID, item.id);
                intent.putExtra(BeerDetailFragment.ARG_ITEM_ID, beer.getName());
                context.startActivity(intent);
            }
        }
    };

    BeerToRecyclerViewAdapter(BeerListActivity parent,
                              //List<DummyContent.DummyItem> items,
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

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        //holder.mIdView.setText(mValues.get(position).id);
        //holder.mContentView.setText(mValues.get(position).content);

        holder.nameView.setText(mValues.get(position).getName());
            /*holder.dateView.setText(mValues.get(position).getDate());
            holder.madeInView.setText(mValues.get(position).getMadeIn());
            holder.typeView.setText(mValues.get(position).getType());
            holder.commentView.setText(mValues.get(position).getcomment());
            holder.moreInfoView.setText(mValues.get(position).getMoreInfo());*/

        holder.itemView.setTag(mValues.get(position));
        holder.itemView.setOnClickListener(mOnClickListener);
    }

    @Override
    public int getItemCount() {
        return mValues.size();
    }


}
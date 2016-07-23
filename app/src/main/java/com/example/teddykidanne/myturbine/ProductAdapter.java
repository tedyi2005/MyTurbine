package com.example.teddykidanne.myturbine;

/**
 * Created by Teddykidanne on 7/22/16.
 */
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.drawable.Drawable;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.io.InputStream;
import java.net.URL;
import java.util.Arrays;
import java.util.List;

public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.MyViewHolder> {

    private Context mContext;
    private List<ProductPojo> albumList;
    String lastNameString;
    Object[] arrayList;
    List<Object> list;


    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView lastName, cat_name, product_name, product_design, numOfRatings, rate;
        public ImageView thumbnail1, thumbnail2;

        public MyViewHolder(View view) {
            super(view);

            lastName = (TextView) view.findViewById(R.id.lastName);
            product_name = (TextView) view.findViewById(R.id.product_name);
            product_design = (TextView) view.findViewById(R.id.product_design);
            cat_name = (TextView) view.findViewById(R.id.category_name);
            rate = (TextView) view.findViewById(R.id.rate);
            numOfRatings = (TextView) view.findViewById(R.id.noOfRatings);
            thumbnail1 = (ImageView) view.findViewById(R.id.thumbnail);
            thumbnail2 = (ImageView) view.findViewById(R.id.thumbnail2);


            view.setOnClickListener(new View.OnClickListener() {


                @Override
                public void onClick(View v) {

                    final int position = getLayoutPosition();

                    final EditText edittext = new EditText(itemView.getContext());
                    AlertDialog.Builder alert = new AlertDialog.Builder(itemView.getContext());
                    alert.setMessage("Enter Your Last Name");
                    alert.setTitle("Fill me");

                    alert.setView(edittext);

                    alert.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int whichButton) {
                            dialog.dismiss();

                            lastNameString = edittext.getText().toString();
                            arrayList[position] = lastNameString;
                            list = Arrays.asList(arrayList);
                            notifyDataSetChanged();
                        }
                    });

                    alert.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int whichButton) {
                            dialog.dismiss();
                        }
                    });

                    alert.show();
                }
            });

        }
    }


    public ProductAdapter(Context mContext, List<ProductPojo> albumList) {
        this.mContext = mContext;
        this.albumList = albumList;
        arrayList = new Object[albumList.size()];
        Log.d("albumList", "" + albumList.size());
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.album_card, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, int position) {
        ProductPojo album = albumList.get(position);

        holder.lastName.setVisibility(View.VISIBLE);
        holder.lastName.setText("Last Name : Kidanne");
        if (list == null) {
        } else {
            if (list.get(position) != null) {
                holder.lastName.setVisibility(View.VISIBLE);
                holder.lastName.setText("Last Name : " + list.get(position));
            } else {
                holder.lastName.setVisibility(View.VISIBLE);
                holder.lastName.setText("Last Name : Kidanne");
            }
        }
        holder.product_name.setText("Product Name : " + album.getpName());
        holder.cat_name.setText("Category Name : " + album.getcName());
        holder.product_design.setText("Product Description : " + album.getpDesc());
        holder.rate.setText("Rating : " + album.getRating());
        holder.numOfRatings.setText("No.of ratings : " + album.getNoOfRatings());

        Drawable productDrawable = LoadImageFromProductLink(album.getpLink());
        holder.thumbnail1.setImageDrawable(productDrawable);
        // loading album cover using Glide library
        Glide.with(mContext).load(album.getpLink()).into(holder.thumbnail1);

        Drawable rateDrawable = LoadImageFromRateLink(album.getRateLink());
        holder.thumbnail2.setImageDrawable(rateDrawable);
        // loading album cover using Glide library
        Glide.with(mContext).load(album.getRateLink()).into(holder.thumbnail2);

    }

    private Drawable LoadImageFromProductLink(String url) {
        try {
            InputStream is = (InputStream) new URL(url).getContent();
            Drawable d = Drawable.createFromStream(is, "src name");
            return d;
        } catch (Exception e) {
            System.out.println("Exc=" + e);
            return null;
        }
    }

    private Drawable LoadImageFromRateLink(String url) {
        try {
            InputStream is = (InputStream) new URL(url).getContent();
            Drawable d = Drawable.createFromStream(is, "src name");
            return d;
        } catch (Exception e) {
            System.out.println("Exc=" + e);
            return null;
        }
    }


    @Override
    public int getItemCount() {
        return albumList.size();
    }

    @Override
    public long getItemId(int position) {
        return super.getItemId(position);
    }
}
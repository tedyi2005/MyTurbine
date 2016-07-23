package com.example.teddykidanne.myturbine;

import android.app.ProgressDialog;
import android.content.res.Resources;
import android.graphics.Rect;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;

import java.io.StringReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    ArrayList<ProductPojo> lastNameList;
    private RecyclerView recyclerView;
    private ProductAdapter adapter;
    private List<ProductPojo> albumList;

    /////
    private ProgressDialog pDialog;
    ApiClass apigetOfferList;
    String response;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        albumList = new ArrayList<>();
        lastNameList = new ArrayList<ProductPojo>(12);

        ListViewLoaderTask listViewLoaderTask = new ListViewLoaderTask();
        listViewLoaderTask.execute();

    }


    /**
     * RecyclerView item decoration - give equal margin around grid item
     */
    public class GridSpacingItemDecoration extends RecyclerView.ItemDecoration {

        private int spanCount;
        private int spacing;
        private boolean includeEdge;

        public GridSpacingItemDecoration(int spanCount, int spacing, boolean includeEdge) {
            this.spanCount = spanCount;
            this.spacing = spacing;
            this.includeEdge = includeEdge;
        }

        @Override
        public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
            int position = parent.getChildAdapterPosition(view); // item position
            int column = position % spanCount; // item column

            if (includeEdge) {
                outRect.left = spacing - column * spacing / spanCount; // spacing - column * ((1f / spanCount) * spacing)
                outRect.right = (column + 1) * spacing / spanCount; // (column + 1) * ((1f / spanCount) * spacing)

                if (position < spanCount) { // top edge
                    outRect.top = spacing;
                }
                outRect.bottom = spacing; // item bottom
            } else {
                outRect.left = column * spacing / spanCount; // column * ((1f / spanCount) * spacing)
                outRect.right = spacing - (column + 1) * spacing / spanCount; // spacing - (column + 1) * ((1f /    spanCount) * spacing)
                if (position >= spanCount) {
                    outRect.top = spacing; // item top
                }
            }
        }
    }

    /**
     * Converting dp to pixel
     */
    private int dpToPx(int dp) {
        Resources r = getResources();
        return Math.round(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, r.getDisplayMetrics()));
    }

    private class ListViewLoaderTask extends AsyncTask<String, Void, String> {


        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            pDialog = new ProgressDialog(MainActivity.this);
            pDialog.setMessage("Loading ...");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(false);
            pDialog.show();

            apigetOfferList = new ApiClass();
        }


        @Override
        protected String doInBackground(String... args) {
            response = apigetOfferList.xmlFetchFunction();
            return response;
        }


        @Override
        protected void onPostExecute(String res) {

            Log.d("res", "" + res);
            StringReader reader = new StringReader(res);

            ProductXmlParser productXmlParser = new ProductXmlParser();

            List<HashMap<String, String>> products = null;

            try {
                /** Getting the parsed data as a List construct */
                products = productXmlParser.parse(reader);
            } catch (Exception e) {
                Log.d("Exception", e.toString());
            }
            Log.d("products", "" + products);

            for (int i = 0; i < products.size(); i++) {

                Log.d("productThumbnail", "" + products.get(i).get("productThumbnail"));
                ProductPojo album = new ProductPojo(products.get(i).get("productThumbnail"), products.get(i).get("productName"), products.get(i).get("categoryName"), products.get(i).get("productDescription"), products.get(i).get("numberOfRatings"), products.get(i).get("rating"), products.get(i).get("averageRatingImageURL"));

                albumList.add(album);
                lastNameList.add(album);
            }


            adapter = new ProductAdapter(MainActivity.this, albumList);
            adapter.notifyDataSetChanged();

            RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(MainActivity.this, 1);
            recyclerView.setLayoutManager(mLayoutManager);
            recyclerView.addItemDecoration(new GridSpacingItemDecoration(2, dpToPx(10), true));
            recyclerView.setItemAnimator(new DefaultItemAnimator());
            recyclerView.setAdapter(adapter);


            pDialog.dismiss();
        }
    }
}

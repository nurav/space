package com.spitspce.space;

/**
 * Created by Sumeet on 15-02-2015.
 */
import android.graphics.Matrix;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by xgc1986 on 9/9/13.
 */
public class ParallaxFragment extends Fragment {

    private HighlightsAdapter mHighlightsAdapter;



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        super.onCreateView(inflater, container, savedInstanceState);

        View v = inflater.inflate(R.layout.parallax_layout, container, false);
        final ImageView image = (ImageView) v.findViewById(R.id.image);

        image.setImageResource(getArguments().getInt("image"));
        image.post(new Runnable() {
            @Override
            public void run() {
                Matrix matrix = new Matrix();
                matrix.reset();

                float wv = image.getWidth();
                float hv = image.getHeight();

                float wi = image.getDrawable().getIntrinsicWidth();
                float hi = image.getDrawable().getIntrinsicHeight();

                float width = wv;
                float height = hv;

                if (wi / wv > hi / hv) {
                    matrix.setScale(hv / hi, hv / hi);
                    width = wi * hv / hi;
                } else {
                    matrix.setScale(wv / wi, wv / wi);
                    height= hi * wv / wi;
                }

                matrix.preTranslate((wv - width) / 2, (hv - height) / 2);
                image.setScaleType(ImageView.ScaleType.MATRIX);
                image.setImageMatrix(matrix);
            }
        });

        Typeface tf1 = Typeface.createFromAsset(getActivity().getAssets(), "fonts/berlin-sans-fb-demi-bold.ttf");
        Typeface tf2 = Typeface.createFromAsset(getActivity().getAssets(), "fonts/Berlin Sans FB.ttf");

        TextView text = (TextView)v.findViewById(R.id.name);
        text.setTypeface(tf1);
        text.setText(getArguments().getString("name"));

        TextView text1 = (TextView)v.findViewById(R.id.desc);
        text1.setTypeface(tf2);
        text1.setText(getArguments().getString("desc"));

        return v;
    }

    public void setAdapter(HighlightsAdapter highlightsAdapter) {
        mHighlightsAdapter = highlightsAdapter;
    }
}

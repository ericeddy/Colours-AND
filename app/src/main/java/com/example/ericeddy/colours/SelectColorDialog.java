package com.example.ericeddy.colours;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

public class SelectColorDialog extends DialogView {

    public LinearLayout listView;
    private int[] colors;
    private int selectedId = -1;

    public SelectColorDialog(Context context) {
        super(context);
        init();
    }

    public SelectColorDialog(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public SelectColorDialog(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    public SelectColorDialog(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init();
    }

    @Override
    protected void init() {
        LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.select_color_dialog, this, true);

        super.init();

        listView = findViewById(R.id.list_view);
    }

    @Override
    public void displayDialog() {
        super.displayDialog();
        colors = Helper.getSelectedColors();
        updateCells();
    }

    private void updateCells() {
        selectedId = PreferenceManager.getTouchColour();
        for(int i = 0; i < colors.length; i++){
            SelectColorHolder v = (SelectColorHolder)listView.getChildAt(i);
            if(v == null ){
                SelectColorHolder view = new SelectColorHolder(mContext);
                view.setup(i, selectedId == i);
                listView.addView(view);
            } else {
                v.setup(i, selectedId == i);
            }
        }
        ViewGroup.LayoutParams ll = listView.getLayoutParams();
        ll.height = Helper.convertDpToPixels(32 + (colors.length * 32) );
        ll.width = Helper.convertDpToPixels(32 + 32);
        listView.setLayoutParams(ll);
    }

    class SelectColorHolder extends RelativeLayout {

        private RelativeLayout background;
        private View selectedView;
        private int id;

        public SelectColorHolder(Context context) {
            super(context);
            mContext = context;
            init();
        }

        public SelectColorHolder(Context context, @Nullable AttributeSet attrs) {
            super(context, attrs);
            mContext = context;
            init();
        }

        public SelectColorHolder(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
            super(context, attrs, defStyleAttr);
            mContext = context;
            init();
        }

        public SelectColorHolder(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
            super(context, attrs, defStyleAttr, defStyleRes);
            mContext = context;
            init();
        }

        public void init() {
            LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            inflater.inflate(R.layout.select_color_holder, this, true);

            background = findViewById(R.id.select_color_holder);
            selectedView = findViewById(R.id.selected);
        }



        public void setup( int colorId, boolean selected ) {
            id = colorId;
            selectedView.setVisibility(selected ? View.VISIBLE : View.INVISIBLE);
            background.setBackgroundColor( mContext.getColor(colors[colorId])
            );
        }
    }
}

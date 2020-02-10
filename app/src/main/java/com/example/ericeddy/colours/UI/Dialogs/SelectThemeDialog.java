package com.example.ericeddy.colours.UI.Dialogs;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.example.ericeddy.colours.System.Helper;
import com.example.ericeddy.colours.System.PreferenceManager;
import com.example.ericeddy.colours.R;
import com.example.ericeddy.colours.UI.MainActivity;

import java.util.ArrayList;

public class SelectThemeDialog extends DialogView {

    public RecyclerView recyclerView;
    public int currentTheme = 0;
    private ThemeAdapter adapter;
    private ArrayList<int[]> themesList;

    public SelectThemeDialog(Context context) {
        super(context);
        init();
    }

    public SelectThemeDialog(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public SelectThemeDialog(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    public SelectThemeDialog(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init();
    }

    @Override
    protected void init() {
        LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.select_theme_dialog, this, true);

        super.init();

        LinearLayoutManager ll = new LinearLayoutManager(mContext);
        recyclerView = findViewById(R.id.loaded_themes);
        recyclerView.setLayoutManager(ll);

        currentTheme = PreferenceManager.getTheme();
    }

    @Override
    public void displayDialog(RelativeLayout relativeLayout) {
        super.displayDialog(relativeLayout);

        currentTheme = PreferenceManager.getTheme();
        themesList = Helper.getThemesList();

        adapter = new ThemeAdapter();
        adapter.notifyDataSetChanged();

        recyclerView.setAdapter(adapter);
    }

    private void themeChanged() {
        currentTheme = PreferenceManager.getTheme();
        adapter.notifyDataSetChanged();
    }

    public class ThemeAdapter extends RecyclerView.Adapter<ThemeHolder> {
        public ThemeAdapter() {}

        @NonNull
        @Override
        public ThemeHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
            View formNameView = LayoutInflater.from(parent.getContext()).inflate(R.layout.select_theme_holder, parent, false);
            return new ThemeHolder(formNameView);
        }

        @Override
        public void onBindViewHolder(ThemeHolder holder, final int position) {
            int[] data = themesList.get(position);
            holder.setup( data, currentTheme == position );
            holder.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (currentTheme != position) {
                        PreferenceManager.setTheme(position);
                        themeChanged();
                        MainActivity.themeChanged();
                    }
                }
            });
        }

        @Override
        public int getItemCount() {
            return themesList.size();
        }
    }

    class ThemeHolder extends RecyclerView.ViewHolder {
        private RelativeLayout background;
        private LinearLayout colorsLayout;

        private int[] _data;

        public ThemeHolder(@NonNull View view) {
            super(view);
            background = view.findViewById(R.id.theme_holder);
            colorsLayout = view.findViewById(R.id.color_holder);
        }

        public void setup( int[] data, boolean isSelected ) {
            _data = data;
            if(isSelected) { background.setBackgroundResource(R.drawable.background_selected_color); } else { background.setBackground(null); }
            colorsLayout.removeAllViews();
            int size = Helper.convertDpToPixels(272) / _data.length;
            Log.v("sdasdad", size+"");
            for(int i = 0; i < _data.length; i++) {
                View v = colorsLayout.getChildAt(i);
                if (v == null) {
                    v = new View(mContext);
                    colorsLayout.addView(v);
                }
                v.setBackgroundColor( mContext.getColor(_data[i]) );
                v.getLayoutParams().width = size;
                v.getLayoutParams().height = size;
            }
//            ViewGroup.LayoutParams ll = colorsLayout.getLayoutParams();
//            ll.height = size;
//            colorsLayout.setLayoutParams(ll);
        }

        public void setOnClickListener(OnClickListener listener){
            background.setOnClickListener(listener);
        }
    }
}

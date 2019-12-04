package com.example.ericeddy.colours;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;
import android.widget.RelativeLayout;

public class DialogView extends RelativeLayout {

    protected Context mContext;

    protected RelativeLayout rootView;

    public DialogView(Context context) {
        super(context);
        mContext = context;
        init();
    }

    public DialogView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        mContext = context;
        init();
    }

    public DialogView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mContext = context;
        init();
    }

    public DialogView(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        mContext = context;
        init();
    }

    protected void init() {
        rootView = findViewById(R.id.root_view);
        rootView.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                closeDialog();
            }
        });
        RelativeLayout mainViewBG = findViewById(R.id.view_background);
        mainViewBG.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }

    public void displayDialog() {
        // Do animate in //
        rootView.setVisibility(View.VISIBLE);
    }

    public void closeDialog() {
        // Do Animate Out //
        rootView.setVisibility(View.GONE);
    }

}

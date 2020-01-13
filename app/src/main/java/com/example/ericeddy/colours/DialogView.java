package com.example.ericeddy.colours;

import android.app.Activity;
import android.content.Context;
import android.graphics.LinearGradient;
import android.graphics.Shader;
import android.graphics.drawable.PaintDrawable;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.RectShape;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
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
        updateBackgroundGradient();
    }

    public void displayDialog() {
        // Do animate in //
        rootView.setVisibility(View.VISIBLE);
    }

    public void closeDialog() {
        // Do Animate Out //
        rootView.setVisibility(View.GONE);
    }


    public void updateBackgroundGradient() {
        final int[] colors = new int[] {
                Helper.getContextColor(R.color.setting_bar_1),
                Helper.getContextColor(R.color.setting_bar_2),
                Helper.getContextColor(R.color.setting_bar_2),
                Helper.getContextColor(R.color.setting_bar_1)
        };

        final float[] positions = new float[] {
                0, 0.08f, 0.92f, 1f
        };
//        LinearGradient lg = new LinearGradient(0, 0, rootView.getMeasuredWidth(), rootView.getMeasuredHeight(), colors, positions, Shader.TileMode.REPEAT);
        ShapeDrawable.ShaderFactory shaderFactory = new ShapeDrawable.ShaderFactory() {
            @Override
            public Shader resize(int width, int height) {
                float angleInRadians = (float)Math.toRadians(90);

                float endX = (float)Math.cos(angleInRadians) * width;
                float endY = (float)Math.sin(angleInRadians) * height;

                LinearGradient linearGradient = new LinearGradient(0, 0, endX, endY,
                        colors,
                        positions,
                        Shader.TileMode.REPEAT);
                return linearGradient;
            }
        };

        PaintDrawable paint = new PaintDrawable();
        paint.setShape(new RectShape());
        paint.setShaderFactory(shaderFactory);
        paint.setCornerRadius(Helper.convertDpToPixels(8));

        RelativeLayout mainViewBG = findViewById(R.id.view_background);
        mainViewBG.setBackground(paint);
    }
}

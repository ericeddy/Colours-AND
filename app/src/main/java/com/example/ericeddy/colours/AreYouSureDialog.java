package com.example.ericeddy.colours;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.EditText;
import android.widget.RelativeLayout;

public class AreYouSureDialog extends DialogView {

    public RelativeLayout yesButton;
    public RelativeLayout noButton;

    public ColourPanel panel;
    public DBManager dbManager;

    public AreYouSureDialog(Context context) {
        super(context);
        init();
    }

    public AreYouSureDialog(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public AreYouSureDialog(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    public AreYouSureDialog(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init();
    }

    @Override
    protected void init() {
        LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.are_you_sure_dialog, this, true);

        super.init();

        yesButton = findViewById(R.id.yes_btn);
        noButton = findViewById(R.id.no_btn);
    }

    public void setYesButtonAction(OnClickListener listener) {
        yesButton.setOnClickListener(listener);
    }

    public void setNoButtonAction(OnClickListener listener) {
        noButton.setOnClickListener(listener);
    }
}

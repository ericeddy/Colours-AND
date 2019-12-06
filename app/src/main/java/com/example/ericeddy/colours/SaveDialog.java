package com.example.ericeddy.colours;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.RelativeLayout;

public class SaveDialog extends DialogView {

    public RelativeLayout saveButton;
    public EditText fileName;

    public ColourPanel panel;
    public DBManager dbManager;

    public SaveDialog(Context context) {
        super(context);
        init();
    }

    public SaveDialog(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public SaveDialog(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    public SaveDialog(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init();
    }

    @Override
    protected void init() {
        LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.save_dialog, this, true);

        super.init();

        fileName = findViewById(R.id.filename);
        saveButton = findViewById(R.id.save_btn);
        
        saveButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                save();
            }
        });
    }

    private void save() {
        int[][] cellsToSerialize = panel.getCellsCopy();
        String serializedCells = SQLiteHelper.serialize(cellsToSerialize);
        String title = fileName.getText().toString();
        dbManager.insert(title, serializedCells, 0);

        closeDialog();
    }

    @Override
    public void displayDialog() {
        super.displayDialog();
        fileName.setText("Untitled");
        fileName.requestFocus();
        InputMethodManager inputMethodManager = (InputMethodManager) mContext.getSystemService(Context.INPUT_METHOD_SERVICE);
        inputMethodManager.toggleSoftInput(InputMethodManager.SHOW_FORCED, 0);
    }
}

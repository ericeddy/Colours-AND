package com.example.ericeddy.colours;

import android.content.Context;
import android.database.Cursor;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.io.IOException;

public class LoadDialog extends DialogView {

    public RelativeLayout saveButton;
    public EditText fileName;

    public TextView emptyState;

    public ColourPanel panel;
    public DBManager dbManager;

    private LoadedDesignAdapter adapter;

    private RecyclerView recyclerView;

    public LoadDialog(Context context) {
        super(context);
        init();
    }

    public LoadDialog(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public LoadDialog(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    public LoadDialog(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init();
    }

    @Override
    protected void init() {
        LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.load_dialog, this, true);

        super.init();

        LinearLayoutManager ll = new LinearLayoutManager(mContext);
        recyclerView = findViewById(R.id.loaded_designs);
        recyclerView.setLayoutManager(ll);

        emptyState = findViewById(R.id.empty_state);
//        adapter = new SimpleCursorAdapter(mContext, R.layout.saved_design_holder, cursor, from, to, 0);
    }


    public class LoadedDesignAdapter extends RecyclerView.Adapter<LoadedDesignHolder> {
        private Cursor mCursor;
        private boolean mDataValid;
        private int mRowIDColumn;

        public LoadedDesignAdapter(Cursor c) {
            setHasStableIds(true);
            swapCursor(c);
        }

        @NonNull
        @Override
        public LoadedDesignHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
            View formNameView = LayoutInflater.from(parent.getContext()).inflate(R.layout.saved_design_holder, parent, false);
            return new LoadedDesignHolder(formNameView);
        }

        @Override
        public void onBindViewHolder(LoadedDesignHolder holder, int position) {

            if (!mDataValid) {
                throw new IllegalStateException("Cannot bind view holder when cursor is in invalid state.");
            }
            if (!mCursor.moveToPosition(position)) {
                throw new IllegalStateException("Could not move cursor to position " + position + " when trying to bind view holder");
            }

            int mColumnIndexName = mCursor.getColumnIndex(SQLiteHelper.TITLE);
            String designName = mCursor.getString(mColumnIndexName);

            int mColumnIndexId = mCursor.getColumnIndex(SQLiteHelper._ID);
            int designId = mCursor.getInt(mColumnIndexId);

            int mColumnIndexData = mCursor.getColumnIndex(SQLiteHelper.DATA);
            String designData = mCursor.getString(mColumnIndexData);

            int mColumnIndexColor = mCursor.getColumnIndex(SQLiteHelper.COLOR);
            int designColor = mCursor.getInt(mColumnIndexColor);

            holder.setup(designId, designName, designData);
        }


        @Override
        public int getItemCount() {
            if (mDataValid) {
                int count = mCursor.getCount();
                emptyState.setVisibility(count == 0 ? View.VISIBLE : View.GONE);
                return count;
            } else {
                return 0;
            }
        }

        @Override
        public long getItemId(int position) {
            if (!mDataValid) {
                throw new IllegalStateException("Cannot lookup item id when cursor is in invalid state.");
            }
            if (!mCursor.moveToPosition(position)) {
                throw new IllegalStateException("Could not move cursor to position " + position + " when trying to get an item id");
            }

            return mCursor.getLong(mRowIDColumn);
        }

        public Cursor getItem(int position) {
            if (!mDataValid) {
                throw new IllegalStateException("Cannot lookup item id when cursor is in invalid state.");
            }
            if (!mCursor.moveToPosition(position)) {
                throw new IllegalStateException("Could not move cursor to position " + position + " when trying to get an item id");
            }
            return mCursor;
        }

        public void swapCursor(Cursor newCursor) {
            if (newCursor == mCursor) {
                return;
            }

            if (newCursor != null) {
                mCursor = newCursor;
                mDataValid = true;
                // notify the observers about the new cursor
                notifyDataSetChanged();
            } else {
                notifyItemRangeRemoved(0, getItemCount());
                mCursor = null;
                mRowIDColumn = -1;
                mDataValid = false;
            }
        }
    }

    class LoadedDesignHolder extends RecyclerView.ViewHolder {

        private TextView title;
        private RelativeLayout deleteButton;

        private int _id;
        private String _data;

        public LoadedDesignHolder(@NonNull View view) {
            super(view);
            title = view.findViewById(R.id.title);
            deleteButton = view.findViewById(R.id.delete);
        }

        public void setup( int id, String titleString, String data) {
            title.setText(titleString);
            _id = id;
            _data = data;
            deleteButton.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    deleteSavedDesignAction(_id);
                }
            });
            title.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    try {
                        int[][] design = SQLiteHelper.deserialize(_data);
                        MainActivity.selectDesign(design);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            });
        }
    }

    private void deleteSavedDesignAction(int id) {
        MainActivity.displayAreYouSureDialogForDeleteDesign(id);
    }
    public void deleteSavedDesign(int id){
        dbManager.delete(id);
        // need to see if it needs to refetch //
        Cursor cursor = dbManager.fetch();
        adapter = new LoadedDesignAdapter(cursor);
        adapter.notifyDataSetChanged();

        recyclerView.setAdapter(adapter);
    }

    @Override
    public void displayDialog(RelativeLayout relativeLayout) {
        super.displayDialog(relativeLayout);
        Cursor cursor = dbManager.fetch();
        adapter = new LoadedDesignAdapter(cursor);
        adapter.notifyDataSetChanged();


        recyclerView.setAdapter(adapter);
    }
}

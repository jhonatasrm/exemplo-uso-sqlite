package com.jhonatasrm.exemplo_uso_sqlite.dialog;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;

import com.jhonatasrm.exemplo_uso_sqlite.R;
import com.jhonatasrm.exemplo_uso_sqlite.data.Content;

public class DeleteDialog extends DialogFragment implements DialogInterface.OnClickListener {

    private OnDeleteListener listener;
    private Content content;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.listener = (OnDeleteListener) context;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setMessage("\n" + content.getDescription() + "\n");
        builder.setPositiveButton(R.string.delete, this);
        builder.setNegativeButton(R.string.ok, this);
        return builder.create();
    }

    @Override
    public void onClick(DialogInterface dialog, int which) {
        if (which == DialogInterface.BUTTON_POSITIVE && listener != null) {
            listener.onDelete(content);
        }
    }

    public void setContent(Content content) {
        this.content = content;
    }

    // interface
    public interface OnDeleteListener {
        void onDelete(Content content);
    }
}

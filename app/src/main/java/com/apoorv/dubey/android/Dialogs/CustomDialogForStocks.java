package com.apoorv.dubey.android.Dialogs;

import android.app.Activity;
import android.app.Dialog;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;

import com.apoorv.dubey.android.instadia.R;
import com.apoorv.dubey.android.model.Stock;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;


public class CustomDialogForStocks extends Dialog implements
        android.view.View.OnClickListener {

    public Dialog dialog;
    public Activity activity;
    public DialogSaveClickedListener dialogSaveClicked;
    public Button btnSave;
    public Button btnCancel;
    private EditText edtName;
    private EditText edtItem;
    private EditText edtInQuantity;
    private EditText edtOutQuantity;

    public CustomDialogForStocks(Activity activity,DialogSaveClickedListener fragment) {
        super(activity);
        this.activity = activity;
        dialogSaveClicked = fragment;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.custom_dialog_for_stocks);
        btnSave = findViewById(R.id.btn_save);
        btnCancel = findViewById(R.id.btn_cancel);
        edtName = findViewById(R.id.edt_name);
        edtItem = findViewById(R.id.edt_item);
        edtInQuantity = findViewById(R.id.edt_in_quantity);
        edtOutQuantity = findViewById(R.id.edt_out_quantity);
        btnSave.setOnClickListener(this);
        btnCancel.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_save:
                Stock stock = new Stock();
                stock.setId(String.valueOf(System.currentTimeMillis()));
                stock.setVendor(edtName.getText().toString());
                stock.setItem(edtItem.getText().toString());
                stock.setDate(getBookingTimestamp());
                stock.setInQuantity(Integer.parseInt(edtInQuantity.getText().toString()));
                stock.setOutQuantity(Integer.parseInt(edtOutQuantity.getText().toString()));
                dialogSaveClicked.onDialogSaveButtonClicked(stock);
                break;

            case R.id.btn_cancel:
                dismiss();
            default:
                break;
        }
        dismiss();
    }

    private String getBookingTimestamp() {
        DateFormat dateFormat = new SimpleDateFormat("dd-MM-yy");
        dateFormat.setTimeZone(TimeZone.getDefault());
        String defaultTimezone = TimeZone.getDefault().getID();
        Date dateObj = new Date();
        return dateFormat.format(dateObj);
    }

    public interface DialogSaveClickedListener
    {
        void onDialogSaveButtonClicked(Stock stock);
    }

}


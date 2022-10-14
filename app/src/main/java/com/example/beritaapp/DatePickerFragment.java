package com.example.beritaapp;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.widget.DatePicker;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import java.util.Calendar;

public class DatePickerFragment extends DialogFragment implements DatePickerDialog.OnDateSetListener {
    @NonNull
    @Override
    // menampilkan calender dan menyimpan tanggal pilihan user
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        final Calendar c = Calendar.getInstance();
        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH);
        int day = c.get(Calendar.DAY_OF_MONTH);

        return new DatePickerDialog(getActivity(), this, year, month, day);
    }

    @Override
    // mengirimkan tanggal yg dipilih ke activity detailUser
    public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
        DetailUser main = (DetailUser) getActivity();
        main.processDatePickerResult(i2, i1, i);
    }
}
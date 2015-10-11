package com.jaitlapps.bestadvice.fragment;

import android.app.Dialog;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;

import com.jaitlapps.bestadvice.BestAdviceApplication;
import com.jaitlapps.bestadvice.R;

import java.util.Calendar;
import java.util.Date;

/**
 * При первом запуске сохраняем дату запуска
 * Выжидаем несколько дней, после этого дней показываем окно
 * После показа окна сохраняем настройку, запрещающую показ
 */
public class AdProVersionFragment extends DialogFragment {
    public static final String PREF_NAME = "AdProVersionFragment.pref";
    public static final String ALLOWED = "AdProVersionFragment.allowed";
    public static final String DATE = "AdProVersionFragment.date";
    public static final int COUNT_DAY = 3;

    private Context context;

    public void setContext(Context context) {
        this.context = context;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("Полезные советы Pro");
        builder.setIcon(R.drawable.ic_launcher);
        builder.setMessage("А вы знали что у нас есть Pro версия приложения \"Полезные советы\"? " +
                "В ней еще больше советов и отсутствует реклама =)");

        builder.setPositiveButton("Посмотреть", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                Intent intent = new Intent(Intent.ACTION_VIEW);
                try {
                    intent.setData(Uri.parse("market://details?id=com.jaitlapps.bestadvice.pro"));
                    getActivity().startActivity(intent);
                } catch (ActivityNotFoundException e) {
                    intent.setData(Uri.parse("http://play.google.com/store/apps/details?id=com.jaitlapps.bestadvice.pro"));
                    getActivity().startActivity(intent);
                } finally {
                    setNotAllowed();
                }
            }
        }).setNegativeButton("Мне не интересно", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialogInterface, int i) {
                setNotAllowed();
            }
        });

        return builder.create();
    }

    public boolean isShow() {
        if (BestAdviceApplication.isFree()) {
            if(isAllowed()) {
                if (isDateOccurred()) {
                    return BestAdviceApplication.isNetworkConnected(context);
                }
            }
        }

        return false;
    }

    /**
     * Разрешено ли показывать рекламу
     */
    private boolean isAllowed() {
        SharedPreferences sharedPref = getAdProPreferences();

        return sharedPref.getInt(ALLOWED, 1) == 1;
    }

    /**
     * Пришло ли время показа рекламы
     */
    private boolean isDateOccurred() {
        SharedPreferences sharedPref = getAdProPreferences();
        long dInstall = sharedPref.getLong(DATE, 0);

        if (dInstall == 0) {
            setCurrentDate();
            return false;
        }

        Date dateInstall = new Date(dInstall);

        Calendar c = Calendar.getInstance();
        c.setTime(dateInstall);
        c.add(Calendar.DAY_OF_MONTH, COUNT_DAY);

       return c.getTime().before(new Date());
    }

    /**
     * Устанавливает флаг запрещающий показ рекламы
     */
    private void setNotAllowed() {
        SharedPreferences.Editor editor = getPreferencesEditor();

        editor.putInt(ALLOWED, 0);
        editor.commit();
    }

    /**
     * Устанавливает текущую дату
     */
    private void setCurrentDate() {
        SharedPreferences.Editor editor = getPreferencesEditor();

        editor.putLong(DATE, new Date().getTime());
        editor.commit();
    }

    private SharedPreferences getAdProPreferences() {
        return context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
    }

    private SharedPreferences.Editor getPreferencesEditor() {
        return getAdProPreferences().edit();
    }

}

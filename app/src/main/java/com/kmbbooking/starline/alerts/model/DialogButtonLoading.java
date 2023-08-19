package com.kmbbooking.starline.alerts.model;

import com.kmbbooking.starline.alerts.AbstractDialogloading;

public class DialogButtonLoading {
    private String title;
    private int icon;
    private AbstractDialogloading.OnClickListener onClickListener;

    public DialogButtonLoading(String title, int icon, AbstractDialogloading.OnClickListener onClickListener) {
        this.title = title;
        this.icon = icon;
        this.onClickListener = onClickListener;
    }

    public String getTitle() {
        return title;
    }

    public int getIcon() {
        return icon;
    }

    public AbstractDialogloading.OnClickListener getOnClickListener() {
        return onClickListener;
    }
}

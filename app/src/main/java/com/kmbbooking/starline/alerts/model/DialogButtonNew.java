package com.kmbbooking.starline.alerts.model;

import com.kmbbooking.starline.alerts.AbstractDialogNew;

public class DialogButtonNew {
    private String title;
    private int icon;
    private AbstractDialogNew.OnClickListener onClickListener;

    public DialogButtonNew(String title, int icon, AbstractDialogNew.OnClickListener onClickListener) {
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

    public AbstractDialogNew.OnClickListener getOnClickListener() {
        return onClickListener;
    }
}

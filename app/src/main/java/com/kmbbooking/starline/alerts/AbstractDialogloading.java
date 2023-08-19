package com.kmbbooking.starline.alerts;

import android.app.Activity;
import android.app.Dialog;
import android.content.res.ColorStateList;
import android.content.res.Configuration;
import android.content.res.TypedArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RawRes;
import androidx.core.content.ContextCompat;

import com.airbnb.lottie.LottieAnimationView;
import com.kmbbooking.starline.R;
import com.kmbbooking.starline.alerts.interfaces.DialogInterface;
import com.kmbbooking.starline.alerts.interfaces.OnCancelListener;
import com.kmbbooking.starline.alerts.interfaces.OnDismissListener;
import com.kmbbooking.starline.alerts.interfaces.OnShowListener;

public class AbstractDialogloading implements DialogInterface {

    //Constants
    public static final int BUTTON_POSITIVE = 1;
    public static final int BUTTON_NEGATIVE = -1;
    public static final int NO_ICON = -111;
    public static final int NO_ANIMATION = -111;

    protected Dialog mDialog;
    protected Activity mActivity;
    protected String title;
    protected String message;
    protected boolean mCancelable;
    protected int mAnimationResId;
    protected String mAnimationFile;
    protected LottieAnimationView mAnimationView;

    protected OnDismissListener mOnDismissListener;
    protected OnCancelListener mOnCancelListener;
    protected OnShowListener mOnShowListener;


    protected AbstractDialogloading(@NonNull Activity mActivity,
                                    @NonNull String title,
                                    boolean mCancelable,
                                    @RawRes int mAnimationResId,
                                    @NonNull String mAnimationFile) {
        this.mActivity = mActivity;
        this.title = title;
        this.mCancelable = mCancelable;
        this.mAnimationResId = mAnimationResId;
        this.mAnimationFile = mAnimationFile;
    }

    protected View createView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container) {
        // Inflate and set the layout for the dialog
        // Pass null as the parent view because its going in the dialog layout
        final View dialogView = inflater.inflate(R.layout.layout_alert_dialog_load, container, false);

        // Initialize Views
        TextView mTitleView = dialogView.findViewById(R.id.textView_title);
        mAnimationView = dialogView.findViewById(R.id.animation_view);

        // Set Title
        if (title != null) {
            mTitleView.setVisibility(View.VISIBLE);
            mTitleView.setText(title);
        } else {
            mTitleView.setVisibility(View.GONE);
        }


        // If Orientation is Horizontal, Hide AnimationView
        int orientation = mActivity.getResources().getConfiguration().orientation;
        if (orientation == Configuration.ORIENTATION_LANDSCAPE) {
            mAnimationView.setVisibility(View.GONE);
        } else {
            // Set Animation from Resource
            if (mAnimationResId != NO_ANIMATION) {
                mAnimationView.setVisibility(View.VISIBLE);
                mAnimationView.setAnimation(mAnimationResId);
                mAnimationView.playAnimation();

                // Set Animation from Assets File
            } else if (mAnimationFile != null) {
                mAnimationView.setVisibility(View.VISIBLE);
                mAnimationView.setAnimation(mAnimationFile);
                mAnimationView.playAnimation();

            } else {
                mAnimationView.setVisibility(View.GONE);
            }
        }

        // Apply Styles
        TypedArray a = mActivity.getTheme().obtainStyledAttributes(R.styleable.MaterialDialog);

        try {
            // Set Dialog Background
            dialogView.setBackgroundColor(
                    a.getColor(R.styleable.MaterialDialog_material_dialog_background,
                            mActivity.getResources().getColor(R.color.material_dialog_background)));

            // Set Title Text Color
            mTitleView.setTextColor(
                    a.getColor(R.styleable.MaterialDialog_material_dialog_title_text_color,
                            mActivity.getResources().getColor(R.color.material_dialog_title_text_color)));


            // Set Positive Button Icon Tint
            ColorStateList mPositiveButtonTint = a.getColorStateList(
                    R.styleable.MaterialDialog_material_dialog_positive_button_text_color);

            if (mPositiveButtonTint == null) {
                mPositiveButtonTint = ContextCompat.getColorStateList(
                        mActivity.getApplicationContext(),
                        R.color.material_dialog_positive_button_text_color);
            }

            // Set Negative Button Icon & Text Tint
            ColorStateList mNegativeButtonTint = a.getColorStateList(
                    R.styleable.MaterialDialog_material_dialog_negative_button_text_color);

            if (mNegativeButtonTint == null) {
                mNegativeButtonTint = ContextCompat.getColorStateList(
                        mActivity.getApplicationContext(),
                        R.color.material_dialog_negative_button_text_color);
            }

            // Set Positive Button Background Tint
            ColorStateList mBackgroundTint = a.getColorStateList(
                    R.styleable.MaterialDialog_material_dialog_positive_button_color);

            if (mBackgroundTint == null) {
                mBackgroundTint = ContextCompat.getColorStateList(
                        mActivity.getApplicationContext(),
                        R.color.material_dialog_positive_button_color);
            }


        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            a.recycle();
        }

        return dialogView;
    }

    /**
     * Displays the Dialog
     */
    public void show() {
        if (mDialog != null) {
            mDialog.show();
        } else {
            throwNullDialog();
        }
    }

    /**
     * Cancels the Dialog
     */
    @Override
    public void cancel() {
        if (mDialog != null) {
            mDialog.cancel();
        } else {
            throwNullDialog();
        }
    }

    /**
     * Dismisses the Dialog
     */
    @Override
    public void dismiss() {
        if (mDialog != null) {
            mDialog.dismiss();
        } else {
            throwNullDialog();
        }
    }

    /**
     * @param onShowListener interface for callback events when dialog is showed.
     */
    public void setOnShowListener(@NonNull final OnShowListener onShowListener) {
        this.mOnShowListener = onShowListener;

        mDialog.setOnShowListener(new android.content.DialogInterface.OnShowListener() {
            @Override
            public void onShow(android.content.DialogInterface dialogInterface) {
                showCallback();
            }
        });
    }

    /**
     * @param onCancelListener interface for callback events when dialog is cancelled.
     */
    public void setOnCancelListener(@NonNull final OnCancelListener onCancelListener) {
        this.mOnCancelListener = onCancelListener;

        mDialog.setOnCancelListener(new android.content.DialogInterface.OnCancelListener() {
            @Override
            public void onCancel(android.content.DialogInterface dialogInterface) {
                cancelCallback();
            }
        });
    }

    /**
     * @param onDismissListener interface for callback events when dialog is dismissed;
     */
    public void setOnDismissListener(@NonNull final OnDismissListener onDismissListener) {
        this.mOnDismissListener = onDismissListener;

        mDialog.setOnDismissListener(new android.content.DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(android.content.DialogInterface dialogInterface) {
                dismissCallback();
            }
        });
    }

    /**
     * @return {@link LottieAnimationView} from the Dialog.
     */
    public LottieAnimationView getAnimationView() {
        return mAnimationView;
    }

    private void showCallback() {
        if (mOnShowListener != null) {
            mOnShowListener.onShow(this);
        }
    }

    private void dismissCallback() {
        if (mOnDismissListener != null) {
            mOnDismissListener.onDismiss(this);
        }
    }

    private void cancelCallback() {
        if (mOnCancelListener != null) {
            mOnCancelListener.onCancel(this);
        }
    }

    private void throwNullDialog() {
        throw new NullPointerException("Called method on null Dialog. Create dialog using `Builder` before calling on Dialog");
    }

    public interface OnClickListener {
        void onClick(DialogInterface dialogInterface, int which);
    }
}
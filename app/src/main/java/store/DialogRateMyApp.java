package store;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.LayerDrawable;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import es.dmoral.prefs.Prefs;
import myfabric.LogFabric;
import myfabric.R;

/**
 * Created by D on 3/11/2018.
 */

public class DialogRateMyApp {
    LogFabric mLogFabric;
    Activity mContext;

    public DialogRateMyApp(Activity mContext) {
        this.mContext = mContext;
    }

    public DialogRateMyApp(Activity mContext, LogFabric mLogFabric) {
        this.mContext = mContext;
        this.mLogFabric = mLogFabric;
    }

    public void show() {
        boolean action = Prefs.with(mContext).readBoolean("action");
        if (!action) {
            if (!Prefs.with(mContext).readBoolean("rate", false))
                showDialog();
            else mContext.finish();
        } else mContext.finish();
    }

    private void showDialog() {
        if (mLogFabric != null)
            mLogFabric.log("DialogRateMyApp", "Show", "Show DialogRateMyApp");
        final Dialog dialog1 = new Dialog(mContext);
        dialog1.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog1.requestWindowFeature(Window.FEATURE_NO_TITLE);
        LayoutInflater layoutInflater = LayoutInflater.from(mContext);
        dialog1.setContentView(layoutInflater.inflate(R.layout.dialog_rate_app, null));
        dialog1.setCancelable(true);
        final TextView btnRate = (TextView) dialog1.findViewById(R.id.btnRate);
        TextView btnLater = (TextView) dialog1.findViewById(R.id.btnLater);
        btnRate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mLogFabric != null) mLogFabric.log("DialogRateMyApp", "Click", "Rate");
                Toast.makeText(mContext, mContext.getString(R.string.sms_thank_you_rate), Toast.LENGTH_SHORT).show();
                Prefs.with(mContext).writeBoolean("rate", true);
                Intent i = new Intent("android.intent.action.VIEW");
                i.setData(Uri.parse("https://play.google.com/store/apps/details?id=" + mContext.getPackageName()));
                mContext.startActivity(i);
                mContext.finish();
                dialog1.dismiss();
                mContext.finish();
            }
        });
        btnLater.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mLogFabric != null) mLogFabric.log("DialogRateMyApp", "Click", "Late");
                Prefs.with(mContext).writeBoolean("rate", false);
                dialog1.dismiss();
                mContext.finish();
            }
        });
        RatingBar mRatingBar = (RatingBar) dialog1.findViewById(R.id.mRatingBar);
        LayerDrawable stars = (LayerDrawable) mRatingBar.getProgressDrawable();
        stars.getDrawable(0).setColorFilter(mContext.getResources().getColor(R.color.colorLater), PorterDuff.Mode.SRC_ATOP);
        mRatingBar.setRating(5);
        dialog1.show();
    }

}

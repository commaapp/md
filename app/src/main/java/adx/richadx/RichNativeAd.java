package adx.richadx;

import android.content.Context;
import android.view.View;
import android.widget.LinearLayout;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;


import adx.inter.OnErrorLoadAd;

import static com.google.android.gms.ads.AdSize.MEDIUM_RECTANGLE;

/**
 * Created by d on 1/24/2018.
 */
/*


 */
public class RichNativeAd {
    OnErrorLoadAd onErrorLoadAd;
    Context mContext;
    View mViewContainer;
    String idAd;

    public RichNativeAd(Context mContext, View mViewContainer, String idAd) {
        this.mContext = mContext;
        this.mViewContainer = mViewContainer;
        this.idAd = idAd;
    }

    public void setOnErrorLoadAd(OnErrorLoadAd onErrorLoadAd) {
        this.onErrorLoadAd = onErrorLoadAd;
    }

    private AdView adViewPlayer;

    public void show() {
        adViewPlayer = new AdView(mContext);
        adViewPlayer.setAdSize(MEDIUM_RECTANGLE);
        adViewPlayer.setAdUnitId(idAd); // richadx
        final LinearLayout nativeAdContainer = (LinearLayout) mViewContainer;
        try {
            AdRequest.Builder adRequestBuilderHeader = new AdRequest.Builder();
            nativeAdContainer.removeAllViews();
            nativeAdContainer.addView(adViewPlayer);
            adViewPlayer.loadAd(adRequestBuilderHeader.build());
            adViewPlayer.setAdListener(
                    new com.google.android.gms.ads.AdListener() {
                        @Override
                        public void onAdFailedToLoad(int i) {
                            super.onAdFailedToLoad(i);
                            if (onErrorLoadAd != null)
                                onErrorLoadAd.onMyError();
                        }
                    });

        } catch (Exception e) {
        }

    }
}

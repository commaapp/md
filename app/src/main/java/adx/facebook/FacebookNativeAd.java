
package adx.facebook;

import android.content.Context;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.facebook.ads.Ad;
import com.facebook.ads.AdChoicesView;
import com.facebook.ads.AdError;
import com.facebook.ads.AdIconView;
import com.facebook.ads.MediaView;
import com.facebook.ads.NativeAd;
import com.facebook.ads.NativeAdListener;

import java.util.ArrayList;
import java.util.List;

import adx.inter.OnErrorLoadAd;
import myfabric.R;

public class FacebookNativeAd {

    NativeAd nativeAd;
    OnErrorLoadAd onErrorLoadAd;
    Context mContext;
    View mViewContainer;
    String idAd;

    public FacebookNativeAd(Context mContext, View mViewContainer, String idAd) {
        this.mContext = mContext;
        this.mViewContainer = mViewContainer;
        this.idAd = idAd;
    }

    public void show() {
        nativeAd = new NativeAd(mContext, idAd);
        nativeAd.setAdListener(new NativeAdListener() {

            @Override
            public void onMediaDownloaded(Ad ad) {

            }

            @Override
            public void onAdLoaded(Ad ad) {
                LinearLayout nativeAdContainer = (LinearLayout) mViewContainer;
                View adView = View.inflate(mContext, R.layout.facebook_native_ad_layout_300, null);
                nativeAdContainer.addView(adView);

                // Add the AdChoices icon
                LinearLayout adChoicesContainer = adView.findViewById(R.id.ad_choices_container);
                AdChoicesView adChoicesView = new AdChoicesView(mContext, nativeAd, true);
                adChoicesContainer.addView(adChoicesView, 0);

                // Create native UI using the ad metadata.
                AdIconView nativeAdIcon = adView.findViewById(R.id.native_ad_icon);
                TextView nativeAdTitle = adView.findViewById(R.id.native_ad_title);
                MediaView nativeAdMedia = adView.findViewById(R.id.native_ad_media);
                TextView nativeAdSocialContext = adView.findViewById(R.id.native_ad_social_context);
                TextView nativeAdBody = adView.findViewById(R.id.native_ad_body);
                TextView sponsoredLabel = adView.findViewById(R.id.sponsored_label);
                Button nativeAdCallToAction = adView.findViewById(R.id.native_ad_call_to_action);

                // Set the Text.
                nativeAdTitle.setText(nativeAd.getAdvertiserName());
                nativeAdBody.setText(nativeAd.getAdBodyText());
                nativeAdSocialContext.setText(nativeAd.getAdSocialContext());
                nativeAdCallToAction.setVisibility(nativeAd.hasCallToAction() ? View.VISIBLE : View.INVISIBLE);
                nativeAdCallToAction.setText(nativeAd.getAdCallToAction());
                sponsoredLabel.setText(nativeAd.getSponsoredTranslation());

                // Create a list of clickable views
                List<View> clickableViews = new ArrayList<>();
                clickableViews.add(nativeAdTitle);
                clickableViews.add(nativeAdCallToAction);

                // Register the Title and CTA button to listen for clicks.
                nativeAd.registerViewForInteraction(
                        adView,
                        nativeAdMedia,
                        nativeAdIcon,
                        clickableViews);
        }

        @Override
        public void onError (Ad ad, AdError error){
            if (onErrorLoadAd != null)
                onErrorLoadAd.onMyError();
        }

        @Override
        public void onAdClicked (Ad ad){
        }

        @Override
        public void onLoggingImpression (Ad ad){
        }
    });
        nativeAd.loadAd();
}


    public void setOnErrorLoadAd(OnErrorLoadAd onErrorLoadAd) {
        this.onErrorLoadAd = onErrorLoadAd;

    }

}

package myfabric;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Build;
import android.util.Log;
import android.widget.Toast;

import com.crashlytics.android.answers.Answers;
import com.crashlytics.android.answers.CustomEvent;
import com.google.gson.Gson;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;

import java.io.IOException;
import java.util.Collection;

import main.MainActivity;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import utils.Connection;

public class LogFabric {
    public void log(String eventName, String key, String value) {
//        Answers.getInstance().logCustom(new CustomEvent("Video Played")
//                .putCustomAttribute("Category", "Comedy")
//                .putCustomAttribute("Length", 350));
        Answers.getInstance().logCustom(new CustomEvent(eventName)
                .putCustomAttribute(key, value));
    }

    public static void AnalyticCountry(final Context context) {
        if (!Connection.isOnline(context)) return;
        new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... voids) {


                try {
                    OkHttpClient client = new OkHttpClient();
                    Request request = new Request.Builder()
                            .url("http://ip-api.com/json")
                            .build();
                    Response responses = null;
                    responses = client.newCall(request).execute();
                    String jsonData = responses.body().string();

                    IPCountry ipCountry = new Gson().fromJson(jsonData, IPCountry.class);
                    if (ipCountry != null)
                        Answers.getInstance().logCustom(new CustomEvent("IP Country")
                                .putCustomAttribute("As", ipCountry.getAs())
                                .putCustomAttribute("City", ipCountry.getCity())
                                .putCustomAttribute("Country", ipCountry.getCountry())
                                .putCustomAttribute("CountryCode", ipCountry.getCountryCode())
                                .putCustomAttribute("Isp", ipCountry.getIsp())
                                .putCustomAttribute("Lat", ipCountry.getLat())
                                .putCustomAttribute("Lon", ipCountry.getLon())
                                .putCustomAttribute("Org", ipCountry.getOrg())
                                .putCustomAttribute("Query", ipCountry.getQuery())
                                .putCustomAttribute("Region", ipCountry.getRegion())
                                .putCustomAttribute("RegionName", ipCountry.getRegionName())
                                .putCustomAttribute("Status", ipCountry.getStatus())
                                .putCustomAttribute("Timezone", ipCountry.getTimezone())
                                .putCustomAttribute("Zip", ipCountry.getZip())
                        );
                } catch (Exception e) {
                    e.printStackTrace();
                }

                return null;
            }
        }.execute();

    }

    public class IPCountry {
        private String as;
        private String city;
        private String country;
        private String countryCode;
        private String isp;
        private float lat;
        private float lon;
        private String org;
        private String query;
        private String region;
        private String regionName;
        private String status;
        private String timezone;
        private String zip;

        public IPCountry(String as, String city, String country, String countryCode, String isp, float lat, float lon, String org, String query, String region, String regionName, String status, String timezone, String zip) {
            this.as = as;
            this.city = city;
            this.country = country;
            this.countryCode = countryCode;
            this.isp = isp;
            this.lat = lat;
            this.lon = lon;
            this.org = org;
            this.query = query;
            this.region = region;
            this.regionName = regionName;
            this.status = status;
            this.timezone = timezone;
            this.zip = zip;
        }


        public String getAs() {
            return as;
        }

        public String getCity() {
            return city;
        }

        public String getCountry() {
            return country;
        }

        public String getCountryCode() {
            return countryCode;
        }

        public String getIsp() {
            return isp;
        }

        public float getLat() {
            return lat;
        }

        public float getLon() {
            return lon;
        }

        public String getOrg() {
            return org;
        }

        public String getQuery() {
            return query;
        }

        public String getRegion() {
            return region;
        }

        public String getRegionName() {
            return regionName;
        }

        public String getStatus() {
            return status;
        }

        public String getTimezone() {
            return timezone;
        }

        public String getZip() {
            return zip;
        }

        // Setter Methods

        public void setAs(String as) {
            this.as = as;
        }

        public void setCity(String city) {
            this.city = city;
        }

        public void setCountry(String country) {
            this.country = country;
        }

        public void setCountryCode(String countryCode) {
            this.countryCode = countryCode;
        }

        public void setIsp(String isp) {
            this.isp = isp;
        }

        public void setLat(float lat) {
            this.lat = lat;
        }

        public void setLon(float lon) {
            this.lon = lon;
        }

        public void setOrg(String org) {
            this.org = org;
        }

        public void setQuery(String query) {
            this.query = query;
        }

        public void setRegion(String region) {
            this.region = region;
        }

        public void setRegionName(String regionName) {
            this.regionName = regionName;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public void setTimezone(String timezone) {
            this.timezone = timezone;
        }

        public void setZip(String zip) {
            this.zip = zip;
        }

        @Override
        public String toString() {
            return "IPCountry{" +
                    "as='" + as + '\'' +
                    ", city='" + city + '\'' +
                    ", country='" + country + '\'' +
                    ", countryCode='" + countryCode + '\'' +
                    ", isp='" + isp + '\'' +
                    ", lat=" + lat +
                    ", lon=" + lon +
                    ", org='" + org + '\'' +
                    ", query='" + query + '\'' +
                    ", region='" + region + '\'' +
                    ", regionName='" + regionName + '\'' +
                    ", status='" + status + '\'' +
                    ", timezone='" + timezone + '\'' +
                    ", zip='" + zip + '\'' +
                    '}';
        }
    }
}

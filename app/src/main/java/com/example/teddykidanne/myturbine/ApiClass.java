package com.example.teddykidanne.myturbine;

/**
 * Created by Teddykidanne on 7/22/16.
 */
public class ApiClass {
    public static String masterAPI = "http://ads.appia.com/getAds?id=236&password=OVUJ1DJN&siteId=4288&deviceId=4230&sessionId=techtestsession&totalCampaignsRequested=10";
    String res;


    // fetch Data
    public String xmlFetchFunction() {

        try {
            res = CustomHttpClient.executeHttpGet(masterAPI);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return res;
    }
}

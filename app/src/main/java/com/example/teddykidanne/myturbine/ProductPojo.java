package com.example.teddykidanne.myturbine;

/**
 * Created by Teddykidanne on 7/22/16.
 */
public class ProductPojo {

    String pLink, pName, cName, pDesc, noOfRatings, rating, rateLink;

    public ProductPojo(String pLink, String pName, String cName, String pDesc, String noOfRatings, String rating, String rateLink) {
        this.pLink = pLink;
        this.pName = pName;
        this.cName = cName;
        this.pDesc = pDesc;
        this.noOfRatings = noOfRatings;
        this.rating = rating;
        this.rateLink = rateLink;
    }

    public String getpLink() {
        return pLink;
    }

    public String getpName() {
        return pName;
    }

    public String getcName() {
        return cName;
    }

    public String getpDesc() {
        return pDesc;
    }

    public String getNoOfRatings() {
        return noOfRatings;
    }

    public String getRating() {
        return rating;
    }

    public String getRateLink() {
        return rateLink;
    }
}

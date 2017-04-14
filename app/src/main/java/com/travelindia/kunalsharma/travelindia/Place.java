package com.travelindia.kunalsharma.travelindia;

import java.io.Serializable;

/**
 * Created by kunal sharma on 05-Mar-17.
 */

class Place implements Serializable {

    private static final long serialVersionUID = 1L;

    private int Catid;
    private String Pname;
    private String Pthumbnail;
    private String Pthumbnailinfo;
    private String Pinfo;
    private String Pcity;
    private String Pstate;
    private String PCountry;
    private String Pnearby;
    //private String MorePlaces;


    public Place(int Catid,String name, String thumbnail, String thumbnailinfo, String info, String city, String State,String Country,String nearby) {
         Catid=Catid;
         Pname = name;
         Pthumbnail=thumbnail;
         Pthumbnailinfo=thumbnailinfo;
         Pinfo=info;
         Pcity=city;
         Pstate=State;
         PCountry=Country;
         Pnearby=nearby;

    }
    int getCatid() {
        return Catid;
    }

     String getPname() {
        return Pname;
    }

     String getPthumbnail() {
        return Pthumbnail;
    }

    String getPthumbnailinfo() {
        return Pthumbnailinfo;
    }

    String getPinfo() {
        return Pinfo;
    }

     String getPcity() {
        return Pcity;
    }

    String getPState() {
        return Pstate;
    }

    String getPCountry() {
        return PCountry;
    }

      String getPnearby() {
        return Pnearby;
    }

    /*String getMorePlaces() {
        return MorePlaces;
    }*/

    @Override
    public String toString() {
        return "Place{" +
                "  Catid='" + Catid + '\'' +
                ", Pname='" + Pname + '\'' +
                ", Pthumbnail='" + Pthumbnail + '\'' +
                ", Pthumbnailinfo='" + Pthumbnailinfo + '\'' +
                ", Pinfo='" + Pinfo + '\'' +
                ", Pcity='" + Pcity + '\'' +
                ", PState='" + Pstate + '\'' +
                ", PCountry='" + PCountry + '\'' +
                ", Pnearby='" + Pnearby + '\'' +
          '}';
    }
}

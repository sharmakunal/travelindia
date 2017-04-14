package com.travelindia.kunalsharma.travelindia;

import java.io.Serializable;
/**
 * Created by kunal sharma on 14-Apr-17.
 */

public class Category implements Serializable {

    private static final long serialVersionUID = 1L;

    //private int Catid;
    private String CatName;
    private String Catthumbnail;

    public Category(String CatName,String Catthumbnail){
        CatName=CatName;
        Catthumbnail=Catthumbnail;
    }


    //public int getCatid() {
      //  return Catid;
    //}

    public String getCatName() {
        return CatName;
    }
    public String getCatthumbnail() {
        return Catthumbnail;
    }

    @Override
    public String toString() {
        return "Category{" +
               // "  Catid='" + Catid + '\'' +
                " CatName='" + CatName + '\'' +
                ", Catthumbnail='" + Catthumbnail + '\'' +
                '}';
    }

}

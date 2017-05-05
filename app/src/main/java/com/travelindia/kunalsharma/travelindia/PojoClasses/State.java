package com.travelindia.kunalsharma.travelindia.PojoClasses;

import java.io.Serializable;

/**
 * Created by kunal sharma on 01-May-17.
 */

public class State implements Serializable {

    private static final long serialVersionUID = 1L;
    private int Stateid;
    private String StateName;
    private String Statethumbnail;
    private String Statethumbnailinfo;

    public State(int stateid, String stateName, String statethumbnail, String statethumbnailinfo) {
        Stateid = stateid;
        StateName = stateName;
        Statethumbnail = statethumbnail;
        Statethumbnailinfo = statethumbnailinfo;
    }


    public int getStateid() {
        return Stateid;
    }

    public String getStateName() {
        return StateName;
    }

    public String getStatethumbnail() {
        return Statethumbnail;
    }

    public String getStatethumbnailinfo() {
        return Statethumbnailinfo;
    }

    @Override
    public String toString() {
        return "State{" +
                "  Stateid='" + Stateid + '\'' +
                ", StateName='" + StateName + '\'' +
                ", Statethumbnail='" + Statethumbnail + '\'' +
                ", Statethumbnailinfo='" + Statethumbnailinfo + '\'' +
                '}';
    }
}

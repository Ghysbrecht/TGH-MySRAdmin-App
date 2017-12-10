package be.chickendinnerinc.school.dbtest;

import android.provider.Telephony;

import com.google.gson.annotations.Expose;

/**
 * Created by Thomas on 8/12/2017.
 */

public class Participant {
    @Expose
    private String name;
    @Expose
    private String uid;

    Participant(String name, String uid){
        this.name = name;
        this.uid = uid;
    }

    public String getName() {
        return name;
    }

    public String getUid() {
        return uid;
    }
}

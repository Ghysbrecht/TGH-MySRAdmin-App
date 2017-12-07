package be.chickendinnerinc.school.dbtest;

import com.google.gson.annotations.Expose;

/**
 * Created by Thomas on 7/12/2017.
 */

public class Score {
    @Expose
    private String timediff;
    @Expose
    private String participant_name;

    public String getTime() {
        return timediff;
    }

    public String getName() {
        return participant_name;
    }
}

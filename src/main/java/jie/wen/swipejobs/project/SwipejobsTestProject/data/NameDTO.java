package jie.wen.swipejobs.project.SwipejobsTestProject.data;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class NameDTO implements Serializable {
    @SerializedName("last")
    private String last;

    @SerializedName("first")
    private String first;

    public String getLast() {
        return last;
    }

    public void setLast(String last) {
        this.last = last;
    }

    public String getFirst() {
        return first;
    }

    public void setFirst(String first) {
        this.first = first;
    }
}

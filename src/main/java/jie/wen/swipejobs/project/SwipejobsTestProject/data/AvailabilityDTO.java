package jie.wen.swipejobs.project.SwipejobsTestProject.data;

import com.google.gson.annotations.SerializedName;

public class AvailabilityDTO {
    @SerializedName("title")
    private String title;

    @SerializedName("dayIndex")
    private int dayIndex;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getDayIndex() {
        return dayIndex;
    }

    public void setDayIndex(int dayIndex) {
        this.dayIndex = dayIndex;
    }
}
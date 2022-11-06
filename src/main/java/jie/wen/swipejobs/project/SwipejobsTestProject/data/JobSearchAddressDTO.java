package jie.wen.swipejobs.project.SwipejobsTestProject.data;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class JobSearchAddressDTO implements Serializable {
    @SerializedName("unit")
    private String unit;

    @SerializedName("maxJobDistance")
    private int maxJobDistance;

    @SerializedName("longitude")
    private float longitude;

    @SerializedName("latitude")
    private float latitude;

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public int getMaxJobDistance() {
        return maxJobDistance;
    }

    public void setMaxJobDistance(int maxJobDistance) {
        this.maxJobDistance = maxJobDistance;
    }

    public float getLongitude() {
        return longitude;
    }

    public void setLongitude(float longitude) {
        this.longitude = longitude;
    }

    public float getLatitude() {
        return latitude;
    }

    public void setLatitude(float latitude) {
        this.latitude = latitude;
    }
}

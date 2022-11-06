package jie.wen.swipejobs.project.SwipejobsTestProject.data;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class JobDTO implements Serializable {
    @SerializedName("driverLicenseRequired")
    private boolean driverLicenseRequired;

    @SerializedName("requiredCertificates")
    private List<String> requiredCertificates;

    @SerializedName("location")
    private LocationDTO location;

    @SerializedName("billRate")
    private String billRate;

    @SerializedName("workersRequired")
    private int workersRequired;

    @SerializedName("startDate")
    private String startDate;

    @SerializedName("about")
    private String about;

    @SerializedName("jobTitle")
    public String jobTitle;

    @SerializedName("company")
    public String company;

    @SerializedName("guid")
    public String guid;

    @SerializedName("jobId")
    public long jobId;

    public boolean isDriverLicenseRequired() {
        return driverLicenseRequired;
    }

    public void setDriverLicenseRequired(boolean driverLicenseRequired) {
        this.driverLicenseRequired = driverLicenseRequired;
    }

    public List<String> getRequiredCertificates() {
        return requiredCertificates;
    }

    public void setRequiredCertificates(List<String> requiredCertificates) {
        this.requiredCertificates = requiredCertificates;
    }

    public LocationDTO getLocation() {
        return location;
    }

    public void setLocation(LocationDTO location) {
        this.location = location;
    }

    public String getBillRate() {
        return billRate;
    }

    public void setBillRate(String billRate) {
        this.billRate = billRate;
    }

    public int getWorkersRequired() {
        return workersRequired;
    }

    public void setWorkersRequired(int workersRequired) {
        this.workersRequired = workersRequired;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getAbout() {
        return about;
    }

    public void setAbout(String about) {
        this.about = about;
    }

    public String getJobTitle() {
        return jobTitle;
    }

    public void setJobTitle(String jobTitle) {
        this.jobTitle = jobTitle;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public String getGuid() {
        return guid;
    }

    public void setGuid(String guid) {
        this.guid = guid;
    }

    public long getJobId() {
        return jobId;
    }

    public void setJobId(long jobId) {
        this.jobId = jobId;
    }
}


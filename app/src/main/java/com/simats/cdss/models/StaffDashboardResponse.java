package com.simats.cdss.models;

import com.google.gson.annotations.SerializedName;
import java.util.List;

public class StaffDashboardResponse {

    @SerializedName("staff")
    private StaffInfo staff;

    @SerializedName("reassessments")
    private List<ReassessmentItem> reassessments;

    @SerializedName("pending_count")
    private int pendingCount;

    public StaffInfo getStaff() { return staff; }
    public List<ReassessmentItem> getReassessments() { return reassessments; }
    public int getPendingCount() { return pendingCount; }

    public static class StaffInfo {
        @SerializedName("name")
        private String name;

        public String getName() { return name; }
    }

    public static class ReassessmentItem {
        @SerializedName("id")
        private int id;

        @SerializedName("type")
        private String type;

        @SerializedName("patient_id")
        private int patientId;

        @SerializedName("patient_name")
        private String patientName;

        @SerializedName("bed_number")
        private String bedNumber;

        @SerializedName("due_in")
        private int dueIn;

        public int getId() { return id; }
        public String getType() { return type; }
        public int getPatientId() { return patientId; }
        public String getPatientName() { return patientName; }
        public String getBedNumber() { return bedNumber; }
        public int getDueIn() { return dueIn; }
    }
}

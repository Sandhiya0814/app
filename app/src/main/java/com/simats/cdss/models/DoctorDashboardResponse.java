package com.simats.cdss.models;

import com.google.gson.annotations.SerializedName;
import java.util.List;

public class DoctorDashboardResponse {

    @SerializedName("total_patients")
    private int totalPatients;

    @SerializedName("critical_count")
    private int criticalCount;

    @SerializedName("warning_count")
    private int warningCount;

    @SerializedName("needs_attention_patients")
    private List<NeedsAttentionPatient> needsAttentionPatients;

    public int getTotalPatients() { return totalPatients; }
    public int getCriticalCount() { return criticalCount; }
    public int getWarningCount() { return warningCount; }
    public List<NeedsAttentionPatient> getNeedsAttentionPatients() { return needsAttentionPatients; }

    public static class NeedsAttentionPatient {
        @SerializedName("name")
        private String name;

        @SerializedName("patient_id")
        private int patientId;

        @SerializedName("room")
        private String room;

        @SerializedName("spo2")
        private String spo2;
        
        @SerializedName("respiratory_rate")
        private String respiratoryRate;

        @SerializedName("status")
        private String status;

        public String getName() { return name; }
        public int getPatientId() { return patientId; }
        public String getRoom() { return room; }
        public String getSpo2() { return spo2; }
        public String getRespiratoryRate() { return respiratoryRate; }
        public String getStatus() { return status; }
    }
}

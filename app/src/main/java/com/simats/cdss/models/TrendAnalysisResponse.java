package com.simats.cdss.models;

import com.google.gson.annotations.SerializedName;
import java.util.List;

public class TrendAnalysisResponse {

    @SerializedName("trend_status")
    private String trendStatus;

    @SerializedName("trend_indicators")
    private List<TrendIndicator> trendIndicators;

    public String getTrendStatus() { return trendStatus; }
    public List<TrendIndicator> getTrendIndicators() { return trendIndicators; }

    public static class TrendIndicator {
        @SerializedName("factor")
        private String factor;

        @SerializedName("description")
        private String description;

        @SerializedName("status")
        private String status;

        public String getFactor() { return factor; }
        public String getDescription() { return description; }
        public String getStatus() { return status; }
    }
}

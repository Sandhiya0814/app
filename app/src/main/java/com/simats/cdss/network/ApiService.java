package com.simats.cdss.network;

import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

import com.simats.cdss.models.*;

public interface ApiService {

    // ─────────────────────────────────────────
    // 1. Authentication (Admin / Doctor / Staff)
    // ─────────────────────────────────────────

    // Admin Login
    @POST("api/admin/login/")
    Call<LoginResponse> adminLogin(@Body Map<String, String> request);

    // Doctor Signup
    @POST("api/doctor/signup/")
    Call<GenericResponse> doctorSignup(@Body SignupRequest request);

    // Doctor Login
    @POST("api/doctor/login/")
    Call<LoginResponse> doctorLogin(@Body Map<String, String> request);

    // Staff Signup
    @POST("api/staff/signup/")
    Call<GenericResponse> staffSignup(@Body SignupRequest request);

    // Staff Login
    @POST("api/staff/login/")
    Call<LoginResponse> staffLogin(@Body Map<String, String> request);


    // ─────────────────────────────────────────
    // 2. Patient Management
    // ─────────────────────────────────────────

    @GET("api/patients/")
    Call<List<PatientResponse>> getPatients();

    @POST("api/patients/add/")
    Call<PatientResponse> addPatient(@Body PatientRequest request);


    // ─────────────────────────────────────────
    // 3. Clinical Data
    // ─────────────────────────────────────────

    @POST("api/patients/{id}/abg-entry/")
    Call<GenericResponse> addABGData(
            @Path("id") int patientId,
            @Body ABGRequest request
    );

    @POST("api/patients/{id}/vitals/")
    Call<GenericResponse> addVitalsData(
            @Path("id") int patientId,
            @Body VitalsRequest request
    );


    // ─────────────────────────────────────────
    // 4. Alerts & Recommendations
    // ─────────────────────────────────────────

    @GET("api/notifications/")
    Call<List<AlertResponse>> getAlerts();

    @GET("api/patients/{id}/therapy-recommendation/")
    Call<List<RecommendationResponse>> getPatientRecommendations(
            @Path("id") int patientId
    );

    @POST("api/patients/{id}/review-recommendation/")
    Call<GenericResponse> handleRecommendation(
            @Path("id") int patientId,
            @Body HandleRecommendationRequest request
    );


    // ─────────────────────────────────────────
    // 5. Admin Profile
    // ─────────────────────────────────────────

    @GET("api/admin/profile/")
    Call<AdminProfileResponse> getAdminProfile();


    // ─────────────────────────────────────────
    // 6. Admin Approval Requests
    // ─────────────────────────────────────────

    @GET("api/admin/approval-requests/")
    Call<List<ApprovalRequest>> getApprovalRequests();

    @POST("api/admin/approve-user/")
    Call<GenericResponse> approveUser(@Body Map<String, Object> request);

    @POST("api/admin/reject-user/")
    Call<GenericResponse> rejectUser(@Body Map<String, Object> request);


    // ─────────────────────────────────────────
    // 7. Admin Manage Doctors
    // ─────────────────────────────────────────

    @GET("api/admin/doctors/")
    Call<List<Doctor>> getDoctors();

    @GET("api/admin/doctors/{id}/")
    Call<DoctorDetailResponse> getDoctorDetails(@Path("id") int doctorId);

    @POST("api/admin/doctors/toggle-status/")
    Call<GenericResponse> toggleDoctorStatus(@Body Map<String, Object> request);

    @DELETE("api/admin/doctors/{id}/")
    Call<GenericResponse> deleteDoctor(@Path("id") int doctorId);


    // ─────────────────────────────────────────
    // 8. Admin Manage Staff
    // ─────────────────────────────────────────

    @GET("api/admin/staff/")
    Call<List<Staff>> getStaff();

    @GET("api/admin/staff/{id}/")
    Call<StaffDetailResponse> getStaffDetails(@Path("id") int staffId);

    @POST("api/admin/staff/toggle-status/")
    Call<GenericResponse> toggleStaffStatus(@Body Map<String, Object> request);

    @DELETE("api/admin/staff/{id}/")
    Call<GenericResponse> deleteStaff(@Path("id") int staffId);


    // ─────────────────────────────────────────
    // 9. Admin Dashboard Metrics
    // ─────────────────────────────────────────

    @GET("api/system-statistics/")
    Call<DashboardStatsResponse> getAdminDashboardStats();

}
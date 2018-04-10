package com.apoorv.dubey.android.model;

import com.google.firebase.database.IgnoreExtraProperties;

/**
 * Created by himanshujain on 09/04/18.
 */

@IgnoreExtraProperties
public class SaveData {

    String date;
    String userName;
    String stand;
    String floor;
    String work_category;
    String chairNumber;
    String sub_workCategory;
    String pavallion;
    String houseKeepingPercentage;
    String issueDescription;
    String completionStatus;
    String photoUri;



    public SaveData( String date,String userName, String stand, String floor, String work_category, String sub_workCategory, String pavallion, String chairNumber, String houseKeepingPercentage, String issueDescription,String completionStatus,String photoUri) {
        this.date=date;
        this.userName = userName;
        this.stand = stand;
        this.floor = floor;
        this.work_category = work_category;
        this.sub_workCategory = sub_workCategory;
        this.pavallion = pavallion;
        this.chairNumber = chairNumber;
        this.photoUri=photoUri;
        this.houseKeepingPercentage = houseKeepingPercentage;
        this.issueDescription = issueDescription;
        this.completionStatus=completionStatus;
    }

    public SaveData() {

    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getStand() {
        return stand;
    }

    public void setStand(String stand) {
        this.stand = stand;
    }

    public String getFloor() {
        return floor;
    }

    public void setFloor(String floor) {
        this.floor = floor;
    }

    public String getWork_category() {
        return work_category;
    }

    public void setWork_category(String work_category) {
        this.work_category = work_category;
    }

    public String getSub_workCategory() {
        return sub_workCategory;
    }

    public void setSub_workCategory(String sub_workCategory) {
        this.sub_workCategory = sub_workCategory;
    }

    public String getPhotoUri() {
        return photoUri;
    }

    public void setPhotoUri(String photoUri) {
        this.photoUri = photoUri;
    }

    public String getPavallion() {
        return pavallion;
    }

    public void setPavallion(String pavallion) {
        this.pavallion = pavallion;
    }

    public String getChairNumber() {
        return chairNumber;
    }

    public void setChairNumber(String chairNumber) {
        this.chairNumber = chairNumber;
    }


    public String getHouseKeepingPercentage() {
        return houseKeepingPercentage;
    }

    public void setHouseKeepingPercentage(String houseKeepingPercentage) {
        this.houseKeepingPercentage = houseKeepingPercentage;
    }

    public String getIssueDescription() {
        return issueDescription;
    }

    public void setIssueDescription(String issueDescription) {
        this.issueDescription = issueDescription;
    }

    public String getCompletionStatus() {
        return completionStatus;
    }

    public void setCompletionStatus(String completionStatus) {
        this.completionStatus = completionStatus;
    }
}

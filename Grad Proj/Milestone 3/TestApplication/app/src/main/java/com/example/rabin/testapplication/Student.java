package com.example.rabin.testapplication;

import android.os.Parcel;
import android.os.Parcelable;
import android.widget.EditText;

import java.io.Serializable;

/**
 * Created by rabin on 8/11/16.
 */
public class Student implements Serializable{

    private String strStudentFirstname;
    private String strStudentLastname;
    private String strStudentEthinicity;
    private String strStudentId;
    private String strStudentDOB;
    private String strStudentPrimaryLang;
    private String strStudentGradeLevel;
    private String strStudentConsentDate;
    private String strStudentPrimaryDisability;
    private String strStudentPlan504;
    private String strStudentLangProficiency;


    //constructors
    public Student ()
    {
        strStudentFirstname = "";
        strStudentLastname = "";
        strStudentEthinicity = "";
        strStudentId = "";
        strStudentDOB = "";
        strStudentPrimaryLang = "";
        strStudentGradeLevel = "";
        strStudentConsentDate = "";
        strStudentPrimaryDisability = "";
        strStudentPlan504 = "";
        strStudentLangProficiency = "";

    }


    public String getStrStudentFirstname() {
        return strStudentFirstname;
    }

    public void setStrStudentFirstname(String strStudentFirstname) {
        this.strStudentFirstname = strStudentFirstname;
    }

    public String getStrStudentLastname() {
        return strStudentLastname;
    }

    public void setStrStudentLastname(String strStudentLastname) {
        this.strStudentLastname = strStudentLastname;
    }

    public String getStrStudentEthinicity() {
        return strStudentEthinicity;
    }

    public void setStrStudentEthinicity(String strStudentEthinicity) {
        this.strStudentEthinicity = strStudentEthinicity;
    }

    public String getStrStudentId() {
        return strStudentId;
    }

    public void setStrStudentId(String strStudentId) {
        this.strStudentId = strStudentId;
    }

    public String getStrStudentDOB() {
        return strStudentDOB;
    }

    public void setStrStudentDOB(String strStudentDOB) {
        this.strStudentDOB = strStudentDOB;
    }

    public String getStrStudentPrimaryLang() {
        return strStudentPrimaryLang;
    }

    public void setStrStudentPrimaryLang(String strStudentPrimaryLang) {
        this.strStudentPrimaryLang = strStudentPrimaryLang;
    }

    public String getStrStudentGradeLevel() {
        return strStudentGradeLevel;
    }

    public void setStrStudentGradeLevel(String strStudentGradeLevel) {
        this.strStudentGradeLevel = strStudentGradeLevel;
    }

    public String getStrStudentConsentDate() {
        return strStudentConsentDate;
    }

    public void setStrStudentConsentDate(String strStudentConsentDate) {
        this.strStudentConsentDate = strStudentConsentDate;
    }

    public String getStrStudentPrimaryDisability() {
        return strStudentPrimaryDisability;
    }

    public void setStrStudentPrimaryDisability(String strStudentPrimaryDisability) {
        this.strStudentPrimaryDisability = strStudentPrimaryDisability;
    }

    public String getStrStudentPlan504() {
        return strStudentPlan504;
    }

    public void setStrStudentPlan504(String strStudentPlan504) {
        this.strStudentPlan504 = strStudentPlan504;
    }

    public String getStrStudentLangProficiency(){ return strStudentLangProficiency; }

    public void setStrStudentLangProficiency(String strStudentLangProficiency) {
        this.strStudentLangProficiency = strStudentLangProficiency;
    }

    public String toString()
    {
        return
                strStudentId+"\n"+
                strStudentFirstname+" \n"+
               strStudentLastname+ " \n"+
               strStudentDOB+" \n"+
               strStudentConsentDate+" \n"+
               strStudentEthinicity+" \n"+
               strStudentGradeLevel+"\n"+
               strStudentPrimaryDisability+"\n"+
               strStudentPrimaryLang+"\n"+
                strStudentPlan504+"\n"+
                strStudentLangProficiency+"\n";

    }

}

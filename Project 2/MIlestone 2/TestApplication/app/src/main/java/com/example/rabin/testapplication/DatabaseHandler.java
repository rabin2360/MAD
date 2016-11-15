package com.example.rabin.testapplication;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.hardware.camera2.params.StreamConfigurationMap;
import android.util.Log;

/**
 * Created by rabin on 8/11/16.
 */
public class DatabaseHandler extends SQLiteOpenHelper {

    //database variables
    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "colaDB";

    //student table in the DB
    private static final String TABLE_STUDENT_INFO = "studentInfoTable";
    private static final String TABLE_OBSERVATION_INFO = "observationInfoTable";

    //columns in the database table
    private static final String STUDENT_ID = "student_id";
    private static final String STUDENT_FIRSTNAME = "student_firstname";
    private static final String STUDENT_LASTNAME = "student_lastname";
    private static final String STUDENT_DOB = "student_dob";
    private static final String STUDENT_PRIMARY_LANG = "student_primary_lang";
    private static final String STUDENT_ETHNICITY = "student_ethnicity";
    private static final String STUDENT_GRADE_LEVEL = "student_grade_level";
    private static final String STUDENT_CONSENT_DATE = "student_consent_date";
    private static final String STUDENT_PRIMARY_DISABILITY = "student_primary_disability";
    private static final String STUDENT_504_PLAN = "student_504_plan";

    //columns in the observation table
    private static final String OBSERVATION_ID = "observation_id";
    private static final String OBSERVATION_ON_TASK = "observation_on_task";
    private static final String OBSERVATION_OFF_TASK = "observation_off_task";
    private static final String OBSERVATION_STUDENT_ID = "observation_student_id";

    public DatabaseHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        //creating the student table
        String CREATE_STUDENT_TABLE = "CREATE TABLE "+ TABLE_STUDENT_INFO + " ("
                +STUDENT_ID+" TEXT PRIMARY KEY,"
                +STUDENT_FIRSTNAME+" TEXT,"
                +STUDENT_LASTNAME+" TEXT,"
                +STUDENT_DOB+" TEXT,"
                +STUDENT_PRIMARY_LANG+" TEXT,"
                +STUDENT_ETHNICITY+ " TEXT,"
                +STUDENT_GRADE_LEVEL+" TEXT,"
                +STUDENT_CONSENT_DATE+" TEXT,"
                +STUDENT_PRIMARY_DISABILITY+" TEXT,"
                +STUDENT_504_PLAN+ " TEXT" +")";

        sqLiteDatabase.execSQL(CREATE_STUDENT_TABLE);

        String CREATE_OBSERVATION_TABLE = "CREATE TABLE "+ TABLE_OBSERVATION_INFO + " ("
                +OBSERVATION_ID+" INTEGER PRIMARY KEY,"
                +OBSERVATION_ON_TASK+" INTEGER,"
                +OBSERVATION_OFF_TASK+" INTEGER,"
                +OBSERVATION_STUDENT_ID+" TEXT" +")";

        sqLiteDatabase.execSQL(CREATE_OBSERVATION_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int olderVersion, int newVersion) {

    }

    //all CRUD operation - for observation table
    void addObservation(Observation observationInfo)
    {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues observationInfoValues = new ContentValues();
        observationInfoValues.put(OBSERVATION_ID, observationInfo.getObservationId());
        observationInfoValues.put(OBSERVATION_ON_TASK, observationInfo.getObservationOnTask());
        observationInfoValues.put(OBSERVATION_OFF_TASK, observationInfo.getObservationOffTask());
        observationInfoValues.put(OBSERVATION_STUDENT_ID, observationInfo.getObservationStudentId());

        //Inserting rows
        db.insert(TABLE_OBSERVATION_INFO,null,observationInfoValues);

        db.close();
    }


    //all CRUD operations - for student info

    //C -create
    void addStudent(Student studentInfo)
    {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues studentInfoValues = new ContentValues();
        studentInfoValues.put(STUDENT_ID,studentInfo.getStrStudentId());
        studentInfoValues.put(STUDENT_FIRSTNAME, studentInfo.getStrStudentFirstname());
        studentInfoValues.put(STUDENT_LASTNAME, studentInfo.getStrStudentLastname());
        studentInfoValues.put(STUDENT_DOB, studentInfo.getStrStudentDOB());
        studentInfoValues.put(STUDENT_PRIMARY_LANG, studentInfo.getStrStudentPrimaryLang());
        studentInfoValues.put(STUDENT_ETHNICITY, studentInfo.getStrStudentEthinicity());
        studentInfoValues.put(STUDENT_GRADE_LEVEL, studentInfo.getStrStudentGradeLevel());
        studentInfoValues.put(STUDENT_CONSENT_DATE, studentInfo.getStrStudentConsentDate());
        studentInfoValues.put(STUDENT_PRIMARY_DISABILITY, studentInfo.getStrStudentPrimaryDisability());
        studentInfoValues.put(STUDENT_504_PLAN, studentInfo.getStrStudentPlan504());

        //Inserting rows
        db.insert(TABLE_STUDENT_INFO,null,studentInfoValues);

        db.close();
    }

    //R - read
    Student getStudentInfo(String id)
    {
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor =  db.query(TABLE_STUDENT_INFO, new String []{STUDENT_ID, STUDENT_FIRSTNAME, STUDENT_LASTNAME, STUDENT_DOB, STUDENT_PRIMARY_LANG, STUDENT_ETHNICITY, STUDENT_GRADE_LEVEL, STUDENT_CONSENT_DATE, STUDENT_PRIMARY_DISABILITY, STUDENT_504_PLAN}, STUDENT_ID + "=?", new String [] {id},null,null,null,null);

        System.out.println("CURSOR: "+cursor.getCount());
        if(cursor !=null)
            cursor.moveToFirst();

        Student studentInfo = new Student();
        if(cursor !=null && cursor.getCount()> 0) {
            studentInfo.setStrStudentId(cursor.getString(0));
            studentInfo.setStrStudentFirstname(cursor.getString(1));
            studentInfo.setStrStudentLastname(cursor.getString(2));
            studentInfo.setStrStudentDOB(cursor.getString(3));
            studentInfo.setStrStudentPrimaryLang(cursor.getString(4));
            studentInfo.setStrStudentEthinicity(cursor.getString(5));
            studentInfo.setStrStudentGradeLevel(cursor.getString(6));
            studentInfo.setStrStudentConsentDate(cursor.getString(7));
            studentInfo.setStrStudentPrimaryDisability(cursor.getString(8));
            studentInfo.setStrStudentPlan504(cursor.getString(9));
        }
        else
        {
            studentInfo = null;
        }

        return studentInfo;

    }

    //U - update
    void updateStudentInfo(Student studentInfo)
    {
        int returnVal = -1;

        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues studentInfoValues = new ContentValues();
        studentInfoValues.put(STUDENT_FIRSTNAME, studentInfo.getStrStudentFirstname());
        studentInfoValues.put(STUDENT_LASTNAME, studentInfo.getStrStudentLastname());
        studentInfoValues.put(STUDENT_DOB, studentInfo.getStrStudentDOB());
        studentInfoValues.put(STUDENT_PRIMARY_LANG, studentInfo.getStrStudentPrimaryLang());
        studentInfoValues.put(STUDENT_ETHNICITY, studentInfo.getStrStudentEthinicity());
        studentInfoValues.put(STUDENT_GRADE_LEVEL, studentInfo.getStrStudentGradeLevel());
        studentInfoValues.put(STUDENT_CONSENT_DATE, studentInfo.getStrStudentConsentDate());
        studentInfoValues.put(STUDENT_PRIMARY_DISABILITY, studentInfo.getStrStudentPrimaryDisability());
        studentInfoValues.put(STUDENT_504_PLAN, studentInfo.getStrStudentPlan504());


        returnVal = db.update(TABLE_STUDENT_INFO,studentInfoValues,STUDENT_ID + " = ? ",new String [] {studentInfo.getStrStudentId()});

        Log.v("Updated Rows: ", Integer.toString(returnVal));

    }
}

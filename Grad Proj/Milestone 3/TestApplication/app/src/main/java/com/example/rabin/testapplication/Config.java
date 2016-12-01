package com.example.rabin.testapplication;

/**
 * Created by rabinranabhat on 11/22/16.
 */
public class Config {

    protected static final String FIREBASE_URL = "https://cola-73f9f.firebaseio.com/";

    //intent constants
    protected static final String I_STUDENT_ID = "studentId";
    protected static final String I_OBS_FREQ = "obsFreq";
    protected static final String I_OBS_TOTAL_TIME = "obsTotalTime";
    protected static final String I_OBSERVER_FIRSTNAME = "obFirstName";
    protected static final String I_OBSERVER_LASTNAME = "obLastName";
    protected static final String I_OBSERVER_TITLE = "obTitle";
    protected static final String I_TEACHER_FIRSTNAME = "teachFirstName";
    protected static final String I_TEACHER_LASTNAME = "teachLastName";
    protected static final String I_CURRENT_CLASS = "teachCurrentClass";
    protected static final String I_STUDENT_DATA = "obsStdData";
    protected static final String I_PEER_DATA = "obsPrData";

    //hashmap keys
    protected static final String CAT_OBSERVING = "observing";
    protected static final String CAT_PARTICIPATING = "participating";
    protected static final String CAT_DISENGAGED = "disengaged";
    protected static final String CAT_VERBAL = "verbal";
    protected static final String CAT_MOTOR = "motor";
    protected static final String CAT_AGGRESSIVE = "aggressive";
    protected static final String CAT_OUT_OF_SEAT = "outofseat";

    //strings mostly used in summary screen class
    protected static final String STR_FIRSTNAME = "Firstname: ";
    protected static final String STR_LASTNAME = "Lastname: ";
    protected static final String STR_CLASS = "Class: ";
    protected static final String STR_TITLE = "Title: ";

    //node names in the db
    protected static final String STUDENT_INFO_TABLE = "student_info";
    protected static final String STUDENT_OBS_TABLE = "student_observation";
    protected static final String TEACHER_INFO_TABLE = "teacher_info";
    protected static final String OBSERVER_INFO_TABLE = "observer_info";
    protected static final String STUDENT_DATA = "student_data";
    protected static final String PEER_DATA = "peer_data";

    //for empty string that go to DB
    protected  static  final String NO_DATA = "no_data";

    //observation type
    protected static final  String I_OBSERVATION_TYPE = "observationType";
    protected static final String OBS_MANUAL = "manual";
    protected static final String OBS_AUTOMATIC = "automatic";
}

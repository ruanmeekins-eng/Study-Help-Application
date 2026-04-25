package com.example.studyhelpapp;
/**
 *
 * This class stores information about the currently logged-in user
 * and the active study set. It allows different controllers to share
 * data without directly passing objects between scenes.
 *
 */
public class Session {
    public static User currentUser; //User that is currently logged-in
    public static StudySet currentStudySet; //Current study set being used

}

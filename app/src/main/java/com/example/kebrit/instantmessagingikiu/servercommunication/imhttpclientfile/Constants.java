/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.kebrit.instantmessagingikiu.servercommunication.imhttpclientfile;

import com.firebase.client.Firebase;

/**
 *
 * @author reza
 */
public class Constants {

    public static  boolean mechanisme = true;

//    public static String URL_SEND = "http://localhost/IMHttpServer/sendMsgTo.php";
//    public static String URL_GET = "http://localhost/IMHttpServer/getMsgFrom.php";
    
    //on net
    public static String URL_SEND = "http://instantmess.asan-web.ir/imfile/sendMsgTo.php";
    public static String URL_GET = "http://instantmess.asan-web.ir/imfile/messages.xml";

//    public static String URL_SEND = "http://nano-pacific.com.au/forum/new/new/Test/imfile/sendMsgTo.php";
//    public static String URL_GET = "http://nano-pacific.com.au/forum/new/new/Test/imfile/messages.xml";
    //on net
    public static  String URL_PHP_READ_FILE = "http://instantmess.asan-web.ir/imfile/getMsgFrom.php";


//    public static String URL_FIREBASE = "https://instantmessaging.firebaseio.com/";  //reza
    public static String URL_FIREBASE = "https://instantmessagingikiu.firebaseio.com/";   //keoush
    public static String KEY_SHAREDPEREFERENCES = "imPrefs";
    public static String KEY_FIRST_TIME = "isItFirstTime";

    public static String KEY_USER_ID = "userId";
    public static String KEY_USERNAME = "username";

    public static Firebase myFirebase;
}

package com.mat.androidbarberbooking.Common;

import android.content.Context;
import android.text.TextUtils;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.mat.androidbarberbooking.Model.Barber;
import com.mat.androidbarberbooking.Model.Salon;
import com.mat.androidbarberbooking.Model.User;

public class Common {
    public static final String KEY_ENABLE_BUTTON_NEXT = "ENABLE_BUTTON_NEXT";
    public static final String KEY_SALON_STORE = "SALON_SAVE";
    public static final String KEY_BARBER_LOAD_DONE = "KEY_BARBER_LOAD_DONE";
    public static final String KEY_DISPLAY_TIME_SLOT = "DISPLAY_TIME_SLOT";
    public static final String KEY_STEP = "STEP";
    public static final String KEY_BARBER_SELECTED = "BARBER_SELECTED";
    public static String IS_LOGIN = "IsLogin";
    public static User currentUser;
    public static Salon currentSalon;
    public static int step = 0;  // init first step is 0
    public static String city = "";
    public static Barber currentBarber;

    public static enum TOKEN_TYPE{
        CLIENT,
        BARBER,
        MANAGER
    }

    public static void updateToken (Context context, String s){
///11min 48s
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null)
        {
//            MyToken myToken = new MyToken();
//            myToken.setToken(s);
//            myToken.setTokenType(TOKEN_TYPE.CLIENT);  ///because token come from client app
//            myToken.setUserPhone(user.getPhoneNumber());

//            FirebaseFirestore.getInstance()
//                    .collection("Tokens")
//                    .document(user.getPhoneNumber())
//                    .set(myToken)
//                    .addOnCompleteListener(task -> {
//
//                    });
        }
        else
        {
//            Paper.init(context);
//            String localUser = Paper.book().read(Common.LOGGED_KEY);
//            if (localUser != null)
//            {
//                if (!TextUtils.isEmpty(localUser))
//                {
//                    MyToken myToken = new MyToken();
//                    myToken.setToken(s);
//                    myToken.setTokenType(TOKEN_TYPE.CLIENT); ///because token come from client app
//                    myToken.setUserPhone(localUser);
//
//                    FirebaseFirestore.getInstance()
//                            .collection("Tokens")
//                            .document(localUser)
//                            .set(myToken)
//                            .addOnCompleteListener(task -> {
//
//                            });
//                }
//            }

        }

    }
}

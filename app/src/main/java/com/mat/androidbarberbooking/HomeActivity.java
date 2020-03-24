package com.mat.androidbarberbooking;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.app.AlertDialog;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.mat.androidbarberbooking.Common.Common;
import com.mat.androidbarberbooking.Fragments.HomeFragment;
import com.mat.androidbarberbooking.Fragments.ShoppingFragment;
import com.mat.androidbarberbooking.Model.User;

import butterknife.BindView;
import butterknife.ButterKnife;
import dmax.dialog.SpotsDialog;

public class HomeActivity extends AppCompatActivity {

    @BindView(R.id.bottom_navigation)
    BottomNavigationView bottomNavigationView;

    BottomSheetDialog bottomSheetDialog;

    CollectionReference userRef;

    AlertDialog dialog;


    @Override
    public void onSaveInstanceState(@NonNull Bundle outState, @NonNull PersistableBundle outPersistentState) {

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        ButterKnife.bind(HomeActivity.this);

        ///init
        userRef = FirebaseFirestore.getInstance().collection("User");
        dialog = new SpotsDialog.Builder().setContext(this).setCancelable(false).build();

        ///check intent, if is login = true, enable full access
        /// If is login = false, just let user around shopping to view
        if (getIntent() != null)
        {

            boolean isLogin = getIntent().getBooleanExtra(Common.IS_LOGIN,false);
            if (isLogin) {

                dialog.show();

                ///check if user exists
                FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();


                ///save userPhone by Paper
//                Paper.init(HomeActivity.this);
//                Paper.book().write(Common.LOGGED_KEY,user.getPhoneNumber());

                            DocumentReference currentUser = userRef.document(user.getPhoneNumber());
                            currentUser.get()
                                    .addOnCompleteListener(task -> {
                                        if (task.isSuccessful())
                                        {
                                            DocumentSnapshot userSnapShot = task.getResult();
                                            if (!userSnapShot.exists()){

                                                showUpdatedialog(user.getPhoneNumber());
                                            }
                                            else
                                            {
                                                ///if user already available in our system
                                                Common.currentUser = userSnapShot.toObject(User.class);
                                                bottomNavigationView.setSelectedItemId(R.id.action_home);
                                            }
                                            if (dialog.isShowing())
                                                dialog.dismiss();
                                        }
                                    });
                        }
                    }

        ///View
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            Fragment fragment = null;
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {  //// up down
                if (menuItem.getItemId() == R.id.action_shopping)
                    fragment = new ShoppingFragment();
                else if (menuItem.getItemId() == R.id.action_home);
                    fragment = new HomeFragment();

                return loadFragment(fragment);
            }
        });


    }

    private boolean loadFragment(Fragment fragment) {
        if (fragment != null)
        {
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,fragment)
                    .commit();
            return true;
        }
        return false;
    }

    private void showUpdatedialog(String phoneNumber) {



        ///init dialog
        bottomSheetDialog = new BottomSheetDialog(this);
        bottomSheetDialog.setTitle("One more step!");
        bottomSheetDialog.setCanceledOnTouchOutside(false);
        bottomSheetDialog.setCancelable(false);
        View sheetView = getLayoutInflater().inflate(R.layout.layout_update_information, null);

        Button btn_update = (Button)sheetView.findViewById(R.id.btn_update);
        TextInputEditText edit_name = (TextInputEditText) sheetView.findViewById(R.id.edit_name);
        TextInputEditText edit_address = (TextInputEditText) sheetView.findViewById(R.id.edit_address);

        btn_update.setOnClickListener(view -> {

            if (!dialog.isShowing())
                dialog.show();

            User user = new User(edit_name.getText().toString(),
                    edit_address.getText().toString(),
                    phoneNumber);
            userRef.document(phoneNumber)
                    .set(user)
                    .addOnSuccessListener(aVoid -> {
                        bottomSheetDialog.dismiss();
                        if (dialog.isShowing())
                            dialog.dismiss();

                        Common.currentUser = user;
                        bottomNavigationView.setSelectedItemId(R.id.action_home);

                        Toast.makeText(HomeActivity.this, "Thank You", Toast.LENGTH_SHORT).show();
                    }).addOnFailureListener(e -> {
                        bottomSheetDialog.dismiss();
                        if (dialog.isShowing())
                            dialog.dismiss();
                        Toast.makeText(HomeActivity.this, ""+e.getMessage(), Toast.LENGTH_SHORT).show();
                    });
        });

        bottomSheetDialog.setContentView(sheetView);
        bottomSheetDialog.show();

    }
}

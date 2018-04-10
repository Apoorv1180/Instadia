package com.apoorv.dubey.android.instadia;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.Preferences.PreferenceWorkArea;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseException;
import com.google.firebase.FirebaseTooManyRequestsException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;

public class LoginActivity extends AppCompatActivity {

    private static final String TAG = "INSTADIA";
    private boolean mVerificationInProgress = false;
    private String mVerificationId;
     private PhoneAuthProvider.ForceResendingToken mResendToken;
    private PhoneAuthProvider.OnVerificationStateChangedCallbacks mCallbacks;
    private FirebaseAuth mAuth;
    EditText e1,e2,name,email,organisation;
    LinearLayout linearLayout;
    Button sendOTP, verifyOTP,resendOTP,saveUserInfo;
    FrameLayout registrationFrameBtn;
    PreferenceWorkArea preferenceWorkArea;
    private ProgressBar mProgressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        e1 = (EditText) findViewById(R.id.phone_number_editText);
        sendOTP = (Button) findViewById(R.id.send_otp_btn);
        e2 = (EditText) findViewById(R.id.otp_editText);
        verifyOTP = (Button)findViewById(R.id.verify_otp_btn);
        registrationFrameBtn=findViewById(R.id.registrationFrame);
        name=findViewById(R.id.name_editText);
        email=findViewById(R.id.email_editText);
        organisation=findViewById(R.id.organisation_editText);
        resendOTP=findViewById(R.id.resend_otp_btn);
        saveUserInfo=findViewById(R.id.save_info_btn);
        linearLayout=findViewById(R.id.login_linear_layout);
        mProgressBar = findViewById(R.id.progressBar);

        mAuth = FirebaseAuth.getInstance();
        mAuth = FirebaseAuth.getInstance();


        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null) {

            startActivity(new Intent(LoginActivity.this,ProfileActivity.class));

        }
        else {
            preferenceWorkArea = new PreferenceWorkArea(LoginActivity.this);

            mCallbacks = new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {

                @Override
                public void onVerificationCompleted(PhoneAuthCredential credential) {
                    // Log.d(TAG, "onVerificationCompleted:" + credential);
                    mVerificationInProgress = false;
                    Toast.makeText(LoginActivity.this, "Verification Complete", Toast.LENGTH_SHORT).show();
                    signInWithPhoneAuthCredential(credential);
                    mProgressBar.setVisibility(View.GONE);

                }

                @Override
                public void onVerificationFailed(FirebaseException e) {
                    // Log.w(TAG, "onVerificationFailed", e);
                    Toast.makeText(LoginActivity.this, "Verification Failed", Toast.LENGTH_SHORT).show();
                    if (e instanceof FirebaseAuthInvalidCredentialsException) {
                        // Invalid request
                        Toast.makeText(LoginActivity.this, "InValid Phone Number", Toast.LENGTH_SHORT).show();
                        // ...
                    } else if (e instanceof FirebaseTooManyRequestsException) {
                    }

                    mProgressBar.setVisibility(View.GONE);


                }

                @Override
                public void onCodeSent(String verificationId,
                                       PhoneAuthProvider.ForceResendingToken token) {
                    // Log.d(TAG, "onCodeSent:" + verificationId);
                    Toast.makeText(LoginActivity.this, "Verification code has been send on your number", Toast.LENGTH_SHORT).show();
                    // Save verification ID and resending token so we can use them later
                    mVerificationId = verificationId;
                    mResendToken = token;
                    sendOTP.setVisibility(View.GONE);
                    e2.setVisibility(View.VISIBLE);
                    verifyOTP.setVisibility(View.VISIBLE);
                    mProgressBar.setVisibility(View.GONE);

                    // ...
                }
            };
            sendOTP.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mProgressBar.setVisibility(View.VISIBLE);

                    PhoneAuthProvider.getInstance().verifyPhoneNumber(
                            "+91" + e1.getText().toString(),
                            60,
                            java.util.concurrent.TimeUnit.SECONDS,
                            LoginActivity.this,
                            mCallbacks);
                }
            });

            verifyOTP.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mProgressBar.setVisibility(View.VISIBLE);

                    PhoneAuthCredential credential = PhoneAuthProvider.getCredential(mVerificationId, e2.getText().toString());
                    // [END verify_with_code]
                    signInWithPhoneAuthCredential(credential);
                }
            });
            resendOTP.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mProgressBar.setVisibility(View.VISIBLE);

                    PhoneAuthProvider.getInstance().verifyPhoneNumber(
                            "+91" + e1.getText().toString(),
                            60,
                            java.util.concurrent.TimeUnit.SECONDS,
                            LoginActivity.this,
                            mCallbacks);
                }
            });

        }

    }

    private void signInWithPhoneAuthCredential(PhoneAuthCredential credential) {
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Log.d(TAG, "signInWithCredential:success");
                            final FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                            final String currentUserId= mAuth.getCurrentUser().getUid();
                            if (user.getDisplayName() == null) {
                                // User is signed in
                                registrationFrameBtn.setVisibility(View.VISIBLE);
                                linearLayout.setVisibility(View.GONE);

                                saveUserInfo.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        if(!TextUtils.isEmpty(name.getText()) && !TextUtils.isEmpty(email.getText()) && !TextUtils.isEmpty(organisation.getText())){

                                            DatabaseReference current_user_db = FirebaseDatabase.getInstance().getReference().child("Users").child(currentUserId);

                                            String nameValue = name.getText().toString();
                                            String emailValue = email.getText().toString();
                                            String organisationValue = organisation.getText().toString();

                                            if(ValidationUtils.isValidName(nameValue) && ValidationUtils.isValidEmail(emailValue) && ValidationUtils.isValidName(organisationValue)) {
                                                user.updateProfile(new UserProfileChangeRequest.Builder().setDisplayName(nameValue).build());
                                                user.updateEmail(emailValue);

                                                Map newPost = new HashMap();
                                                newPost.put("name", nameValue);
                                                newPost.put("email", emailValue);
                                                newPost.put("organisation", organisationValue);

                                                current_user_db.setValue(newPost);
                                                preferenceWorkArea.writePreferencesuserName(nameValue);
                                                e1.setText(null);
                                                e2.setText(null);
                                                name.setText(null);
                                                email.setText(null);
                                                organisation.setText(null);
                                                startActivity(new Intent(LoginActivity.this, ProfileActivity.class));
                                            }
                                            else{
                                                Toast.makeText(LoginActivity.this,"Please Enter All values Correctly",Toast.LENGTH_SHORT).show();

                                            }

                                        }else {
                                            Toast.makeText(LoginActivity.this,"Please Enter All the values",Toast.LENGTH_SHORT).show();

                                        }
                                    }
                                });
                                Toast.makeText(LoginActivity.this,"Please Register YourSelf",Toast.LENGTH_SHORT).show();

                            } else {
                                // No user information accepted till now
                                registrationFrameBtn.setVisibility(View.INVISIBLE);
                                linearLayout.setVisibility(View.VISIBLE);

                                startActivity(new Intent(LoginActivity.this,ProfileActivity.class));
                                Toast.makeText(LoginActivity.this,"Verification Done",Toast.LENGTH_SHORT).show();
                            }

                            // ...
                        } else {
                            // Log.w(TAG, "signInWithCredential:failure", task.getException());
                            if (task.getException() instanceof FirebaseAuthInvalidCredentialsException) {
                                // The verification code entered was invalid
                                registrationFrameBtn.setVisibility(View.INVISIBLE);
                                Toast.makeText(LoginActivity.this,"Invalid Verification",Toast.LENGTH_SHORT).show();
                            }
                        }
                    }
                });
    }


}




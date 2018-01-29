package com.gap22.community.apartment;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.gap22.community.apartment.Common.CommonFunctions;
import com.gap22.community.apartment.Common.DeviceInfo;
import com.gap22.community.apartment.Common.FontsOverride;
import com.gap22.community.apartment.Common.GlobalValues;
import com.gap22.community.apartment.Dao.GlobalUserDao;
import com.gap22.community.apartment.Entities.GlobalUser;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.IOException;
import java.util.Date;

import de.hdodenhof.circleimageview.CircleImageView;

import static com.gap22.community.apartment.Common.GlobalValues.PERMISSION_REQUEST_CODE;

public class SignUp extends AppCompatActivity {

    private static final int FILE_SELECT_CODE = 0;
    private Button btn_sign_up, btn_cancel;
    private CircleImageView img_user_image;
    private Bitmap img_user_photo;
    private DatabaseReference mDatabase;
    private FirebaseAuth fireauth;
    private ProgressDialog progress;
    private EditText et_emailid, et_firstname, et_lastname, et_phone;
    private Spinner spin_title;
    Uri uri;
    FirebaseStorage storage = FirebaseStorage.getInstance();
    StorageReference storageRef = storage.getReferenceFromUrl("gs://communify-4b71c.appspot.com");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        progress = new ProgressDialog(this);
        FontsOverride.setDefaultFont(this, "MONOSPACE", "fonts/avenirltstd-book.ttf");
        setContentView(R.layout.activity_sign_up);

        spin_title = (Spinner) findViewById(R.id.spin_title);
        et_firstname = (EditText) findViewById(R.id.et_firstname);
        et_lastname = (EditText) findViewById(R.id.et_lastname);
        et_emailid = (EditText) findViewById(R.id.et_emailid);
        et_phone = (EditText) findViewById(R.id.et_phone);
        img_user_image = (CircleImageView) findViewById(R.id.img_user_image);

        fireauth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance().getReference("member");

        if (!DeviceInfo.CheckPermission(GlobalValues.WantPermission, this)) {
            DeviceInfo.RequestPermission(GlobalValues.WantPermission, this);
        } else {
            et_phone.setText(DeviceInfo.GetPhoneNumber(this, this));
        }

        populateTitles();
    }

    private void requestPermission(String permission) {
        if (ActivityCompat.shouldShowRequestPermissionRationale(SignUp.this, permission)) {
            Toast.makeText(SignUp.this, "Phone state permission allows us to get phone number. Please allow it for additional functionality.", Toast.LENGTH_LONG).show();
        }
        ActivityCompat.requestPermissions(SignUp.this, new String[]{permission}, PERMISSION_REQUEST_CODE);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        switch (requestCode) {
            case PERMISSION_REQUEST_CODE:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    et_phone.setText(DeviceInfo.GetPhoneNumber(this, this));
                } else {
                    Toast.makeText(SignUp.this, "Permission Denied. We can't get phone number.", Toast.LENGTH_LONG).show();
                }
                break;
        }
    }

    public void btn_sign_up_onClickBtn(View v) {
        Date createdDate = new Date();
        String emailAuthKey = CommonFunctions.GenerateRandomString(23);
        String mobileOTP = CommonFunctions.GenerateOTPNumber(6);

        String title = spin_title.getSelectedItem().toString();
        String firstname = et_firstname.getText().toString();
        String lastname = et_lastname.getText().toString();
        String emailid = et_emailid.getText().toString();
        String phone = et_phone.getText().toString();

        final GlobalUser globalUser = new GlobalUser("MR", firstname, lastname, emailid, "", phone, "N", mobileOTP, "N", emailAuthKey, createdDate, "");

        if (ValidateForm() == true) {

            progress.show();
            GlobalUserDao globalUserDao = new GlobalUserDao();
            globalUserDao.CreateUser(globalUser, phone);

            if (uri != null) {
                String filename = phone + ".jpg";
                StorageReference childRef = storageRef.child(filename);
                //uploading the image
                UploadTask uploadTask = childRef.putFile(uri);

                uploadTask.addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(SignUp.this, "Upload Failed -> " + e, Toast.LENGTH_SHORT).show();
                        return;
                    }
                });
            } /*else {
                Toast.makeText(SignUp.this, "Select an image", Toast.LENGTH_SHORT).show();
                return;
            }*/
            progress.dismiss();
            Intent menu = new Intent(SignUp.this, MainActivity.class);
            startActivity(menu);
            Toast.makeText(SignUp.this, "User Created Succesfully", Toast.LENGTH_SHORT).show();
            finish();
            overridePendingTransition(R.anim.slide_up_info, R.anim.slide_down_info);
            return;
        }

    }

    public void img_user_image_onClickBtn(View v) {
        openFileBrowser();
    }

    public void populateTitles() {
        Spinner spinner = (Spinner) findViewById(R.id.spin_title);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.act_signup_title_collection, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
    }

    private void openFileBrowser() {
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("*/*");
        intent.addCategory(Intent.CATEGORY_OPENABLE);

        try {
            startActivityForResult(Intent.createChooser(intent, "Select a Image"), FILE_SELECT_CODE);
        } catch (android.content.ActivityNotFoundException ex) {
            Toast.makeText(this, "Please install a File Manager.", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case FILE_SELECT_CODE:
                if (resultCode == RESULT_OK) {
                    uri = data.getData();
                    try {
                        Bitmap bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), uri);
                        img_user_image.setImageBitmap(bitmap);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                break;
        }
    }

    /*public String getRealPathFromURI(Uri contentUri) {
        String path = "";
        String[] proj = {MediaStore.Images.Media.DATA};
        Cursor cursor = managedQuery(contentUri, proj, null, null, null);
        int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
        cursor.moveToFirst();
        path = cursor.getString(column_index);
        cursor.close();
        return path;
    }*/

    public boolean ValidateForm() {
        if (uri == null) {
            Toast.makeText(SignUp.this, "Please Upload File", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (TextUtils.isEmpty(et_firstname.getText().toString())) {
            Toast.makeText(SignUp.this, "Please Enter FirstName", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (TextUtils.isEmpty(et_lastname.getText().toString())) {
            Toast.makeText(SignUp.this, "Please Enter LastName", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (TextUtils.isEmpty(et_emailid.getText())) {
            Toast.makeText(SignUp.this, "Please Enter Email", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (et_phone.getText().toString().length() <= 3) {
            Toast.makeText(SignUp.this, "Please Enter Phone", Toast.LENGTH_SHORT).show();
            return false;
        }


        return true;
    }
}

package com.gap22.community.apartment;

import android.app.ProgressDialog;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.gap22.community.apartment.Common.CommonFunctions;
import com.gap22.community.apartment.Common.FontsOverride;
import com.gap22.community.apartment.Dao.GlobalUserDao;
import com.gap22.community.apartment.Entities.GlobalUser;
import com.gap22.community.apartment.Entities.Members;
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

public class SignUp extends AppCompatActivity {

    private static final int FILE_SELECT_CODE = 0;
    private Button btn_sign_up, btn_cancel;
    private CircleImageView img_user_image;
    private Bitmap img_user_photo;
    private DatabaseReference mDatabase;
    private FirebaseAuth fireauth;
    private ProgressDialog progress;
    private EditText et_emailid, et_password, et_firstname, et_lastname, et_phone;
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
        et_password = (EditText) findViewById(R.id.et_password);
        et_phone = (EditText) findViewById(R.id.et_phone);
        img_user_image = (CircleImageView) findViewById(R.id.img_user_image);

        fireauth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance().getReference("member");

        populateTitles();
    }

    public void btn_sign_up_onClickBtn(View v) {
        Date createdDate = new Date();
        String emailAuthKey = CommonFunctions.GenerateRandomString(23);
        String mobileOTP = CommonFunctions.GenerateOTPNumber(6);

        String title = spin_title.getSelectedItem().toString();
        String firstname = et_firstname.getText().toString();
        String lastname = et_lastname.getText().toString();
        String emailid = et_emailid.getText().toString();
        String password = et_password.getText().toString();
        String phone = et_phone.getText().toString();

        final GlobalUser globalUser = new GlobalUser("MR", firstname, lastname, emailid, password, phone, "N", mobileOTP, "N", emailAuthKey, createdDate, "");

        if (ValidateForm() == true) {

            fireauth.createUserWithEmailAndPassword(et_emailid.getText().toString(), et_password.getText().toString()).addOnCompleteListener(SignUp.this, new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    progress.dismiss();
                    if (task.isSuccessful()) {
                        GlobalUserDao globalUserDao = new GlobalUserDao();
                        String userId = task.getResult().getUser().getUid();
                        globalUserDao.CreateUser(globalUser, userId);

                        /*if (uri != null) {
                            String filename = userId + ".jpg";
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
                        } else {
                            Toast.makeText(SignUp.this, "Select an image", Toast.LENGTH_SHORT).show();
                            return;
                        }*/

                        Intent menu = new Intent(SignUp.this, OptionalSignup.class);
                        startActivity(menu);
                        finish();
                        overridePendingTransition(R.anim.slide_up_info, R.anim.slide_down_info);
                        return;
                    }
                }
            });
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
        /*if (TextUtils.isEmpty(et_emailid.getText())) {
            Toast.makeText(SignUp.this, "Please Enter Email", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (TextUtils.isEmpty(et_password.getText().toString())) {
            Toast.makeText(SignUp.this, "Please Enter Password", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (TextUtils.isEmpty(et_password.getText().toString())) {
            Toast.makeText(SignUp.this, "Please Enter Title", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (TextUtils.isEmpty(m.getFirstname())) {
            Toast.makeText(SignUp.this, "Please Enter FirstName", Toast.LENGTH_SHORT).show();
            return false;
        }

        if (TextUtils.isEmpty(m.getLastname())) {
            Toast.makeText(SignUp.this, "Please Enter LastName", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (TextUtils.isEmpty(m.getUnit())) {
            Toast.makeText(SignUp.this, "Please Enter Unit", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (uri == null) {
            Toast.makeText(SignUp.this, "Please Upload File", Toast.LENGTH_SHORT).show();
            return false;
        }*/

        return true;
    }
}

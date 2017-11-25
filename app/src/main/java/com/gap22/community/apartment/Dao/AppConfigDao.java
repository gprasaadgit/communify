package com.gap22.community.apartment.Dao;

import com.gap22.community.apartment.Common.CommunifyCrypto;
import com.gap22.community.apartment.Common.GlobalValues;
import com.gap22.community.apartment.Entities.AppConfig;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import javax.crypto.SecretKey;

public class AppConfigDao {

    private DatabaseReference dbReference;
    private FirebaseAuth fbAuthentication;
    private KeyGenerator keyGen = new KeyGenerator();
    private AppConfig appConfig;
    private SecretKey secretKey;
    CommunifyCrypto communifyCrypto;

    public AppConfigDao() {
        dbReference = FirebaseDatabase.getInstance().getReference();
        communifyCrypto = new CommunifyCrypto();
    }

    public void SetDefaultAppConfig() throws CommunifyCrypto.CryptoException {
        communifyCrypto = new CommunifyCrypto();

        secretKey = communifyCrypto.getSecretKey(GlobalValues.getEncriptionPass(), GlobalValues.getEncriptionSalt());

        AppConfig appConfig = new AppConfig(communifyCrypto.encrypt(secretKey, "communify.alerts@gmail.com"), communifyCrypto.encrypt(secretKey, "Communify_123"));
        dbReference.child("APP-CONFIG").setValue(appConfig);
    }

    public AppConfig GetAppConfig() throws CommunifyCrypto.CryptoException {
        appConfig = new AppConfig();

        DatabaseReference ref = dbReference.child("/APP-CONFIG/");
        secretKey = communifyCrypto.getSecretKey(GlobalValues.getEncriptionPass(), GlobalValues.getEncriptionSalt());

        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot item : dataSnapshot.getChildren()) {
                    appConfig = dataSnapshot.getValue(AppConfig.class);
                    appConfig.email_user_id = communifyCrypto.decrypt(secretKey, appConfig.email_user_id);
                    appConfig.email_password = communifyCrypto.decrypt(secretKey, appConfig.email_password);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                System.out.println("The read failed: " + databaseError.getCode());
            }
        });

        return appConfig;
    }
}

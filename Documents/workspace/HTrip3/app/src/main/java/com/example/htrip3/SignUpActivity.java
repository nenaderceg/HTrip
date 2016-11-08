package com.example.htrip3;

import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.microsoft.windowsazure.mobileservices.http.NextServiceFilterCallback;
import com.microsoft.windowsazure.mobileservices.http.OkHttpClientFactory;
import com.microsoft.windowsazure.mobileservices.http.ServiceFilter;
import com.microsoft.windowsazure.mobileservices.http.ServiceFilterRequest;
import com.microsoft.windowsazure.mobileservices.http.ServiceFilterResponse;
import com.microsoft.windowsazure.mobileservices.table.MobileServiceTable;
import com.microsoft.windowsazure.mobileservices.table.TableQueryCallback;
import com.microsoft.windowsazure.mobileservices.table.query.Query;
import com.microsoft.windowsazure.mobileservices.table.query.QueryOperations;
import com.microsoft.windowsazure.mobileservices.table.sync.MobileServiceSyncContext;
import com.microsoft.windowsazure.mobileservices.table.sync.MobileServiceSyncTable;
import com.microsoft.windowsazure.mobileservices.table.sync.localstore.ColumnDataType;
import com.microsoft.windowsazure.mobileservices.table.sync.localstore.MobileServiceLocalStoreException;
import com.microsoft.windowsazure.mobileservices.table.sync.localstore.SQLiteLocalStore;
import com.microsoft.windowsazure.mobileservices.table.sync.synchandler.SimpleSyncHandler;
import com.squareup.okhttp.OkHttpClient;

import static com.microsoft.windowsazure.mobileservices.table.query.QueryOperations.*;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.atomic.AtomicBoolean;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

import com.microsoft.windowsazure.mobileservices.authentication.MobileServiceAuthenticationProvider;
import com.microsoft.windowsazure.mobileservices.authentication.MobileServiceUser;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.widget.Toast;

import java.util.List;

import java.net.MalformedURLException;
import com.microsoft.windowsazure.mobileservices.MobileServiceClient;





public class SignUpActivity extends AppCompatActivity {


    // UI references
    private EditText edFirstName;
    private EditText edLastName;
    private EditText edEmail;
    private EditText edPassw;

    //URL
    private String URL = "http://demohunter.azurewebsites.net";
    private EditText mEdit;
    private MobileServiceTable<Account> accTable;
    private Boolean UserNExist;
    private String HunterEmail = "@myhunter.cuny.edu";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        //Toolbar setting
        Toolbar myToolbar = (Toolbar) findViewById(R.id.sign_up_toolbar);
        setSupportActionBar(myToolbar);
        myToolbar.setLogo(R.drawable.ic_logo);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);

        try {
            MobileServiceClient mClient = new MobileServiceClient(URL, SignUpActivity.this);

            accTable = mClient.getTable(Account.class);



        } catch (MalformedURLException e) {
            e.printStackTrace();
        }




        Button btnRegister = (Button) findViewById(R.id.btnRegister);


        btnRegister.setOnClickListener(new View.OnClickListener() {

            public void onClick(View view) {


                Account myAcc = new Account();

                edFirstName = (EditText) findViewById(R.id.txtFirstName);
                //myAcc.firstname = edFirstName.getText().toString();

                edLastName = (EditText) findViewById(R.id.txtLastName);
                //myAcc.lastname = edLastName.getText().toString();

                edEmail = (EditText) findViewById(R.id.txtEmail);
                //myAcc.email = edEmail.getText().toString();

                edPassw = (EditText) findViewById(R.id.txtPassword);
                //myAcc.password= edPassw.getText().toString();

                myAcc.firstname = edFirstName.getText().toString();
                myAcc.lastname = edLastName.getText().toString();
                myAcc.email = edEmail.getText().toString();
                myAcc.password= edPassw.getText().toString();

                CheckFromtable(edEmail.getText().toString(),myAcc);

                Toast.makeText(getApplicationContext(),
                        myAcc.email, Toast.LENGTH_SHORT).show();

            }
        });

    }

    public void CheckFromtable(final String email, final Account temp) {


        // accTable.where().field("deleted").eq(val(false)).execute(new TableQueryCallback<Account>() {
        accTable.where().field("email").eq(val(email)).execute(new TableQueryCallback<Account>() {
            @Override
            public void onCompleted(List<Account> result, int count, Exception exception, ServiceFilterResponse response) {

                if (exception == null) {

                    Log.e("Email:", result.toString());
                    if (result.size() != 0) {
                        if (result.get(0).email.equals(email)) {

                            Log.e("email", "exisit");
                            Toast.makeText(getApplicationContext(),
                                    "Email Account Already Registered", Toast.LENGTH_SHORT).show();

                            //UserNExist=false;
                        } else {
                            Log.e("Notfound ", "User not found");


                        }


                    }
                    else {
                        //UserNExist=true;
                        if (temp.email.toLowerCase().contains(HunterEmail)) {

                            accTable.insert(temp);
                            Toast.makeText(getApplicationContext(), "User was created", Toast.LENGTH_SHORT).show();
                            //UserNExist=false;
                        } else {

                            Toast.makeText(getApplicationContext(), "Not a correct Hunter Email", Toast.LENGTH_SHORT).show();
                        }

                    }

                    //Log.e("AccountList",exception.getMessage());
                }


            }
        });
    }
}





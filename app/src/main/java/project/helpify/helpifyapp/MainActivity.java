package project.helpify.helpifyapp;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.IdRes;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private Button buttonRegister;
    private EditText editTextEmail;
    private EditText editTextPassword;
    private TextView textViewSignin;

    private ProgressDialog progressDialog;
    private FirebaseAuth firebaseAuth; //Firebase object



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        firebaseAuth = FirebaseAuth.getInstance();

        progressDialog = new ProgressDialog(this);

        buttonRegister =(Button)findViewById(R.id.buttonRegister);

        editTextEmail  = (EditText) findViewById(R.id.editTextEmail);
        editTextPassword = (EditText )findViewById(R.id.editTextPassword);

        textViewSignin = (TextView)findViewById(R.id.textViewSignin);

        if(firebaseAuth.getCurrentUser() != null){
            finish();
            startActivity(new Intent(getApplicationContext(),ProfileActivity.class));
        }


        //Text in forms centered
        EditText t = (EditText) findViewById(R.id.editTextEmail);
        t.setGravity(Gravity.CENTER);
        EditText p = (EditText) findViewById(R.id.editTextPassword);
        p.setGravity(Gravity.CENTER);


        buttonRegister.setOnClickListener(this);
        textViewSignin.setOnClickListener(this);
    }

    private void registerUser(){
        String email = editTextEmail.getText().toString().trim();
        String password = editTextPassword.getText().toString().trim();

        if (TextUtils.isEmpty(email)){
            //email is empty
            Toast.makeText(this,"Please enter email",Toast.LENGTH_SHORT).show();
            //Stops the function from excecuting further.
            return;
        }
        if (TextUtils.isEmpty(password)){
            //Same for password
            Toast.makeText(this,"Please enter a password",Toast.LENGTH_SHORT).show();
            return;
        }


        progressDialog.setMessage("Registering User...");
        progressDialog.show();

        // USER CREATION
        firebaseAuth.createUserWithEmailAndPassword(email,password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>(){
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task){
                        //CHECKING IF REGISTRATION WAS SUCCESSFUL
                        if(task.isSuccessful()){
                                //finish current activity and start profile
                                finish();
                                startActivity(new Intent(getApplicationContext(),ProfileActivity.class));

                        }else{
                            Toast.makeText(MainActivity.this,"Something went wrong, try again :(", Toast.LENGTH_SHORT).show();
                        }
                    }

                });

    }

    @Override
    public void onClick(View view) {
        if(view == buttonRegister){
            registerUser();
        }
        if(view == textViewSignin){
            finish();
            startActivity(new Intent(this,LoginActivity.class));
        }
    }
}

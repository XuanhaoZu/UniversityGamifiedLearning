package au.edu.unsw.infs3634.unswgamifiedlearningapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import java.util.concurrent.Executors;


public class SignInActivity extends AppCompatActivity {
    private EditText zid, name, psw, email;
    private ImageButton a1, a2, a3, a4, a5, a6;
    private Button btnSignIn;
    private AppDatabase mDb;
    private int imageSource;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signin);

        zid = findViewById(R.id.etZid);
        name = findViewById(R.id.etName);
        psw = findViewById(R.id.etPassword);
        email = findViewById(R.id.etEmail);
        a1 = findViewById(R.id.iva1);
        a2 = findViewById(R.id.iva2);
        a3 = findViewById(R.id.iva3);
        a4 = findViewById(R.id.iva4);
        a5 = findViewById(R.id.iva5);
        a6 = findViewById(R.id.iva6);
        btnSignIn = findViewById(R.id.btnSignIn);


        mDb = Room.databaseBuilder(getApplicationContext(), AppDatabase.class, "app-database").build();

        //image for user to choose as  user avatar
        a1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                imageSource = R.drawable.avatar1;
            }
        });
        a2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                imageSource = R.drawable.avatar2;
            }
        });
        a3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                imageSource = R.drawable.avatar3;
            }
        });
        a4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                imageSource = R.drawable.avatar4;
            }
        });
        a5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                imageSource = R.drawable.avatar5;
            }
        });
        a6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                imageSource = R.drawable.avatar6;
            }
        });


        //Sign up function
        btnSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                zid.getText();
                name.getText();
                psw.getText();
                email.getText();

                User user = new User(String.valueOf(zid.getText()), String.valueOf(name.getText()), String.valueOf(psw.getText()), String.valueOf(email.getText()), imageSource);

                Executors.newSingleThreadExecutor().execute(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            if ((user.getZid() != null) && (user.getPassword() != null) && (user.getEmail() != null) && (user.getImageId() != 0)) {
                                mDb.userDao().insetUser(user);

                                runOnUiThread(new Runnable() {
                                    public void run() {
                                        Toast.makeText(SignInActivity.this, "Sign in Successful!", Toast.LENGTH_SHORT).show();
                                    }
                                });

                                Intent intent = new Intent(SignInActivity.this, MainActivity.class);
                                startActivity(intent);

                            } else {
                                Toast.makeText(SignInActivity.this, "Please fill all information field.", Toast.LENGTH_SHORT).show();
                            }

                        } catch (Exception e) {
                            runOnUiThread(new Runnable() {
                                public void run() {
                                    Toast.makeText(SignInActivity.this, "Invalid information, check it again.", Toast.LENGTH_SHORT).show();
                                }
                            });
                        }

                    }
                });

            }
        });


    }
}

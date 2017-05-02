package solutions.it.zanjo.travease.Activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import solutions.it.zanjo.travease.R;

public class ProfileActivity extends AppCompatActivity {

    TextView title;
    ImageView backBT,forwordBT;
    Button changepassBT;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

      title = (TextView) toolbar.findViewById(R.id.TitleTV);
        backBT= (ImageView) toolbar.findViewById(R.id.backBT);
        forwordBT= (ImageView) toolbar.findViewById(R.id.forwordBT);
        changepassBT= (Button) findViewById(R.id.changeBT);
        title.setText("Profile");
        forwordBT.setBackgroundResource(R.drawable.logout_img_b);
        backBT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        forwordBT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        changepassBT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ProfileActivity.this,ChangePasswordActivity.class));
            }
        });


    }
}

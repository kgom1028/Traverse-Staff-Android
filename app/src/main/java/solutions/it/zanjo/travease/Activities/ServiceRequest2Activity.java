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

public class ServiceRequest2Activity extends AppCompatActivity {

     Button completeBT,escalateBT,reassignBT;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_service_request2);

        completeBT= (Button) findViewById(R.id.completeBT);
        escalateBT= (Button) findViewById(R.id.escalateBT);
        reassignBT= (Button) findViewById(R.id.reassignBT);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        TextView title= (TextView) toolbar.findViewById(R.id.TitleTV);
        title.setText("Service Request");
       ImageView backBT= (ImageView) toolbar.findViewById(R.id.backBT);
        ImageView forwordBT= (ImageView) toolbar.findViewById(R.id.forwordBT);
        forwordBT.setBackgroundResource(R.drawable.chat_service_icon);
        forwordBT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ServiceRequest2Activity.this,ChatMessageActivity.class));

            }
        });

        backBT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        completeBT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              finish();
            }
        });
        escalateBT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
           startActivity(new Intent(ServiceRequest2Activity.this,EscalateActivity.class));
                finish();
            }
        });
        reassignBT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ServiceRequest2Activity.this,ReassignActivity.class));
                finish();

            }
        });
    }
}

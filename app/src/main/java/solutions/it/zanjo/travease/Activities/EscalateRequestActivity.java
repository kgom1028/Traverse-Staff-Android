package solutions.it.zanjo.travease.Activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import solutions.it.zanjo.travease.R;

public class EscalateRequestActivity extends AppCompatActivity {

    Button acceptBT,rejectBT;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_escalate_request);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        TextView title= (TextView) toolbar.findViewById(R.id.TitleTV);
        title.setText("Escalate Request");
        ImageView backBT= (ImageView) toolbar.findViewById(R.id.backBT);
        backBT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        acceptBT=(Button)findViewById(R.id.acceptBT);
        rejectBT=(Button)findViewById(R.id.rejectBT);

        acceptBT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        rejectBT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }
}

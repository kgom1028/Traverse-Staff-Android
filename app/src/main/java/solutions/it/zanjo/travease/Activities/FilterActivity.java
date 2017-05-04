package solutions.it.zanjo.travease.Activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import solutions.it.zanjo.travease.R;

public class FilterActivity extends AppCompatActivity {

    CheckBox newReqCB,assignCB,asignTootherCB,problemCB,receptionCB,housekeepCB,conceirgeCB,foodCB,enggCB;
    TextView title;
    ImageView backBT,forwordBT;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_filter);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        newReqCB=(CheckBox)findViewById(R.id.newReqCB);
        assignCB=(CheckBox)findViewById(R.id.assignCB);
        asignTootherCB=(CheckBox)findViewById(R.id.assignToOtherCB);
        problemCB=(CheckBox)findViewById(R.id.problemCB);
        receptionCB=(CheckBox)findViewById(R.id.receptionCB);
        housekeepCB=(CheckBox)findViewById(R.id.housekeepCB);
        conceirgeCB=(CheckBox)findViewById(R.id.conceirgeCB);
        foodCB=(CheckBox)findViewById(R.id.foodCB);
        enggCB=(CheckBox)findViewById(R.id.enggCB);


        title = (TextView) toolbar.findViewById(R.id.TitleTV);
        backBT= (ImageView) toolbar.findViewById(R.id.backBT);
        forwordBT= (ImageView) toolbar.findViewById(R.id.forwordBT);
        forwordBT.setBackgroundResource(R.drawable.clear);
        title.setText("Filter");
        backBT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        forwordBT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                newReqCB.setChecked(false);
                assignCB.setChecked(false);
                asignTootherCB.setChecked(false);
                problemCB.setChecked(false);
                receptionCB.setChecked(false);
                housekeepCB.setChecked(false);
                conceirgeCB.setChecked(false);
                foodCB.setChecked(false);
                enggCB.setChecked(false);

            }
        });
    }
}

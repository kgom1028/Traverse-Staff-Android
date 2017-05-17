package solutions.it.zanjo.travease.Activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import solutions.it.zanjo.travease.Commons.Common;
import solutions.it.zanjo.travease.R;

public class ReportProblemActivity extends AppCompatActivity {

    EditText toET,ccET,subjectET,textET;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report_problem);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        toET= (EditText) findViewById(R.id.toET);
        ccET= (EditText) findViewById(R.id.ccET);
        subjectET= (EditText) findViewById(R.id.subjectET);
        textET= (EditText) findViewById(R.id.textET);
        TextView title= (TextView) toolbar.findViewById(R.id.TitleTV);
        title.setText("Report a Problem");
        ImageView forwordBT=(ImageView)findViewById(R.id.forwordBT);
        forwordBT.setBackgroundResource(R.drawable.next_icon);
        ImageView backBT= (ImageView) toolbar.findViewById(R.id.backBT);
        backBT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        forwordBT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Common.isInternetOn())
                {
                    if (!validate()) {
                        onResetFailed();
                        return;
                    }

                Intent email = new Intent(Intent.ACTION_SEND);
                email.putExtra(Intent.EXTRA_EMAIL, new String[]{ toET.getText().toString()});
                email.putExtra(Intent.EXTRA_CC, new String[]{ ccET.getText().toString()});
                email.putExtra(Intent.EXTRA_SUBJECT, subjectET.getText().toString());
                email.putExtra(Intent.EXTRA_TEXT, textET.getText().toString());

                //need this to prompts email client only
                email.setType("message/rfc822");
                try {
                    startActivity(Intent.createChooser(email, "Send mail..."));
                    finish();
                } catch (android.content.ActivityNotFoundException ex) {
                    Toast.makeText(ReportProblemActivity.this, "There is no email client installed.", Toast.LENGTH_SHORT).show();
                }

                } else  startActivity(new Intent(ReportProblemActivity.this,NoInternetActivity.class));
            }
        });
    }
    public void onResetFailed() {
        Toast.makeText(ReportProblemActivity.this, "Mail send failed", Toast.LENGTH_LONG).show();

    }
    public boolean validate() {
        boolean valid = true;

        String To = toET.getText().toString();
        String CC = ccET.getText().toString();
        String Subject = subjectET.getText().toString();
        String Text = textET.getText().toString();


        if (To.isEmpty() || !android.util.Patterns.EMAIL_ADDRESS.matcher(To).matches()) {
            toET.setError("enter a valid email address");
            valid = false;
        } else {
            toET.setError(null);
        }
        if (CC.isEmpty() || !android.util.Patterns.EMAIL_ADDRESS.matcher(CC).matches()) {
            ccET.setError("enter a valid email address");
            valid = false;
        } else {
            ccET.setError(null);
        }
        if (Subject.isEmpty()) {
            subjectET.setError("enter subject");
            valid = false;
        } else {
            subjectET.setError(null);
        }
        if (Text.isEmpty()) {
            textET.setError("enter message");
            valid = false;
        } else {
            textET.setError(null);
        }

        return valid;
    }
}

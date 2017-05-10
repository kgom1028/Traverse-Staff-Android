package solutions.it.zanjo.travease.Commons;

import android.app.Application;
import android.content.Context;

import org.acra.ReportingInteractionMode;
import org.acra.annotation.ReportsCrashes;

import solutions.it.zanjo.travease.Other.MyKeyStoreFactory;
import solutions.it.zanjo.travease.R;

@ReportsCrashes(keyStoreFactoryClass=MyKeyStoreFactory.class,formUri = "http://www.backendofyourchoice.com/reportpath",
        mode = ReportingInteractionMode.DIALOG,
        resToastText = R.string.crash_toast_text, // optional, displayed as soon as the crash occurs, before collecting data which can take a few seconds
        resDialogText = R.string.crash_dialog_text,
        resDialogIcon = android.R.drawable.ic_dialog_info, //optional. default is a warning sign
        resDialogTitle = R.string.crash_dialog_title, // optional. default is your application name
        resDialogCommentPrompt = R.string.crash_dialog_comment_prompt, // optional. When defined, adds a user text field input with this text resource as a label
        resDialogEmailPrompt = R.string.crash_user_email_label, // optional. When defined, adds a user email text entry with this text resource as label. The email address will be populated from SharedPreferences and will be provided as an ACRA field if configured.
        resDialogOkToast = R.string.crash_dialog_ok_toast, // optional. displays a Toast message when the user accepts to send a report.
        resDialogTheme = R.style.AppTheme_Dialog) //optional. default is Theme.Dialog)
public class MyApplication extends Application {

    private static Context context;

    public void onCreate() {
        super.onCreate();
        MyApplication.context = getApplicationContext();
    }

    public static Context getAppContext() {
        return MyApplication.context;
    }

   /* @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);

        // Create an ConfigurationBuilder. It is prepopulated with values specified via annotation.
        // Set any additional value of the builder and then use it to construct an ACRAConfiguration.
        final ACRAConfiguration config = new ConfigurationBuilder(this)
                .setFoo(foo)
                .setBar(bar)
                .build();

        // Initialise ACRA
        ACRA.init(this, config);
    }*/
}

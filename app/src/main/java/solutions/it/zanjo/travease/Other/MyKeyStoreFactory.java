package solutions.it.zanjo.travease.Other;


import android.content.Context;
import android.support.annotation.NonNull;

import org.acra.security.BaseKeyStoreFactory;

import java.io.InputStream;

/**
 * Created by abc on 5/10/2017.
 */

public class MyKeyStoreFactory extends BaseKeyStoreFactory {
    @Override
    protected InputStream getInputStream(@NonNull Context context) {
        return null;
    }
}

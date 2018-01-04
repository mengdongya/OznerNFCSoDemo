package com.ozner.nfc;

import android.content.Context;

/**
 * Created by ozner_67 on 2017/6/7.
 * 邮箱：xinde.zhang@cftcn.com
 */

public class OznerNFC {
    static {
        System.loadLibrary("oznernfc_lib");
    }
    public native String getKey(Context context);
}

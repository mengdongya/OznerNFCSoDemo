package com.ozner.nfc;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

/**
 * Created by ozner_67 on 2017/6/7.
 * 邮箱：xinde.zhang@cftcn.com
 */

public abstract class BaseNFCActivity extends AppCompatActivity {
   protected OznerNfcManager oznerNfcManager;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayoutResID());
        oznerNfcManager = new OznerNfcManager(this);
        oznerNfcManager.onCreate();
        oznerNfcManager.setOznerNfcOpera(getNfcOpera());
    }

    protected abstract int getLayoutResID();

    /**
     * 设置NFC操作回调
     *
     * @return
     */
    protected abstract OznerNfcManager.IOnzerNfcOpera getNfcOpera();

    /**
     * @param errmsg 与NFC有关的错误信息
     */
    protected abstract void onErrorMessage(String errmsg);

    @Override
    protected void onResume() {
        super.onResume();
        if (oznerNfcManager != null) {
            try {
                oznerNfcManager.onReusme();
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        if (!OznerNfcManager.isDeviceSupport(intent)) {
            onErrorMessage("设备不支持");
            return;
        }

        if (oznerNfcManager != null) {
            try {
                oznerNfcManager.onNewIntent(intent);
            } catch (Exception ex) {
                ex.printStackTrace();
                onErrorMessage(ex.getMessage());

            }
        }
    }

    @Override
    protected void onPause() {
        if (oznerNfcManager != null) {
            oznerNfcManager.onPause();
        }
        super.onPause();
    }
}

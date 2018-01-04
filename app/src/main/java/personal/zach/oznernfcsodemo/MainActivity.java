package personal.zach.oznernfcsodemo;

import android.content.ComponentName;
import android.content.Intent;
import android.nfc.NfcAdapter;
import android.nfc.cardemulation.CardEmulation;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.ozner.nfc.OznerNfcManager;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";
    OznerNfcManager oznerNfcManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        oznerNfcManager = new OznerNfcManager(this);
//        oznerNfcManager.set
//        ((TextView) findViewById(R.id.tv_key)).setText(oznerNfcManager.getKey(this));
//        Log.e(TAG, "onCreate:" + oznerNfcManager.getKey(this));
        findViewById(R.id.btnNFC).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this,NFCTestActivity.class));
            }
        });
    }

    private void checkIsDefaultApp() {
        CardEmulation cardEmulationManager = CardEmulation.getInstance(NfcAdapter.getDefaultAdapter(this));
        ComponentName paymentServiceComponent = new ComponentName(getApplicationContext(), CardMangerService.class.getCanonicalName());
        if (!cardEmulationManager.isDefaultServiceForCategory(paymentServiceComponent, CardEmulation.CATEGORY_PAYMENT)) {
            Intent intent = new Intent(CardEmulation.ACTION_CHANGE_DEFAULT);
            intent.putExtra(CardEmulation.EXTRA_CATEGORY,CardEmulation.CATEGORY_PAYMENT);
            intent.putExtra(CardEmulation.EXTRA_SERVICE_COMPONENT,paymentServiceComponent);
            startActivityForResult(intent, 0);
            L.d("TAG","当前应用不是默认支付，需手动设置");
        } else {
            L.d("TAG","当前应用是系统默认支付程序");
        }
    }
}

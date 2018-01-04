package personal.zach.oznernfcsodemo;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import com.ozner.nfc.BaseNFCActivity;
import com.ozner.nfc.CardBean.MonthCard;
import com.ozner.nfc.OperationState;
import com.ozner.nfc.OznerNfcManager;

public class NFCTestActivity extends BaseNFCActivity {
    private static final String TAG = "NFCTestActivity";
    Handler mHandler;
    private ProgressDialog dialog;
    TextView tvResult;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mHandler = new Handler();
        tvResult = (TextView) findViewById(R.id.tvResult);
        dialog = new ProgressDialog(this);
        dialog.setMessage("正在读写卡");

        if (!oznerNfcManager.hasNfc()) {
            Toast.makeText(this, "不支持NFC功能", Toast.LENGTH_SHORT).show();
            finish();
        }

        if (!oznerNfcManager.isNfcEnable()) {
            new AlertDialog.Builder(this, android.app.AlertDialog.THEME_HOLO_LIGHT)
                    .setMessage("打开NFC")
                    .setPositiveButton("好", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            oznerNfcManager.openNfc();
                        }
                    })
                    .setNegativeButton("否", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            NFCTestActivity.this.finish();
                        }
                    }).show();
        }
    }

    @Override
    protected int getLayoutResID() {
        return R.layout.activity_nfctest;
    }

    @Override
    protected OznerNfcManager.IOnzerNfcOpera getNfcOpera() {
        return new OznerNfcManager.IOnzerNfcOpera() {
            @Override
            public void dealWork() {
//                mHandler.post(new Runnable() {
//                    @Override
//                    public void run() {
//                        tvResult.setText("正在读写卡");
//                        dialog.show();
//                    }
//                });
                oznerNfcManager.writeMonthCard("2", "2", "9990", "24", "0001", new OznerNfcManager.INfcWriterListener() {
                    @Override
                    public void onResult(int state, final String errmsg) {
                        if (state == OperationState.Result_Ok) {
                            mHandler.post(new Runnable() {
                                @Override
                                public void run() {
                                    tvResult.setText("写入完成\n");
                                }
                            });
                        } else {
                            mHandler.post(new Runnable() {
                                @Override
                                public void run() {
                                    tvResult.setText(errmsg + "\n");
                                }
                            });
                        }
                    }
                });


                oznerNfcManager.readMonthCard(new OznerNfcManager.IMonthCardListener() {
                    @Override
                    public void onResult(int state, String errmsg, final MonthCard monthCard) {
//                    Log.e(TAG, "读卡onResult:\n" + monthCard.toString());
//                        mHandler.post(new Runnable() {
//                            @Override
//                            public void run() {
//                                if (dialog.isShowing()) {
//                                    dialog.cancel();
//                                }
//                            }
//                        });
                        Log.e(TAG, "读卡onResult:");
                        if (state == OperationState.Result_Ok) {
                            mHandler.post(new Runnable() {
                                @Override
                                public void run() {

//                                if (isCipherCard) {
//                                    tvResult.setText("");
//                                } else {
                                    if (monthCard != null) {
                                        tvResult.append(monthCard.toString());
//                                }
                                    }
                                    tvResult.append("\n读写卡完成");

//                                int cardType = Integer.parseInt(monthCard.cardType);


//                                tvCardType.setText(String.valueOf(Integer.parseInt(monthCard.cardType)) + "(" + getCardType(cardType) + ")");
//                                tvCardNumber.setText(String.valueOf(Long.parseLong(monthCard.cardNumber)));
//                                try {
//                                    if (isCipherCard) {
//                                        tvCardFace.setText("KeyA");
//                                        tvCardValue.setText("KeyB");
//                                    } else {
//                                        tvCardFace.setText(String.valueOf(Integer.parseInt(monthCard.cardFaceValue)));
//                                        tvCardValue.setText(String.valueOf(Integer.parseInt(monthCard.cardValue)));
//                                    }
//                                    tvDeviceType.setText(String.valueOf(Integer.parseInt(monthCard.deviceType)));
//                                    tvAreaId.setText(String.valueOf(Integer.parseInt(monthCard.areaCode)));
//                                } catch (Exception ex) {
//                                    Log.e(TAG, "readMonthCard_Ex: " + ex.getMessage());
//                                }
                                }
                            });

                        } else {
                            Log.e(TAG, "onResult: " + errmsg);
                            Toast.makeText(NFCTestActivity.this, "state:" + state + ",errmsg:" + errmsg, Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        };
    }

    @Override
    protected void onErrorMessage(String errmsg) {
        Toast.makeText(this, errmsg, Toast.LENGTH_SHORT).show();
    }
}

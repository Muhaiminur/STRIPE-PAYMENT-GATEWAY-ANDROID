package com.muhaiminurabir.stripepaymentgateway;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.stripe.android.Stripe;
import com.stripe.android.TokenCallback;
import com.stripe.android.model.Card;
import com.stripe.android.model.Token;
import com.stripe.android.view.CardInputWidget;
import com.vinaygaba.creditcardview.CreditCardView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.send)
    Button send;


    CardInputWidget mCardInputWidget;
    Card card;
    @BindView(R.id.token_view)
    TextView tokenView;

    CreditCardView creditCardView;
    @BindView(R.id.send2)
    Button send2;
    @BindView(R.id.token_view2)
    TextView tokenView2;
    @BindView(R.id.card_name)
    EditText cardName;
    @BindView(R.id.card_num)
    EditText cardNum;
    @BindView(R.id.card_month)
    EditText cardMonth;
    @BindView(R.id.card_year)
    EditText cardYear;
    @BindView(R.id.card_cvc)
    EditText cardCvc;
    @BindView(R.id.send3)
    Button send3;
    @BindView(R.id.token_view3)
    TextView tokenView3;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        try {
            setContentView(R.layout.activity_main);
            ButterKnife.bind(this);
            mCardInputWidget = (CardInputWidget) findViewById(R.id.card_input_widget);
            creditCardView = (CreditCardView) findViewById(R.id.second_card);
        } catch (Exception e) {
            Log.d("Error Line Number", Log.getStackTraceString(e));
        }
    }

    @OnClick({R.id.send, R.id.send2})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.send:
                Log.d("Stripe", "going");
                card = mCardInputWidget.getCard();
                if (card == null) {
                    Toast.makeText(this, "Invalid Card Data", Toast.LENGTH_SHORT).show();
                    //mErrorDialogHandler.showError("Invalid Card Data");
                    return;
                }
                final ProgressDialog pd = new ProgressDialog(MainActivity.this);
                pd.setMessage("loading");
                pd.show();
                if (!card.validateCard()) {
                    pd.dismiss();
                    // Show errors
                    Toast.makeText(this, "Invalid Card Data2", Toast.LENGTH_SHORT).show();
                    return;
                }
                Log.d("card number", card.getNumber());
                Stripe stripe = new Stripe(getApplicationContext(), "pk_test_qt2MDLGhYOg6hznSMiD8fNLE");
                stripe.createToken(
                        card,
                        new TokenCallback() {
                            public void onSuccess(Token token) {
                                pd.dismiss();
                                tokenView.setText("ID = " + token.getId());
                                Log.d("Stripe token", token.getId());
                            }

                            public void onError(Exception error) {
                                pd.dismiss();
                                Log.d("Stripe token error", error.getMessage());
                                Toast.makeText(getApplicationContext(), error.getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        }
                );
                break;
            case R.id.send2:
                Log.d("Stripe2", "going");
                String cardNumber = creditCardView.getCardNumber();
                if (cardNumber == null) {
                    Toast.makeText(this, "Invalid Card1 Data", Toast.LENGTH_SHORT).show();
                    //mErrorDialogHandler.showError("Invalid Card Data");
                    return;
                }
                final ProgressDialog pd2 = new ProgressDialog(MainActivity.this);
                pd2.setMessage("loading");
                pd2.show();
                Log.d("Stripe2", cardNumber);
                Card card2 = new Card("4111111111111111", 12, 2019, "123");
                if (!card2.validateCard()) {
                    // Show errors
                    pd2.dismiss();
                    Toast.makeText(this, "Invalid Card1 Data2", Toast.LENGTH_SHORT).show();
                    return;
                }
                Stripe stripe2 = new Stripe(getApplicationContext(), "pk_test_qt2MDLGhYOg6hznSMiD8fNLE");
                stripe2.createToken(
                        card2,
                        new TokenCallback() {
                            public void onSuccess(Token token) {
                                pd2.dismiss();
                                tokenView2.setText("ID2 = " + token.getId());
                                Log.d("Stripe token2", token.getId());
                            }

                            public void onError(Exception error) {
                                pd2.dismiss();
                                Log.d("Stripe token2 error", error.getMessage());
                                Toast.makeText(getApplicationContext(), error.getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        }
                );
                break;
        }
    }

    @OnClick(R.id.send3)
    public void onViewClicked() {
        Log.d("Stripe3", "going3");
        Log.d("Number", cardNum.getText().toString());
        if (cardNum.getText() == null) {
            Toast.makeText(this, "Invalid Card3 Data", Toast.LENGTH_SHORT).show();
            //mErrorDialogHandler.showError("Invalid Card Data");
            return;
        }
        final  ProgressDialog pd3 = new ProgressDialog(MainActivity.this);
        pd3.setMessage("loading");
        pd3.show();
        Card card3 = new Card(cardNum.getText().toString(), Integer.parseInt(cardMonth.getText().toString()), Integer.parseInt(cardYear.getText().toString()), cardCvc.getText().toString());
        Log.d("CARD 3", "Number "+cardNum.getText().toString()+" Month "+Integer.parseInt(cardMonth.getText().toString())+" Year "+Integer.parseInt(cardYear.getText().toString())+" cvc "+cardCvc.getText().toString());
        Log.d("CARD 3 Main", "Number "+card3.getNumber()+" Month "+card3.getExpMonth()+" Year "+card3.getExpYear()+" cvc "+card3.getCVC());
        if (!card3.validateCard()) {
            // Show errors
            pd3.dismiss();
            Toast.makeText(this, "Invalid Card3 Data2", Toast.LENGTH_SHORT).show();
            return;
        }
        Stripe stripe3 = new Stripe(getApplicationContext(), "pk_test_qt2MDLGhYOg6hznSMiD8fNLE");
        stripe3.createToken(
                card3,
                new TokenCallback() {
                    public void onSuccess(Token token) {
                        pd3.dismiss();
                        tokenView3.setText("ID = " + token.getId());
                        Log.d("Stripe token", token.getId());
                    }

                    public void onError(Exception error) {
                        pd3.dismiss();
                        Log.d("Stripe token error", error.getMessage());
                        Toast.makeText(getApplicationContext(), error.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
        );
    }
}

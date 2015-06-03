package edu.ub.pis2015.csoler.appdivises;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;


public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    /**
     * Exchanges USD - EUR labels.
     * @param vw
     */
    public void changeLblUsdEuro(View vw){
        TextView txtEuro = (TextView) this.findViewById(R.id.lbl_eur);
        TextView txtUsd = (TextView) this.findViewById(R.id.lbl_usd);
        TextView txtBtn = (TextView) this.findViewById(R.id.btn_change);

        TextView inputRate = (TextView) this.findViewById(R.id.input_exchange);

        if (txtEuro.getText().equals(getString(R.string.lbl_eur)) && (txtUsd.getText().equals(getString(R.string.lbl_usd)))
                && txtBtn.getText().equals(getString(R.string.lbl_eur_to_usd))){
            txtEuro.setText(R.string.lbl_usd);
            txtUsd.setText(R.string.lbl_eur);
            txtBtn.setText(R.string.lbl_usd_to_eur);
        } else {
            if (txtEuro.getText().equals(getString(R.string.lbl_usd))&& (txtUsd.getText().equals(getString(R.string.lbl_eur)))
                    && txtBtn.getText().equals(getString(R.string.lbl_usd_to_eur))){
                txtEuro.setText(R.string.lbl_eur);
                txtUsd.setText(R.string.lbl_usd);
                txtBtn.setText(R.string.lbl_eur_to_usd);
            }
        }

        if (inputRate.getText().toString() != ""){
            float floatRate = Float.parseFloat(inputRate.getText().toString());
            if (floatRate > 0){
                inputRate.setText(Float.toString(1/floatRate));
            }
        }
    }

    /**
     * Makes the conversion with the current parametres and updates the TextView of the Conversion.
     * If anything is missing, an error message box appears.
     * @param vw
     */
    public void convert(View vw) {
        TextView inputMoney = (TextView) this.findViewById(R.id.input_divisa);
        TextView inputCommission = (TextView) this.findViewById(R.id.input_commission);
        TextView inputExchange = (TextView) this.findViewById(R.id.input_exchange);

        String txtMoney = inputMoney.getText().toString();
        String txtCommission = inputCommission.getText().toString();
        String txtRate = inputExchange.getText().toString();

        if (txtMoney.equals("") || txtCommission.equals("") || txtRate.equals("")) {
            Context context = this;
            AlertDialog.Builder alertDialogBuilder = this.createErrorMessage(context, getString(R.string.error_convert));
            AlertDialog alert = alertDialogBuilder.create();
            alert.show();

        } else {

            float floatMoney = Float.parseFloat(inputMoney.getText().toString());
            float floatCommission = Float.parseFloat(inputCommission.getText().toString());
            float floatRate = Float.parseFloat(inputExchange.getText().toString());

            if (floatMoney < 0 || floatCommission < 0 || floatCommission > 100 || floatRate < 0) {
                Context context = this;
                AlertDialog.Builder alertDialogBuilder = this.createErrorMessage(context, getString(R.string.error_convert));
                AlertDialog alert = alertDialogBuilder.create();
                alert.show();
            }

            else {
                TextView lblResult = (TextView) this.findViewById(R.id.lbl_result);
                lblResult.setText(Float.toString(this.calculateResult(floatMoney, floatCommission, floatRate)));
            }
        }
    }

    /**
     * Calculates the result of the conversion.
     * @param money
     * @param commission
     * @param rate
     * @return
     */
    private float calculateResult(float money, float commission, float rate){
        return (100-commission)*money*rate/100;
    }

    /**
     * Creates an AlertDialog with "message" in it.
     * @param context
     * @param message
     * @return
     */
    private AlertDialog.Builder createErrorMessage(Context context, String message){
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);

        alertDialogBuilder
                .setTitle(message)
                .setCancelable(false)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });
        return alertDialogBuilder;
    }
}
package com.jmedinilla.medinillamontaditos;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class ResultActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);

        TextView txtResult = (TextView) findViewById(R.id.txtResult);
        String result = getIntent().getStringExtra("result_extra");
        txtResult.setText("Este es tu pedido:\n\n" + result);
    }
}

package com.cashow.cashowmemorymonitor;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class ThirdActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        TextView textView = (TextView) findViewById(R.id.text);
        textView.setText("ThirdActivity");
        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                throw new OutOfMemoryError();
            }
        });
    }
}

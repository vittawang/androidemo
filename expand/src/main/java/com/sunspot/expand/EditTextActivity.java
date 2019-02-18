package com.sunspot.expand;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextWatcher;
import android.text.style.ForegroundColorSpan;
import android.widget.EditText;

public class EditTextActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_text);
        final EditText edit = (EditText) findViewById(R.id.edit);
        final SpannableString hello = new SpannableString("你好呀你好呀");
        hello.setSpan(new ForegroundColorSpan(Color.GRAY),0,hello.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        edit.setText(hello);
        edit.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.toString().length() == 0){
                    edit.setText(hello);
                }
            }
        });
    }
}

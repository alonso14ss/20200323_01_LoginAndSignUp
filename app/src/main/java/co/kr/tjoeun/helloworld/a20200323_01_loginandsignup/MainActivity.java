package co.kr.tjoeun.helloworld.a20200323_01_loginandsignup;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.content.Context;
import android.os.Bundle;
import android.view.View;

import co.kr.tjoeun.helloworld.a20200323_01_loginandsignup.databinding.ActivityMainBinding;
import co.kr.tjoeun.helloworld.a20200323_01_loginandsignup.utils.ContextUtil;

public class MainActivity extends BaseActivity {

    ActivityMainBinding binding = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this,R.layout.activity_main);

        setupEvents();
        setValues();
    }

    @Override
    public void setupEvents() {
//        로그인 버튼을 누르면 입력되어있는 이메일 저장
        binding.loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String inputEmail = binding.emailEdt.getText().toString();

                ContextUtil.setEmail(mContext, inputEmail);
            }
        });

    }

    @Override
    public void setValues() {

//        이 화면을 키면 저장된 이메일값을 email에 입력하려고 함
        binding.emailEdt.setText(ContextUtil.getEmail(mContext));

    }
}

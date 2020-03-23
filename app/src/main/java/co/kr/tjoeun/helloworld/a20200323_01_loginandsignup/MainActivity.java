package co.kr.tjoeun.helloworld.a20200323_01_loginandsignup;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.CompoundButton;

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

//        체크박스에 변화가 있을때 마다 체크여부를 저장.
        binding.idCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

//                Log.d("체크여부", isChecked+"");
//                ContextUtil을 이용해서 체크 여부를 저장
                ContextUtil.setIdCheck(mContext, isChecked);

            }
        });



//        로그인 버튼을 누르면 =>id 저장이 체크되어있다면 입력되어있는 이메일 저장
        binding.loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

//                체크박스에 체크가 되어있나?
                if (binding.idCheckBox.isChecked()){

//                    체크가 되어있는 상황
                    String inputEmail = binding.emailEdt.getText().toString();

                    ContextUtil.setEmail(mContext, inputEmail);

                }else{
//                    체크가 안된 상황
                    ContextUtil.setEmail(mContext, "");
                }


            }
        });

    }

    @Override
    public void setValues() {

//        이 화면을 키면 저장된 이메일값을 email에 입력하려고 함
        binding.emailEdt.setText(ContextUtil.getEmail(mContext));
        binding.idCheckBox.setChecked(ContextUtil.isIdCheck(mContext));

    }
}

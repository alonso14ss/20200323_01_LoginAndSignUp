package co.kr.tjoeun.helloworld.a20200323_01_loginandsignup;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import co.kr.tjoeun.helloworld.a20200323_01_loginandsignup.databinding.ActivitySignUpBinding;
import co.kr.tjoeun.helloworld.a20200323_01_loginandsignup.datas.User;
import co.kr.tjoeun.helloworld.a20200323_01_loginandsignup.utils.ServerUtil;

public class SignUpActivity extends BaseActivity {

    ActivitySignUpBinding binding = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this,R.layout.activity_sign_up);
        setupEvents();
        setValues();
    }

    @Override
    public void setupEvents() {

        binding.signupBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

//                ID/비번/이름/폰번을 따와서 => 서버로 전달
//                회원가입 API에 전달

                String inputId = binding.idEdt.getText().toString();
                String inputPw = binding.pwEdt.getText().toString();
                String inputName = binding.nameEdt.getText().toString();
                String inputPhone = binding.phoneEdt.getText().toString();

                ServerUtil.putRequestSignUp(mContext, inputId, inputPw, inputName, inputPhone, new ServerUtil.JsonResponseHandler() {
                    @Override
                    public void onResponse(JSONObject json) {
                        Log.d("회원가입",json.toString());

//                        응용 문제 : 회원가입에 성공? "이름" 님 환영합니다! 토스트 출력
//                        실패? 서버에서 주는 message를 그대로 토스트로 출력

                        try {
                            final String message = json.getString("message");
                            int code = json.getInt("code");

                            if (code==200){
                                JSONObject data = json.getJSONObject("data");
                                JSONObject user = data.getJSONObject("user");
//                                final String name = user.getString("name");
                                final User signUpUser = User.getUserFromJson(user);

                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        Toast.makeText(mContext,String.format(("%s님! 환영합니다!"), signUpUser.getName()),Toast.LENGTH_LONG).show();
                                    }
                                });
                            }
                            else{
                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        Toast.makeText(mContext,message,Toast.LENGTH_SHORT).show();
                                    }
                                });
                            }


                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                });


            }
        });

    }

    @Override
    public void setValues() {

    }
}

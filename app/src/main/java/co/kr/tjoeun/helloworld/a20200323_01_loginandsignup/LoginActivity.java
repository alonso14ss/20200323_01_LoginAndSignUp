package co.kr.tjoeun.helloworld.a20200323_01_loginandsignup;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.Toast;

import androidx.databinding.DataBindingUtil;

import org.json.JSONException;
import org.json.JSONObject;

import co.kr.tjoeun.helloworld.a20200323_01_loginandsignup.databinding.ActivityLoginBinding;
import co.kr.tjoeun.helloworld.a20200323_01_loginandsignup.datas.User;
import co.kr.tjoeun.helloworld.a20200323_01_loginandsignup.utils.ContextUtil;
import co.kr.tjoeun.helloworld.a20200323_01_loginandsignup.utils.ServerUtil;

public class LoginActivity extends BaseActivity {

    ActivityLoginBinding binding = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this,R.layout.activity_login);

        setupEvents();
        setValues();
    }

    @Override
    public void setupEvents() {

        binding.signupBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, SignUpActivity.class);
                startActivity(intent);
            }
        });

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
                String inputEmail = binding.emailEdt.getText().toString();
                String inputPw =  binding.pwEdt.getText().toString();

                ServerUtil.postRequestLogin(mContext, inputEmail, inputPw, new ServerUtil.JsonResponseHandler() {
                    @Override
                    public void onResponse(JSONObject json) {
//                        응답실행코드는 비동기 처리가 반드시 필요함
//                        비동기 : 다른 할 일들을 하다가  완료되면 별도로 실행해주자
//                        okHttp : 비동기처리를 자동으로 지원 => 별도 쓰레드가 알아서 진행
//                        => 이 onResponse는 다른 스레드가 돌리고있다
//                        UI동작은 메인스레드가 전용으로 처리함
//                        => 다른 스레드가 UI를 건드리면 앱이 터짐

                        Log.d("제이슨 내용-메인에서", json.toString());

                        try {
                            final String message = json.getString("message");
                            Log.d("서버가 주는 메세지", message);

                            int code = json.getInt("code");
                            Log.d("서버가 주는 코드",code+"");

                            if (code == 200){
//                                해당 기능이 성공적으로 동작
//                                로그인 성공!
                                JSONObject data = json.getJSONObject("data");
                                JSONObject user = data.getJSONObject("user");
                                final String token = data.getString("token");
//                                로그인한 사람의 이름을 토스트로 띄워보자
//                                final String name = data.getString("name");
//                                final String phone = data.getString("phone");

                                final User loginUser = User.getUserFromJson(user);

                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        Toast.makeText(mContext,String.format(("%s/%s"), loginUser.getName(),loginUser.getPhone()),Toast.LENGTH_LONG).show();

//                                        따온 토큰을 SharedPreferences에 저장 => 로그인에 성공했다 + 내가 누군지 기록
                                        ContextUtil.setUserToken(mContext, token);

//                                        메인화면으로 진입 => 저장된 토큰을 이용할 예정
                                        Intent intent = new Intent(mContext, MainActivity.class);
                                        startActivity(intent);
                                    }
                                });

                            }else{
//                                뭔가 문제가 있음

//                                toast를 띄우는데 앱이 뒤짐!
//                                조치 : UIThread 안에서 토스트를 띄우도록

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

//        이 화면을 키면 저장된 이메일값을 email에 입력하려고 함
        binding.emailEdt.setText(ContextUtil.getEmail(mContext));
        binding.idCheckBox.setChecked(ContextUtil.isIdCheck(mContext));

    }
}

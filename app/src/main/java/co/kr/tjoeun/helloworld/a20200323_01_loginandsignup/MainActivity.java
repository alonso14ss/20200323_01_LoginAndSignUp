package co.kr.tjoeun.helloworld.a20200323_01_loginandsignup;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import org.json.JSONException;
import org.json.JSONObject;

import co.kr.tjoeun.helloworld.a20200323_01_loginandsignup.databinding.ActivityMainBinding;
import co.kr.tjoeun.helloworld.a20200323_01_loginandsignup.datas.User;
import co.kr.tjoeun.helloworld.a20200323_01_loginandsignup.utils.ContextUtil;
import co.kr.tjoeun.helloworld.a20200323_01_loginandsignup.utils.ServerUtil;

public class MainActivity extends BaseActivity {

    ActivityMainBinding binding = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this,R.layout.activity_main);
        setValues();
        setupEvents();
    }

    @Override
    public void setupEvents() {

        binding.goToBoardBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, BoardListAcitivity.class);
                startActivity(intent);
            }
        });

        binding.logoutBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

//                얼럿으로 정말 로그아웃 하시겠습니까?
                AlertDialog.Builder alert = new AlertDialog.Builder(mContext);
                alert.setMessage("정말 로그아웃 하시겠습니까?");
                alert.setPositiveButton("확인", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
//                        로그아웃 처리
//                        저장된 토큰을 => 날려버림
                        ContextUtil.setUserToken(mContext,"");
//                        로그인 액티비티를 띄우자
                        Intent intent = new Intent(mContext,LoginActivity.class);
                        startActivity(intent);
//                        메인 액티비티 종료
                        finish();
                    }
                });
                alert.setNegativeButton("취소",null);
                alert.show();

            }
        });

    }

    @Override
    public void setValues() {

//        저장된 토큰을 확인해서 => 그 토큰으로 내 정보를 서버에서 다시 불러올것 - 조회 (get)
        ServerUtil.getRequestMyInfo(mContext, new ServerUtil.JsonResponseHandler() {
            @Override
            public void onResponse(JSONObject json) {
                try {
                    int code = json.getInt("code");
                    if(code == 200){
                        JSONObject data = json.getJSONObject("data");
                        JSONObject user = data.getJSONObject("user");

                        final User myInfo = User.getUserFromJson(user);

                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                binding.nameTxt.setText(myInfo.getName());
                                binding.memoTxt.setText(myInfo.getMemo());
                                binding.phoneTxt.setText(myInfo.getPhone());
                            }
                        });
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });



    }
}

package co.kr.tjoeun.helloworld.a20200323_01_loginandsignup;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.os.Bundle;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import co.kr.tjoeun.helloworld.a20200323_01_loginandsignup.databinding.ActivityBoardListAcitivityBinding;
import co.kr.tjoeun.helloworld.a20200323_01_loginandsignup.datas.Black;
import co.kr.tjoeun.helloworld.a20200323_01_loginandsignup.utils.ServerUtil;

public class BoardListAcitivity extends BaseActivity {

    ActivityBoardListAcitivityBinding binding = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_board_list_acitivity);
        setupEvents();
        setValues();
    }

    @Override
    public void setupEvents() {

    }

    @Override
    public void setValues() {

        ServerUtil.getRequestBlackList(mContext, new ServerUtil.JsonResponseHandler() {
            @Override
            public void onResponse(JSONObject json) {

                Log.d("게시글 목록", json.toString());

                try {
                    int code = json.getInt("code");

                    if(code ==200){
                        JSONObject data = json.getJSONObject("data");
                        JSONArray blackLists = data.getJSONArray("black_lists");

                        for (int i=0; i<blackLists.length();i++){
                            JSONObject  bl = blackLists.getJSONObject(i);
                            Black blackPost = Black.getBlackFromJson(bl);
                            Log.d("블랙신고제목", blackPost.getTitle());
                        }
                    }
                    else{

                    }


                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        });
    }
}

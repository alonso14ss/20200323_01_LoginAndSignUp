package co.kr.tjoeun.helloworld.a20200323_01_loginandsignup.utils;

import android.content.Context;
import android.content.SharedPreferences;

public class ContextUtil {

//    메모장파일 처럼 데이터를 저장할 공간의 이름으로 쓸 변수
    private static final String prefName = "myPref";

//    항목명도 자동완성 지원할 수 있도록 미리 변수화
    private static final String EMAIL = "EMAIL";
    private static final String ID_CHECK = "ID_CHECK";
    private static final String USER_TOKEN = "USER_TOKEN";

//    static : math.random(), String.format() : 누가? 하는지 중요하지 않음
//    static : 프로그램 시작시 제일 먼저 별도로 생성
//    static method : 멤버변수 사용시 => 멤버 변수도 static이어야 함

//    해당 항목의 값을 저장(setter) / 조회(getter)하는 메소드 두개

//    setter
     public static void setEmail(Context context, String email){

//         메모장을 이용해서 값을 기록 => 메모장 파일부터 열어야 함
//         메모장은 Context 이용해서 열어야 함 => Context변수도 파라미터로 필요

//         메모장을 열떄 1) 어떤 메모장? 2) 어떤 모드? - Context.MODE_PRIVATE
         SharedPreferences pref = context.getSharedPreferences(prefName, Context.MODE_PRIVATE);

//         열린 메모장에 항목(key)/값(value) 저장
         pref.edit().putString(EMAIL, email).apply();
     }

//     getter
    public static String getEmail(Context context){

//         메모장을 열어야 뭐라고 적혀있는지 확인 가능
        SharedPreferences pref = context.getSharedPreferences(prefName, Context.MODE_PRIVATE);

//        이메일 항목에 적혀있는 값을 확인해서 바로 리넡 처리
//        저장된 값이 없다면 빈칸으로
         return pref.getString(EMAIL, "");
    }

    public static void setIdCheck(Context context, boolean isCheck){

         SharedPreferences pref = context.getSharedPreferences(prefName, Context.MODE_PRIVATE);
         pref.edit().putBoolean(ID_CHECK, isCheck).apply();

    }

    public static boolean isIdCheck(Context context){
         SharedPreferences pref = context.getSharedPreferences(prefName, Context.MODE_PRIVATE);
         return pref.getBoolean(ID_CHECK, true);
    }

    public static void setUserToken(Context context, String token){
         SharedPreferences pref = context.getSharedPreferences(prefName, Context.MODE_PRIVATE);
         pref.edit().putString(USER_TOKEN, token).apply();
    }

    public static  String getUserToken(Context context){
        SharedPreferences pref = context.getSharedPreferences(prefName, Context.MODE_PRIVATE);
        return pref.getString(USER_TOKEN, "");

    }

}

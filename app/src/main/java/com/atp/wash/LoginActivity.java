package com.atp.wash;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

public class LoginActivity extends Activity implements OnClickListener{

	private String userNameServer;
	private String passwordServer;
	private EditText mEdtUserName;
	private EditText mEdtPassword;
	private Button mBtnOk;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.login_activity);
		
		initView();
	}

	private void initView() {
		mEdtUserName = (EditText) findViewById(R.id.edt_name);
		mEdtPassword = (EditText) findViewById(R.id.edt_pass);
		mBtnOk = (Button) findViewById(R.id.btn_login_ok);
		mBtnOk.setOnClickListener(this);
	}
	
	private boolean callWSGetUser(){
		userNameServer = "khiem";
		passwordServer = "a";
		return true;
	}
	
	private boolean compareWithServer(){
		if(mEdtUserName.getText().toString().equals(userNameServer) && 
				mEdtPassword.getText().toString().equals(passwordServer)){
			return true;
		}
		return false;
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.btn_login_ok:
			doLogin();
			break;

		default:
			break;
		}
	}
	
	private void doLogin(){
		if(callWSGetUser()){
			if(compareWithServer()){
				loginSuccessful();
			}else{
				loginWrong();
			}
		}else{
			loginWSFail();
		}
	}
	
	private void loginSuccessful(){
		GeneralUtil.saveString(this, Contanst.Shareference.USER_NAME, userNameServer);
		GeneralUtil.saveBoolean(this, Contanst.Shareference.IS_LOGIN, true);
		Intent intent = new Intent(this, MainActivity.class);
		startActivity(intent);
		GeneralUtil.beginStartActivityAnimation(this);
		finish();
	}
	
	private void loginWSFail(){
		
	}
	
	private void loginWrong(){
		
	}
}

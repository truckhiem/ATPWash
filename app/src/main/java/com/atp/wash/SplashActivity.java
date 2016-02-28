package com.atp.wash;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;


public class SplashActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.splash_activity);
		
		CustomCircle circle = (CustomCircle) findViewById(R.id.circle);

		CircleAngleAnimation animation = new CircleAngleAnimation(circle, 360);
		animation.setDuration(3000);
		animation.setAnimationListener(new AnimationListener() {
			
			@Override
			public void onAnimationStart(Animation arg0) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void onAnimationRepeat(Animation arg0) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void onAnimationEnd(Animation arg0) {
				// TODO Auto-generated method stub
				redirect();
			}
		});
		circle.startAnimation(animation);
	}
	
	private void redirect(){
		Intent intent = null;
		if(checkLogin()){
			intent = new Intent(this, MainActivity.class);
		}else{
			intent = new Intent(this, LoginActivity.class);
		}
		startActivity(intent);
		GeneralUtil.beginStartActivityAnimation(this);
		finish();
				
	}
	
	private boolean checkLogin(){
		if(GeneralUtil.getBoolean(this, Contanst.Shareference.IS_LOGIN, false)){
			return true;
		}
		return false;
	}
}

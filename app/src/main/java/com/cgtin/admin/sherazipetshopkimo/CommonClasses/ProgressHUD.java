package com.cgtin.admin.sherazipetshopkimo.CommonClasses;

import android.app.Dialog;
import android.content.Context;
import android.os.Build;
import android.view.Gravity;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ProgressBar;

import com.cgtin.admin.sherazipetshopkimo.R;


public class ProgressHUD extends Dialog {

	static Context contexts;


	public ProgressHUD(Context context) {
		super(context);
		contexts=context;


	}

	public ProgressHUD(Context context, int theme) {
		super(context, theme);
	}


	public void onWindowFocusChanged(boolean hasFocus){


		ProgressBar progressBar= (ProgressBar) findViewById(R.id.progressBar);
		progressBar.setIndeterminate(true);


		if (progressBar.getIndeterminateDrawable() != null) {

			if (Build.VERSION.SDK_INT >= 23) {
				progressBar.getIndeterminateDrawable().setColorFilter(contexts.getColor(R.color.colorAccent), android.graphics.PorterDuff.Mode.SRC_IN);
			} else {
				progressBar.getIndeterminateDrawable().setColorFilter(contexts.getResources().getColor(R.color.colorAccent), android.graphics.PorterDuff.Mode.SRC_IN);
			}

		}
	/*	ImageView imageView = (ImageView) findViewById(R.id.progressBar);
        AnimationDrawable spinner = (AnimationDrawable) imageView.getBackground();
        spinner.start();*//*
		PlayGifView pGif = (PlayGifView) findViewById(R.id.progressBar);
		pGif.setImageResource(R.drawable.progress);*/
    }
	
	public void setMessage(CharSequence message) {
		if(message != null && message.length() > 0) {
			//findViewById(R.id.message).setVisibility(View.VISIBLE);
			//TextView txt = (TextView)findViewById(R.id.message);
			//txt.setText(message);
			////txt.invalidate();
		}
	}
	
	public static ProgressHUD show(Context context, CharSequence message, boolean indeterminate
			) {
		ProgressHUD dialog = new ProgressHUD(context,R.style.ProgressHUD);
		dialog.setTitle("");
		dialog.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
		dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
		dialog.setContentView(R.layout.progress_hud);

		contexts=context;

//		if(message == null || message.length() == 0) {
//			dialog.findViewById(R.id.message).setVisibility(View.GONE);
//		} else {
//			TextView txt = (TextView)dialog.findViewById(R.id.message);
//			txt.setText(message);
//		}

		dialog.getWindow().getAttributes().gravity= Gravity.CENTER;
		WindowManager.LayoutParams lp = dialog.getWindow().getAttributes();

		lp.dimAmount=.9f;
		dialog.getWindow().setAttributes(lp); 
		//dialog.getWindow().addFlags(WindowManager.LayoutParams.FLAG_BLUR_BEHIND);
		dialog.show();
		dialog.setCanceledOnTouchOutside(false);

		dialog.setCancelable(false);
		return dialog;
	}	
}

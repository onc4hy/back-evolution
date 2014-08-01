package com.houyalab.android.backevolution.ui;

import android.app.AlertDialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.RadioButton;
import android.widget.TextView;

import com.houyalab.android.backevolution.R;
import com.houyalab.android.backevolution.util.HelperUtil;

public class ActivityBookVolumeDetail extends BaseActivity implements View.OnClickListener {

	private String mBookTitle;
	private String mVolumenTitle;
	private String mVolumenContent;
	private float mDefaultTextSize;
	private float mTextSize;
	private TextView mTvBookTitle;
	private TextView mTvVolumeTitle;
	private TextView mTvVolumeContent;
	private RadioButton mRbTextSizeDefault;
	private RadioButton mRbTextSizeSmaller;
	private RadioButton mRbTextSizeGreater;
	
	public ActivityBookVolumeDetail() {
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.w_book_volume_detail);
		
		mBookTitle = getIntent().getExtras().getString("bookTitle");
		mVolumenTitle = getIntent().getExtras().getString("volumeTitle");
		mVolumenContent = getIntent().getExtras().getString("volumeContent");
		mTvBookTitle = (TextView)findViewById(R.id.tv_book_volume_detail_head_booktitle);
		mTvVolumeTitle = (TextView)findViewById(R.id.tv_book_volume_detail_head_title);
		mTvVolumeContent = (TextView)findViewById(R.id.tv_book_volume_detail_content);
		mTvVolumeTitle.setText(mVolumenTitle);
		mTvVolumeContent.setText(mVolumenContent);
		mRbTextSizeDefault = (RadioButton)findViewById(R.id.rb_book_volume_detail_textsize_default);
		mRbTextSizeSmaller = (RadioButton)findViewById(R.id.rb_book_volume_detail_textsize_smaller);
		mRbTextSizeGreater = (RadioButton)findViewById(R.id.rb_book_volume_detail_textsize_greater);
		mRbTextSizeDefault.setOnClickListener(this);
		mRbTextSizeSmaller.setOnClickListener(this);
		mRbTextSizeGreater.setOnClickListener(this);
		
		mTextSize = HelperUtil.DEFAULT_TEXT_SIZE;
		mDefaultTextSize = HelperUtil.DEFAULT_TEXT_SIZE;
		
		mTvBookTitle = (TextView) findViewById(R.id.tv_book_volume_detail_head_booktitle);
		mTvBookTitle.setText(mBookTitle);
		
	}

	@Override
	public void onClick(View view) {
		if (view.getId() == R.id.rb_book_volume_detail_textsize_default) {
			mTextSize = mDefaultTextSize;
			mTvVolumeContent.setTextSize(TypedValue.COMPLEX_UNIT_SP,mTextSize);
		} else if (view.getId() == R.id.rb_book_volume_detail_textsize_greater) {
			mTextSize = mTextSize + 2;
			mTvVolumeContent.setTextSize(TypedValue.COMPLEX_UNIT_SP,mTextSize);
		} else if (view.getId() == R.id.rb_book_volume_detail_textsize_smaller) {
			mTextSize = mTextSize - 1;
			mTvVolumeContent.setTextSize(TypedValue.COMPLEX_UNIT_SP,mTextSize);
		}
	}

	
}

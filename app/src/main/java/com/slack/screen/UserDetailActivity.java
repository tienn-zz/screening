package com.slack.screen;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.slack.screen.model.User;
import com.squareup.picasso.Picasso;

public class UserDetailActivity extends AppCompatActivity {

  @BindView(R.id.image) ImageView imageView;
  @BindView(R.id.username) TextView usernameView;
  @BindView(R.id.real_name) TextView realNameView;
  @BindView(R.id.title) TextView titleView;
  @BindView(R.id.email) TextView emailView;
  @BindView(R.id.phone) TextView phoneView;

  @Override protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.user_detail);
    ButterKnife.bind(this);

    User user = (User) getIntent().getSerializableExtra("user");
    Picasso.with(this).load(user.profile.image192).into(imageView);
    usernameView.setText(user.name);
    realNameView.setText(user.profile.realName);
    titleView.setText(user.profile.title);
    emailView.setText(user.profile.email);
    phoneView.setText(user.profile.phone);
  }
}

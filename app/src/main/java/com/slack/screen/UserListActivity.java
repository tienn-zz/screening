package com.slack.screen;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import com.slack.screen.model.User;
import java.util.ArrayList;

public class UserListActivity extends Activity {

  @Override protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.user_list);

    ListView listView = (ListView) findViewById(R.id.user_list);
    final UserAdapter adapter = new UserAdapter(this, new ArrayList<User>());
    listView.setAdapter(adapter);
    listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
      @Override public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Intent intent = new Intent(UserListActivity.this, UserDetailActivity.class);
        intent.putExtra("user", adapter.getItem(position));
        startActivity(intent);
      }
    });

    UserFetcher.fetchAndUpdate(this, adapter);
  }
}

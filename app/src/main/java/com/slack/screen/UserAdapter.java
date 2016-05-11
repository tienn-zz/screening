package com.slack.screen;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import com.slack.screen.model.User;
import java.util.List;

public class UserAdapter extends ArrayAdapter<User> {

  private List<User> users;

  public UserAdapter(Context context, List<User> users) {
    super(context, android.R.layout.simple_list_item_1, users);
    this.users = users;
  }

  public void update(List<User> users) {
    this.users.clear();
    for (User user : users) {
      if (!user.deleted) this.users.add(user);
    }
    notifyDataSetChanged();
  }

  @Override public View getView(int position, View convertView, ViewGroup parent) {
    super.getView(position, convertView, parent);
    if (convertView == null) {
      convertView =
          LayoutInflater.from(getContext()).inflate(android.R.layout.simple_list_item_1, null);
    }
    ((TextView) convertView).setText(users.get(position).name);
    return convertView;
  }
}

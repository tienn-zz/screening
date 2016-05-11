package com.slack.screen.model;

import com.google.gson.annotations.SerializedName;
import java.io.Serializable;

public class User implements Serializable {
  public String id;
  public String name;
  public boolean deleted;
  public String color;
  public Profile profile;
  @SerializedName("is_admin") public String isAdmin;
  @SerializedName("is_owner") public String isOwner;
}

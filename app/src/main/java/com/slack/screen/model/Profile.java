package com.slack.screen.model;

import com.google.gson.annotations.SerializedName;
import java.io.Serializable;

public class Profile implements Serializable {
  @SerializedName("first_name") public String firstName;
  @SerializedName("last_name") public String lastName;
  @SerializedName("real_name") public String realName;
  public String email;
  public String skype;
  public String phone;
  public String title;
  @SerializedName("image_24") public String image24;
  @SerializedName("image_32") public String image32;
  @SerializedName("image_48") public String image48;
  @SerializedName("image_72") public String image72;
  @SerializedName("image_192") public String image192;
  @SerializedName("image_512") public String image512;
}

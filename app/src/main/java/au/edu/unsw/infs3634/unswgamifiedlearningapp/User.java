package au.edu.unsw.infs3634.unswgamifiedlearningapp;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

@Entity(tableName = "user_table")
public class User {

    public User(@NonNull String zid, String name, String password, String email, int imageId) {
        this.zid = zid;
        this.name = name;
        this.password = password;
        this.email = email;
        this.imageId = imageId;
    }


    @PrimaryKey
    @NonNull
    @SerializedName("zid")
    @Expose
    private String zid;

    @NonNull
    @SerializedName("name")
    @Expose
    private String name;

    @SerializedName("password")
    @Expose
    @NonNull
    private String password;

    @SerializedName("email")
    @Expose
    @NonNull
    private String email;

    @NonNull
    @SerializedName("imageId")
    @Expose
    private int imageId;

    public String getZid() {
        return zid;
    }

    public void setZid(String zid) {
        this.zid = zid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getImageId() {
        return imageId;
    }

    public void setImageId(int imageId) {
        this.imageId = imageId;
    }


//    public static ArrayList<User> getUsers(){
//        ArrayList<User> users = new ArrayList<>();
//        users.add(new User("z1111111","Tony","1111111","z1111111@unsw.edu.au,",R.drawable.ic_launcher_foreground));
//        users.add(new User("z2222222","Cindy","2222222","z2222222@unsw.edu.au,",R.drawable.ic_launcher_foreground));
//
//        return users;
//
//    }


}

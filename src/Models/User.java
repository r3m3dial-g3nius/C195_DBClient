package Models;

public class User {

    private int userID;
    private String userName;
    private String password;

    public User(int userId, String userName, String password)
    {
        this.userID = userId;
        this.userName = userName;
        this.password = password;
    }

    public int getUserID() {
        return userID;
    }

    public void setUserID(int userID) {
        this.userID = userID;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }


    //   ------------------------------------   passwords....private?   ----------------------------------
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

}

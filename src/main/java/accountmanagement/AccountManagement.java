package accountmanagement;

import account.UserInfo;
import connectdb.JDBCUtils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class AccountManagement {

    private static Map<String, UserInformation> account = new HashMap<>();

    private static final Exception LoginValidationException = new Exception("Incorrect username of password. Please try again!");
    private static final Exception UsernameHasAlreadyTakenException = new Exception("Username has already taken!");
    private static final Exception WeekPasswordException = new Exception("Password week");
    private static final Exception EmptyFieldException = new Exception("Please fill out this field!");

    private static Connection connection;

    static {
        try {
            connection = JDBCUtils.getConn();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void ConnectToDatabase() throws SQLException {
        try {
            String query = "SELECT * FROM users;";
            ResultSet result = connection.createStatement().executeQuery(query);

            while (result.next()) {
                String username     = result.getString("username");
                String password     = result.getString("password");
                String firstname    = result.getString("firstname");
                String lastname     = result.getString("lastname");
                String email        = result.getString("email");
                String phone        = result.getString("phone");
                String address      = result.getString("address");
                account.put(username, new UserInformation(username, password
                        , firstname, lastname
                        , email, phone, address));
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
//        connection.close();
    }

    public static UserInformation getUserInformation(String username) {
        return account.get(username);
    }

    public static boolean CheckLogin(String username, String password) throws Exception {
        if (!account.containsKey(username))
            throw LoginValidationException;
        if (!Objects.equals(AccountManagement.getUserInformation(username).getPassword(), password))
            throw LoginValidationException;
        return true;
    }

    public static boolean AddAccount(String username, String password,
                                     String firstname, String lastname,
                                     String email, String phone, String address) throws Exception
    {
        if (account.containsKey(username))
            throw UsernameHasAlreadyTakenException;

        String query = "INSERT INTO users (username, password, firstname, lastname, email, phone, address) VALUES (?, ?, ?, ?, ?, ?, ?);";

        PreparedStatement prep = connection.prepareStatement(query);

        prep.setString(1, username);
        prep.setString(2, password);
        prep.setString(3, firstname);
        prep.setString(4, lastname);
        prep.setString(5, email);
        prep.setString(6, phone);
        prep.setString(7, address);

        prep.executeUpdate();

        account.put(username, new UserInformation(username, password, firstname, lastname, email, phone, address));
        return true;
    }

    public static boolean AddAccount(UserInformation user) throws Exception {
        if (account.containsKey(user.getUsername()))
            throw UsernameHasAlreadyTakenException;

        // check strength password
        // check format email
        // ...

        try {
            String query = "INSERT INTO users (username, password, firstname, lastname, email, phone, address) VALUES (?, ?, ?, ?, ?, ?, ?);";

            PreparedStatement prep = connection.prepareStatement(query);

            prep.setString(1, user.getUsername());
            prep.setString(2, user.getPassword());
            prep.setString(3, user.getFirstname());
            prep.setString(4, user.getLastname());
            prep.setString(5, user.getEmail());
            prep.setString(6, user.getPhone());
            prep.setString(7, user.getAddress());

            prep.executeUpdate();

            account.put(user.getUsername(), user);

            System.out.println("Accept new account!!!");
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return true;
    }

    public static int size() {
        return account.size();
    }

    private static boolean checkFormationEmail(String email) {
        return false;
    }
}

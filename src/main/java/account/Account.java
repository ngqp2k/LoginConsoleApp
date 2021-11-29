package account;

import connectdb.JDBCUtils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

public class Account {
    private Map<String, UserInfo> account;

    public Account () {
        account = new HashMap<>();
    }

    public Account (Connection conn) {
        account = new HashMap<>();
        try {
            String query = "SELECT * FROM users;";
            ResultSet result = conn.createStatement().executeQuery(query);

            while (result.next()) {
                String username     = result.getString("username");
                String password     = result.getString("password");
                String firstname    = result.getString("firstname");
                String lastname     = result.getString("lastname");
                String email        = result.getString("email");
                String phone        = result.getString("phone");
                String address      = result.getString("address");
                account.put(username, new UserInfo(username, password
                        , firstname, lastname
                        , email, phone, address));
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    public Map<String, UserInfo> getAccount(Connection conn) {
        return account;
    }

    private boolean checkUsername(UserInfo user) {
        return !account.containsKey(user.getUsername());
    }

    public void addAccount(Connection conn, UserInfo newUser) {
        if (checkUsername(newUser)) {
            try {
                String query = "INSERT INTO users (username, password, firstname, lastname, email, phone, address) VALUES (?, ?, ?, ?, ?, ?, ?);";

                PreparedStatement prep = conn.prepareStatement(query);

                prep.setString(1, newUser.getUsername());
                prep.setString(2, newUser.getPassword());
                prep.setString(3, newUser.getFirstname());
                prep.setString(4, newUser.getLastname());
                prep.setString(5, newUser.getEmail());
                prep.setString(6, newUser.getPhone());
                prep.setString(7, newUser.getAddress());

                prep.executeUpdate();

                account.put(newUser.getUsername(),
                        new UserInfo(newUser.getUsername(), newUser.getPassword(),
                                     newUser.getFirstname(), newUser.getLastname(),
                                     newUser.getEmail(), newUser.getPhone(), newUser.getAddress()));

                System.out.println("Accept username!!!");
            } catch (Exception e) {
                System.out.println("Can't connecting!");
                e.printStackTrace();
            }
        }
        else {
            // already exist
            System.out.println("Username has already exits! Please try again!");
        }

    }

    public void printAccountToConsole() {
        for (Map.Entry<String, UserInfo> entry: account.entrySet()) {
            System.out.println(entry.getKey() + " --> " + entry.getValue());
        }
    }

    public int size() {
        return account.size();
    }

}

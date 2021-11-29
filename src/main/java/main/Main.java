package main;

import account.Account;
import account.UserInfo;
import connectdb.JDBCUtils;

import java.sql.*;
import java.util.HashMap;
import java.util.Map;
import java.util.Queue;
import java.util.Set;

public class Main {
    public static void main(String[] args) throws SQLException {
        Connection conn = JDBCUtils.getConn();

        Account acc = new Account(conn);


        UserInfo u1 = new UserInfo("acc04", "p02"
                , "Nghĩa", "Nguyễn"
                , "email03@yahoo.com.vn", "113"
                , "Hải Phòng");

//        System.out.println(u1);
        acc.printAccountToConsole();

        acc.addAccount(conn, u1);

        acc.printAccountToConsole();

        conn.close();
    }
}

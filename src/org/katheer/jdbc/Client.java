package org.katheer.jdbc;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class Client {
    public static void main(String[] args) throws Exception {
        Class.forName("oracle.jdbc.OracleDriver");
        Connection connection = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521" +
                ":orcl", "system", "katheer");

        /*
        //BLOB Insert
        //1.Prepare a file object
        File in_image = new File("F:/input.png");

        //2.Convert image to InputStream
        FileInputStream in_fis = new FileInputStream(in_image);

        //3.Create a prepared statement with insert query
        PreparedStatement insert = connection.prepareStatement("INSERT INTO emp1 VALUES(?, ?)");

        //4.Set values to normal positional parameter
        insert.setInt(1, 111);

        //5.Set values to BLOB type positional parameter
        insert.setBinaryStream(2, in_fis, in_image.length());

        //6.Execute query
        int insertRowCount = 0;

        try {
            insertRowCount = insert.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }

        if(insertRowCount == 1) {
            System.out.println("Image inserted successfully");
        } else {
            System.out.println("Image insert failed");
        }

         */

        //BLOB Read
        //1.Create a Prepared Statement
        PreparedStatement get = connection.prepareStatement("SELECT * FROM emp1 WHERE eid = ?");

        //2.Set data to positional parameters
        get.setInt(1, 111);

        //3.Execute query
        ResultSet rs = get.executeQuery();

        //4.Fetch data from ResultSet
        rs.next();
        InputStream out_fis = rs.getBinaryStream("eimage");

        //5.Write InputStream data to a OutputStream
        FileOutputStream out_fos = new FileOutputStream("F:/output.png");

        int data = out_fis.read();
        while (data != -1) {
            out_fos.write(data);
            data = out_fis.read();
        }
        System.out.println("Image fetched successfully");
    }
}

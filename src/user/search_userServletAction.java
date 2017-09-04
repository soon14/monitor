package user;

import org.json.JSONException;
import org.json.JSONObject;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by LJX on 2017/5/24.
 */
public class search_userServletAction extends HttpServlet {

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request, response);
    }
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
        List jsonList = new ArrayList();
        String userId = request.getParameter("user_id");
        //System.out.print(userId);
        try {
            System.out.println("1");
            Class.forName("com.mysql.jdbc.Driver");
        } catch (ClassNotFoundException classnotfoundexception) {
            classnotfoundexception.printStackTrace();
        }
        try {
            Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/test?user=ylx&password=ylx&useUnicode=true&characterEncoding=UTF-8");
            Statement statement = conn.createStatement();
            //¹¹ÔìsqlÓï¾ä
            String sql="select * from user_file where user_id like '%"+userId+"%'";
            System.out.println(sql);
            ResultSet rs = statement.executeQuery(sql);
            while (rs.next()) {
                List list = new ArrayList();
                list.add(rs.getString("user_id"));
                list.add(rs.getString("user_name"));
                list.add(rs.getString("user_gender"));
                list.add(rs.getString("user_password"));
                list.add(rs.getString("add_time"));
                list.add(rs.getString("id"));
                jsonList.add(list);
            }
            statement.close();
            conn.close();

        } catch (SQLException sqlexception) {
            sqlexception.printStackTrace();
        }

        JSONObject json=new JSONObject();
        try {
            json.put("aaData",jsonList);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        try {
            json.put("result_msg","ok");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        try {
            json.put("result_code",0);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        System.out.println(json.toString());
        response.setContentType("text/html; charset=UTF-8");
        try {
            response.getWriter().print(json);
            response.getWriter().flush();
            response.getWriter().close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

package com.khac.swp.fuj.posts;

import com.khac.swp.fuj.utils.DBUtils;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class PostDAO {

    public List<PostDTO> getAllPost(String keyword, String sortCol) {
        List<PostDTO> list = new ArrayList<PostDTO>();
        try {
            Connection con = DBUtils.getConnection();
            String sql = " select postID, postName, postImage, description, postText from Post ";
            if (keyword != null && !keyword.isEmpty()) {
                sql += " where postName like ? ";
            }

            if (sortCol != null && !sortCol.isEmpty()) {
                sql += " ORDER BY " + sortCol + " ASC ";
            }

            PreparedStatement stmt = con.prepareStatement(sql);

            if (keyword != null && !keyword.isEmpty()) {
                stmt.setString(1, "%" + keyword + "%");

            }
            ResultSet rs = stmt.executeQuery();
            if (rs != null) {
                while (rs.next()) {

                    int postid = rs.getInt("postID");
                    String postname = rs.getString("postName");
                    String postimage = rs.getString("postImage");
                    String description = rs.getString("description");
                    String posttext = rs.getString("postText");

                    PostDTO post = new PostDTO();
                    post.setId(postid);
                    post.setName(postname);
                    post.setImage(postimage);
                    post.setDescription(description);
                    post.setText(posttext);

                    list.add(post);
                }
            }

            con.close();
        } catch (SQLException ex) {
            System.out.println("Error in servlet. Details:" + ex.getMessage());
            ex.printStackTrace();
        }
        return list;
    }

    public PostDTO load(int postID) {

        String sql = "select postID, postName, postImage, description, postText from Post where postID = ?";

        try {

            Connection conn = DBUtils.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, postID);

            ResultSet rs = ps.executeQuery();
            if (rs.next()) {

                int postid = rs.getInt("postID");
                String postname = rs.getString("postName");
                String postimage = rs.getString("postImage");
                String description = rs.getString("description");
                String text = rs.getString("postText");

                PostDTO post = new PostDTO();
                post.setId(postid);
                post.setName(postname);
                post.setImage(postimage);
                post.setDescription(description);
                post.setText(text);
                return post;
            }
        } catch (SQLException ex) {
            System.out.println("Query User error!" + ex.getMessage());
            ex.printStackTrace();
        }
        return null;
    }

    public Integer insert(PostDTO post) {
        String sql = "INSERT INTO [Post] (postID, postName, postImage, description, postText) VALUES (?, ?, ?, ?, ?)";
        try {

            Connection conn = DBUtils.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql);

            ps.setInt(1, post.getId());
            ps.setString(2, post.getName());
            ps.setString(3, post.getImage());
            ps.setString(4, post.getDescription());
            ps.setString(5, post.getText());
            ps.executeUpdate();
            conn.close();
            return post.getId();
        } catch (SQLException ex) {
            System.out.println("Insert Post error!" + ex.getMessage());
            ex.printStackTrace();
        }
        return null;
    }

    public boolean update(PostDTO post) {
        String sql = "UPDATE [Post] SET postName = ?, postImage = ?, description = ?, postText WHERE postID = ? ";
        try {

            Connection conn = DBUtils.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql);

            ps.setInt(4, post.getId());
            ps.setString(1, post.getName());
            ps.setString(2, post.getImage());
            ps.setString(3, post.getDescription());
            ps.setString(5, post.getText());

            ps.executeUpdate();
            conn.close();
        } catch (SQLException ex) {
            System.out.println("Update Post error!" + ex.getMessage());
            ex.printStackTrace();
        }
        return false;
    }
    public boolean delete(int id) {
        String sql = "DELETE [Post] WHERE postID = ? ";
        try {

            Connection conn = DBUtils.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql);

            ps.setInt(1, id);

            ps.executeUpdate();
            conn.close();
            return true;
        } catch (SQLException ex) {
            System.out.println("Delete Post error!" + ex.getMessage());
            ex.printStackTrace();
        }

        return false;
    }

    public PostDTO checkPostExistByID(int postID) {

        String sql = "select postID from Post where postID = ?";

        try {

            Connection conn = DBUtils.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, postID);

            ResultSet rs = ps.executeQuery();
            if (rs.next()) {

                PostDTO post = new PostDTO();
                post.setId(rs.getInt("postID"));
                return post;
            }
        } catch (SQLException ex) {
            System.out.println("Query User error!" + ex.getMessage());
            ex.printStackTrace();
        }
        return null;
    }
    
    public static void main(String[] args) {
        PostDAO dao = new PostDAO();
        PostDTO list = dao.load(1);
            System.out.println(list);

    }
}

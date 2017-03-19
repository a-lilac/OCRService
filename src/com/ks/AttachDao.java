package com.ks;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/3/18.
 */
public class AttachDao {

    public List<Attachment> getAttachList(int num) {
        String sql = "select attach_id,attach_path from tb_attachment" +
                " where (attach_type='jpg' OR attach_type='png') and rownum<? and attach_ocr_flag=?";//" ";//" order by attach_ctime asc";
        List<Attachment> attachments = new ArrayList<Attachment>();
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            conn = BaseDaoJdbc.getConn();
            ps = conn.prepareStatement(sql);
            ps.setInt(1, num);
            ps.setString(2, "N");
            rs = ps.executeQuery();
            if (rs != null) {
                while (rs.next()) {
                    Attachment m = new Attachment();
                    m.setAttach_id(rs.getString("attach_id"));
                    m.setAttach_path(rs.getString("attach_path"));
                    attachments.add(m);
                }
                rs.close();
            }
            ps.close();
            conn.close();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            BaseDaoJdbc.closeConn(conn, ps, rs);
        }
        return attachments;
    }

    public void setAttach(String val, String attach_id) {
        String sql = "update tb_attachment set attach_ocr_flag='Y',attach_ocr=? where attach_id=?";
        Connection conn = null;
        PreparedStatement ps = null;
        int rs = 0;
        try {
            conn = BaseDaoJdbc.getConn();
            ps = conn.prepareStatement(sql);
            ps.setString(1, val);
            ps.setString(2, attach_id);
            rs = ps.executeUpdate();
            ps.close();
            conn.close();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            BaseDaoJdbc.closeConn(conn, ps);
        }
    }

}

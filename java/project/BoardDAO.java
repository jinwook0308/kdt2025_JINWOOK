package ch20;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class BoardDAO {

    /* ResultSet → DTO 매핑 */
    private BoardDTO map(ResultSet rs) throws SQLException {
        BoardDTO d = new BoardDTO();
        d.setId      (rs.getInt("id"));
        d.setTitle   (rs.getString("title"));
        d.setWriter  (rs.getString("writer"));
        d.setContent (rs.getString("content"));
        d.setRegDate (rs.getTimestamp("regdate").toLocalDateTime());
        d.setHits    (rs.getInt("hits"));
        return d;
    }

    /** 전체 목록 */
    public List<BoardDTO> selectAll() throws Exception {
        String sql = "SELECT * FROM board ORDER BY id DESC";
        try (Connection c = DBUtil.getConnection();
             PreparedStatement p = c.prepareStatement(sql);
             ResultSet rs = p.executeQuery()) {

            List<BoardDTO> list = new ArrayList<>();
            while (rs.next()) list.add(map(rs));
            return list;
        }
    }

    /** 단건 조회 */
    public BoardDTO select(int id) throws Exception {
        try (Connection c = DBUtil.getConnection();
             PreparedStatement p = c.prepareStatement(
                     "SELECT * FROM board WHERE id=?")) {
            p.setInt(1, id);
            try (ResultSet rs = p.executeQuery()) {
                return rs.next() ? map(rs) : null;
            }
        }
    }

    /** 새 글 */
    public void insert(BoardDTO d) throws Exception {
        String q = "INSERT INTO board(title,writer,content) VALUES (?,?,?)";
        try (Connection c = DBUtil.getConnection();
             PreparedStatement p = c.prepareStatement(q)) {
            p.setString(1, d.getTitle());
            p.setString(2, d.getWriter());
            p.setString(3, d.getContent());
            p.executeUpdate();
        }
    }

    /** 수정 */
    public void update(BoardDTO d) throws Exception {
        String q = "UPDATE board SET title=?, writer=?, content=? WHERE id=?";
        try (Connection c = DBUtil.getConnection();
             PreparedStatement p = c.prepareStatement(q)) {
            p.setString(1, d.getTitle());
            p.setString(2, d.getWriter());
            p.setString(3, d.getContent());
            p.setInt   (4, d.getId());
            p.executeUpdate();
        }
    }

    /** 삭제 */
    public void delete(int id) throws Exception {
        try (Connection c = DBUtil.getConnection();
             PreparedStatement p = c.prepareStatement(
                     "DELETE FROM board WHERE id=?")) {
            p.setInt(1, id);
            p.executeUpdate();
        }
    }

    /** 조회수 +1 */
    public void hitUp(int id) throws Exception {
        try (Connection c = DBUtil.getConnection();
             PreparedStatement p = c.prepareStatement(
                     "UPDATE board SET hits = hits+1 WHERE id=?")) {
            p.setInt(1, id);
            p.executeUpdate();
        }
    }
}

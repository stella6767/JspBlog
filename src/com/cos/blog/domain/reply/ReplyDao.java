package com.cos.blog.domain.reply;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

import com.cos.blog.config.DB;
import com.cos.blog.domain.reply.dto.SaveReqDto;
import com.cos.blog.domain.reply.dto.SaveRespDto;

public class ReplyDao {
	
	public int deleteById(int id) { // 회원가입
		String sql = "DELETE FROM reply WHERE id = ?";
		Connection conn = DB.getConnection();
		PreparedStatement pstmt = null;
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, id);
			int result = pstmt.executeUpdate();
			return result;
		} catch (Exception e) {
			e.printStackTrace();
		} finally { // 무조건 실행
			DB.close(conn, pstmt);
		}
		return -1;
	}
	
	
	public SaveRespDto findById(int id){
		
		StringBuffer sb = new StringBuffer();
		sb.append("SELECT r.*, u.username ");
		sb.append("FROM reply r inner join user u ");
		sb.append("on r.userid = u.id ");
		sb.append("WHERE r.id = ?");
		String sql = sb.toString();
		
		Connection conn = DB.getConnection();
		PreparedStatement pstmt = null;
		ResultSet rs  = null;
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, id);
			rs =  pstmt.executeQuery();

			// Persistence API
			if(rs.next()) { // 커서를 이동하는 함수
				SaveRespDto dto = new SaveRespDto();
				dto.setId(rs.getInt("id"));
				dto.setUserId(rs.getInt("userId"));
				dto.setBoardId(rs.getInt("boardId"));
				dto.setContent(rs.getString("content"));
				dto.setCreateDate(rs.getTimestamp("createDate"));
				dto.setUsername(rs.getString("u.username"));
				return dto;
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally { // 무조건 실행
			DB.close(conn, pstmt, rs);
		}
		return null;
	}
	
	public int save(SaveReqDto dto) { // 댓글저장
		String sql = "INSERT INTO reply(userId, boardId, content, createDate) VALUES(?,?,?, now())";
		Connection conn = DB.getConnection();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		int generateKey;
		
		try {
			pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
			pstmt.setInt(1, dto.getUserId());
			pstmt.setInt(2, dto.getBoardId());
			pstmt.setString(3, dto.getContent());
			int result = pstmt.executeUpdate();
			rs = pstmt.getGeneratedKeys(); //자동 증가 특성의 컬럼 값 검색, 댓글 아이디
			if(rs.next()) {
				generateKey = rs.getInt(1);
				System.out.println("생성된 키(ID) : "+ generateKey);
				if(result ==1) {
					return generateKey;
				}
				
			}
						
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally { // 무조건 실행
			DB.close(conn, pstmt);
		}
		return -1;
	}

}

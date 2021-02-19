package co.mydiary;

import java.beans.Statement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

public class DiaryOracleDAO implements DAO {
	
	Connection conn;
	Statement stmt;
	PreparedStatement psmt;
	ResultSet rs;
	
	@Override
	public int insert(DiaryVO vo) {
		int r = 0;
		try {
			
			// 1. connect (연결)
			conn = JdbcUtil.connect();
			// 2. statement (실행한 sql 구문)
			
			String sql = "INSERT INTO diary VALUES (?, ?)";
			PreparedStatement psmt = conn.prepareStatement(sql);
			
			// 3. execute (실행)
			
			psmt.setString(1, vo.getWdate());
			psmt.setString(2, vo.getContents());
			
			r = psmt.executeUpdate();
			System.out.println(r + " 건이 등록됨.");
			
			// 4. resultSet(select 라면 조회 결과 처리, 없으면 스킵)
			
			} catch(Exception e) {
				e.printStackTrace();
			} finally {
			// 5. close (연결해제)
				JdbcUtil.disconnect(conn);
			}
		return r;
	}

	@Override
	public void update(DiaryVO vo) {
		// 수정
		int r = 0;
		try {
			conn = JdbcUtil.connect();
			String sql = "Update diary set contents = ? where wdate = ?";
			PreparedStatement psmt = conn.prepareStatement(sql);
			
			psmt.setString(1, vo.getContents());
			psmt.setString(2, vo.getWdate());
			
			r = psmt.executeUpdate();
			System.out.println(r + " 건이 수정됨.");
			
		} catch(Exception e) {
			e.printStackTrace();
		} finally {
			JdbcUtil.disconnect(conn);
		}
		
	}

	@Override
	public int delete(String date) {
		// 삭제
		int r = 0;
		try {
			conn = JdbcUtil.connect();
			String sql = "delete from diary where wdate = ?";
			PreparedStatement psmt = conn.prepareStatement(sql);
			
			psmt.setString(1, date);
			r = psmt.executeUpdate();
		} catch(Exception e) {
			e.printStackTrace();
		} finally {
			JdbcUtil.disconnect(conn);
		}
		return r;
	}

	@Override
	public DiaryVO selectDate(String date) {
		// 날짜 조회
		try {
			
		} catch(Exception e) {
			e.printStackTrace();
		} finally {
			JdbcUtil.disconnect(conn);
		}
		
		return null;
	}

	@Override
	public ArrayList<DiaryVO> selectContent(String content) {
		// TODO 내용 검색 select * from diary where contents like '%' || ? || '%'
		ArrayList<DiaryVO> list = new ArrayList<DiaryVO>();
		DiaryVO vo = null;
		try {
			conn = JdbcUtil.connect();
			String sql = "select wdate, contents from diary where contents like '%' || ? || '%'";
			psmt = conn.prepareStatement(sql);
			rs = psmt.executeQuery();
			while(rs.next()) {
				vo = new DiaryVO();
				vo.setContents(content);
				list.add(vo);
			}
		} catch(Exception e) {
			e.printStackTrace();
		} finally {
			JdbcUtil.disconnect(conn);
		}
		return list;
	}

	@Override
	public ArrayList<DiaryVO> selectAll() {
		// 전체조회
		ArrayList<DiaryVO> list = new ArrayList<DiaryVO>();
		DiaryVO vo = null;
		try {
			conn = JdbcUtil.connect();
			String sql = "select wdate, contents from diary";
			psmt = conn.prepareStatement(sql);
			rs = psmt.executeQuery();
			while(rs.next()) {
				vo = new DiaryVO();
				vo.setWdate(rs.getString(1));
				vo.setContents(rs.getString(2));
				list.add(vo);
			}
		} catch(Exception e) {
			e.printStackTrace();
		} finally {
			JdbcUtil.disconnect(conn);
		}
		return list;
	}

}

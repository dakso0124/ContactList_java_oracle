package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.SQLTimeoutException;
import java.util.ArrayList;
import java.util.logging.Logger;

import vo.ContactInfoVO;
import vo.RelationVO;

public class ContactDAO // db access object
{
	public ContactDAO()
	{
		
	}
	
	private Connection conn = null;

	// region connection
	private Connection getConnection()
	{
		conn = null;

		String url = "jdbc:oracle:thin:@localhost:1521:xe";
		String userID = "ora_user";
		String password = "hong";

		try
		{
			conn = DriverManager.getConnection(url, userID, password);
		}
		catch (SQLException e)
		{
			e.printStackTrace();
		}
		return conn;
	}

	private void close(Connection conn, PreparedStatement pstmt, ResultSet rs)
	{
		try
		{
			if (rs != null)
				rs.close();
			if (pstmt != null)
				pstmt.close();
			if (conn != null)
				conn.close();
		}
		catch (SQLException e)
		{
			e.printStackTrace();
		}
	}
	
	private void close(Connection conn, PreparedStatement pstmt)
	{
		try
		{
			if (pstmt != null)
				pstmt.close();
			if (conn != null)
				conn.close();
		}
		catch (SQLException e)
		{
			e.printStackTrace();
		}
	}
	// endregion connection

	//region - Create Table
	// 만약 첫 실행이라 테이블이 없다면 테이블들 생성 및 제약조건 지정
	// CONTACTLIST Table 생성
	public void createContactListTable()
	{
		conn = getConnection();
		PreparedStatement pstmt = null;

		StringBuilder sql = new StringBuilder();
		try
		{
			sql.append("CREATE TABLE CONTACTLIST							");
			sql.append("(													");
			sql.append("		memberid		NUMBER						");
			sql.append("	,	name            VARCHAR2(50)    NOT NULL	");
			sql.append("	,   phone           VARCHAR2(15)				");
			sql.append("	,   address         VARCHAR2(50)    NOT NULL	");
			sql.append("	,   relation_type   VARCHAR2(3)					");
			sql.append("	,   join_date       CHAR(8)						");
			sql.append(")													");
			pstmt = conn.prepareStatement(sql.toString());
			pstmt.executeUpdate();
			System.out.println("CONTACTLIST 테이블 생성에 성공했습니다.");
		}
		catch (SQLTimeoutException e)
		{
			Logger.getGlobal().warning("TimeOut Exception 발생");
		}
		catch (SQLException e)
		{
			Logger.getGlobal().warning("CONTACTLIST 테이블 생성 도중 문제가 발생했습니다." + e.getMessage());
		}
		finally
		{
			close(conn, pstmt);

			CreateRelationTable();
		}
	}

	// RELATION 테이블 생성
	private void CreateRelationTable()
	{
		conn = getConnection();
		PreparedStatement pstmt = null;

		StringBuilder sql = new StringBuilder();

		try
		{
			sql.append("CREATE TABLE RELATION								");
			sql.append("(													");
			sql.append("		relation_type   VARCHAR2(3)					");
			sql.append("	,   relation_name   VARCHAR2(50)				");
			sql.append(")													");
			pstmt = conn.prepareStatement(sql.toString());
			pstmt.executeUpdate();
			System.out.println("RELATION 테이블 생성에 성공했습니다.");
		} 
		catch (SQLTimeoutException e)
		{
			Logger.getGlobal().warning("TimeOut Exception 발생");
		} 
		catch (SQLException e)
		{
			Logger.getGlobal().warning("RELATION 테이블 생성 도중 문제가 발생했습니다." + e.getMessage());
		} 
		finally
		{
			close(conn, pstmt);
			addContactListMemberidPK();
		}
	}
	
	// CONTACTLIST - memberid 컬럼에 PK 지정
	private void addContactListMemberidPK()
	{
		conn = getConnection();
		PreparedStatement pstmt = null;

		StringBuilder sql = new StringBuilder();

		try
		{
			conn = getConnection();
			pstmt = null;
			sql.append("ALTER TABLE CONTACTLIST											");
			sql.append("  ADD CONSTRAINT pk_contactlist_memberid PRIMARY KEY(memberid)	");
			pstmt = conn.prepareStatement(sql.toString());
			pstmt.executeUpdate();
			System.out.println("CONTACTLIST memberid column PK 지정에 성공했습니다.");
		}
		catch (SQLTimeoutException e)
		{
			Logger.getGlobal().warning("TimeOut Exception 발생");
		}
		catch (SQLException e)
		{
			Logger.getGlobal().warning("CONTACTLIST PK 지정 도중 문제가 발생했습니다." + e.getMessage());
		}
		finally
		{
			close(conn, pstmt);
			addContactListPhoneUK();
		}
	}
	
	// CONTACTLIST - phone 컬럼에 Unique지정
	private void addContactListPhoneUK()
	{
		conn = getConnection();
		PreparedStatement pstmt = null;

		StringBuilder sql = new StringBuilder();

		try
		{
			conn = getConnection();
			pstmt = null;
			sql.append("ALTER TABLE CONTACTLIST										");
			sql.append("  ADD CONSTRAINT uk_contactlist_phone UNIQUE (phone)	");
			pstmt = conn.prepareStatement(sql.toString());
			pstmt.executeUpdate();
			System.out.println("CONTACTLIST phone column UK 지정에 성공했습니다.");
		}
		catch (SQLTimeoutException e)
		{
			Logger.getGlobal().warning("TimeOut Exception 발생");
		}
		catch (SQLException e)
		{
			Logger.getGlobal().warning("CONTACTLIST UK 지정 도중 문제가 발생했습니다." + e.getMessage());
		}
		finally
		{
			close(conn, pstmt);
			addRelationTypePK();
		}
	}

	// RELATION - relation_type 컬럼에 PK 지정 
	private void addRelationTypePK()
	{
		conn = getConnection();
		PreparedStatement pstmt = null;

		StringBuilder sql = new StringBuilder();

		try
		{
			sql.append("ALTER TABLE RELATION													");
			sql.append("  ADD CONSTRAINT pk_relation_relation_type PRIMARY KEY(relation_type)	");
			pstmt = conn.prepareStatement(sql.toString());
			pstmt.executeUpdate();
			System.out.println("RELATION relation_type column PK 지정에 성공했습니다.");
		}
		catch (SQLTimeoutException e)
		{
			Logger.getGlobal().warning("TimeOut Exception 발생");
		}
		catch (SQLException e)
		{
			Logger.getGlobal().warning("RELATION PK 지정 도중 문제가 발생했습니다." + e.getMessage());
		}
		finally
		{
			close(conn, pstmt);
			addContactListTypeFK();
		}
	}

	// CONTACTLIST relation_type에 FK 지정, RELATION relation_type Reference 참조 지정
	private void addContactListTypeFK()
	{
		conn = getConnection();
		PreparedStatement pstmt = null;

		StringBuilder sql = new StringBuilder();

		try
		{
			sql.append("ALTER TABLE CONTACTLIST														");
			sql.append("  ADD CONSTRAINT fk_contactlist_relation_type FOREIGN KEY(relation_type)	");
			sql.append("REFERENCES RELATION(relation_type)											");
			pstmt = conn.prepareStatement(sql.toString());
			pstmt.executeUpdate();
			System.out.println("CONTACTLIST relation_type FK - RELATION relation_type Reference 지정에 성공했습니다.");
		}
		catch (SQLTimeoutException e)
		{
			Logger.getGlobal().warning("TimeOut Exception 발생");
		}
		catch (SQLException e)
		{
			Logger.getGlobal().warning("CONTACTLIST FK 지정 도중 문제가 발생했습니다." + e.getMessage());
		}
		finally
		{
			close(conn, pstmt);
		}
	}
	// endregion
	
	// 그룹 추가 
	public int addRelationType(String typeName)
	{
		int result = 0;
		
		conn = getConnection();
		PreparedStatement pstmt = null;

		StringBuilder sql = new StringBuilder();
		
		sql.append("INSERT INTO RELATION										");
		sql.append("values(														");
		sql.append("			LPAD( (SELECT NVL(MAX(relation_type), '000' )	"); 
		sql.append("					 FROM relation)+1, 3, 0), ?				");
		sql.append("	   )													");
		
		try
		{
			pstmt = conn.prepareStatement(sql.toString());
			pstmt.setString(1, typeName);
			result = pstmt.executeUpdate();
		}
		catch (SQLTimeoutException e)
		{
			System.out.println("TimeOut Exception");
		}
		catch (SQLException e)
		{
			Logger.getGlobal().warning("RELATION 컬럼 추가 도중 문제가 발생했습니다." + e.getMessage());
		}
		finally
		{
			close(conn, pstmt);
		}
		
		return result;
	}
	
	// 관계 테이블 가져오기
	public ArrayList<RelationVO> getRelationData()
	{
		ArrayList<RelationVO> result = new ArrayList<RelationVO>();
		
		conn = getConnection();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		StringBuilder sql = new StringBuilder();
		
		sql.append("SELECT relation_type, relation_name		");
		sql.append("  FROM RELATION							");
		sql.append(" ORDER BY relation_type					");
		
		try
		{
			pstmt = conn.prepareStatement(sql.toString());
			
			rs = pstmt.executeQuery();
			
			while(rs.next())
			{
				RelationVO relation = new RelationVO(rs.getString("relation_type"), rs.getString("relation_name"));
				
				result.add(relation);
			}
		}
		catch(SQLException e)
		{
			Logger.getGlobal().warning("관계 테이블을 가져오던 도중 문제가 발생하였습니다." + e.getMessage());
		}
		finally
		{
			close(conn, pstmt, rs);
		}
				
		return result;
	}

	// 연락처 전체 보기
	public ArrayList<ContactInfoVO> showAll()
	{
		ArrayList<ContactInfoVO> result = new ArrayList<ContactInfoVO>();
		
		conn = getConnection();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		StringBuilder sql = new StringBuilder();
		
		sql.append("SELECT c.memberid							");
		sql.append("     , c.name								");
		sql.append("     , c.phone								");
		sql.append("	 , c.address							");
		sql.append("	 , c.join_date							");
		sql.append("	 , c.relation_type						");
		sql.append("	 , r.relation_name						");
		sql.append("  FROM contactlist c						");
		sql.append("	 , relation r							");
		sql.append(" WHERE c.relation_type = r.relation_type	");
		sql.append(" ORDER BY c.memberid						");
		
		try
		{
			pstmt = conn.prepareStatement(sql.toString());
			
			rs = pstmt.executeQuery();
			
			while(rs.next())
			{
				ContactInfoVO contact = new ContactInfoVO(rs.getInt("memberid"), rs.getString("name"), rs.getString("phone")
														, rs.getString("address"), rs.getString("join_date")
														, rs.getString("relation_type"), rs.getString("relation_name"));
				
				result.add(contact);
			}
		}
		catch(SQLException e)
		{
			Logger.getGlobal().warning("연락처 검색중 문제가 발생하였습니다." + e.getMessage());
		}
		finally
		{
			close(conn,pstmt, rs);
		}
		
		return result;
	}
	
	// 연락처 이름 검색
	public ArrayList<ContactInfoVO> searchContact(String name)
	{
		ArrayList<ContactInfoVO> result = new ArrayList<ContactInfoVO>();
		
		conn = getConnection();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		StringBuilder sql = new StringBuilder();

		sql.append("SELECT c.memberid							");
		sql.append("     , c.name								");
		sql.append("     , c.phone								");
		sql.append("	 , c.address							");
		sql.append("	 , c.join_date							");
		sql.append("	 , c.relation_type						");
		sql.append("	 , r.relation_name						");
		sql.append("  FROM contactlist c						");
		sql.append("	 , relation r							");
		sql.append(" WHERE c.relation_type = r.relation_type	");
		sql.append("   AND c.name LIKE ?						");
		sql.append(" ORDER BY memberid							");
		
		try
		{
			pstmt = conn.prepareStatement(sql.toString());
			name = "%" + name + "%";
			pstmt.setString(1, name);
			
			rs = pstmt.executeQuery();
			
			while(rs.next())
			{
				ContactInfoVO contact = new ContactInfoVO(rs.getInt("memberid"), rs.getString("name"), rs.getString("phone")
														, rs.getString("address"), rs.getString("join_date")
														, rs.getString("relation_type"), rs.getString("relation_name"));
				
				result.add(contact);
			}
		}
		catch(SQLException e)
		{
			Logger.getGlobal().warning("연락처 검색 도중 문제가 발생하였습니다." + e.getMessage());
		}
		finally
		{
			close(conn,pstmt, rs);
		}
		
		return result;
	}
	
	// 연락처 추가
	public int insertContact(ContactInfoVO contact)
	{
		int result = 0;
		
		conn = getConnection();
		PreparedStatement pstmt = null;

		StringBuilder sql = new StringBuilder();
		
		// 회원번호는 가장큰수를 select하여 거기에 +1로 지정
		sql.append("INSERT INTO CONTACTLIST(memberid, name, phone, address, relation_type, join_date)	");
		sql.append("VALUES( (SELECT NVL(MAX(memberid) + 1, 1)											"); 
		sql.append("		  FROM CONTACTLIST), ? , ? , ? , ? , TO_CHAR(SYSDATE, 'YYYYMMDD'))			");

		try
		{
			pstmt = conn.prepareStatement(sql.toString());
			pstmt.setString(1, contact.getName());
			pstmt.setString(2, contact.getPhone());
			pstmt.setString(3, contact.getAddress());
			pstmt.setString(4, contact.getRelation_type());
			
			result = pstmt.executeUpdate();
			
			if(result > 0)
			{
				result = 1;
			}
		}
		catch (SQLTimeoutException e)
		{
			System.out.println("TimeOut Exception");
		}
		catch (SQLException e)
		{
			if(e.getMessage().contains("value too large"))	// value overflow
			{
				result = -1;
			}
			else
			{
				Logger.getGlobal().warning("연락처 추가 도중 문제가 발생하였습니다." + e.getMessage());	
			}
		}
		finally
		{
			close(conn, pstmt);
		}
		
		return result;
	}
	
	// 연락처 수정
	public int editContact(ContactInfoVO contact)
	{
		int result = 0;
		
		conn = getConnection();
		PreparedStatement pstmt = null;

		StringBuilder sql = new StringBuilder();
		
		sql.append("UPDATE CONTACTLIST			");
		sql.append("   SET name 		 = ?	");
		sql.append("	 , phone 		 = ?	");
		sql.append("	 , address 	 	 = ?	");
		sql.append("     , relation_type = ?	");
		sql.append(" WHERE memberid		 = ?	");
				
		try
		{
			pstmt = conn.prepareStatement(sql.toString());
			
			pstmt.setString(1, contact.getName());
			pstmt.setString(2, contact.getPhone());
			pstmt.setString(3, contact.getAddress());
			pstmt.setString(4, contact.getRelation_type());
			pstmt.setInt   (5, contact.getMemberID());
			
			result = pstmt.executeUpdate();
			
			if(result > 0)
			{
				result = 2;
			}
		}
		catch (SQLTimeoutException e)
		{
			System.out.println("TimeOut Exception");
		}
		catch (SQLException e)
		{
			if(e.getMessage().contains("value too large"))	// value overflow
			{
				result = -1;
			}
			else
			{
				Logger.getGlobal().warning("연락처 수정 도중 문제가 발생했습니다." + e.getMessage());
			}
		}
		finally
		{
			close(conn, pstmt);
		}
		
		return result;
	}
	
	// 연락처 삭제
	public int removeContact(ContactInfoVO contact)
	{
		int result = 0;
		
		conn = getConnection();
		PreparedStatement pstmt = null;

		StringBuilder sql = new StringBuilder();
		
		sql.append("DELETE FROM CONTACTLIST		");
		sql.append(" WHERE memberid = ?			");
				
		try
		{
			pstmt = conn.prepareStatement(sql.toString());
			pstmt.setInt(1, contact.getMemberID());
			
			result = pstmt.executeUpdate();
			
			if(result > 0)
			{
				result = 3;
			}
		}
		catch (SQLTimeoutException e)
		{
			System.out.println("TimeOut Exception");
		}
		catch (SQLException e)
		{
			Logger.getGlobal().warning("연락처 삭제 도중 문제가 발생했습니다."+ e.getMessage());
		}
		finally
		{
			close(conn, pstmt);
		}
		
		return result;
	}
	
	// 전화번호 중복체크
	// param phone : 입력한 전화번호
	// param memberID : 연락처 수정시의 멤버ID
	public int checkPhoneNum(String phone, int memberID)
	{
		int result = 0;
		
		conn = getConnection();
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		StringBuilder sql = new StringBuilder();
		
		sql.append("SELECT COUNT(phone) AS count	");
		sql.append("  FROM contactlist				");
		sql.append(" WHERE phone = ?				");
		sql.append("   AND memberid != ?			");
				
		try
		{
			pstmt = conn.prepareStatement(sql.toString());
			pstmt.setString(1, phone);
			pstmt.setInt(2, memberID);
			
			rs = pstmt.executeQuery();
			
			rs.next();
			result = rs.getInt("count");	// 중복이라면 1, 중복이 아니라면 0
		}
		catch (SQLTimeoutException e)
		{
			System.out.println("TimeOut Exception");
		}
		catch (SQLException e)
		{
			System.out.println("전화번호 검색 도중 문제가 발생했습니다."+ e.getMessage());
		}
		finally
		{
			close(conn, pstmt);
		}
		
		return result;
	}
}
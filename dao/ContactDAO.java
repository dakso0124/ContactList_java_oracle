package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.SQLTimeoutException;
import java.time.LocalDate;
import java.util.ArrayList;

import vo.ContactInfoVO;
import vo.RelationVO;

public class ContactDAO // db access object
{
	private static ContactDAO instance;
	public static ContactDAO getInstance()
	{
		if(instance == null)
			instance = new ContactDAO();
		
		return instance;
	}
	
	private ContactDAO()
	{
		
	}
	
	private Connection conn = null;

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

	//region - Create Table
	// 만약 첫 실행이라 테이블이 없다면 테이블들 생성 및 제약조건 지정
	// CONTACTLIST Table 생성
	public void createContactListTable()
	{
		conn = getConnection();
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		StringBuilder sql = new StringBuilder();
		try
		{
			sql.append("CREATE TABLE CONTACTLIST							");
			sql.append("(													");
			sql.append("		name            varchar(50)     NOT NULL	");
			sql.append("	,   phone           varchar2(15)				");
			sql.append("	,   address         varchar2(50)    NOT NULL	");
			sql.append("	,   relation_type   varchar2(3)					");
			sql.append("	,   join_date       DATE						");
			sql.append(")													");
			pstmt = conn.prepareStatement(sql.toString());
			pstmt.executeUpdate();
			System.out.println("CONTACTLIST 테이블 생성에 성공했습니다.");
		}
		catch (SQLTimeoutException e)
		{
			System.out.println("TimeOut Exception 발생");
		}
		catch (SQLException e)
		{
			System.out.println("쿼리 실행중 문제가 발생했습니다.");
		}
		finally
		{
			close(conn, pstmt, rs);

			CreateRelationTable();
		}
	}

	// RELATION 테이블 생성
	private void CreateRelationTable()
	{
		conn = getConnection();
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		StringBuilder sql = new StringBuilder();

		try
		{
			sql.append("CREATE TABLE RELATION								");
			sql.append("(													");
			sql.append("		relation_type   varchar2(3)					");
			sql.append("	,   relation_name   varchar(50)					");
			sql.append(")													");
			pstmt = conn.prepareStatement(sql.toString());
			pstmt.executeUpdate();
			System.out.println("RELATION 테이블 생성에 성공했습니다.");
		} 
		catch (SQLTimeoutException e)
		{
			System.out.println("TimeOut Exception 발생");
		} 
		catch (SQLException e)
		{
			System.out.println("쿼리 실행중 문제가 발생했습니다.");
		} 
		finally
		{
			close(conn, pstmt, rs);
			addContactListPhonePK();
		}
	}

	// CONTACTLIST - phone 컬럼에 PK 지정
	private void addContactListPhonePK()
	{
		conn = getConnection();
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		StringBuilder sql = new StringBuilder();

		try
		{
			conn = getConnection();
			pstmt = null;
			sql.append("ALTER TABLE CONTACTLIST										");
			sql.append("  ADD CONSTRAINT pk_contactlist_phone PRIMARY KEY(phone)	");
			pstmt = conn.prepareStatement(sql.toString());
			pstmt.executeUpdate();
			System.out.println("CONTACTLIST phone column PK 지정에 성공했습니다.");
		}
		catch (SQLTimeoutException e)
		{
			System.out.println("TimeOut Exception 발생");
		}
		catch (SQLException e)
		{
			System.out.println("쿼리 실행중 문제가 발생했습니다.");
		}
		finally
		{
			close(conn, pstmt, rs);
			addRelationTypePK();
		}
	}

	// RELATION - relation_type 컬럼에 PK 지정 
	private void addRelationTypePK()
	{
		conn = getConnection();
		PreparedStatement pstmt = null;
		ResultSet rs = null;

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
			System.out.println("TimeOut Exception 발생");
		}
		catch (SQLException e)
		{
			System.out.println("쿼리 실행중 문제가 발생했습니다.");
		}
		finally
		{
			close(conn, pstmt, rs);
			addContactListTypeFK();
		}
	}

	// CONTACTLIST relation_type에 FK 지정 ref - RELATION relation_type 참조 FK 지정
	private void addContactListTypeFK()
	{
		conn = getConnection();
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		StringBuilder sql = new StringBuilder();

		try
		{
			sql.append("ALTER TABLE CONTACTLIST														");
			sql.append("  ADD CONSTRAINT fk_contactlist_relation_type FOREIGN KEY(relation_type)	");
			sql.append("REFERENCES RELATION(relation_type)											");
			pstmt = conn.prepareStatement(sql.toString());
			pstmt.executeUpdate();
			System.out.println("CONTACTLIST relation_type FK - RELATION relation_type FK 지정에 성공했습니다.");
		}
		catch (SQLTimeoutException e)
		{
			System.out.println("TimeOut Exception 발생");
		}
		catch (SQLException e)
		{
			System.out.println("쿼리 실행중 문제가 발생했습니다.");
		}
		finally
		{
			close(conn, pstmt, rs);
		}
	}
	// endregion
	
	// region ALTER TABLE
	// insert table 
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
			
			if(result > 0)
			{
				System.out.println("타입이 정상적으로 추가되었습니다.");
			}
			else
			{
				System.out.println("타입추가에 실패했습니다.");
			}
		}
		catch (SQLTimeoutException e)
		{
			System.out.println("TimeOut Exception");
		}
		catch (SQLException e)
		{
			System.out.println("쿼리 실행중 문제가 발생했습니다.");
		}
		finally
		{
			close(conn, pstmt);
		}
		
		return result;
	}
		
//	public int removeRelationTypeColumn(String typeName)
//	{
//		int result = 0;
//		
//		
//		
//		return result;
//	}
	// endregion ALTER TABLE
	
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
			System.out.println("관계 테이블을 가져오던 도중 문제가 발생하였습니다.");
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
		
		sql.append("select c.name								");
		sql.append("     , c.phone								");
		sql.append("	 , c.address							");
		sql.append("	 , c.join_date							");
		sql.append("	 , c.relation_type						");
		sql.append("	 , r.relation_name						");
		sql.append("  from contactlist c						");
		sql.append("	 , relation r							");
		sql.append(" where c.relation_type = r.relation_type	");		
		
		try
		{
			pstmt = conn.prepareStatement(sql.toString());
			
			rs = pstmt.executeQuery();
			
			while(rs.next())
			{
				ContactInfoVO contact = new ContactInfoVO(rs.getString("name"), rs.getString("phone")
														, rs.getString("address"), rs.getString("join_date")
														, rs.getString("relation_type"), rs.getString("relation_name"));
				
				result.add(contact);
			}
		}
		catch(SQLException e)
		{
			System.out.println("전체 멤버 검색중 문제가 발생하였습니다.");
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

		sql.append("select c.name								");
		sql.append("     , c.phone								");
		sql.append("	 , c.address							");
		sql.append("	 , c.join_date							");
		sql.append("	 , c.relation_type						");
		sql.append("	 , r.relation_name						");
		sql.append("  from contactlist c						");
		sql.append("	 , relation r							");
		sql.append(" where c.relation_type = r.relation_type	");
		sql.append("   and c.name LIKE ?						");
		
		try
		{
			pstmt = conn.prepareStatement(sql.toString());
			name = "%" + name + "%";
			pstmt.setString(1, name);
			
			rs = pstmt.executeQuery();
			
			while(rs.next())
			{
				ContactInfoVO contact = new ContactInfoVO(rs.getString("name"), rs.getString("phone")
														, rs.getString("address"), rs.getString("join_date")
														, rs.getString("relation_type"), rs.getString("relation_name"));
				
				result.add(contact);
			}
		}
		catch(SQLException e)
		{
			System.out.println("전체 멤버 검색중 문제가 발생하였습니다.");
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
		
		LocalDate curDate = LocalDate.now();

		StringBuilder sql = new StringBuilder();
		
		sql.append("INSERT INTO CONTACTLIST(name, phone, address, join_date, relation_type)		");
		sql.append("values( ? , ? , ? , ? , ? )");
		
		try
		{
			pstmt = conn.prepareStatement(sql.toString());
			pstmt.setString(1, contact.getName());
			pstmt.setString(2, contact.getPhone());
			pstmt.setString(3, contact.getAddress());
			pstmt.setString(4, curDate.toString());
			pstmt.setString(5, contact.getRelation_type());
			
			result = pstmt.executeUpdate();
			
			if(result > 0)
			{
				System.out.println("연락처 수정에 성공했습니다.");
			}
			else
			{
				System.out.println("연락처 수정에 실패했습니다.");
			}
		}
		catch (SQLTimeoutException e)
		{
			System.out.println("TimeOut Exception");
		}
		catch (SQLException e)
		{
			System.out.println("쿼리 실행중 문제가 발생했습니다.");
		}
		finally
		{
			close(conn, pstmt);
		}
		
		return result;
	}
	
	// 연락처 수정
	public int editContact(ContactInfoVO contact, String originPhone)
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
		sql.append(" WHERE phone 		 = ?	");
				
		try
		{
			pstmt = conn.prepareStatement(sql.toString());
			pstmt.setString(1, contact.getName());
			pstmt.setString(2, contact.getPhone());
			pstmt.setString(3, contact.getAddress());
			pstmt.setString(4, contact.getRelation_type());
			pstmt.setString(5, originPhone);
			
			result = pstmt.executeUpdate();
			
			if(result > 0)
			{
				System.out.println("연락처 수정에 성공했습니다.");
			}
			else
			{
				System.out.println("연락처 수정에 실패했습니다.");
			}
		}
		catch (SQLTimeoutException e)
		{
			System.out.println("TimeOut Exception");
		}
		catch (SQLException e)
		{
			System.out.println("쿼리 실행중 문제가 발생했습니다.");
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
		sql.append(" WHERE phone = ?			");
				
		try
		{
			pstmt = conn.prepareStatement(sql.toString());
			pstmt.setString(1, contact.getPhone());
			
			result = pstmt.executeUpdate();
			
			if(result > 0)
			{
				System.out.println("연락처 삭제에 성공했습니다.");
			}
			else
			{
				System.out.println("연락처 삭제에 실패했습니다.");
			}
		}
		catch (SQLTimeoutException e)
		{
			System.out.println("TimeOut Exception");
		}
		catch (SQLException e)
		{
			System.out.println("쿼리 실행중 문제가 발생했습니다.");
		}
		finally
		{
			close(conn, pstmt);
		}
		
		return result;
	}
}
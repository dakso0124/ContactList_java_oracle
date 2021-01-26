package main;


import controller.ContactController;
//import dao.ContactDAO;

public class ContactListMain
{
	public static void main(String[] args)
	{
		// 기본 테이블생성 & 제약조건 추가 
//		ContactDAO dao = new ContactDAO();
//		dao.createContactListTable();
		
		ContactController m_controller = new ContactController();
		
		System.out.println("연락처 관리 프로그램입니다.");
		
		m_controller.StartContactProgram();
		System.exit(0); // 정상 종료
	}
}

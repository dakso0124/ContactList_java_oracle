package main;


import controller.ContactController;
import dao.ContactDAO;

public class ContactListMain
{
	public static void main(String[] args)
	{
//		ContactDAO dao = new ContactDAO();
//		dao.createContactListTable();
		ContactController m_controller = new ContactController();
		
		System.out.println("연락처 관리 프로그램입니다.");
		
		m_controller.StartContactProgram();
		System.exit(0); // 정상 종료
		
		//ContactDAO.getInstance().createContactListTable(); //테이블 생성
	}
}

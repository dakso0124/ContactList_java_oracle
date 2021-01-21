package main;


import java.time.LocalDate;

import controller.ContactController;
import dao.ContactDAO;
import vo.ContactInfoVO;

public class ContactListMain
{
	public static void main(String[] args)
	{
		System.out.println("연락처 관리 프로그램입니다.");
		
		ContactController.getInstance().init();
		ContactController.getInstance().StartContactProgram();

		//ContactDAO dao = new ContactDAO();
		//ContactDAO.getInstance().insertContact(new ContactInfoVO("정재겸", "01024702307", "부산시 중구 영주동 12-1 2층", LocalDate.now().toString(), "001"));
		//System.out.println(ContactDAO.getInstance().searchContact("재").toString());
		//ContactDAO.getInstance().editContact(new ContactInfoVO("뀨뀨", "01012345678", "우리집", LocalDate.now().toString(), "001"), "01024702307");
		//dao.insertContact(new ContactInfoVO("정재겸", "01024702307", "부산시 중구 영주동 12-1 2층", LocalDate.now().toString(), "001"));
		//System.out.println(ContactDAO.getInstance().showAll().get(0).toString());
		//System.out.println(dao.searchContact("정재겸").get(0).toString());
		//dao.removeContact(new ContactInfoVO("정재겸", "01024702307", "부산시 중구 영주동 12-1 2층", LocalDate.now().toString(), "001"));

		//System.out.println(ContactDAO.getInstance().getRelationData().get(0).toString());
		
		
		//dao.createContactListTable();
	}
}

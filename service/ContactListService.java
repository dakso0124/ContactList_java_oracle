package service;

import java.util.ArrayList;

import dao.ContactDAO;
import vo.ContactInfoVO;

public class ContactListService
{
	private static ContactListService instance;
	public static ContactListService getInstance()
	{
		if(instance == null)
			instance = new ContactListService();
		
		return instance;
	}
	
	private ContactListService()
	{
		
	}
	
	public ArrayList<ContactInfoVO> showAll()
	{
		ArrayList<ContactInfoVO> result = new ArrayList<ContactInfoVO>();
		
		result = ContactDAO.getInstance().showAll();
		
		return result;
	}
	
	public ArrayList<ContactInfoVO> searchContact(String name)
	{
		ArrayList<ContactInfoVO> result = new ArrayList<ContactInfoVO>();
		
		return result;
	}
	
	public int addContact(ContactInfoVO contact)
	{
		int result = 0;
		
		result = ContactDAO.getInstance().insertContact(contact);
		
		return result;
	}
	
	public int editContact(ContactInfoVO contact, ContactInfoVO origin)
	{
		int result = 0;
		
		result = ContactDAO.getInstance().editContact(contact, origin);
		
		return result;
	}
	
	public int removeContact(ContactInfoVO contact)
	{
		int result = 0;
		
		result = ContactDAO.getInstance().removeContact(contact);
		
		return result;
	}
}

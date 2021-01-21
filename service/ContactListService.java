package service;

import java.util.ArrayList;

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
		
		return result;
	}
	
	public int editContact(ContactInfoVO contact)
	{
		int result = 0;
		
		return result;		
	}
	
	public int removeContact(ContactInfoVO contact)
	{
		int result = 0;
		
		return result;		
	}
}

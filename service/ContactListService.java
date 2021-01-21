package service;

import java.util.ArrayList;

import dao.ContactDAO;
import vo.ContactInfoVO;
import vo.RelationVO;

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
		
		result = ContactDAO.getInstance().searchContact(name);
		
		return result;
	}
	
	public ArrayList<RelationVO> getRelations()
	{
		ArrayList<RelationVO> result = new ArrayList<RelationVO>();
		
		result = ContactDAO.getInstance().getRelationData();
		
		return result;
	}
	
	public int addRelationType(String typeName)
	{
		int result = 0;
		
		result = ContactDAO.getInstance().addRelationType(typeName);
		
		return result;
	}
	
	public int addContact(ContactInfoVO contact)
	{
		int result = 0;
		
		result = ContactDAO.getInstance().insertContact(contact);
		
		return result;
	}
	
	public int editContact(ContactInfoVO contact, String originPhone)
	{
		int result = 0;
		
		result = ContactDAO.getInstance().editContact(contact, originPhone);
		
		return result;
	}
	
	public int removeContact(ContactInfoVO contact)
	{
		int result = 0;
		
		result = ContactDAO.getInstance().removeContact(contact);
		
		return result;
	}
}

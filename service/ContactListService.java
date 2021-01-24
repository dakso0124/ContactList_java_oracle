package service;

import java.util.ArrayList;

import dao.ContactDAO;
import vo.ContactInfoVO;
import vo.RelationVO;

public class ContactListService
{
	private ContactDAO m_cDAO;
	
	public ContactListService()
	{
		m_cDAO = new ContactDAO();
	}
	
	public ArrayList<ContactInfoVO> showAll()
	{
		ArrayList<ContactInfoVO> result = new ArrayList<ContactInfoVO>();
		
		result = m_cDAO.showAll();
		
		return result;
	}
	
	public ArrayList<ContactInfoVO> searchContact(String name)
	{
		ArrayList<ContactInfoVO> result = new ArrayList<ContactInfoVO>();
		
		result = m_cDAO.searchContact(name);
		
		return result;
	}
	
	public ArrayList<RelationVO> getRelations()
	{
		ArrayList<RelationVO> result = new ArrayList<RelationVO>();
		
		result = m_cDAO.getRelationData();
		
		return result;
	}
	
	public int addRelationType(String typeName)
	{
		int result = 0;
		
		result = m_cDAO.addRelationType(typeName);
		
		return result;
	}
	
	public int addContact(ContactInfoVO contact)
	{
		int result = 0;
		
		result = m_cDAO.insertContact(contact);
		
		return result;
	}
	
	public int editContact(ContactInfoVO contact)
	{
		int result = 0;
		
		result = m_cDAO.editContact(contact);
		
		return result;
	}
	
	public int removeContact(ContactInfoVO contact)
	{
		int result = 0;
		
		result = m_cDAO.removeContact(contact);
		
		return result;
	}
}

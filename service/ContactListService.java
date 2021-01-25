package service;

import java.util.ArrayList;

import dao.ContactDAO;
import vo.ContactInfoVO;
import vo.RelationVO;

import exceptions.PhoneNumberException;

public class ContactListService
{
	private ContactDAO m_cDAO;
	
	private String regEx = "^0(([1-6])([0-9])|2)(\\d{3}|\\d{4})(\\d{4})$"; // 전화번호 정규식 지역번호 및 핸드폰번호
	
	public ContactListService()
	{
		m_cDAO = new ContactDAO();
	}
	
	public ArrayList<ContactInfoVO> showAll()
	{
		ArrayList<ContactInfoVO> result = m_cDAO.showAll();
		
		return result;
	}
	
	public ArrayList<ContactInfoVO> searchContact(String name)
	{
		ArrayList<ContactInfoVO> result = m_cDAO.searchContact(name);
		
		return result;
	}
	
	public ArrayList<RelationVO> getRelations()
	{
		ArrayList<RelationVO> result = m_cDAO.getRelationData();
		
		return result;
	}
	
	public int addRelationType(String typeName)
	{
		int result = m_cDAO.addRelationType(typeName);
		
		return result;
	}
	
	public int addContact(ContactInfoVO contact)
	{
		int result = m_cDAO.insertContact(contact);
		
		return result;
	}
	
	public int editContact(ContactInfoVO contact)
	{
		int result = m_cDAO.editContact(contact);
		
		return result;
	}
	
	public int removeContact(ContactInfoVO contact)
	{
		int result = m_cDAO.removeContact(contact);
		
		return result;
	}
	
	// check phone num
	// return 0 : 정상
	// return 1 : regex err
	// return 2 : parse err
	// return 3 : 전화번호 중복
	public int checkPhoneNumber(String phone, int memberID)
	{
		int result = 0;
		
		try
		{
			if(!phone.matches(regEx))	// 정규식 체크
			{
				throw new PhoneNumberException();
			}
			else if(m_cDAO.checkPhoneNum(phone, memberID) > 0)	// 이미 존재하는 전화번호
			{
				result = 2;
			}
		}
		catch(PhoneNumberException e)	// 정규식 에러
		{
			result = 1;
		}

		return result;
	}
}

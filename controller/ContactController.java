package controller;

import java.util.ArrayList;
import java.util.NoSuchElementException;
import java.util.Scanner;

import exceptions.WrongNumberException;
import service.ContactListService;
import vo.ContactInfoVO;
import vo.RelationVO;

public class ContactController
{
	private static ContactController instance;
	public static ContactController getInstance()
	{
		if(instance == null)
			instance = new ContactController();
		
		return instance;
	}
	
	private ContactController()
	{
		
	}
	
 	private Scanner m_scanner;
 	
 	public void init()
 	{
 		m_scanner = new Scanner(System.in);
 	}
 	
	public void StartContactProgram()
	{
		String temp = null;
		
		while(true)
		{
			try
			{
				System.out.print("1. 연락처 검색\n2. 연락처 추가\n3. 연락처 수정\n4. 연락처 삭제\n5. 프로그램 종료\n원하는 목록을 선택해 주세요 : ");
				temp = m_scanner.nextLine();
				
				switch(temp)
				{
				case "1":
					searchContact();
					break;
					
				case "2":
					addContact();
					break;
					
				case "3":
					editContact();
					break;
					
				case "4":
					removeContact();
					break;
					
				case "5":
					System.out.println("프로그램을 종료합니다.");
					m_scanner.close();
					return;
					
				default :
					System.out.println("메뉴를 확인하시고 원하는 숫자를 입력해 주세요.");
					break;
				}
			}
			catch(NoSuchElementException e)
			{
				e.printStackTrace();
				System.out.println("");
			}
			catch(IllegalStateException e)
			{
				e.printStackTrace();
				System.out.println("");
			}
		}
	}
	
	private void searchContact()
	{
		String temp = null;
		
		System.out.println("연락처 검색 메뉴입니다.");
		
		while(true)
		{
			try
			{
				System.out.print("1. 연락처 전체 보기\n2. 연락처 검색\n0. 이전 메뉴로\n원하는 메뉴를 선택해 주세요 : ");
				temp = m_scanner.nextLine();
				
				switch(temp)
				{
					case "0":
						System.out.println("이전 메뉴로 돌아갑니다.");
						return;
					
					case "1":
						showAll();
						break;
					
					case "2":
						searchContactByName();
						break;
						
					default:
						System.out.println("메뉴를 확인하시고 원하는 숫자를 입력해 주세요.");
						continue;
				}
				
				break;
			}
			catch(NoSuchElementException e)
			{
				e.printStackTrace();
				System.out.println("");
			}
			catch(IllegalStateException e)
			{
				e.printStackTrace();
				System.out.println("");
			}
		}
	}
	
	private void showAll()
	{
		System.out.println("연락처 목록----------------------------------");
		ArrayList<ContactInfoVO> infoList = ContactListService.getInstance().showAll();
		
		for(int i = 0 ; i < infoList.size(); i++)
		{
			System.out.println(infoList.get(i).toString());
		}
		System.out.println("------------------------------------------");
	}
	
	// '연락처 검색 -> 이름으로 검색' 메뉴를 통해 접근
	private void searchContactByName()
	{
		String temp = null;
		
		ArrayList<ContactInfoVO> infoList = new ArrayList<ContactInfoVO>();
		
		while(true)
		{
			System.out.print("이름을 입력하세요(이전 메뉴로 돌아가시려면 0을 입력하세요) : ");
			temp = m_scanner.nextLine();
			
			if(temp.equals("0"))
			{
				System.out.println("이전 메뉴로 돌아갑니다.");
				return;
			}

			infoList = searchByName(temp);
			
			if(infoList.size() == 0)
			{
				System.out.println("검색 결과가 없습니다");
				continue;
			}
			
			System.out.println(String.format("검색 결과 %d명이 검색되었습니다.-----------------", infoList.size()));
			for(int i = 0 ; i < infoList.size(); i++)
			{
				System.out.println( (i+1) + " : " + infoList.get(i).toString());
			}
			System.out.println("-----------------------------------------");
			
			break;
		}
	}
	
	// 실제 이름으로 검색해서 arrlist 리턴
	private ArrayList<ContactInfoVO> searchByName(String name)
	{
		ArrayList<ContactInfoVO> result = new ArrayList<ContactInfoVO>();
		
		result = ContactListService.getInstance().searchContact(name);
		
		return result;
	}
	
	private void addContact()
	{
		System.out.println("연락처를 추가합니다.");
		ContactInfoVO insert = entryContact(new ContactInfoVO());
		
		if(ContactListService.getInstance().addContact(insert) == 0)
		{
			System.out.println("연락처 추가에 실패했습니다.");
		}
		else
		{
			System.out.println("연락처 추가에 성공했습니다.");
		}
	}
	
	private void editContact()
	{
		String temp = null;
		int result = 0;
		System.out.println("연락처 수정 메뉴입니다.");
		
		while(true)
		{
			try
			{
				System.out.print("수정할 회원의 이름을 입력하세요 (이전 메뉴로 돌아가시려면 0을 입력하세요) : ");
				temp = m_scanner.nextLine();
				
				if(temp.equals("0"))
				{
					System.out.println("이전 메뉴로 돌아갑니다.");
					return;
				}
				
				ArrayList<ContactInfoVO> editList = searchByName(temp);
				
				if(editList.size() == 0)
				{
					System.out.println("입력한 이름의 회원이 없습니다.");
					continue;
				}
				else if(editList.size() == 1)
				{
					System.out.println(String.format("1명의 연락처가 검색되었습니다-----------------", editList.size()));
					System.out.println( editList.get(0).toString());
					System.out.println("연락처를 수정합니다------------------------");
					String temporary = editList.get(0).getPhone();
					
					entryContact(editList.get(0));
					
					result = ContactListService.getInstance().editContact(editList.get(0), temporary);
				}
				else
				{
					while(true)
					{
						System.out.println(String.format("검색 결과 %d명이 검색되었습니다.-----------------", editList.size()));
						for(int i = 0 ; i < editList.size(); i++)
						{
							System.out.println( (i+1) + " : " + editList.get(i).toString());
						}
						System.out.println("-----------------------------------------");
						
						try
						{
							System.out.print("수정하실 회원의 번호를 입력하세요 : ");
							temp = m_scanner.nextLine();
							
							if(Integer.parseInt(temp) < 1 || editList.size() <= Integer.parseInt(temp))
							{
								System.out.println("숫자를 정확히 입력해 주세요.");
								continue;
							}
							else
							{
								System.out.println("연락처를 수정합니다.");
								String originPhone = editList.get(Integer.parseInt(temp)-1).getPhone();
								result = ContactListService.getInstance().editContact(editList.get(Integer.parseInt(temp)-1), originPhone );
							}
						}
						catch(NumberFormatException e)
						{
							e.printStackTrace();
							System.out.println("번호를 확인하신 후 숫자를 입력해 주세요.");
							continue;
						}
						break;
					}
				}
				
				break;
			}
			catch(NoSuchElementException e)
			{
				e.printStackTrace();
				System.out.println("");
			}
			catch(IllegalStateException e)
			{
				e.printStackTrace();
				System.out.println("");
			}
			break;
		}
		
		if(result == 0)
		{
			System.out.println("연락처 변경에 실패했습니다.");
		}
		else
		{
			System.out.println("연락처 변경에 실패했습니다.");
		}
	}
	
	private void removeContact()
	{
		int result = 0;
		String temp = null;
		System.out.println("연락처 삭제 메뉴입니다.");
		
		while(true)
		{
			try
			{
				System.out.print("이름을 입력하세요 (이전 메뉴로 돌아가시려면 0을 입력하세요) : ");
				temp = m_scanner.nextLine();
				
				ArrayList<ContactInfoVO> delList = searchByName(temp);
				
				if(delList.size() == 0)
				{
					System.out.println("입력하신 이름의 회원이 없습니다.");
					continue;
				}
				else if(delList.size() == 1)
				{
					System.out.println(String.format("1명의 연락처가 검색되었습니다-----------------", delList.size()));
					System.out.println( delList.get(0).toString());
					System.out.println("연락처를 삭제합니다------------------------");
					result = ContactListService.getInstance().removeContact(delList.get(0));
				}
				else
				{
					while(true)
					{
						System.out.println(String.format("검색 결과 %d명이 검색되었습니다.-----------------", delList.size()));
						for(int i = 0 ; i < delList.size(); i++)
						{
							System.out.println( (i+1) + " : " + delList.get(i).toString());
						}
						System.out.println("-----------------------------------------");
						
						try
						{
							System.out.print("삭제하실 회원의 번호를 입력하세요 : ");
							temp = m_scanner.nextLine();
							
							if(Integer.parseInt(temp) < 1 || delList.size() <= Integer.parseInt(temp))
							{
								throw new WrongNumberException();
							}
							else
							{
								System.out.println("연락처를 삭제합니다.");
								result = ContactListService.getInstance().removeContact((delList.get(Integer.parseInt(temp)-1)));
							}
						}
						catch(WrongNumberException e)
						{
							System.out.println("숫자를 정확히 입력해 주세요.");
							continue;
						}
						catch(NumberFormatException e)
						{
							e.printStackTrace();
							System.out.println("번호를 확인하신 후 숫자를 입력해 주세요.");
							continue;
						}
						break;
					}
				}
			}
			catch(NoSuchElementException e)
			{
				e.printStackTrace();
				System.out.println("");
			}
			catch(IllegalStateException e)
			{
				e.printStackTrace();
				System.out.println("");
			}
			
			break;
		}
	}
	
	private ContactInfoVO entryContact(ContactInfoVO contact)
	{
		String name			 = null;
		String phone 		 = null;
		String address 		 = null;
		String relation_type = null;
		String relation_name = null;
		
		int relation = 0;
		
		while(true)
		{
			try
			{
				System.out.print("이름을 입력하세요 : ");
				name = m_scanner.nextLine();
				
				while(true)
				{
					System.out.print("전화번호를 입력하세요(숫자만 입력) : ");
					phone = m_scanner.nextLine();

					break;
				}
				
				System.out.print("주소를 입력하세요 : ");
				address = m_scanner.nextLine();
				
				while(true)
				{
					try
					{
						ArrayList<RelationVO> relations = ContactListService.getInstance().getRelations(); 
						
						System.out.println("----------------------------------");
						for(int i = 0 ; i < relations .size(); i++)
						{
							System.out.println((i+1) + " : " + relations.get(i).getRelation_name());
						}
						System.out.println("----------------------------------");
						System.out.print("관계를 선택해주세요 (관계 추가는 0을 선택해 주세요): ");
						relation_type = m_scanner.nextLine();
						
						relation = Integer.parseInt(relation_type);
						if(relation == 0)
						{
							boolean isExist = false;
							while(true)
							{
								System.out.print("추가할 관계명을 입력하세요 : ");
								relation_name = m_scanner.nextLine();
								
								for(int i = 0 ; i < relations.size() ; i++ )
								{
									if(relations.get(i).getRelation_name().equals(relation_name))
									{
										isExist = true;
										break;
									}
								}
								
								if(!isExist)
								{
									ContactListService.getInstance().addRelationType(relation_name);
									relation_type = String.format("%03d", relations.size()+1);
								}
								else
								{
									System.out.println("입력하신 관계명이 이미 존재합니다. 다시 입력해 주세요.");
									continue;
								}
								break;
							}
						}
						else if(relation < 1 || relation > relations.size())
						{
							throw new WrongNumberException();
						}
						else
						{
							relation_type = relations.get(relation -1).getRealation_type();
							relation_name = relations.get(relation -1).getRelation_name();	
						}
					}
					catch(WrongNumberException e)
					{
						System.out.println("숫자를 정확히 입력해 주세요.");
						continue;
					}
					catch(NumberFormatException e)
					{
						
					}
					catch(NoSuchElementException e)
					{
						e.printStackTrace();
						System.out.println("");
					}
					catch(IllegalStateException e)
					{
						e.printStackTrace();
						System.out.println("");
					}
					
					break;
				}
			}
			catch(NoSuchElementException e)
			{
				e.printStackTrace();
				System.out.println("");
			}
			catch(IllegalStateException e)
			{
				e.printStackTrace();
				System.out.println("");
			}
			break;
		}
		
		contact.setName(name);
		contact.setPhone(phone);
		contact.setAddress(address);
		contact.setRelation_name(relation_name);
		contact.setRelation_type(relation_type);
		
		return contact;
	}
}

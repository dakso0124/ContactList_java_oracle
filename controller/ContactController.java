package controller;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import java.util.Scanner;
import java.util.ArrayList;
import java.util.logging.Logger;

import java.util.NoSuchElementException;
import exceptions.PhoneNumberException;
import exceptions.WrongNumberException;

import service.ContactListService;
import viewer.ContactListViewer;
import vo.ContactInfoVO;
import vo.RelationVO;

public class ContactController
{	
	public ContactController()
	{
		init();
	}
	
	private ContactListService m_service;
	
 	private Scanner m_scanner;
 	private Logger m_logger;		// exception 및 예외 상황에서 사용할 logger
 	private String regEx = "^0(([1-6])([0-9])|2)(\\d{3}|\\d{4})(\\d{4})$"; // 전화번호 정규식 지역번호 및 핸드폰번호
 	
 	private void init()
 	{
 		m_scanner = new Scanner(System.in);
 		m_logger = Logger.getLogger(ContactController.class.getName());
 		
 		m_service = new ContactListService();
 	}
 	
 	// 프로그램 시작
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
					throw new WrongNumberException();
				}
			}
			catch(WrongNumberException e)
			{
				m_logger.warning("메뉴를 확인하시고 숫자를 정확히 입력해 주세요.");
				continue;
			}
			catch(NoSuchElementException e)
			{
				m_logger.warning("아무것도 입력하지 않으셨습니다.");
				continue;
			}
			catch(IllegalStateException e)	// scanner 비정상적 종료
			{
				m_logger.warning("프로그램에 문제가 생겨 종료합니다.");
				System.exit(1);
			}// 메인 메뉴 선택 try catch
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
						throw new WrongNumberException();
				}
				
				break;
			}
			catch(WrongNumberException e)
			{
				m_logger.warning("메뉴를 확인하시고 숫자를 정확히 입력해 주세요.");
				continue;
			}
			catch(NoSuchElementException e)
			{
				m_logger.warning("아무것도 입력하지 않으셨습니다.");
				continue;
			}
			catch(IllegalStateException e)	// scanner 비정상적 종료
			{
				m_logger.warning("프로그램에 문제가 생겨 종료합니다.");
				System.exit(1);
			}// 검색 메뉴 선택 try catch
		}
	}
	
	// 모든 연락처 출력
	private void showAll()
	{
		ArrayList<ContactInfoVO> infoList = m_service.showAll();
		
		ContactListViewer.getInstance().showList(infoList);	// viewer클래스를 통해 출력
	}
	
	// '연락처 검색 -> 이름으로 검색' 메뉴를 통해 접근
	private void searchContactByName()
	{
		String temp = null;
		
		ArrayList<ContactInfoVO> infoList = new ArrayList<ContactInfoVO>();
		
		while(true)
		{
			try
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
				
				ContactListViewer.getInstance().showList(infoList);	// viewer클래스를 통해 출력
			}
			catch(NoSuchElementException e)
			{
				m_logger.warning("아무것도 입력하지 않으셨습니다.");
				continue;
			}
			catch(IllegalStateException e)	// scanner 비정상적 종료
			{
				m_logger.warning("프로그램에 문제가 생겨 종료합니다.");
				System.exit(1);
			}// 이름 검색 try catch
			
			break;
		}
	}
	
	// 실제 이름으로 검색해서 arrlist 리턴
	private ArrayList<ContactInfoVO> searchByName(String name)
	{
		ArrayList<ContactInfoVO> result = new ArrayList<ContactInfoVO>();
		
		result = m_service.searchContact(name);
		
		return result;
	}
	
	private void addContact()
	{
		System.out.println("연락처를 추가합니다.");
		ContactInfoVO insert = entryContact(new ContactInfoVO());
		
		// 년월일만 join_date로 입력 basic_iso_date = 'YYYYMMDD'
		LocalDate curDate = LocalDate.now();
		insert.setJoin_date(curDate.format(DateTimeFormatter.BASIC_ISO_DATE));
		
		int result = m_service.addContact(insert);
		
		ContactListViewer.getInstance().checkResult(result);		
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
				System.out.print("수정하실 회원의 이름을 입력하세요 (이전 메뉴로 돌아가시려면 0을 입력하세요) : ");
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
				
				ContactListViewer.getInstance().showList(editList);	// viewer클래스를 통해 출력
				
				if(editList.size() == 1)
				{
					System.out.println("연락처를 수정합니다------------------------");
					
					entryContact(editList.get(0));
					
					result = m_service.editContact(editList.get(0));
				}
				else
				{
					while(true)
					{
						try
						{
							System.out.print("수정하실 회원의 번호를 입력하세요 : ");
							temp = m_scanner.nextLine();
							
							if(Integer.parseInt(temp) < 1 || editList.size() < Integer.parseInt(temp))
							{
								m_logger.warning("숫자를 정확히 입력해 주세요.");
								continue;
							}
							else
							{
								System.out.println("연락처를 수정합니다.");
								ContactInfoVO editContact = entryContact(editList.get(Integer.parseInt(temp)-1));
								
								result = m_service.editContact(editContact);
							}
						}
						catch(NumberFormatException e)
						{
							m_logger.warning("번호를 확인하신 후 숫자를 입력해 주세요.");
							continue;
						}
						catch(NoSuchElementException e)
						{
							m_logger.warning("아무것도 입력하지 않으셨습니다.");
							continue;
						}
						catch(IllegalStateException e)	// scanner 비정상적 종료
						{
							m_logger.warning("프로그램에 문제가 생겨 종료합니다.");
							System.exit(1);
						}// 연락처 list try catch
						break;
					}
				}
				
				break;
			}
			catch(NoSuchElementException e)
			{
				m_logger.warning("아무것도 입력하지 않으셨습니다. 이름을 입력해 주세요.");
				continue;
			}
			catch(IllegalStateException e)	// scanner 비정상적 종료
			{
				m_logger.warning("프로그램에 문제가 생겨 종료합니다.");
				System.exit(1);
			}// 이름 입력 try catch
			break;
		}
		
		ContactListViewer.getInstance().checkResult(result);
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
				
				ContactListViewer.getInstance().showList(delList);	// viewer클래스를 통해 출력
				
				if(delList.size() == 1)
				{
					System.out.println("연락처를 삭제합니다.");
					result = m_service.removeContact(delList.get(0));
				}
				else
				{
					while(true)
					{
						try
						{
							System.out.print("삭제하실 회원의 번호를 입력하세요 : ");
							temp = m_scanner.nextLine();
							
							if(Integer.parseInt(temp) < 1 || delList.size() < Integer.parseInt(temp))
							{
								throw new WrongNumberException();
							}
							else
							{
								System.out.println("연락처를 삭제합니다.");
								result = m_service.removeContact((delList.get(Integer.parseInt(temp)-1)));
							}
						}
						catch(WrongNumberException e)
						{
							m_logger.warning("메뉴를 확인하시고 숫자를 정확히 입력해 주세요.");
							continue;
						}
						catch(NumberFormatException e)
						{
							m_logger.warning("번호를 확인하신 후 숫자를 입력해 주세요.");
							continue;
						}
						catch(NoSuchElementException e)
						{
							m_logger.warning("아무것도 입력하지 않으셨습니다. 이름을 입력해 주세요.");
							continue;
						}
						catch(IllegalStateException e) // scanner 비정상적 종료
						{
							m_logger.warning("프로그램에 문제가 생겨 종료합니다.");
							System.exit(1);
						}// 회원번호 try catch
						break;
					}
				}
			}
			catch(NoSuchElementException e)
			{
				m_logger.warning("아무것도 입력하지 않으셨습니다. 이름을 입력해 주세요.");
				continue;
			}
			catch(IllegalStateException e) // scanner 비정상적 종료
			{
				m_logger.warning("프로그램에 문제가 생겨 종료합니다.");
				System.exit(1);
			}// 회원 이름 try catch
			
			ContactListViewer.getInstance().checkResult(result);
			
			break;
		}
	}
	
	// 연락처 추가 & 수정 시 호출. set contact & return
	private ContactInfoVO entryContact(ContactInfoVO contact)
	{
		String name			 = null;
		String phone 		 = null;
		String address 		 = null;
		String relation_type = null;
		String relation_name = null;
		
		int relation 		 = 0;
		
		while(true)
		{
			try
			{
				System.out.print("이름을 입력하세요 : ");
				name = m_scanner.nextLine();
				
				while(true)
				{
					try
					{
						System.out.print("전화번호를 입력하세요(숫자만 입력) : ");
						phone = m_scanner.nextLine();
						
						Integer.parseInt(phone);
						
						if(!phone.matches(regEx))	// 정규식으로 검사
						{
							throw new PhoneNumberException();
						}
					}
					catch(PhoneNumberException e)	// 전화번호 에러
					{
						m_logger.warning("전화번호를 확인하시고 정확히 입력해 주세요.");
						continue;
					}
					catch(NumberFormatException e) // Integer.parse
					{
						m_logger.warning("전화번호를 숫자로 정확히 입력해 주세요.");
						continue;
					}
					catch(NoSuchElementException e)
					{
						m_logger.warning("아무것도 입력하지 않으셨습니다. 전화번호를 입력해 주세요.");
						continue;
					}
					catch(IllegalStateException e) // scanner 비정상적 종료
					{
						m_logger.warning("프로그램에 문제가 생겨 종료합니다.");
						System.exit(1);
					}// 전화번호 입력 try catch

					break;
				}
				
				System.out.print("주소를 입력하세요 : ");
				address = m_scanner.nextLine();
				
				while(true)
				{
					try
					{
						ArrayList<RelationVO> relations = m_service.getRelations();
						
						if(relations.size() == 0)
						{
							System.out.println("그룹이 없습니다. 추가해 주세요.");
						}
						else
						{
							System.out.println("----------------------------------");
							for(int i = 0 ; i < relations .size(); i++)
							{
								System.out.println((i+1) + " : " + relations.get(i).getRelation_name());
							}
							System.out.println("----------------------------------");
							System.out.print("그룹을 선택해주세요 (그룹 추가는 0을 선택해 주세요) : ");
							relation_type = m_scanner.nextLine();
							
							relation = Integer.parseInt(relation_type);	
						}
						
						if(relation == 0 || relations.size() == 0)	// 그룹 추가
						{
							while(true)
							{
								try
								{
									boolean isExist = false;
									
									System.out.print("추가할 그룹명을 입력하세요 : ");
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
										m_service.addRelationType(relation_name);
										relation_type = String.format("%03d", relations.size()+1);
									}
									else
									{
										System.out.println("입력하신 그룹명이 이미 존재합니다. 다시 입력해 주세요.");
										continue;
									}
								}
								catch(NoSuchElementException e)	// nextline
								{
									m_logger.warning("아무것도 입력하지 않으셨습니다. 메뉴를 확인하시고 입력해 주세요.");
									continue;
								}
								catch(IllegalStateException e)	// scanner 비정상적 종료
								{
									m_logger.warning("프로그램에 문제가 생겨 종료합니다.");
									System.exit(1);
								}//// 그룹명 추가 try catch
								
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
					catch(WrongNumberException e)	// array index out of bound
					{
						m_logger.warning("메뉴를 확인하시고 숫자를 정확히 입력해 주세요.");
						continue;
					}
					catch(NumberFormatException e)	// Integer.parse relation 
					{
						m_logger.warning("메뉴를 확인하시고 숫자를 정확히 입력해 주세요.");
						continue;
					}
					catch(NoSuchElementException e)	// nextline
					{
						m_logger.warning("아무것도 입력하지 않으셨습니다. 메뉴를 확인하시고 입력해 주세요.");
						continue;
					}
					catch(IllegalStateException e)	// scanner 비정상적 종료
					{
						m_logger.warning("프로그램에 문제가 생겨 종료합니다.");
						System.exit(1);
					}/// 그룹 선택 try catch
					
					break;
				}
			}
			catch(NoSuchElementException e) // nextline
			{
				m_logger.warning("아무것도 입력하지 않으셨습니다. 메뉴를 확인하시고 정확히 입력해 주세요.");
				continue;
			}
			catch(IllegalStateException e)	// scanner 비정상적 종료
			{
				m_logger.warning("프로그램에 문제가 생겨 종료합니다.");
				System.exit(1);
			}// 이름, 주소 try catch
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
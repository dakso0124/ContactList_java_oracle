package controller;

import java.util.NoSuchElementException;
import java.util.Scanner;

public class ContactController
{
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
					searchMember();
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
	
	private void searchMember()
	{
		String temp = null;
		
		System.out.println("연락처 검색 메뉴입니다.");
		
		while(true)
		{
			System.out.print("1. 연락처 전체 보기\n2. 연락처 검색\n3. 이전 메뉴로");
			temp = m_scanner.nextLine();
			
			switch(temp)
			{
				case "1":
					searchAll();
					break;
				
				case "2":
					searchContact();
					break;
					
				case "3":
					break;
					
				default:
					System.out.println("메뉴를 확인하시고 원하는 숫자를 입력해 주세요.");
					break;
			}
		}
	}
	
	private void searchAll()
	{
		
	}
	
	private void searchContact()
	{
		String temp = null;
		System.out.println("연락처를 검색합니다.");
		
		while(true)
		{
			System.out.print("이름을 입력하세요(이전 메뉴로 돌아가시려면 exit를 입력하세요.) : ");
			temp = m_scanner.nextLine();
			
			if(temp.equals("exit"))
			{
				System.out.println("이전 메뉴로 돌아갑니다.");
				return;
			}
			
			
		}
	}
	
	private void addContact()
	{
		String temp 	= null;
		
		String name 	= null;
		String phone 	= null;
		String address 	= null;
		String relation = null;
		
		System.out.println("연락처를 추가합니다.");
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
					System.out.print("관계를 선택하세요 (추가하시려면 0을 입력하세요.) : ");
					relation = m_scanner.nextLine();
					
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
	
	private void editContact()
	{
		
	}
	
	private void removeContact()
	{
		
	}
}

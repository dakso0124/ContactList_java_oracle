package viewer;

import java.util.ArrayList;
import java.util.logging.Logger;

import vo.ContactInfoVO;
import vo.RelationVO;

public class ContactListViewer {

	public ContactListViewer()
	{
		
	}
	
	// 연락처 리스트
	public void showContactList(ArrayList<ContactInfoVO> showList)
	{
		System.out.println(String.format("검색 결과 %d명이 검색되었습니다.-----------------", showList.size()));
		for(int i = 0 ; i < showList.size(); i++)
		{
			System.out.println( (i+1) + " : " + showList.get(i).toString());
		}
		System.out.println("-----------------------------------------");
	}
	
	// 그룹 리스트
	public void showRelationList(ArrayList<RelationVO> showList)
	{
		System.out.println("-----------------------------------------");
		for(int i = 0 ; i < showList.size(); i++)
		{
			System.out.println( (i+1) + " : " + showList.get(i).toString());
		}
		System.out.println("-----------------------------------------");
	}
	
	// 추가, 수정, 삭제 시 결과 확인
	// DAO에서 작업 성공 or 실패시 결과 출력
	public void checkResult(int result)
	{
		switch(result)
		{
			case 0:
				System.out.println("작업에 실패했습니다.");
				break;
			case 1:
				System.out.println("연락처를 추가했습니다.");
				break;
			case 2:
				System.out.println("연락처를 수정했습니다.");
				break;
			case 3:
				System.out.println("연락처를 삭제했습니다.");
				break;
			case -1:
				System.out.println("입력하신 정보중 너무 길게 작성하신 정보가 있습니다.");
				break;
		}
	}
}

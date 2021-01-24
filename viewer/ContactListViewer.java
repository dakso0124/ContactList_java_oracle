package viewer;

import java.util.ArrayList;
import java.util.logging.Logger;

import vo.ContactInfoVO;

public class ContactListViewer {
	private static ContactListViewer instance;
	public static ContactListViewer getInstance()
	{
		if(instance == null)
		{
			instance = new ContactListViewer();
		}
		return instance;
	}
	
	private ContactListViewer()
	{
		
	}
	
	public void showList(ArrayList<ContactInfoVO> showList)
	{
		System.out.println(String.format("검색 결과 %d명이 검색되었습니다.-----------------", showList.size()));
		for(int i = 0 ; i < showList.size(); i++)
		{
			System.out.println( (i+1) + " : " + showList.get(i).toString());
		}
		System.out.println("-----------------------------------------");
	}
	
	// 추가, 수정, 삭제 시 메세지 확인
	public void checkResult(int result)
	{
		switch(result)
		{
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
			Logger.getGlobal().warning("이미 존재하는 전화번호입니다. 번호를 다시한번 확인해 주세요.");
			break;
		case -2:
			Logger.getGlobal().warning("입력한 정보가 너무 깁니다.");
			break;
		case -96:
			Logger.getGlobal().warning("관계 테이블을 가져오던 도중 문제가 발생하였습니다.");
			break;
		case -97:
			Logger.getGlobal().warning("전체 멤버 검색중 문제가 발생하였습니다.");
			break;
		case -98:
			Logger.getGlobal().warning("멤버 검색 도중 문제가 발생하였습니다.");
			break;
		case -99:
			Logger.getGlobal().warning("데이터를 운용하던 중 에러가 발생했습니다.");
			break;
		}
	}
}

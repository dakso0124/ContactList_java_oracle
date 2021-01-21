package viewer;

import service.ContactListService;

public class ContactListViewer
{
	private static ContactListViewer instance;
	public static ContactListViewer getInstance()
	{
		if(instance == null)
			instance = new ContactListViewer();
		
		return instance;
	}
	
	private ContactListViewer()
	{
		
	}
}

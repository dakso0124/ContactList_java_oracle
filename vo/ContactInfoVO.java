package vo;

public class ContactInfoVO
{
	private String name;			// Not Null
	private String phone;			// pk
	private String address;			// not null
	private String join_date;
	private String relation_type;	// fk
	private String relation_name;	// not null
	
	public ContactInfoVO()
	{
		name			= null;
		phone			= null;
		address			= null;
		join_date		= null;
		relation_type	= null;
		relation_name	= null;
	}
	
	public ContactInfoVO(String name, String phone, String address, String relationType)
	{
		this.name 			= name;
		this.phone			= phone;
		this.address       	= address;
		this.relation_type	= relationType;
	}
	
	public ContactInfoVO(String name, String phone, String address, String join_date, String relationType, String relation_name)
	{
		this.name 			= name;
		this.phone			= phone;
		this.address       	= address;
		this.join_date		= join_date;
		this.relation_type	= relationType;
		this.relation_name	= relation_name;
	}
	
	public String getName()
	{
		return name;
	}
	
	public void setName(String name)
	{
		this.name = name;
	}
	
	public String getPhone()
	{
		return phone;
	}
	
	public void setPhone(String phone)
	{
		this.phone = phone;
	}
	
	public String getAddress()
	{
		return address;
	}
	
	public void setAddress(String address)
	{
		this.address = address;
	}
	
	public String getJoin_date()
	{
		return join_date;
	}

	public void setJoin_date(String join_date)
	{
		this.join_date = join_date;
	}
	
	public String getRelation_type()
	{
		return relation_type;
	}
	
	public void setRelation_type(String relation_type)
	{
		this.relation_type = relation_type;
	}
	
	public String getRelation_name()
	{
		return relation_name;
	}

	public void setRelation_name(String relation_name)
	{
		this.relation_name = relation_name;
	}
	
	@Override
	public String toString()
	{
		return String.format("[ %-30s  %-30s  %-30s  %-30s  %-30s ]", this.name, this.phone, this.address, this.join_date, this.relation_name);
	}
}

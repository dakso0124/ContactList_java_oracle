package vo;

public class RelationVO
{
	private String realation_type;
	private String relation_name;
	
	public RelationVO()
	{
		
	}
	
	public RelationVO(String relation_type, String relation_name)
	{

		this.realation_type = relation_type;
		this.relation_name = relation_name;
	}
	
	public String getRelation_name()
	{
		return relation_name;
	}
	
	public void setRelation_name(String relation_name)
	{
		this.relation_name = relation_name;
	}
	
	public String getRealation_type()
	{
		return realation_type;
	}
	
	public void setRealation_type(String realation_type)
	{
		this.realation_type = realation_type;
	}
	
	@Override
	public String toString()
	{
		return String.format("[ %-20s | %-20s ]", this.relation_name, this.realation_type);
	}
}

package vo;

public class RelationVO
{
	private String relation_name;
	private String realation_type;
	
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
		return String.format("[ %s \t\t %s \t]", this.relation_name, this.realation_type);
	}
}

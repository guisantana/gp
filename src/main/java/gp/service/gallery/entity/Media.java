package gp.service.gallery.entity;

public class Media
{
	private String id;
	private int type;
	private String mediaFile;
	private String title;
	private String description;
	private String legend;
	private String parentFolder;
	private String credits;

	public void setId(String id){
		this.id=id;
	}
	public String getId()
	{
		return id;
	}
	public String getMediaFile()
	{
		return mediaFile;
	}
	public void setMediaFile(String mediaFile)
	{
		this.mediaFile = mediaFile;
	}
	public String getLegend()
	{
		return legend;
	}
	public void setLegend(String legend)
	{
		this.legend = legend;
	}
	public String getTitle()
	{
		return title;
	}
	public void setTitle(String title)
	{
		this.title = title;
	}
	public String getDescription()
	{
		return description;
	}
	public void setDescription(String description)
	{
		this.description = description;
	}
	public int getType()
	{
		return type;
	}
	public void setType(int type)
	{
		this.type = type;
	}
	public String getParentFolder()
	{
		return parentFolder;
	}
	public void setParentFolder(String parentFolder)
	{
		this.parentFolder = parentFolder;
	}
	public String getCredits()
	{
		return credits;
	}
	public void setCredits(String credits)
	{
		this.credits = credits;
	}
}

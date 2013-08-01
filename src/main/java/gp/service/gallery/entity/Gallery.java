package gp.service.gallery.entity;

public class Gallery {

	private String id;
	private String media;
	private String parentContent;
	private int position;
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getMedia() {
		return media;
	}
	public void setMedia(String media) {
		this.media = media;
	}
	public String getParentContent() {
		return parentContent;
	}
	public void setParentContent(String parentContent) {
		this.parentContent = parentContent;
	}
	public int getPosition() {
		return position;
	}
	public void setPosition(int position) {
		this.position = position;
	}
	
	
}

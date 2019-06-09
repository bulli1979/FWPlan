package biz.wgc.fwplan.data;

import java.util.UUID;

public class EditIcon {
	private String title;
	private String image;
	private int text;
	private String id;
	private int top;
	private int left;
	public EditIcon() {
		this.id = UUID.randomUUID().toString();
	}
	public String getTitle() {
		return title;
	}
	public String getImage() {
		return image;
	}
	public String getId() {
		return id;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public void setImage(String image) {
		this.image = image;
	}
	public void setText(int text) {
		this.text = text;
	}
	public int getText() {
		return this.text;
	}
	public void setId(String id) {
		this.id = id;
	}
	public int getTop() {
		return top;
	}
	public void setTop(int top) {
		this.top = top;
	}
	public int getLeft() {
		return left;
	}
	public void setLeft(int left) {
		this.left = left;
	}
}

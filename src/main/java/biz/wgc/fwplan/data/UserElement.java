package biz.wgc.fwplan.data;

public class UserElement {
	private String id;
	private String planId;
	private String text;
	private int textPosition;
	private double top;
	private double left;
	private double height;
	private double width;
	private int type;
	private String image;
	public UserElement() {}
	private UserElement(Builder builder) {
		this.id = builder.id;
		this.top = builder.top;
		this.left = builder.left;
		this.type = builder.type;
		this.text = builder.text;
		this.width = builder.width;
		this.height = builder.height;
		this.planId = builder.planId;
		this.image = builder.image;
		this.textPosition = builder.textPosition;
	}
	
	public String getId() {
		return id;
	}
	public double getTop() {
		return top;
	}
	public void setTop(double top) {
		this.top = top;
	}
	public double getLeft() {
		return left;
	}
	public void setLeft(double left) {
		this.left = left;
	}
	public int getType() {
		return type;
	}
	public void setType(int type) {
		this.type = type;
	}
	public String getText() {
		return text;
	}
	public void setText(String text) {
		this.text = text;
	}
	public double getHeight() {
		return height;
	}
	public void setHeight(double height) {
		this.height = height;
	}
	public double getWidth() {
		return width;
	}
	public void setWidth(double width) {
		this.width = width;
	}
	public String getPlanId() {
		return planId;
	}
	public void setPlanId(String planId) {
		this.planId = planId;
	}
	public void setId(String id) {
		this.id = id;
	}
	
	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}
	
	public int getTextPosition() {
		return textPosition;
	}
	public void setTextPosition(int textPosition) {
		this.textPosition = textPosition;
	}

	public static class Builder{
		private String id;
		private String planId;
		private String text;
		private double top;
		private double left;
		private double height;
		private double width;
		private int type;
		private String image;
		private int textPosition;

		public Builder(){
		}
		
		public Builder setId(String id) {
			this.id = id;
			return this;
		}
		public Builder forPlan(String planId) {
			this.planId = planId;
			return this;
		}
		public Builder withText(String text) {
			this.text = text;
			return this;
		}
		public Builder withLeft(double left) {
			this.left = left;
			return this;
		}
		public Builder withWidth(double width) {
			this.width = width;
			return this;
		}
		public Builder withTop(double top) {
			this.top = top;
			return this;
		}
		public Builder withHeight(double height) {
			this.height = height;
			return this;
		}
		
		public Builder withType(int type) {
			this.type = type;
			return this;
		}
		public Builder setPlanId(String planId) {
			this.planId = planId;
			return this;
		}
		public Builder withImage(String image) {
			this.image = image;
			return this;
		}
		public Builder withTextPosition(int textPosition) {
			this.textPosition = textPosition;
			return this;
		}
		public UserElement build() {
			return new UserElement(this);
		}
	}

	
	
}

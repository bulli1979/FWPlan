package data;

public class UserElement {
	private String id;
	private String planId;
	private String text;
	private double top;
	private double left;
	private double top2;
	private double left2;
	private int type;
	
	private UserElement(Builder builder) {
		this.id = builder.id;
		this.top = builder.top;
		this.left = builder.left;
		this.type = builder.type;
		this.text = builder.text;
		this.top2 = builder.top2;
		this.left2 = builder.left2;
		this.planId = builder.planId;
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
	public double getTop2() {
		return top2;
	}
	public void setTop2(double top2) {
		this.top2 = top2;
	}
	public double getLeft2() {
		return left2;
	}
	public void setLeft2(double left2) {
		this.left2 = left2;
	}
	public String getPlanId() {
		return planId;
	}
	public void setPlanId(String planId) {
		this.planId = planId;
	}
	
	public static class Builder{
		private String id;
		private String planId;
		private String text;
		private double top;
		private double left;
		private double top2;
		private double left2;
		private int type;
		
		public Builder(){}
		
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
		public Builder withLeft2(double left2) {
			this.left2 = left2;
			return this;
		}
		public Builder withTop(double top) {
			this.top = top;
			return this;
		}
		public Builder withTop2(double top2) {
			this.top2 = top2;
			return this;
		}
		
		public Builder withType(int type) {
			this.type = type;
			return this;
		}
		
		public UserElement build() {
			return new UserElement(this);
		}
	}
	
}

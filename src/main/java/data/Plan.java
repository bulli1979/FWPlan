package data;

public class Plan {
	private String id;
	private String title;
	private String description;
	private String planNumber;
	public Plan(Builder builder) {
		this.id = builder.id;
		this.title = builder.title;
		this.description = builder.description;
		this.planNumber = builder.planNumber;
	}

	public String getId() {
		return id;
	}

	public String getTitle() {
		return title;
	}

	public String getDescription() {
		return description;
	}

	public String getPlanNumber() {
		return planNumber;
	}

	public static class Builder{
		private String id;
		private String title;
		private String description;
		private String planNumber;		
		public Builder setId(String id){
			this.id = id;
			return this;
		}
		
		public Builder withTitle(String title){
			this.title = title;
			return this;
		}
		
		public Builder withDescription(String description){
			this.description = description;
			return this;
		}
		
		public Builder withPlanNumber(String planNumber){
			this.planNumber = planNumber;
			return this;
		}
		
		public Plan build(){
			return new Plan(this);
		}
	
	}
}

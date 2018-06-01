package data;

public class Plan {
	private String id;
	private String title;
	private String description;
	private String planNumber;
	private String adress;
	private String instantaction;
	private String wathertransport;
	private String importantcontacts;
	private String map;
	private String imageLink;
	
	public Plan(Builder builder) {
		this.id = builder.id;
		this.title = builder.title;
		this.description = builder.description;
		this.planNumber = builder.planNumber;
		this.adress = builder.adress;
		this.instantaction = builder.instantAction;
		this.wathertransport = builder.watherTransport;
		this.importantcontacts = builder.importantContacts;
		this.map = builder.map;
		this.imageLink = builder.imageLink;
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
	public String getMap() {
		return map;
	}

	public void setMap(String map) {
		this.map = map;
	}

	public String getAdress() {
		return adress;
	}

	public String getInstantAction() {
		return instantaction;
	}

	public String getWatherTransport() {
		return wathertransport;
	}

	public String getImportantContacts() {
		return importantcontacts;
	}
	
	public String getImageLink() {
		return imageLink;
	}

	public void setImageLink(String imageLink) {
		this.imageLink = imageLink;
	}



	public static class Builder{
		private String id;
		private String title;
		private String description;
		private String planNumber;
		private String adress;
		private String instantAction;
		private String watherTransport;
		private String importantContacts;
		private String map;
		private String imageLink;
		
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
		
		public Builder withAdress(String adress){
			this.adress = adress;
			return this;
		}
		
		public Builder withInstantAction(String instantaction){
			this.instantAction = instantaction;
			return this;
		}
		
		public Builder withWatherTransport(String wathertransport){
			this.watherTransport = wathertransport;
			return this;
		}
		
		public Builder withImportantContact(String importantcontacts){
			this.importantContacts = importantcontacts;
			return this;
		}
		
		public Builder withMap(String map){
			this.map = map;
			return this;
		}
		
		public Builder withImageLink(String imageLink){
			this.imageLink = imageLink;
			return this;
		}
		
		public Plan build(){
			return new Plan(this);
		}
	
	}
}

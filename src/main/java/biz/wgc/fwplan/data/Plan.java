package biz.wgc.fwplan.data;

import lombok.Data;

@Data
public class Plan {
	private String id;
	private String title;
	private String description;
	private String planNumber;
	private String adress;
	private String instantAction;
	private String watherTransport1;
	private String watherTransport2;
	private String watherTransport3;
	private String watherTransport4;
	private String watherColor1;
	private String watherColor2;
	private String watherColor3;
	private String wahterColor4;
	private String importantContacts;
	private String map;
	private String imageLink;
	
	public Plan(Builder builder) {
		this.id = builder.id;
		this.title = builder.title;
		this.description = builder.description;
		this.planNumber = builder.planNumber;
		this.adress = builder.adress;
		this.instantAction = builder.instantAction;
		this.watherTransport1 = builder.watherTransport1;
		this.watherTransport2 = builder.watherTransport2;
		this.watherTransport3 = builder.watherTransport3;
		this.watherTransport4 = builder.watherTransport4;
		this.importantContacts = builder.importantContacts;
		this.map = builder.map;
		this.imageLink = builder.imageLink;
	}
	public static class Builder{
		private String id;
		private String title;
		private String description;
		private String planNumber;
		private String adress;
		private String instantAction;
		private String watherTransport1;
		private String watherTransport2;
		private String watherTransport3;
		private String watherTransport4;
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
		
		public Builder withWatherTransport1(String wathertransport){
			this.watherTransport1 = wathertransport;
			return this;
		}
		
		public Builder withWatherTransport2(String wathertransport){
			this.watherTransport2 = wathertransport;
			return this;
		}
		
		public Builder withWatherTransport3(String wathertransport){
			this.watherTransport3 = wathertransport;
			return this;
		}
		
		public Builder withWatherTransport4(String wathertransport){
			this.watherTransport4 = wathertransport;
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

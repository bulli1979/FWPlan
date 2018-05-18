package data;

public class Plan {
	private String id;
	private String title;
	private String description;
	private String planNumber;
	private String adresse;
	private String sofortmassnahmen;
	private String wassertransport;
	private String wichtigeKontakte;
	
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

	public String getAdresse() {
		return adresse;
	}

	public void setAdresse(String adresse) {
		this.adresse = adresse;
	}

	public String getSofortmassnahmen() {
		return sofortmassnahmen;
	}

	public void setSofortmassnahmen(String sofortmassnahmen) {
		this.sofortmassnahmen = sofortmassnahmen;
	}

	public String getWassertransport() {
		return wassertransport;
	}

	public void setWassertransport(String wassertransport) {
		this.wassertransport = wassertransport;
	}

	public String getWichtigeKontakte() {
		return wichtigeKontakte;
	}

	public void setWichtigeKontakte(String wichtigeKontakte) {
		this.wichtigeKontakte = wichtigeKontakte;
	}

	public static class Builder{
		private String id;
		private String title;
		private String description;
		private String planNumber;
		private String adresse;
		private String sofortmassnahmen;
		private String wassertransport;
		private String wichtigeKontakte;
		
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
		
		public Builder withAdresse(String adresse){
			this.adresse = adresse;
			return this;
		}
		
		public Builder withSofortmassnahmen(String sofortmassnahmen){
			this.sofortmassnahmen = sofortmassnahmen;
			return this;
		}
		
		public Builder withWassertransport(String wassertransport){
			this.wassertransport = wassertransport;
			return this;
		}
		
		public Builder withWichtigeKontakte(String wichtigeKontakte){
			this.wichtigeKontakte = wichtigeKontakte;
			return this;
		}
		
		public Plan build(){
			return new Plan(this);
		}
	
	}
}

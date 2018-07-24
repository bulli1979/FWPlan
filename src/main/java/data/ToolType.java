package data;

public enum ToolType {
	HYDRANTH(1),IMAGE(2),CAR(3);
	
	private int type;
	
	private ToolType(int type) {
		this.type=type;
	}
	
	public int getType() {
		return this.type;
	}
	
	public static ToolType getToolType(int type) {
		for(ToolType tt : ToolType.values()) {
			if(tt.type == type) {
				return tt;
			}
		}
		return null;
	}
	
}

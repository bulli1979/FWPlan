package data;

public enum ToolType {
	TEXT(1),IMAGE(2),LINE(3);
	
	private int type;
	
	private ToolType(int type) {
		this.type=type;
	}
	
	public int getType() {
		return this.type;
	}
	
}

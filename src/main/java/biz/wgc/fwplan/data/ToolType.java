package biz.wgc.fwplan.data;

public enum ToolType {
	IMAGE(1), ICON(2);
	
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

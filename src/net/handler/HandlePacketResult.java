package net.handler;

import lombok.Getter;
import lombok.Setter;

public class HandlePacketResult {

	@Getter @Setter
	private boolean cancelled;
	
	@Getter
	private byte[] resultPayload;
	
	@Getter
	private boolean newPayload;
	
	public HandlePacketResult() {
		cancelled = false;
		resultPayload = null;
		newPayload = false;
	}
	
	public void setResultPayload(byte[] resultPayload) {
		this.resultPayload = resultPayload;
		newPayload = true;
	}
	
}

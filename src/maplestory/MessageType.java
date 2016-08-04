package maplestory;

import lombok.Getter;

public enum MessageType {

	NOTICE(0),
	POPUP(1),
	MEGAPHONE(2),
	SUPER_MEGAPHONE(3),
	SCROLLING_TEXT(4),
	PINK_TEXT(5),
	LIGHT_BLUE_TEXT(6)
	;
	
	@Getter
	private final int id;
	
	MessageType(int id) {
		this.id = id;
	}
	
}

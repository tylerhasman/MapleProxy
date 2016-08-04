package maplestory;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

@AllArgsConstructor
@ToString
public class MapleChannel {

	@Getter
	private String name;
	@Getter
	private byte id;
	@Getter
	private int population;
	
}

package com.qzing.websocket.pojo;

import lombok.Data;

@Data
public class ChatRoomResponse {
	private String userId;
	private String name;
	private String chatValue;
}

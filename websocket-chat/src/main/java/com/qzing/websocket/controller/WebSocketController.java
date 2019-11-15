package com.qzing.websocket.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Controller;

import com.qzing.websocket.pojo.ChatRoomRequest;
import com.qzing.websocket.pojo.ChatRoomResponse;
import com.qzing.websocket.pojo.User;
 
@Controller
public class WebSocketController {
	
    @Autowired
    private SimpMessagingTemplate template;
	
    //广播群推送消息
    //@Scheduled(initialDelay = 5000,fixedRate = 5000)
    public void serverToManyMessage() {
	System.out.println("后台广播群体推送！");
	User user=new User();
	user.setUserName("server to many client publish...");
	user.setAge(10);
    	this.template.convertAndSend("/topic/getResponse",user);
    }
    //广播单一向指定用户推送消息
    @Scheduled(initialDelay = 5000,fixedRate = 5000)
    public void serverToOneMessage() {
	System.out.println("后台广播指定对象推送！");
	User user=new User();
	user.setUserId(1);
	user.setUserName("server to one client publish...");
	user.setAge(10);
    	this.template.convertAndSendToUser(user.getUserId()+"", "/queue/getResponse", user);
    }
    
    //客户端主动发送消息到服务端，服务端马上回应指定的客户端消息
    //类似http无状态请求，但是有质的区别
    //websocket可以从服务器指定发送哪个客户端，而不像http只能响应请求端
    
    //群发
    @MessageMapping("/massRequest")
    //SendTo 发送至 Broker 下的指定订阅路径
    @SendTo("/mass/getResponse")
    public ChatRoomResponse mass(ChatRoomRequest chatRoomRequest){
        //方法用于群发测试
        System.out.println("name = " + chatRoomRequest.getName());
        System.out.println("chatValue = " + chatRoomRequest.getChatValue());
        ChatRoomResponse response=new ChatRoomResponse();
        response.setName(chatRoomRequest.getName());
        response.setChatValue(chatRoomRequest.getChatValue());
        return response;
    }
		
    //单独聊天
    @MessageMapping("/aloneRequest")	
    public ChatRoomResponse alone(ChatRoomRequest chatRoomRequest){
        //方法用于一对一测试
	System.out.println("userId = " + chatRoomRequest.getUserId());
        System.out.println("name = " + chatRoomRequest.getName());
        System.out.println("chatValue = " + chatRoomRequest.getChatValue());	       
        ChatRoomResponse response=new ChatRoomResponse();
        response.setName(chatRoomRequest.getName());       
        response.setChatValue(chatRoomRequest.getChatValue());
        this.template.convertAndSendToUser(chatRoomRequest.getUserId(),"/alone/getResponse",response);
        return response;

    }  
}
package com.qzing.websocket.config;
import javax.websocket.OnClose;
import javax.websocket.OnError;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;

import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.AbstractWebSocketMessageBrokerConfigurer;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;

import lombok.extern.slf4j.Slf4j;
 
/**
 * 配置WebSocket
 */
@Slf4j
@Configuration
//注解开启使用STOMP协议来传输基于代理(message broker)的消息,这时控制器支持使用@MessageMapping,就像使用@RequestMapping一样
@EnableWebSocketMessageBroker
public class WebSocketConfig extends AbstractWebSocketMessageBrokerConfigurer{
	
	@Override
	//注册STOMP协议的节点(endpoint),并映射指定的url
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        //注册一个STOMP的endpoint,并指定使用SockJS协议
        registry.addEndpoint("/endpointQzing").setAllowedOrigins("*").withSockJS();
    }
    @Override
    //配置消息代理(Message Broker)
    public void configureMessageBroker(MessageBrokerRegistry registry) {
    	//名字自定义 
    	//topic代表群体消息广播代理
    	//user代表指定广播
    	//mass代表以代理群发喜消息
    	//alone用以代码一对一聊天
        registry.enableSimpleBroker("/topic","/toOne","/mass","/alone");
        //点对点使用的订阅前缀（客户端订阅路径上会体现出来），不设置的话，默认是/user/
        registry.setUserDestinationPrefix("/toOne");
    }
}
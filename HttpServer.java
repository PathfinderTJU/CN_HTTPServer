package com.tju.edu.p01;

import java.io.*;
import java.net.*;

	/*
	 * 程序主类，实现HTTP基本功能
	 */
public class HttpServer {
	
	//WEB_PATH常量为当前project目录下的webroot目录，作为服务器文件存放目录
	//PORT为服务器端口号
	public static final String WEB_PATH = System.getProperty("user.dir") + File.separator +"webroot";
	public static final int PORT = 30001;
	
	public static void main(String args[]) {
		HttpServer server = new HttpServer();
		server.waitConnect();
	}
	
	//等待连接
	@SuppressWarnings("resource")
	public void waitConnect() {
		ServerSocket serverSocket = null;
		try {
			serverSocket = new ServerSocket(PORT, 1, InetAddress.getByName("127.0.0.1"));//创建到服务器的套接字
		}catch(Exception e) {
			System.out.println("Failed to create socket.");
			e.printStackTrace();
			System.exit(1);
		}
		System.out.println("Server socket create successfully.");
		
		//循环等待连接
		while(true) {
			try {
				//使用accept方法获取连接到服务器客户套接字
				Socket clientSocket = serverSocket.accept();
				System.out.println("Client socket get.");
						
				//获取客户端输入输出流
				InputStream input = clientSocket.getInputStream();
				OutputStream output = clientSocket.getOutputStream();
				
				//建立Request对象，用来解析请求
				Request request = new Request(input);
				request.parse();
				System.out.println("Parse Conplete.");
				
				System.out.println("Start responsing...");
				//建立Response对象，用来做出响应
				Response reponse = new Response(output);
				reponse.setRequest(request);
				reponse.sendResponse();
				System.out.println("Responsing complete.");
				
				//关闭套接字
				clientSocket.close();
				
			}catch(Exception e) {//处理异常并继续等待连接
				e.printStackTrace();
				continue;
			}
		}
	}	
}

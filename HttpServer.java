package com.tju.edu.p01;

import java.io.*;
import java.net.*;

	/*
	 * �������࣬ʵ��HTTP��������
	 */
public class HttpServer {
	
	//WEB_PATH����Ϊ��ǰprojectĿ¼�µ�webrootĿ¼����Ϊ�������ļ����Ŀ¼
	//PORTΪ�������˿ں�
	public static final String WEB_PATH = System.getProperty("user.dir") + File.separator +"webroot";
	public static final int PORT = 30001;
	
	public static void main(String args[]) {
		HttpServer server = new HttpServer();
		server.waitConnect();
	}
	
	//�ȴ�����
	@SuppressWarnings("resource")
	public void waitConnect() {
		ServerSocket serverSocket = null;
		try {
			serverSocket = new ServerSocket(PORT, 1, InetAddress.getByName("127.0.0.1"));//���������������׽���
		}catch(Exception e) {
			System.out.println("Failed to create socket.");
			e.printStackTrace();
			System.exit(1);
		}
		System.out.println("Server socket create successfully.");
		
		//ѭ���ȴ�����
		while(true) {
			try {
				//ʹ��accept������ȡ���ӵ��������ͻ��׽���
				Socket clientSocket = serverSocket.accept();
				System.out.println("Client socket get.");
						
				//��ȡ�ͻ������������
				InputStream input = clientSocket.getInputStream();
				OutputStream output = clientSocket.getOutputStream();
				
				//����Request����������������
				Request request = new Request(input);
				request.parse();
				System.out.println("Parse Conplete.");
				
				System.out.println("Start responsing...");
				//����Response��������������Ӧ
				Response reponse = new Response(output);
				reponse.setRequest(request);
				reponse.sendResponse();
				System.out.println("Responsing complete.");
				
				//�ر��׽���
				clientSocket.close();
				
			}catch(Exception e) {//�����쳣�������ȴ�����
				e.printStackTrace();
				continue;
			}
		}
	}	
}

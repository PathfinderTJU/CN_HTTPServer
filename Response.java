package com.tju.edu.p01;

import java.io.*;
	
	/*
	 * ���ڷ���HTTP��Ӧ���ĵ���
	 */
public class Response {

	//˽�б��������û��׽��ִ������ݵ������������������Ϣ��Request����
	private OutputStream output;
	private Request request;
	
	//������Ӧ����
	public void sendResponse() throws Exception{
		//��Request�����л�ȡ��Ϣ
		String requestType = request.getRequestType();
		String uri = request.getUri();
		String fileType = request.getFileType();
		
		//ʹ�ÿ�������д���PrintStream��
		PrintStream writer = new PrintStream(output);
		
		//�ж������Ƿ�Ϊ���ʸ�Ŀ¼
		if (uri.equals("/webroot")) {
			File dir = new File(HttpServer.WEB_PATH);
			
			//��Ӧ����ͷ���Կ��н���
			writer.println("HTTP/1.0 200 OK");
			writer.println("Content-type:text/html");
			writer.println();
			
			//��Ŀ¼���ļ�����html�ļ���ʽ����
			writer.println("<h1>Server WebRoot:</h1>");
			writer.println("<ul>");
			printDir(dir);
			writer.println("</ul>");
		}else {
			File fileToSend = new File(HttpServer.WEB_PATH , uri);
			//���ж������Ƿ�Ϊ�������Ŀ¼
			if (fileToSend.isFile() || (!fileToSend.isFile() && !fileToSend.isDirectory())) {
				//��Ϊ�ļ������ļ� ���Ȳ����ļ�Ҳ����Ŀ¼������һ��404ҳ��
				transferFile(requestType,uri,fileType);
			}else {
				File dir = new File(HttpServer.WEB_PATH, uri);
				
				//�����Ŀ¼
				writer.println("HTTP/1.0 200 OK");
				writer.println("Content-type:text/html");
				writer.println();
				writer.println("<h1>Server webroot" + uri + ":</h1>");
				writer.println("<ul>");
				printDir(dir);
				writer.println("</ul>");
			}
		}

	}
	
	//�ݹ��ӡһ��Ŀ¼�µ��ļ�����html��ʽ��
	private void printDir(File file) throws Exception{
		PrintStream writer = new PrintStream(output);
			
		if (file.isFile()) {
			writer.println("<li>" + file.getName() + "</li>");
		}else {
			writer.println("<li>" + file.getName() + "</li>");
			File[] files = file.listFiles();
			for (int i = 0 ; i < files.length ; i++) {
				writer.println("<ul>");
				printDir(files[i]);
				writer.println("</ul>");
			}
		}
	}
	
	//�����ļ�
	private void transferFile(String requestType, String uri, String fileType) throws Exception{
		File fileToSend = new File(HttpServer.WEB_PATH , uri);
		
		//�ж��ļ��Ƿ����
		if (fileToSend.exists() && !fileToSend.isDirectory()) {
			PrintStream writer = new PrintStream(output);
			//��Ӧͷ��
			writer.println("HTTP/1.1 200 OK");
			writer.println("Content-length: "+fileToSend.length());
			
			//ͨ����ͬ���ļ����ͣ�����HTTP��Ӧ�����в�ͬ��contentType�ײ����Ӷ�ʵ�����������ֱ�Ӵ�һЩ�ļ�
			String contentType = null;
			//��ҳ
			if (fileType.equals("html") || fileType.equals("css")) {
				contentType = "text/" + fileType;
			//�ı�
			}else if(fileType.equals("txt")) {
				contentType = "text/plain";
			//ͼ��
			}else if (fileType.equals("jpg") || fileType.equals("gif") || fileType.equals("jpeg") || fileType.equals("png")) {
				contentType = "image/" + fileType;
			//��Ƶ
			}else if (fileType.equals("mp4") || fileType.equals("avi")){
				contentType = "video/" + fileType;
			//��Ƶ
			}else if (fileType.equals("mp3")) {
				contentType = "audio/" + fileType;
			//ʸ��ͼ
			}else if (fileType.equals("svg")) {
				contentType = "text/xml";
			//���������ļ�����zip��������һ������
			}else {
				contentType = "application/binary";
			}
			
			writer.println("Content-Type:" + contentType);
			writer.println();
			
			//����������ΪGETʱ�������ļ�������ΪHEAD���������ļ�
			if (requestType.equals("GET")) {
				FileInputStream is = new FileInputStream(fileToSend);
				byte[] buffer = new byte[1024];
				int check = 0;
				while ((check = is.read(buffer)) > 0) {
					writer.write(buffer, 0, check);
				}
				is.close();
			}
	
			writer.close();
		//�ļ������ڣ�����һ��404ҳ��
		}else {
			PrintStream writer = new PrintStream(output);
			writer.println("HTTP/1.0 404 File Not Found");
			writer.println("Content-type:text/html");
			writer.println("Content-length: 23");
			writer.println();
			writer.println("<h1>404 Not Found</h1>");
		}
	}
	
	public OutputStream getOutput() {
		return output;
	}

	public void setOutput(OutputStream output) {
		this.output = output;
	}

	public Request getRequest() {
		return request;
	}

	public void setRequest(Request request) {
		this.request = request;
	}
	
	public Response() {
		this.output = null;
		this.request = null;
	}
	
	public Response(OutputStream output) {
		this.output = output;
		this.request = null;
	}


}

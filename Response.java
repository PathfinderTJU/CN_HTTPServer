package com.tju.edu.p01;

import java.io.*;
	
	/*
	 * 用于发送HTTP响应报文的类
	 */
public class Response {

	//私有变量：向用户套接字传输数据的输出流、传递请求信息的Request对象
	private OutputStream output;
	private Request request;
	
	//发送响应报文
	public void sendResponse() throws Exception{
		//从Request对象中获取信息
		String requestType = request.getRequestType();
		String uri = request.getUri();
		String fileType = request.getFileType();
		
		//使用可以逐行写入的PrintStream类
		PrintStream writer = new PrintStream(output);
		
		//判断请求是否为访问根目录
		if (uri.equals("/webroot")) {
			File dir = new File(HttpServer.WEB_PATH);
			
			//响应报文头，以空行结束
			writer.println("HTTP/1.0 200 OK");
			writer.println("Content-type:text/html");
			writer.println();
			
			//根目录下文件名以html文件格式返回
			writer.println("<h1>Server WebRoot:</h1>");
			writer.println("<ul>");
			printDir(dir);
			writer.println("</ul>");
		}else {
			File fileToSend = new File(HttpServer.WEB_PATH , uri);
			//再判断请求是否为浏览其他目录
			if (fileToSend.isFile() || (!fileToSend.isFile() && !fileToSend.isDirectory())) {
				//若为文件传输文件 若既不是文件也不是目录，返回一个404页面
				transferFile(requestType,uri,fileType);
			}else {
				File dir = new File(HttpServer.WEB_PATH, uri);
				
				//传输该目录
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
	
	//递归打印一个目录下的文件名（html格式）
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
	
	//传输文件
	private void transferFile(String requestType, String uri, String fileType) throws Exception{
		File fileToSend = new File(HttpServer.WEB_PATH , uri);
		
		//判断文件是否存在
		if (fileToSend.exists() && !fileToSend.isDirectory()) {
			PrintStream writer = new PrintStream(output);
			//响应头部
			writer.println("HTTP/1.1 200 OK");
			writer.println("Content-length: "+fileToSend.length());
			
			//通过不同的文件类型，设置HTTP响应报文中不同的contentType首部，从而实现在浏览器中直接打开一些文件
			String contentType = null;
			//网页
			if (fileType.equals("html") || fileType.equals("css")) {
				contentType = "text/" + fileType;
			//文本
			}else if(fileType.equals("txt")) {
				contentType = "text/plain";
			//图像
			}else if (fileType.equals("jpg") || fileType.equals("gif") || fileType.equals("jpeg") || fileType.equals("png")) {
				contentType = "image/" + fileType;
			//视频
			}else if (fileType.equals("mp4") || fileType.equals("avi")){
				contentType = "video/" + fileType;
			//音频
			}else if (fileType.equals("mp3")) {
				contentType = "audio/" + fileType;
			//矢量图
			}else if (fileType.equals("svg")) {
				contentType = "text/xml";
			//其余类型文件（如zip）都返回一个下载
			}else {
				contentType = "application/binary";
			}
			
			writer.println("Content-Type:" + contentType);
			writer.println();
			
			//当请求类型为GET时，传输文件，否则为HEAD，不传输文件
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
		//文件不存在，返回一个404页面
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

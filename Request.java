package com.tju.edu.p01;

import java.io.*;
	
	/*
	 * Request类用于解析获得的用户请求报文，并将信息传递给Response对象
	 */
	
public class Request {
	
	//私有变量：客户socket输入流（用于获取请求报文）、请求类型、URI、文件类型、HTTP报文首行
	private InputStream input;
	private String uri;
	private String requestType;
	private String fileType;
	private String headline;
	
	//有参构造函数
	public Request(InputStream input) {
		this.input = input;
	}
	
	//解析HTTP请求报文，采用可以逐行读取的BufferedReader类读取
	public void parse() throws Exception{
		BufferedReader reader = new BufferedReader(new InputStreamReader(input));
		
		//只需读取首行报文即可获取必要信息
		headline = reader.readLine();
		System.out.println("Headline: " + headline);
		
		//首行格式：请求码 URI HTTP1.1，获取相应信息
		String words[] = headline.split(" ");
		requestType = words[0];
		uri = words[1];
		System.out.println("Request Type: " + requestType);
		System.out.println("Uri: " + uri);
		
		//截取URI尾部获得请求文件类型（有可能为目录名）
		fileType = uri.substring(uri.lastIndexOf(".") + 1);
		System.out.println("FileType: " + fileType);
		
	}
	
	public InputStream getInput() {
		return input;
	}

	public void setInput(InputStream input) {
		this.input = input;
	}

	public String getUri() {
		return uri;
	}

	public void setUri(String uri) {
		this.uri = uri;
	}
	
	public String getRequestType() {
		return requestType;
	}

	public void setRequestType(String requestType) {
		this.requestType = requestType;
	}
	
	public String getFileType() {
		return fileType;
	}

	public void setFileType(String fileType) {
		this.fileType = fileType;
	}
	
	public String getHeadline() {
		return headline;
	}

	public void setHeadline(String headline) {
		this.headline = headline;
	}

	public Request() {
		input = null;
		uri = null;
		requestType = null;
	}
	
}

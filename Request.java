package com.tju.edu.p01;

import java.io.*;
	
	/*
	 * Request�����ڽ�����õ��û������ģ�������Ϣ���ݸ�Response����
	 */
	
public class Request {
	
	//˽�б������ͻ�socket�����������ڻ�ȡ�����ģ����������͡�URI���ļ����͡�HTTP��������
	private InputStream input;
	private String uri;
	private String requestType;
	private String fileType;
	private String headline;
	
	//�вι��캯��
	public Request(InputStream input) {
		this.input = input;
	}
	
	//����HTTP�����ģ����ÿ������ж�ȡ��BufferedReader���ȡ
	public void parse() throws Exception{
		BufferedReader reader = new BufferedReader(new InputStreamReader(input));
		
		//ֻ���ȡ���б��ļ��ɻ�ȡ��Ҫ��Ϣ
		headline = reader.readLine();
		System.out.println("Headline: " + headline);
		
		//���и�ʽ�������� URI HTTP1.1����ȡ��Ӧ��Ϣ
		String words[] = headline.split(" ");
		requestType = words[0];
		uri = words[1];
		System.out.println("Request Type: " + requestType);
		System.out.println("Uri: " + uri);
		
		//��ȡURIβ����������ļ����ͣ��п���ΪĿ¼����
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

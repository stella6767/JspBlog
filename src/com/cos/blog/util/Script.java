package com.cos.blog.util;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.http.HttpServletResponse;

public class Script {
	
	public static void responseData(HttpServletResponse resp, String jsonData) throws IOException { 		
		//나중에 CommonRespDto<T> jsonData
		PrintWriter out;
		
		try {
			out =resp.getWriter();
			out.println(jsonData);
			out.flush(); //버퍼 비우기
		} catch (Exception e) {
			e.printStackTrace();
		}

		
	}
	
	public static void back(HttpServletResponse resp, String msg) throws IOException { 		
		
		PrintWriter out;
		
		try {
			out =resp.getWriter();
			out.println("<script>");
			out.println("alert('"+msg+"')");
			out.println("history.back();");
			out.println("</script>");
			out.flush();
		} catch (Exception e) {
			e.printStackTrace();
		}

		
	}
	
	
	
	
}

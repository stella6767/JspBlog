package com.cos.blog.config;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.cos.blog.util.Script;

public class AttackFilter implements Filter {
	//<!-- 해당 페이지로 직접 URL(자원에 직접 파일.확장자) 접근을 하게 되면 또 파일 내부에서 세션 체크를 해야함. -->
	//<!-- 필터에 .jsp로 접근하는 모든 접근을 막아버리면 됨. -->
	@Override
	public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain)
			throws IOException, ServletException {
		
		HttpServletRequest request = (HttpServletRequest) req;
    	HttpServletResponse response = (HttpServletResponse) resp;
		
    	System.out.println("jsp직접 들어오는 거 감지");
    	
    	//Script.back(response, "url 직접 접근금지");
    	
    	//req.setAttribute("req",req);
    	RequestDispatcher dis = request.getRequestDispatcher("board?cmd=list");
		dis.forward(request, response);
  	
		chain.doFilter(request, response);
	}
}
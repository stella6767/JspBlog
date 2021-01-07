package com.cos.blog.config;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.cos.blog.util.Script;

//이제부터는 내부에서의 모든 요청은 RequestDispatcher로 해야한다. 
//그래야 다시 필터를 타지 않는다.
public class ForbiddenUrlConfig implements Filter {
	//<!-- 해당 페이지로 직접 URL(자원에 직접 파일.확장자) 접근을 하게 되면 또 파일 내부에서 세션 체크를 해야함. -->
	//<!-- 필터에 .jsp로 접근하는 모든 접근을 막아버리면 됨. -->
	@Override
	public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain)
			throws IOException, ServletException {
		HttpServletRequest request = (HttpServletRequest) req;
    	HttpServletResponse response = (HttpServletResponse) resp;

    	System.out.println("ForbiddenUrlConfig 접근");
    	System.out.println(request.getRequestURL());
    	System.out.println(request.getRequestURI());

    	if(request.getRequestURI().equals("/blog/") || request.getRequestURI().equals("/blog/index.jsp")) {
    		chain.doFilter(request, response);
    	}else {
    		PrintWriter out = response.getWriter();
    		out.print("잘못된 접근입니다.");
    		out.flush();
    	}

	}
}
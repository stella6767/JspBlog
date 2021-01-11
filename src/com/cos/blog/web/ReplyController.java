package com.cos.blog.web;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.cos.blog.domain.board.dto.CommonRespDto;
import com.cos.blog.domain.reply.Reply;
import com.cos.blog.domain.reply.dto.SaveReqDto;
import com.cos.blog.domain.reply.dto.SaveRespDto;
import com.cos.blog.domain.user.User;
import com.cos.blog.service.BoardService;
import com.cos.blog.service.ReplyService;
import com.cos.blog.util.Script;
import com.google.gson.Gson;

@WebServlet("/reply")
public class ReplyController extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public ReplyController() {
		super();

	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doProcess(request, response);
		

	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

			doProcess(request, response);
	}
	
	protected void doProcess(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		
		String cmd = request.getParameter("cmd");
		ReplyService replyService = new ReplyService();
		// http://localhost:8080/blog/reply?cmd=save
		HttpSession session = request.getSession();
		
		if (cmd.equals("save")) {
			
			BufferedReader br = request.getReader(); //제이슨으로 던진 데이터는 버퍼로 받아야됨
			String reqData = br.readLine();
			
			Gson gson = new Gson();
			SaveReqDto dto = gson.fromJson(reqData, SaveReqDto.class);
			System.out.println("dto: "+dto);
						
			CommonRespDto<SaveRespDto> commonRespDto = new CommonRespDto<>();
			SaveRespDto saveRespDto = null;			
			int result = replyService.댓글쓰기(dto); //generated key를 돌려받는다. 댓글 아이디
			
			if(result != -1) {
				saveRespDto = replyService.댓글찾기(result);
				commonRespDto.setStatusCode(1); //1, -1
				commonRespDto.setData(saveRespDto);
			}else {
				commonRespDto.setStatusCode(-1); //1, -1
				
			}
			
			
			String responseData = gson.toJson(commonRespDto); 
			System.out.println("responseData : "+responseData);
			Script.responseData(response, responseData);	
			
		}else if(cmd.equals("delete")) {
			
			System.out.println("댓글 삭제");
			
			int id = Integer.parseInt(request.getParameter("id"));
			System.out.println(id);
			
			int result = replyService.댓글삭제(id);
			
			CommonRespDto<String> commonRespDto = new CommonRespDto<>();
			commonRespDto.setStatusCode(result);
			commonRespDto.setData("성공"); //여기서 if문으로 분기해야되는 거 아닌가?
			
			Gson gson = new Gson();
			String respData = gson.toJson(commonRespDto);
			System.out.println("respData : "+respData);
			PrintWriter out = response.getWriter();
			out.print(respData);
			
		}

			
	}

}

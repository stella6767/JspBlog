package com.cos.blog.web;

import java.io.IOException;
import java.util.List;
import java.util.Vector;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.cos.blog.domain.board.Board;
import com.cos.blog.domain.board.dto.SaveReqDto;
import com.cos.blog.domain.user.User;
import com.cos.blog.service.BoardService;
import com.cos.blog.util.Script;

//http://localhost:8080/blog/board
@WebServlet("/board")
public class BoardController extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public BoardController() {
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
		BoardService boardService = new BoardService();
		// http://localhost:8080/blog/board?cmd=saveForm
		HttpSession session = request.getSession();

		if (cmd.equals("saveForm")) {
			User principal = (User) session.getAttribute("principal");
			if (principal != null) {
				RequestDispatcher dis = request.getRequestDispatcher("board/saveForm.jsp");
				dis.forward(request, response);
			} else {
				RequestDispatcher dis = request.getRequestDispatcher("user/loginForm.jsp");
				dis.forward(request, response);
			}
		} else if (cmd.equals("save")) {
			int userId = Integer.parseInt(request.getParameter("userId"));
			String title = request.getParameter("title");
			String content = request.getParameter("content");

			SaveReqDto dto = new SaveReqDto();
			dto.setUserId(userId);
			dto.setTitle(title);
			dto.setContent(content);
			int result = boardService.글쓰기(dto);

			if (result == 1) { // 정상
				response.sendRedirect("index.jsp");
			} else {
				Script.back(response, "글쓰기실패");
			}
		} else if (cmd.equals("list")) {

			int page = Integer.parseInt(request.getParameter("page"));
			List<Board> boards = boardService.글목록보기(page);
			request.setAttribute("boards", boards); // 앞이 키값 뒤가 데이터!// request에 담고
			
			//계산 (전체 데이터 수랑 한페이지 몇개-총 몇페이지 나와야되는지 계산. 3page라면 max값은 2
			//page 2가 되는 순간 isEnd = true
			
			
			int boardNum=boardService.전체데이터(); //머리가 안 돌아가서 이 방법밖에 생각이 안 남...
			System.out.println(boardNum%4);
			int max = boardNum%4-1;
			
			System.out.println("max값: "+max);
			System.out.println("page 값: " + page);
			
			if(max == page) {
				request.setAttribute("isEnd", true);
			}
			
			if(page<=0) {
				request.setAttribute("isStart", true);
			}
			
			RequestDispatcher dis = request.getRequestDispatcher("board/list.jsp");// RequestDispathcer 만들어서 이동
			dis.forward(request, response);

		} else if (cmd.equals("detail")) {

		}

	}

}

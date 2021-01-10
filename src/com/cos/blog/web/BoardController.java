package com.cos.blog.web;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.cos.blog.domain.board.Board;
import com.cos.blog.domain.board.dto.CommonRespDto;
import com.cos.blog.domain.board.dto.DetailRespDto;
import com.cos.blog.domain.board.dto.SaveReqDto;
import com.cos.blog.domain.board.dto.UpdateReqDto;
import com.cos.blog.domain.user.User;
import com.cos.blog.service.BoardService;
import com.cos.blog.util.Script;
import com.google.gson.Gson;

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

			// 계산 (전체 데이터 수랑 한페이지 몇개-총 몇페이지 나와야되는지 계산. 3page라면 max값은 2
			// page 2가 되는 순간 isEnd = true

			int boardCount = boardService.글개수();
			System.out.println(boardCount);
			int lastPage = (boardCount - 1) / 4; // 2/4 = 0, 3/4 = 0, 4/4 = 1, 9/4 = 2 ( 0page, 1page, 2page)
			double currentPosition = (double) page / (lastPage) * 100;

			request.setAttribute("lastPage", lastPage);
			request.setAttribute("currentPosition", currentPosition);

			RequestDispatcher dis = request.getRequestDispatcher("board/list.jsp");// RequestDispathcer 만들어서 이동
			dis.forward(request, response);

		} else if (cmd.equals("detail")) {

			int id = Integer.parseInt(request.getParameter("id"));
			DetailRespDto dto = boardService.글상세보기(id);// board테이블+user테이블 = 조인된 데이터!!

			if (dto == null) {
				Script.back(response, "상세보기에 실패하였습니다.");
			} else {
				request.setAttribute("dto", dto);
				RequestDispatcher dis = request.getRequestDispatcher("board/detail.jsp");// RequestDispathcer 만들어서 이동
				dis.forward(request, response);
			}
		} else if (cmd.equals("delete")) {
			int id = Integer.parseInt(request.getParameter("id"));
			
			int result = boardService.글삭제(id);
			
			CommonRespDto<String> commonRespDto = new CommonRespDto<>();
			commonRespDto.setStatusCode(result);
			commonRespDto.setData("성공"); //여기서 if문으로 분기해야되는 거 아닌가?
			
			Gson gson = new Gson();
			String respData = gson.toJson(commonRespDto);
			System.out.println("respData : "+respData);
			PrintWriter out = response.getWriter();
			out.print(respData);
			
			
		}else if(cmd.equals("updateForm")) {
			int id = Integer.parseInt(request.getParameter("id"));
			DetailRespDto dto = boardService.글상세보기(id);
			request.setAttribute("dto", dto);
			RequestDispatcher dis = request.getRequestDispatcher("board/updateForm.jsp");
			dis.forward(request, response);		
		}else if(cmd.equals("update")) {
			int id = Integer.parseInt(request.getParameter("id"));
			String title = request.getParameter("title");
			String content = request.getParameter("content");

			UpdateReqDto dto = new UpdateReqDto();
			dto.setId(id);
			dto.setTitle(title);
			dto.setContent(content);

			int result = boardService.글수정(dto);

			if(result == 1) {
				// 고민해보세요. 왜 RequestDispatcher 안썻는지... 한번 써보세요. detail.jsp 호출 ?
				
				
				request.setAttribute("dto", dto);
				RequestDispatcher dis = request.getRequestDispatcher("/board?cmd=detail&id="+id);
				//RequestDispatcher dis = request.getRequestDispatcher("board/detail.jsp");
				dis.forward(request, response);	
				
				//response.sendRedirect("/blog/board?cmd=detail&id="+id);
			}else {
				Script.back(response,"글 수정에 실패하였습니다.");
			}
			
			
		}
		
		

	}

}

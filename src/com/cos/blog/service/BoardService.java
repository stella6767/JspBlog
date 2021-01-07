package com.cos.blog.service;

import java.util.List;
import java.util.Vector;

import com.cos.blog.domain.board.Board;
import com.cos.blog.domain.board.BoardDao;
import com.cos.blog.domain.board.dto.SaveReqDto;

public class BoardService {
	
	private BoardDao boardDao;
	
	public BoardService() {
		boardDao = new BoardDao();
	}
	
	
	public int 글쓰기(SaveReqDto dto) {
		return boardDao.save(dto);
		
	}
	
	public List<Board> 글목록보기(int page){
	
		return boardDao.findAll(page);
	}
	
	public int 전체데이터() {
		
		return boardDao.findAll2();
	}
	
	
}

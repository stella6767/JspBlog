package com.cos.blog.service;

import java.util.List;

import com.cos.blog.domain.reply.Reply;
import com.cos.blog.domain.reply.ReplyDao;
import com.cos.blog.domain.reply.dto.SaveReqDto;
import com.cos.blog.domain.reply.dto.SaveRespDto;

public class ReplyService {
	private ReplyDao replyDao;

	public ReplyService() {
		replyDao = new ReplyDao();
	}

	public List<SaveRespDto> 글목록보기(int boardId){
		return replyDao.findAll(boardId);
	}
	
	public int 댓글쓰기(SaveReqDto dto) {
		return replyDao.save(dto);
	}
	
	public SaveRespDto 댓글찾기(int id) {
		return replyDao.findById(id);		
	}
		
	public int 댓글삭제(int id) {
		return replyDao.deleteById(id);
	}
	
	
}

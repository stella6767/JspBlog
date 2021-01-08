package com.cos.blog.domain.board;

import java.sql.Timestamp;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Board {
	private int id;
	private int userId;
	private String title;
	private String content;
	private int readCount; // 조회수 디폴트값 0
	private Timestamp createDate;
	
	//주말에 루시필터 적용해보기
//	public String getTitle() { //내가 직접 getter 함수를 만들어주면, Lombok에서 안 만들어준다.
//		return title.replaceAll("<", "&lt;").replaceAll(">", "&gt;");
//	}
}

<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<%@ include file="../layout/header.jsp"%>

<div class="container">

	<c:if test="${sessionScope.principal.id == dto.userId}">
		<!-- POST,GET,DELETE,PUT  //POST -->
		<button class="btn btn-danger" onClick="deleteById(${dto.id})">삭제</button>
	</c:if>

	<br /> <br />
	<h6 class="m-2">
		작성자 : <i>${dto.username}</i> 조회수 : <i>${dto.readCount}</i>
	</h6>
	<br />
	<h3 class="m-2">
		<b>${dto.title}</b>
	</h3>
	<hr />
	<div class="form-group">
		<div class="m-2">${dto.content}</div>
	</div>

	<hr />

	<!-- 댓글 박스 -->
	<div class="row bootstrap snippets">
		<div class="col-md-12">
			<div class="comment-wrapper">
				<div class="panel panel-info">
					<div class="panel-heading m-2">
						<b>Comment</b>
					</div>
					<div class="panel-body">
						<textarea id="reply__write__form" class="form-control"
							placeholder="write a comment..." rows="2"></textarea>
						<br>
						<button onclick="#" class="btn btn-primary pull-right">댓글쓰기</button>
						<div class="clearfix"></div>
						<hr />

						<!-- 댓글 리스트 시작-->
						<ul id="reply__list" class="media-list">

							<!-- 댓글 아이템 -->
							<li id="reply-1" class="media">
								<div class="media-body">
									<strong class="text-primary">홍길동</strong>
									<p>댓글입니다.</p>
								</div>
								<div class="m-2">

									<i onclick="#" class="material-icons">delete</i>

								</div>
							</li>

						</ul>
						<!-- 댓글 리스트 끝-->
					</div>
				</div>
			</div>

		</div>
	</div>
	<!-- 댓글 박스 끝 -->
</div>
<script>
	function deleteById(boardId){
		// ajax로 delete 요청 (Mehtod : POST)
		//요청과 응답을 json으로
		
		var data = {boardId : boardId}
		//var boardId = ${dto.id} // 이런식으로 쓰면, 나중에 js 파일과 jsp 파일을 분리해낼 수가 없다. 이렇게 하지말자.
		
		$.ajax({
			type: "POST",
			url: "/blog/board?cmd=delete",
			data: JSON.stringify(data),
			contentType: "application/json; charset=utf-8;", 
			dataType: "json" 
	
		}).done(function(result){
			console.log(result);
			if(result.status=="ok"){
				location.href="index.jsp";
			}else{
				alert("삭제에 실패하였습니다.");
			}
		})
	}
</script>
</body>
</html>

package org.hdcd.controller;

import java.util.List;

import org.hdcd.domain.Comment;
import org.hdcd.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RequestMapping("/comment")
@Controller
public class CommentController {
	
	@Autowired
	private final CommentService commentService;

	//댓글 리스트 
	@RequestMapping("/comList")
	@ResponseBody
	public List<Comment> list(int boardNo) throws Exception {
		Comment comment = new Comment();
		comment.setBoardNo(boardNo);
		System.out.println(boardNo);
		List<Comment> list = commentService.list(boardNo);
		return list;
	}
	
	//댓글 쓰기 
	@RequestMapping("/comWrite")
	@ResponseBody
	public void commentWrite(@RequestParam int boardNo, @RequestParam String content, @RequestParam String writer)throws Exception {
		System.out.println(boardNo);
		Comment comment = new Comment();
		comment.setBoardNo(boardNo);
		comment.setContent(content);
		comment.setWriter(writer);
		
		commentService.register(comment);
	}

	@RequestMapping("/comModify")
	@ResponseBody
	public void commentUpdate(@RequestParam int comNo, @RequestParam String content) throws Exception {
		Comment comment = new Comment();
		comment.setComNo(comNo);
		comment.setContent(content);
		commentService.modify(comment);
	}

	@RequestMapping("/comRemove")
	@ResponseBody
	public void commentRemove(@PathVariable int comNo) throws Exception {
		commentService.remove(comNo);
	}
}

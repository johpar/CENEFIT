package org.hdcd.service;

import java.util.List;

import org.hdcd.domain.Board;
import org.hdcd.domain.Comment;

public interface CommentService {

	public void register(Comment comment) throws Exception;

	public void modify(Comment comment) throws Exception;

	public void remove(Integer comNo) throws Exception;
	
	public Comment select(Integer comNo) throws Exception;
	
	public List<Comment> list(Integer boardNo) throws Exception;
	
}

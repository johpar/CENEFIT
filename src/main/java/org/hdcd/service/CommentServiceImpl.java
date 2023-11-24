package org.hdcd.service;

import java.util.List;

import org.hdcd.domain.Comment;
import org.hdcd.mapper.CommentMapper;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class CommentServiceImpl implements CommentService{
	
	private final CommentMapper mapper;
	
	@Override
	public void register(Comment comment) throws Exception {
		// TODO Auto-generated method stub
		
		mapper.create(comment);
	}

	@Override
	public void modify(Comment comment) throws Exception {
		// TODO Auto-generated method stub
		mapper.update(comment);
	}

	@Override
	public void remove(Integer comNo) throws Exception {
		// TODO Auto-generated method stub
		mapper.delete(comNo);
	}

	@Override
	public List<Comment> list(Integer boardNo) throws Exception {
		// TODO Auto-generated method stub
		return mapper.list(boardNo);
	}

	@Override
	public Comment select(Integer comNo) throws Exception {
		return mapper.select(comNo);
	}
	
}

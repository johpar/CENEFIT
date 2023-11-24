package org.hdcd.mapper;

import java.util.List;

import org.hdcd.common.domain.PageRequest;
import org.hdcd.domain.Comment;

public interface CommentMapper {
	public void create(Comment comment) throws Exception;
	public void update(Comment comment) throws Exception;
	public void delete(Integer comNo) throws Exception;
	public Comment select(Integer comNo) throws Exception;
	public List<Comment> list(Integer boardNo) throws Exception;
}

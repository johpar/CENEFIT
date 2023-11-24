package org.hdcd.service;


import org.hdcd.common.domain.PageRequest;
import org.hdcd.domain.Board;

import java.util.List;

public interface BoardService {

	public void register(Board board) throws Exception;

	public Board read(Integer boardNo) throws Exception;

	public void modify(Board board) throws Exception;

	public void remove(Integer boardNo) throws Exception;

	public List<Board> list(PageRequest pageRequest) throws Exception;
	//개시글 건수 반환
	public int count(PageRequest pageRequest) throws Exception;


}

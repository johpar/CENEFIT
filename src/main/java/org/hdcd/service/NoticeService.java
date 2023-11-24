package org.hdcd.service;

import org.hdcd.domain.Notice;

import java.util.List;

public interface NoticeService {

	public void register(Notice notice) throws Exception;

	public Notice read(Integer noticeNo) throws Exception;

	public void modify(Notice notice) throws Exception;

	public void remove(Integer noticeNo) throws Exception;

	public List<Notice> list() throws Exception;

	public void faq(Notice notice) throws Exception;






}

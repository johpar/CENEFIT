package org.hdcd.service;

import lombok.RequiredArgsConstructor;
import org.hdcd.domain.Notice;
import org.hdcd.mapper.NoticeMapper;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class NoticeServiceImpl implements NoticeService {

	private final NoticeMapper mapper;

	@Override
	public void register(Notice notice) throws Exception {
		mapper.create(notice);
	}
	@Override
	public void faq(Notice notice) throws Exception {
		mapper.create(notice);
	}


	@Override
	public Notice read(Integer noticeNo) throws Exception {
		return mapper.read(noticeNo);
	}

	@Override
	public void modify(Notice notice) throws Exception {
		mapper.update(notice);
	}

	@Override
	public void remove(Integer noticeNo) throws Exception {
		mapper.delete(noticeNo);
	}

	@Override
	public List<Notice> list() throws Exception {
		return mapper.list();
	}
	
}

package org.hdcd.service;

import lombok.RequiredArgsConstructor;
import org.hdcd.domain.Pds;
import org.hdcd.mapper.PdsMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@RequiredArgsConstructor
@Service
public class PdsServiceImpl implements PdsService {

	private final PdsMapper mapper;

	@Transactional
	@Override
	public void register(Pds item) throws Exception {
		mapper.create(item);

		String[] files = item.getFiles();

		if (files == null) {
			return;
		}

		for (String fileName : files) {
			mapper.addAttach(fileName);
		}
	}

	@Override
	public Pds read(Integer itemId) throws Exception {
		mapper.updateViewCnt(itemId);

		return mapper.read(itemId);
	}


	@Override
	public void card(Pds item) throws Exception {
		mapper.create(item);
	}

	@Override
	public void benefit(Pds item) throws Exception {
		mapper.create(item);
	}

	@Transactional
	@Override
	public void modify(Pds item) throws Exception {
		mapper.update(item);

		Integer itemId = item.getItemId();

		mapper.deleteAttach(itemId);

		String[] files = item.getFiles();

		if (files == null) {
			return;
		}

		for (String fileName : files) {
			mapper.replaceAttach(fileName, itemId);
		}
	}

	@Transactional
	@Override
	public void remove(Integer itemId) throws Exception {
		mapper.deleteAttach(itemId);
		mapper.delete(itemId);
	}

	@Override
	public List<Pds> list() throws Exception {
		return mapper.list();
	}

	@Override
	public List<String> getAttach(Integer itemId) throws Exception {
		return mapper.getAttach(itemId);
	}

	@Override
	public void updateAttachDownCnt(String fullName) throws Exception {
		mapper.updateAttachDownCnt(fullName);
	}
	
}

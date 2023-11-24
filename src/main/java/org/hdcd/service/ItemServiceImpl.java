package org.hdcd.service;

import lombok.RequiredArgsConstructor;
import org.hdcd.common.domain.PageRequest;
import org.hdcd.domain.Item;
import org.hdcd.mapper.ItemMapper;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class ItemServiceImpl implements ItemService {

	private final ItemMapper mapper;

	@Override
	public void register(Item item) throws Exception {
		mapper.create(item);
	}

	@Override
	public Item read(Integer itemId) throws Exception {
		return mapper.read(itemId);
	}

	@Override
	public void modify(Item item) throws Exception {
		mapper.update(item);
	}

	@Override
	public void remove(Integer itemId) throws Exception {
		mapper.delete(itemId);
	}

	@Override
	public List<Item> list(PageRequest pageRequest) throws Exception {
		// TODO Auto-generated method stub
		return mapper.list(pageRequest);
	}

	@Override
	public String getPreview(Integer itemId) throws Exception {
		return mapper.getPreview(itemId);
	}

	@Override
	public String getPicture(Integer itemId) throws Exception {
		return mapper.getPicture(itemId);
	}

	@Override
	public void faq(Item item) throws Exception {
		mapper.faq(item);
	}

	@Override
	public int count(PageRequest pageRequest) throws Exception {
		// TODO Auto-generated method stub
		return mapper.count(pageRequest);
	}

}

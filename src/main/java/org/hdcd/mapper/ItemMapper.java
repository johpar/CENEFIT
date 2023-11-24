package org.hdcd.mapper;

import org.hdcd.common.domain.PageRequest;
import org.hdcd.domain.Item;

import java.util.List;

public interface ItemMapper {

	public void create(Item item) throws Exception;

	public Item read(Integer itemId) throws Exception;

	public void update(Item item) throws Exception;

	public void delete(Integer itemId) throws Exception;

	public List<Item> list(PageRequest pageRequest) throws Exception;

	public int count(PageRequest pageRequest) throws Exception;

	public String getPicture(Integer itemId) throws Exception;

	public String getPreview(Integer itemId) throws Exception;

	public void faq(Item item) throws Exception;

}

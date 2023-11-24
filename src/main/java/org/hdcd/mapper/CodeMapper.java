package org.hdcd.mapper;

import org.hdcd.common.domain.CodeLabelValue;

import java.util.List;

public interface CodeMapper {
	
	public List<CodeLabelValue> getCodeGroupList() throws Exception;
	
	public List<CodeLabelValue> getCodeList(String groupCode) throws Exception;
	
}

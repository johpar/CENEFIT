package org.hdcd.service;

import org.hdcd.common.domain.CodeLabelValue;

import java.util.List;

public interface CodeService {

	public List<CodeLabelValue> getCodeGroupList() throws Exception;

	public List<CodeLabelValue> getCodeList(String groupCode) throws Exception;
	
}

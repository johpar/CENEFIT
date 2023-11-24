package org.hdcd.service;

import lombok.RequiredArgsConstructor;
import org.hdcd.common.domain.CodeLabelValue;
import org.hdcd.mapper.CodeMapper;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class CodeServiceImpl implements CodeService {

	private final CodeMapper mapper;

	@Override
	public List<CodeLabelValue> getCodeGroupList() throws Exception {
		return mapper.getCodeGroupList();
	}

	@Override
	public List<CodeLabelValue> getCodeList(String groupCode) throws Exception {
		return mapper.getCodeList(groupCode);
	}
	
}

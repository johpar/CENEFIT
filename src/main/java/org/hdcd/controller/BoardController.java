package org.hdcd.controller;


import lombok.RequiredArgsConstructor;
import org.hdcd.common.domain.CodeLabelValue;
import org.hdcd.common.domain.PageRequest;
import org.hdcd.common.domain.Pagination;
import org.hdcd.domain.Board;
import org.hdcd.service.BoardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Controller
@RequestMapping("/board")
public class BoardController {
	@Autowired
	private final BoardService service;

	@GetMapping("/register")
	public void registerForm(Model model) throws Exception {
		Board board = new Board();

		model.addAttribute(board);
	}
	@PostMapping("/register")
	public String register(Board board, RedirectAttributes rttr) throws Exception {
		service.register(board);

		rttr.addFlashAttribute("msg", "SUCCESS");
		return "redirect:/board/list";
	}
	@GetMapping("/list")
	public void list(@ModelAttribute("pgrq") PageRequest pageRequest, Model model) throws Exception {

		model.addAttribute("list", service.list(pageRequest));

		Pagination pagination = new Pagination();
		pagination.setPageRequest(pageRequest);
//	    네비게이션 정보에 검색처리된 게시물 갯수 저장
		pagination.setTotalCount(service.count(pageRequest));

		model.addAttribute("pagination", pagination);
//		검색유형의 코드명과 코드값 정의
		List<CodeLabelValue> searchTypeCodeValueList = new ArrayList<CodeLabelValue>();
		searchTypeCodeValueList.add(new CodeLabelValue("tcw", "Title OR Content OR Writer"));
//		searchTypeCodeValueList.add(new CodeLabelValue("n", "---"));
		searchTypeCodeValueList.add(new CodeLabelValue("t", "Title"));
		searchTypeCodeValueList.add(new CodeLabelValue("c", "Content"));
		searchTypeCodeValueList.add(new CodeLabelValue("w", "Writer"));
		searchTypeCodeValueList.add(new CodeLabelValue("tc", "Title OR Content"));
		searchTypeCodeValueList.add(new CodeLabelValue("cw", "Content OR Writer"));


		model.addAttribute("searchTypeCodeValueList", searchTypeCodeValueList);
	}

	@GetMapping("/read")
	public void read(int boardNo, @ModelAttribute("pgrq") PageRequest pageRequest, Model model) throws Exception {
		Board board = service.read(boardNo);
		model.addAttribute(board);
	}

	@PostMapping("/remove")
	public String remove(int boardNo, PageRequest pageRequest, RedirectAttributes rttr) throws Exception {
		service.remove(boardNo);

		rttr.addAttribute("page", pageRequest.getPage());
		rttr.addAttribute("sizePerPage", pageRequest.getSizePerPage());
//		검색어와유형 뷰에 전달
		rttr.addAttribute("searchType", pageRequest.getSearchType());
		rttr.addAttribute("keyword", pageRequest.getKeyword());

		rttr.addFlashAttribute("msg", "SUCCESS");

		return "redirect:/board/list";
	}
	@GetMapping("/modify")
	public void modifyForm(int boardNo, @ModelAttribute("pgrq") PageRequest pageRequest, Model model) throws Exception {

		/*
		model.addAttribute(service.read(boardNo));
		*/

		Board board = service.read(boardNo);

		model.addAttribute(board);
	}
	@PostMapping("/modify")
	public String modify(Board board, PageRequest pageRequest, RedirectAttributes rttr) throws Exception {
		service.modify(board);

		rttr.addAttribute("page", pageRequest.getPage());
		rttr.addAttribute("sizePerPage", pageRequest.getSizePerPage());
//		검색어와유형 뷰에 전달
		rttr.addAttribute("searchType", pageRequest.getSearchType());
		rttr.addAttribute("keyword", pageRequest.getKeyword());

		rttr.addFlashAttribute("msg", "SUCCESS");

		return "redirect:/board/list";
	}

}
package org.hdcd.controller;

import lombok.RequiredArgsConstructor;
import org.apache.commons.io.IOUtils;
import org.hdcd.common.domain.CodeLabelValue;
import org.hdcd.common.domain.PageRequest;
import org.hdcd.common.domain.Pagination;
import org.hdcd.domain.Item;
import org.hdcd.prop.ShopProperties;
import org.hdcd.service.ItemService;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@RequiredArgsConstructor
@Controller
@RequestMapping("/item")
public class ItemController {

	private final ItemService itemService;

	private final ShopProperties shopProperties;

	@GetMapping("/register")
	public String registerForm(Model model) {
		model.addAttribute(new Item());

		return "item/register";
	}

	@PostMapping("/register")
	public String register(Item item, RedirectAttributes rttr) throws Exception {
		MultipartFile pictureFile = item.getPicture();
		MultipartFile previewFile = item.getPreview();

		String createdPictureFilename = uploadFile(pictureFile.getOriginalFilename(), pictureFile.getBytes());
		String createdPreviewFilename = uploadFile(previewFile.getOriginalFilename(), previewFile.getBytes());

		item.setPictureUrl(createdPictureFilename);
		item.setPreviewUrl(createdPreviewFilename);

		itemService.register(item);

		rttr.addFlashAttribute("msg", "SUCCESS");
		return "redirect:/item/list";
	}

	@GetMapping("/list")
	public void list(@ModelAttribute("pgrq") PageRequest pageRequest, Model model) throws Exception {

		model.addAttribute("list", itemService.list(pageRequest));

		Pagination pagination = new Pagination();
		pagination.setPageRequest(pageRequest);
//	    네비게이션 정보에 검색처리된 게시물 갯수 저장
		pagination.setTotalCount(itemService.count(pageRequest));

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
	public void read(Integer itemId, @ModelAttribute("pgrq") PageRequest pageRequest, Model model) throws Exception {
		Item item = itemService.read(itemId);

		model.addAttribute(item);

	}


	@GetMapping("/modify")
	public void modifyForm(Integer itemId, @ModelAttribute("pgrq") PageRequest pageRequest, Model model) throws Exception {
		Item item = itemService.read(itemId);

		model.addAttribute(item);

	}

//	@GetMapping("/faq")
//	public String faq(Item item, Model model) throws Exception {
//		itemService.register(item);
//
//		model.addAttribute(item);
//		return "item/faq";
//	}

	@GetMapping("/faq")
	public void faq(Model model,PageRequest pageRequest) throws Exception {
		model.addAttribute("faq", itemService.list(pageRequest));
	}

	@PostMapping("/modify")
	public String modify(Item item, PageRequest pageRequest, RedirectAttributes rttr) throws Exception {
		MultipartFile pictureFile = item.getPicture();

		if (pictureFile != null && pictureFile.getSize() > 0) {
			String createdFilename = uploadFile(pictureFile.getOriginalFilename(), pictureFile.getBytes());
			item.setPictureUrl(createdFilename);
		}
		MultipartFile previewFile = item.getPreview();
		if (previewFile != null && previewFile.getSize() > 0) {
			String createdFilename = uploadFile(previewFile.getOriginalFilename(), previewFile.getBytes());
			item.setPreviewUrl(createdFilename);
		}

		itemService.modify(item);

		rttr.addAttribute("page", pageRequest.getPage());
		rttr.addAttribute("sizePerPage", pageRequest.getSizePerPage());
//		검색어와유형 뷰에 전달
		rttr.addAttribute("searchType", pageRequest.getSearchType());
		rttr.addAttribute("keyword", pageRequest.getKeyword());
		rttr.addFlashAttribute("msg", "SUCCESS");

		return "redirect:/item/list";
	}

	@GetMapping("/remove")
	public String removeForm(Integer itemId, Model model) throws Exception {
		Item item = itemService.read(itemId);

		model.addAttribute(item);

		return "item/remove";
	}

	@PostMapping("/remove")
	public String remove(Item item, PageRequest pageRequest, RedirectAttributes rttr) throws Exception {
		itemService.remove(item.getItemId());
		rttr.addAttribute("page", pageRequest.getPage());
		rttr.addAttribute("sizePerPage", pageRequest.getSizePerPage());
//		검색어와유형 뷰에 전달
		rttr.addAttribute("searchType", pageRequest.getSearchType());
		rttr.addAttribute("keyword", pageRequest.getKeyword());

		rttr.addFlashAttribute("msg", "SUCCESS");
		return "redirect:/item/list";
	}

	private String uploadFile(String originalName, byte[] fileData) throws Exception {
		UUID uid = UUID.randomUUID();

		String createdFileName = uid.toString() + "_" + originalName;

		String uploadPath = shopProperties.getUploadPath();
		File target = new File(uploadPath, createdFileName);

		FileCopyUtils.copy(fileData, target);

		return createdFileName;
	}

	@GetMapping("/display")
	@ResponseBody
	public ResponseEntity<byte[]> displayFile(Integer itemId) throws Exception {
		InputStream in = null;
		ResponseEntity<byte[]> entity = null;

		String fileName = itemService.getPreview(itemId);

		try {
			String formatName = fileName.substring(fileName.lastIndexOf(".") + 1);

			MediaType mType = getMediaType(formatName);

			HttpHeaders headers = new HttpHeaders();

			String uploadPath = shopProperties.getUploadPath();
			in = new FileInputStream(uploadPath + File.separator + fileName);

			if (mType != null) {
				headers.setContentType(mType);
			}

			entity = new ResponseEntity<byte[]>(IOUtils.toByteArray(in), headers, HttpStatus.CREATED);
		} catch (Exception e) {
			e.printStackTrace();
			entity = new ResponseEntity<byte[]>(HttpStatus.BAD_REQUEST);
		} finally {
			in.close();
		}
		return entity;
	}

	private MediaType getMediaType(String formatName){
		if(formatName != null) {
			if(formatName.equals("JPG")) {
				return MediaType.IMAGE_JPEG;
			}

			if(formatName.equals("GIF")) {
				return MediaType.IMAGE_GIF;
			}

			if(formatName.equals("PNG")) {
				return MediaType.IMAGE_PNG;
			}
		}

		return null;
	}

	@GetMapping("/picture")
	@ResponseBody
	public ResponseEntity<byte[]> pictureFile(Integer itemId) throws Exception {
		InputStream in = null;
		ResponseEntity<byte[]> entity = null;

		String fileName = itemService.getPicture(itemId);

		try {
			String formatName = fileName.substring(fileName.lastIndexOf(".") + 1);

			MediaType mType = getMediaType(formatName);

			HttpHeaders headers = new HttpHeaders();

			String uploadPath = shopProperties.getUploadPath();
			in = new FileInputStream(uploadPath + File.separator + fileName);

			if (mType != null) {
				headers.setContentType(mType);
			}

			entity = new ResponseEntity<byte[]>(IOUtils.toByteArray(in), headers, HttpStatus.CREATED);
		} catch (Exception e) {
			e.printStackTrace();
			entity = new ResponseEntity<byte[]>(HttpStatus.BAD_REQUEST);
		} finally {
			in.close();
		}
		return entity;
	}

}

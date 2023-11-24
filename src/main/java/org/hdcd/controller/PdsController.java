package org.hdcd.controller;

import lombok.RequiredArgsConstructor;
import org.apache.commons.io.IOUtils;
import org.hdcd.common.util.UploadFileUtils;
import org.hdcd.domain.Pds;
import org.hdcd.prop.ShopProperties;
import org.hdcd.service.PdsService;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.FileInputStream;
import java.io.InputStream;
import java.util.List;

@RequiredArgsConstructor
@Controller
@RequestMapping("/pds")
public class PdsController {

	private final PdsService pdsService;

	private final ShopProperties shopProperties;

	@GetMapping("/list")
	public void list(Model model) throws Exception {
		List<Pds> itemList = this.pdsService.list();

		model.addAttribute("itemList", itemList);
	}

	@GetMapping("/register")
	public String registerForm(Model model) {
		model.addAttribute(new Pds());

		return "pds/register";
	}

	@GetMapping("/card")
	public void card(Model model) throws Exception {
		model.addAttribute("card", pdsService.list());
	}

	@GetMapping("/benefit")
	public void benefit(Model model) throws Exception {
		model.addAttribute("benefit", pdsService.list());
	}



	@PostMapping("/register")
	public String register(Pds pds, RedirectAttributes rttr) throws Exception {
		this.pdsService.register(pds);

		rttr.addFlashAttribute("msg", "SUCCESS");

	    return "redirect:/pds/list";
	}

	@GetMapping("/read")
	public String read(Integer itemId, Model model) throws Exception {
		Pds pds = this.pdsService.read(itemId);

		model.addAttribute(pds);

		return "pds/read";
	}

	@GetMapping("/modify")
	public String modifyForm(Integer itemId, Model model) throws Exception {
		Pds pds = this.pdsService.read(itemId);

		model.addAttribute(pds);

		return "pds/modify";
	}

	@PostMapping("/modify")
	public String modify(Pds pds, RedirectAttributes rttr) throws Exception {
		this.pdsService.modify(pds);

		rttr.addFlashAttribute("msg", "SUCCESS");

	    return "redirect:/pds/list";
	}

	@GetMapping("/remove")
	public String removeForm(Integer itemId, Model model) throws Exception {
		Pds pds = this.pdsService.read(itemId);

		model.addAttribute(pds);

		return "pds/remove";
	}

	@PostMapping("/remove")
	public String remove(Pds pds, RedirectAttributes rttr) throws Exception {
		this.pdsService.remove(pds.getItemId());

		rttr.addFlashAttribute("msg", "SUCCESS");

	    return "redirect:/pds/list";
	}

	@ResponseBody
	@PostMapping(value = "/uploadAjax", produces = "text/plain;charset=UTF-8")
	public ResponseEntity<String> uploadAjax(MultipartFile file) throws Exception {
		String savedName = UploadFileUtils.uploadFile(shopProperties.getUploadPath(), file.getOriginalFilename(), file.getBytes());

		return new ResponseEntity<String>(savedName, HttpStatus.CREATED);
	}

	@ResponseBody
	@GetMapping("/downloadFile")
	public ResponseEntity<byte[]> downloadFile(String fullName) throws Exception {
		InputStream in = null;
		ResponseEntity<byte[]> entity = null;

		pdsService.updateAttachDownCnt(fullName);
		
		try {
			HttpHeaders headers = new HttpHeaders();

			in = new FileInputStream(shopProperties.getUploadPath() + fullName);

			String fileName = fullName.substring(fullName.indexOf("_") + 1);
			headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
			headers.add("Content-Disposition", "attachment; filename=\"" + new String(fileName.getBytes("UTF-8"), "ISO-8859-1") + "\"");

			entity = new ResponseEntity<byte[]>(IOUtils.toByteArray(in), headers, HttpStatus.CREATED);
		} catch (Exception e) {
			e.printStackTrace();
			entity = new ResponseEntity<byte[]>(HttpStatus.BAD_REQUEST);
		} finally {
			in.close();
		}
		
		return entity;
	}

	@ResponseBody
	@GetMapping("/getAttach/{itemId}")
	public List<String> getAttach(@PathVariable("itemId") Integer itemId) throws Exception {
		return pdsService.getAttach(itemId);
	}
	
}

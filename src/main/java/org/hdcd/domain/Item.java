package org.hdcd.domain;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;

@Getter
@Setter
@ToString
public class Item {

	private int boardNo;
	private String title;
	private String content;
	private String writer;

	private Integer itemId;

	private String itemName;

	private Integer price;

	private String description;

	private MultipartFile picture;

	private String pictureUrl;

	private MultipartFile preview;

	private String previewUrl;

	private LocalDateTime regDate;


}

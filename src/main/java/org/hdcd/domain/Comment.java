package org.hdcd.domain;


import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;

@Getter
@Setter
@ToString
public class Comment {

    private int comNo;
    private int boardNo;
    private String content;
    private String writer;
//    private LocalDateTime regDate;

}
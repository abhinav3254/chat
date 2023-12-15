package com.multi.entity;

import lombok.Data;

import javax.persistence.*;


@Data
@Entity
public class FileEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String fileName;

    @Lob
    private byte[] fileContent;

}

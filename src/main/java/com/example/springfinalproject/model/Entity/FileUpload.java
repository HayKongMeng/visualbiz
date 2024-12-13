package com.example.springfinalproject.model.Entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class FileUpload {
    private String fileName;
    private String fileUrl;
    private String fileType;
    private Long fileSize;
}

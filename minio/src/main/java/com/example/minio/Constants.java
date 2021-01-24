package com.example.minio;

import java.nio.file.Paths;

public class Constants {

  public static final String UPLOAD_DIR = Paths.get("").toAbsolutePath().getParent().toString() + "/src/main/resources/uploads/";
}

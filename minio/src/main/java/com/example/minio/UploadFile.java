package com.example.minio;

import org.apache.commons.io.IOUtils;
import org.jboss.resteasy.plugins.providers.multipart.InputPart;
import org.jboss.resteasy.plugins.providers.multipart.MultipartFormDataInput;

import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.Response;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

public class UploadFile {

  private final String UPLOADED_FILE_PATH = "/tmp/";

  public Response uploadFile(MultipartFormDataInput input) {
    String fileName = "";

    List<InputPart> list = input.getFormDataMap().get("file");
    for (InputPart inputPart : list) {
      try {

        MultivaluedMap<String, String> header = inputPart.getHeaders();
        fileName = getFileName(header);

        //convert the uploaded file to inputstream
        InputStream inputStream = inputPart.getBody(InputStream.class, null);

        byte[] bytes = IOUtils.toByteArray(inputStream);

        //constructs upload file path
        fileName = UPLOADED_FILE_PATH + fileName;

        writeFile(bytes, fileName);

        System.out.println("Done");

      } catch (IOException e) {
        e.printStackTrace();
      }

    }

    return Response.status(200).entity("foi caraio" + fileName).build();
  }

  /**
   * Exemplo de header:
   * {
   * Content-Type=[image/png],
   * Content-Disposition=[form-data; name="file"; filename="filename.extension"]
   * }
   * <p>
   * Pega o header, mapeia a propriedade "Content-Disposition";
   * Quando o encontrar uma propriedade "filename",
   * retorna o nome do arquivo;
   *
   * @param header Header da requisição;
   * @return String nome do arquivo
   */
  private String getFileName(MultivaluedMap<String, String> header) {

    // Split no header separando os valores
    String[] contentDisposition = header.getFirst("Content-Disposition").split(";");

    // Procura por um header com "filename"
    for (String filename : contentDisposition) {
      if ((filename.trim().startsWith("filename"))) {

        // Exemplo: Chave=Valor
        String[] name = filename.split("=");

        // retorna o nome do arquivo
        return name[1].trim().replaceAll("\"", "");
      }
    }
    return null;
  }

  //save to somewhere
  private void writeFile(byte[] content, String filename) throws IOException {

    File file = new File(filename);

    if (!file.exists()) {
      file.createNewFile();
    }

    FileOutputStream fop = new FileOutputStream(file);

    fop.write(content);
    fop.flush();
    fop.close();

  }
}
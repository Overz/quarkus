package com.example.minio;

import org.jboss.resteasy.plugins.providers.multipart.InputPart;

import javax.ws.rs.core.MultivaluedMap;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.List;

import static com.example.minio.Constants.UPLOAD_DIR;

public class UploadFileProcess {

  /**
   * Separa todos os headers de um arquivo para ser escrito durante a requisição.
   *
   * @param inputs Lista de multipart/form-data, específica para um header chamado "file"
   * @return String[] um array de duas posições, 1 - path, 2 - filename
   */
  public static String[] getFileHeaders(List<InputPart> inputs) {

    for (InputPart input : inputs) {
      try {
        MultivaluedMap<String, String> fileHeader = input.getHeaders();
        InputStream stream = input.getBody(InputStream.class, null);

        byte[] content = stream.readAllBytes();
        String filename = getFileName(fileHeader);
        if (filename != null) {
          String path = UPLOAD_DIR + filename;
          System.out.println("File Path:" + path);
          writeFile(content, path);
          return new String[]{path, filename};
        }

      } catch (Exception e) {
        System.out.println(e.getMessage());
      }
    }

    return null;
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
  private static String getFileName(MultivaluedMap<String, String> header) {

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

  /**
   * Escreve um arquivo em um diretório
   *
   * @param content conteudo em bytes
   * @param path    caminho + nome do arvui para criar
   */
  private static void writeFile(byte[] content, String path) {
    try {
      File file = new File(path);

      if (!file.exists()) {
        file.createNewFile();
      }

      FileOutputStream stream = new FileOutputStream(file);

      stream.write(content);
      // força a saida dos dados de escrita em cima do arquivo
      stream.flush();
      stream.close();

    } catch (Exception e) {
      System.out.println(e.getMessage());
    }
  }


}

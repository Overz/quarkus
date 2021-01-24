package com.example.minio;

import io.minio.*;
import org.eclipse.microprofile.config.inject.ConfigProperty;
import org.jboss.resteasy.plugins.providers.multipart.MultipartFormDataInput;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.InputStream;

@Path("/minio")
public class MinioService {

  @ConfigProperty(name = "quarkus.minio.port")
  Integer MINIO_PORT;

  @ConfigProperty(name = "quarkus.minio.url")
  String MINIO_URL;

  @ConfigProperty(name = "quakus.minio.bucket-name")
  String MINIO_BUCKET;

  @ConfigProperty(name = "quarkus.minio.access-key")
  String MINIO_ACCESS_KEY;

  @ConfigProperty(name = "quarkus.minio.secret-key")
  String MINIO_SECRET_KEY;

  MinioClient minio;

  private MinioClient accessMinio(MinioClient minio) {
    if (minio == null) {
      return MinioClient.builder().endpoint(MINIO_URL, MINIO_PORT, false)
          .credentials(MINIO_ACCESS_KEY, MINIO_SECRET_KEY).build();
    }
    return minio;
  }

  @POST
  @Path("/bucket")
  @Produces(MediaType.APPLICATION_JSON)
  public Response upload() {
    try {
      minio = accessMinio(minio);

      boolean exists = minio.bucketExists(BucketExistsArgs.builder().bucket(MINIO_BUCKET).build());

      if (!exists) {
        minio.makeBucket(MakeBucketArgs.builder().bucket(MINIO_BUCKET).build());
      }

      minio.listBuckets().forEach((e) -> System.out.println("Buckets: " + e.name()));
    } catch (Exception e) {
      System.out.println(e.getMessage());
    }
    return Response.ok().entity("{ok: true}").build();
  }

  @POST
  @Path("/upload")
  @Consumes(MediaType.MULTIPART_FORM_DATA)
  @Produces(MediaType.APPLICATION_JSON)
  public Response uploadObject(MultipartFormDataInput multipart) {
    try {
      String[] res = UploadFileProcess.getFileHeaders(multipart.getFormDataMap().get("file"));
      if (res.length > 0) {
        String path = res[0];
        String filename = res[1];
        minio = accessMinio(minio);
        minio.uploadObject(UploadObjectArgs.builder().bucket(MINIO_BUCKET).object("img/" + filename).filename(path).build());
        return Response.ok().entity("{ok: true}").build();
      }
    } catch (Exception e) {
      System.out.println(e.getMessage());
    }

    return Response.status(500).build();
  }

  public String getObject(String name) {
    try (InputStream is = minio.getObject(
        GetObjectArgs.builder()
            .bucket(name)
            .object("")
            .build());
    ) {
      // Do whatever you want...
      System.out.println("foi");
    } catch (Exception e) {
      System.out.println(e.getMessage());
    }
    return "";
  }
}

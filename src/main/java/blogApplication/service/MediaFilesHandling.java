package blogApplication.service;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.GetObjectRequest;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.amazonaws.services.s3.model.S3Object;
import org.springframework.stereotype.Service;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;

@Service
public class MediaFilesHandling {
    private AWSCredentials credentials;
    private AmazonS3 s3Client;
    private static String accessKey = "AKIASETDCCVE47BKBEO3";
    private static String secretKey = "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";

    public MediaFilesHandling(){
        credentials = new BasicAWSCredentials(MediaFilesHandling.accessKey,
                MediaFilesHandling.secretKey);
        s3Client = AmazonS3ClientBuilder.standard()
                .withRegion("us-east-2")
                .withCredentials(new AWSStaticCredentialsProvider(credentials))
                .build();
    }

    public void sendMediaData(MultipartFile multipart) throws IOException {
        InputStream is = multipart.getInputStream();

        PutObjectRequest putObjectRequest = new PutObjectRequest(
                "media-yerm87", "blog/media/test.jpg", is, new ObjectMetadata()
        );

        s3Client.putObject(putObjectRequest);
    }

    public void getMediaData(HttpServletResponse response, String path) throws IOException {
        GetObjectRequest request = new GetObjectRequest(
                "media-yerm87", "blog/media/" + path);
        S3Object s3Object = s3Client.getObject(request);
        InputStream is = s3Object.getObjectContent();
        FileCopyUtils.copy(is, response.getOutputStream());
    }
}

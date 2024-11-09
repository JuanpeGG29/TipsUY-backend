package TipsUY.backend.services;

import com.ibm.cloud.objectstorage.ClientConfiguration;
import com.ibm.cloud.objectstorage.auth.AWSStaticCredentialsProvider;
import com.ibm.cloud.objectstorage.auth.BasicAWSCredentials;
import com.ibm.cloud.objectstorage.services.s3.AmazonS3;
import com.ibm.cloud.objectstorage.services.s3.AmazonS3ClientBuilder;
import com.ibm.cloud.objectstorage.services.s3.model.ObjectListing;
import com.ibm.cloud.objectstorage.services.s3.model.ObjectMetadata;
import com.ibm.cloud.objectstorage.services.s3.model.PutObjectRequest;
import com.ibm.cloud.objectstorage.services.s3.model.S3ObjectSummary;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class IBMCloudStorageService {

    private AmazonS3 s3Client;

    public IBMCloudStorageService(String endpoint, String accessKey, String secretKey) {
        BasicAWSCredentials credentials = new BasicAWSCredentials(accessKey, secretKey);
        ClientConfiguration clientConfig = new ClientConfiguration().withRequestTimeout(5000);
        this.s3Client = AmazonS3ClientBuilder.standard()
                .withEndpointConfiguration(new AmazonS3ClientBuilder.EndpointConfiguration(endpoint, "br-sao"))
                .withCredentials(new AWSStaticCredentialsProvider(credentials))
                .withPathStyleAccessEnabled(true)
                .withClientConfiguration(clientConfig)
                .build();
    }

    public List<String> listFileNames(String bucketName) {
        System.out.println("ENTRE A LIST FILES ICOS");
        List<String> fileNames = new ArrayList<>();
        ObjectListing objectListing = s3Client.listObjects(bucketName);
        for (S3ObjectSummary os : objectListing.getObjectSummaries()) {
            fileNames.add(os.getKey());
        }
        return fileNames;
    }

    public String getFileUrl(String bucketName, String fileName) {
        // Remueve cualquier duplicación de nombre en el archivo, si es que ocurre
        String cleanedFileName = fileName.replaceAll(":.+:", ":");

        return String.format("https://%s.s3.%s.cloud-object-storage.appdomain.cloud/%s",
                bucketName, "br-sao", cleanedFileName);
    }

    public String uploadFile(String bucketName, String fileName, byte[] content) throws IOException {
        ObjectMetadata metadata = new ObjectMetadata();
        metadata.setContentLength(content.length);

        PutObjectRequest putObjectRequest = new PutObjectRequest(bucketName, fileName, new ByteArrayInputStream(content), metadata);
        s3Client.putObject(putObjectRequest);

        return getFileUrl(bucketName, fileName);
    }
}

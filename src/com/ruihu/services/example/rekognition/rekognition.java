package com.ruihu.services.example.rekognition;

import java.util.Iterator;
import java.util.List;

import com.amazonaws.regions.Regions;
import com.amazonaws.services.rekognition.AmazonRekognition;
import com.amazonaws.services.rekognition.AmazonRekognitionClientBuilder;
import com.amazonaws.services.rekognition.model.CompareFacesMatch;
import com.amazonaws.services.rekognition.model.CompareFacesRequest;
import com.amazonaws.services.rekognition.model.CompareFacesResult;
import com.amazonaws.services.rekognition.model.Image;
import com.amazonaws.services.rekognition.model.S3Object;
import com.ruihu.services.common.utils.CredentialProviderImp;

public class rekognition {

	
	public static void main(String[] args) {
		
		new rekognition().faceReconigze();
	}
	
	
	public void faceReconigze() {
		AmazonRekognition rekClient = AmazonRekognitionClientBuilder.standard()
																   .withRegion(Regions.US_EAST_1)
																   .withCredentials(new CredentialProviderImp()).build();
		
		//face recognition 
		CompareFacesRequest compareFaces = new CompareFacesRequest();

		Image sourceImage = null;
		Image targetImage = null;
		
		try {
			//Get photo from S3, do what you want to get source compare
			sourceImage = new Image().withS3Object(new S3Object().withBucket("xci-test-1231241234").withName("1.jpeg"));
			targetImage = new Image().withS3Object(new S3Object().withBucket("xci-test-1231241234").withName("2.jpeg"));

			//SourceImage , targetImage and simlarity
			compareFaces.setSourceImage(sourceImage);
			compareFaces.setTargetImage(targetImage);
			//Special any number you expect, the rang should 1 - 100, if the the result number is higher than '49' then will return nothing.
			compareFaces.withSimilarityThreshold(Float.valueOf("49"));


			CompareFacesResult compareFaces2 = rekClient.compareFaces(compareFaces);
			List<CompareFacesMatch> faceMatches = compareFaces2.getFaceMatches();
			Iterator<CompareFacesMatch> iterator = faceMatches.iterator();
			while(iterator.hasNext()) {
				CompareFacesMatch match = iterator.next();
				//get final similar result
				System.out.println(match.getSimilarity());
			}		
		} catch (Exception e) {
			System.out.println("error: " + e.getMessage());
		}

	}
	
	
}

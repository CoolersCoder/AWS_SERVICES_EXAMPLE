package com.ruihu.services.example.rekognition;

import java.util.Iterator;
import java.util.List;

import com.amazonaws.regions.Regions;
import com.amazonaws.services.rekognition.AmazonRekognition;
import com.amazonaws.services.rekognition.AmazonRekognitionClientBuilder;
import com.amazonaws.services.rekognition.model.CompareFacesMatch;
import com.amazonaws.services.rekognition.model.CompareFacesRequest;
import com.amazonaws.services.rekognition.model.CompareFacesResult;
import com.amazonaws.services.rekognition.model.DetectLabelsRequest;
import com.amazonaws.services.rekognition.model.DetectLabelsResult;
import com.amazonaws.services.rekognition.model.DetectTextRequest;
import com.amazonaws.services.rekognition.model.DetectTextResult;
import com.amazonaws.services.rekognition.model.Image;
import com.amazonaws.services.rekognition.model.Label;
import com.amazonaws.services.rekognition.model.S3Object;
import com.amazonaws.services.rekognition.model.TextDetection;
import com.ruihu.services.common.utils.CredentialProviderImp;
import com.ruihu.services.common.utils.InternPrxoy;
/**
 * 
 * @author CoolersCoder
 * @since 01/26/2018
 */
public class rekognition {

	static {
		InternPrxoy.proxy();
	}

	public static void main(String[] args) {
		AmazonRekognition rekClient = AmazonRekognitionClientBuilder.standard()
				   .withRegion(Regions.US_EAST_1)
				   .withCredentials(new CredentialProviderImp()).build();
		
//		//Test 1
//		new rekognition().faceReconigze(rekClient);
		
		//Test 2
		new rekognition().textDetect(rekClient);
		
//		//Test 3
//	     new rekognition().detectLable(rekClient);
	}

	public void faceReconigze(AmazonRekognition rekClient) {

		// face recognition
		CompareFacesRequest compareFaces = new CompareFacesRequest();

		Image sourceImage = null;
		Image targetImage = null;

		try {
			// Get photo from S3, do what you want to get source compare
			sourceImage = new Image().withS3Object(new S3Object().withBucket("xci-test-1231241234").withName("1.jpeg"));
			targetImage = new Image().withS3Object(new S3Object().withBucket("xci-test-1231241234").withName("2.jpeg"));

			// SourceImage , targetImage and simlarity
			compareFaces.setSourceImage(sourceImage);
			compareFaces.setTargetImage(targetImage);
			// Special any number you expect, the rang should 1 - 100, if the the result
			// number is higher than '49' then will return nothing.
			compareFaces.withSimilarityThreshold(Float.valueOf("49"));

			CompareFacesResult compareFaces2 = rekClient.compareFaces(compareFaces);
			List<CompareFacesMatch> faceMatches = compareFaces2.getFaceMatches();
			Iterator<CompareFacesMatch> iterator = faceMatches.iterator();
			while (iterator.hasNext()) {
				CompareFacesMatch match = iterator.next();
				// get final similar result
				System.out.println(match.getSimilarity());
			}
		} catch (Exception e) {
			System.out.println("error: " + e.getMessage());
		}

	}
	
	
    /**
     * Detect text in the image
     */
	public void textDetect(AmazonRekognition rekClient) {

		DetectTextRequest detectTextRequest = new DetectTextRequest();
		detectTextRequest.setImage(new Image().withS3Object(new S3Object().withBucket("xci-test-1231241234").withName("text.jpeg")));
		
		DetectTextResult detectText = rekClient.detectText(detectTextRequest);
		List<TextDetection> textDetections = detectText.getTextDetections();
		
		textDetections.stream().forEach(System.out::println);
		
//Blow is detect result, focus on properties like "DetectedText", "Confidence" and "Type"
//		{DetectedText: Arnold chwarzenegger,Type: LINE,Id: 0,Confidence: 91.054245,Geometry: {BoundingBox: {Width: 0.8652344,Height: 0.07372621,Left: 0.064266406,Top: 0.7744366},Polygon: [{X: 0.064266406,Y: 0.7744366}, {X: 0.92950076,Y: 0.78566116}, {X: 0.9280733,Y: 0.8593874}, {X: 0.062838934,Y: 0.84816283}]}}
//		{DetectedText: arnoldschnitzel,Type: LINE,Id: 1,Confidence: 99.24315,Geometry: {BoundingBox: {Width: 0.41814068,Height: 0.051430997,Left: 0.29159632,Top: 0.86422235},Polygon: [{X: 0.29159632,Y: 0.86422235}, {X: 0.709737,Y: 0.8640867}, {X: 0.7097619,Y: 0.9155177}, {X: 0.29162124,Y: 0.91565335}]}}
//		{DetectedText: Arnold,Type: WORD,Id: 2,ParentId: 0,Confidence: 96.465096,Geometry: {BoundingBox: {Width: 0.24764886,Height: 0.06696534,Left: 0.06421223,Top: 0.77730966},Polygon: [{X: 0.06421598,Y: 0.777041}, {X: 0.3121137,Y: 0.7776519}, {X: 0.311868,Y: 0.8444497}, {X: 0.063970305,Y: 0.8438388}]}}
//		{DetectedText: chwarzenegger,Type: WORD,Id: 3,ParentId: 0,Confidence: 85.64338,Geometry: {BoundingBox: {Width: 0.5601218,Height: 0.067014456,Left: 0.37072337,Top: 0.78697735},Polygon: [{X: 0.3881651,Y: 0.78646785}, {X: 0.92851096,Y: 0.788324}, {X: 0.92817307,Y: 0.854235}, {X: 0.38782716,Y: 0.85237885}]}}
//		{DetectedText: arnoldschnitzel,Type: WORD,Id: 4,ParentId: 1,Confidence: 99.24315,Geometry: {BoundingBox: {Width: 0.4147768,Height: 0.05099833,Left: 0.2928502,Top: 0.8648192},Polygon: [{X: 0.29159632,Y: 0.86422235}, {X: 0.709737,Y: 0.8640867}, {X: 0.7097619,Y: 0.9155177}, {X: 0.29162124,Y: 0.91565335}]}}

	}
	
	
	public void detectLable(AmazonRekognition rekClient) {
		DetectLabelsRequest detectLabelsRequest = new DetectLabelsRequest();
		
		detectLabelsRequest.setImage(new Image().withS3Object(new S3Object().withBucket("xci-test-1231241234").withName("label.png")));
		detectLabelsRequest.setMaxLabels(2);
		detectLabelsRequest.setMinConfidence(Float.valueOf("10"));
		
		//Only png and JPEG
		DetectLabelsResult detectLabels = rekClient.detectLabels(detectLabelsRequest);
		List<Label> labels = detectLabels.getLabels();

		System.out.println(detectLabels);
		
	}
	
	

}

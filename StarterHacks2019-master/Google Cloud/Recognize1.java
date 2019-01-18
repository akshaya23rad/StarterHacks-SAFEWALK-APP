
package com.example.speech;

import com.google.api.gax.longrunning.OperationFuture;
import com.google.api.gax.rpc.ApiStreamObserver;
import com.google.api.gax.rpc.BidiStreamingCallable;
import com.google.api.gax.rpc.ClientStream;
import com.google.api.gax.rpc.ResponseObserver;
import com.google.api.gax.rpc.StreamController;
import com.google.cloud.speech.v1.LongRunningRecognizeMetadata;
import com.google.cloud.speech.v1.LongRunningRecognizeResponse;
import com.google.cloud.speech.v1.RecognitionAudio;
import com.google.cloud.speech.v1.RecognitionConfig;
import com.google.cloud.speech.v1.RecognitionConfig.AudioEncoding;
import com.google.cloud.speech.v1.RecognizeResponse;
import com.google.cloud.speech.v1.SpeechClient;
import com.google.cloud.speech.v1.SpeechRecognitionAlternative;
import com.google.cloud.speech.v1.SpeechRecognitionResult;
import com.google.cloud.speech.v1.StreamingRecognitionConfig;
import com.google.cloud.speech.v1.StreamingRecognitionResult;
import com.google.cloud.speech.v1.StreamingRecognizeRequest;
import com.google.cloud.speech.v1.StreamingRecognizeResponse;
import com.google.cloud.speech.v1.WordInfo;
import com.google.common.util.concurrent.SettableFuture;
import com.google.protobuf.ByteString;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.DataLine.Info;
import javax.sound.sampled.TargetDataLine;

public class Recognize1 {
	/**
	 * Performs speech recognition on raw PCM audio and prints the transcription.
	 *
	 * @param fileName the path to a PCM audio file to transcribe.
	 */
	public static void syncRecognizeFile(String fileName) throws Exception {
	  try (SpeechClient speech = SpeechClient.create()) {
	    Path path = Paths.get(fileName);
	    byte[] data = Files.readAllBytes(path);
	    ByteString audioBytes = ByteString.copyFrom(data);

	    // Configure request with local raw PCM audio
	    RecognitionConfig config =
	        RecognitionConfig.newBuilder()
	            .setEncoding(AudioEncoding.LINEAR16)
	            .setLanguageCode("en-US")
	            .setSampleRateHertz(16000)
	            .build();
	    RecognitionAudio audio = RecognitionAudio.newBuilder().setContent(audioBytes).build();

	    // Use blocking call to get audio transcript
	    RecognizeResponse response = speech.recognize(config, audio);
	    List<SpeechRecognitionResult> results = response.getResultsList();

	    for (SpeechRecognitionResult result : results) {
	      // There can be several alternative transcripts for a given chunk of speech. Just use the
	      // first (most likely) one here.
	      SpeechRecognitionAlternative alternative = result.getAlternativesList().get(0);
	      
	      //Edit: Lucille Xiong (StarterHacks 2019)
	      //Checks for keyword in transcription
	        if (Keyword(alternative.getTranscript())) {
	        	System.out.print("Calling police\n");
	        }
	      
	      System.out.printf("Transcription: %s%n", alternative.getTranscript());
	    }
	  }
	}
}

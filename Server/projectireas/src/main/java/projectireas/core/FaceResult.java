package projectireas.core;

import com.fasterxml.jackson.annotation.JsonProperty;

public class FaceResult {
	
	public FaceResult() {
		super();
	}

	public FaceResult(Boolean result, int predictedLabel, double confidence) {
		super();
		this.result = result;
		this.predictedLabel = predictedLabel;
		this.confidence = confidence;
	}

	@JsonProperty
	private Boolean result = true;
	
	@JsonProperty
	private int predictedLabel;
	
	@JsonProperty
	private double confidence;
	
	public Boolean getResult() {
		return result;
	}

	public void setResult(Boolean result) {
		this.result = result;
	}

	public int getPredictedLabel() {
		return predictedLabel;
	}

	public void setPredictedLabel(int predictedLabel) {
		this.predictedLabel = predictedLabel;
	}

	public double getConfidence() {
		return confidence;
	}

	public void setConfidence(double confidence) {
		this.confidence = confidence;
	}

	@Override
	public String toString() {
		return "FaceResult [result=" + result + ", predictedLabel=" + predictedLabel + ", confidence=" + confidence
				+ "]";
	}
	
	
}

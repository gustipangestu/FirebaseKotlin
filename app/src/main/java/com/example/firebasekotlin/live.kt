package com.example.firebasekotlin

import android.util.Log
import androidx.annotation.OptIn
import androidx.camera.core.ExperimentalGetImage
import androidx.camera.core.ImageAnalysis
import androidx.camera.core.ImageProxy
import com.google.mlkit.vision.common.InputImage
import com.google.mlkit.vision.label.ImageLabeling
import com.google.mlkit.vision.label.defaults.ImageLabelerOptions
import com.google.mlkit.vision.objects.ObjectDetection
import com.google.mlkit.vision.objects.custom.CustomObjectDetectorOptions
import com.google.mlkit.vision.objects.defaults.ObjectDetectorOptions


class Live : ImageAnalysis.Analyzer{
//    val builderr = ImageLabelerOptions.Builder()
    val labeler = ImageLabeling.getClient(ImageLabelerOptions.DEFAULT_OPTIONS)

    @OptIn(ExperimentalGetImage::class) override fun analyze(imageProxy: ImageProxy) {
        val mediaImage = imageProxy.image
        if (mediaImage != null) {
            val image = InputImage.fromMediaImage(mediaImage, imageProxy.imageInfo.rotationDegrees)
            labeler.process(image)
                .addOnSuccessListener { labels ->
                    for (label in labels) {
                        val text = label.text
                        val confidence = label.confidence
                        val index = label.index
                        Log.d("hasil", text)
                    }
                }
                .addOnFailureListener { e ->
                   Log.d("hasil", "gagal cuy")
                }
        }
    }

}

class ObjectDetects : ImageAnalysis.Analyzer{
    val options = ObjectDetectorOptions.Builder()
        .setDetectorMode(ObjectDetectorOptions.STREAM_MODE)
        .enableClassification()  // Optional
        .build()



    val objectDetector = ObjectDetection.getClient(options)

    @OptIn(ExperimentalGetImage::class) override fun analyze(image: ImageProxy) {

        val mediaImage = image.image


        if (mediaImage != null) {
            val image = InputImage.fromMediaImage(mediaImage, image.imageInfo.rotationDegrees)
            objectDetector
                .process(image)
                .addOnSuccessListener {results ->
                    for (detectedObject in results) {
                        Log.d("hasil", "ada hasil")
                        val boundingBox = detectedObject.boundingBox
                        val trackingId = detectedObject.trackingId
                        for (label in detectedObject.labels) {
                            val text = label.text
                            val index = label.index
                            val confidence = label.confidence
                            Log.d("hasil", text)
                        }
                    }
                }
                .addOnFailureListener { e ->
                    Log.d("error", e.toString())
                }
                .addOnCompleteListener{
                    mediaImage.close()
                }
        }
    }
}


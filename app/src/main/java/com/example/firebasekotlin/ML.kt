package com.example.firebasekotlin

import android.util.Log
import androidx.annotation.OptIn
import androidx.camera.core.ExperimentalGetImage
import androidx.camera.core.ImageAnalysis
import androidx.camera.core.ImageProxy
import com.google.mlkit.vision.common.InputImage
import com.google.mlkit.vision.objects.ObjectDetection
import com.google.mlkit.vision.objects.defaults.ObjectDetectorOptions

class ObjDetection: ImageAnalysis.Analyzer{

    val options = ObjectDetectorOptions.Builder()
        .setDetectorMode(ObjectDetectorOptions.STREAM_MODE)
        .enableClassification()  // Optional
        .build()

    val objectDetector = ObjectDetection.getClient(options)

    @OptIn(ExperimentalGetImage::class) override fun analyze(imageProxy: ImageProxy) {
//        Log.d("hasil", imageProxy.toString())
        val mediaImage = imageProxy.image
        if (mediaImage != null) {
            val image = InputImage.fromMediaImage(mediaImage, imageProxy.imageInfo.rotationDegrees)
            objectDetector.process(image)
                .addOnFailureListener { e ->
                    Log.d("hasil", e.toString())
                }
                .addOnSuccessListener { hasil ->
                    for(hsl in hasil){
                        val label = hsl.labels
                        val tracking = hsl.trackingId
                        val box = hsl.boundingBox
                        for (lbl in label){
                            val cls = lbl.text
                            val cls1 = lbl.text
                            val conf = lbl.confidence
                            Log.d("hasil", cls.toString()+conf.toString())
                        }

                    }

                }
                .addOnCompleteListener{
                    imageProxy.close()
                }
        }
    }

}
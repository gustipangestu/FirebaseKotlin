package com.example.firebasekotlin

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.firebasekotlin.ui.theme.FirebaseKotlinTheme
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        val db = Firebase.firestore
        getData(db)
        super.onCreate(savedInstanceState)
        setContent {
            FirebaseKotlinTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                        CameraPreview()

                }
            }
        }
    }
}

fun getData(db:FirebaseFirestore){
    db.collection("kelas")
        .document("BRbw28Vhrmna4uPiimPP")
        .collection("OK")
        .get()
        .addOnSuccessListener { result ->
            for(dc in result){
                Log.d("data", "${dc.id} => ${dc.data}")
            }
        }
        .addOnFailureListener { exception ->
            Log.w("data", "Error getting documents.", exception)
        }
}

fun tambahData(db:FirebaseFirestore){
    val data = hashMapOf(
        "nama" to "Ada",
        "lahir" to 1815,
    )
    db.collection("orang")
        .add(data)
        .addOnSuccessListener { dcref ->
            Log.d("status", "berhasil tambah data")
        }
        .addOnFailureListener { dcref ->
            Log.d("status", "Gagal tambah data", dcref)
        }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier, db: FirebaseFirestore) {
    Button(onClick = {
        tambahData(db)
    }) {

    }

}



//@Preview(showBackground = true)
//@Composable
//fun GreetingPreview() {
//    FirebaseKotlinTheme {
//        Greeting("Android", Modifier, )
//    }
//}
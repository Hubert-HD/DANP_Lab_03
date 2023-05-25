package com.example.lab_02

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.ViewModel
import com.example.lab_02.ui.theme.Lab_02Theme

data class Assistant(
    var id: Int,
    var name: String,
    var date: String,
    var phone: String,
    var email: String,
    var typeBlood: String,
    var mount: String,
    )

class CrudAssistantViewModel : ViewModel() {
    val items = mutableStateListOf<Assistant>()
    var isEditable = mutableStateOf(false)
    val form = mutableStateOf(Assistant(
        id = 0,
        name = "",
        date = "20/05/2000",
        phone = "",
        email = "",
        typeBlood = "",
        mount = ""
    ))

    fun addAssistant(assistant: Assistant): Unit {
        items.add(assistant)
    }

    fun editAssistant(id: Int, assistant: Assistant): Unit {
        val index = items.indexOfFirst { it.id == id }
        if (index != -1)
            items[index] = assistant
        isEditable.value = false
        form.value = Assistant(
            id = 0,
            name = "",
            date = "20/05/2000",
            phone = "",
            email = "",
            typeBlood = "",
            mount = ""
        )
    }

    fun removeAssistantById(id: Int): Unit {
        val assistantToRemove = items.find { it.id == id }
        assistantToRemove?.let { items.remove(it) }
    }
}

@ExperimentalMaterial3Api
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Lab_02Theme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    MyApp()
                }
            }
        }
    }
}

@ExperimentalMaterial3Api
@Composable
fun ListAssistant(crudAssistant: CrudAssistantViewModel) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        Text(
            text = "Lista de asistentes",
            fontWeight = FontWeight.Bold,
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 16.dp)
        )
        LazyColumn(modifier = Modifier.fillMaxSize()) {
            items(crudAssistant.items.size) { item ->
                ItemAssistant(
                    assistant = crudAssistant.items.get(item),
                    edit = { id, assistant ->
                        crudAssistant.isEditable.value = true
                        crudAssistant.form.value = assistant.copy()
                     },
                    delete = { id -> if (!crudAssistant.isEditable.value) crudAssistant.removeAssistantById(id)  }
                )
            }
        }
    }
}

@ExperimentalMaterial3Api
@Composable
fun ItemAssistant(assistant: Assistant, edit: (Int, Assistant) -> Unit, delete: (Int) -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Text(
                text = assistant.name,
                fontWeight = FontWeight.Bold,
                fontSize = 16.sp,
                color = Color.Black
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(text = "Fecha de inscripción: ${assistant.date}")
            Text(text = "Tipo de sangre: ${assistant.typeBlood}")
            Text(text = "Teléfono: ${assistant.phone}")
            Text(text = "Correo: ${assistant.email}")
            Text(text = "Monto pagado: ${assistant.mount}")
            Row(
                modifier = Modifier
                .align(Alignment.End)
            ) {
                Button(
                    onClick = { edit(assistant.id, assistant) },
                ) {
                    Text("Editar")
                }
                Spacer(modifier = Modifier.width(8.dp))
                Button(
                    onClick = { delete(assistant.id) },
                ) {
                    Text("Eliminar")
                }
            }

        }
    }
}

@ExperimentalMaterial3Api
@Composable
fun FormAssistant(crudAssistant: CrudAssistantViewModel) {
    Column (
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp)
    ){
        TextField(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 8.dp),
            value = crudAssistant.form.value.name,
            onValueChange = { crudAssistant.form.value = crudAssistant.form.value.copy(name = it) },
            label = { Text(text = "Nombres") }
        )
        TextField(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 8.dp),
            value = crudAssistant.form.value.phone,
            onValueChange = { crudAssistant.form.value = crudAssistant.form.value.copy(phone = it) },
            label = { Text(text = "Teléfono") }
        )
        TextField(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 8.dp),
            value = crudAssistant.form.value.email,
            onValueChange = { crudAssistant.form.value = crudAssistant.form.value.copy(email = it) },
            label = { Text(text = "Correo") }
        )
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 8.dp)
        ) {
            TextField(
                modifier = Modifier.weight(1f),
                value = crudAssistant.form.value.typeBlood,
                onValueChange = { crudAssistant.form.value = crudAssistant.form.value.copy(typeBlood = it) },
                label = { Text(text = "Tipo de sangre") }
            )
            Spacer(modifier = Modifier.width(8.dp))
            TextField(
                modifier = Modifier.weight(1f),
                keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number),
                value = crudAssistant.form.value.mount,
                onValueChange = { crudAssistant.form.value = crudAssistant.form.value.copy(mount = it) },
                label = { Text(text = "Monto pagado") }
            )
        }
        Button(
            modifier = Modifier.align(Alignment.End),
            onClick = {
                if (crudAssistant.isEditable.value)
                    crudAssistant.editAssistant(crudAssistant.form.value.id, crudAssistant.form.value)
                else
                    crudAssistant.addAssistant(crudAssistant.form.value)
            }) {
            Text(text = run { if (crudAssistant.isEditable.value) "Guardar" else "Agregar"})
        }
    }
}

@ExperimentalMaterial3Api
@Composable
fun MyApp() {
    val crudAssistant: CrudAssistantViewModel = CrudAssistantViewModel()
    crudAssistant.addAssistant(Assistant(
        id = 1,
        name = "John Doe",
        date = "20/05/2000",
        phone = "1234567890",
        email = "johndoe@example.com",
        typeBlood = "A+",
        mount = "10.00"
    ))

    Column {
        Text(
            text = "Registro",
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        )
        FormAssistant(crudAssistant)
        ListAssistant(crudAssistant)
    }
}

@Preview(
    showSystemUi = true,
    showBackground = false
)
@ExperimentalMaterial3Api
@Composable
fun GreetingPreview() {
    Lab_02Theme {
        MyApp()
    }
}
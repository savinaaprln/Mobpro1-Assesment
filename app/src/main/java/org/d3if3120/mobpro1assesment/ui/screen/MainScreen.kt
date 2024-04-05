package org.d3if3120.mobpro1assesment.ui.screen

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableDoubleStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import org.d3if3120.mobpro1assesment.R
import org.d3if3120.mobpro1assesment.navigation.Screen
import org.d3if3120.mobpro1assesment.ui.theme.Mobpro1AssesmentTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen(navController: NavHostController) {
    Scaffold (
        topBar = {
            TopAppBar(
                title = {
                    Text(text = stringResource(id = R.string.app_name))
                },
                colors = TopAppBarDefaults.mediumTopAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                    titleContentColor = MaterialTheme.colorScheme.primary,
                ),
                actions = {
                    IconButton(onClick = {
                        navController.navigate(Screen.About.route)
                    }) {
                        Icon(imageVector = Icons.Filled.AccountCircle,
                            contentDescription = stringResource(id = R.string.about_me),
                            tint = MaterialTheme.colorScheme.primary
                        )
                    }
                }
            )
        }
    ) { padding ->
        ScreenContent(Modifier.padding(padding))
    }
}

@Composable
fun ScreenContent(modifier: Modifier ){

    var celcius by rememberSaveable { mutableStateOf("") }
    val radioOptions = listOf(
        stringResource(id = R.string.kelvin),
        stringResource(id = R.string.fharenheit)
    )
    var pilihanSuhu by rememberSaveable { mutableStateOf(radioOptions[0])}
    var hasil by rememberSaveable { mutableDoubleStateOf(0.0) }
    var suhuEmpty by rememberSaveable { mutableStateOf(false) }

    Column (
        modifier = modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ){
        Text(text = stringResource(id = R.string.app_intro),
            style = MaterialTheme.typography.bodyLarge,
            modifier = Modifier.fillMaxWidth()
        )
        OutlinedTextField(
            value =celcius ,
            onValueChange ={celcius = it},
            label = { Text(text = stringResource(id = R.string.celcius_label))},
            singleLine = true,
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Number,
                imeAction = ImeAction.Next
            ),
            modifier = Modifier.fillMaxWidth()
        )
        //radioOption untuk pilihan kelvin atau farenheit
        Row (
            modifier = Modifier
                .padding(top = 9.dp)
                .border(1.dp, Color.Gray, RoundedCornerShape(4.dp))
        ){
            radioOptions.forEach { text ->
                SuhuOption(
                    label = text,
                    isSelected = pilihanSuhu == text ,
                    modifier = Modifier
                        .selectable(
                            selected = pilihanSuhu == text,
                            onClick = { pilihanSuhu = text },
                            role = Role.RadioButton
                        )
                        .weight(1f)
                        .padding(16.dp)
                )

            }
        }
        Button(onClick = {
            suhuEmpty = (celcius == "")
            if (suhuEmpty)return@Button

            hasil = hitungSuhu(celcius.toDouble(), pilihanSuhu == radioOptions[1]).toDouble()
        },
            modifier = Modifier.padding(top = 8.dp),
            contentPadding = PaddingValues(horizontal = 32.dp, vertical = 16.dp)
        ) {
            Text(text = stringResource(id = R.string.hitung))
        }

        if (hasil != 0.0){
            Text(text = stringResource(id = R.string.hasil, hasil))
        }

    }
}

@Composable
fun SuhuOption(label: String, isSelected: Boolean, modifier: Modifier) {
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically
    ) {
        RadioButton(selected = isSelected, onClick = null)
        Text(
            text = label,
            style = MaterialTheme.typography.bodyLarge,
            modifier = Modifier.padding(start = 8.dp)
        )
    }
}


fun hitungSuhu(celcius: Double, kategori: Boolean): Double {
    return if (!kategori) {
        celcius + 273.15 // konversi Celcius ke Kelvin
    } else {
        celcius * 9 / 5 + 32 // konversi Celcius ke Fahrenheit
    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    Mobpro1AssesmentTheme {
        MainScreen(rememberNavController())
    }
}
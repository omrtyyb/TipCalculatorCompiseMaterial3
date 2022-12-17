package com.example.tipcalculator

import android.widget.Space
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.input.key.Key
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.tipcalculator.ui.theme.TipCalculatorTheme
import java.text.NumberFormat


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TipCalc(
    modifier: Modifier,
) {
    var amountInput by remember {
        mutableStateOf("")
    }
    var tipPercentageInput by remember {
        mutableStateOf("")
    }
    var switchState by remember {
        mutableStateOf(false)
    }
    val amount = amountInput.toDoubleOrNull() ?: 0.0
    val tipPercentage = tipPercentageInput.toDoubleOrNull() ?: 0.0
    val tip = calculateTip(amount, tipPercentage, switchState)

    val focusManager = LocalFocusManager.current

    Column(
        modifier = Modifier.padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {

        Text(
            modifier = Modifier.align(Alignment.CenterHorizontally),
            text = stringResource(id = R.string.calculate_tip),
            fontSize = MaterialTheme.typography.headlineLarge.fontSize,
            fontWeight = FontWeight.Bold,
        )
        Spacer(modifier = Modifier.height(16.dp))

        EditNumberField(
            value = amountInput,
            label = stringResource(id = R.string.cost_of_service),
            keyboardActions = KeyboardActions(
                onNext = {focusManager.moveFocus(FocusDirection.Down)}
            ),
            onValueChange = {
                amountInput = it
            },
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Number,
                imeAction = ImeAction.Next
            )
        )

//        Spacer(modifier = Modifier.height(8.dp))
        EditNumberField(
            value = tipPercentageInput,
            label = stringResource(id = R.string.tip_percentage),
            onValueChange = {
                tipPercentageInput = it
            },
            keyboardActions = KeyboardActions(
                onDone = {
                    focusManager.clearFocus()
                }
            ),
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Number,
                imeAction = ImeAction.Done
            )
        )
//        Spacer(modifier = Modifier.height(8.dp))
        RoundOffSwitch(
            switchState = switchState,
        ){
            switchState = it
        }

        Spacer(modifier = Modifier.height(16.dp))
        Text(
            modifier = Modifier.align(Alignment.CenterHorizontally),
            text = stringResource(R.string.tip_amount, tip),
            fontSize = 18.sp,
            fontWeight = FontWeight.Thin
        )
    }


}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EditNumberField(
    value: String,
    label: String,
    keyboardActions: KeyboardActions,
    keyboardOptions: KeyboardOptions,
    onValueChange: (String) -> Unit,
) {
    OutlinedTextField(
        modifier = Modifier.fillMaxWidth(),
        value = value,
        onValueChange = onValueChange,
        label = {
            Text(text = label)
        },
        keyboardOptions = keyboardOptions,
        keyboardActions = keyboardActions,
        singleLine = true,
    )
}



@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    TipCalculatorTheme {
        TipCalc(modifier = Modifier.fillMaxSize())
    }
}


//@Preview(showBackground = true)
@Composable
fun RoundOffSwitch(
    switchState: Boolean,
    onCheckedChange: (Boolean) -> Unit
) {

    Row(
        modifier = Modifier
            .fillMaxWidth(),
//            .clickable {
//
//            },
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(text = stringResource(id = R.string.tip_switch))
        Switch(
            checked = switchState,
            onCheckedChange = onCheckedChange
        )
    }
}




fun calculateTip(
    amount: Double,
    tipPercentage: Double = 0.0,
    roundUp: Boolean
): String {
    var tip = tipPercentage / 100 * amount
    return if(tipPercentage <= 100){
        if (roundUp){
            tip = kotlin.math.ceil(tip)
        }
        NumberFormat.getCurrencyInstance().format(tip)
    }else NumberFormat.getCurrencyInstance().format(0.0)

}




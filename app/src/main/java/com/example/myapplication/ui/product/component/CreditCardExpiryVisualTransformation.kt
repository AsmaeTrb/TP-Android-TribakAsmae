package com.example.myapplication.ui.product.component

import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.input.OffsetMapping
import androidx.compose.ui.text.input.TransformedText
import androidx.compose.ui.text.input.VisualTransformation

class CreditCardExpiryVisualTransformation : VisualTransformation {
    override fun filter(text: AnnotatedString): TransformedText {
        val trimmed = if (text.text.length >= 4) text.text.substring(0..3) else text.text
        val out = StringBuilder()
        for (i in trimmed.indices) {
            out.append(trimmed[i])
            if (i == 1) out.append(" / ")
        }

        val offsetTranslator = object : OffsetMapping {
            override fun originalToTransformed(offset: Int): Int =
                when {
                    offset <= 1 -> offset
                    offset <= 4 -> offset + 3
                    else -> 7
                }

            override fun transformedToOriginal(offset: Int): Int =
                when {
                    offset <= 2 -> offset
                    offset <= 7 -> offset - 3
                    else -> 4
                }
        }

        return TransformedText(AnnotatedString(out.toString()), offsetTranslator)
    }
}

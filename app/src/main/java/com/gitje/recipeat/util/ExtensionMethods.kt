package com.gitje.recipeat.util

import androidx.annotation.DimenRes
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.unit.sp

@Composable
fun fontSizeDimensionResource(@DimenRes id: Int) = dimensionResource(id = id).value.sp

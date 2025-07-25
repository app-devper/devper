package com.devper.app.core.design.component

import androidx.compose.runtime.Composable
import androidx.compose.ui.layout.ContentScale
import coil3.ImageLoader
import coil3.compose.rememberAsyncImagePainter
import com.devper.app.design.resources.Res
import com.devper.app.design.resources.default_image_loader
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.painterResource


@OptIn(ExperimentalResourceApi::class)
@Composable
fun rememberCustomImagePainter(
    model: Any?,
    imageLoader: ImageLoader,
    contentScale: ContentScale = ContentScale.Fit,
) = rememberAsyncImagePainter(
    model, imageLoader,
    error = painterResource(Res.drawable.default_image_loader),
    placeholder = painterResource(Res.drawable.default_image_loader),
    contentScale = contentScale
)

@OptIn(ExperimentalResourceApi::class)
@Composable
fun rememberCustomImagePainter(
    model: Any?,
    contentScale: ContentScale = ContentScale.Fit,
) = rememberAsyncImagePainter(
    model,
    error = painterResource(Res.drawable.default_image_loader),
    placeholder = painterResource(Res.drawable.default_image_loader),
    contentScale = contentScale,
)
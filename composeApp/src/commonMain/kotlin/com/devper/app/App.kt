package com.devper.app

import androidx.compose.runtime.Composable
import coil3.ImageLoader
import coil3.PlatformContext
import coil3.annotation.ExperimentalCoilApi
import coil3.compose.setSingletonImageLoaderFactory
import coil3.disk.DiskCache
import coil3.memory.MemoryCache
import coil3.network.NetworkFetcher
import coil3.network.ktor3.KtorNetworkFetcherFactory
import coil3.request.CachePolicy
import coil3.request.crossfade
import coil3.util.DebugLogger
import com.devper.app.core.design.theme.AppTheme
import okio.FileSystem
import org.jetbrains.compose.ui.tooling.preview.Preview

@OptIn(ExperimentalCoilApi::class)
@Composable
@Preview
fun App(disableDiskCache: Boolean = false) {
    AppTheme {
        val fetcherFactory: NetworkFetcher.Factory = KtorNetworkFetcherFactory()
        setSingletonImageLoaderFactory { context ->
            if (disableDiskCache) {
                context.asyncImageLoader(fetcherFactory)
            } else {
                context.asyncImageLoader(fetcherFactory).enableDiskCache()
            }
        }

        AppNavGraph()
    }
}

fun PlatformContext.asyncImageLoader(fetcherFactory: NetworkFetcher.Factory) =
    ImageLoader
        .Builder(this)
        .components {
            add(fetcherFactory)
        }.crossfade(true)
        .networkCachePolicy(CachePolicy.ENABLED)
        .diskCachePolicy(CachePolicy.ENABLED)
        .memoryCachePolicy(CachePolicy.ENABLED)
        .memoryCache {
            MemoryCache
                .Builder()
                .maxSizePercent(this, 0.25)
                .strongReferencesEnabled(true)
                .build()
        }.logger(DebugLogger())
        .build()

fun ImageLoader.enableDiskCache() =
    this
        .newBuilder()
        .diskCache {
            DiskCache
                .Builder()
                .directory(FileSystem.SYSTEM_TEMPORARY_DIRECTORY / "image_cache")
                .build()
        }.build()

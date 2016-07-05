package com.android.mvpp2p;

import android.app.Application;

import com.android.mvpp2p.injector.component.ApplicationComponent;
import com.android.mvpp2p.injector.component.DaggerApplicationComponent;
import com.android.mvpp2p.injector.module.ApplicationModule;
import com.android.mvpp2p.utils.ToastUtil;
import com.facebook.cache.disk.DiskCacheConfig;
import com.facebook.common.internal.Supplier;
import com.facebook.common.util.ByteConstants;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.imagepipeline.backends.okhttp3.OkHttpImagePipelineConfigFactory;
import com.facebook.imagepipeline.cache.MemoryCacheParams;
import com.facebook.imagepipeline.core.ImagePipelineConfig;
import com.facebook.imagepipeline.decoder.SimpleProgressiveJpegConfig;
import com.liulishuo.filedownloader.FileDownloader;
import com.liulishuo.filedownloader.util.FileDownloadHelper;
import com.squareup.leakcanary.LeakCanary;

import javax.inject.Inject;

import okhttp3.OkHttpClient;

/**
 * Created by sll on 2016/3/8.
 */
public class MyApplication extends Application {

  private ApplicationComponent mApplicationComponent;

  @Inject OkHttpClient mOkHttpClient;

  @Override public void onCreate() {
    super.onCreate();
    initComponent();
    FileDownloader.init(this, new FileDownloadHelper.OkHttpClientCustomMaker() {
        @Override
        public OkHttpClient customMake() {
            return mOkHttpClient;
        }
    });
    initFrescoConfig();
    ToastUtil.register(this);
    LeakCanary.install(this);
  }

  private void initComponent() {
    mApplicationComponent =
        DaggerApplicationComponent.builder().applicationModule(new ApplicationModule(this)).build();
    mApplicationComponent.inject(this);
  }

  public ApplicationComponent getApplicationComponent() {
    return mApplicationComponent;
  }


  private static final int MAX_HEAP_SIZE = (int) Runtime.getRuntime().maxMemory();

  public static final int MAX_DISK_CACHE_SIZE = 50 * ByteConstants.MB;
  public static final int MAX_MEMORY_CACHE_SIZE = MAX_HEAP_SIZE / 8;

  private void initFrescoConfig() {
    final MemoryCacheParams bitmapCacheParams =
        new MemoryCacheParams(MAX_MEMORY_CACHE_SIZE, // Max total size of elements in the cache
            Integer.MAX_VALUE,                     // Max entries in the cache
            MAX_MEMORY_CACHE_SIZE, // Max total size of elements in eviction queue
            Integer.MAX_VALUE,                     // Max length of eviction queue
            Integer.MAX_VALUE);
    ImagePipelineConfig config = OkHttpImagePipelineConfigFactory.newBuilder(this, mOkHttpClient)
        .setProgressiveJpegConfig(new SimpleProgressiveJpegConfig())
        .setBitmapMemoryCacheParamsSupplier(new Supplier<MemoryCacheParams>() {
          public MemoryCacheParams get() {
            return bitmapCacheParams;
          }
        })
        .setMainDiskCacheConfig(
            DiskCacheConfig.newBuilder(this).setMaxCacheSize(MAX_DISK_CACHE_SIZE).build())
        .setDownsampleEnabled(true)
        .build();
    Fresco.initialize(this, config);
  }
}

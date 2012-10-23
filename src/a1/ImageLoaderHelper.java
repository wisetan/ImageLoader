package a1;

import a2.DisplayImageOptions;
import a2.ImageLoader;
import a2.ImageLoaderConfiguration;
import a2.ImageLoadingListener;
import a2.Md5FileNameGenerator;
import android.content.Context;
import android.widget.ImageView;

public class ImageLoaderHelper {
	
	private static boolean isInit;
	
	public ImageLoaderHelper(Context ctx){
		init(ctx);
		mOptions = new DisplayImageOptions.Builder()
		.cacheInMemory()
		.cacheOnDisc()
		.build();
	}
	
	public ImageLoaderHelper(Context ctx, int stubImageId){
		init(ctx);
		mOptions = new DisplayImageOptions.Builder()
		.showStubImage(stubImageId)
		.cacheInMemory()
		.cacheOnDisc()
		.build();
	}
	
	private void init(Context ctx){
		if(!isInit){
			isInit = true;
			ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(ctx.getApplicationContext())
			.threadPoolSize(3)
			.threadPriority(Thread.NORM_PRIORITY - 2)
			.memoryCacheSize(1500000) // 1.5 Mb
			.denyCacheImageMultipleSizesInMemory()
			.discCacheFileNameGenerator(new Md5FileNameGenerator())
			//.discCache(new UnlimitedDiscCache(FileMgr.getInstance(ctx).getHome()))//自定义缓存目录
			//.enableLogging() // Not necessary in common
			.build();
			ImageLoader.getInstance().init(config);
		}
	}
	
	private DisplayImageOptions mOptions;
	
	public void set(String uri, ImageView imageView){
		ImageLoader.getInstance().displayImage(uri, imageView, mOptions);
	}
	
	public static void load(Context ctx, String uri, ImageLoadingListener listener){
		ImageLoader.getInstance().loadImage(ctx, uri, listener);
	}
	
	public static boolean check(Context ctx, String uri){
		return ImageLoader.getInstance().getDiscCache().get(uri).exists();
	}
	
	public static void stop(){
		ImageLoader.getInstance().stop();
	}

}

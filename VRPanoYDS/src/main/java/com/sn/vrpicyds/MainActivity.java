package com.sn.vrpicyds;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import com.google.vr.sdk.widgets.pano.VrPanoramaEventListener;
import com.google.vr.sdk.widgets.pano.VrPanoramaView;

import java.io.IOException;
import java.io.InputStream;

/*      0.0 在项目里新建一个资产目录assets,把图片放入资产目录下,
        1.0 在清单文件下Application节点中加入android:largeHeap="true"的属下节点.
        2.0 导入VR需要依赖的library库,以导model的方式去导入:Common,Commonwidge,Panowidget
        3.0 在完成Module的build.gradle文件里dependencies,添加:compile 'com.google.protobuf.nano:protobuf-javanano:3.0.0-alpha-7'
        4.0 项目XML布局,VrPanoramaView
        5.0 由于VR资源数据量大,获取需要时间,故把加载图片放到子线程中进行,主线程来显示图片,可以使用一个异步线程AsyncTask或EventBus技术完成
        6.0 因为VR很占用内存,所以当界面进入onPause状态,暂停VR视图显示,进入onResume状态,继续VR视图显示,进入onDestroy状态,杀死VR,关闭异步任务
        7.0 设置对VR运行状态的监听,如果VR运行出现错误,可以及时的处理.
        8.0 播放VR效果,只需执行异步任务即可.
*/

public class MainActivity extends AppCompatActivity {

    private VrPanoramaView vr_panorama;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //A.对VR控件进行初始化
        vr_panorama = (VrPanoramaView) findViewById(R.id.vr_panorama);
        //E.使用VR控件对象调用setInfoButtonEnabled,false隐藏掉VR效果左下角的信息按钮显示
        vr_panorama.setInfoButtonEnabled(false);
        //E.调用setFullscreenButtonEnabled,false隐藏掉VR效果右下角全屏显示按钮
        vr_panorama.setFullscreenButtonEnabled(false);
        //E.切换VR的模式调用,调用setDisplayMode  参数: VrWidgetView.DisplayMode.FULLSCREEN_STEREO设备模式(手机横着放试试)   VrWidgetView.DisplayMode.FULLSCREEN_MONO手机模式
        vr_panorama.setDisplayMode(VrPanoramaView.DisplayMode.FULLSCREEN_STEREO);
        //D.调用setEventListener,设置对VR运行状态的监听,如果VR运行出现错误,可以及时的处理.   参数就是自定义继承VrPanoramaEventListener的类
        vr_panorama.setEventListener(new myVrPanoramaEventListener());

        //B.使用自定义的AsyncTask,播放VR效果,创建其类对象,调用execute.
        new ImageAsyncTask().execute();
    }

    /**
     * B.自定义一个类继承AsyncTask,只使用我们需要的方法.最后一个泛型为Bitmap
     * 由于VR资源数据量大,获取需要时间,故把加载图片放到子线程中进行,主线程来显示图片,故可以使用一个异步线程AsyncTask或EventBus来处理.
     */
    private class ImageAsyncTask extends AsyncTask<Void, Void, Bitmap> {
        //B. doInBackground在子线程运行,从本地文件中把资源加载到内存中.
        @Override
        protected Bitmap doInBackground(Void... voids) {
            try {
                //getAssets().open("andes.jpg") ,返回结果为字节流,从资产目录拿到资源,返回结果是字节流
                InputStream open = getAssets().open("andes.jpg");
                //把字节流转换为Bitmap对象,使用Bitmap工程.decodeStream
                Bitmap bitmap = BitmapFactory.decodeStream(open);
                //return 返回一个Bitmap对象
                return bitmap;
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }

        //B.onPostExecute方法在主线程运行
        @Override
        protected void onPostExecute(Bitmap bitmap) {
            //创建VrPanoramaView.Options,去决定VR是普通效果,还是立体效果
            VrPanoramaView.Options options = new VrPanoramaView.Options();
            //TYPE_STEREO_OVER_UNDER立体效果:图片的上半部分放在左眼显示,下半部分放在右眼显示   TYPE_MONO普通:效果没有上一种明显
            //返回结果 options.inputType 去接收
            options.inputType = VrPanoramaView.Options.TYPE_STEREO_OVER_UNDER;
            //使用VR控件对象,调用loadImageFromBitmap,显示效果.   参数: 1.Bitmap对象		2VrPanoramaView.Options对象,决定显示效果
            vr_panorama.loadImageFromBitmap(bitmap, options);
            super.onPostExecute(bitmap);
        }

    }

    //C.因为VR很占用内存,所以当界面进入onPause状态,暂停VR视图显示,进入onResume状态,继续VR视图显示,进入onDestroy,杀死VR,关闭异步任务

    /**
     * onPause:当失去焦点时,回调
     */
    @Override
    protected void onPause() {
        //pauseRendering,暂停渲染和显示
        vr_panorama.pauseRendering();
        super.onPause();
    }

    /**
     * onResume:当获取焦点时,回调
     */
    @Override
    protected void onResume() {
        //resumeRendering,继续渲染和显示
        vr_panorama.resumeRendering();
        super.onResume();
    }

    /**
     * onDestroy:当Activity销毁时,回调.
     */
    @Override
    protected void onDestroy() {
        //shutdown,关闭渲染视图
        vr_panorama.shutdown();
        super.onDestroy();
    }

    /**
     * D.VR运行状态监听类,自定义一个类继承VrPanoramaEventListener,复写里面的两个方法
     */
    private class myVrPanoramaEventListener extends VrPanoramaEventListener {
        //当VR视图加载成功的时候的回调, onLoadSuccess 弹个吐司即可
        @Override
        public void onLoadSuccess() {
            Toast.makeText(MainActivity.this, "VR运行成功,可以享受了,么么哒", Toast.LENGTH_SHORT).show();
            super.onLoadSuccess();
        }

        //当VR试图加载失败的时候的回调, onLoadError  弹个吐司即可
        @Override
        public void onLoadError(String errorMessage) {
            Toast.makeText(MainActivity.this, "因为胡文帅,太帅了,导致程序异常,颜值爆表", Toast.LENGTH_SHORT).show();
            super.onLoadError(errorMessage);
        }
    }


}

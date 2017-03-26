package com.sn.vrpicyds;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
/*      0.0 在项目里新建一个资产目录assets,把图片放入资产目录下,
        1.0 在清单文件下Application节点中加入android:largeHeap="true"的属下节点.
        2.0 导入VR需要依赖的library库,以导model的方式去导入:Common,Commonwidge,Panowidget
        3.0 在Module的build.gradle文件里dependencies,添加:compile 'com.google.protobuf.nano:protobuf-javanano:3.0.0-alpha-7'
        4.0 完成项目XML布局,VrPanoramaView
        5.0 由于VR资源数据量大,获取需要时间,故把加载图片放到子线程中进行,主线程来显示图片,可以使用一个异步线程AsyncTask或EventBus技术完成
        6.0 因为VR很占用内存,所以当界面进入onPause状态,暂停VR视图显示,进入onResume状态,继续VR视图显示,进入onDestroy状态,杀死VR,关闭异步任务
        7.0 设置对VR运行状态的监听,如果VR运行出现错误,可以及时的处理.
        8.0 播放VR效果,只需执行异步任务即可.
*/

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //A.对VR控件进行初始化

        //E.使用VR控件对象调用setInfoButtonEnabled,false隐藏掉VR效果左下角的信息按钮显示

        //E.调用setFullscreenButtonEnabled,false隐藏掉VR效果右下角全屏显示按钮

        //E.切换VR的模式调用,调用setDisplayMode  参数: VrWidgetView.DisplayMode.FULLSCREEN_STEREO设备模式(手机横着放试试)   VrWidgetView.DisplayMode.FULLSCREEN_MONO手机模式

        //D.调用setEventListener,设置对VR运行状态的监听,如果VR运行出现错误,可以及时的处理.   参数就是自定义继承VrPanoramaEventListener的类

        //B.使用自定义的AsyncTask,播放VR效果,创建其类对象,调用execute.

    }

    /**
     * B.自定义一个类继承AsyncTask,只使用我们需要的方法.最后一个泛型为Bitmap
     * 由于VR资源数据量大,获取需要时间,故把加载图片放到子线程中进行,主线程来显示图片,故可以使用一个异步线程AsyncTask或EventBus来处理.
     */
        //B. doInBackground在子线程运行,从本地文件中把资源加载到内存中.

                //getAssets().open("andes.jpg") ,返回结果为字节流,从资产目录拿到资源,返回结果是字节流

                //把字节流转换为Bitmap对象,使用Bitmap工程.decodeStream

                //return 返回一个Bitmap对象

        //B.onPostExecute方法在主线程运行

            //创建VrPanoramaView.Options,去决定VR是普通效果,还是立体效果

            //TYPE_STEREO_OVER_UNDER立体效果:图片的上半部分放在左眼显示,下半部分放在右眼显示   TYPE_MONO普通:效果没有上一种明显
            //返回结果 options.inputType 去接收

            //使用VR控件对象,调用loadImageFromBitmap,显示效果.   参数: 1.Bitmap对象		2.VrPanoramaView.Options对象,决定显示效果

    //C.因为VR很占用内存,所以当界面进入onPause状态,暂停VR视图显示,进入onResume状态,继续VR视图显示,进入onDestroy,杀死VR,关闭异步任务

    /**
     * onPause:当失去焦点时,回调
     */
    //pauseRendering,暂停渲染和显示

    /**
     *   onResume:当获取焦点时,回调
     */
        //resumeRendering,继续渲染和显示

    /**
     * onDestroy:当Activity销毁时,回调.
     */
        //shutdown,关闭渲染视图

    /**
     * D.VR运行状态监听类,自定义一个类继承VrPanoramaEventListener,复写里面的两个方法
     */
        //当VR视图加载成功的时候的回调, onLoadSuccess 弹个吐司即可

        //当VR试图加载失败的时候的回调, onLoadError  弹个吐司即可

}

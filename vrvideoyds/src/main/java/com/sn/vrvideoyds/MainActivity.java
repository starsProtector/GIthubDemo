package com.sn.vrvideoyds;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

/*      0.0 在项目里新建一个资产目录assets,把视屏资源放入资产目录下,
        1.0 在清单文件下Application节点中加入android:largeHeap="true"的属下节点.
        2.0 导入VR需要依赖的library库,以导model的方式去导入:Common,Commonwidge,Videowidget
        3.0 在Module的build.gradle文件里dependencies,添加:compile 'com.google.protobuf.nano:protobuf-javanano:3.0.0-alpha-7'       compile 'com.google.android.exoplayer:exoplayer:r1.5.10'
        4.0 完成项目XML布局,VrVideoView
        5.0 由于VR资源数据量大,获取需要时间,故把加载图片放到子线程中进行,主线程来显示效果,可以使用一个异步线程AsyncTask或EventBus技术完成
        6.0 因为VR很占用内存,所以当界面进入onPause状态,暂停VR视屏,进入onResume状态,继续播放VR视频,进入onDestroy状态,杀死VR,关闭异步任务
        7.0 对VR视频播放设置监听,方便对视频播放进度的显示及播放暂停的操作
        8.0 播放VR效果,只需执行异步任务即可.
*/
public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //A.进行控件的初始化


        //隐藏VR效果左下角的信息按钮显示.setInfoButtonEnabled  ,参数false是不显示

        //切换VR的模式.setDisplayMode    参数:VrVideoView.DisplayMode.FULLSCREEN_STEREO:设备模式(手机横着放试试)      VrVideoView.DisplayMode..FULLSCREEN_MONO手机模式

        //D.对VR视频进行事件监听.setEventListener        参数就是自定义继承VrPanoramaEventListener的类

        //B.使用自定义的AsyncTask,播放VR效果,创建其类对象,调用execute(参数就是播放资源的全面).


    }

    //B.由于VR资源数据量大,获取需要时间,故把加载视频放到子线程中进行,主线程来显示,可以使用一个异步线程AsyncTask或EventBus技术完成.

    //B.自定义一个类继承AsyncTask,只使用我们需要的方法.完成在子线程加载图片资源,在主线程显示,第一个参数就是String,其他Void

        //B.该方法在子线程运行,从本地文件中把资源加载到内存中

            //创建VrVideoView.Options对象,决定VR是普通的效果,还是立体效果

            //TYPE_STEREO_OVER_UNDER立体效果:图片的上半部分放在左眼显示,下半部分放在右眼显示   TYPE_MONO普通:效果没有上一种明显
            //返回结果 options.inputType 去接收

            //处理加载的视频格式
            //FORMAT_DEFAULT:默认格式(SD卡或assets)
            //FORMAT_HLS:流媒体数据格式(直播)
            //返回结果 options..inputFormat


                //提示:视频加载的方法还做了把视频读取到内存中的操作,所以它有一个矛盾,调用该方法是放在主线程还是子线程(一般我们放在子线程)
                //使用VR控件对象.loadVideoFromAsset,从资产目录加载视频数据,显示效果 参数: 1.String数组0 2.VrVideoView.Options对象,决定显示效果

    //C.因为VR很占用内存,所以当界面进入OnPause状态,暂停VR视图显示,进入OnResume状态,继续VR视图显示,进入OnDestroy状态,杀死VR,关闭异步任务

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

    //VR运行状态监听类,自定义一个类继承VrVideoEventListener,复写里面需要的方法.
        //当VR视图加载成功的时候的回调,此时还未开始播放,onLoadSuccess

            //vr控件对象.getDuration();获取视频的长度
            //设置seekbar的进度最大值,.setMax((int)max) 给seek_bar设置的参数要是int型,所以要强类型转换

        //当VR视图加载失败的时候回调的方法,onLoadErro
            //弹个吐司即可

        //当视频开始播放,每次进入下一帧的时候,回调这个方法onNewFrame(就是播放时,会不停的回调该方法)
            //获取当前视频的播放时间位置,VR控件对象.getCurrentPosition(),在这里就int强类型转换
            //设置seekBar的进度条.setProgress
            //显示播放的进度数字.setText

        //当视频播放结束后的回调,onCompletion
            //让视频回到0点,VR控件对象.seekTo(0)
            //视频停止.pauseVideo()
            //让进度条也设置到0点.setProgress(0)

            //播放完成后,重新设置标签,标签false代表着视频处于暂停的状态.

        //设置一个视频播放状态的标签,定义一个私有的boolean类型变量,默认状态时播放

        //重写点击视图的方法,是视频被点击时,播放或者暂停,onClick()
            //根据标签,判断当前视频的状态,做对应的逻辑处理
            //false代表视频正处于暂停状态,使用if语句,过根据标签,做对应的逻辑处理
                //视频暂停.pauseVideo()
            //true代表视频正在播放的状态.
                //视频播放.playVideo()
            //对标签进行一次操作后,取反!

}

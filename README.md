# WeChatMoments

## 设计概述：
  + ### 功能概述
        主要实现功能有：朋友圈列表，朋友圈主页，朋友圈图片、评论列表、上拉刷新、下拉加载
  + ### 设计模式
        本项目主要采用MVP模式设计,Model层主要负责数据交互，View层定义数据回传接口，Presenter负责调度Model和View，解除了Model层与View层的耦合，从而实现数据到界面的展现

## 框架概述：
  + **Okhttp**    网络通讯基础框架
  + **Retrofit**  数据接口定义，并与OKhttp配合，完成数据请求
  + **Rxjava**    异步框架，主要用来处理异步网络请求操作
  + **Glide**     图片加载框架
  + **ButterKnife** View注入框架，避免大量的findviewById代码
  

## 分包说明：
   + **api**  
       数据接口定义
   + **base**  
       基类定义包
   + **bean**  
       数据类定义包
   + **constant**  
       常量定义包
   + **customview**  
       自View包
   + **glide**  
       glide图片加载相关
   + **moudle**  
       业务模块包，业务相关模块全部放在这个包下
   + **net**  
       网络请求定义包
   + **rx**  
       rx异步相关定义包
 
   
  

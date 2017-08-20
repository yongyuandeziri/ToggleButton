# 自定义View  ToggleButton

自定义View一般步骤：
1.构造方法创建对象(几个构造方法的使用情景)
2.测量view的大小  onMeature(int,int)
3.确定view的位置，view自身有一些建议权，但是决定权在父view  onLayout(),在自定义view中不常用。
4.绘制view的内容 onDraw(Canvas).
![image](https://github.com/yongyuandeziri/ToggleButton/blob/master/app/src/main/res/drawable/togglebutton.gif) 

package com.boxuegu.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Xml;
import android.widget.ImageView;

import com.boxuegu.bean.CourseBean;
import com.boxuegu.bean.ExercisesBean;

import org.xmlpull.v1.XmlPullParser;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2018/10/16 0016.
 */

public class AnalysisUtils {
    public static String readLoginUserName(Context context){
        SharedPreferences sharedPreferences=context.getSharedPreferences("loginInfo",Context.MODE_PRIVATE);
        String userName=sharedPreferences.getString("loginUserName","");
        return userName;
    }

    public static List<ExercisesBean> getExercisesInfos(InputStream is) throws Exception {
        XmlPullParser parser= Xml.newPullParser();
        parser.setInput(is, "utf-8");
        List<ExercisesBean> exercisesInfos=null;//整章的习题
        ExercisesBean exercisesInfo=null; //每章中的一道习题
        int type=parser.getEventType();
        while (type!=XmlPullParser.END_DOCUMENT) {
            switch (type) {
                case XmlPullParser.START_TAG:
                    if("infos".equals(parser.getName())){
                        exercisesInfos=new ArrayList<ExercisesBean>();
                    }else if("exercises".equals(parser.getName())){
                        exercisesInfo=new ExercisesBean();
                        String ids=parser.getAttributeValue(0); //获取第一个属性的属性值
                        exercisesInfo.subjectId=Integer.parseInt(ids);
                    }else if("subject".equals(parser.getName())){
                        String subject=parser.nextText(); //获取当前解析对象元素的文本值
                        exercisesInfo.subject=subject;
                    }else if("a".equals(parser.getName())){
                        String a=parser.nextText();
                        exercisesInfo.a=a;
                    }else if("b".equals(parser.getName())){
                        String b=parser.nextText();
                        exercisesInfo.b=b;
                    }else if("c".equals(parser.getName())){
                        String c=parser.nextText();
                        exercisesInfo.c=c;
                    }else if("d".equals(parser.getName())){
                        String d=parser.nextText();
                        exercisesInfo.d=d;
                    }else if("answer".equals(parser.getName())){
                        String answer=parser.nextText();
                        exercisesInfo.answer=Integer.parseInt(answer);
                    }
                    break;
                case XmlPullParser.END_TAG:
                    if("exercises".equals(parser.getName())){ //碰到exercises的结束标签，说明解析完了一道习题
                        exercisesInfos.add(exercisesInfo);  //接着将该道习题加到练习列表数组对象exercisesInfos中
                        exercisesInfo=null;  //清空该题（因为已经添加到练习列表数组对象）
                    }
                    break;
            }
            type=parser.next();
        }
        return exercisesInfos;  //全部解析完后，返回该章的练习（该章的所有习题已经添加进来了）
    }
    /**
     * 设置A、B、C、D选项是否可点击
     */
    public static void setABCDEnable(boolean value, ImageView iv_a, ImageView iv_b, ImageView iv_c, ImageView iv_d){
        iv_a.setEnabled(value);
        iv_b.setEnabled(value);
        iv_c.setEnabled(value);
        iv_d.setEnabled(value);
    }

    public static List<List<CourseBean>> getCourseInfos(InputStream is) throws Exception {
        XmlPullParser parser=Xml.newPullParser();
        parser.setInput(is, "utf-8");
        List<List<CourseBean>> courseInfos=null;
        List<CourseBean> courseList=null;
        CourseBean courseInfo=null;
        int count=0;
        int type=parser.getEventType();
        while (type!=XmlPullParser.END_DOCUMENT) {
            switch (type) {
                case XmlPullParser.START_TAG:
                    if("infos".equals(parser.getName())){
                        courseInfos=new ArrayList<List<CourseBean>>();
                        courseList=new ArrayList<CourseBean>();
                    }else if("course".equals(parser.getName())){
                        courseInfo=new CourseBean();
                        String ids=parser.getAttributeValue(0);
                        courseInfo.id=Integer.parseInt(ids);
                    }else if("imgtitle".equals(parser.getName())){
                        String imgtitle=parser.nextText();
                        courseInfo.imgTitle=imgtitle;
                    }else if("title".equals(parser.getName())){
                        String title=parser.nextText();
                        courseInfo.title=title;
                    }else if("intro".equals(parser.getName())){
                        String intro=parser.nextText();
                        courseInfo.intro=intro;
                    }
                    break;
                case XmlPullParser.END_TAG:
                    if("course".equals(parser.getName())){
                        count++;
                        courseList.add(courseInfo);
                        if(count%2==0){// 课程界面每两个数据是一组放在List集合中
                            courseInfos.add(courseList);
                            courseList=null;
                            courseList=new ArrayList<CourseBean>();
                        }
                        courseInfo=null;
                    }
                    break;
            }
            type=parser.next();
        }
        return courseInfos;
    }
}

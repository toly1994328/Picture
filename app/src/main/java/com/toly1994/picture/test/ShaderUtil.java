package com.toly1994.picture.test;

import android.content.res.Resources;
import android.opengl.GLES20;
import android.util.Log;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;

//���ض���Shader��ƬԪShader�Ĺ�����
public class ShaderUtil 
{
   //�����ƶ�shader�ķ���
   public static int loadShader
   (
		 int shaderType, //shader������  GLES20.GL_VERTEX_SHADER   GLES20.GL_FRAGMENT_SHADER
		 String source   //shader�Ľű��ַ���
   ) 
   {
	    //����һ����shader
        int shader = GLES20.glCreateShader(shaderType);
        //�������ɹ������shader
        if (shader != 0) 
        {
        	//����shader��Դ����
            GLES20.glShaderSource(shader, source);
            //����shader
            GLES20.glCompileShader(shader);
            //��ű���ɹ�shader����������
            int[] compiled = new int[1];
            //��ȡShader�ı������
            GLES20.glGetShaderiv(shader, GLES20.GL_COMPILE_STATUS, compiled, 0);
            if (compiled[0] == 0) 
            {//������ʧ������ʾ������־��ɾ����shader
                Log.e("ES20_ERROR", "Could not compile shader " + shaderType + ":");
                Log.e("ES20_ERROR", GLES20.glGetShaderInfoLog(shader));
                GLES20.glDeleteShader(shader);
                shader = 0;      
            }  
        }
        return shader;
    }
    
   //����shader����ķ���
   public static int createProgram(String vertexSource, String fragmentSource) 
   {
	    //���ض�����ɫ��
        int vertexShader = loadShader(GLES20.GL_VERTEX_SHADER, vertexSource);
        if (vertexShader == 0) 
        {
            return 0;
        }
        
        //����ƬԪ��ɫ��
        int pixelShader = loadShader(GLES20.GL_FRAGMENT_SHADER, fragmentSource);
        if (pixelShader == 0) 
        {
            return 0;
        }

        //��������
        int program = GLES20.glCreateProgram();
        //�����򴴽��ɹ���������м��붥����ɫ����ƬԪ��ɫ��
        if (program != 0) 
        {
        	//������м��붥����ɫ��
            GLES20.glAttachShader(program, vertexShader);
            checkGlError("glAttachShader");
            //������м���ƬԪ��ɫ��
            GLES20.glAttachShader(program, pixelShader);
            checkGlError("glAttachShader");
            //���ӳ���
            GLES20.glLinkProgram(program);
            //������ӳɹ�program����������
            int[] linkStatus = new int[1];
            //��ȡprogram���������
            GLES20.glGetProgramiv(program, GLES20.GL_LINK_STATUS, linkStatus, 0);
            //������ʧ���򱨴�ɾ������
            if (linkStatus[0] != GLES20.GL_TRUE) 
            {
                Log.e("ES20_ERROR", "Could not link program: ");
                Log.e("ES20_ERROR", GLES20.glGetProgramInfoLog(program));
                GLES20.glDeleteProgram(program);
                program = 0;
            }
        }
        return program;
    }
    
   //���ÿһ�������Ƿ��д���ķ��� 
   public static void checkGlError(String op) 
   {
        int error;
        while ((error = GLES20.glGetError()) != GLES20.GL_NO_ERROR) 
        {
            Log.e("ES20_ERROR", op + ": glError " + error);
            throw new RuntimeException(op + ": glError " + error);
        }
   }
   
   //��sh�ű��м���shader���ݵķ���
   public static String loadFromAssetsFile(String fname,Resources r)
   {
   	String result=null;    	
   	try
   	{
   		InputStream in=r.getAssets().open(fname);
			int ch=0;
		    ByteArrayOutputStream baos = new ByteArrayOutputStream();
		    while((ch=in.read())!=-1)
		    {
		      	baos.write(ch);
		    }      
		    byte[] buff=baos.toByteArray();
		    baos.close();
		    in.close();
   		result=new String(buff,"UTF-8"); 
   		result=result.replaceAll("\\r\\n","\n");
   	}
   	catch(Exception e)
   	{
   		e.printStackTrace();
   	}    	
   	return result;
   }
}

precision mediump float;
varying  vec3 vPosition;  //顶点位置
void main() {
   vec4 bColor=vec4(1,0,0,0);//砖块的颜色
   vec4 mColor=vec4(0.7254902,0.84313726,1,0);//间隔的颜色
   float y=vPosition.y;
   y=mod((y+100.0)*4.0,4.0);
   if(y>1.8) {
     gl_FragColor = bColor;//给此片元颜色值
   } else {
     gl_FragColor = mColor;//给此片元颜色值
}}
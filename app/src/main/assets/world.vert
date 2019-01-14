attribute vec3 vPosition;//顶点坐标
uniform mat4 uMVPMatrix; //总变换矩阵
attribute vec4 aColor;//顶点颜色
varying  vec4 vColor;//片元颜色

void main() {
  gl_Position = uMVPMatrix*vec4(vPosition,1);
  vColor = aColor;//将顶点颜色传给片元
  gl_PointSize=10.0;//设置点的大小，默认为0
}
attribute vec3 vPosition;//顶点坐标
uniform mat4 uMVPMatrix; //总变换矩阵

attribute vec2 vCoordinate;//贴图顶点坐标
varying vec2 aCoordinate;//贴图顶点坐标--片元变量

void main() {
  gl_Position = uMVPMatrix*vec4(vPosition,1);
  aCoordinate=vCoordinate;
}
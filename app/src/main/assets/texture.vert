attribute vec3 aPosition;//顶点坐标
uniform mat4 uMVPMatrix; //总变换矩阵

attribute vec2 aCoordinate;//贴图顶点坐标
varying vec2 vCoordinate;//贴图顶点坐标--片元变量

void main() {
  gl_Position = uMVPMatrix*vec4(aPosition,1);
  vCoordinate=aCoordinate;
}
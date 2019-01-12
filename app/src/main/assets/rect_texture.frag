precision mediump float;
varying vec2 aCoordinate;//贴图坐标系
uniform sampler2D vTexture;//贴图

void main() {
  gl_FragColor=texture2D(vTexture,aCoordinate);
}
const { defineConfig } = require('@vue/cli-service')
module.exports = defineConfig({
  transpileDependencies: true,
  devServer: {
    port:7999,
    proxy: {
      '/api': {
        target: 'http://localhost:8080/',
        changeOrigin: true,
        ws: true,  //是否代理 websockets
        secure: false,  //是否https接口
        pathRewrite: {  //路径重置
          '^/api': ''
        }
      }
    }
  },
})
const { defineConfig } = require('@vue/cli-service')
module.exports = defineConfig({
  transpileDependencies: true,
  devServer: {
    port: 9000,
    proxy: {
      '/api': {
        //target: 'http://localhost:8080/',
        target: 'http://1.15.170.195:87/',
        changeOrigin: true,
        ws: true,  //是否代理 websocket
        secure: false,  //是否https接口
        pathRewrite: {  //路径重置
          '^/api': ''
        }
      },
    },
    client: {
      overlay: false   //关闭Uncaught runtime errors
    }

  },

})
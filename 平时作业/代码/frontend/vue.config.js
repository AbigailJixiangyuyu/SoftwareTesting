const { defineConfig } = require('@vue/cli-service')
module.exports = defineConfig({
  transpileDependencies: true,
  devServer: {
    proxy: {
      '/testurl': {
        target: 'http://47.116.193.81:25690',
        changeOrigin: true,
        pathRewrite: {
          '^/testurl': '' // 如果接口路径中不包含 /testurl 前缀，可以通过 pathRewrite 去掉
        }
      }
    }
  }
})

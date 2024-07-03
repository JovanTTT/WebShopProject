const { defineConfig } = require('@vue/cli-service')
module.exports = defineConfig({
      transpileDependencies: true
    }
)
module.exports = {
  chainWebpack: config => {
    config.module
        .rule('images')
        .test(/.(png|jpe?g|gif|webp)$/i)
        .use('file-loader')
        .loader('file-loader')
        .options({
          name: 'assets/[name].[hash:8].[ext]'
        });
  }

};
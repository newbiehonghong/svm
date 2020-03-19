const path = require('path');
const resolve = dir => path.join(__dirname, dir);

var config = {
    publicPath: './',
    configureWebpack: {
        devtool: 'source-map'
    },
    chainWebpack: config => {
        config.resolve.alias.set('@', resolve('src'));
        if (process.env.VUE_APP_MOCK) {
            config.plugin('html')
                .tap(args => {
                    args[0].template = path.resolve('test/index.html');
                    return args
                });
            config.plugin('copy')
                .tap(args => {
                    args[0].push({
                        from: path.resolve('test'),
                        to: path.resolve('dist')
                    });
                    args[0].push({
                        from: path.resolve('node_modules/mockjs/dist/mock.js'),
                        to: path.resolve('dist')
                    });
                    return args
                });
        }
    }
};

if (process.env.VUE_APP_OUTPUT_PATH) {
    config.outputDir = process.env.VUE_APP_OUTPUT_PATH;
}
module.exports = config;
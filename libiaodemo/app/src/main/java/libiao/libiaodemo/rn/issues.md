###1、npm 安装包报错 rollbackFailedOptional

原因事设置的代理错误，删除即可

npm config rm proxy
npm config rm https-proxy

然后使用npm install -g cnpm --registry=https://registry.npm.taobao.org

安装淘宝的cnpm

然后就可以使用cnpm命令了


###2、react native 渐变 BVLinearGradient报错不存在

react-native link react-native-linear-gradient

链接后重新打包
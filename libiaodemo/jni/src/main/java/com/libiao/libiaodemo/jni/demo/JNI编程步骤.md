1、新建一个JNITest类

2、javah工具来生成c语言头文件

终端进入到JNITest.java所在目录，执行 javac JNITest.java ，得到JNITest.class文件

3、终端里面用命令行退到java文件目录下面，用javah生成c++头文件

javah -jni com.libiao.libiaodemo.jni.demo.JNITest

4、在main目录下新建cpp目录，将.h文件移至此目录，实现其cpp文件的相关逻辑

5、用CMakeList.txt配置环境

6、build.gradle配置相关信息

```
externalNativeBuild {
            cmake {
                abiFilters "armeabi-v7a", "x86"
                cppFlags "-frtti -fexceptions"
            }
}

externalNativeBuild {
        cmake {
            path "src/main/java/CMakeLists.txt"  // 设置所要编写的c源码位置，以及编译后so文件的名字
        }
}

```

7、调用这个JNITest的getString()函数就行了。
###AndroidStudio 运行main方法

1、当前项目右键->new->Module->Java Library

2、修改你创建javaLib的build.gradle文件
```
apply plugin: 'java'
apply plugin: 'application'
mainClassName = 'java.MainRun'

```
3、来个main方法
```
public class test {
    public static void main(String[] args) {
       System.out.println("libiao");
    }
}

```

4、在Java文件中- 右键- run'test.main()'


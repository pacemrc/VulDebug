# 项目介绍
使用Java编写的漏洞POC集合，主要用于Java应用漏洞的分析调试。持续复现热门开源及国产应用的漏洞。

# 配置
本项目所有漏洞调试过程中用到的依赖包下载地址：


## 开启调试

**SmartBI**

```
C:\Smartbi\Tomcat\bin\startup.bat

:START_TOMCAT
echo start Smartbi server
set JAVA_OPTS=-Xms128m -Xmx2047m -Xdebug -Xrunjdwp:transport=dt_socket,server=y,suspend=n,address=8888
```

**用友NC**

```
C:\yonyou\home\ierp\bin\prop.xml

jvmArgs标签内配置
```

## 指定代理

项目内该代码为指定HTTP代理

```
HttpRequest httpRequest = new HttpRequest("127.0.0.1",8080);
```

取消代理

```
HttpRequest httpRequest = new HttpRequest();
```

## 配置依赖


**如何快速获取指定的类所在的jar包？**

部分依赖文件夹下存在`list.txt`文件，该文件为应用下所有的jar包的类文件清单。搜索匹配到类名后，反向查找`=`号即为该类所在的jar包。（推荐配合notepad++和everything工具使用）


# 声明
本项目用于学习和交流，请勿用于非法用途。


### HTTP GET请求

- 目前支持打开的文件类型：HTML、CSS、txt、markdown、jpg/jpeg/gif/png/svg图片，mp3/音频，mp4/avi视频文件直接在浏览器中打开，其它文件均默认进行下载。

- 请求的文件放在project目录下的webroot目录中，目录中已有实例文件供测试使用

- 测试方式1：

  > 打开浏览器，输入localhost:30001/文件名，或172.0.0.1: 30001/文件名（30001为端口号，可在代码中修改PORT）

- 测试方式2：使用命令行。

  > 首先输入命令
  >
  > telnet localhost 30001 或 telnet 172.0.0.1 30001 创建连接
  >
  > 再输入请求报文：GET URI HTTP1.1，按下回车即可得到结果

### HTTP HEAD请求

- 使用命令行进行测试，方法与（1）中相同

  > 输入请求报文 HEAD URI HTTP1.1，按下回车即可得到结果

### 浏览服务器目录

> 浏览器中输入：localhost:30001/webroot(或webroot下目录名)输入如下请求报文：GET /webroot(或目录名) HTTP1.1
>
> 即可浏览服务器webroot及其中目录下的文件名称

### 控制台观察程序运行状况
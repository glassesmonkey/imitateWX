# 仿微信群视频功能
## 1.**场景**：
视频会议

## 2.**功能**：
仿微信加入群音视频模式

## 3.**实现方式**：
该场景可以通过云信提供的群客户端自定义扩展字段来实现。

（1）创建者在创建多人音视频房间成功后，修改群客户端自定义扩展字段添加创建的roomname以及自己的accid

（2）修改群扩展字段后，群内成员会收到一条群通知消息，他们从消息对象（或手动查询群扩展字段）中获取到新的扩展字段，从而进行UI层的渲染

（3）有新成员加入该多人音视频房间后，修改群客户端自定义扩展字段，在原有基础上添加自己的accid，重复（2）的操作

（4）当有有人挂断后，修改群客户端自定义扩展字段，在原有基础上删除自己的accid，重复（2）的操作

## 4.**建议**：
（1）群客户端自定义扩展字段的更新是有权限的，需要把在建群的时候把权限开放给所有成员

（2）查询和修改群客户端扩展字段是异步的，建议在他们的回调中处理业务

（3）Demo 中对这部分代码有加注释//imitateweixin ，全局搜索可以快速找到这部分代码

## 5.**Demo讲解**
1. 界面自定义了UIKIT的右上角的 ActionBar 按钮定制，添加了一个检查当前会议有无人员参加的入口（SessionTeamCustomization.java），[参考链接](https://github.com/netease-im/NIM_Android_UIKit/blob/master/documents/%E5%AE%9A%E5%88%B6%E8%81%8A%E5%A4%A9%E7%AA%97%E5%8F%A3.md#actionbar-%E5%8F%B3%E4%BE%A7%E6%8C%89%E9%92%AE%E5%AE%9A%E5%88%B6)
2. 查询和修改群客户端扩展字段在 TeamAVChatActivity.java 和 AVChatActivity.java 中实现

3. 本Demo是手动查询当前群组群聊状态的，如果需要实时，建议注册群资料变化监听 observeTeamUpdate
![入口右上角](https://s2.ax1x.com/2020/03/08/3vBPJK.png)

![有人](https://i.loli.net/2020/03/08/lUCMEPofhxVL2T6.png)

![无人](https://i.loli.net/2020/03/08/WDF4CNlHQMjocYK.png)


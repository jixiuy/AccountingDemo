# 松果记账

<img src="README/app.png" alt="app" style="zoom:50%;" />

## 应用背景

本项目旨在开发一款基于安卓系统的记账软件，以满足用户在日常消费中对账单管理的需求，其主要功能有：

1. **事件记录及账单与事件的相互绑定**：
    - 引入记事模块，允许用户记录更详细的账单相关信息。
    - 通过添加账单与事件之间的绑定功能，间接表示多个账单之间的联系。

2. **导入支付宝、微信支付账单**：
    - 添加从支付宝或微信支付等第三方支付工具导入账单的功能，方便用户整合多个平台的消费记录。

3. **其他实用功能**：
    - 新用户引导、用户基本信息管理、记账管理、记事管理、多账本管理、桌面小组件、搜索、账单导出、预算结余设置、图表统计、可自定义标签、云备份。

## 技术实现

1. **相关技术**：
   - Android平台、Java语言、Sqlite数据库。
   - 开源工具库：Android-FilePicker（文件选择工具）、EasyPermissions（权限申请工具）、MPAndroidChart（图表展示工具）。
2. **界面设计**：
   - 参考主流的UI设计，整体简洁、方便用户操作。

## 功能展示

### 添加账单

<img src="README/image-20241008115031030.png" alt="image-20241008115031030" style="zoom: 33%;" />

### 添加事件

<img src="README/image-20241008115023816.png" alt="image-20241008115023816" style="zoom:33%;" />

### 账单与事件绑定

<img src="README/image-20241008115251127.png" alt="image-20241008115251127" style="zoom: 80%;" />

### 多账本管理

<img src="README/image-20241008115128896.png" alt="image-20241008115128896" style="zoom:33%;" />

### 桌面小组件

<img src="README/image-20241008115012675.png" alt="image-20241008115012675" style="zoom:33%;" />

### 预算结余设置

<img src="README/image-20241008115725405.png" alt="image-20241008115725405" style="zoom:33%;" />

### 图表统计

<img src="README/image-20241008115734416.png" alt="image-20241008115734416" style="zoom:33%;" />

### 自定义标签

<img src="README/image-20241008115743029.png" alt="image-20241008115743029" style="zoom:33%;" />

### 云备份

云备份需要部署项目中的云端服务

<img src="README/image-20241008115046652.png" alt="image-20241008115046652" style="zoom:33%;" />
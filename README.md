# SpringBootIntegration

   ### GIT基础操作
   
     // 首次初始化本地库默认是master分支,需关联远程库.
     // 可以在本地库新建文件用于测试,步骤如下：
           git init                     // 本地库初始化
           git remote add origin xxxx   // 关联远程库地址
           git push -u origin master    // 第一次推送时
           git push origin master       // 第一次推送后，直接使用该命令即可推送修改
           
           // 创建新分支
           git checkout -b dev1        // 创建并切换分支
           git remote add origin xxxx  // 关联远程库地址
           git push origin dev1:dev1   // 把新建的本地分支push到远程库
           
           // 先拉取创建好的分支
           git pull origin dev1
           git add .                    // 添加文件到版本库（只是添加到缓存区, . 代表添加文件夹下所有文件
           git commit -m "提交备注" // 把添加的文件提交到版本库, 并填写提交备注
           git push origin dev1         // 推送到远程dev1分支上
           

# Easirobot
“智能孝子机器人后台”
层级框架如下
![Image text](https://raw.githubusercontent.com/Acheron2012/Easirobot/master/img-folder/Hierarchical.png)

更新项目（新加了文件）：
$cd ~/easirobot
$git add .                  //这样可以自动判断新加了哪些文件，或者手动加入文件名字
$git commit              //提交到本地仓库，不加参数会提示，注意:^=Ctrl，按照提示来就好了～～～
$git remote add origin git@github.com:Acheron2012/Easirobot.git        //增加到remote
$git push origin master    //不是新创建的，不用再add 到remote上了

//远程更新了代码
$git pull origin master

clone代码到本地：
$git clone  git@github.com:Acheron2012/Easirobot.git
假如本地已经存在了代码，而仓库里有更新，把更改的合并到本地的项目：
$git fetch origin    //获取远程更新
$git merge origin/master //把更新的内容合并到本地分支

撤销
$git reset
